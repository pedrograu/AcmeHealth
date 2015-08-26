package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Timetable;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Integer> {

	//Devuelve una collection de timetables de un especialista dado
    @Query("select t from Timetable t where t.specialist.id=?1")
    Collection<Timetable> getTimetablesForSpecialist(int id);

    //Devuelve una lista de timetables para un dia de la semana y un especialista dado
    @Query("select t from Timetable t where t.day=?1 and t.specialist.id=?2")
    List<Timetable> getTimetablesForDayOfWeekAndSpecialist(int diaDeLaSemana, int specialist);
    
    //Devuelve todos los timetables almacenados en el sistema
    @Query("select t from Timetable t")
    Collection<Timetable> findAllTimetables();

}
