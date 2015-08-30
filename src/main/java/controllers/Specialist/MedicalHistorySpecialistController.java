package controllers.Specialist;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MedicalHistoryService;

import controllers.AbstractController;
import domain.MedicalHistory;

@Controller
@RequestMapping("/medicalHistory/specialist")
public class MedicalHistorySpecialistController extends AbstractController {

	//Services.........................................................
	
    @Autowired
    private MedicalHistoryService medicalHistoryService;
    
    //Constructors......................................................
    
    public MedicalHistorySpecialistController() {
        super();
    }

    //Muestra los detalles del historial medico dado su id
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(@RequestParam int medicalHistoryId) {

        ModelAndView result;
        MedicalHistory medicalHistory = medicalHistoryService.findOneToEdit(medicalHistoryId);

        result = new ModelAndView("medicalHistory/edit");
        result.addObject("medicalHistory", medicalHistory);
        result.addObject("detailsMedicalHistory", true);

        return result;
    }

    //Editar la informacion del historial medico
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int medicalHistoryId) {

        ModelAndView result;

        MedicalHistory medicalHistory = medicalHistoryService.findOneToEdit(medicalHistoryId);

        result = createEditModelAndView(medicalHistory);
        result.addObject("detailsMedicalHistory", false);

        return result;
    }

    //Guarda el historial medico en la bd
    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid MedicalHistory medicalHistory, BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors()) {
            result = createEditModelAndView(medicalHistory);
        } else {
            try {
                medicalHistoryService.save(medicalHistory);
                result = new ModelAndView("redirect:detail.do?medicalHistoryId=" + medicalHistory.getId());
            } catch (Throwable oops) {
                result = createEditModelAndView(medicalHistory, "medicalHistory.commit.error");
            }
        }

        return result;

    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(MedicalHistory medicalHistory) {
        assert medicalHistory != null;
        ModelAndView result;

        result = createEditModelAndView(medicalHistory, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(MedicalHistory medicalHistory, String message) {

        Assert.notNull(medicalHistory);
        ModelAndView result;

        result = new ModelAndView("medicalHistory/edit");

        result.addObject("medicalHistory", medicalHistory);
        result.addObject("requestURI", "medicalHistory/specialist/edit.do?medicalHistoryId=" + medicalHistory.getId());
        result.addObject("message", message);
        result.addObject("detailsMedicalHistory", false);

        return result;
    }

}