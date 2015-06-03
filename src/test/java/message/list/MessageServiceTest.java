package message.list;

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
import utilities.PopulateDatabase;
import domain.Customer;
import domain.FreeDay;
import domain.Message;


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
	
	@Test
	public void testListInbox() {

		authenticate("patient1");
		Customer customerConnect = customerService.findByPrincipal();
		
		Collection<Message> messages;
		messages = messageService.getMessageInbox();

		
		for(Message m : messages){
			Assert.isTrue(m.getRecipient().equals(customerConnect));
		}
		Assert.isTrue(messages.size()==1);

		
	}
	
	@Test
	public void testListOutbox() {

		authenticate("specialist1");
		Customer customerConnect = customerService.findByPrincipal();
		
		Collection<Message> messages;
		messages = messageService.getMessageOutbox();

		
		for(Message m : messages){
			Assert.isTrue(m.getSender().equals(customerConnect));
		}
		Assert.isTrue(messages.size()==1);

		
	}
	


}
