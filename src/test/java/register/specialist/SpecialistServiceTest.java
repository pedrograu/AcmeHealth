package register.specialist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import services.SpecialistService;
import services.SpecialtyService;
import utilities.PopulateDatabase;
import domain.Specialist;
import domain.Specialty;
import forms.SpecialistForm;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SpecialistServiceTest {

    @Autowired
    private SpecialistService specialistService;

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
    public void testRegister() {

        authenticate("administrator1");

        Specialist specialist;

        Specialty specialty = specialtyService.findOneToEdit(10);

        SpecialistForm specialistForm = new SpecialistForm();
        specialistForm.setSpecialty(specialty);
        specialistForm.setUsername("specialist99");
        specialistForm.setName("specialist99");
        specialistForm.setSurname("specialist99");
        specialistForm.setEmailAddress("specialist2@specialist2.com");
        specialistForm.setPassword("5ce4d191fd14ac85a1469fb8c29b7a7b");
        specialistForm.setSecondPassword("5ce4d191fd14ac85a1469fb8c29b7a7b");
        specialistForm.setAvailable(true);

        specialist = specialistService.reconstruct(specialistForm);
        specialistService.save(specialist);

        Specialist specialist2 = specialistService.findForUsername(specialistForm.getUsername());
        Assert.notNull(specialist2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterNotAdministratorConnect() {

        Specialist specialist;

        Specialty specialty = specialtyService.findOneToEdit(10);

        SpecialistForm specialistForm = new SpecialistForm();
        specialistForm.setSpecialty(specialty);
        specialistForm.setUsername("specialist98");
        specialistForm.setName("specialist98");
        specialistForm.setSurname("specialist98");
        specialistForm.setEmailAddress("specialist2@specialist2.com");
        specialistForm.setPassword("5ce4d191fd14ac85a1469fb8c29b7a7b");
        specialistForm.setSecondPassword("5ce4d191fd14ac85a1469fb8c29b7a7b");
        specialistForm.setAvailable(true);

        specialist = specialistService.reconstruct(specialistForm);
        specialistService.save(specialist);

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testFailDuplicate() {
        Specialist specialist;
        Specialty specialty = specialtyService.findOneToEdit(7);

        SpecialistForm specialistForm = new SpecialistForm();
        specialistForm.setSpecialty(specialty);
        specialistForm.setUsername("specialist1");
        specialistForm.setName("specialist1");
        specialistForm.setSurname("specialist1");
        specialistForm.setEmailAddress("specialist1@specialist1.com");
        specialistForm.setPassword("d5cee333abe432891a0de57d0ee38713");
        specialistForm.setSecondPassword("d5cee333abe432891a0de57d0ee38713");
        specialistForm.setAvailable(true);

        specialist = specialistService.reconstruct(specialistForm);
        specialistService.save(specialist);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterPasswordNotEqual() {

        Specialty specialty = specialtyService.findOneToEdit(10);
        Specialist specialist;
        SpecialistForm specialistForm = new SpecialistForm();
        specialistForm.setSpecialty(specialty);
        specialistForm.setUsername("specialist99");
        specialistForm.setName("specialist99");
        specialistForm.setSurname("specialist99");
        specialistForm.setEmailAddress("specialist2@specialist2.com");
        specialistForm.setPassword("5ce4d191fd14ac85a1469fb8c29b7a7b");
        specialistForm.setSecondPassword("5ce4d191fd14ac85a3333333333b7a7b");
        specialistForm.setAvailable(true);

        specialist = specialistService.reconstruct(specialistForm);
        specialistService.save(specialist);

    }

}