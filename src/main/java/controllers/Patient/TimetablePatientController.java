package controllers.Patient;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SpecialistService;
import services.TimetableService;
import controllers.AbstractController;
import domain.Specialist;
import domain.Timetable;

@Controller
@RequestMapping("/timetable/patient")
public class TimetablePatientController extends AbstractController {

    // Services.....................

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private SpecialistService specialistService;

    //Controller-------------------------

    public TimetablePatientController() {
        super();
    }

    // Listing.....................

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam int specialistId) {

        ModelAndView result;
        Collection<Timetable> timetables;

        Specialist specialist = specialistService.findOneToEdit(specialistId);

        timetables = timetableService.getTimetablesForSpecialist(specialist);

        result = new ModelAndView("timetable/list");
        result.addObject("timetables", timetables);
        result.addObject("requestURI", "timetable/patient/list.do");

        return result;
    }

}