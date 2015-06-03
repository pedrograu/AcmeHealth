package message.operations;

import java.util.Collection;

import javax.transaction.Transactional;

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
import org.springframework.util.Assert;

import security.LoginService;
import services.CustomerService;
import services.MessageService;
import services.PatientService;
import utilities.PopulateDatabase;
import domain.Customer;
import domain.Message;
import domain.Patient;
import forms.MessageForm;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private LoginService loginService;
	@Autowired
	private PatientService patientService;
	

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

	@Test()
	public void testCreateMessage() {
		authenticate("patient1");
		Customer customerRecipient = customerService.findOneToEdit(12);
		Message message;

		MessageForm messageForm = new MessageForm();
		messageForm.setTextBody("wolaaa");
		messageForm.setSubject("jajaja");
		messageForm.setRecipient(customerRecipient);

		message = messageService.recontructor(messageForm);
		messageService.save(message);
		Collection<Message> messages = messageService.getMessageOutbox();

		boolean res = false;
		for (Message m : messages) {
			if (m.getTextBody().equals(messageForm.getTextBody())) {
				res = true;
				break;
			}
		}
		Assert.isTrue(res);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateMessageFailNotAuthenticated() {
		desauthenticate();
		Customer customerRecipient = customerService.findOneToEdit(12);
		Message message;

		MessageForm messageForm = new MessageForm();
		messageForm.setTextBody("wolaaa");
		messageForm.setSubject("jajaja");
		messageForm.setRecipient(customerRecipient);

		message = messageService.recontructor(messageForm);
		messageService.save(message);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateFailPatientWithOtherPatient() {
		authenticate("patient1");
		Customer customerRecipient = customerService.findOneToEdit(20);
		Message message;

		MessageForm messageForm = new MessageForm();
		messageForm.setTextBody("wolaaa");
		messageForm.setSubject("jajaja");
		messageForm.setRecipient(customerRecipient);

		message = messageService.recontructor(messageForm);
		messageService.save(message);

	}
	
	@Test
	public void testEnableSendMessage(){
		authenticate("administrator1");
		Patient patient = patientService.findOneToEdit(20);

		patient.setEnableMessage(true);
		
		Assert.isTrue(patient.getEnableMessage()==true);
	
	}
	
	@Test
	public void testDisableSendMessage(){
		authenticate("administrator1");
		Patient patient = patientService.findOneToEdit(20);

		patient.setEnableMessage(false);
		
		Assert.isTrue(patient.getEnableMessage()==false);
	
	}

}
