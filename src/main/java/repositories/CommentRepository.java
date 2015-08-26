package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	//Devuelve una collection de comentarios de un especialista dado
    @Query("select c from Comment c where c.profile.specialist.id = ?1")
    Collection<Comment> getCommentsForSpecialist(int id);

}
