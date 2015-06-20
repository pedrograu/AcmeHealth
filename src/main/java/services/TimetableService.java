package services;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
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

    @Autowired
    private AppointmentService appointmentService;

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

    public List<Date> getDatesAvailables(Offer offer) {

        //miramos si es para tu medico de cabecera o para un especialista de una oferta
        Specialist specialist;
        if (offer == null) {
            Patient patientConnect = patientService.findByPrincipal();
            specialist = patientConnect.getSpecialist();
        } else {
            specialist = offer.getSpecialist();
        }

        Date fechaDeHoy = new Date();
        Calendar starCalendar = new GregorianCalendar();
        starCalendar.setTime(fechaDeHoy);

        List<Date> freeSlotsComplete = new ArrayList<Date>();

        //iteramos 7 veces, uno por cada dia de la semana desde la fecha de hoy.
        mainLoop: //para que el break salga hasta aqui
        for (int i = 0; i < 7; i++) {

            List<Date> freeSlots = new ArrayList<Date>();

            //miramos si el dia elegido es uno que esta contenido en algun freeDay de ese especialista, en ese caso no hay citas disponibles para dicho dia.
            for (FreeDay f : specialist.getFreeDays()) {

                Date startShift = f.getStartMoment();
                Date endShift = f.getFinishMoment();

                //si esta contenida la fecha elegida en un freeDay devuelve la lista de freeSlots para ese dia vacia
                if ((fechaDeHoy.after(startShift) || fechaDeHoy.equals(startShift))
                        && (fechaDeHoy.before(endShift) || fechaDeHoy.equals(endShift))) {

                    break mainLoop;

                }

            }

            //dia de la semana al que pertenece la fecha elegida.
            int diaDeLaSemana = starCalendar.get(Calendar.DAY_OF_WEEK);

            List<Timetable> timetablesParaEseDiaDeLaSemana = new ArrayList<Timetable>();
            timetablesParaEseDiaDeLaSemana = timetableRepository.getTimetablesForDayOfWeekAndSpecialist(diaDeLaSemana,
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

                startShiftCalendar.set(Calendar.DAY_OF_MONTH, starCalendar.get(Calendar.DAY_OF_MONTH));
                startShiftCalendar.set(Calendar.MONTH, starCalendar.get(Calendar.MONTH));
                startShiftCalendar.set(Calendar.YEAR, starCalendar.get(Calendar.YEAR));

                endShiftCalendar.set(Calendar.DAY_OF_MONTH, starCalendar.get(Calendar.DAY_OF_MONTH));
                endShiftCalendar.set(Calendar.MONTH, starCalendar.get(Calendar.MONTH));
                endShiftCalendar.set(Calendar.YEAR, starCalendar.get(Calendar.YEAR));

                // si la cita es para el dia de hoy comprobamos la hora actual para
                // no crear citas pasadas.

                boolean mismoDia = false;
                if ((starCalendar.get(Calendar.DAY_OF_MONTH) == actualMoment.get(Calendar.DAY_OF_MONTH))
                        && (starCalendar.get(Calendar.MONTH) == actualMoment.get(Calendar.MONTH))
                        && (starCalendar.get(Calendar.YEAR) == actualMoment.get(Calendar.YEAR))) {
                    mismoDia = true;
                }

                //recogemos la hora actual:
                Calendar calendario = new GregorianCalendar();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minutos = calendario.get(Calendar.MINUTE);
                Date horaActual = new Time(hora, minutos, 0);

                if (horaActual.after(t.getEndShift()) && mismoDia) { //si la hora actual es mayor que la de ese timetable y fecha de la cita igual a la de hoy
                    continue;

                } else if (horaActual.after(t.getStartShift()) && horaActual.before(t.getEndShift()) && mismoDia) {//si la hora actual está contenida en ese 
                    // timetable y fecha de la cita igual a la de hoy
                    List<Date> freeSlotsProvisionales = new ArrayList<Date>();

                    Calendar startShiftCalendarProvisional = (Calendar) startShiftCalendar.clone();
                    Calendar endShiftCalendarProvisional = (Calendar) endShiftCalendar.clone();

                    freeSlotsProvisionales.add(startShiftCalendarProvisional.getTime());
                    while (startShiftCalendarProvisional.before(endShiftCalendarProvisional)) {
                        startShiftCalendarProvisional.add(Calendar.MINUTE, 10); // Añadimos 10
                                                                                // minutos
                        freeSlotsProvisionales.add(startShiftCalendarProvisional.getTime());
                    }

                    Iterator<Date> iter = freeSlotsProvisionales.iterator();

                    while (iter.hasNext()) {

                        Date elemento = iter.next();

                        if (actualMoment.getTime().equals(elemento) || actualMoment.getTime().after(elemento)) {
                            iter.remove();
                        } else {
                            break;
                        }
                    }

                    freeSlots.addAll(freeSlotsProvisionales);

                } else if (horaActual.before(t.getStartShift()) && horaActual.before(t.getEndShift()) && mismoDia) {//hora actual menor que timetable y mismo dia

                    List<Date> freeSlotsProvisionales = new ArrayList<Date>();

                    Calendar startShiftCalendarProvisional = (Calendar) startShiftCalendar.clone();
                    Calendar endShiftCalendarProvisional = (Calendar) endShiftCalendar.clone();

                    freeSlotsProvisionales.add(startShiftCalendarProvisional.getTime());
                    while (startShiftCalendarProvisional.before(endShiftCalendarProvisional)) {
                        startShiftCalendarProvisional.add(Calendar.MINUTE, 10); // Añadimos 10
                                                                                // minutos
                        freeSlotsProvisionales.add(startShiftCalendarProvisional.getTime());
                    }

                    freeSlots.addAll(freeSlotsProvisionales);

                } else if (!mismoDia) {//si la cita es para dia distinto al de hoy

                    freeSlots.add(startShiftCalendar.getTime());
                    while (startShiftCalendar.before(endShiftCalendar)) {
                        startShiftCalendar.add(Calendar.MINUTE, 10); // Añadimos 10
                                                                     // minutos
                        freeSlots.add(startShiftCalendar.getTime());
                    }
                } else {
                    //No debería entrar en aqui.
                    break mainLoop;
                }

            }

            // 3º quitamos los slots ocupados

            List<Date> ocupadosSlots = new ArrayList<Date>();

            List<Appointment> ap = (List<Appointment>) appointmentsForDay(specialist, fechaDeHoy);

            for (Appointment a : ap) {
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

            freeSlots.removeAll(ocupadosSlots);

            freeSlotsComplete.addAll(freeSlots);

            //avanzamos un dia:
            Calendar c = Calendar.getInstance();
            c.setTime(fechaDeHoy);
            c.add(Calendar.DATE, 1);
            fechaDeHoy = c.getTime();
            starCalendar.setTime(fechaDeHoy);

        }

        return freeSlotsComplete;
    }

    public String convertListToStringJson(List<Date> listaDeFechas, String tipo, int offerId, int patientId , int specialistId) {

        //List<Date> listaDeFechas = getDatesAvailables(new Date(), null);

        String cadena = "[";
        int i = 0;
        for (Date d : listaDeFechas) {

            //convertiendo de date a string (1º forma):
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String dateString = df.format(d);

            //convertiendo de date a string (2º forma):
            DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            String dateString2 = df2.format(d);

            //añadir 10 min para obtener fecha de fin y convertiendo a string:
            Calendar cal = new GregorianCalendar();
            cal.setLenient(false);
            cal.setTime(d);
            cal.add(Calendar.MINUTE, 10);
            Date datePlusTeenMinutes = cal.getTime();
            DateFormat df3 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            String dateString3 = df3.format(datePlusTeenMinutes);

            if (tipo == "calendar") {

                if (i == 0) {
                    cadena += "{\"title\" : \"disponible\",\"start\" : \"" + dateString2 + "\",\"end\" :   \""
                            + dateString3 + "\",\"url\" : \"appointment/patient/create.do?startMoment=" + dateString;
                } else {
                    cadena += ",{\"title\" : \"disponible\",\"start\" : \"" + dateString2 + "\",\"end\" :   \""
                            + dateString3 + "\",\"url\" : \"appointment/patient/create.do?startMoment=" + dateString;
                }

            } else if (tipo == "calendar2") {

                if (i == 0) {
                    cadena += "{\"title\" : \"disponible\",\"start\" : \"" + dateString2 + "\",\"end\" :   \""
                            + dateString3 + "\",\"url\" : \"appointment/patient/create2.do?startMoment=" + dateString+"&offerId="+offerId;
                } else {
                    cadena += ",{\"title\" : \"disponible\",\"start\" : \"" + dateString2 + "\",\"end\" :   \""
                            + dateString3 + "\",\"url\" : \"appointment/patient/create2.do?startMoment=" + dateString+"&offerId="+offerId;
                }

            } else {

                if (i == 0) {
                    cadena += "{\"title\" : \"disponible\",\"start\" : \"" + dateString2 + "\",\"end\" :   \""
                            + dateString3 + "\",\"url\" : \"appointment/specialist/create.do?startMoment=" + dateString+"&patientId="+patientId+"&specialistId="+specialistId;
                } else {
                    cadena += ",{\"title\" : \"disponible\",\"start\" : \"" + dateString2 + "\",\"end\" :   \""
                            + dateString3 + "\",\"url\" : \"appointment/specialist/create.do?startMoment=" + dateString+"&patientId="+patientId+"&specialistId="+specialistId;
                }

            }

            cadena += "\"}";
            i++;
        }
        cadena += "]";

        return cadena;
    }

    public Collection<Appointment> appointmentsForDay(Specialist s, Date day) {

        Collection<Appointment> aps = appointmentService.getAppointmentforOneSpecialistAndDay(s.getId(), day);

        return aps;
    }

    public List<Date> getDatesAvailables2(Specialist specialist) {

        Date fechaDeHoy = new Date();
        Calendar starCalendar = new GregorianCalendar();
        starCalendar.setTime(fechaDeHoy);

        List<Date> freeSlotsComplete = new ArrayList<Date>();

        //iteramos 49 veces, uno por cada dia de la semana desde la fecha de hoy.(ofrece citas para los siguientes 2 meses)
        mainLoop: //para que el break salga hasta aqui
        for (int i = 0; i < 56; i++) {

            List<Date> freeSlots = new ArrayList<Date>();

            //miramos si el dia elegido es uno que esta contenido en algun freeDay de ese especialista, en ese caso no hay citas disponibles para dicho dia.
            for (FreeDay f : specialist.getFreeDays()) {

                Date startShift = f.getStartMoment();
                Date endShift = f.getFinishMoment();

                //si esta contenida la fecha elegida en un freeDay devuelve la lista de freeSlots para ese dia vacia
                if ((fechaDeHoy.after(startShift) || fechaDeHoy.equals(startShift))
                        && (fechaDeHoy.before(endShift) || fechaDeHoy.equals(endShift))) {

                    break mainLoop;

                }

            }

            //dia de la semana al que pertenece la fecha elegida.
            int diaDeLaSemana = starCalendar.get(Calendar.DAY_OF_WEEK);

            List<Timetable> timetablesParaEseDiaDeLaSemana = new ArrayList<Timetable>();
            timetablesParaEseDiaDeLaSemana = timetableRepository.getTimetablesForDayOfWeekAndSpecialist(diaDeLaSemana,
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

                startShiftCalendar.set(Calendar.DAY_OF_MONTH, starCalendar.get(Calendar.DAY_OF_MONTH));
                startShiftCalendar.set(Calendar.MONTH, starCalendar.get(Calendar.MONTH));
                startShiftCalendar.set(Calendar.YEAR, starCalendar.get(Calendar.YEAR));

                endShiftCalendar.set(Calendar.DAY_OF_MONTH, starCalendar.get(Calendar.DAY_OF_MONTH));
                endShiftCalendar.set(Calendar.MONTH, starCalendar.get(Calendar.MONTH));
                endShiftCalendar.set(Calendar.YEAR, starCalendar.get(Calendar.YEAR));

                // si la cita es para el dia de hoy comprobamos la hora actual para
                // no crear citas pasadas.

                boolean mismoDia = false;
                if ((starCalendar.get(Calendar.DAY_OF_MONTH) == actualMoment.get(Calendar.DAY_OF_MONTH))
                        && (starCalendar.get(Calendar.MONTH) == actualMoment.get(Calendar.MONTH))
                        && (starCalendar.get(Calendar.YEAR) == actualMoment.get(Calendar.YEAR))) {
                    mismoDia = true;
                }

                //recogemos la hora actual:
                Calendar calendario = new GregorianCalendar();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minutos = calendario.get(Calendar.MINUTE);
                Date horaActual = new Time(hora, minutos, 0);

                if (horaActual.after(t.getEndShift()) && mismoDia) { //si la hora actual es mayor que la de ese timetable y fecha de la cita igual a la de hoy
                    continue;

                } else if (horaActual.after(t.getStartShift()) && horaActual.before(t.getEndShift()) && mismoDia) {//si la hora actual está contenida en ese 
                    // timetable y fecha de la cita igual a la de hoy
                    List<Date> freeSlotsProvisionales = new ArrayList<Date>();

                    Calendar startShiftCalendarProvisional = (Calendar) startShiftCalendar.clone();
                    Calendar endShiftCalendarProvisional = (Calendar) endShiftCalendar.clone();

                    freeSlotsProvisionales.add(startShiftCalendarProvisional.getTime());
                    while (startShiftCalendarProvisional.before(endShiftCalendarProvisional)) {
                        startShiftCalendarProvisional.add(Calendar.MINUTE, 10); // Añadimos 10
                                                                                // minutos
                        freeSlotsProvisionales.add(startShiftCalendarProvisional.getTime());
                    }

                    Iterator<Date> iter = freeSlotsProvisionales.iterator();

                    while (iter.hasNext()) {

                        Date elemento = iter.next();

                        if (actualMoment.getTime().equals(elemento) || actualMoment.getTime().after(elemento)) {
                            iter.remove();
                        } else {
                            break;
                        }
                    }

                    freeSlots.addAll(freeSlotsProvisionales);

                } else if (horaActual.before(t.getStartShift()) && horaActual.before(t.getEndShift()) && mismoDia) {//hora actual menor que timetable y mismo dia

                    List<Date> freeSlotsProvisionales = new ArrayList<Date>();

                    Calendar startShiftCalendarProvisional = (Calendar) startShiftCalendar.clone();
                    Calendar endShiftCalendarProvisional = (Calendar) endShiftCalendar.clone();

                    freeSlotsProvisionales.add(startShiftCalendarProvisional.getTime());
                    while (startShiftCalendarProvisional.before(endShiftCalendarProvisional)) {
                        startShiftCalendarProvisional.add(Calendar.MINUTE, 10); // Añadimos 10
                                                                                // minutos
                        freeSlotsProvisionales.add(startShiftCalendarProvisional.getTime());
                    }

                    freeSlots.addAll(freeSlotsProvisionales);

                } else if (!mismoDia) {//si la cita es para dia distinto al de hoy

                    freeSlots.add(startShiftCalendar.getTime());
                    while (startShiftCalendar.before(endShiftCalendar)) {
                        startShiftCalendar.add(Calendar.MINUTE, 10); // Añadimos 10
                                                                     // minutos
                        freeSlots.add(startShiftCalendar.getTime());
                    }
                } else {
                    //No debería entrar en aqui.
                    break mainLoop;
                }

            }

            // 3º quitamos los slots ocupados

            List<Date> ocupadosSlots = new ArrayList<Date>();

            List<Appointment> ap = (List<Appointment>) appointmentsForDay(specialist, fechaDeHoy);

            for (Appointment a : ap) {
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

            freeSlots.removeAll(ocupadosSlots);

            freeSlotsComplete.addAll(freeSlots);

            //avanzamos un dia:
            Calendar c = Calendar.getInstance();
            c.setTime(fechaDeHoy);
            c.add(Calendar.DATE, 1);
            fechaDeHoy = c.getTime();
            starCalendar.setTime(fechaDeHoy);

        }

        return freeSlotsComplete;
    }

    public List<Timetable> getTimetablesForDayOfWeekAndSpecialist(int diaDeLaSemana, int id) {

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
        Timetable result = new Timetable();
        result.setSpecialist(specialistConnect);

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
