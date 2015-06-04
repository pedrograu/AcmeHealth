package controllers.Patient;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SpecialistService;
import controllers.AbstractController;
import domain.Specialist;

@Controller
@RequestMapping("/specialist/patient")
public class SpecialistPatientController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private SpecialistService specialistService;

    // Constructors -----------------------------------------------------------

    public SpecialistPatientController() {
        super();
    }

    // List ------------------------------------------------------------------		

    //listar todos los especialistas del sistema
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {

        ModelAndView result;
        Collection<Specialist> specialists;

        specialists = specialistService.findAllSpecialists();

        result = new ModelAndView("specialist/list");
        result.addObject("specialists", specialists);
        result.addObject("requestURI", "specialist/patient/list.do");

        return result;
    }

}
