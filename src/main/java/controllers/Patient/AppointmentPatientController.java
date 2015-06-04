package controllers.Patient;

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
import services.OfferService;
import services.PatientService;
import services.SpecialistService;
import services.TimetableService;
import controllers.AbstractController;
import domain.Appointment;
import domain.Offer;
import domain.Patient;
import forms.AppointmentForm;

@Controller
@RequestMapping("/appointment/patient")
public class AppointmentPatientController extends AbstractController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private SpecialistService specialistService;

    public AppointmentPatientController() {
        super();
    }

    // Listing.....................

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {

        ModelAndView result;
        Collection<Appointment> appointments;

        appointments = appointmentService.getMyScheduledAppointments();

        result = new ModelAndView("appointment/list");
        result.addObject("appointments", appointments);
        result.addObject("requestURI", "appointment/patient/list.do");

        return result;
    }

    // Edition ----------------------------------------------------------------

    //calendario (citas para medico de cabecera)
    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    public ModelAndView calendar() {

        ModelAndView result;

        result = new ModelAndView("appointment/calendar");
        result.addObject("isPatient", true);
        result.addObject("requestURI", "appointment/patient/create.do");

        return result;
    }

    //calendario (citas para oferta)
    @RequestMapping(value = "/calendar2", method = RequestMethod.GET)
    public ModelAndView calendar2(@RequestParam int offerId) {

        ModelAndView result;

        result = new ModelAndView("appointment/calendar");
        result.addObject("isPatient", true);
        result.addObject("requestURI", "appointment/patient/create2.do?offerId=" + offerId);

        return result;
    }

    //citas para tu medico de cabecera
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam String startMoment) {

        ModelAndView result;

        //Appointment appointment;

        Patient patientConnect = patientService.findByPrincipal();

        boolean cumplePatron = appointmentService.cumplePatron(startMoment);

        if (startMoment != "" && cumplePatron && patientConnect.getSpecialist() != null) {
            Date fechaElegida = appointmentService.stringToDate(startMoment);
            List<Date> listaDeFechas = timetableService.getDatesAvailables(fechaElegida, null);

            //appointment = appointmentService.create();
            AppointmentForm appointmentForm = new AppointmentForm();

            result = createEditModelAndView(appointmentForm);

            result.addObject("appointmentForm", appointmentForm);
            result.addObject("listaDeFechas", listaDeFechas);
            if (listaDeFechas.isEmpty()) {
                result.addObject("hayHorasDisponibles", false);
            } else {
                result.addObject("hayHorasDisponibles", true);
            }
            result.addObject("create", false);
            result.addObject("isOffer", false);
            result.addObject("isSpecialist", false);
            result.addObject("existAppointment", false);

        } else {

            //appointment = appointmentService.create();
            AppointmentForm appointmentForm = new AppointmentForm();

            result = createEditModelAndView(appointmentForm);

            result.addObject("appointmentForm", appointmentForm);
            result.addObject("hayHorasDisponibles", false);
            result.addObject("create", false);
            result.addObject("isOffer", false);
            result.addObject("isSpecialist", false);
            result.addObject("existAppointment", false);

        }

        return result;

    }

    //citas para una oferta
    @RequestMapping(value = "/create2", method = RequestMethod.GET)
    public ModelAndView create2(@RequestParam String startMoment, @RequestParam int offerId) {

        Offer offer = offerService.findOneToEdit(offerId);

        ModelAndView result;

        boolean cumplePatron = appointmentService.cumplePatron(startMoment);

        if (startMoment != "" && cumplePatron) {
            Date fechaElegida = appointmentService.stringToDate(startMoment);
            List<Date> listaDeFechas = timetableService.getDatesAvailables(fechaElegida, offer);

            //appointment = appointmentService.create2(offer);
            AppointmentForm appointmentForm = new AppointmentForm();
            appointmentForm.setOffer(offer);

            result = createEditModelAndView(appointmentForm);

            //controlar que si ya existe un appointment para esa oferta no puedas volver a pedir otra.
            Appointment appointment2 = appointmentService.getAppointmentForPatientAndOffer(offer);
            if (appointment2 != null) {
                result.addObject("existAppointment", true);
            } else {
                result.addObject("existAppointment", false);
            }
            result.addObject("appointmentForm", appointmentForm);
            result.addObject("listaDeFechas", listaDeFechas);
            if (listaDeFechas.isEmpty()) {
                result.addObject("hayHorasDisponibles", false);
            } else {
                result.addObject("hayHorasDisponibles", true);
            }
            result.addObject("create", false);
            result.addObject("isOffer", true);
            result.addObject("isSpecialist", false);

        } else {

            //appointment = appointmentService.create2(offer);
            AppointmentForm appointmentForm = new AppointmentForm();
            appointmentForm.setOffer(offer);

            result = createEditModelAndView(appointmentForm);

            result.addObject("appointmentForm", appointmentForm);
            result.addObject("hayHorasDisponibles", false);
            result.addObject("create", false);
            result.addObject("isOffer", true);
            result.addObject("isSpecialist", false);
            result.addObject("existAppointment", false);
            result.addObject("requestURI", "appointment/patient/edit.do?offerId=" + offer.getId());

        }

        return result;

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save2")
    public ModelAndView save2(@Valid AppointmentForm appointmentForm, BindingResult binding) {

        ModelAndView result;
        Appointment appointment;
        Patient patientConnect = patientService.findByPrincipal();

        if (binding.hasErrors()) {
            result = createEditModelAndView2(appointmentForm);
        } else {
            try {
                boolean res;
                appointment = appointmentService.recontructor(appointmentForm);
                if (appointment.getOffer() != null) {
                    appointment.setSpecialist(appointment.getOffer().getSpecialist());
                    //Controlar que la cita esta dentro del periodo de la oferta
                    res = offerService.appointmentValid(appointment, appointment.getOffer());
                } else {
                    appointment.setSpecialist(patientConnect.getSpecialist());
                    res = true;
                }
                //para controlar concurrencia
                Appointment appointment2 = appointmentService.getAppointmentForStartMoment(
                        appointment.getStartMoment(), appointment.getSpecialist());
                if (appointment2 == null && res == true) {
                    appointmentService.save2(appointment);
                    result = new ModelAndView("redirect:list.do");
                } else {
                    result = createEditModelAndView2(appointmentForm, "appointment.commit.error");
                }

            } catch (Throwable oops) {
                result = createEditModelAndView2(appointmentForm, "appointment.commit.error");
            }
        }

        return result;

    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(AppointmentForm appointmentForm) {
        assert appointmentForm != null;
        ModelAndView result;

        result = createEditModelAndView(appointmentForm, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(AppointmentForm appointmentForm, String message) {

        Assert.notNull(appointmentForm);
        ModelAndView result;
        Appointment appointment = appointmentService.recontructor(appointmentForm);

        result = new ModelAndView("appointment/edit");

        if (appointment.getOffer() == null) {
            result.addObject("existAppointment", false);
        } else {

            Appointment appointment2 = appointmentService.getAppointmentForPatientAndOffer(appointment.getOffer());
            if (appointment2 != null) {
                result.addObject("existAppointment", true);
            } else {
                result.addObject("existAppointment", false);
            }
        }
        result.addObject("appointmentForm", appointmentForm);
        result.addObject("isSpecialist", false);
        result.addObject("requestURI", "appointment/patient/edit.do?appointmentId=" + appointment.getId());
        result.addObject("create", true);

        result.addObject("message", message);

        return result;
    }

    protected ModelAndView createEditModelAndView2(AppointmentForm appointmentForm) {
        assert appointmentForm != null;
        ModelAndView result;

        result = createEditModelAndView2(appointmentForm, null);

        return result;
    }

    protected ModelAndView createEditModelAndView2(AppointmentForm appointmentForm, String message) {

        Assert.notNull(appointmentForm);
        ModelAndView result;

        Appointment appointment = appointmentService.recontructor(appointmentForm);

        result = new ModelAndView("appointment/edit");

        result.addObject("appointmentForm", appointmentForm);
        result.addObject("isSpecialist", false);
        result.addObject("existAppointment", false);
        result.addObject("requestURI", "appointment/patient/edit.do?appointmentId=" + appointment.getId());
        result.addObject("create", false);

        List<Date> listaDeFechas;
        if (appointment.getOffer() != null) {
            result.addObject("isOffer", true);
            listaDeFechas = timetableService.getDatesAvailables(appointment.getStartMoment(), appointment.getOffer());
        } else {
            result.addObject("isOffer", false);
            listaDeFechas = timetableService.getDatesAvailables(appointment.getStartMoment(), null);
        }
        if (listaDeFechas.isEmpty()) {
            result.addObject("hayHorasDisponibles", false);
        } else {
            result.addObject("hayHorasDisponibles", true);
        }
        result.addObject("listaDeFechas", listaDeFechas);
        result.addObject("message", message);

        return result;
    }

}