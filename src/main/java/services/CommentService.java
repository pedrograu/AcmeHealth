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

	public Collection<Comment> getCommentsForSpecialist(Specialist specialist) {
		Collection<Comment> comments = commentRepository
				.getCommentsForSpecialist(specialist.getId());
		return comments;
	}

	public Comment findOneToEdit(int commentId) {
		Comment comment = commentRepository.findOne(commentId);
		return comment;
	}

	public CommentForm createForm(Comment comment) {
		CommentForm commentForm = new CommentForm();
		commentForm.setId(comment.getId());
		commentForm.setVersion(comment.getVersion());

		commentForm.setText(comment.getText());
		commentForm.setRating(comment.getRating());
		commentForm.setProfile(comment.getProfile());

		return null;
	}

	public Comment recontructor(CommentForm commentForm) {
		Comment result = create();

		result.setId(commentForm.getId());
		result.setVersion(commentForm.getVersion());
		result.setProfile(commentForm.getProfile());
		result.setRating(commentForm.getRating());
		result.setText(commentForm.getText());

		return result;
	}

	public Comment create() {

		Comment result = new Comment();
		
		Patient patient = patientService.findByPrincipal();
		Date creationMoment = new Date();
		
		result.setPatient(patient);
		result.setCreationMoment(creationMoment);
		
		

		return result;
	}

	public void save(Comment comment) {
		
		

		Patient patient = patientService.findByPrincipal();
		Specialist specialist = comment.getProfile().getSpecialist();
		Collection<Appointment> appointments = appointmentService.getAppointmentforOneSpecialistAndOnePatient(specialist, patient);
		Assert.isTrue(!appointments.isEmpty());
		commentRepository.save(comment);
		profileService.updateRating(comment.getProfile());

	}

	public void delete2(Collection<Comment> comments) {
		
		commentRepository.delete(comments);
		
		
	}

}
