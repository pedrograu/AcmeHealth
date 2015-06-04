package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {

    @Query("select s from Specialty s")
    Collection<Specialty> getAll();

}
