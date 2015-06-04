package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "name") })
public class Specialty extends DomainEntity {

    private String name;
    private String description;

    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Relationships ----------------------------------------------------------

    private Collection<Specialist> specialists;
    private Administrator administrator;

    @Valid
    @NotNull
    @OneToMany(mappedBy = "specialty")
    public Collection<Specialist> getSpecialists() {
        return specialists;
    }

    public void setSpecialists(Collection<Specialist> specialists) {
        this.specialists = specialists;
    }

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

}
