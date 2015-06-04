package patient.list;

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
import services.OfferService;
import services.PatientService;
import services.SpecialistService;
import utilities.PopulateDatabase;
import domain.Patient;
import domain.Specialist;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PatientServiceTest {

    @Autowired
    private OfferService offerService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private PatientService patientService;
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

    //listar los pacientes que ha recibido en el dia de hoy
    @Test
    public void testPatientsAttendToday() {
        authenticate("specialist1");

        Collection<Patient> patients = patientService.findToday();

        Assert.isTrue(patients.size() == 0);

    }

    //listar todos sus pacientes actuales
    @Test
    public void testPatientsActual() {
        authenticate("specialist1");
        Specialist specialist = specialistService.findByPrincipal();
        Collection<Patient> patients = patientService.findOwn();

        Assert.isTrue(patients.size() == 2);

        for (Patient patient : patients) {
            Assert.isTrue(patient.getSpecialist().equals(specialist));
        }

    }

}
