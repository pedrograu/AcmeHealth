package controllers.Specialist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AppointmentService;
import services.MessageService;
import services.PatientService;
import services.SpecialistService;
import services.TimetableService;
import controllers.AbstractController;
import domain.Appointment;
import domain.Message;
import domain.Patient;
import domain.Specialist;
import forms.AppointmentForm2;
import forms.AppointmentForm3;

@Controller
@RequestMapping("/appointment/specialist")
public class AppointmentSpecialistController extends AbstractController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private MessageService messageService;

    public AppointmentSpecialistController() {
        super();
    }

    // Listing.....................

    // citas finalizadas
    @RequestMapping(value = "/listFinish", method = RequestMethod.GET)
    public ModelAndView listFinish() {

        ModelAndView result;
        Collection<Appointment> appointments;

        appointments = appointmentService.getAppointmentsFinish();

        result = new ModelAndView("appointment/list");
        result.addObject("appointments", appointments);
        result.addObject("requestURI", "appointment/specialist/listFinish.do");

        return result;
    }

    // citas pendientes
    @RequestMapping(value = "/listNotFinish", method = RequestMethod.GET)
    public ModelAndView listNotFinish() {

        ModelAndView result;
        Collection<Appointment> appointments;

        appointments = appointmentService.getAppointmentsNotFinish();

        result = new ModelAndView("appointment/list");
        result.addObject("appointments", appointments);
        result.addObject("isNotFinish", true);
        result.addObject("requestURI", "appointment/specialist/listNotFinish.do");

        return result;
    }

    // Edit.........................................
    
    
    //seleccionar especialista para pedir cita con él
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public ModelAndView select(@RequestParam int patientId) {

        ModelAndView result;
        
        Collection<Specialist> specialists = specialistService.getAllSpecialist();

        result = new ModelAndView("appointment/select");
        result.addObject("specialists", specialists);
        result.addObject("requestURI", "appointment/specialist/calendar.do?patientId=" + patientId);

        return result;
    }

    // crear cita para un paciente
    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    public ModelAndView calendar(@RequestParam int patientId, @RequestParam int specialistId) {

        ModelAndView result;
        
        Specialist specialist = specialistService.findOneToEdit(specialistId);
        List<Date> lista = timetableService.getDatesAvailables2(specialist);
        String eventos = timetableService.convertListToStringJson(lista,"calendarSpecialist", 0, patientId,specialistId );

        result = new ModelAndView("appointment/calendar");
        result.addObject("eventos", eventos);
        result.addObject("requestURI", "appointment/specialist/create.do?patientId=" + patientId+"&specialistId="+specialistId);

        return result;
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public ModelAndView cancel(@RequestParam int appointmentId) {

        ModelAndView result;
        Appointment appointment = appointmentService.findOneToEdit(appointmentId);
        Message message = messageService.create();
        message.setRecipient(appointment.getPatient());
        appointmentService.cancel(appointment);

        result = new ModelAndView("message/edit");
        result.addObject("requestURI", "message/customer/answer.do??messageId=" + message.getId());

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam String startMoment, @RequestParam int patientId, @RequestParam int specialistId) {

        ModelAndView result;

        Specialist specialist = specialistService.findOneToEdit(specialistId);
        Patient patient = patientService.findOneToEdit(patientId);

        boolean cumplePatron = appointmentService.cumplePatron(startMoment);

        if (startMoment != "" & cumplePatron) {
            Date fechaElegida = appointmentService.stringToDate(startMoment);
            List<Date> listaDeFechas = new ArrayList<Date>();
            listaDeFechas.add(fechaElegida);
            
            DateFormat fec = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = fec.format(listaDeFechas.get(0));
            
            DateFormat hor = new SimpleDateFormat("HH:mm");
            String hora = hor.format(listaDeFechas.get(0));

            AppointmentForm3 appointmentForm = new AppointmentForm3();
            appointmentForm.setPatient(patient);
            appointmentForm.setSpecialist(specialist);

            result = createEditModelAndView2(appointmentForm);

            result.addObject("appointmentForm", appointmentForm);
            result.addObject("listaDeFechas", listaDeFechas);
            result.addObject("fecha", fecha);
            result.addObject("hora", hora);
            result.addObject("hayHorasDisponibles", true);
            result.addObject("create", false);
            result.addObject("isOffer", false);
            result.addObject("isSpecialist2", true);
            result.addObject("existAppointment", false);

        } else {

            AppointmentForm3 appointmentForm = new AppointmentForm3();
            appointmentForm.setPatient(patient);
            appointmentForm.setSpecialist(specialist);

            result = createEditModelAndView2(appointmentForm);

            result.addObject("appointmentForm", appointmentForm);
            result.addObject("hayHorasDisponibles", false);
            result.addObject("create", false);
            result.addObject("isOffer", false);
            result.addObject("isSpecialist2", true);
            result.addObject("existAppointment", false);

        }

        return result;

    }

    // Atender cita
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int appointmentId) {

        ModelAndView result;
        Appointment appointment = appointmentService.findOneToEdit(appointmentId);
        AppointmentForm2 appointmentForm = appointmentService.createForm2(appointment);

        result = new ModelAndView("appointment/edit");
        result.addObject("appointment", appointment);
        result.addObject("appointmentForm", appointmentForm);
        result.addObject("isSpecialist", true);
        result.addObject("requestURI", "appointment/specialist/edit.do?appointmentId=" + appointment.getId());

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid AppointmentForm2 appointmentForm, BindingResult binding) {

        ModelAndView result;
        Specialist specialistConnect = specialistService.findByPrincipal();

        if (binding.hasErrors()) {
            result = createEditModelAndView(appointmentForm);
        } else {
            try {

                Appointment appointment = appointmentService.recontructor2(appointmentForm);
                appointment.setSpecialist(specialistConnect);
                Appointment appointment2 = appointmentService.save3(appointment);
                result = new ModelAndView("redirect:edit.do?appointmentId=" + appointment2.getId());

            } catch (Throwable oops) {
                result = createEditModelAndView(appointmentForm, "appointment.commit.error");
            }
        }

        return result;

    }

    @RequestMapping(value = "/edit2", method = RequestMethod.POST, params = "save2")
    public ModelAndView save2(@Valid AppointmentForm3 appointmentForm, BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors()) {
            result = createEditModelAndView2(appointmentForm);
        } else {
            try {

                Appointment appointment = appointmentService.recontructor3(appointmentForm);
                appointmentService.save2(appointment);
                result = new ModelAndView("redirect:../../customerArea/specialist/list.do");

            } catch (Throwable oops) {
                result = createEditModelAndView2(appointmentForm, "appointment.commit.error");
            }
        }

        return result;

    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(AppointmentForm2 appointmentForm) {
        assert appointmentForm != null;
        ModelAndView result;

        result = createEditModelAndView(appointmentForm, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(AppointmentForm2 appointmentForm, String message) {

        Assert.notNull(appointmentForm);
        ModelAndView result;

        result = new ModelAndView("appointment/edit");

        result.addObject("appointmentForm", appointmentForm);
        result.addObject("isSpecialist", true);
        result.addObject("requestURI", "appointment/specialist/edit.do?appointmentId=" + appointmentForm.getId());
        result.addObject("message", message);

        return result;
    }

    protected ModelAndView createEditModelAndView2(AppointmentForm3 appointmentForm) {
        assert appointmentForm != null;
        ModelAndView result;

        result = createEditModelAndView2(appointmentForm, null);

        return result;
    }

    protected ModelAndView createEditModelAndView2(AppointmentForm3 appointmentForm, String message) {

        Assert.notNull(appointmentForm);
        ModelAndView result;

        //Appointment appointment = appointmentService.recontructor3(appointmentForm);

        result = new ModelAndView("appointment/edit");

        result.addObject("appointmentForm", appointmentForm);

        result.addObject("existAppointment", false);
        //result.addObject("requestURI", "appointment/patient/edit.do?appointmentId=" + appointment.getId());
        result.addObject("create", false);
        result.addObject("hayHorasDisponibles", true);

        result.addObject("isSpecialist2", true);
        result.addObject("requestURI", "appointment/specialist/edit2.do?appointmentId=" + appointmentForm.getId());
        result.addObject("message", message);

        return result;
    }

}
