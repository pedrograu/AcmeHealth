package specialist.list;

import java.util.Collection;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import services.SpecialistService;
import services.SpecialtyService;
import utilities.PopulateDatabase;
import domain.Specialist;
import domain.Specialty;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SpecialistServiceTest {

	@Autowired
	private SpecialistService specialistService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private SpecialtyService specialtyService;
	
	
	public void authenticate (String username){
		UserDetails userDetails;
		TestingAuthenticationToken authenticationToken;
		SecurityContext context;
		
		userDetails = loginService.loadUserByUsername(username);
		authenticationToken= new TestingAuthenticationToken(userDetails, null);
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
	public void testListNotAuthenticate() {
		Collection<Specialist> specialists;
		specialists = specialistService.findAllSpecialists();
		
		Assert.isTrue(specialists.size() == 3);
	}
	
	@Test
	public void testListAuthenticateAdministrator() {
		authenticate("administrator1");
		Collection<Specialist> specialists;
		specialists = specialistService.findAllSpecialists();
		
		Assert.isTrue(specialists.size() == 3);
	}
	
	@Test
	public void testListNotAuthenticateForSpecialityNotAuthenticate() {
		Collection<Specialist> specialists;
		Specialty specialty = specialtyService.findOneToEdit(10);
		specialists = specialistService.findSpecialistsForSpecialty(specialty);
		
		Assert.isTrue(specialists.size() == 2);
	}
	
	@Test
	public void testListNotAuthenticateForSpecialityAuthenticateAdministrator() {
		authenticate("administrator1");
		Collection<Specialist> specialists;
		Specialty specialty = specialtyService.findOneToEdit(10);
		specialists = specialistService.findSpecialistsForSpecialty(specialty);
		
		Assert.isTrue(specialists.size() == 2);
	}
	
	@Test
	public void testListPatient() {
		authenticate("patient1");
		Collection<Specialist> specialists;
		specialists = specialistService.findAllSpecialists();
		
		Assert.isTrue(specialists.size() == 3);
	}
	

	
	
	
}
