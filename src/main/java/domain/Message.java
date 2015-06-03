package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message  extends DomainEntity{

	private String subject;

	private String textBody;

	private Date creationMoment;

	@NotBlank
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getTextBody() {
		return textBody;
	}

	public void setTextBody(String textBody) {
		this.textBody = textBody;
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
	private Customer recipient;

	private Customer sender;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Customer getRecipient() {
		return recipient;
	}

	public void setRecipient(Customer recipient) {
		this.recipient = recipient;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Customer getSender() {
		return sender;
	}

	public void setSender(Customer sender) {
		this.sender = sender;
	}

}
