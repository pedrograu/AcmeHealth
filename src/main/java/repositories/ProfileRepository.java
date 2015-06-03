package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import domain.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer>{

	@Query("select avg(c.rating) from Comment c where c.profile.id=?1")
	Double updateRating(int id);
	@Query("select p from Profile p where p.rating = (select max(p.rating) from Profile p)")
	Collection<Profile> getBestSpecialist();

}
