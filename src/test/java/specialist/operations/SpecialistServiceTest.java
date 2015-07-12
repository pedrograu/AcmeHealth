package specialist.operations;

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
import services.OfferService;
import services.PatientService;
import services.SpecialistService;
import utilities.PopulateDatabase;
import domain.Patient;
import domain.Specialist;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SpecialistServiceTest {

    @Autowired
    private OfferService offerService;
    @Autowired
    private SpecialistService specialistService;
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

    //cambiar su medico de cabecera por otro
    @Test
    public void testChangeSpecialist() {

        authenticate("patient1");

        Patient patient = patientService.findByPrincipal();
        Specialist specialist = specialistService.findOneToEdit(18);

        patientService.save(patient, specialist);

        Assert.isTrue(patient.getSpecialist() == specialist);

    }

    //cambiar su medico de cabecera por otro que no es de "Medicina General"
    @Test(expected = IllegalArgumentException.class)
    public void testChangeSpecialistNotMedicalHistory() {

        authenticate("patient1");

        Patient patient = patientService.findByPrincipal();
        Specialist specialist = specialistService.findOneToEdit(20);

        patientService.save(patient, specialist);

    }

}
