package controllers.Patient;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AppointmentService;
import services.CommentService;
import services.PatientService;
import services.ProfileService;
import services.SpecialistService;
import controllers.AbstractController;
import domain.Appointment;
import domain.Comment;
import domain.Patient;
import domain.Specialist;

@Controller
@RequestMapping("/profile/patient")
public class ProfilePatientController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private ProfileService profileService;

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

    // Details.....................
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView details(@RequestParam int specialistId) {

        ModelAndView result;

        Specialist specialist = specialistService.findOneToEdit(specialistId);
        Collection<Comment> comments = commentService.getCommentsForSpecialist(specialist);

        Patient patient = patientService.findByPrincipal();
        Collection<Appointment> appointments = appointmentService.getAppointmentforOneSpecialistAndOnePatient(
                specialist, patient);
        result = new ModelAndView("profile/edit");
        if ((patient.getSpecialist() == null && specialist.getSpecialty().getName().equals("Medicina General"))
                || (patient.getSpecialist() != null && !patient.getSpecialist().equals(specialist) && specialist
                        .getSpecialty().getName().equals("Medicina General"))) {
            result.addObject("isGP", true);
        } else {
            result.addObject("isGP", false);
        }
        if (patient.getSpecialist().equals(specialist) && specialist
                .getSpecialty().getName().equals("Medicina General")){
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
        result.addObject("requestURI", "profile/patient/details.do?specialistId=" + specialistId);
        result.addObject("detailsProfile", true);

        return result;
    }

    // Change.....................
    @RequestMapping(value = "/change", method = RequestMethod.GET)
    public ModelAndView change(@RequestParam int specialistId) {
        ModelAndView result;

        Specialist specialist = specialistService.findOneToEdit(specialistId);
        result = new ModelAndView("profile/edit");
        Patient patient = patientService.findByPrincipal();
        patientService.save(patient, specialist);
        Collection<Comment> comments = commentService.getCommentsForSpecialist(specialist);

        result.addObject("specialist", specialist);
        result.addObject("comments", comments);
        result.addObject("requestURI", "profile/patient/details.do?specialistId=" + specialistId);
        result.addObject("detailsProfile", true);

        return result;
    }

}
