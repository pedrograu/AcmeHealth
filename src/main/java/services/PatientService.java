package services;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Appointment;
import domain.Comment;
import domain.MedicalHistory;
import domain.Message;
import domain.Offer;
import domain.Patient;
import domain.PersonalData;
import domain.Specialist;
import domain.Token;
import forms.PatientForm;
import forms.PatientForm2;
import forms.PatientForm3;
import forms.PatientForm4;
import forms.PatientForm5;
import repositories.PatientRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class PatientService {

    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    // Managed repository ---------------------------------------
    @Autowired
    private PatientRepository patientRepository;

    // Managed service ---------------------------------------
    @Autowired
    private MedicalHistoryService medicalHistoryService;
    @Autowired
    private SpecialistService specialistService;

    // Constructor...............................

    public PatientService() {
        super();

    }

    // Simple CRUD methods --------------------------------------

    //Crea un paciente
    public Patient create() {
        Patient patient = new Patient();

        Collection<Comment> comments = new HashSet<Comment>();
        Date currentMoment = new Date();
        Collection<Message> messageRecipients = new HashSet<Message>();
        Collection<Message> messageSenders = new HashSet<Message>();
        Collection<Offer> offers = new HashSet<Offer>();
        Collection<Appointment> appointments = new HashSet<Appointment>();

        UserAccount ua = new UserAccount();
        Authority patient1 = new Authority();
        patient1.setAuthority("PATIENT");
        ua.getAuthorities().add(patient1);

        Authority customer1 = new Authority();
        customer1.setAuthority("CUSTOMER");
        ua.getAuthorities().add(customer1);

        patient.setUserAccount(ua);
        patient.setComments(comments);
        patient.setCreationMoment(currentMoment);
        patient.setMessageRecipient(messageRecipients);
        patient.setMessageSender(messageSenders);
        patient.setOffers(offers);
        patient.setAppointments(appointments);
        patient.setEnableMessage(true);

        return patient;
    }

    //Guarda un paciente en la base de datos
    public void save(Patient patient) {

        Date currentMoment = new Date(System.currentTimeMillis() - 1000);

        //Comprueba que la tarjeta de credito no esté caducada
        Assert.isTrue(patient.getCreditCard().getExpirationYear() >= currentMoment.getYear());
        if (patient.getCreditCard().getExpirationYear() == currentMoment.getYear()) {
            Assert.isTrue(patient.getCreditCard().getExpirationMonth() >= currentMoment.getMonth());
        }
        Assert.notNull(patient);

        //Encripta en md5 la contraseña
        Md5PasswordEncoder encoder;
        String pass = patient.getUserAccount().getPassword();
        encoder = new Md5PasswordEncoder();
        String hash = encoder.encodePassword(pass, null);
        patient.getUserAccount().setPassword(hash);

        
        Patient patient2 = patientRepository.save(patient);

        //creamos un medialHistory para este patient
        MedicalHistory medicalHistory = medicalHistoryService.create(patient2);
        medicalHistoryService.save(medicalHistory);

    }
 
    //Guarda un patient en la base de datos
    public Patient save2(Patient patient) {

        Assert.notNull(patient);
         return patientRepository.save(patient);

    }
    
    //Reconstruye un patient a partir de un objeto formulario de tipo patient
    public Patient reconstruct(PatientForm patientForm) {

        Calendar currentMoment = new GregorianCalendar();
        int month = patientForm.getCreditCard().getExpirationMonth();
        int year = patientForm.getCreditCard().getExpirationYear();
        Calendar dateCreditCard = new GregorianCalendar();
        dateCreditCard.set(year, month, 1);

        //Comprueba que el token devuelto por la mutua (aplicación externa) es válido.
        Assert.isTrue(tokenIsValid((patientForm.getToken())));
        
        //Comprueba que la tarjeta de credito no está caducada
        Assert.isTrue(currentMoment.before(dateCreditCard));

        Patient patient = create();
        
        //Comprueba que se han aceptado los términos y las condiciones 
        Assert.isTrue(patientForm.getAvailable());
        
        //Comprueba que las contraseñas coinciden
        Assert.isTrue(patientForm.getPassword().equals(patientForm.getSecondPassword()));

        patient.setCreditCard(patientForm.getCreditCard());
        patient.setName(patientForm.getName());
        patient.setSurname(patientForm.getSurname());
        patient.setEmailAddress(patientForm.getEmailAddress());
        patient.setAddress(patientForm.getAddress());
        patient.getUserAccount().setPassword(patientForm.getPassword());
        patient.getUserAccount().setUsername(patientForm.getUsername());
        patient.setPhone(patientForm.getPhone());
        patient.setSpecialist(patientForm.getSpecialist());
        patient.setImage(patientForm.getImage());

        return patient;
    }
    
    
    //Reconstruye un patient a partir de un objeto formulario de tipo patient
    public Patient reconstruct2(PatientForm2 patientForm) {
    	
    	Patient patientConnect = findByPrincipal();

    	patientConnect.setEmailAddress(patientForm.getEmailAddress());
    	patientConnect.setAddress(patientForm.getAddress());
    	patientConnect.setPhone(patientForm.getPhone());

        return patientConnect;
    }
    
  //Reconstruye un patient a partir de un objeto formulario de tipo patient
    public Patient reconstruct3(PatientForm3 patientForm) {
    	
    	Patient patientConnect = findByPrincipal();
    	
        Md5PasswordEncoder encoder;
        String pass = patientForm.getOldPassword();
        encoder = new Md5PasswordEncoder();
        String hash = encoder.encodePassword(pass, null);

    	Assert.isTrue(hash.equals(patientConnect.getUserAccount().getPassword()));
    	Assert.isTrue(patientForm.getNewPassword().equals(patientForm.getSecondPassword()));
    	
    	
        Md5PasswordEncoder encoder2;
        String pass2 = patientForm.getNewPassword();
        encoder2 = new Md5PasswordEncoder();
        String hash2 = encoder2.encodePassword(pass2, null);
    	
    	patientConnect.getUserAccount().setPassword(hash2);

        return patientConnect;
    }
    
  //Reconstruye un patient a partir de un objeto formulario de tipo patient
    public Patient reconstruct4(PatientForm4 patientForm) {

        Calendar currentMoment = new GregorianCalendar();
        int month = patientForm.getCreditCard().getExpirationMonth();
        int year = patientForm.getCreditCard().getExpirationYear();
        Calendar dateCreditCard = new GregorianCalendar();
        dateCreditCard.set(year, month, 1);

        Assert.isTrue(currentMoment.before(dateCreditCard));

        Patient patientConnect = findByPrincipal();

        patientConnect.setCreditCard(patientForm.getCreditCard());


        return patientConnect;
    }
    
  //Reconstruye un patient a partir de un objeto formulario de tipo patient
    public Patient reconstruct5(PatientForm5 patientForm) {

        Calendar currentMoment = new GregorianCalendar();
        int month = patientForm.getCreditCard().getExpirationMonth();
        int year = patientForm.getCreditCard().getExpirationYear();
        Calendar dateCreditCard = new GregorianCalendar();
        dateCreditCard.set(year, month, 1);
        Collection<String> creditCardNumbers = getAllCreditCardNumber();
        
        Assert.isTrue(currentMoment.before(dateCreditCard));

        Patient patient = create();
        Assert.isTrue(patientForm.getAvailable());
        Assert.isTrue(patientForm.getPassword().equals(patientForm.getSecondPassword()));
        Assert.isTrue(!creditCardNumbers.contains(patientForm.getCreditCard().getNumber()));

        patient.setCreditCard(patientForm.getCreditCard());
        patient.setName(patientForm.getName());
        patient.setSurname(patientForm.getSurname());
        patient.setEmailAddress(patientForm.getEmailAddress());
        patient.setAddress(patientForm.getAddress());
        patient.getUserAccount().setPassword(patientForm.getPassword());
        patient.getUserAccount().setUsername(patientForm.getUsername());
        patient.setPhone(patientForm.getPhone());
        patient.setSpecialist(patientForm.getSpecialist());
        patient.setImage(patientForm.getImage());

        return patient;
    }

    //Devuelve el patient logueado en el sistema
    public Patient findByPrincipal() {
        UserAccount userAccount = LoginService.getPrincipal();
        return findByUserAccount(userAccount);
    }

    //Devuelve el patient a partir del userAccount
    public Patient findByUserAccount(UserAccount userAccount) {

        Patient result;

        result = patientRepository.findByUserAccountId(userAccount.getId());

        return result;
    }

    //Devuelve el número de pacientes registrados en el sistema
    public Integer getNumberOfPatient() {
        Integer result = patientRepository.findAll().size();
        return result;
    }

    //Devuelve un paciente dado su username
    public Patient findForUsername(String username) {
        Patient patient;
        patient = patientRepository.findForUsername(username);
        return patient;
    }

    //Guarda un paciente en la base de datos
    public void save(Patient patient, Specialist specialist) {
        Assert.notNull(patient);
        Assert.notNull(specialist);
        
        //Comprueba que su médico de cabecera es realmente un médico de cabecera(es un especialista con especialidad en "Medicina General")
        Assert.isTrue(specialist.getSpecialty().getName().equals("Medicina General"));

        patient.setSpecialist(specialist);
        patientRepository.save(patient);

    }

    //Devuelve todos los pacientes registrados en el sistema
    public Collection<Patient> findAll() {
        Collection<Patient> patients = patientRepository.findAll();
        return patients;
    }

    //Devuelve los pacientes con más citas
    public Collection<Patient> getPatientWithMoreAppointment() {
        Collection<Patient> patients;
        patients = patientRepository.getPatientWithMoreAppointment();
        return patients;
    }

    //Devuelve los pacientes que más dinero se han gastado en recetas.
    public Collection<Patient> getPatientWithMoreSpending() {
        Collection<Patient> patients;
        patients = patientRepository.getPatientWithMoreSpending();
        return patients;
    }

    //Devuelve los pacientes registrados en el sistema el ultimo año
    public Collection<Patient> getPatientLastYear() {
        Date currentYear = new Date();
        Calendar lastYearCalendar = new GregorianCalendar();
        lastYearCalendar.add(Calendar.YEAR, -1);
        Date lastYear = lastYearCalendar.getTime();

        Collection<Patient> patients;
        patients = patientRepository.getPatientLastYear(currentYear, lastYear);
        return patients;
    }

    //Devuelve los pacientes del especialista logueado en el sistema
    public Collection<Patient> findOwn() {
        Specialist specialistConnect = specialistService.findByPrincipal();
        Collection<Patient> patients;
        patients = patientRepository.findOwn(specialistConnect.getId());
        return patients;
    }

    //Devuelve los pacientes atendidos en el día de hoy por el especialista que está logueado en el sistema
    public Collection<Patient> findToday() {
        Calendar today = new GregorianCalendar();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        Date currentMoment = today.getTime();
        Specialist specialistConnect = specialistService.findByPrincipal();

        Collection<Patient> patients;
        patients = patientRepository.findToday(currentMoment, specialistConnect.getId());
        return patients;
    }

    //Devuelve un patient dado su id
    public Patient findOneToEdit(int patientId) {
        Assert.isTrue(patientId != 0);
        Patient res;
        res = patientRepository.findOne(patientId);
        return res;
    }

    //Habilita/deshabilita el envío de mensajes por parte de un paciente
    public void ban(Patient patient) {
        Assert.notNull(patient);
        if (patient.getEnableMessage()) {
            patient.setEnableMessage(false);
        } else {
            patient.setEnableMessage(true);
        }

    }

    //Obtiene el token dados el nombre de la mutua y el dni y la contraseña del usuario (hace una petición a través de un servicio web a una
    //aplicacion externa y ésta le devuelve el token si los datos son válidos)
    public String getToken(String name, String nif, String pass) {

        String token = "null";
        try {
            ArrayList<Token> tokens = JSON_MAPPER.readValue(new URL(
                    "http://52.24.124.225/index.php/api/mutua/get-token?name="+ name + "&nif=" + nif + "&pass=" + pass), JSON_MAPPER
                    .getTypeFactory().constructCollectionType(ArrayList.class, Token.class));

            Token t = tokens.get(0);
            return t.getToken();

        } catch (IOException e) {
            return token;
        }
    }
    
    //Comprueba que el token sea válido (se comunica a traves de un servicio web con una aplicación externa y ésta
    //devuelve los datos del usuario si el token es valido o lanza una excepcion si no lo es)
    public boolean tokenIsValid(String token) {

        boolean bool = false;
        try {
            ArrayList<PersonalData> pd = JSON_MAPPER.readValue(new URL(
                    "http://52.24.124.225/index.php/api/mutua/get-personaldata?token="+ token), JSON_MAPPER
                    .getTypeFactory().constructCollectionType(ArrayList.class, PersonalData.class));

            bool = true;
            return bool;

        } catch (IOException e) {
            return bool;
        }
    }
    
    //Obtiene los datos personales de un usuario dado un token(se comunica mediante un servicio web a una aplicación externa
    //pasandole como parámetro un token y devolviendo ésta los datos personales del usuario asociado a ese token)
    public ArrayList<String> getPersonalData(String token) {

    	ArrayList<String> array = new ArrayList<String>();

        try {
            ArrayList<PersonalData> pd = JSON_MAPPER.readValue(new URL(
                    "http://52.24.124.225/index.php/api/mutua/get-personaldata?token="+ token), JSON_MAPPER
                    .getTypeFactory().constructCollectionType(ArrayList.class, PersonalData.class));

            PersonalData p = pd.get(0);
            
            array.add(p.getUsername());
            array.add(p.getSurname());
            array.add(p.getAddress());
            array.add(p.getEmail());
            array.add(p.getPhone());
            
            return array;

        } catch (IOException e) {
            return array;
        }
    }

    //Devuelve todos los numeros de las tarjetas de creditos almacenadas en el sistema
	public Collection<String> getAllCreditCardNumber() {
		 return patientRepository.getAllCreditCardNumber();
	}

	//Devuelve todos los nombres de usuario de los pacientes almacenados en el sistema
	public Collection<String> getAllNameUserPatient() {
		return patientRepository.getAllNameUserPatients();
	}

}
