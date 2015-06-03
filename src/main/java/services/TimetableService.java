package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TimetableRepository;
import domain.Appointment;
import domain.FreeDay;
import domain.Offer;
import domain.Patient;
import domain.Specialist;
import domain.Timetable;
import forms.TimetableForm;



@Service
@Transactional
public class TimetableService {
	
	// Managed repository ---------------------------------------
	@Autowired
	private TimetableRepository timetableRepository;

	// Managed service ---------------------------------------
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private SpecialistService specialistService;
	


	// Constructors ---------------------------------------------

	public TimetableService() {
		super();
	}



	// Simple CRUD methods --------------------------------------



	public Collection<Timetable> getTimetablesForSpecialist(Specialist s) {
		
		Collection<Timetable> timetables = timetableRepository.getTimetablesForSpecialist(s.getId());
	
		return timetables;
	}


	public Timetable findOneToEdit(int timetableId) {
		Timetable timetable = timetableRepository.findOne(timetableId);
		return timetable;
	}
	
	
	public List<Date> getDatesAvailables(Date fechaElegida, Offer offer) {

		// Este metodo coge del startmoment del appointment q recibe como
		// parametro
		// el dia de la semana seleccionado, coge el timetable correspondiente a
		// ese dia para
		// el medico de cabecera del patient conectado y muestra los intervalos
		// disponibles.

		// 1º vemos a que dia de la semana pertenece la fecha elegida y
		// mostramos los timetables correspondientes a ese dia de la semana:
		Calendar starCalendar = new GregorianCalendar();

		starCalendar.setTime(fechaElegida);
		
		//miramos si es para tu medico de cabecera o para un especialista de una oferta
		Specialist specialist;
		if(offer==null){
			Patient patientConnect = patientService.findByPrincipal();
			specialist = patientConnect.getSpecialist();
		}else{
			specialist = offer.getSpecialist();
		}

		List<Date> freeSlots = new ArrayList<Date>();
		
		Calendar cal = new GregorianCalendar();
		cal.setLenient(false);
		cal.setTime(fechaElegida);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.getTime();
		
		//miramos si el dia elegido es uno que esta contenido en algun freeDay de ese especialista.
		for(FreeDay f : specialist.getFreeDays()){
			
			Date startShift = f.getStartMoment();
			Date endShift = f.getFinishMoment();

	
			
			//si esta contenida la fecha elegida en un freeDay devuelve la lista de freeSlots para ese dia vacia
			if(cal.getTime().after(startShift) && fechaElegida.before(endShift) ){
				
				return freeSlots;
			
			}
			
		}	


		int diaDeLaSemana = starCalendar.get(Calendar.DAY_OF_WEEK);
	
		List<Timetable> timetablesParaEseDiaDeLaSemana = new ArrayList<Timetable>();
		timetablesParaEseDiaDeLaSemana = timetableRepository
				.getTimetablesForDayOfWeekAndSpecialist(diaDeLaSemana,
						specialist.getId());

		// 2º sacamos el startMoment y el finishMoment de cada timetable, lo
		// convertimos a tipo calendar y creamos los intervalos cada 10 minutos:

		

		Calendar actualMoment = new GregorianCalendar();

		for (Timetable t : timetablesParaEseDiaDeLaSemana) {

			Date startShift = t.getStartShift();
			Date endShift = t.getEndShift();

			Calendar startShiftCalendar = new GregorianCalendar();
			Calendar endShiftCalendar = new GregorianCalendar();

			startShiftCalendar.setTime(startShift);
			endShiftCalendar.setTime(endShift);

			// cambiamos la fecha que nos da del 1/1/1970 por la seleccionada
			// por el patient:

			startShiftCalendar.set(Calendar.DAY_OF_MONTH,
					starCalendar.get(Calendar.DAY_OF_MONTH));
			startShiftCalendar.set(Calendar.MONTH,
					starCalendar.get(Calendar.MONTH));
			startShiftCalendar.set(Calendar.YEAR,
					starCalendar.get(Calendar.YEAR));

			endShiftCalendar.set(Calendar.DAY_OF_MONTH,
					starCalendar.get(Calendar.DAY_OF_MONTH));
			endShiftCalendar.set(Calendar.MONTH,
					starCalendar.get(Calendar.MONTH));
			endShiftCalendar
					.set(Calendar.YEAR, starCalendar.get(Calendar.YEAR));

			// si la cita es para el dia de hoy comprobamos la hora actual para
			// no crear citas pasadas.
			if (actualMoment.after(startShiftCalendar)
					&& actualMoment.after(endShiftCalendar)) {
				startShiftCalendar = actualMoment;

			} else if (actualMoment.after(startShiftCalendar)) {
				startShiftCalendar = actualMoment;

				while (startShiftCalendar.before(endShiftCalendar)) {
					startShiftCalendar.add(Calendar.MINUTE, 10); // Añadimos 10
																	// minutos
					freeSlots.add(startShiftCalendar.getTime());
				}

			} else {

				freeSlots.add(startShiftCalendar.getTime());
				while (startShiftCalendar.before(endShiftCalendar)) {
					startShiftCalendar.add(Calendar.MINUTE, 10); // Añadimos 10
																	// minutos
					freeSlots.add(startShiftCalendar.getTime());
				}
			}

		}

		// 3º quitamos los slots ocupados

		List<Date> ocupadosSlots = new ArrayList<Date>();

		for (Timetable t : timetablesParaEseDiaDeLaSemana) {
			for (Appointment a : t.getAppointments()) {
				for (Date d : freeSlots) {

					Calendar fecha = new GregorianCalendar();
					Calendar fecha2 = new GregorianCalendar();

					fecha.setTime(a.getStartMoment());
					fecha2.setTime(d);

					if (fecha.equals(fecha2)) {
						ocupadosSlots.add(fecha2.getTime());
					}
				}
			}
		}
		
		freeSlots.removeAll(ocupadosSlots);
		


		return freeSlots;
	}

	
		
		
		
		
	public List<Date> getDatesAvailables2(Date fechaElegida,Specialist specialist) {

		// Este metodo coge del startmoment del appointment q recibe como
		// parametro
		// el dia de la semana seleccionado, coge el timetable correspondiente a
		// ese dia para
		// el medico de cabecera del patient conectado y muestra los intervalos
		// disponibles.

		// 1º vemos a que dia de la semana pertenece la fecha elegida y
		// mostramos los timetables correspondientes a ese dia de la semana:
		Calendar starCalendar = new GregorianCalendar();

		starCalendar.setTime(fechaElegida);

		int diaDeLaSemana = starCalendar.get(Calendar.DAY_OF_WEEK);

		List<Timetable> timetablesParaEseDiaDeLaSemana = new ArrayList<Timetable>();
		timetablesParaEseDiaDeLaSemana = timetableRepository
				.getTimetablesForDayOfWeekAndSpecialist(diaDeLaSemana,
						specialist.getId());

		// 2º sacamos el startMoment y el finishMoment de cada timetable, lo
		// convertimos a tipo calendar y creamos los intervalos cada 10 minutos:

		List<Date> freeSlots = new ArrayList<Date>();
		
		Calendar cal = new GregorianCalendar();
		cal.setLenient(false);
		cal.setTime(fechaElegida);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.getTime();
		
		//miramos si el dia elegido es uno que esta contenido en algun freeDay de ese especialista.
		for(FreeDay f : specialist.getFreeDays()){
			
			Date startShift = f.getStartMoment();
			Date endShift = f.getFinishMoment();

			
			//si esta contenida la fecha elegida en un freeDay devuelve la lista de freeSlots para ese dia vacia
			if(cal.getTime().after(startShift) && fechaElegida.before(endShift) ){
				
				return freeSlots;
			
			}
			
		}	
		

		Calendar actualMoment = new GregorianCalendar();

		for (Timetable t : timetablesParaEseDiaDeLaSemana) {

			Date startShift = t.getStartShift();
			Date endShift = t.getEndShift();

			Calendar startShiftCalendar = new GregorianCalendar();
			Calendar endShiftCalendar = new GregorianCalendar();

			startShiftCalendar.setTime(startShift);
			endShiftCalendar.setTime(endShift);

			// cambiamos la fecha que nos da del 1/1/1970 por la seleccionada
			// por el patient:

			startShiftCalendar.set(Calendar.DAY_OF_MONTH,
					starCalendar.get(Calendar.DAY_OF_MONTH));
			startShiftCalendar.set(Calendar.MONTH,
					starCalendar.get(Calendar.MONTH));
			startShiftCalendar.set(Calendar.YEAR,
					starCalendar.get(Calendar.YEAR));

			endShiftCalendar.set(Calendar.DAY_OF_MONTH,
					starCalendar.get(Calendar.DAY_OF_MONTH));
			endShiftCalendar.set(Calendar.MONTH,
					starCalendar.get(Calendar.MONTH));
			endShiftCalendar
					.set(Calendar.YEAR, starCalendar.get(Calendar.YEAR));

			// si la cita es para el dia de hoy comprobamos la hora actual para
			// no crear citas pasadas.
			if (actualMoment.after(startShiftCalendar)
					&& actualMoment.after(endShiftCalendar)) {
				startShiftCalendar = actualMoment;

			} else if (actualMoment.after(startShiftCalendar)) {
				startShiftCalendar = actualMoment;

				while (startShiftCalendar.before(endShiftCalendar)) {
					startShiftCalendar.add(Calendar.MINUTE, 10); // Añadimos 10
																	// minutos
					freeSlots.add(startShiftCalendar.getTime());
				}

			} else {

				freeSlots.add(startShiftCalendar.getTime());
				while (startShiftCalendar.before(endShiftCalendar)) {
					startShiftCalendar.add(Calendar.MINUTE, 10); // Añadimos 10
																	// minutos
					freeSlots.add(startShiftCalendar.getTime());
				}
			}

		}

		// 3º quitamos los slots ocupados: (primero vamos a quitar los appointment)

		List<Date> ocupadosSlots = new ArrayList<Date>();

		for (Timetable t : timetablesParaEseDiaDeLaSemana) {
			for (Appointment a : t.getAppointments()) {
				for (Date d : freeSlots) {

					Calendar fecha = new GregorianCalendar();
					Calendar fecha2 = new GregorianCalendar();

					fecha.setTime(a.getStartMoment());
					fecha2.setTime(d);

					if (fecha.equals(fecha2)) {
						ocupadosSlots.add(fecha2.getTime());
					}
				}
			}
		}
		
		freeSlots.removeAll(ocupadosSlots);
		


		return freeSlots;
	}

	
	


	public List<Timetable> getTimetablesForDayOfWeekAndSpecialist( int diaDeLaSemana, int id) {

		List<Timetable> timetablesParaEseDiaDeLaSemana = new ArrayList<Timetable>();
		timetablesParaEseDiaDeLaSemana = timetableRepository.getTimetablesForDayOfWeekAndSpecialist(diaDeLaSemana, id);
		
		return timetablesParaEseDiaDeLaSemana;
	}



	public void delete2(Collection<Timetable> timetables) {
		
		timetableRepository.delete(timetables);
		
	}



	public Timetable recontructor(TimetableForm timetableForm) {
		Timetable result = create();
		result.setId(timetableForm.getId());
		result.setVersion(timetableForm.getVersion());
		result.setDay(timetableForm.getDay());
		result.setEndShift(timetableForm.getEndShift());
		result.setStartShift(timetableForm.getStartShift());
		
		return result;
	}



	public Timetable create() {
		Specialist specialistConnect = specialistService.findByPrincipal();
		Collection<Appointment> appointments = new HashSet<Appointment>();
		Timetable result = new Timetable();
		result.setSpecialist(specialistConnect);
		result.setAppointments(appointments);
		
		return result;
	}



	public void save(Timetable timetable) {
		Assert.notNull(timetable);
		Calendar start = new GregorianCalendar();
		start.setTime(timetable.getStartShift());
		Calendar finish = new GregorianCalendar();
		finish.setTime(timetable.getEndShift());
		Assert.isTrue(start.before(finish));
		timetableRepository.save(timetable);
		
	}
}
