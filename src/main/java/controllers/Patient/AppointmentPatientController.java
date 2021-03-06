package controllers.Patient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import services.TimetableService;
import controllers.AbstractController;
import domain.Appointment;
import domain.Offer;
import domain.Patient;
import forms.AppointmentForm;

@Controller
@RequestMapping("/appointment/patient")
public class AppointmentPatientController extends AbstractController {
    
	//Services.......................................

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private PatientService patientService;

    //Constructors......................................
    
    public AppointmentPatientController() {
        super();
    }

    // Listing.....................

    //Lista las citas pendientes que tiene el paciente 
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

    //Muestra el calendario (citas para medico de cabecera)
    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    public ModelAndView calendar() {

        ModelAndView result;
        
        Patient patientConnect = patientService.findByPrincipal();
        result = new ModelAndView("appointment/calendar");
        
        if(patientConnect.getSpecialist()!=null){
        	
            List<Date> lista = timetableService.getDatesAvailables(null);
            String eventos = timetableService.convertListToStringJson(lista,"calendar", 0,0,0);
                   
            result.addObject("eventos", eventos);
            result.addObject("requestURI", "appointment/patient/create.do");
        	
        }else{
        	
        	result.addObject("mensaje", true);
        }
          
        return result;
    }

    //Muestra el calendario (citas para tratamiento en oferta)
    @RequestMapping(value = "/calendar2", method = RequestMethod.GET)
    public ModelAndView calendar2(@RequestParam int offerId) {

        ModelAndView result;
        
        Offer offer = offerService.findOneToEdit(offerId);
        
        Date currentMoment = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentMoment);
        c.add(Calendar.DATE, 7);
        currentMoment = c.getTime();
        
        result = new ModelAndView("appointment/calendar");

            
        if(offer.getStartMoment().after(currentMoment)){
            
            result.addObject("error",true);
            
        }else{
            List<Date> lista = timetableService.getDatesAvailables(offer);
            String eventos = timetableService.convertListToStringJson(lista,"calendar2", offerId,0,0);

            result.addObject("eventos", eventos);
            result.addObject("requestURI", "appointment/patient/create2.do?offerId=" + offerId);
        }
        


        return result;
    }

    //Crea una nueva cita para tu medico de cabecera dada la fecha
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam String startMoment) {

        ModelAndView result;

        Patient patientConnect = patientService.findByPrincipal();

        boolean cumplePatron = appointmentService.cumplePatron(startMoment);
        
        if (startMoment != "" && patientConnect.getSpecialist() != null & cumplePatron) {
            
            
            Date fechaElegida = appointmentService.stringToDate(startMoment);
            List<Date> listaDeFechas = new ArrayList<Date>();
            listaDeFechas.add(fechaElegida);
            
            DateFormat fec = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = fec.format(listaDeFechas.get(0));
            
            DateFormat hor = new SimpleDateFormat("HH:mm");
            String hora = hor.format(listaDeFechas.get(0));

            AppointmentForm appointmentForm = new AppointmentForm();

            result = createEditModelAndView(appointmentForm);

            result.addObject("appointmentForm", appointmentForm);
            result.addObject("listaDeFechas", listaDeFechas);
            result.addObject("fecha", fecha);
            result.addObject("hora", hora);
            result.addObject("hayHorasDisponibles", true);
            result.addObject("create", false);
            result.addObject("isOffer", false);
            result.addObject("isSpecialist", false);
            result.addObject("existAppointment", false);

        } else {

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

    //Crea una nueva cita para un tratamiento en oferta dada la fecha y el id de la oferta.
    @RequestMapping(value = "/create2", method = RequestMethod.GET)
    public ModelAndView create2(@RequestParam String startMoment, @RequestParam int offerId) {

        Offer offer = offerService.findOneToEdit(offerId);

        ModelAndView result;

        boolean cumplePatron = appointmentService.cumplePatron(startMoment);

        if (startMoment != "" & cumplePatron) {
            Date fechaElegida = appointmentService.stringToDate(startMoment);
            List<Date> listaDeFechas = new ArrayList<Date>();
            listaDeFechas.add(fechaElegida);
            
            DateFormat fec = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = fec.format(listaDeFechas.get(0));
            
            DateFormat hor = new SimpleDateFormat("HH:mm");
            String hora = hor.format(listaDeFechas.get(0));
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
            result.addObject("fecha", fecha);
            result.addObject("hora", hora);
            result.addObject("hayHorasDisponibles", true);
            result.addObject("create", false);
            result.addObject("isOffer", true);
            result.addObject("isSpecialist", false);

        } else {

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
    
    
    //Guarda en la base de datos una cita
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
                    result = new ModelAndView("redirect:../../customerArea/patient/list.do");
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

        List<Date> listaDeFechas = new ArrayList<Date>();
        listaDeFechas.add(appointment.getStartMoment());
        if (appointment.getOffer() != null) {
            result.addObject("isOffer", true);
        } else {
            result.addObject("isOffer", false);
        }
        result.addObject("listaDeFechas", listaDeFechas);
        result.addObject("message", message);

        return result;
    }

}