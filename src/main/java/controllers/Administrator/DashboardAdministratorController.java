package controllers.Administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Patient;
import domain.Profile;
import domain.Specialist;
import services.PatientService;
import services.ProfileService;
import services.SpecialistService;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	//Services.........................................................
	
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private SpecialistService specialistService;
    
    @Autowired
    private ProfileService profileService;

    //Constructor............................................................
    
    public DashboardAdministratorController() {
        super();
    }

    // Listing ----------------------------------------------------------------

    //Muestra el panel de control del administrador con todas las estadisticas
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Integer numberOfPatients = patientService.getNumberOfPatient();
        Collection<Profile> bestSpecialist = profileService.getBestSpecialist();
        Collection<Specialist> specialistWithMoreAppointment = specialistService.getSpecialistWithMoreAppointment();
        Collection<Patient> patientWithMoreAppointment = patientService.getPatientWithMoreAppointment();
        Collection<Patient> patientWithMoreSpending = patientService.getPatientWithMoreSpending();
        Collection<Patient> patientLastYear = patientService.getPatientLastYear();

        result = new ModelAndView("dashboard/list");
        result.addObject("numberOfPatients", numberOfPatients);
        result.addObject("bestSpecialist", bestSpecialist);
        result.addObject("specialistWithMoreAppointment", specialistWithMoreAppointment);
        result.addObject("patientWithMoreAppointment", patientWithMoreAppointment);
        result.addObject("patientWithMoreSpending", patientWithMoreSpending);
        result.addObject("patientLastYear", patientLastYear);

        result.addObject("requestURI", "dashboard/administrator/dashboard.do");

        return result;
    }
}
