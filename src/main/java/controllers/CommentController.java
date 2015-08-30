package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import domain.Comment;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private CommentService commentService;

    // Constructors -----------------------------------------------------------

    public CommentController() {
        super();
    }

    // Edit........................

    //Muestra los detalles de un comentario dado su id
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView details(@RequestParam int commentId) {

        ModelAndView result;
        Comment comment = commentService.findOneToEdit(commentId);

        result = new ModelAndView("comment/edit");
        result.addObject("comment", comment);
        result.addObject("details", true);
        result.addObject("requestURI", "comment/details.do?commentId=" + comment.getId());

        return result;
    }

}