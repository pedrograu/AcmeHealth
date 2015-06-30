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

import repositories.PatientRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

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

    public Patient create() {
        Patient patient = new Patient();

        Collection<Comment> comments = new HashSet<Comment>();
        //MedicalHistory medicalHistory = new MedicalHistory();
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
        //patient.setMedicalHistory(medicalHistory);
        patient.setCreationMoment(currentMoment);
        patient.setMessageRecipient(messageRecipients);
        patient.setMessageSender(messageSenders);
        patient.setOffers(offers);
        patient.setAppointments(appointments);
        patient.setEnableMessage(true);

        return patient;
    }

    public void save(Patient patient) {

        Date currentMoment = new Date(System.currentTimeMillis() - 1000);

        Assert.isTrue(patient.getCreditCard().getExpirationYear() >= currentMoment.getYear());
        if (patient.getCreditCard().getExpirationYear() == currentMoment.getYear()) {
            Assert.isTrue(patient.getCreditCard().getExpirationMonth() >= currentMoment.getMonth());
        }
        Assert.notNull(patient);

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
    
    
    public void save2(Patient patient) {

        Assert.notNull(patient);
        patientRepository.save(patient);

    }
    
//    public Collection<String> getTokens(){
//        Collection<String> tokens = patientRepository.getTokens();
//    
//        return tokens;
//    }

    public Patient reconstruct(PatientForm patientForm) {

        Calendar currentMoment = new GregorianCalendar();
        int month = patientForm.getCreditCard().getExpirationMonth();
        int year = patientForm.getCreditCard().getExpirationYear();
        Calendar dateCreditCard = new GregorianCalendar();
        dateCreditCard.set(year, month, 1);

        Assert.isTrue(tokenIsValid((patientForm.getToken())));
        
        Assert.isTrue(currentMoment.before(dateCreditCard));

        Patient patient = create();
        Assert.isTrue(patientForm.getAvailable());
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
    
    
    
    public Patient reconstruct2(PatientForm2 patientForm) {
    	
    	Patient patientConnect = findByPrincipal();

    	patientConnect.setEmailAddress(patientForm.getEmailAddress());
    	patientConnect.setAddress(patientForm.getAddress());
    	patientConnect.setPhone(patientForm.getPhone());

        return patientConnect;
    }
    
    
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

    public Patient findByPrincipal() {
        UserAccount userAccount = LoginService.getPrincipal();
        return findByUserAccount(userAccount);
    }

    public Patient findByUserAccount(UserAccount userAccount) {

        Patient result;

        result = patientRepository.findByUserAccountId(userAccount.getId());

        return result;
    }

    public Integer getNumberOfPatient() {
        Integer result = patientRepository.findAll().size();
        return result;
    }

    public Patient findForUsername(String username) {
        Patient patient;
        patient = patientRepository.findForUsername(username);
        return patient;
    }

    public void save(Patient patient, Specialist specialist) {
        Assert.notNull(patient);
        Assert.notNull(specialist);
        //Assert.isTrue(patient.getSpecialist() == null || !patient.getSpecialist().equals(specialist));
        Assert.isTrue(specialist.getSpecialty().getName().equals("Medicina General"));

        patient.setSpecialist(specialist);
        patientRepository.save(patient);

    }

    public Collection<Patient> findAll() {
        Collection<Patient> patients = patientRepository.findAll();
        return patients;
    }

    public Collection<Patient> getPatientWithMoreAppointment() {
        Collection<Patient> patients;
        patients = patientRepository.getPatientWithMoreAppointment();
        return patients;
    }

    public Collection<Patient> getPatientWithMoreSpending() {
        Collection<Patient> patients;
        patients = patientRepository.getPatientWithMoreSpending();
        return patients;
    }

    public Collection<Patient> getPatientLastYear() {
        Date currentYear = new Date();
        Calendar lastYearCalendar = new GregorianCalendar();
        lastYearCalendar.add(Calendar.YEAR, -1);
        Date lastYear = lastYearCalendar.getTime();

        Collection<Patient> patients;
        patients = patientRepository.getPatientLastYear(currentYear, lastYear);
        return patients;
    }

    public Collection<Patient> findOwn() {
        Specialist specialistConnect = specialistService.findByPrincipal();
        Collection<Patient> patients;
        patients = patientRepository.findOwn(specialistConnect.getId());
        return patients;
    }

    public Collection<Patient> findToday() {
        Calendar today = new GregorianCalendar();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        Date currentMoment = today.getTime();

        Collection<Patient> patients;
        patients = patientRepository.findToday(currentMoment);
        return patients;
    }

    public Patient findOneToEdit(int patientId) {
        Assert.isTrue(patientId != 0);
        Patient res;
        res = patientRepository.findOne(patientId);
        return res;
    }

    public void ban(Patient patient) {
        Assert.notNull(patient);
        if (patient.getEnableMessage()) {
            patient.setEnableMessage(false);
        } else {
            patient.setEnableMessage(true);
        }

    }

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

}
