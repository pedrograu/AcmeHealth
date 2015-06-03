package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes={@Index(columnList="rating")})
public class Profile  extends DomainEntity{
	
	private Double rating;
	private String text;

	@Digits(integer = 9, fraction = 2)
	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	// Relationships ----------------------------------------------------------
	
	

	private Specialist specialist;
	private Collection<Comment> comments;

	@Valid
	@NotNull
	@OneToOne(optional=false)
	public Specialist getSpecialist() {
		return specialist;
	}

	public void setSpecialist(Specialist specialist) {
		this.specialist = specialist;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy="profile")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
	
	

}
