package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SpecialistRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Appointment;
import domain.FreeDay;
import domain.Message;
import domain.Offer;
import domain.Patient;
import domain.Profile;
import domain.Specialist;
import domain.Specialty;
import domain.Timetable;
import forms.SpecialistForm;

@Service
@Transactional
public class SpecialistService {

    // Managed repository ---------------------------------------
    @Autowired
    private SpecialistRepository specialistRepository;

    // Managed service ---------------------------------------

    @Autowired
    private ProfileService profileService;

    @Autowired
    private AdministratorService administratorService;

    // Constructors ---------------------------------------------

    public SpecialistService() {
        super();
    }

    // Simple CRUD methods --------------------------------------

    //Crea un nuevo especialista
    public Specialist create() {
        Specialist specialist = new Specialist();

        Collection<Timetable> timetables = new HashSet<Timetable>();
        Collection<Patient> patients = new HashSet<Patient>();
        Collection<Message> messageRecipients = new HashSet<Message>();
        Collection<Message> messageSenders = new HashSet<Message>();
        Collection<FreeDay> freeDays = new HashSet<FreeDay>();
        Collection<Offer> offers = new HashSet<Offer>();
        Collection<Appointment> appointments = new HashSet<Appointment>();

        UserAccount ua = new UserAccount();
        Authority specialist1 = new Authority();
        specialist1.setAuthority("SPECIALIST");
        ua.getAuthorities().add(specialist1);

        Authority customer1 = new Authority();
        customer1.setAuthority("CUSTOMER");
        ua.getAuthorities().add(customer1);

        specialist.setUserAccount(ua);
        specialist.setTimetables(timetables);
        specialist.setPatients(patients);
        specialist.setMessageRecipient(messageRecipients);
        specialist.setMessageSender(messageSenders);
        specialist.setOffers(offers);
        specialist.setFreeDays(freeDays);
        specialist.setAppointments(appointments);

        return specialist;
    }

    //Guarda en la base de datos un especialista
    public void save(Specialist specialist) {

        Assert.notNull(specialist);
        Administrator administratorConnect = administratorService.findByPrincipal();
        Assert.isTrue(Administrator.class == administratorConnect.getClass());

        //Encripta en md5 la contraseņa
        Md5PasswordEncoder encoder;
        String pass = specialist.getUserAccount().getPassword();
        encoder = new Md5PasswordEncoder();
        String hash = encoder.encodePassword(pass, null);
        specialist.getUserAccount().setPassword(hash);

        Specialist specialist2 = specialistRepository.save(specialist);

        // creamos un profile
        Profile profile = profileService.create(specialist2);
        Profile profile2 = profileService.save(profile);
        specialist2.setProfile(profile2);
        specialistRepository.save(specialist2);

    }

    //Reconstruye un especialista a partir de un objeto formulario de tipo especialista
    public Specialist reconstruct(SpecialistForm specialistForm) {

        Specialist specialist = create();
        
        //Comprueba que se aceptaron los terminos y condiciones
        Assert.isTrue(specialistForm.getAvailable());
        
        //Comprueba que las contraseņas coinciden
        Assert.isTrue(specialistForm.getPassword().equals(specialistForm.getSecondPassword()));

        specialist.setName(specialistForm.getName());
        specialist.setSurname(specialistForm.getSurname());
        specialist.setEmailAddress(specialistForm.getEmailAddress());

        specialist.getUserAccount().setPassword(specialistForm.getPassword());
        specialist.getUserAccount().setUsername(specialistForm.getUsername());

        specialist.setSpecialty(specialistForm.getSpecialty());

        return specialist;
    }

    //devuelve todos los especialistas registrados en el sistema
    public Collection<Specialist> findAllSpecialists() {
        Collection<Specialist> res = specialistRepository.findAll();
        return res;
    }

    //Devuelve todos los especialistas que tengan la especialidad pasada como parametro de entrada
    public Collection<Specialist> findSpecialistsForSpecialty(Specialty specialty) {
        Collection<Specialist> res = specialistRepository.findSpecialistsForSpecialty(specialty.getId());
        return res;
    }

    //Devuelve un especialista dado su id
    public Specialist findOneToEdit(int id) {
        Assert.isTrue(id != 0);
        Specialist res;
        res = specialistRepository.findOne(id);
        return res;
    }

    //Devuelve el especialista que se encuentra logueado en el sistema
    public Specialist findByPrincipal() {
        UserAccount userAccount = LoginService.getPrincipal();
        return findByUserAccount(userAccount);
    }

    //Devuelve un especialista pasado el userAccount
    public Specialist findByUserAccount(UserAccount userAccount) {

        Specialist result;

        result = specialistRepository.findByUserAccountId(userAccount.getId());

        return result;
    }

    //Devuelve todos los especialistas que son medicos de cabecera
    public Collection<Specialist> findAllSpecialistsOfGeneralMedicine() {
        String medicinaGeneral = "Medicina General";
        Collection<Specialist> specialists;
        specialists = specialistRepository.findAllSpecialistsOfGeneralMedicine(medicinaGeneral);
        return specialists;
    }

    //Devuelve un especialista dado su username
    public Specialist findForUsername(String username) {
        Specialist specialist;
        specialist = specialistRepository.findForUsername(username);
        return specialist;
    }

    //Devuelve los especialistas que mas citas tienen
    public Collection<Specialist> getSpecialistWithMoreAppointment() {
        Collection<Specialist> specialists;
        specialists = specialistRepository.getSpecialistWithMoreAppointment();
        return specialists;
    }


}
