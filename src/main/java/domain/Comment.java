package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

    private Date creationMoment;
    private String text;
    private Integer rating;


    @Past
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getCreationMoment() {
        return creationMoment;
    }

    public void setCreationMoment(Date creationMoment) {
        this.creationMoment = creationMoment;
    }

    @NotBlank
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NotNull
    @Range(min = 0, max = 10)
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }


    // RelationShips.............................................

    private Customer customer;
    private Profile profile;
    private Comment commentParent;
    private Collection<Comment> commentChilds;



    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    @Valid
    @ManyToOne(optional = true)
    public Comment getCommentParent() {
        return commentParent;
    }

    public void setCommentParent(Comment commentParent) {
        this.commentParent = commentParent;
    }
    
    @Valid
    @NotNull
    @OneToMany(mappedBy = "commentParent")
    public Collection<Comment> getCommentChilds() {
        return commentChilds;
    }

    public void setCommentChilds(Collection<Comment> commentChilds) {
        this.commentChilds = commentChilds;
    }

}
