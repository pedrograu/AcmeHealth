package controllers.Administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;

import controllers.AbstractController;
import domain.Comment;

@Controller
@RequestMapping("/comment/administrator")
public class CommentAdministratorController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private CommentService commentService;

    // Constructors -----------------------------------------------------------

    public CommentAdministratorController() {
        super();
    }

    // Edit........................

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView details(@RequestParam int commentId) {

        ModelAndView result;
        Comment comment = commentService.findOneToEdit(commentId);

        result = new ModelAndView("comment/edit");
        result.addObject("comment", comment);
        result.addObject("details", true);
        result.addObject("requestURI", "comment/administrator/details.do?commentId=" + comment.getId());

        return result;
    }

}
