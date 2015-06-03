package forms;

import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Customer;

public class MessageForm {

	private int id;
	private int version;

	private String subject;
	private String textBody;

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

	private Customer recipient;

	@NotNull
	@Valid
	public Customer getRecipient() {
		return recipient;
	}
	
	public void setRecipient(Customer recipient) {
		this.recipient = recipient;
	}

}
