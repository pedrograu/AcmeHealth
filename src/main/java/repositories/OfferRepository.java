package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

    @Query("select o from Offer o where o.administrator.id = ?1")
    Collection<Offer> findOwn(int id);

    @Query("select p.offers from Patient p where p.id=?1")
    Collection<Offer> findOwnPatient(int id);

    @Query("select o from Offer o where o.finishMoment >= ?1")
    Collection<Offer> findNotFinished(Date currentMoment);

    @Query("select o from Offer o where o.finishMoment >= ?1 and o.startMoment <= ?1")
    Collection<Offer> findActive(Date currentMoment);

    @Query("select o from Offer o order by o.price")
    Collection<Offer> findOrder();

    @Query("select o from Offer o where o.finishMoment >= ?1 and o.administrator.id = ?2")
    Collection<Offer> findNotFinishedOwn(Date currentMoment, int id);

    @Query("select o from Offer o where o.finishMoment >= ?1 and o.specialist.id = ?2")
    Collection<Offer> findNotFinishedOwnSpecialist(Date currentMoment, int id);

}
