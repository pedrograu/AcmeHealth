package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query("select a from Appointment a where a.patient.id=?1 and a.isFinish=false")
    Collection<Appointment> getAppointmentsNotFinish(int id);

//    @Query("select a from Appointment a where a.timetable.specialist.id=?1 and a.isFinish=false")
//    Collection<Appointment> getAppointmentsNotFinish2(int id);

//    @Query("select a from Appointment a where a.timetable.specialist.id=?1 and a.isFinish=true")
//    Collection<Appointment> getAppointmentsFinish(int id);
    
    @Query("select a from Appointment a where a.specialist.id=?1 and a.isFinish=true")
    Collection<Appointment> getAppointmentsFinish(int id);

    @Query("select a from Appointment a where a.patient.id=?1 and a.offer.id=?2")
    Appointment getAppointmentForPatientAndOffer(int id, int id2);

    @Query("select a from Appointment a where a.startMoment=?1 and a.specialist.id=?2")
    Appointment getAppointmentForStartMoment(Date startMoment, int id);

//    @Query("select a from Appointment a where a.timetable.specialist.id=?1 and a.patient.id =?2")
//    Collection<Appointment> getAppointmentforOneSpecialistAndOnePatient(int id, int id2);
    
    @Query("select a from Appointment a where a.specialist.id=?1 and a.patient.id =?2")
    Collection<Appointment> getAppointmentforOneSpecialistAndOnePatient(int id, int id2);

}
