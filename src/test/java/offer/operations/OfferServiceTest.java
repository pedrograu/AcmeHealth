package offer.operations;

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
import services.OfferService;
import services.PatientService;
import services.SpecialistService;
import utilities.PopulateDatabase;
import domain.Offer;
import domain.Patient;
import domain.Specialist;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OfferServiceTest {

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

    @Test
    public void testCreateOffer() {
        authenticate("administrator1");
        Date startMoment = new Date(2014, 05, 30);
        Date finishMoment = new Date(2015, 05, 30);
        Specialist specialist = specialistService.findOneToEdit(12);
        Offer offer = offerService.create();
        offer.setSpecialist(specialist);
        offer.setStartMoment(startMoment);
        offer.setFinishMoment(finishMoment);
        offer.setTitle("Masaje terapeutico");
        offer.setDescription("Lo mejor para tu espalda");
        offer.setPrice(30.2);
        offer.setAmountPerson(100);

        offerService.save(offer);
        Collection<Offer> offers = offerService.findOwn();
        boolean res = false;
        for (Offer o : offers) {
            if (o.getTitle().equals(offer.getTitle())) {
                res = true;
                break;
            }

        }
        Assert.isTrue(res);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateOfferNotAuthenticated() {
        desauthenticate();
        Date startMoment = new Date(2014, 05, 30);
        Date finishMoment = new Date(2015, 05, 30);
        Specialist specialist = specialistService.findOneToEdit(12);
        Offer offer = offerService.create();
        offer.setSpecialist(specialist);
        offer.setStartMoment(startMoment);
        offer.setFinishMoment(finishMoment);
        offer.setTitle("Masaje terapeutico");
        offer.setDescription("Lo mejor para tu espalda");
        offer.setPrice(30.2);
        offer.setAmountPerson(100);

        offerService.save(offer);

    }

    @Test
    public void testDelete() {
        authenticate("administrator1");
        Offer offer = offerService.findOneToEdit(23);
        offerService.delete(offer);
        Assert.isTrue(offerService.findOneToEdit(23) == null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteFailNotAuthenticated() {
        desauthenticate();
        Offer offer = offerService.findOneToEdit(23);
        offerService.delete(offer);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteFailPatientEnroller() {
        authenticate("administrator1");
        Offer offer = offerService.findOneToEdit(22);
        offerService.delete(offer);

    }

    @Test
    public void testHire() {
        authenticate("patient1");
        Patient patientConnect = patientService.findByPrincipal();
        Offer offer = offerService.findOneToEdit(23);
        offerService.hire(offer);
        Assert.isTrue(offerService.findOneToEdit(23).getEnrollees().equals(1));
        Assert.isTrue(patientConnect.getOffers().contains(offerService.findOneToEdit(23)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHireFailNotAuthenticated() {
        desauthenticate();
        Offer offer = offerService.findOneToEdit(23);
        offerService.hire(offer);

    }

    //Intentar apuntarse a una oferta que no tiene plazas libres
    @Test(expected = IllegalArgumentException.class)
    public void testHireEnrolleesFull() {
        authenticate("patient2");

        Offer offer = offerService.findOneToEdit(22);
        offerService.hire(offer);

    }

    //Intentar apuntarse a una oferta que a finalizado
    @Test(expected = IllegalArgumentException.class)
    public void testHireOfferFinish() {
        authenticate("patient2");

        Offer offer = offerService.findOneToEdit(25);
        offerService.hire(offer);

    }

}
