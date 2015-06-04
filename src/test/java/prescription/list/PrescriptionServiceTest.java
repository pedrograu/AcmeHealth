package prescription.list;

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
    private PrescriptionService prescriptionService;
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
    public void testListMyPrescriptionsAuthenticatePatient() {

        authenticate("patient1");
        Patient patientConnect = patientService.findByPrincipal();
        Collection<Prescription> prescriptions = prescriptionService.findMyPrescriptions();

        boolean res = false;
        for (Prescription p : prescriptions) {
            res = false;
            for (Appointment a : patientConnect.getMedicalHistory().getAppointments()) {
                if (a.getPrescriptions().contains(p)) {
                    res = true;
                    break;
                }
            }
        }
        Assert.isTrue(res);

    }

    //Listar todas las recetas que se le ha recetado a un paciente
    @Test
    public void testListPrescriptionsForPatient() {

        authenticate("specialist1");
        Patient patient = patientService.findOneToEdit(18);
        Collection<Prescription> prescriptions = prescriptionService.findForPatient(patient);

        boolean res = false;
        for (Prescription p : prescriptions) {
            res = false;
            for (Appointment a : patient.getMedicalHistory().getAppointments()) {
                if (a.getPrescriptions().contains(p)) {
                    res = true;
                    break;
                }
            }
        }
        Assert.isTrue(res);

        Assert.isTrue(prescriptions.size() == 1);

    }

}
