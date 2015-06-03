package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MedicalHistoryRepository;
import domain.Appointment;
import domain.MedicalHistory;
import domain.Patient;
import domain.Specialty;

@Service
@Transactional
public class MedicalHistoryService {

	// Managed repository ---------------------------------------
	@Autowired
	private MedicalHistoryRepository medicalHistoryRepository;

	// Managed service ---------------------------------------
	@Autowired
	private PatientService patientService;

	// Constructors ---------------------------------------------

	public MedicalHistoryService() {
		super();
	}

	// Simple CRUD methods --------------------------------------

	public MedicalHistory create(Patient patient2) {

		MedicalHistory medicalHistory = new MedicalHistory();
		Collection<Appointment> appointments = new HashSet<Appointment>();
		
		medicalHistory.setPatient(patient2);
		medicalHistory.setAppointments(appointments);
		
		return medicalHistory;
	}

	public void save(MedicalHistory medicalHistory) {
		
		MedicalHistory medicalHistory2 = medicalHistoryRepository.save(medicalHistory);
		medicalHistory2.getPatient().setMedicalHistory(medicalHistory2);

		
		
	}
	
	public MedicalHistory findOneToEdit(int medicalHistoryId) {
		MedicalHistory s = medicalHistoryRepository.findOne(medicalHistoryId);
		return s;
	}

}
