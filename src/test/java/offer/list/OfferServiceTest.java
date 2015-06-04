package offer.list;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import services.OfferService;
import services.PatientService;
import utilities.PopulateDatabase;
import domain.Offer;
import domain.Patient;


@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OfferServiceTest {

	@Autowired
	private OfferService offerService;
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

	@Test
	public void testListOfferNotFinishNotAuthenticate() {

		Collection<Offer> offers = offerService.findNotFinished();
		Date currentMoment = new Date();
		
		for(Offer o : offers){
			Assert.isTrue(o.getFinishMoment().after(currentMoment));
		}
		
		Assert.isTrue(offers.size() == 4);
		
		
	}
	
	@Test
	public void testListAllOfferCreateForAdministrator() {

		authenticate("administrator1");
		Collection<Offer> offers = offerService.findOwn();

		for(Offer o : offers){
			Assert.isTrue(o.getAdministrator().getId() == 8);
		}
		
		Assert.isTrue(offers.size() == 4);
		
		
	}
	
	
	@Test
	public void testListOfferCreateForAdministratorNotFinish() {

		authenticate("administrator1");
		Collection<Offer> offers = offerService.findNotFinishedOwn();
		Date currentMoment = new Date();

		for(Offer o : offers){
			Assert.isTrue(o.getAdministrator().getId() == 8);
			Assert.isTrue(o.getFinishMoment().after(currentMoment));
		}
		
		Assert.isTrue(offers.size() == 3);
		
		
	}
	
	
	@Test
	public void testListAllActiveOffers() {

		authenticate("administrator1");
		Collection<Offer> offers = offerService.findActive();
		Date currentMoment = new Date();

		for(Offer o : offers){
			
			Assert.isTrue(o.getFinishMoment().after(currentMoment));
			Assert.isTrue(o.getStartMoment().before(currentMoment));
		}
		
		Assert.isTrue(offers.size() == 2);
		
		
	}
	
	
	@Test
	public void testListAllOffersOrderByPrice() {

		authenticate("administrator1");
		Collection<Offer> offers = offerService.findOrder();

		List<Offer> offersList = new ArrayList<Offer>();
		
		for(Offer o : offers){
			offersList.add(o);

		}
		

		Assert.isTrue(offersList.get(0).getPrice()<offersList.get(1).getPrice());
		Assert.isTrue(offersList.get(1).getPrice()<offersList.get(2).getPrice());
		Assert.isTrue(offersList.get(2).getPrice()<offersList.get(3).getPrice());
		Assert.isTrue(offersList.get(3).getPrice()<offersList.get(4).getPrice());
		Assert.isTrue(offersList.get(4).getPrice()<offersList.get(5).getPrice());

		
		Assert.isTrue(offers.size() == 6);
		
		
	}
	
	
	
	@Test
	public void testListOfferOwnNotFinishAuthenticateSpecialist() {

		authenticate("specialist1");
		Collection<Offer> offers = offerService.findNotFinishedOwnSpecialist();
		Date currentMoment = new Date();
		
		for(Offer o : offers){
			Assert.isTrue(o.getSpecialist().getId() == 12);
			Assert.isTrue(o.getFinishMoment().after(currentMoment));
		}
		
		Assert.isTrue(offers.size() == 4);
		
	}
	
	@Test
	public void testListOfferNotFinishAuthenticatePatient() {

		authenticate("patient1");
		Collection<Offer> offers = offerService.findNotFinished();
		Date currentMoment = new Date();
		
		for(Offer o : offers){
			Assert.isTrue(o.getFinishMoment().after(currentMoment));
		}
		
		
	}
	
	
	@Test
	public void testListOfferHireAuthenticatePatient() {

		authenticate("patient1");
		Patient patientConnect = patientService.findByPrincipal();
		Collection<Offer> offers = offerService.findOwnPatient();
		
		for(Offer o : offers){
			Assert.isTrue(patientConnect.getOffers().contains(o));
		}
		
		Assert.isTrue(offers.size()==1);
		
	}
	
	
	

}
