package controllers.Specialist;

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
@RequestMapping("/customerArea/specialist")
public class CustomerAreaSpecialistController extends AbstractController {

    @Autowired
    private AppointmentService appointmentService;

    public CustomerAreaSpecialistController() {
        super();
    }

    // Listing ----------------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        Collection<Appointment> appointments;

        appointments = appointmentService.getAppointmentsNotFinish();

        result = new ModelAndView("customerArea/list");
        result.addObject("appointments", appointments);
        result.addObject("requestURI", "customerArea/specialist/list.do");

        return result;
    }
}
