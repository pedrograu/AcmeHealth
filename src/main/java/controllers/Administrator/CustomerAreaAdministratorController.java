package controllers.Administrator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

@Controller
@RequestMapping("/customerArea/administrator")
public class CustomerAreaAdministratorController extends AbstractController {

	//Constructors............................................................
	
    public CustomerAreaAdministratorController() {
        super();
    }

    // Listing ----------------------------------------------------------------

    //Muestra el área de cliente de un administrador
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;
        result = new ModelAndView("customerArea/list");
        result.addObject("requestURI", "customerArea/administrator/list.do");

        return result;
    }
}
