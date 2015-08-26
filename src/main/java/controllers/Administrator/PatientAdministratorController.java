package controllers.Administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Patient;
import domain.PatientNotAccepted;
import services.PatientNotAcceptedService;
import services.PatientService;

@Controller
@RequestMapping("/patient/administrator")
public class PatientAdministratorController extends AbstractController {

    // Services --------------------------------------------------------------
	
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private PatientNotAcceptedService patientNotAcceptedService;

    // Constructor --------------------------------------------------------------
    
    public PatientAdministratorController() {
        super();
    }

    // List --------------------------------------------------------------
    
    //Lista todos los pacientes registrados en el sistema
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {

        ModelAndView result;

        Collection<Patient> patients = patientService.findAll();

        result = new ModelAndView("patient/list");
        result.addObject("patients", patients);
        result.addObject("requestURI", "patient/administrator/list.do");

        return result;
    }
    
    //listado de pacientes no dados de alta (los que se registran como persona particular y tienen que esperar a que el admin los acepte)
    @RequestMapping(value = "/listPatientsNoDischarged", method = RequestMethod.GET)
    public ModelAndView listPatientsNoDischarged() {

        ModelAndView result;

        Collection<PatientNotAccepted> listPatientsNotAccepted = patientNotAcceptedService.findAll();

        result = new ModelAndView("patientNotAccepted/list");
        result.addObject("listPatientsNotAccepted", listPatientsNotAccepted);
        result.addObject("requestURI", "patient/administrator/listPatientsNoDischarged.do");

        return result;
    }

    //Permitir/prohibir el envío de mensajes a un paciente
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
    
    //Aceptar en el sistema a un paciente que se registra como persona particular
    @RequestMapping(value = "/discharge", method = RequestMethod.GET)
    public ModelAndView discharge(@RequestParam int patientNotAcceptedId) {

        ModelAndView result;

        PatientNotAccepted patientNotAccepted = patientNotAcceptedService.findOneToEdit(patientNotAcceptedId);
        patientNotAcceptedService.discharge(patientNotAccepted);
        Collection<PatientNotAccepted> listPatientsNotAccepted = patientNotAcceptedService.findAll();

        result = new ModelAndView("patientNotAccepted/list");
        result.addObject("listPatientsNotAccepted", listPatientsNotAccepted);
        result.addObject("requestURI", "patient/administrator/listPatientsNoDischarged.do");

        return result;
    }

}
