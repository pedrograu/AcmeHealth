package specialty.operations;

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
    public void testCreateSpecialty() {
        authenticate("administrator1");

        Specialty specialty = specialtyService.create();
        specialty.setDescription("Esta es la description de la nueva especialidad");
        specialty.setName("GinecologiaTest");

        specialtyService.save(specialty);
        Collection<Specialty> specialties = specialtyService.getAll();
        boolean res = false;
        for (Specialty s : specialties) {
            if (s.getName().equals(specialty.getName())) {
                res = true;
                break;
            }

        }
        Assert.isTrue(res);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSpecialtyNotAuthenticated() {
        desauthenticate();

        Specialty specialty = specialtyService.create();
        specialty.setDescription("Esta es la description de la nueva especialidad");
        specialty.setName("GinecologiaTest");

        specialtyService.save(specialty);

    }

    //da un NullPointerException porque el checkprincipal devuelve nulo el administratorConnect
    @Test(expected = NullPointerException.class)
    public void testCreateSpecialtyAuthenticatedPatient() {
        authenticate("patient1");

        Specialty specialty = specialtyService.create();
        specialty.setDescription("Esta es la description de la nueva especialidad2");
        specialty.setName("GinecologiaTest2");

        specialtyService.save(specialty);

    }

    //da un NullPointerException porque el checkprincipal devuelve nulo el administratorConnect
    @Test(expected = NullPointerException.class)
    public void testCreateSpecialtyAuthenticatedSpecialist() {
        authenticate("specialist1");

        Specialty specialty = specialtyService.create();
        specialty.setDescription("Esta es la description de la nueva especialidad3");
        specialty.setName("GinecologiaTest3");

        specialtyService.save(specialty);

    }

}
