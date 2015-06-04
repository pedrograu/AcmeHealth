package appointment.list;

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
import services.AppointmentService;
import services.FreeDayService;
import services.PatientService;
import services.SpecialistService;
import utilities.PopulateDatabase;
import domain.Appointment;
import domain.Patient;
import domain.Specialist;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AppointmentServiceTest {

    @Autowired
    private FreeDayService freeDayService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private AppointmentService appointmentService;

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
    public void testListMyAppouintmentNotFinishAuthenticatedPatient() {

        authenticate("patient2");

        Patient patient = patientService.findByPrincipal();

        Collection<Appointment> appointments = appointmentService.getMyScheduledAppointments();

        for (Appointment a : appointments) {

            Assert.isTrue(a.getPatient() == patient);
            Assert.isTrue(a.getIsFinish() == false);
        }

        Assert.isTrue(patient.getAppointments().size() == 1);

    }

    @Test
    public void testListMyAppouintmentNotFinishAuthenticatedSpecialist() {

        authenticate("specialist1");

        Specialist specialist = specialistService.findByPrincipal();

        Collection<Appointment> appointments = appointmentService.getAppointmentsNotFinish();

        for (Appointment a : appointments) {

            Assert.isTrue(a.getSpecialist() == specialist && a.getIsFinish() == false);
        }

        Assert.isTrue(appointments.size() == 1);

    }

    @Test
    public void testListMyAppouintmentFinishAuthenticatedSpecialist() {

        authenticate("specialist1");

        Specialist specialist = specialistService.findByPrincipal();

        Collection<Appointment> appointments = appointmentService.getAppointmentsFinish();

        for (Appointment a : appointments) {

            Assert.isTrue(a.getSpecialist() == specialist && a.getIsFinish() == true);
        }

        Assert.isTrue(appointments.size() == 1);

    }

}
