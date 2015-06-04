package dashboard.list;

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
import services.CommentService;
import services.CustomerService;
import services.PatientService;
import services.ProfileService;
import services.SpecialistService;
import utilities.PopulateDatabase;
import domain.Patient;
import domain.Profile;
import domain.Specialist;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DashboardTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ProfileService profileService;

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
    public void testNumberOfPatients() {

        Integer number = patientService.getNumberOfPatient();
        Assert.isTrue(number.equals(2));

    }

    @Test
    public void testSpecialistWithMoreAppointment() {
        Collection<Specialist> specialists = specialistService.getSpecialistWithMoreAppointment();

        Boolean res = false;

        for (Specialist s : specialists) {
            if (s.getName().equals("Francisco"))
                ;
            res = true;

        }
        Assert.isTrue(res);

    }

    @Test
    public void testPatientWithMoreAppointment() {
        Collection<Patient> patients = patientService.getPatientWithMoreAppointment();

        Boolean res = false;

        for (Patient p : patients) {
            if (p.getName().equals("Maite"))
                ;
            res = true;

        }
        Assert.isTrue(res);

    }

    @Test
    public void testPatientWithMoreSpending() {
        Collection<Patient> patients = patientService.getPatientWithMoreSpending();

        Boolean res = false;

        for (Patient p : patients) {
            if (p.getName().equals("Antonio"))
                ;
            res = true;

        }
        Assert.isTrue(res);

    }

    @Test
    public void testPatientLastYear() {
        Collection<Patient> patients = patientService.getPatientLastYear();

        Boolean res = false;

        for (Patient p : patients) {
            if (p.getName().equals("Antonio"))
                ;
            res = true;

        }
        Assert.isTrue(res);

    }

    @Test
    public void testBestSpecialist() {
        Collection<Profile> profiles = profileService.getBestSpecialist();

        Boolean res = false;

        for (Profile p : profiles) {
            if (p.getSpecialist().getName().equals("Antonio"))
                ;
            res = true;

        }
        Assert.isTrue(res);

    }

}
