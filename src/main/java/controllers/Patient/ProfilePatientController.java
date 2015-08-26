package controllers.Patient;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Appointment;
import domain.Comment;
import domain.Patient;
import domain.Specialist;
import forms.PatientForm2;
import forms.PatientForm3;
import forms.PatientForm4;
import services.AppointmentService;
import services.CommentService;
import services.PatientService;
import services.SpecialistService;

@Controller
@RequestMapping("/profile/patient")
public class ProfilePatientController extends AbstractController {

	// Services -----------------------------------------------------------

	@Autowired
	private SpecialistService specialistService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private PatientService patientService;

	// Constructors -----------------------------------------------------------

	public ProfilePatientController() {
		super();
	}
	
	//para recuperar imagen de perfil de un paciente de la base de datos y mostrarla
	@RequestMapping(value = "/showImage")
    public ModelAndView showImage(HttpServletResponse response, @RequestParam int patientId) throws Exception {

			Patient patient = patientService.findOneToEdit(patientId);
     

            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            response.setContentLength(patient.getImage().length);
            response.setHeader("Content-Disposition","attachment; filename=\"" + patient.getName() +"\"");
     
            FileCopyUtils.copy(patient.getImage(), response.getOutputStream());
     
            return null;
     
        }

	//Muestra los datos personales del paciente
	@RequestMapping(value = "/myPersonalDatas", method = RequestMethod.GET)
	public ModelAndView personalDatas() {

		ModelAndView result;

		Patient patient = patientService.findByPrincipal();

		result = new ModelAndView("personalData/edit");
		result.addObject("detail", true);
		result.addObject("patient", patient);
		if(patient.getImage()!=null && patient.getImage().length>0){
			result.addObject("haveImage", true);
		}else{
			result.addObject("haveImage", false);
		}

		return result;
	}

	// Details.....................
	
	//Muestra el perfil de un especialista dado su id
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int specialistId) {

		ModelAndView result;

		Specialist specialist = specialistService.findOneToEdit(specialistId);
		Collection<Comment> comments = commentService
				.getCommentsForSpecialist(specialist);

		Patient patient = patientService.findByPrincipal();
		Collection<Appointment> appointments = appointmentService
				.getAppointmentforOneSpecialistAndOnePatient(specialist,
						patient);
		result = new ModelAndView("profile/edit");
		if ((patient.getSpecialist() == null && specialist.getSpecialty()
				.getName().equals("Medicina General"))
				|| (patient.getSpecialist() != null
						&& !patient.getSpecialist().equals(specialist) && specialist
						.getSpecialty().getName().equals("Medicina General"))) {
			result.addObject("isGP", true);
		} else {
			result.addObject("isGP", false);
		}
		if (patient.getSpecialist().equals(specialist)
				&& specialist.getSpecialty().getName()
						.equals("Medicina General")) {
			result.addObject("GPmine", true);
		} else {
			result.addObject("GPmine", false);
		}

		if (!appointments.isEmpty()) {
			result.addObject("hasAppointmentFinish", true);
		} else {
			result.addObject("hasAppointmentFinish", false);
		}
		result.addObject("specialist", specialist);
		result.addObject("comments", comments);
		result.addObject("requestURI",
				"profile/patient/details.do?specialistId=" + specialistId);
		result.addObject("detailsProfile", true);

		return result;
	}

	//Cambia el medico de cabecera del paciente por el que se le pasa como parametro de entrada
	@RequestMapping(value = "/change", method = RequestMethod.GET)
	public ModelAndView change(@RequestParam int specialistId) {
		ModelAndView result;

		Specialist specialist = specialistService.findOneToEdit(specialistId);
		result = new ModelAndView("profile/edit");
		Patient patient = patientService.findByPrincipal();
		patientService.save(patient, specialist);
		Collection<Comment> comments = commentService
				.getCommentsForSpecialist(specialist);

		result.addObject("specialist", specialist);
		result.addObject("comments", comments);
		result.addObject("requestURI",
				"profile/patient/details.do?specialistId=" + specialistId);
		result.addObject("detailsProfile", true);
		result.addObject("GPmine", true);

		return result;
	}

	//Editar datos personales del paciente
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editPersonalDatas() {

		ModelAndView result;
		Patient patient = patientService.findByPrincipal();
		PatientForm2 patientForm2 = new PatientForm2();

		patientForm2.setAddress(patient.getAddress());
		patientForm2.setEmailAddress(patient.getEmailAddress());
		patientForm2.setPhone(patient.getPhone());

		result = createEditModelAndView(patientForm2);

		return result;
	}

	// Cambiar contraseña del paciente
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword() {

		ModelAndView result;
		PatientForm3 patientForm3 = new PatientForm3();

		result = createEditModelAndView2(patientForm3);

		return result;
	}
	
	// Cambiar datos de la tarjeta de credito
	@RequestMapping(value = "/editCreditCard", method = RequestMethod.GET)
	public ModelAndView editCreditCard() {

		ModelAndView result;
		PatientForm4 patientForm4 = new PatientForm4();
		
		Patient patient = patientService.findByPrincipal();

		patientForm4.setCreditCard(patient.getCreditCard());


		result = createEditModelAndView3(patientForm4);

		return result;
	}

	//Guarda los nuevos datos personales del paciente en la base de datos
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid PatientForm2 patientForm2,
			BindingResult binding) {

		ModelAndView result;
		Patient patient;

		if (binding.hasErrors()) {
			result = createEditModelAndView(patientForm2);

		} else {

			try {

				patient = patientService.reconstruct2(patientForm2);

				patientService.save2(patient);

				result = new ModelAndView("redirect:myPersonalDatas.do");

			} catch (Throwable oops) {

				result = createEditModelAndView(patientForm2,
						"personalData.commit.error");

			}
		}
		return result;
	}

	//Guarda la nueva contraseña del paciente en la base de datos
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, params = "save2")
    public ModelAndView save2(@Valid PatientForm3 patientForm, BindingResult binding) {

        ModelAndView result;
        Patient patient;

        Patient patientConnect = patientService.findByPrincipal();
        
        if (binding.hasErrors()) {
            result = createEditModelAndView2(patientForm);

        } else {

            try {
            	
                patient = patientService.reconstruct3(patientForm);

                patientService.save2(patient);

                result = new ModelAndView("redirect:myPersonalDatas.do");

            }catch (Throwable oops) {
            	
                Md5PasswordEncoder encoder;
                String pass = patientForm.getOldPassword();
                encoder = new Md5PasswordEncoder();
                String hash = encoder.encodePassword(pass, null);

            	if(!(hash.equals(patientConnect.getUserAccount().getPassword()))){
            		result = createEditModelAndView2(patientForm, "personalData.password.error");
            	}else if(!(patientForm.getSecondPassword().equals(patientForm.getNewPassword()))){
            		result = createEditModelAndView2(patientForm, "personalData.wrongSecondPassword.error");
            	}else{
                result = createEditModelAndView2(patientForm, "personalData.commit.error");
            	}
            }
        }
        return result;
    }
	
	//Guarda los nuevos datos bancarios del paciente en la base de datos
	@RequestMapping(value = "/editCreditCard", method = RequestMethod.POST, params = "save3")
	public ModelAndView save3(@Valid PatientForm4 patientForm4,
			BindingResult binding) {

		ModelAndView result;
		Patient patient;

		if (binding.hasErrors()) {
			result = createEditModelAndView3(patientForm4);

		} else {

			try {

				patient = patientService.reconstruct4(patientForm4);

				patientService.save2(patient);

				result = new ModelAndView("redirect:myPersonalDatas.do");

			} catch (Throwable oops) {

				result = createEditModelAndView3(patientForm4,
						"personalData.commit.error");

			}
		}
		return result;
	}
	
    // Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(PatientForm2 patientForm) {

		ModelAndView result;

		result = createEditModelAndView(patientForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(PatientForm2 patientForm,
			String message) {

		ModelAndView result;

		result = new ModelAndView("personalData/edit");
		result.addObject("detail", false);
		result.addObject("patientForm2", patientForm);
		result.addObject("requestURI", "profile/patient/edit.do");

		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndView2(PatientForm3 patientForm) {

		ModelAndView result;

		result = createEditModelAndView2(patientForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView2(PatientForm3 patientForm,
			String message) {

		ModelAndView result;

		result = new ModelAndView("personalData/edit");
		result.addObject("changePassword", true);
		result.addObject("patientForm3", patientForm);
		result.addObject("requestURI", "profile/patient/changePassword.do");

		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView createEditModelAndView3(PatientForm4 patientForm) {

		ModelAndView result;

		result = createEditModelAndView3(patientForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView3(PatientForm4 patientForm,
			String message) {

		ModelAndView result;

		result = new ModelAndView("personalData/edit");
		result.addObject("changeCreditCard", true);
		result.addObject("patientForm4", patientForm);
		result.addObject("requestURI", "profile/patient/editCreditCard.do");

		result.addObject("message", message);

		return result;
	}

}
