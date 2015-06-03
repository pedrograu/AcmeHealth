package profile;


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
import services.ProfileService;
import services.SpecialistService;
import utilities.PopulateDatabase;
import domain.Comment;
import domain.Profile;
import domain.Specialist;


@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProfileServiceTest {

	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SpecialistService specialistService;

	
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
	public void testViewProfileNotAuthenticate() {
		Profile profile = profileService.findOneToEdit(13);
		Specialist specialist = profile.getSpecialist();
		Double rating  = profile.getRating();
		Collection<Comment> comments = profile.getComments();
		
		Assert.isTrue(specialist.getId() == 12);
		
		Integer acum = 0;
		for(Comment c : comments){
			Assert.isTrue(c.getProfile().getSpecialist().getId() == 12);
			acum += c.getRating();
			
		}
		Assert.isTrue(rating == acum/comments.size());
	
		
	}

	
	@Test
	public void testViewProfileAuthenticatePatient() {
		
		authenticate("patient1");
		
		Profile profile = profileService.findOneToEdit(13);
		Specialist specialist = profile.getSpecialist();
		Double rating  = profile.getRating();
		Collection<Comment> comments = profile.getComments();
		
		Assert.isTrue(specialist.getId() == 12);
		
		Integer acum = 0;
		for(Comment c : comments){
			Assert.isTrue(c.getProfile().getSpecialist().getId() == 12);
			acum += c.getRating();
			
		}
		Assert.isTrue(rating == acum/comments.size());
	
		
	}
	
	
	@Test
	public void testEditProfileAuthenticateSpecialist() {
		
		authenticate("specialist1");
		
		Specialist specialistConnect = specialistService.findByPrincipal();
		Profile profile = profileService.findOneToEdit(13);
		Specialist specialist = profile.getSpecialist();
		Assert.isTrue(specialistConnect == specialist);
		
		profile.setText("modificando el texto");
		profileService.save2(profile);
		
		Assert.isTrue(profile.getText() == "modificando el texto");
		
		
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testEditOtherProfileAuthenticateSpecialist() {
		
		authenticate("specialist1");

		Profile profile = profileService.findOneToEdit(17);
		profile.setText("modificando el texto");

		profileService.save2(profile);
		
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEditProfileAuthenticatePatient() {
		
		authenticate("patient1");

		Profile profile = profileService.findOneToEdit(17);
		profile.setText("modificando el texto2");

		profileService.save2(profile);
		
		
	}
	

}
