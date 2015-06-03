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
public class Administrator extends Actor {

	
	
	// Relationships ----------------------------------------------------------


	private Collection<Offer> offers;
	private Collection<Specialty> specialties;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "administrator")
	public Collection<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Collection<Offer> offers) {
		this.offers = offers;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "administrator")
	public Collection<Specialty> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(Collection<Specialty> specialties) {
		this.specialties = specialties;
	}

}
