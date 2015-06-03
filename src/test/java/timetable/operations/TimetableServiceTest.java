package timetable.operations;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

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
import services.TimetableService;
import utilities.PopulateDatabase;
import domain.Comment;
import domain.Profile;
import domain.Specialist;
import domain.Timetable;
import forms.CommentForm;
import forms.TimetableForm;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TimetableServiceTest {

	@Autowired
	private TimetableService timetableService;

	@Autowired
	private SpecialistService specialistService;

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

	@Test()
	public void testCreateTimetable() {
		authenticate("specialist1");

		Calendar endCal = new GregorianCalendar();
		endCal.set(Calendar.MONTH, 8);
		Date endShift = endCal.getTime();

		Timetable timetable;

		TimetableForm timetableForm = new TimetableForm();
		timetableForm.setDay(5);
		timetableForm.setStartShift(new Date());
		timetableForm.setEndShift(endShift);

		timetable = timetableService.recontructor(timetableForm);
		timetableService.save(timetable);
		Specialist specialistConnect = specialistService.findByPrincipal();
		Collection<Timetable> timetables = timetableService
				.getTimetablesForSpecialist(specialistConnect);

		boolean res = false;
		for (Timetable t : timetables) {
			if (t.getEndShift().equals(endShift)) {
				res = true;
				break;
			}
		}
		Assert.isTrue(res);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTimetableNotAuthenticated() {
		desauthenticate();

		Calendar endCal = new GregorianCalendar();
		endCal.set(Calendar.MONTH, 8);
		Date endShift = endCal.getTime();

		Timetable timetable;

		TimetableForm timetableForm = new TimetableForm();
		timetableForm.setDay(5);
		timetableForm.setStartShift(new Date());
		timetableForm.setEndShift(endShift);

		timetable = timetableService.recontructor(timetableForm);
		timetableService.save(timetable);
	}
}
