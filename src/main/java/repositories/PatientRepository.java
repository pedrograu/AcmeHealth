package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

	//Devuelve un paciente dado el id del userAccount
    @Query("select p from Patient p where p.userAccount.id=?1")
    Patient findByUserAccountId(int id);

    //Devuelve un paciente dado su username
    @Query("select p from Patient p where p.userAccount.username=?1")
    Patient findForUsername(String username);

    //Devuelve una collection de pacientes que mas citas han tenido con los especialistas
    @Query("select p from Patient p where p.medicalHistory.appointments.size = (select max(p.medicalHistory.appointments.size) from Patient p)")
    Collection<Patient> getPatientWithMoreAppointment();

    //Devuelve una collection de pacientes que mas dinero se han gastado en recetas medicas
    @Query("select p.appointment.medicalHistory.patient from Prescription p group by p.appointment.medicalHistory.patient  having sum(p.price) >= all (select sum(p.price) from Prescription p group by p.appointment.medicalHistory.patient having p.appointment.medicalHistory.patient != null))")
    Collection<Patient> getPatientWithMoreSpending();

    //Devuelve una collection de pacientes registrados en el sistema en el ultimo año
    @Query("select p from Patient p where p.creationMoment >= ?2 and p.creationMoment <= ?1")
    Collection<Patient> getPatientLastYear(Date currentYear, Date lastYear);

    //Devuelve una collection de pacientes que dado su medico de cabecera
    @Query("select p from Patient p where p.specialist.id = ?1")
    Collection<Patient> findOwn(int id);

    //Devuelve una collection de pacientes que han sido atendidos en el dia de hoy
    @Query("select a.medicalHistory.patient from Appointment a where a.startMoment >= ?1 and a.isFinish=true and a.specialist.id=?2")
    Collection<Patient> findToday(Date currentMoment, int id);

    //Devuelve una collection de los numeros de tarjetas de credito registrados en el sistema
    @Query("select p.creditCard.number from Patient p")
	Collection<String> getAllCreditCardNumber();

    //Devuelve una collection de usernames de los pacientes registrados en el sistema
    @Query("select p.userAccount.username from Patient p")
	Collection<String> getAllNameUserPatients();
    


}
