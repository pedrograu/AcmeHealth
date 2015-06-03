package controllers.Administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PatientService;
import controllers.AbstractController;
import domain.Patient;

@Controller
@RequestMapping("/patient/administrator")
public class PatientAdministratorController extends AbstractController {

	@Autowired
	private PatientService patientService;

	public PatientAdministratorController() {
		super();
	}

	// List --------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		Collection<Patient> patients = patientService.findAll();

		result = new ModelAndView("patient/list");
		result.addObject("patients", patients);
		result.addObject("requestURI", "patient/administrator/list.do");

		return result;
	}

	// Details.....................
	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam int patientId) {

		ModelAndView result;

		Patient patient = patientService.findOneToEdit(patientId);
		patientService.ban(patient);
		Collection<Patient> patients = patientService.findAll();

		result = new ModelAndView("patient/list");
		result.addObject("patients", patients);
		result.addObject("requestURI", "patient/administrator/list.do");

		return result;
	}

}
