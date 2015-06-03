package services;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AppointmentRepository;
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
	private TimetableService timetableService;
	

	@Autowired
	private SpecialistService specialistService;

	// Constructors ---------------------------------------------

	public AppointmentService() {
		super();
	}

	// Simple CRUD methods --------------------------------------


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
	
	public Appointment create2() {
		
		Appointment appointment = new Appointment();
		Collection<Prescription> prescriptions = new HashSet<Prescription>();

		appointment.setPrescriptions(prescriptions);
	
		
		
		return appointment;
	}
	
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
	
	public AppointmentForm2 createForm2(Appointment appointment) {
		
		AppointmentForm2 appointmentForm = new AppointmentForm2();
		
		appointmentForm.setId(appointment.getId());
		appointmentForm.setVersion(appointment.getVersion());

		appointmentForm.setPurpose(appointment.getPurpose());
		appointmentForm.setResult(appointment.getResult());
		appointmentForm.setIsFinish(appointment.getIsFinish());
		//appointmentForm.setStartMoment(appointment.getStartMoment());
	
		
		return appointmentForm;
	}
	
	public AppointmentForm3 createForm3(Appointment appointment) {
		
		AppointmentForm3 appointmentForm = new AppointmentForm3();
		
		appointmentForm.setId(appointment.getId());
		appointmentForm.setVersion(appointment.getVersion());

	
		appointmentForm.setStartMoment(appointment.getStartMoment());
		appointmentForm.setPatient(appointment.getPatient());

		return appointmentForm;
	}
	
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
	
	public Appointment recontructor2(AppointmentForm2 appointmentForm) {
		
		Specialist specialist = specialistService.findByPrincipal();
		Appointment result;

		result = appointmentRepository.findOne(appointmentForm.getId());
		Assert.isTrue(result.getTimetable().getSpecialist().getId() == specialist.getId());

		result.setId(appointmentForm.getId());
		result.setVersion(appointmentForm.getVersion());
		result.setPurpose(appointmentForm.getPurpose());
		result.setResult(appointmentForm.getResult());
		result.setIsFinish(appointmentForm.getIsFinish());
		//result.setStartMoment(appointmentForm.getStartMoment());


		return result;
	}
	
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
	
	public Appointment create2(Offer offer) {
		
		Appointment appointment = new Appointment();
		Collection<Prescription> prescriptions = new HashSet<Prescription>();
		Patient patientConnect = patientService.findByPrincipal();
		
		//appointment.setTimetable(timetable);
		appointment.setPrescriptions(prescriptions);
		//appointment.setSpecialist(patientConnect.getSpecialist());
		appointment.setPatient(patientConnect);
		appointment.setMedicalHistory(patientConnect.getMedicalHistory());
		appointment.setOffer(offer);
		
		
		return appointment;
	}

//	public Appointment save(Appointment appointment, boolean isOffer) {
//
//		Date currentMoment = new Date();
//		if (isOffer == false) {
//			Assert.isTrue(appointment.getStartMoment().after(currentMoment));
//		} else {
//			Appointment appointment2 = getAppointmentForPatientAndOffer(appointment.getOffer());
//			Assert.isTrue(appointment2==null);
//			Assert.isTrue(appointment.getStartMoment().after(currentMoment));
//			Assert.isTrue(appointment.getOffer().getStartMoment().before(appointment.getStartMoment())
//					&& appointment.getOffer().getFinishMoment().after(appointment.getStartMoment()));
//
//		}
//		Appointment appointment2 = appointmentRepository.save(appointment);
//
//		return appointment2;
//
//	}
	
	public void save2(Appointment appointment){
		
		Date currentMoment = new Date();

		Assert.isTrue(appointment.getStartMoment().after(currentMoment));
		
		

		Calendar calendar3 = new GregorianCalendar();
		calendar3.setTime(appointment.getStartMoment());
		Integer h1 = calendar3.get(Calendar.HOUR_OF_DAY);
		boolean res = false;
		for(Timetable t : appointment.getSpecialist().getTimetables()){
			Integer h2 = t.getStartShift().getHours();
			Integer h3 = t.getEndShift().getHours();
			if(h1>=h2 && h1<=h3){
				res=true;
				break;
			}
		}
		Assert.isTrue(res);
		boolean res2 = true;
		for(FreeDay f : appointment.getSpecialist().getFreeDays()){

			if(appointment.getStartMoment().after(f.getStartMoment()) && appointment.getStartMoment().before(f.getFinishMoment())){
				res2=false;
				break;
			}
		}
		Assert.isTrue(res2);
		
		
		
		if(appointment.getOffer()!=null){
			
			Appointment appointment2 = getAppointmentForPatientAndOffer(appointment.getOffer());
			Assert.isTrue(appointment2==null);
			Assert.isTrue(appointment.getOffer().getStartMoment().before(appointment.getStartMoment())
					&& appointment.getOffer().getFinishMoment().after(appointment.getStartMoment()));
			Assert.isTrue(appointment.getOffer().getPatients().contains(appointment.getPatient()));
			
		}
		
		
		Appointment appointment2 = appointmentRepository.save(appointment);
		
		//Le asociamos el timetable correspondiente a la cita elegida.

		Calendar starCalendar = new GregorianCalendar();
		Date startMomentAppointment = appointment.getStartMoment();
		
		starCalendar.setTime(startMomentAppointment);
		
		int diaDeLaSemana = starCalendar.get(Calendar.DAY_OF_WEEK);
		Specialist specialist = appointment.getSpecialist();
		List<Timetable> timetablesParaEseDiaDeLaSemana = new ArrayList<Timetable>();
		timetablesParaEseDiaDeLaSemana = timetableService.getTimetablesForDayOfWeekAndSpecialist(diaDeLaSemana, specialist.getId());
		
		Date fechaElegida = appointment2.getStartMoment();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fechaElegida);
		Integer h = calendar.get(Calendar.HOUR_OF_DAY);


		for(Timetable t : timetablesParaEseDiaDeLaSemana){
			Integer h2 = t.getStartShift().getHours();
			Integer h3 = t.getEndShift().getHours();
			if(h>=h2 && h<=h3){
				appointment2.setTimetable(t);
				break;
			}
		}
		
	
	}
	
	public Appointment save3(Appointment appointment) {
		checkPrincipal(appointment);
		Appointment appointment2 = appointmentRepository.save(appointment);
		return appointment2;
	}


	public Collection<Appointment> getAppointmentsFinish() {
		Specialist specialistConnect = specialistService.findByPrincipal();
		Collection<Appointment> appointments = appointmentRepository.getAppointmentsFinish(specialistConnect.getId());
		
		return appointments;
	}
	
	public Collection<Appointment> getAppointmentsNotFinish() {
		Specialist specialistConnect = specialistService.findByPrincipal();
		Collection<Appointment> appointments = appointmentRepository.getAppointmentsNotFinish2(specialistConnect.getId());
		
		return appointments;
	}

	public Collection<Appointment> getMyScheduledAppointments() {
		
		Patient patientConnect = patientService.findByPrincipal();
		Collection<Appointment> appointments = appointmentRepository.getAppointmentsNotFinish(patientConnect.getId());
		
		return appointments;
	}

	public Collection<Appointment> getAppointmentforOneSpecialistAndOnePatient( Specialist specialist, Patient patient) {
		Collection<Appointment> appointments = appointmentRepository.getAppointmentforOneSpecialistAndOnePatient(specialist.getId(), patient.getId());
		return appointments;
	}

	public Appointment findOneToEdit(int appointmentId) {
		Appointment appointment = appointmentRepository.findOne(appointmentId);
		return appointment;
	}

	public boolean isCreate(Appointment appointment) {
		boolean res = false;
		Date startMoment = appointment.getStartMoment();
		if (startMoment == null) {
			res = true;
		} else {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(startMoment);
			Integer h = calendar.get(Calendar.HOUR);
			Integer m = calendar.get(Calendar.MINUTE);

			if (h == 0 && m == 0) {
				res = true;
			}
		}
		return res;
	}

	public Appointment getAppointmentForPatientAndOffer(Offer offer) {
		Patient patientConnect = patientService.findByPrincipal();
		Appointment ap = appointmentRepository.getAppointmentForPatientAndOffer(patientConnect.getId(),offer.getId());
		return ap;
	}

	public Date stringToDate(String startMoment) {
		
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
		String strFecha = startMoment;
		Date fecha = null;

		try {

			fecha = formatoDelTexto.parse(strFecha);

		}catch (ParseException ex) {

			ex.printStackTrace();

			}
		
		return fecha;
	}

	public boolean cumplePatron(String startMoment) {
		boolean res = false;
		Pattern pat = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)[0-9][0-9]$");
		Matcher mat = pat.matcher(startMoment);
		if (mat.matches()) {
			res = true;
		} 
		return res;
	}

	public Appointment getAppointmentForStartMoment(Date startMoment,Specialist specialist) {
		Appointment ap = appointmentRepository.getAppointmentForStartMoment(startMoment,specialist.getId());
				
		return ap;
	}

	public void checkPrincipal(Appointment appointment) {
		Specialist specialist = specialistService.findByPrincipal();
		Assert.isTrue(appointment.getTimetable().getSpecialist().equals(specialist));
	}

	public void cancel(Appointment appointment) {
		appointmentRepository.delete(appointment);
		
	}
	


}
