package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PrescriptionRepository;
import domain.Appointment;
import domain.Patient;
import domain.Prescription;
import domain.Specialist;

@Service
@Transactional
public class PrescriptionService {

    // Managed repository ---------------------------------------
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    // Services----------------------------------

    @Autowired
    private PatientService patientService;

    @Autowired
    private SpecialistService specialistService;

    // Constructors ---------------------------------------------

    public PrescriptionService() {
        super();
    }

    // Metodos ---------------------------------------------

    //Crea una prescription asociada a un appointment
    public Prescription create(Appointment a) {

        Prescription p = new Prescription();
        Date currentMoment = new Date();

        p.setAppointment(a);
        p.setCreationMoment(currentMoment);

        return p;

    }

    //Guarda una prescription en la base de datos
    public Prescription save(Prescription p) {

        checkPrincipal(p);
        Date currentMoment = new Date(System.currentTimeMillis() - 1000);
        p.setCreationMoment(currentMoment);

        Prescription prescription2 = prescriptionRepository.save(p);

        return prescription2;
    }

    //Devuelve todas las prescription del paciente que se encuentra logueado en el sistema
    public Collection<Prescription> findMyPrescriptions() {
        Patient patientConnect = patientService.findByPrincipal();
        Collection<Prescription> prescriptions = prescriptionRepository.findMyPrescriptions(patientConnect.getId());
        return prescriptions;
    }

    //Devuelve una prescription dada su id
    public Prescription findOneToEdit(int id) {
        Assert.isTrue(id != 0);
        Prescription res;
        res = prescriptionRepository.findOne(id);
        return res;
    }

    //Comprueba que la prescription es del especialista logueado en el sistema
    public void checkPrincipal(Prescription prescription) {
        Specialist specialistConnect = specialistService.findByPrincipal();
        Assert.isTrue(prescription.getAppointment().getSpecialist().equals(specialistConnect));
    }

    //Devuelve todas las prescription del paciente logueado en el sistema y del paciente dado como parametro de entrada.
    public Collection<Prescription> findForPatient(Patient patientConnect) {

        Specialist specialistConnect = specialistService.findByPrincipal();
        Collection<Prescription> prescriptions;
        prescriptions = prescriptionRepository.findForPatient(patientConnect.getId(), specialistConnect.getId());
        return prescriptions;
    }

}
