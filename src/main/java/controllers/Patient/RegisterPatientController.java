package controllers.Patient;

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
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import services.MedicalHistoryService;
import services.PatientService;
import services.SpecialistService;
import controllers.AbstractController;
import domain.Patient;
import domain.Specialist;
import forms.PatientForm;
import forms.TokenForm;

@Controller
@RequestMapping("/register/patient")
public class RegisterPatientController extends AbstractController {

    // Services ---------------------------------------------------------------

    @Autowired
    private PatientService patientService;

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @Autowired
    private SpecialistService specialistService;

    // Constructors
    // ---------------------------------------------------------------

    public RegisterPatientController() {
        super();
    }
    
    
    //Convertidor de imagenes
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {

		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	

    // Registration -----------------------------------------------------------

    @RequestMapping(value = "/mutua", method = RequestMethod.GET)
    public ModelAndView mutua() {
        ModelAndView result;
        TokenForm tokenForm = new TokenForm();

        result = createEditModelAndView2(tokenForm);

        return result;
    }

    @RequestMapping(value = "/mutua", method = RequestMethod.POST, params = "check")
    public ModelAndView check(@Valid TokenForm tokenForm, BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors()) {
            result = createEditModelAndView2(tokenForm);

        } else {
            String nif = tokenForm.getNif();
            String pass = tokenForm.getPass();
            
            //encriptamos pass
            Md5PasswordEncoder encoder;
            encoder = new Md5PasswordEncoder();
            String hash = encoder.encodePassword(pass, null);

            String token = patientService.getToken(nif, hash);

            if (token == "null") {
                result = new ModelAndView("register/mutua");
                result.addObject("error", true);
                result.addObject("tokenForm", tokenForm);
                result.addObject("actor", "tokenForm");
            } else {
                result = new ModelAndView("redirect:edit.do?token=" + token);
            }

        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView result;
        PatientForm patientForm = new PatientForm();

        result = createEditModelAndView(patientForm);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid PatientForm patientForm, BindingResult binding) {

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
            	
            	
                result = createEditModelAndView(patientForm, "register.duplicate.error");

            } catch (Throwable oops) {
                oops.getMessage();

                if (!(patientForm.getSecondPassword().equals(patientForm.getPassword()))) {
                    result = createEditModelAndView(patientForm, "register.wrongSecondPassword.error");
                } else {

                    if (patientForm.getAvailable() == false) {
                        result = createEditModelAndView(patientForm, "register.available.error");
                    } else if (!currentMoment.before(dateCreditCard)) {
                        result = createEditModelAndView(patientForm, "register.creditCard.error");
                    } else if (!patientService.getTokens().contains(patientForm.getToken())) {
                        result = createEditModelAndView(patientForm, "register.token.error");
                    } 
                    else {
                        result = createEditModelAndView(patientForm, "register.commit.error");
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

    protected ModelAndView createEditModelAndView(PatientForm patientForm, String message) {

        ModelAndView result;

        Patient patient = patientService.create();
        Collection<Specialist> specialists;

        specialists = specialistService.findAllSpecialistsOfGeneralMedicine();

        result = new ModelAndView("register/edit");
        result.addObject("patientForm", patientForm);
        result.addObject("patient", patient);
        result.addObject("actor", "patientForm");
        result.addObject("requestURI", "register/patient/edit.do");
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

    protected ModelAndView createEditModelAndView2(TokenForm tokenForm, String message) {

        ModelAndView result;

        result = new ModelAndView("register/mutua");
        result.addObject("tokenForm", tokenForm);
        result.addObject("actor", "tokenForm");
        result.addObject("error", false);
        result.addObject("requestURI", "register/patient/mutua.do");

        result.addObject("message", message);

        return result;
    }

}
