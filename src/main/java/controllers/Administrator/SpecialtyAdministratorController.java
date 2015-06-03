package controllers.Administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SpecialistService;
import services.SpecialtyService;

import controllers.AbstractController;

import domain.Specialist;
import domain.Specialty;

@Controller
@RequestMapping("/specialty/administrator")
public class SpecialtyAdministratorController extends AbstractController {
	
	@Autowired
	private SpecialtyService specialtyService;
	
	@Autowired
	private SpecialistService specialistService;
	
	
	public SpecialtyAdministratorController() {
		super();
	}
	
	// Listing.....................


		@RequestMapping(value = "/list-all", method = RequestMethod.GET)
		public ModelAndView listAll() {

			ModelAndView result;
			Collection<Specialty> specialtys;

			specialtys = specialtyService.getAll();

			result = new ModelAndView("specialty/list");
			result.addObject("specialtys", specialtys);
			result.addObject("requestURI", "specialty/administrator/list-all.do");

			return result;
		}
		
		@RequestMapping(value = "/detail", method = RequestMethod.GET)
		public ModelAndView detail(@RequestParam int specialtyId) {

			
			ModelAndView result;
			Specialty specialty = specialtyService.findOneToEdit(specialtyId);
			
			result = createEditModelAndView(specialty);

			result.addObject("specialty", specialty);
			result.addObject("details", true);
			result.addObject("createSpecialty", false);
			result.addObject("detailsSpecialty", true);

			return result;
		}
		
		
		// Edition ----------------------------------------------------------------

					@RequestMapping(value = "/create", method = RequestMethod.GET)
					public ModelAndView create() {

						ModelAndView result;
						
						Specialty specialty;

						specialty = specialtyService.create();

						result = createEditModelAndView(specialty);

						
						result.addObject("specialty", specialty);
						result.addObject("createSpecialty", true);
						result.addObject("detailsSpecialty", false);
						
						

						return result;
					}
					
					@RequestMapping(value = "/edit", method = RequestMethod.GET)
					public ModelAndView edit(@RequestParam int specialtyId) {

						ModelAndView result;

						Specialty specialty = specialtyService.findOneToEdit(specialtyId);

						result = createEditModelAndView(specialty);
						result.addObject("createSpecialty", false);
						result.addObject("detailsSpecialty", true);



						return result;
					}
					
					@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
					public ModelAndView save(@Valid Specialty specialty, BindingResult binding) {

						ModelAndView result ;


						if (binding.hasErrors()) {
							result = createEditModelAndView(specialty);
						} else {
							try {
								specialtyService.save(specialty);
								result = new ModelAndView("redirect:list-all.do");
							} catch (Throwable oops) {
								result = createEditModelAndView(specialty,"specialty.commit.error");
							}
						}
						
						return result;

					}
					


					// Ancillary methods ------------------------------------------------------

					protected ModelAndView createEditModelAndView(Specialty specialty) {
						assert specialty != null;
						ModelAndView result;

						result = createEditModelAndView(specialty, null);

						return result;
					}

					protected ModelAndView createEditModelAndView(Specialty specialty,String message) {
						
						
						Assert.notNull(specialty);
						ModelAndView result;
						
				    Collection<Specialist> specialists;
						
				    specialists = specialistService.findSpecialistsForSpecialty(specialty);	

					result = new ModelAndView("specialty/edit");

					result.addObject("specialty", specialty);
					result.addObject("requestURI","specialty/administrator/edit.do?specialtyId=");
					result.addObject("message", message);
					result.addObject("createSpecialty", true);
					result.addObject("detailsSpecialty", false);
					result.addObject("specialists", specialists);

						return result;
					}
		

}
