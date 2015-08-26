package controllers.Patient;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AppointmentService;
import controllers.AbstractController;
import domain.Appointment;

@Controller
@RequestMapping("/customerArea/patient")
public class CustomerAreaPatientController extends AbstractController {

	//Services..............................................................
	
    @Autowired
    private AppointmentService appointmentService;

    //Constructors.........................................................
    
    public CustomerAreaPatientController() {
        super();
    }

    // Listing ----------------------------------------------------------------

    //Lista las citas pendientes que tiene el paciente
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Appointment> appointments;

        appointments = appointmentService.getMyScheduledAppointments();

        result = new ModelAndView("customerArea/list");
        result.addObject("appointments", appointments);
        result.addObject("requestURI", "customerArea/patient/list.do");

        return result;
    }
}
