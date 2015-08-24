package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PatientNotAcceptedRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.MedicalHistory;
import domain.Patient;
import domain.PatientNotAccepted;

@Service
@Transactional
public class PatientNotAcceptedService {

    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    
    // Services ---------------------------------------
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private MedicalHistoryService medicalHistoryService;
    
    // Managed repository ---------------------------------------
    @Autowired
    private PatientNotAcceptedRepository patientNotAcceptedRepository;


    // Constructor...............................

    public PatientNotAcceptedService() {
        super();

    }

    // Simple CRUD methods --------------------------------------

    public void savePatientNotAccepted(Patient patient) {

        Date currentMoment = new Date(System.currentTimeMillis() - 1000);
        
		Collection<String> creditCardNumbers = patientService.getAllCreditCardNumber();
		Collection<String> 	nameUserPatiens = patientService.getAllNameUserPatient();
		
		Assert.isTrue(!nameUserPatiens.contains(patient.getUserAccount().getUsername()));
		Assert.isTrue(!creditCardNumbers.contains(patient.getCreditCard().getNumber()));

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
        
        PatientNotAccepted p = new PatientNotAccepted();
        
        p.setAddress(patient.getAddress());
        p.setCreditCard(patient.getCreditCard());
        p.setEmailAddress(patient.getEmailAddress());
        p.setImage(patient.getImage());
        p.setPassword(patient.getUserAccount().getPassword());
        p.setPhone(patient.getPhone());
        p.setSpecialist(patient.getSpecialist());
        p.setName(patient.getName());
        p.setSurname(patient.getSurname());
        p.setUsername(patient.getUserAccount().getUsername());
        
        patientNotAcceptedRepository.save(p);

    }
    
    public Collection<PatientNotAccepted> findAll() {
        Collection<PatientNotAccepted> patients = patientNotAcceptedRepository.findAll();
        return patients;
    }
    
    public PatientNotAccepted findOneToEdit(int patientNotAcceptedId) {
        Assert.isTrue(patientNotAcceptedId != 0);
        PatientNotAccepted res;
        res = patientNotAcceptedRepository.findOne(patientNotAcceptedId);
        return res;
    }

    public void discharge(PatientNotAccepted patientNotAccepted) {
        Assert.notNull(patientNotAccepted);
        
        Patient p = patientService.create();
         p.setAddress(patientNotAccepted.getAddress());
         p.setCreditCard(patientNotAccepted.getCreditCard());
         p.setEmailAddress(patientNotAccepted.getEmailAddress());
         p.setImage(patientNotAccepted.getImage());
         p.setName(patientNotAccepted.getName());
         p.setSurname(patientNotAccepted.getSurname());
         p.setPhone(patientNotAccepted.getPhone());
         p.setSpecialist(patientNotAccepted.getSpecialist());
         p.getUserAccount().setUsername(patientNotAccepted.getUsername());
         p.getUserAccount().setPassword(patientNotAccepted.getPassword());
         
         
         Date currentMoment = new Date(System.currentTimeMillis() - 1000);

         Assert.isTrue(p.getCreditCard().getExpirationYear() >= currentMoment.getYear());
         if (p.getCreditCard().getExpirationYear() == currentMoment.getYear()) {
             Assert.isTrue(p.getCreditCard().getExpirationMonth() >= currentMoment.getMonth());
         }
         
         Patient patient2 = patientService.save2(p);

         //creamos un medialHistory para este patient
         MedicalHistory medicalHistory = medicalHistoryService.create(patient2);
         medicalHistoryService.save(medicalHistory);
         
         patientNotAcceptedRepository.delete(patientNotAccepted);
        

    }
    


}
