package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FreeDay;

@Repository
public interface FreeDayRepository extends JpaRepository<FreeDay, Integer> {

	//Devuelve una collection de freedays de un especialista
    @Query("select f from FreeDay f where f.specialist.id=?1")
    Collection<FreeDay> getFreeDaysForSpecialist(int id);

    //Devuelve todos los freedays del sistema
    @Query("select f from FreeDay f")
    Collection<FreeDay> findAllFreeDays();

}
