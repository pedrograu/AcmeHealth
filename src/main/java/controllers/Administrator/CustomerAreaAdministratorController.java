package controllers.Administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CustomerService;
import services.PatientService;
import services.ProfileService;
import services.SpecialistService;
import controllers.AbstractController;

@Controller
@RequestMapping("/customerArea/administrator")
public class CustomerAreaAdministratorController extends AbstractController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ProfileService profileService;

    public CustomerAreaAdministratorController() {
        super();
    }

    // Listing ----------------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        result = new ModelAndView("customerArea/list");
        result.addObject("requestURI", "customerArea/administrator/list.do");

        return result;
    }
}
