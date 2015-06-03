package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import domain.MedicalHistory;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer>{

}
