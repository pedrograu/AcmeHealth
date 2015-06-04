package freeDay.operations;

import java.util.Collection;
import java.util.Date;

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
import services.FreeDayService;
import services.SpecialistService;
import utilities.PopulateDatabase;
import domain.FreeDay;
import domain.Specialist;
import forms.FreeDayForm;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FreeDayServiceTest {

    @Autowired
    private FreeDayService freeDayService;
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

    @Test
    public void testCreateFreeDayAuthenticateSpecialist() {

        authenticate("specialist1");

        Date currentMoment = new Date(System.currentTimeMillis() + 10000);
        Date currentMoment2 = new Date(System.currentTimeMillis() + 1000000000);

        FreeDayForm freeDayForm = new FreeDayForm();
        freeDayForm.setDescription("freeDay Junit");
        freeDayForm.setStartMoment(currentMoment);
        freeDayForm.setFinishMoment(currentMoment2);

        FreeDay freeDay = freeDayService.recontructor(freeDayForm);

        freeDayService.save(freeDay);

        Specialist s = specialistService.findOneToEdit(12);
        Collection<FreeDay> freeDays = freeDayService.getFreeDaysForSpecialist(s);

        boolean res = false;
        for (FreeDay f1 : freeDays) {
            if (f1.getDescription().equals(freeDayForm.getDescription())) {
                res = true;
                break;
            }
        }

        Assert.isTrue(res);
        Assert.isTrue(s.getFreeDays().size() == 2);

    }

    @Test
    public void testDeleteFreeDayAuthenticateSpecialist() {

        authenticate("specialist1");

        FreeDay freeDay = freeDayService.findOneToEdit(36);
        freeDayService.delete(freeDay);
        FreeDay freeDay2 = freeDayService.findOneToEdit(36);

        Assert.isTrue(freeDay2 == null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteOtherFreeDayAuthenticateSpecialist() {

        authenticate("specialist2");

        FreeDay freeDay = freeDayService.findOneToEdit(36);
        freeDayService.delete(freeDay);

    }

}
