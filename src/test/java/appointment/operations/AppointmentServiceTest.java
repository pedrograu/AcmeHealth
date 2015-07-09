package appointment.operations;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

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
import services.MessageService;
import services.OfferService;
import services.PatientService;
import services.ProfileService;
import services.SpecialistService;
import utilities.PopulateDatabase;
import domain.Appointment;
import domain.Message;
import domain.Offer;
import domain.Patient;
import domain.Specialist;
import forms.AppointmentForm;
import forms.AppointmentForm2;
import forms.AppointmentForm3;
import forms.MessageForm;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AppointmentServiceTest {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private OfferService offerService;

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

    //pedir cita con tu medico de cabecera, en un horario valido
    @Test
    public void testCreateAppointmentForSpecialistInMedicinaGeneral() {

        authenticate("patient1");

        Calendar startCalendar = new GregorianCalendar();
        startCalendar.set(Calendar.DAY_OF_MONTH, 27);
        startCalendar.set(Calendar.MONTH, 06);
        startCalendar.set(Calendar.YEAR, 2016);
        startCalendar.set(Calendar.HOUR_OF_DAY, 8);
        startCalendar.set(Calendar.MINUTE, 00);
        startCalendar.set(Calendar.SECOND, 00);
        Date fechaElegida = startCalendar.getTime();
        AppointmentForm appointmentForm = new AppointmentForm();

        appointmentForm.setStartMoment(fechaElegida);

        Appointment appointment = appointmentService.recontructor(appointmentForm);
        Specialist s = specialistService.findOneToEdit(16);
        appointment.setSpecialist(s);
        appointmentService.save2(appointment);

        Patient patient = patientService.findByPrincipal();

        Collection<Appointment> ap = appointmentService.getAppointmentforOneSpecialistAndOnePatient(s, patient);

        boolean res = false;
        for (Appointment a : ap) {
            if ((a.getStartMoment().equals(fechaElegida)) && (a.getSpecialist().equals(patient.getSpecialist()))
                    && a.getIsFinish() == false && a.getOffer() == null) {

                res = true;
                break;
            }
        }

        Assert.isTrue(res);

    }

    //pedir cita para una oferta en la que está apuntado, en un horario valido
    @Test
    public void testCreateAppointmentForSpecialistAndOffer() {

        authenticate("patient1");

        Offer offer = offerService.findOneToEdit(30);

        Calendar startCalendar = new GregorianCalendar();
        startCalendar.set(Calendar.DAY_OF_MONTH, 7);
        startCalendar.set(Calendar.MONTH, 1);
        startCalendar.set(Calendar.YEAR, 2016);
        startCalendar.set(Calendar.HOUR_OF_DAY, 8);
        startCalendar.set(Calendar.MINUTE, 00);
        startCalendar.set(Calendar.SECOND, 00);
        Date fechaElegida = startCalendar.getTime();
        AppointmentForm appointmentForm = new AppointmentForm();

        appointmentForm.setStartMoment(fechaElegida);
        appointmentForm.setOffer(offer);

        Appointment appointment = appointmentService.recontructor(appointmentForm);
        Specialist s = specialistService.findOneToEdit(16);
        appointment.setSpecialist(s);
        appointmentService.save2(appointment);

        Patient patient = patientService.findByPrincipal();

        Collection<Appointment> ap = appointmentService.getAppointmentforOneSpecialistAndOnePatient(s, patient);

        boolean res = false;
        for (Appointment a : ap) {
            if ((a.getStartMoment().equals(fechaElegida)) && (a.getSpecialist().equals(offer.getSpecialist()))
                    && a.getIsFinish() == false && a.getOffer() == offer) {

                res = true;
                break;
            }
        }

        Assert.isTrue(res);

    }

    //Atender una cita por un especialista
    @Test
    public void testAttendAppointment() {

        authenticate("specialist1");

        Appointment ap = appointmentService.findOneToEdit(43);

        AppointmentForm2 appointmentForm = appointmentService.createForm2(ap);

        appointmentForm.setPurpose("purpose para Junit");
        appointmentForm.setResult("result para Junit");
        appointmentForm.setIsFinish(true);

        Appointment appointment = appointmentService.recontructor2(appointmentForm);

        Appointment appointment2 = appointmentService.save3(appointment);

        Assert.isTrue(appointment2.getPurpose() == "purpose para Junit");
        Assert.isTrue(appointment2.getResult() == "result para Junit");
        Assert.isTrue(appointment2.getIsFinish() == true);

    }

    //Un especialista pide una cita para un paciente
    @Test
    public void testCreateAppointmentSpecialistForPatient() {

        authenticate("specialist1");
        Patient patient = patientService.findOneToEdit(26);
        Specialist specialist = specialistService.findOneToEdit(24);

        Calendar startCalendar = new GregorianCalendar();
        startCalendar.set(Calendar.DAY_OF_MONTH, 14);
        startCalendar.set(Calendar.MONTH, 9);
        startCalendar.set(Calendar.YEAR, 2016);
        startCalendar.set(Calendar.HOUR_OF_DAY, 14);
        startCalendar.set(Calendar.MINUTE, 00);
        startCalendar.set(Calendar.SECOND, 00);
        Date fechaElegida = startCalendar.getTime();
        AppointmentForm3 appointmentForm = new AppointmentForm3();

        appointmentForm.setStartMoment(fechaElegida);
        appointmentForm.setPatient(patient);
        appointmentForm.setSpecialist(specialist);

        Appointment appointment = appointmentService.recontructor3(appointmentForm);
        appointment.setSpecialist(specialist);
        appointmentService.save2(appointment);

        Assert.isTrue(!specialist.getAppointments().isEmpty());

        boolean res = false;
        for (Appointment a : patient.getAppointments()) {
            if ((a.getStartMoment().equals(fechaElegida)) && (a.getSpecialist().equals(specialist))
                    && a.getIsFinish() == false && a.getOffer() == null) {

                res = true;
                break;
            }
        }

        Assert.isTrue(res);

    }

    //Un especialista cancela una cita con paciente
    @Test
    public void testCancelAppointmentSpecialistForPatient() {

        authenticate("specialist1");
        Patient patient = patientService.findOneToEdit(26);
        Specialist specialist = specialistService.findByPrincipal();
        Appointment appointment = appointmentService.findOneToEdit(43);

        MessageForm messageForm = new MessageForm();

        messageForm.setRecipient(patient);
        messageForm.setSubject("mensaje Junit");
        messageForm.setTextBody("textBody Junit");

        Message message = messageService.recontructor(messageForm);

        Message messagePrueba = messageService.save(message);
        appointmentService.cancel(appointment);

        Appointment appointment2 = appointmentService.findOneToEdit(43);
        Assert.isTrue(appointment2 == null);
        Assert.isTrue(messagePrueba.getSender().equals(specialist) && messagePrueba.getRecipient().equals(patient));

    }

    //pedir cita con tu medico de cabecera, en un dia en el que el especialista no trabaja.
    @Test(expected = IllegalArgumentException.class)
    public void testCreateAppointmentForSpecialistInMedicinaGeneralInvalidMoment() {

        authenticate("patient1");

        Calendar startCalendar = new GregorianCalendar();
        startCalendar.set(Calendar.DAY_OF_MONTH, 10);
        startCalendar.set(Calendar.MONTH, 8);
        startCalendar.set(Calendar.YEAR, 2014);
        startCalendar.set(Calendar.HOUR_OF_DAY, 15);
        startCalendar.set(Calendar.MINUTE, 30);
        startCalendar.set(Calendar.SECOND, 00);
        Date fechaElegida = startCalendar.getTime();
        AppointmentForm appointmentForm = new AppointmentForm();

        appointmentForm.setStartMoment(fechaElegida);

        Appointment appointment = appointmentService.recontructor(appointmentForm);
        Specialist s = specialistService.findOneToEdit(16);
        appointment.setSpecialist(s);
        appointmentService.save2(appointment);

    }

    //pedir cita con tu medico de cabecera, en un dia en el que el especialista esta de "freeDay".
    @Test(expected = IllegalArgumentException.class)
    public void testCreateAppointmentForSpecialistInMedicinaGeneralInvalidMoment2() {

        authenticate("patient1");

        Calendar startCalendar = new GregorianCalendar();
        startCalendar.set(Calendar.DAY_OF_MONTH, 15);
        startCalendar.set(Calendar.MONTH, 5);
        startCalendar.set(Calendar.YEAR, 2014);
        startCalendar.set(Calendar.HOUR_OF_DAY, 8);
        startCalendar.set(Calendar.MINUTE, 30);
        startCalendar.set(Calendar.SECOND, 00);
        Date fechaElegida = startCalendar.getTime();
        AppointmentForm appointmentForm = new AppointmentForm();

        appointmentForm.setStartMoment(fechaElegida);

        Appointment appointment = appointmentService.recontructor(appointmentForm);
        Specialist s = specialistService.findOneToEdit(16);
        appointment.setSpecialist(s);
        appointmentService.save2(appointment);

    }

    //pedir cita para una oferta en la que NO está apuntado
    @Test(expected = IllegalArgumentException.class)
    public void testCreateAppointmentForSpecialistAndOfferNotHire() {

        authenticate("patient1");

        Offer offer = offerService.findOneToEdit(30);

        Calendar startCalendar = new GregorianCalendar();
        startCalendar.set(Calendar.DAY_OF_MONTH, 8);
        startCalendar.set(Calendar.MONTH, 1);
        startCalendar.set(Calendar.YEAR, 2015);
        startCalendar.set(Calendar.HOUR_OF_DAY, 8);
        startCalendar.set(Calendar.MINUTE, 00);
        startCalendar.set(Calendar.SECOND, 00);
        Date fechaElegida = startCalendar.getTime();
        AppointmentForm appointmentForm = new AppointmentForm();

        appointmentForm.setStartMoment(fechaElegida);
        appointmentForm.setOffer(offer);

        Appointment appointment = appointmentService.recontructor(appointmentForm);
        Specialist s = specialistService.findOneToEdit(16);
        appointment.setSpecialist(s);
        appointmentService.save2(appointment);

    }

    //pedir cita para una oferta en la que está apuntado, despues del finisMoment de la offer
    @Test(expected = IllegalArgumentException.class)
    public void testCreateAppointmentForSpecialistAndOfferAfterFinishMoment() {

        authenticate("patient1");

        Offer offer = offerService.findOneToEdit(30);

        Calendar startCalendar = new GregorianCalendar();
        startCalendar.set(Calendar.DAY_OF_MONTH, 8);
        startCalendar.set(Calendar.MONTH, 0);
        startCalendar.set(Calendar.YEAR, 2017);
        startCalendar.set(Calendar.HOUR_OF_DAY, 8);
        startCalendar.set(Calendar.MINUTE, 00);
        startCalendar.set(Calendar.SECOND, 00);
        Date fechaElegida = startCalendar.getTime();
        AppointmentForm appointmentForm = new AppointmentForm();

        appointmentForm.setStartMoment(fechaElegida);
        appointmentForm.setOffer(offer);

        Appointment appointment = appointmentService.recontructor(appointmentForm);
        Specialist s = specialistService.findOneToEdit(16);
        appointment.setSpecialist(s);
        appointmentService.save2(appointment);

    }

}
