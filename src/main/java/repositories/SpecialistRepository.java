package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Specialist;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Integer> {

    @Query("select s from Specialist s")
    Collection<Specialist> findAllSpecialists();

    @Query("select p from Specialist p where p.userAccount.id=?1")
    Specialist findByUserAccountId(int id);

    @Query("select s from Specialist s where s.specialty.id = ?1")
    Collection<Specialist> findSpecialistsForSpecialty(int id);

    @Query("select s from Specialist s where s.specialty.name = ?1")
    Collection<Specialist> findAllSpecialistsOfGeneralMedicine(String str);

    @Query("select s from Specialist s where s.userAccount.username=?1")
    Specialist findForUsername(String username);

    @Query("select a.specialist from Appointment a group by a.specialist having sum(a) >= all (select sum(a) from Appointment a group by a.specialist having a.specialist != null)")
    Collection<Specialist> getSpecialistWithMoreAppointment();

    @Query("select s from Specialist s")
    Collection<Specialist> getAllSpecialist();

}
