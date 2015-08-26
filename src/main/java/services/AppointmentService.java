package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Appointment;
import domain.FreeDay;
import domain.Offer;
import domain.Patient;
import domain.Prescription;
import domain.Specialist;
import domain.Timetable;
import forms.AppointmentForm;
import forms.AppointmentForm2;
import forms.AppointmentForm3;
import repositories.AppointmentRepository;

@Service
@Transactional
public class AppointmentService {

    // Managed repository ---------------------------------------
    @Autowired
    private AppointmentRepository appointmentRepository;

    // Managed service ---------------------------------------

    @Autowired
    private PatientService patientService;

    @Autowired
    private SpecialistService specialistService;

    // Constructors ---------------------------------------------

    public AppointmentService() {
        super();
    }

    // Simple CRUD methods --------------------------------------

    //Crea un appointment nuevo para el paciente que está logueado
    public Appointment create() {

        Appointment appointment = new Appointment();
        Collection<Prescription> prescriptions = new HashSet<Prescription>();
        Patient patientConnect = patientService.findByPrincipal();

        appointment.setPrescriptions(prescriptions);
        appointment.setPatient(patientConnect);
        appointment.setMedicalHistory(patientConnect.getMedicalHistory());
        appointment.setIsFinish(false);

        return appointment;
    }

    //Crea un appointment nuevo
    public Appointment create2() {

        Appointment appointment = new Appointment();
        Collection<Prescription> prescriptions = new HashSet<Prescription>();

        appointment.setPrescriptions(prescriptions);

        return appointment;
    }

    //Crea un objeto formulario de tipo appointment a partir de un appointment
    public AppointmentForm createForm(Appointment appointment) {

        AppointmentForm appointmentForm = new AppointmentForm();

        appointmentForm.setId(appointment.getId());
        appointmentForm.setVersion(appointment.getVersion());

        appointmentForm.setPurpose(appointment.getPurpose());
        appointmentForm.setResult(appointment.getResult());
        appointmentForm.setStartMoment(appointment.getStartMoment());
        appointmentForm.setOffer(appointment.getOffer());

        return appointmentForm;
    }

    //Crea un objeto formulario de tipo appointment a partir de un appointment
    public AppointmentForm2 createForm2(Appointment appointment) {

        AppointmentForm2 appointmentForm = new AppointmentForm2();

        appointmentForm.setId(appointment.getId());
        appointmentForm.setVersion(appointment.getVersion());

        appointmentForm.setPurpose(appointment.getPurpose());
        appointmentForm.setResult(appointment.getResult());
        appointmentForm.setIsFinish(appointment.getIsFinish());

        return appointmentForm;
    }

    //Crea un objeto formulario de tipo appointment a partir de un appointment
    public AppointmentForm3 createForm3(Appointment appointment) {

        AppointmentForm3 appointmentForm = new AppointmentForm3();

        appointmentForm.setId(appointment.getId());
        appointmentForm.setVersion(appointment.getVersion());

        appointmentForm.setStartMoment(appointment.getStartMoment());
        appointmentForm.setPatient(appointment.getPatient());

        return appointmentForm;
    }

    //Se reconstruye un appointment a partir de un objeto formulario de tipo appointment
    public Appointment recontructor(AppointmentForm appointmentForm) {

        Appointment result;

        result = create();

        result.setId(appointmentForm.getId());
        result.setVersion(appointmentForm.getVersion());
        result.setPurpose(appointmentForm.getPurpose());
        result.setResult(appointmentForm.getResult());
        result.setStartMoment(appointmentForm.getStartMoment());
        result.setOffer(appointmentForm.getOffer());

        return result;
    }

    //Se reconstruye un appointment a partir de un objeto formulario de tipo appointment
    public Appointment recontructor2(AppointmentForm2 appointmentForm) {

        Specialist specialist = specialistService.findByPrincipal();
        Appointment result;

        result = appointmentRepository.findOne(appointmentForm.getId());
        Assert.isTrue(result.getSpecialist().getId() == specialist.getId());

        result.setId(appointmentForm.getId());
        result.setVersion(appointmentForm.getVersion());
        result.setPurpose(appointmentForm.getPurpose());
        result.setResult(appointmentForm.getResult());
        result.setIsFinish(appointmentForm.getIsFinish());

        return result;
    }

  //Se reconstruye un appointment a partir de un objeto formulario de tipo appointment
    public Appointment recontructor3(AppointmentForm3 appointmentForm) {

        Appointment result;

        result = create2();

        result.setId(appointmentForm.getId());
        result.setVersion(appointmentForm.getVersion());
        result.setStartMoment(appointmentForm.getStartMoment());
        result.setPatient(appointmentForm.getPatient());
        result.setSpecialist(appointmentForm.getSpecialist());
        result.setMedicalHistory(appointmentForm.getPatient().getMedicalHistory());

        return result;
    }

    //Se crea un appointment para un tratamiento en oferta
    public Appointment create2(Offer offer) {

        Appointment appointment = new Appointment();
        Collection<Prescription> prescriptions = new HashSet<Prescription>();
        Patient patientConnect = patientService.findByPrincipal();

        appointment.setPrescriptions(prescriptions);
        appointment.setPatient(patientConnect);
        appointment.setMedicalHistory(patientConnect.getMedicalHistory());
        appointment.setOffer(offer);

        return appointment;
    }

    //Se guarda un appointment en la base de datos
    public Appointment save2(Appointment appointment) {

        Date currentMoment = new Date();

        //Se comprueba que la fecha de la cita es valida(es posterior a la fecha actual)
        Assert.isTrue(appointment.getStartMoment().after(currentMoment));

        Calendar calendar3 = new GregorianCalendar();
        calendar3.setTime(appointment.getStartMoment());
        Integer h1 = calendar3.get(Calendar.HOUR_OF_DAY);
        boolean res = false;
        for (Timetable t : appointment.getSpecialist().getTimetables()) {
            Integer h2 = t.getStartShift().getHours();
            Integer h3 = t.getEndShift().getHours();
            if (h1 >= h2 && h1 <= h3) {
                res = true;
                break;
            }
        }
        //Se comprueba que la fecha de la cita es valida(está dentro del horario del especialista) 
        Assert.isTrue(res);
        boolean res2 = true;
        for (FreeDay f : appointment.getSpecialist().getFreeDays()) {

            if (appointment.getStartMoment().after(f.getStartMoment())
                    && appointment.getStartMoment().before(f.getFinishMoment())) {
                res2 = false;
                break;
            }
        }
        //Se comprueba que la fecha de la cita es valida(no está contenida dentro de los días libres del especialista)
        Assert.isTrue(res2);

        if (appointment.getOffer() != null) {

            Appointment appointment2 = getAppointmentForPatientAndOffer(appointment.getOffer());
            Assert.isTrue(appointment2 == null);
            //Se comprueba que la  fecha de la cita que se solicita para recibir el tratamiento en oferta 
            //está entre la fecha de inicio y de fin del tratamiento en oferta.
            Assert.isTrue(appointment.getOffer().getStartMoment().before(appointment.getStartMoment())
                    && appointment.getOffer().getFinishMoment().after(appointment.getStartMoment()));
            //Se comprueba que ese paciente está inscrito en la oferta para la que va a pedir cita
            Assert.isTrue(appointment.getOffer().getPatients().contains(appointment.getPatient()));

        }
        
        //le sumamos 10 min a la fecha de inicio para obtener el finishMoment:
        Calendar c = Calendar.getInstance();
        c.setTime(appointment.getStartMoment());
        c.add(Calendar.MINUTE, 10);
        appointment.setFinishMoment(c.getTime());
        
        return appointmentRepository.save(appointment);

    }

    //Se guarda un appointment en la base de datos
    public Appointment save3(Appointment appointment) {
        checkPrincipal(appointment);
        Appointment appointment2 = appointmentRepository.save(appointment);
        return appointment2;
    }

    //Devuelve una collection de appointments finalizados del especialista logueado en el sistema.
    public Collection<Appointment> getAppointmentsFinish() {
        Specialist specialistConnect = specialistService.findByPrincipal();
        Collection<Appointment> appointments = appointmentRepository.getAppointmentsFinish(specialistConnect.getId());

        return appointments;
    }

    //Devuelve una collection de appointments no finalizados del especialista logueado en el sistema.
    public Collection<Appointment> getAppointmentsNotFinish() {
        Specialist specialistConnect = specialistService.findByPrincipal();
        Collection<Appointment> appointments = appointmentRepository.getAppointmentsNotFinish2(specialistConnect.getId());

        return appointments;
    }

    //Devuelve una collection de appointments no finalizadas para el paciente logueado en el sistema.
    public Collection<Appointment> getMyScheduledAppointments() {

        Patient patientConnect = patientService.findByPrincipal();
        Collection<Appointment> appointments = appointmentRepository.getAppointmentsNotFinish(patientConnect.getId());

        return appointments;
    }

    //Devuelve una collection de appointments para un especialista y un paciente
    public Collection<Appointment> getAppointmentforOneSpecialistAndOnePatient(Specialist specialist, Patient patient) {
        Collection<Appointment> appointments = appointmentRepository.getAppointmentforOneSpecialistAndOnePatient(
                specialist.getId(), patient.getId());
        return appointments;
    }

    //Devuelve un appointment dado su id
    public Appointment findOneToEdit(int appointmentId) {
        Appointment appointment = appointmentRepository.findOne(appointmentId);
        return appointment;
    }

    //
//    public boolean isCreate(Appointment appointment) {
//        boolean res = false;
//        Date startMoment = appointment.getStartMoment();
//        if (startMoment == null) {
//            res = true;
//        } else {
//            Calendar calendar = new GregorianCalendar();
//            calendar.setTime(startMoment);
//            Integer h = calendar.get(Calendar.HOUR);
//            Integer m = calendar.get(Calendar.MINUTE);
//
//            if (h == 0 && m == 0) {
//                res = true;
//            }
//        }
//        return res;
//    }

    //Devuelve el appointment para un tratamiento en oferta del paciente que está logueado en el sistema
    public Appointment getAppointmentForPatientAndOffer(Offer offer) {
        Patient patientConnect = patientService.findByPrincipal();
        Appointment ap = appointmentRepository.getAppointmentForPatientAndOffer(patientConnect.getId(), offer.getId());
        return ap;
    }

    //Convierte un string en un date
    public Date stringToDate(String startMoment) {

        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strFecha = startMoment;
        Date fecha = null;

        try {

            fecha = formatoDelTexto.parse(strFecha);

        } catch (ParseException ex) {

            ex.printStackTrace();

        }

        return fecha;
    }

    //Comprueba que un string cumple el patron de una fecha
    public boolean cumplePatron(String startMoment) {
        boolean res = false;
        Pattern pat = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)[0-9][0-9]\\s([01][0-9]|2[0-4]):[0-5][0-9]$");
        Matcher mat = pat.matcher(startMoment);
        if (mat.matches()) {
            res = true;
        }
        return res;
    }

    //Devuelve un appointment dado el especialista y la fecha de inicio
    public Appointment getAppointmentForStartMoment(Date startMoment, Specialist specialist) {
        Appointment ap = appointmentRepository.getAppointmentForStartMoment(startMoment, specialist.getId());

        return ap;
    }

    //Devuelve una collection de appointment para un especialista y una fecha de inicio.
    public Collection<Appointment> getAppointmentforOneSpecialistAndDay(int id, Date d1) {

        String format = new SimpleDateFormat("yyyy-MM-dd").format(d1);
        String string1 = format + " " + "00:00:00.1";
        String string2 = format + " " + "23:59:59.9";
        Date date1 = new Date();
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(string1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = new Date();
        try {
            date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(string2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return appointmentRepository.getAppointmentforOneSpecialistAndDay(id, date1, date2);
    }

    //Comprueba que el appointment es del especialista que está logueado en el sistema.
    public void checkPrincipal(Appointment appointment) {
        Specialist specialist = specialistService.findByPrincipal();
        Assert.isTrue(appointment.getSpecialist().equals(specialist));
    }

    //Elimina un appointment de la base de datos
    public void cancel(Appointment appointment) {
        appointmentRepository.delete(appointment);

    }

}
