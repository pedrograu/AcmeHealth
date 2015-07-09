package specialty.list;

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

import domain.Specialty;

import security.LoginService;
import services.SpecialtyService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;
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
    public void testListNotAuthenticated() {
        Collection<Specialty> specialties;
        specialties = specialtyService.getAll();

        Assert.isTrue(specialties.size() == 4);
    }

    @Test
    public void testListAdministrator() {
        authenticate("administrator1");
        Collection<Specialty> specialties;
        specialties = specialtyService.getAll();

        Assert.isTrue(specialties.size() == 4);
    }

    @Test
    public void testDetails() {
        Specialty specialty = specialtyService.findOneToEdit(12);
        Assert.isTrue(specialty.getAdministrator().getUserAccount().getUsername().equals("administrator1"));
    }

}
