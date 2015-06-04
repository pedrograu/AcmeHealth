package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Customer extends Actor {

    // RelationShips.............................
    private Collection<Message> messageRecipient;
    private Collection<Message> messageSender;

    @Valid
    @NotNull
    @OneToMany(mappedBy = "recipient")
    public Collection<Message> getMessageRecipient() {
        return messageRecipient;
    }

    public void setMessageRecipient(Collection<Message> messageRecipient) {
        this.messageRecipient = messageRecipient;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "sender")
    public Collection<Message> getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(Collection<Message> messageSender) {
        this.messageSender = messageSender;
    }

}
