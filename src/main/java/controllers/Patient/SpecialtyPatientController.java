package controllers.Patient;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Specialist;
import domain.Specialty;
import controllers.AbstractController;
import services.SpecialistService;
import services.SpecialtyService;

@Controller
@RequestMapping("/specialty/patient")
public class SpecialtyPatientController extends AbstractController {

	//Services......................................
	
    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private SpecialistService specialistService;

    //Constructor......................................
    
    public SpecialtyPatientController() {
        super();
    }

    // Listing.....................

    //Lista todas las especialidades
    @RequestMapping(value = "/list-all", method = RequestMethod.GET)
    public ModelAndView listAll() {

        ModelAndView result;
        Collection<Specialty> specialtys;

        specialtys = specialtyService.getAll();

        result = new ModelAndView("specialty/list");
        result.addObject("specialtys", specialtys);
        result.addObject("requestURI", "specialty/patient/list-all.do");

        return result;
    }

    //Muestra los detalles de una especialidad dada su id
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(@RequestParam int specialtyId) {

        ModelAndView result;
        Specialty specialty = specialtyService.findOneToEdit(specialtyId);

        result = createEditModelAndView(specialty);

        result.addObject("specialty", specialty);
        result.addObject("details", true);
        result.addObject("createSpecialty", false);
        result.addObject("detailsSpecialty", true);

        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(Specialty specialty) {
        assert specialty != null;
        ModelAndView result;

        result = createEditModelAndView(specialty, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(Specialty specialty, String message) {

        Assert.notNull(specialty);
        ModelAndView result;

        Collection<Specialist> specialists;

        specialists = specialistService.findSpecialistsForSpecialty(specialty);

        result = new ModelAndView("specialty/edit");
        result.addObject("specialty", specialty);
        result.addObject("message", message);
        result.addObject("createSpecialty", false);
        result.addObject("detailsSpecialty", true);
        result.addObject("specialists", specialists);

        return result;
    }

}
