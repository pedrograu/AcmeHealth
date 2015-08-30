package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Appointment;
import domain.MedicalHistory;
import domain.Patient;
import repositories.MedicalHistoryRepository;

@Service
@Transactional
public class MedicalHistoryService {

    // Managed repository ---------------------------------------
    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    // Managed service ---------------------------------------


    // Constructors ---------------------------------------------

    public MedicalHistoryService() {
        super();
    }

    // Simple CRUD methods --------------------------------------

    //Crea un medicalHistory para un paciente
    public MedicalHistory create(Patient patient2) {

        MedicalHistory medicalHistory = new MedicalHistory();
        Collection<Appointment> appointments = new HashSet<Appointment>();

        medicalHistory.setPatient(patient2);
        medicalHistory.setAppointments(appointments);

        return medicalHistory;
    }

    //Guarda en la base de datos un medicalHistory
    public void save(MedicalHistory medicalHistory) {

        MedicalHistory medicalHistory2 = medicalHistoryRepository.save(medicalHistory);
        medicalHistory2.getPatient().setMedicalHistory(medicalHistory2);

    }

    //Devuelve un medicalhistory a partir de su id
    public MedicalHistory findOneToEdit(int medicalHistoryId) {
        MedicalHistory s = medicalHistoryRepository.findOne(medicalHistoryId);
        return s;
    }

}
