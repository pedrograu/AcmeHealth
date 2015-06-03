package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SpecialistService;
import domain.Specialist;



@Controller
@RequestMapping("/specialist")
public class SpecialistController extends AbstractController {

	// Services -----------------------------------------------------------

	@Autowired
	private SpecialistService specialistService;
	
	// Constructors -----------------------------------------------------------
	
	public SpecialistController() {
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
		result.addObject("requestURI", "specialist/list.do");

		return result;
	}

}
