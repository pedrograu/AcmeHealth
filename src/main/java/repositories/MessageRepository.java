package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	//Devuelve una collection de mensajes dado el destinatario
    @Query("select m from Message m where m.recipient.id = ?1")
    Collection<Message> getMessageInbox(int id);

    //Devuelve una collection de mensajes dado el remitente
    @Query("select m from Message m where m.sender.id = ?1")
    Collection<Message> getMessageOutbox(int id);

}
