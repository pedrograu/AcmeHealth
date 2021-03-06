package controllers.Specialist;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FreeDayService;
import services.SpecialistService;
import controllers.AbstractController;
import domain.FreeDay;
import domain.Specialist;
import forms.FreeDayForm;

@Controller
@RequestMapping("/freeDay/specialist")
public class FreeDaySpecialistController extends AbstractController {

	//Services........................................................................
	
    @Autowired
    private SpecialistService specialistService;
    
    @Autowired
    private FreeDayService freeDayService;

    //Constructors.......................................................................
    
    public FreeDaySpecialistController() {
        super();
    }

    // List -------------------------------------------------------------------

    //Lista los dias libres del especialista logueado
    @RequestMapping(value = "/list-own", method = RequestMethod.GET)
    public ModelAndView listOwn() {

        ModelAndView result;
        Collection<FreeDay> freeDays;
        Specialist specialistConnect = specialistService.findByPrincipal();

        freeDays = freeDayService.getFreeDaysForSpecialist(specialistConnect);

        result = new ModelAndView("freeDay/list");
        result.addObject("freeDays", freeDays);
        result.addObject("requestURI", "freeDay/specialist/list-own.do");

        return result;
    }

    // Edition -----------------------------------------------------------------

    //Crea un nuevo freeday
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {

        ModelAndView result;
        Collection<FreeDay> freeDays;
        Specialist specialistConnect = specialistService.findByPrincipal();

        freeDays = freeDayService.getFreeDaysForSpecialist(specialistConnect);


        FreeDayForm freeDayForm = new FreeDayForm();

        result = createEditModelAndView(freeDayForm);
        result.addObject("freeDayForm", freeDayForm);
        result.addObject("freeDays", freeDays);

        return result;
    }

    //Guarda en la base de datos un freeday
    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid FreeDayForm freeDayForm, BindingResult binding) {

        ModelAndView result;
        FreeDay freeDay;

        if (binding.hasErrors()) {
            result = createEditModelAndView(freeDayForm);
        } else {
            try {
                freeDay = freeDayService.recontructor(freeDayForm);
                freeDayService.save(freeDay);
                result = new ModelAndView("redirect:create.do");

            } catch (Throwable oops) {
                result = createEditModelAndView(freeDayForm, "freeDay.commit.error");
            }
        }

        return result;

    }

    //Elimina un freeday
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int freeDayId) {

        ModelAndView result;
        FreeDay freeDay = freeDayService.findOneToEdit(freeDayId);

        freeDayService.delete(freeDay);
        Collection<FreeDay> freeDays;

        Specialist specialistConnect = specialistService.findByPrincipal();

        freeDays = freeDayService.getFreeDaysForSpecialist(specialistConnect);

        result = new ModelAndView("freeDay/edit");
        result.addObject("freeDays", freeDays);
        FreeDayForm freeDayForm = new FreeDayForm();
        result.addObject("freeDayForm", freeDayForm);
        result = new ModelAndView("redirect:create.do");

        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(FreeDayForm freeDayForm) {
        assert freeDayForm != null;
        ModelAndView result;

        result = createEditModelAndView(freeDayForm, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(FreeDayForm freeDayForm, String message) {

        Assert.notNull(freeDayForm);
        ModelAndView result;

        result = new ModelAndView("freeDay/edit");
        result.addObject("requestURI", "freeDay/specialist/edit.do?freeDayId=" + freeDayForm.getId());

        result.addObject("message", message);
        result.addObject("freeDayForm", freeDayForm);
        
        
        Collection<FreeDay> freeDays;
        Specialist specialistConnect = specialistService.findByPrincipal();
        freeDays = freeDayService.getFreeDaysForSpecialist(specialistConnect);
        result.addObject("freeDays", freeDays);

        return result;
    }

}
