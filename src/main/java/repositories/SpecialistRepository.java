package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Specialist;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Integer> {

	//Devuelve un especialista dado el id del userAccount
    @Query("select p from Specialist p where p.userAccount.id=?1")
    Specialist findByUserAccountId(int id);

    //Devuelve una collection de especialistas que tienen la especialidad dada
    @Query("select s from Specialist s where s.specialty.id = ?1")
    Collection<Specialist> findSpecialistsForSpecialty(int id);

    //Devuelve una collection de los especialistas que tienen la especialidad "Medicina General"
    @Query("select s from Specialist s where s.specialty.name = ?1")
    Collection<Specialist> findAllSpecialistsOfGeneralMedicine(String str);

    //Devuelve un especialista dado su username
    @Query("select s from Specialist s where s.userAccount.username=?1")
    Specialist findForUsername(String username);

    //Devuelve una collection de los especialistas que mas citas han tenido con sus pacientes
    @Query("select a.specialist from Appointment a group by a.specialist having sum(a) >= all (select sum(a) from Appointment a group by a.specialist having a.specialist != null)")
    Collection<Specialist> getSpecialistWithMoreAppointment();


}
