package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Customer extends Actor {

    // RelationShips.............................
    private Collection<Message> messageRecipient;
    private Collection<Message> messageSender;
    private Collection<Comment> comments;
    private Profile profile;
    

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

    @Valid
    @NotNull
    @OneToMany(mappedBy = "customer")
    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }
    
    @Valid
    @OneToOne(cascade = CascadeType.ALL, optional = true)
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
