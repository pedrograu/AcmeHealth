package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query("select p from Patient p where p.userAccount.id=?1")
    Patient findByUserAccountId(int id);

    @Query("select p from Patient p where p.userAccount.username=?1")
    Patient findForUsername(String username);

    @Query("select p from Patient p where p.medicalHistory.appointments.size = (select max(p.medicalHistory.appointments.size) from Patient p)")
    Collection<Patient> getPatientWithMoreAppointment();

    @Query("select p.appointment.medicalHistory.patient from Prescription p group by p.appointment.medicalHistory.patient  having sum(p.price) >= all (select sum(p.price) from Prescription p group by p.appointment.medicalHistory.patient having p.appointment.medicalHistory.patient != null))")
    Collection<Patient> getPatientWithMoreSpending();

    @Query("select p from Patient p where p.creationMoment >= ?2 and p.creationMoment <= ?1")
    Collection<Patient> getPatientLastYear(Date currentYear, Date lastYear);

    @Query("select p from Patient p where p.specialist.id = ?1")
    Collection<Patient> findOwn(int id);

    @Query("select a.medicalHistory.patient from Appointment a where a.startMoment >= ?1 and a.isFinish=true")
    Collection<Patient> findToday(Date currentMoment);
    
    @Query("select m.token from Mutua m")
    Collection<String> getTokens();

}
