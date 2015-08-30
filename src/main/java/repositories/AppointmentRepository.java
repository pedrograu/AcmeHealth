package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	//Devuelve una collection de appointment no finalizadas dados el id del paciente
    @Query("select a from Appointment a where a.patient.id=?1 and a.isFinish=false")
    Collection<Appointment> getAppointmentsNotFinish(int id);

    //Devuelve una collection de appointment no finalizadas dado el id del especialista
    @Query("select a from Appointment a where a.specialist.id=?1 and a.isFinish=false")
    Collection<Appointment> getAppointmentsNotFinish2(int id);

    //Devuelve una collection de appointment finalizadas dado el id del especialista
    @Query("select a from Appointment a where a.specialist.id=?1 and a.isFinish=true")
    Collection<Appointment> getAppointmentsFinish(int id);

    //Devuelve un appointment dado el id del paciente y el id de la oferta
    @Query("select a from Appointment a where a.patient.id=?1 and a.offer.id=?2")
    Appointment getAppointmentForPatientAndOffer(int id, int id2);

    //Devuelve un appointment para una fecha de inicio y un especialista
    @Query("select a from Appointment a where a.startMoment=?1 and a.specialist.id=?2")
    Appointment getAppointmentForStartMoment(Date startMoment, int id);

    //Devuelve una collection de appointment para un especialista y un paciente
    @Query("select a from Appointment a where a.specialist.id=?1 and a.patient.id =?2")
    Collection<Appointment> getAppointmentforOneSpecialistAndOnePatient(int id, int id2);

    //Devuelve una collection de appointment para un especialista dado y una fecha de inicio contenida en un rango
    @Query("select a from Appointment a where a.specialist.id=?1 and a.startMoment > ?2 and a.startMoment < ?3")
    Collection<Appointment> getAppointmentforOneSpecialistAndDay(int id, Date id2, Date id3);

}
