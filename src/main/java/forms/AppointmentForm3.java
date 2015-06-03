package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import domain.Patient;
import domain.Specialist;


public class AppointmentForm3 {

	
	private int id;
	private int version;
	private Date startMoment;
	private Patient patient;
	private Specialist specialist;
	

	@Valid
	@NotNull
	public Specialist getSpecialist() {
		return specialist;
	}
	public void setSpecialist(Specialist specialist) {
		this.specialist = specialist;
	}
	@Valid
	@NotNull
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern ="dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return startMoment;
	}
	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}

	
}

