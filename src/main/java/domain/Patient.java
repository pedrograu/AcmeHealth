package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes={@Index(columnList="address")})
public class Patient extends Customer {
	
	private CreditCard creditCard;
	private String address;
	private String phone;
	private Date creationMoment;
	private Boolean enableMessage;
	
	
	
	
	@NotNull
	public Boolean getEnableMessage() {
		return enableMessage;
	}

	public void setEnableMessage(Boolean enableMessage) {
		this.enableMessage = enableMessage;
	}

	@Valid
	@NotNull
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	@NotBlank
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@NotBlank
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}
	
	// Relationships ----------------------------------------------------------
	
	private MedicalHistory medicalHistory;
	private Collection<Comment> comments;
	private Specialist specialist;
	private Collection<Offer> offers;
	private Collection<Appointment> appointments;


	@Valid
	@NotNull
	@OneToMany(mappedBy="patient")
	public Collection<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Collection<Appointment> appointments) {
		this.appointments = appointments;
	}

	@Valid
	@OneToOne(cascade=CascadeType.ALL,optional=true)
	public MedicalHistory getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(MedicalHistory medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "patient")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}


	@Valid
	@ManyToOne(optional = true)
	public Specialist getSpecialist() {
		return specialist;
	}

	public void setSpecialist(Specialist specialist) {
		this.specialist = specialist;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "patients")
	public Collection<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Collection<Offer> offers) {
		this.offers = offers;
	}
	
	
	public boolean add(Offer o) {
		return offers.add(o);
	}

	public boolean remove(Offer o) {
		return offers.remove(o);
	}
	
	
	

}
