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

import services.AppointmentService;
import services.PatientService;
import services.PrescriptionService;
import controllers.AbstractController;
import domain.Appointment;
import domain.Patient;
import domain.Prescription;


@Controller
@RequestMapping("/prescription/specialist")
public class PrescriptionSpecialistController extends AbstractController {
	
	// Services ----------------------------------------------------------------
	
	@Autowired
	private PrescriptionService prescriptionService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	
	// Constructor ----------------------------------------------------------------
	
	public PrescriptionSpecialistController() {
		super();
	}
	
	//List --------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int patientId) {

		ModelAndView result;
		Patient patientConnect = patientService.findOneToEdit(patientId);
		Collection<Prescription> prescriptions;

		prescriptions = prescriptionService.findForPatient(patientConnect);

		result = new ModelAndView("prescription/list");
		result.addObject("prescriptions", prescriptions);
		result.addObject("requestURI", "prescription/specialist/list.do");

		return result;
	}
	
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int appointmentId) {

		ModelAndView result;
		
		Prescription prescription;
		
		Appointment a = appointmentService.findOneToEdit(appointmentId);

		prescription = prescriptionService.create(a);

		result = createEditModelAndView(prescription);

		
		result.addObject("prescription", prescription);
		result.addObject("isPatient", false);
		

		return result;
	}

	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Prescription prescription, BindingResult binding) {

		ModelAndView result ;


		if (binding.hasErrors()) {
			result = createEditModelAndView(prescription);
		} else {
			try {
				prescriptionService.save(prescription);
				result = new ModelAndView("redirect:../../appointment/specialist/edit.do?appointmentId="+prescription.getAppointment().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(prescription,"prescription.commit.error");
			}
		}
		
		return result;

	}

	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Prescription prescription) {
		assert prescription != null;
		ModelAndView result;

		result = createEditModelAndView(prescription, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Prescription prescription,String message) {
		
		
		Assert.notNull(prescription);
		ModelAndView result;
		

	result = new ModelAndView("prescription/edit");

	result.addObject("prescription", prescription);
	result.addObject("isPatient", false);
	result.addObject("requestURI","prescription/specialist/edit.do?prescriptionId="+prescription.getId());
	result.addObject("message", message);


	

		return result;
	}



}