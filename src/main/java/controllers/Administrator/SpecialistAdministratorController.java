package controllers.Administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SpecialistService;

import controllers.AbstractController;
import domain.Specialist;

@Controller
@RequestMapping("/specialist/administrator")
public class SpecialistAdministratorController extends AbstractController {
	
	// Services -----------------------------------------------------------

		@Autowired
		private SpecialistService specialistService;
		
		// Constructors -----------------------------------------------------------
		
		public SpecialistAdministratorController() {
			super();
		}
			
		// List ------------------------------------------------------------------		
		
		
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {

			ModelAndView result;
			Collection<Specialist> specialists;

			specialists = specialistService.findAllSpecialists();

			result = new ModelAndView("specialist/list");
			result.addObject("specialists", specialists);
			result.addObject("requestURI", "specialist/administrator/list.do");

			return result;
		}
		

}
