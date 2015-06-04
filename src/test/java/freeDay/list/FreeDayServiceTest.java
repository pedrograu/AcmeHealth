package freeDay.list;

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
import services.FreeDayService;
import services.PatientService;
import services.SpecialistService;
import utilities.PopulateDatabase;
import domain.FreeDay;
import domain.Specialist;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FreeDayServiceTest {

    @Autowired
    private FreeDayService freeDayService;
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

    @Test
    public void testListMyFreeDaysAuthenticateSpecialist() {

        authenticate("specialist1");
        Specialist specialist1 = specialistService.findByPrincipal();

        Collection<FreeDay> freeDays = freeDayService.getFreeDaysForSpecialist(specialist1);

        for (FreeDay f : freeDays) {
            Assert.isTrue(f.getSpecialist() == specialist1);
        }

        Assert.isTrue(freeDays.size() == 1);
    }

}
