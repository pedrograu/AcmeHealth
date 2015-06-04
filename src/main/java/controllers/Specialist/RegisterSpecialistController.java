package controllers.Specialist;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SpecialistService;
import services.SpecialtyService;
import controllers.AbstractController;
import domain.Specialist;
import domain.Specialty;
import forms.SpecialistForm;

@Controller
@RequestMapping("/register/specialist")
public class RegisterSpecialistController extends AbstractController {

    // Services ---------------------------------------------------------------

    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private SpecialtyService specialtyService;

    // Constructors
    // ---------------------------------------------------------------

    public RegisterSpecialistController() {
        super();
    }

    // Registration -----------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView result;
        SpecialistForm specialistForm = new SpecialistForm();

        result = createEditModelAndView(specialistForm);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid SpecialistForm specialistForm, BindingResult binding) {

        ModelAndView result;
        Specialist specialist;

        if (binding.hasErrors()) {
            result = createEditModelAndView(specialistForm);

        } else {

            try {
                specialist = specialistService.reconstruct(specialistForm);

                specialistService.save(specialist);
                result = new ModelAndView("redirect:../../security/login.do");

            } catch (DataIntegrityViolationException oops) {
                result = createEditModelAndView(specialistForm, "register.duplicate.error");

            } catch (Throwable oops) {
                oops.getMessage();

                if (!(specialistForm.getSecondPassword().equals(specialistForm.getPassword()))) {
                    result = createEditModelAndView(specialistForm, "register.wrongSecondPassword.error");
                } else {

                    if (specialistForm.getAvailable() == false) {
                        result = createEditModelAndView(specialistForm, "register.available.error");

                    } else {
                        result = createEditModelAndView(specialistForm, "register.commit.error");
                    }
                }

            }
        }
        return result;
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(SpecialistForm specialistForm) {

        ModelAndView result;

        result = createEditModelAndView(specialistForm, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(SpecialistForm specialistForm, String message) {

        ModelAndView result;

        Specialist specialist = specialistService.create();
        Collection<Specialty> specialtys;

        specialtys = specialtyService.getAll();

        result = new ModelAndView("register/edit");
        result.addObject("specialistForm", specialistForm);
        result.addObject("specialist", specialist);
        result.addObject("actor", "specialistForm");
        result.addObject("requestURI", "register/specialist/edit.do");
        result.addObject("registerPatient", false);
        result.addObject("registerSpecialist", true);

        result.addObject("specialtys", specialtys);

        result.addObject("message", message);

        return result;
    }

}
