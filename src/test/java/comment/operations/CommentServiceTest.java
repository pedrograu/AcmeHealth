package comment.operations;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import services.CommentService;
import services.ProfileService;
import utilities.PopulateDatabase;
import domain.Comment;
import domain.Profile;
import forms.CommentForm;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ProfileService profileService;

    @Autowired
    private LoginService loginService;

    public void authenticate(String username) {
        UserDetails userDetails;
        TestingAuthenticationToken authenticationToken;
        SecurityContext context;

        userDetails = loginService.loadUserByUsername(username);
        authenticationToken = new TestingAuthenticationToken(userDetails, null);
        context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);
    }

    public void desauthenticate() {
        UserDetails userDetails;
        TestingAuthenticationToken authenticationToken;
        SecurityContext context;

        userDetails = loginService.loadUserByUsername(null);
        authenticationToken = new TestingAuthenticationToken(userDetails, null);
        context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);
    }

    @Before
    public void setUp() {
        PopulateDatabase.main(null);
    }

    //crear comentario para un especialista con el cual he tenido una cita.
    @Test()
    public void testCreateComment() {
        authenticate("patient1");
        Comment comment;
        Profile profile = profileService.findOneToEdit(13);
        CommentForm commentForm = new CommentForm();
        commentForm.setProfile(profile);
        commentForm.setText("Hola jUnit");
        commentForm.setRating(6);

        comment = commentService.recontructor(commentForm);
        commentService.save(comment);
        Collection<Comment> comments = commentService.getCommentsForSpecialist(profile.getSpecialist());

        boolean res = false;
        for (Comment c : comments) {
            if (c.getText().equals(commentForm.getText())) {
                res = true;
                break;
            }
        }
        Assert.isTrue(res);

    }

    //intentar crear comentario para un especialista con el cual no he tenido ninguna cita.
    @Test(expected = IllegalArgumentException.class)
    public void testCreateCommentNotAppointment() {
        authenticate("patient1");
        Comment comment;
        Profile profile = profileService.findOneToEdit(15);
        CommentForm commentForm = new CommentForm();
        commentForm.setProfile(profile);
        commentForm.setText("Hola jUnit");
        commentForm.setRating(6);

        comment = commentService.recontructor(commentForm);
        commentService.save(comment);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCommentNotAuthenticated() {
        desauthenticate();
        Comment comment;
        Profile profile = profileService.findOneToEdit(13);
        CommentForm commentForm = new CommentForm();
        commentForm.setProfile(profile);
        commentForm.setText("Hola jUnit");
        commentForm.setRating(6);

        comment = commentService.recontructor(commentForm);
        commentService.save(comment);

    }
}
