package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Appointment;
import domain.Comment;
import domain.Patient;
import domain.Specialist;
import forms.CommentForm;

@Service
@Transactional
public class CommentService {

    // Managed repository ---------------------------------------
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ProfileService profileService;

    // Supporting services --------------------------------------

    // Constructors ---------------------------------------------

    public CommentService() {
        super();
    }

    // Simple CRUD methods --------------------------------------

    //Devuelve una collection de commnents para un especialista dado.
    public Collection<Comment> getCommentsForSpecialist(Specialist specialist) {
        Collection<Comment> comments = commentRepository.getCommentsForSpecialist(specialist.getId());
        return comments;
    }

    //Devuelve un comment dado su id
    public Comment findOneToEdit(int commentId) {
        Comment comment = commentRepository.findOne(commentId);
        return comment;
    }

    //Crea un objeto formulario de tipo comment para un commnent dado.
    public CommentForm createForm(Comment comment) {
        CommentForm commentForm = new CommentForm();
        commentForm.setId(comment.getId());
        commentForm.setVersion(comment.getVersion());

        commentForm.setText(comment.getText());
        commentForm.setRating(comment.getRating());
        commentForm.setProfile(comment.getProfile());

        return null;
    }

    //Reconstruye un comment a partir de un objeto formulario de tipo comment
    public Comment recontructor(CommentForm commentForm) {
        Comment result = create();

        result.setId(commentForm.getId());
        result.setVersion(commentForm.getVersion());
        result.setProfile(commentForm.getProfile());
        result.setRating(commentForm.getRating());
        result.setText(commentForm.getText());

        return result;
    }

    //Crea un comment para el paciente logueado en el sistema
    public Comment create() {

        Comment result = new Comment();

        Patient patient = patientService.findByPrincipal();
        Date creationMoment = new Date();

        result.setPatient(patient);
        result.setCreationMoment(creationMoment);

        return result;
    }

    //Guarda un comment en la base da datos
    public void save(Comment comment) {

        Patient patient = patientService.findByPrincipal();
        Specialist specialist = comment.getProfile().getSpecialist();
        Collection<Appointment> appointments = appointmentService.getAppointmentforOneSpecialistAndOnePatient(
                specialist, patient);
        //Comprueba que el paciente ha tenido al menos una cita con el especialista al que va a ponerle un comentario
        Assert.isTrue(!appointments.isEmpty());
        commentRepository.save(comment);
        
        //Actualiza la valoración del especialista
        profileService.updateRating(comment.getProfile());

    }

    //Elimina de la base de datos una collections de comments
    public void delete2(Collection<Comment> comments) {

        commentRepository.delete(comments);

    }

}
