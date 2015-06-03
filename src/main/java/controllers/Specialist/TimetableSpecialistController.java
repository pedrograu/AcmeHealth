package controllers.Specialist;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.SpecialistService;
import services.TimetableService;
import domain.Specialist;
import domain.Timetable;
import forms.TimetableForm;

@Controller
@RequestMapping("/timetable/specialist")
public class TimetableSpecialistController extends AbstractController{

	@Autowired
	private SpecialistService specialistService;
	@Autowired
	private TimetableService timetableService;

	public TimetableSpecialistController() {
		super();
	}
	
	// List -------------------------------------------------------------------
	
	@RequestMapping(value = "/list-own", method = RequestMethod.GET)
	public ModelAndView listOwn() {

		ModelAndView result;
		Collection<Timetable> timetables;
		Specialist specialistConnect = specialistService.findByPrincipal();

		timetables = timetableService.getTimetablesForSpecialist(specialistConnect);

		result = new ModelAndView("timetable/list");
		result.addObject("timetables", timetables);
		result.addObject("requestURI", "timetable/specialist/list-own.do");

		return result;
	}
	
	
	
	// Edition -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		TimetableForm timetableForm = new TimetableForm();

		result = createEditModelAndView(timetableForm);
		result.addObject("timetableForm", timetableForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid TimetableForm timetableForm,
			BindingResult binding) {

		ModelAndView result;
		Timetable timetable;

		if (binding.hasErrors()) {
			result = createEditModelAndView(timetableForm);
		} else {
			try {
				timetable = timetableService.recontructor(timetableForm);
				timetableService.save(timetable);
				result = new ModelAndView("redirect:list-own.do");

			} catch (Throwable oops) {
				result = createEditModelAndView(timetableForm,
						"timetable.commit.error");
			}
		}

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(TimetableForm t) {
		assert t != null;
		ModelAndView result;

		result = createEditModelAndView(t, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(TimetableForm timetableForm,
			String message) {

		Assert.notNull(timetableForm);
		ModelAndView result;
		Collection<Integer> days = new HashSet<Integer>();
		days.add(1);
		days.add(2);
		days.add(3);
		days.add(4);
		days.add(5);
		days.add(6);
		days.add(7);
		
		

		result = new ModelAndView("timetable/edit");
		result.addObject("requestURI", "timetable/specialist/edit.do?timetableId="
				+ timetableForm.getId());
	
		result.addObject("message", message);
		result.addObject("timetableForm", timetableForm);
		result.addObject("days", days);

		return result;
	}

}
