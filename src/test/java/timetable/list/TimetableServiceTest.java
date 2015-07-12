package timetable.list;

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
import services.TimetableService;
import utilities.PopulateDatabase;
import domain.Specialist;
import domain.Timetable;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TimetableServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TimetableService timetableService;

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
    public void testListTimetablesForSpecialistAuthenticatePatient() {
        authenticate("administrator1");
        Collection<Timetable> timetables;
        Specialist specialist = specialistService.findOneToEdit(16);
        timetables = timetableService.getTimetablesForSpecialist(specialist);
        Assert.isTrue(timetables.size() == 3);

        for (Timetable t : timetables) {
            Assert.isTrue(t.getSpecialist() == specialist);
        }
    }

    @Test
    public void testListMyTimetablesForSpecialistAuthenticateSpecialist() {

        authenticate("specialist1");

        Collection<Timetable> timetables;
        Specialist specialist = specialistService.findOneToEdit(16);
        timetables = timetableService.getTimetablesForSpecialist(specialist);
        Assert.isTrue(timetables.size() == 3);

        for (Timetable t : timetables) {
            Assert.isTrue(t.getSpecialist() == specialist);
        }
    }

}
