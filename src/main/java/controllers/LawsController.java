package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/laws")
public class LawsController extends AbstractController {
	
	// Constructors -----------------------------------------------------------
	
		public LawsController() {
			super();
		}
		@RequestMapping(value = "/list")
		public ModelAndView index(@RequestParam(required=false, defaultValue="") String name) {
			ModelAndView result;
			
		
			
					
			result = new ModelAndView("laws/list");
			result.addObject("name", name);
			

			return result;
		}
		

}
