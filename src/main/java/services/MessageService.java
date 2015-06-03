package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Comment;
import domain.Customer;
import domain.Message;
import domain.Offer;
import domain.Patient;
import domain.Specialist;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {
	// Managed repository ---------------------------------------

	@Autowired
	private MessageRepository messageRepository;

	// Supporting services --------------------------------------
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PatientService patientService;

	// Constructors ---------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods --------------------------------------

	public Collection<Message> getMessageInbox() {
		Collection<Message> messages;
		Customer customerConnect = customerService.findByPrincipal();
		messages = messageRepository.getMessageInbox(customerConnect.getId());
		return messages;
	}

	public Collection<Message> getMessageOutbox() {
		Collection<Message> messages;
		Customer customerConnect = customerService.findByPrincipal();
		messages = customerConnect.getMessageSender();
		return messages;
	}

	public Message create() {
		Customer customer;
		customer = customerService.findByPrincipal();

		Message message;
		message = new Message();
		Date currentMoment;
		currentMoment = new Date();

		message.setSender(customer);
		message.setCreationMoment(currentMoment);

		Assert.notNull(message);

		return message;
	}

	public Message save(Message customerMessage) {
		Assert.notNull(customerMessage);
		Customer customerConnect = customerService.findByPrincipal();

		checkPrincipal(customerMessage);
		Date currentMoment;
		currentMoment = new Date(System.currentTimeMillis() - 1000);
		customerMessage.setCreationMoment(currentMoment);
		if (Patient.class == customerConnect.getClass()) {
			Assert.isTrue(customerMessage.getRecipient().getClass() == Specialist.class);
			Patient patientConnect = patientService.findByPrincipal();
			Assert.isTrue(patientConnect.getEnableMessage());
		}

		messageRepository.save(customerMessage);
		
		return customerMessage;
	}

	public void checkPrincipal(Message customerMessage) {

		Assert.notNull(customerMessage);
		Customer customer;

		customer = customerService.findByPrincipal();

		Assert.isTrue(customer.getId() == customerMessage.getSender().getId()
				|| customer.getId() == customerMessage.getRecipient().getId());

	}

	public Message findOneToEdit(int messageId) {

		Assert.isTrue(messageId != 0);
		Message res;
		res = messageRepository.findOne(messageId);
		checkPrincipal(res);
		return res;
	}

	public Message recontructor(MessageForm messageForm) {
		Message result = create();
		Customer customerConnect = customerService.findByPrincipal();
		Date creationMoment = new Date();

		result.setRecipient(messageForm.getRecipient());
		result.setSender(customerConnect);
		result.setCreationMoment(creationMoment);
		result.setSubject(messageForm.getSubject());
		result.setTextBody(messageForm.getTextBody());
		result.setId(messageForm.getId());
		result.setVersion(messageForm.getVersion());

		return result;
	}

}
