package controllers.Administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Comment;
import domain.Specialist;
import services.CommentService;
import services.SpecialistService;

@Controller
@RequestMapping("/profile/administrator")
public class ProfileAdministratorController extends AbstractController {

	//Services............................................
	
    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private CommentService commentService;

    //Constructor......................................
    
    public ProfileAdministratorController() {
        super();
    }

    // Details.....................
    
    //Muestra el perfil de un especialista dado el id del especialista
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView details(@RequestParam int specialistId) {

        ModelAndView result;

        Specialist specialist = specialistService.findOneToEdit(specialistId);
        Collection<Comment> comments = commentService.getCommentsForSpecialist(specialist);

        result = new ModelAndView("profile/edit");
        result.addObject("specialist", specialist);
        result.addObject("comments", comments);
        result.addObject("requestURI", "profile/details.do");
        result.addObject("detailsProfile", true);

        return result;
    }

}
