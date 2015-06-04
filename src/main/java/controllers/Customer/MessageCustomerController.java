package controllers.Customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AppointmentService;
import services.CustomerService;
import services.MessageService;
import controllers.AbstractController;
import domain.Appointment;
import domain.Customer;
import domain.Message;
import domain.Patient;
import forms.MessageForm;

@Controller
@RequestMapping("/message/customer")
public class MessageCustomerController extends AbstractController {

    // Services-----
    @Autowired
    private MessageService messageService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AppointmentService appointmentService;

    // Constructors -----------------------------------------------------------

    public MessageCustomerController() {
        super();
    }

    // List ------------------------------------------------------------------

    @RequestMapping(value = "/list-inbox", method = RequestMethod.GET)
    public ModelAndView listInbox() {

        ModelAndView result;
        Collection<Message> messages;

        messages = messageService.getMessageInbox();

        result = new ModelAndView("message/list");

        result.addObject("messages", messages);
        result.addObject("inbox", true);
        result.addObject("requestURI", "message/customer/list-inbox.do");

        return result;
    }

    @RequestMapping(value = "/list-outbox", method = RequestMethod.GET)
    public ModelAndView listOutbox() {

        ModelAndView result;
        Collection<Message> messages;

        messages = messageService.getMessageOutbox();

        result = new ModelAndView("message/list");
        result.addObject("messages", messages);
        result.addObject("inbox", false);
        result.addObject("requestURI", "message/customer/list-outbox.do");

        return result;
    }

    // Creation ---------------------------------------------------------------
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;

        MessageForm messageForm = new MessageForm();

        result = createEditModelAndView(messageForm);
        result.addObject("messageForm", messageForm);

        return result;
    }

    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ModelAndView answer(@RequestParam int messageId) {

        Message originalMessage = messageService.findOneToEdit(messageId);

        Customer customer = originalMessage.getSender();
        ModelAndView result;

        MessageForm messageForm = new MessageForm();

        messageForm.setRecipient(customer);
        result = createEditModelAndView(messageForm);
        result.addObject("answer", true);
        result.addObject("cancel", false);
        result.addObject("messageForm", messageForm);
        return result;
    }

    //Cancel an appointment and send message to patient
    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public ModelAndView cancel(@RequestParam int appointmentId) {

        Appointment appointment = appointmentService.findOneToEdit(appointmentId);

        Customer customer = customerService.findOneToEdit(appointment.getPatient().getId());
        ModelAndView result;

        MessageForm messageForm = new MessageForm();

        messageForm.setRecipient(customer);
        result = createEditModelAndView2(messageForm, appointmentId);
        result.addObject("answer", true);
        result.addObject("cancel", true);
        result.addObject("messageForm", messageForm);
        result.addObject("requestURI", "message/customer/edit.do?messageId=" + messageForm.getId() + "&appointmentId="
                + appointmentId);
        return result;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView details(@RequestParam int messageId) {
        ModelAndView result;
        Message messageCustomer = messageService.findOneToEdit(messageId);

        result = new ModelAndView("message/edit");

        result.addObject("messageCustomer", messageCustomer);
        result.addObject("details", true);
        result.addObject("requestURI", "message/customer/details.do?messageId=" + messageCustomer.getId());

        return result;
    }

    // Edition--------------------------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid MessageForm messageForm, BindingResult binding) {
        ModelAndView result;
        Message message;

        if (binding.hasErrors()) {
            result = createEditModelAndView(messageForm);
        } else {
            try {
                message = messageService.recontructor(messageForm);
                messageService.save(message);
                result = new ModelAndView("redirect:list-outbox.do");
            } catch (Throwable oops) {
                result = createEditModelAndView(messageForm, "message.commit.error");
            }
        }

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save2")
    public ModelAndView save2(@Valid MessageForm messageForm, BindingResult binding, @RequestParam int appointmentId) {
        ModelAndView result;
        Message message;
        Appointment appointment = appointmentService.findOneToEdit(appointmentId);

        if (binding.hasErrors()) {
            result = createEditModelAndView2(messageForm, appointmentId);
        } else {
            try {
                message = messageService.recontructor(messageForm);

                messageService.save(message);
                appointmentService.cancel(appointment);

                result = new ModelAndView("redirect:../../appointment/specialist/listNotFinish.do");
            } catch (Throwable oops) {
                result = createEditModelAndView2(messageForm, "message.commit.error", appointmentId);
            }
        }

        return result;
    }

    // Ancillary methods--------------------------------------------------------

    protected ModelAndView createEditModelAndView(MessageForm messageForm) {
        assert messageForm != null;

        ModelAndView result;

        result = createEditModelAndView(messageForm, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(MessageForm messageForm, String message) {
        assert messageForm != null;
        ModelAndView result;

        result = new ModelAndView("message/edit");
        result.addObject("requestURI", "message/customer/edit.do?messageId=" + messageForm.getId());
        result.addObject("messageForm", messageForm);
        result.addObject("message", message);
        Customer customerConnect = customerService.findByPrincipal();

        if (messageForm.getRecipient() == null) {
            Collection<Customer> customers = customerService.findAll();
            if (Patient.class == customerConnect.getClass()) {
                customers = customerService.findOnlySpecialist();

            }

            result.addObject("customers", customers);
            result.addObject("answer", false);
        } else {
            result.addObject("answer", true);
        }
        result.addObject("details", false);
        result.addObject("cancel", false);

        return result;
    }

    protected ModelAndView createEditModelAndView2(MessageForm messageForm, int appointmentId) {
        assert messageForm != null;

        ModelAndView result;

        result = createEditModelAndView2(messageForm, null, appointmentId);

        return result;
    }

    protected ModelAndView createEditModelAndView2(MessageForm messageForm, String message, int appointmentId) {
        assert messageForm != null;
        ModelAndView result;

        result = new ModelAndView("message/edit");
        result.addObject("requestURI", "message/customer/edit.do?messageId=" + messageForm.getId() + "&appointmentId="
                + appointmentId);
        result.addObject("messageForm", messageForm);
        result.addObject("message", message);
        Customer customerConnect = customerService.findByPrincipal();

        if (messageForm.getRecipient() == null) {
            Collection<Customer> customers = customerService.findAll();
            if (Patient.class == customerConnect.getClass()) {
                customers = customerService.findOnlySpecialist();

            }

            result.addObject("customers", customers);
            result.addObject("answer", false);
        } else {
            result.addObject("answer", true);
        }
        result.addObject("details", false);
        result.addObject("cancel", true);

        return result;
    }
}
