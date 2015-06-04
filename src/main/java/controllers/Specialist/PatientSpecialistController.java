package controllers.Specialist;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PatientService;
import controllers.AbstractController;
import domain.Patient;

@Controller
@RequestMapping("/patient/specialist")
public class PatientSpecialistController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private PatientService patientService;

    // Constructors -----------------------------------------------------------

    public PatientSpecialistController() {
        super();
    }

    // List ------------------------------------------------------------------		

    @RequestMapping(value = "/list-own", method = RequestMethod.GET)
    public ModelAndView listOwn() {

        ModelAndView result;
        Collection<Patient> patients;

        patients = patientService.findOwn();

        result = new ModelAndView("patient/list");
        result.addObject("patients", patients);
        result.addObject("today", false);
        result.addObject("isSpecialist", true);
        result.addObject("requestURI", "patient/specialist/list-own.do");

        return result;
    }

    @RequestMapping(value = "/list-today", method = RequestMethod.GET)
    public ModelAndView listToday() {

        ModelAndView result;
        Collection<Patient> patients;

        patients = patientService.findToday();

        result = new ModelAndView("patient/list");
        result.addObject("patients", patients);
        result.addObject("today", true);
        result.addObject("isSpecialist", true);
        result.addObject("requestURI", "patient/specialist/list-today.do");

        return result;
    }

}
