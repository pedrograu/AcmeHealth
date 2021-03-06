package controllers.Patient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Patient;
import domain.Specialist;
import forms.PatientForm;
import forms.PatientForm5;
import forms.TokenForm;
import services.PatientNotAcceptedService;
import services.PatientService;
import services.SpecialistService;

@Controller
@RequestMapping("/register/patient")
public class RegisterPatientController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PatientNotAcceptedService patientNotAcceptedService;

	@Autowired
	private SpecialistService specialistService;

	// Constructors---------------------------------------------------------------

	public RegisterPatientController() {
		super();
	}

	// Convertidor de imagenes
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {

		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	// Registration -----------------------------------------------------------

	//Selecciona el tipo de registro (como particular o a traves de mutua)
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public ModelAndView select() {
		ModelAndView result;

		result = new ModelAndView("register/select");

		return result;
	}
	
	//Selecciona la mutua (para el registro a traves de mutua)
	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public ModelAndView company() {
		ModelAndView result;

		result = new ModelAndView("register/company");

		return result;
	}

	//Introducir el nif y la contraseņa proporcionada por la mutua (para el registro a traves de mutua)
	@RequestMapping(value = "/mutua", method = RequestMethod.GET)
	public ModelAndView mutua(@RequestParam String name) {
		ModelAndView result;

		TokenForm tokenForm = new TokenForm();
		tokenForm.setName(name);

		result = createEditModelAndView2(tokenForm);

		return result;
	}

	//Comprueba que el token es valido para los datos personales introducidos
	@RequestMapping(value = "/mutua", method = RequestMethod.POST, params = "check")
	public ModelAndView check(@Valid TokenForm tokenForm, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView2(tokenForm);

		} else {
			String name = tokenForm.getName();
			String nif = tokenForm.getNif();
			String pass = tokenForm.getPass();

			// encriptamos pass
			Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();
			String hash = encoder.encodePassword(pass, null);

			String token = patientService.getToken(name, nif, hash);

			if (token == "null") {
				result = new ModelAndView("register/mutua");
				result.addObject("error", true);
				result.addObject("tokenForm", tokenForm);
				result.addObject("actor", "tokenForm");
			} else {

				ArrayList<String> array = patientService.getPersonalData(token);

				result = new ModelAndView("redirect:edit.do?token=" + token
						+ "&name=" + array.get(0) + "&surname=" + array.get(1)
						+ "&address=" + array.get(2) + "&email=" + array.get(3)
						+ "&phone=" + array.get(4));
			}

		}
		return result;
	}

	//Muestra el formulario de registro (para el registro a traves de mutua)
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		PatientForm patientForm = new PatientForm();

		result = createEditModelAndView(patientForm);

		return result;
	}
	
	//Muestra el formulario de registro (para el registro como persona particular)
	@RequestMapping(value = "/editParticulate", method = RequestMethod.GET)
	public ModelAndView registerParticulate() {
		ModelAndView result;
		PatientForm5 patientForm = new PatientForm5();

		result = createEditModelAndView3(patientForm);

		return result;
	}

	//Guarda el nuevo paciente en la base de datos (registro a traves de mutua)
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid PatientForm patientForm,
			BindingResult binding) {

		ModelAndView result;
		Patient patient;
		Calendar currentMoment = new GregorianCalendar();
		int month = patientForm.getCreditCard().getExpirationMonth();
		int year = patientForm.getCreditCard().getExpirationYear();
		Calendar dateCreditCard = new GregorianCalendar();
		dateCreditCard.set(year, month, 1);

		if (binding.hasErrors()) {
			result = createEditModelAndView(patientForm);

		} else {

			try {
				patient = patientService.reconstruct(patientForm);

				patientService.save(patient);

				result = new ModelAndView("redirect:../../security/login.do");

			} catch (DataIntegrityViolationException oops) {

				result = createEditModelAndView(patientForm,
						"register.duplicate.error");

			} catch (Throwable oops) {
				oops.getMessage();

				if (!(patientForm.getSecondPassword().equals(patientForm
						.getPassword()))) {
					result = createEditModelAndView(patientForm,
							"register.wrongSecondPassword.error");
				} else {

					if (patientForm.getAvailable() == false) {
						result = createEditModelAndView(patientForm,
								"register.available.error");
					} else if (!currentMoment.before(dateCreditCard)) {
						result = createEditModelAndView(patientForm,
								"register.creditCard.error");
					} else if (!patientService.tokenIsValid((patientForm
							.getToken()))) {
						result = createEditModelAndView(patientForm,
								"register.token.error");
					} else {
						result = createEditModelAndView(patientForm,
								"register.commit.error");
					}
				}

			}
		}
		return result;
	}
	
	//Guarda el nuevo paciente en la base de datos (registro como persona particular)
	@RequestMapping(value = "/editParticulate", method = RequestMethod.POST, params = "save2")
	public ModelAndView save2(@Valid PatientForm5 patientForm,
			BindingResult binding) {

		ModelAndView result;
		Patient patient;
		Calendar currentMoment = new GregorianCalendar();
		int month = patientForm.getCreditCard().getExpirationMonth();
		int year = patientForm.getCreditCard().getExpirationYear();
		Calendar dateCreditCard = new GregorianCalendar();
		dateCreditCard.set(year, month, 1);
		Collection<String> creditCardNumbers = patientService.getAllCreditCardNumber();
		Collection<String> 	nameUserPatiens = patientService.getAllNameUserPatient();

		if (binding.hasErrors()) {
			result = createEditModelAndView3(patientForm);

		} else {

			try {
				patient = patientService.reconstruct5(patientForm);

				patientNotAcceptedService.savePatientNotAccepted(patient);

				result = new ModelAndView("redirect:../../security/login.do");

			} catch (DataIntegrityViolationException oops) {

				result = createEditModelAndView3(patientForm,
						"register.duplicate.error");

			} catch (Throwable oops) {
				oops.getMessage();

				if (!(patientForm.getSecondPassword().equals(patientForm
						.getPassword()))) {
					result = createEditModelAndView3(patientForm,
							"register.wrongSecondPassword.error");
				} else {

					if (patientForm.getAvailable() == false) {
						result = createEditModelAndView3(patientForm,
								"register.available.error");
					} else if (!currentMoment.before(dateCreditCard)) {
						result = createEditModelAndView3(patientForm,
								"register.creditCard.error");
					}else if (creditCardNumbers.contains(patientForm.getCreditCard().getNumber())) {
						result = createEditModelAndView3(patientForm,
								"register.creditCardNumber.error");
					}else if (nameUserPatiens.contains(patientForm.getUsername())) {
						result = createEditModelAndView3(patientForm,
								"register.duplicateNameUser.error");
					} else {
						result = createEditModelAndView3(patientForm,
								"register.commit.error");
					}
				}

			}
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(PatientForm patientForm) {

		ModelAndView result;

		result = createEditModelAndView(patientForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(PatientForm patientForm,
			String message) {

		ModelAndView result;

		Patient patient = patientService.create();
		Collection<Specialist> specialists;

		String token = patientForm.getToken();
		ArrayList<String> array = new ArrayList<String>();
		if (token != null) {
			array = patientService.getPersonalData(token);
		}

		specialists = specialistService.findAllSpecialistsOfGeneralMedicine();

		result = new ModelAndView("register/edit");
		result.addObject("patientForm", patientForm);
		result.addObject("patient", patient);
		result.addObject("actor", "patientForm");

		if (token != null) {
			result.addObject(
					"requestURI",
					"register/patient/edit.do?token=" + token + "&name="
							+ array.get(0) + "&surname=" + array.get(1)
							+ "&address=" + array.get(2) + "&email="
							+ array.get(3) + "&phone=" + array.get(4));
		}
		result.addObject("registerPatient", true);

		result.addObject("specialists", specialists);

		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndView2(TokenForm tokenForm) {

		ModelAndView result;

		result = createEditModelAndView2(tokenForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView2(TokenForm tokenForm,
			String message) {

		ModelAndView result;

		result = new ModelAndView("register/mutua");
		result.addObject("tokenForm", tokenForm);
		result.addObject("actor", "tokenForm");
		result.addObject("error", false);
		result.addObject("requestURI", "register/patient/mutua.do?name="
				+ tokenForm.getName());

		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView createEditModelAndView3(PatientForm5 patientForm) {

		ModelAndView result;

		result = createEditModelAndView3(patientForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView3(PatientForm5 patientForm,
			String message) {

		ModelAndView result;

		Collection<Specialist> specialists;


		specialists = specialistService.findAllSpecialistsOfGeneralMedicine();

		result = new ModelAndView("register/edit");
		result.addObject("patientForm5", patientForm);

		result.addObject("requestURI","register/patient/editParticulate.do");
		
		result.addObject("registerPatientParticulate", true);

		result.addObject("specialists", specialists);

		result.addObject("message", message);

		return result;
	}

}
