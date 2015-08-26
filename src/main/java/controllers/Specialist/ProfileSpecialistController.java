package controllers.Specialist;

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

import controllers.AbstractController;
import domain.Comment;
import domain.Profile;
import domain.Specialist;
import services.CommentService;
import services.ProfileService;
import services.SpecialistService;

@Controller
@RequestMapping("/profile/specialist")
public class ProfileSpecialistController extends AbstractController {

	//Services.................................................
	
    @Autowired
    private ProfileService profileService;

    @Autowired
    private SpecialistService specialistService;
    
    @Autowired
    private CommentService commentService;
    
    //Constructors.............................................
    public ProfileSpecialistController() {
        super();
    }

    //Muestra el perfil del especialista logueado
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail() {

        ModelAndView result;
        Specialist specialist = specialistService.findByPrincipal();
        Profile profile = specialist.getProfile();
        
        Collection<Comment> comments = commentService.getCommentsForSpecialist(specialist);

        result = new ModelAndView("profile/edit");

        result.addObject("profile", profile);
        result.addObject("specialist", specialist);
        result.addObject("comments", comments);
        result.addObject("detailsProfile", true);

        return result;
    }

    //Edita el perfil de un especialista
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int profileId) {

        ModelAndView result;

        Profile profile = profileService.findOneToEdit(profileId);

        result = createEditModelAndView(profile);
        result.addObject("detailsProfile", false);

        return result;
    }

    //Guarda en la bd el perfil de un especialista
    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Profile profile, BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors()) {
            result = createEditModelAndView(profile);
        } else {
            try {
                profileService.save2(profile);
                result = new ModelAndView("redirect:detail.do");
            } catch (Throwable oops) {
                result = createEditModelAndView(profile, "profile.commit.error");
            }
        }

        return result;

    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(Profile profile) {
        assert profile != null;
        ModelAndView result;

        result = createEditModelAndView(profile, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(Profile profile, String message) {

        Assert.notNull(profile);
        ModelAndView result;

        result = new ModelAndView("profile/edit");

        result.addObject("profile", profile);
        result.addObject("requestURI", "profile/specialist/edit.do");
        result.addObject("message", message);
        result.addObject("detailsProfile", false);

        return result;
    }

}