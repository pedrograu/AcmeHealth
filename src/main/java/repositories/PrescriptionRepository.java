package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

	//Devuelve una collection de recetas de un paciente ordenadas por fecha
    @Query("select p from Prescription p where p.appointment.medicalHistory.patient.id = ?1 order by p.creationMoment")
    Collection<Prescription> findMyPrescriptions(int id);

    //Devuelve una collection de recetas dado un paciente y un especialista
    @Query("select a.prescriptions from Appointment a where a.medicalHistory.patient.id = ?1 and a.specialist.id = ?2")
    Collection<Prescription> findForPatient(int id, int id2);

}
