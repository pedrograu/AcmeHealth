package medicalHistory.operations;

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
import services.MedicalHistoryService;
import services.PatientService;
import utilities.PopulateDatabase;
import domain.MedicalHistory;
import domain.Patient;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MedicalHistoryServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private MedicalHistoryService medicalHistoryService;

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
    public void testEditMedicalHistory() {
        authenticate("specialist1");
        MedicalHistory medicalHistory = medicalHistoryService.findOneToEdit(19);
        Patient owner = patientService.findOneToEdit(18);
        medicalHistory.setAllergy("Graminias");
        medicalHistoryService.save(medicalHistory);
        Assert.isTrue(owner.getMedicalHistory().getAllergy().equals(medicalHistory.getAllergy()));

    }

}
