package prescription.operations;

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
import services.AppointmentService;
import services.PatientService;
import services.PrescriptionService;
import services.SpecialistService;
import utilities.PopulateDatabase;
import domain.Appointment;
import domain.Patient;
import domain.Prescription;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PrescriptionServiceTest {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PrescriptionService prescriptionService;

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

    //crear receta en una cita que el paciente tiene con él
    @Test
    public void testCreatePrescriptionAuthenticateSpecialist() {
        authenticate("specialist1");

        Appointment a = appointmentService.findOneToEdit(35);

        Prescription prescription = prescriptionService.create(a);
        prescription.setTitle("titulo de la receta");
        prescription.setDescription("receta para Junit");
        prescription.setPrice(20.00);

        Prescription prescription2 = prescriptionService.save(prescription);

        Patient patient = patientService.findOneToEdit(20);

        Assert.isTrue(prescription2.getAppointment().getMedicalHistory().getPatient().equals(patient));

    }

    //crear receta para un paciente con el que no tiene cita
    @Test(expected = IllegalArgumentException.class)
    public void testCreatePrescriptionAuthenticateSpecialist2() {
        authenticate("specialist3");

        Appointment a = appointmentService.findOneToEdit(35);

        Prescription prescription = prescriptionService.create(a);
        prescription.setTitle("titulo de la receta");
        prescription.setDescription("receta para Junit");
        prescription.setPrice(20.00);

        prescriptionService.save(prescription);

    }
}
