package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.PatientNotAccepted;

@Repository
public interface PatientNotAcceptedRepository extends JpaRepository<PatientNotAccepted, Integer> {


}
