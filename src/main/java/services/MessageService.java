package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Customer;
import domain.Message;
import domain.Patient;
import domain.Specialist;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {
    // Managed repository ---------------------------------------

    @Autowired
    private MessageRepository messageRepository;

    // Supporting services --------------------------------------
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PatientService patientService;

    // Constructors ---------------------------------------------

    public MessageService() {
        super();
    }

    // Simple CRUD methods --------------------------------------

    //Devuelve todos los mensajes de la bandeja de entrada del customer que se encuentra logueado en el sistema
    public Collection<Message> getMessageInbox() {
        Collection<Message> messages;
        Customer customerConnect = customerService.findByPrincipal();
        messages = messageRepository.getMessageInbox(customerConnect.getId());
        return messages;
    }

    //Devuelve todos los mensajes de la bandeja de salida del customer que se encuentra logueado en el sistema
    public Collection<Message> getMessageOutbox() {
        Collection<Message> messages;
        Customer customerConnect = customerService.findByPrincipal();
        messages = customerConnect.getMessageSender();
        return messages;
    }

    //Crea un mensaje para el customer que se encuentra logueado en el sistema
    public Message create() {
        Customer customer;
        customer = customerService.findByPrincipal();

        Message message;
        message = new Message();
        Date currentMoment;
        currentMoment = new Date();

        message.setSender(customer);
        message.setCreationMoment(currentMoment);

        Assert.notNull(message);

        return message;
    }

    //Guarda en la base de datos un mensaje para el customer que se encuentra logueado en el sistema
    public Message save(Message customerMessage) {
        Assert.notNull(customerMessage);
        Customer customerConnect = customerService.findByPrincipal();

        checkPrincipal(customerMessage);
        Date currentMoment;
        currentMoment = new Date(System.currentTimeMillis() - 1000);
        customerMessage.setCreationMoment(currentMoment);
        if (Patient.class == customerConnect.getClass()) {
        	
        	//Comprueba que la persona a la cual le manda el mensaje es un especialista
            Assert.isTrue(customerMessage.getRecipient().getClass() == Specialist.class);
            Patient patientConnect = patientService.findByPrincipal();
            
            //Comprueba que ese customer tiene habilitado el envío de mensajes
            Assert.isTrue(patientConnect.getEnableMessage());
        }

        messageRepository.save(customerMessage);

        return customerMessage;
    }

    //Comprueba que el customer que se encuentra logueado en el sistema es el que envía o el que recibe el mensaje
    public void checkPrincipal(Message customerMessage) {

        Assert.notNull(customerMessage);
        Customer customer;

        customer = customerService.findByPrincipal();

        Assert.isTrue(customer.getId() == customerMessage.getSender().getId()
                || customer.getId() == customerMessage.getRecipient().getId());

    }

    //Devuelve un mensaje dado su id
    public Message findOneToEdit(int messageId) {

        Assert.isTrue(messageId != 0);
        Message res;
        res = messageRepository.findOne(messageId);
        checkPrincipal(res);
        return res;
    }

    //Reconstruye un mensaje a partir de un objeto formulario de tipo mensaje
    public Message recontructor(MessageForm messageForm) {
        Message result = create();
        Customer customerConnect = customerService.findByPrincipal();
        Date creationMoment = new Date();

        result.setRecipient(messageForm.getRecipient());
        result.setSender(customerConnect);
        result.setCreationMoment(creationMoment);
        result.setSubject(messageForm.getSubject());
        result.setTextBody(messageForm.getTextBody());
        result.setId(messageForm.getId());
        result.setVersion(messageForm.getVersion());

        return result;
    }

}
