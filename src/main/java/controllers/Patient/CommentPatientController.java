package controllers.Patient;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.ProfileService;
import controllers.AbstractController;
import domain.Comment;
import domain.Profile;
import forms.CommentForm;

@Controller
@RequestMapping("/comment/patient")
public class CommentPatientController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private CommentService commentService;

    @Autowired
    private ProfileService profileService;

    // Constructors -----------------------------------------------------------

    public CommentPatientController() {
        super();
    }

    // Edit........................

    //Crea un nuevo comentario en el perfil que se le pasa como parametro de entrada
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int profileId) {

        ModelAndView result;

        Profile profile = profileService.findOneToEdit(profileId);
        CommentForm commentForm = new CommentForm();
        commentForm.setProfile(profile);

        result = createEditModelAndView(commentForm);
        result.addObject("details", false);
        result.addObject("commentForm", commentForm);

        return result;
    }

    //Muestra los detalles de un comentario dado su id
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView details(@RequestParam int commentId) {

        ModelAndView result;
        Comment comment = commentService.findOneToEdit(commentId);

        result = new ModelAndView("comment/edit");
        result.addObject("comment", comment);
        result.addObject("details", true);
        result.addObject("requestURI", "comment/patient/details.do?commentId=" + comment.getId());

        return result;
    }

    //Guarda un comentario en la base de datos
    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid CommentForm commentForm, BindingResult binding) {

        ModelAndView result;
        Comment comment;

        if (binding.hasErrors()) {
            result = createEditModelAndView(commentForm);
        } else {
            try {
                comment = commentService.recontructor(commentForm);
                commentService.save(comment);
                result = new ModelAndView("redirect:../../profile/patient/details.do?specialistId="
                        + commentForm.getProfile().getSpecialist().getId());

            } catch (Throwable oops) {
                result = createEditModelAndView(commentForm, "comment.commit.error");
            }
        }

        return result;

    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(CommentForm i) {
        assert i != null;
        ModelAndView result;

        result = createEditModelAndView(i, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(CommentForm commentForm, String message) {

        Assert.notNull(commentForm);
        ModelAndView result;
        Profile profile = commentForm.getProfile();
        Comment comment = commentService.findOneToEdit(commentForm.getId());

        result = new ModelAndView("comment/edit");
        result.addObject("requestURI", "comment/patient/edit.do?commentId=" + commentForm.getId());
        if (commentForm.getId() == 0) {
            result.addObject("details", false);
        } else {
            result.addObject("details", true);
        }
        result.addObject("comment", comment);
        result.addObject("profile", profile);
        result.addObject("message", message);
        result.addObject("commentForm", commentForm);

        return result;
    }

}
