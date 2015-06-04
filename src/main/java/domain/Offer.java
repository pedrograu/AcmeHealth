package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "price"), @Index(columnList = "startMoment"),
        @Index(columnList = "finishMoment") })
public class Offer extends DomainEntity {

    private Date creationMoment;
    private Date startMoment;
    private Date finishMoment;
    private String title;
    private String description;
    private Double price;
    private Integer amountPerson;
    private Integer enrollees;

    @NotNull
    public Integer getEnrollees() {
        return enrollees;
    }

    public void setEnrollees(Integer enrollees) {
        this.enrollees = enrollees;
    }

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

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date getStartMoment() {
        return startMoment;
    }

    public void setStartMoment(Date startMoment) {
        this.startMoment = startMoment;
    }

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date getFinishMoment() {
        return finishMoment;
    }

    public void setFinishMoment(Date finishMoment) {
        this.finishMoment = finishMoment;
    }

    @NotBlank
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @Min(0)
    @Digits(integer = 9, fraction = 2)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @NotNull
    @Min(1)
    public Integer getAmountPerson() {
        return amountPerson;
    }

    public void setAmountPerson(Integer amountPerson) {
        this.amountPerson = amountPerson;
    }

    // Relationships ----------------------------------------------------------
    private Administrator administrator;
    private Collection<Patient> patients;
    private Specialist specialist;
    private Collection<Appointment> appointments;

    @NotNull
    @Valid
    @OneToMany(mappedBy = "offer")
    public Collection<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Collection<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
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

    @Valid
    @NotNull
    @ManyToMany
    public Collection<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Collection<Patient> patients) {
        this.patients = patients;
    }

    public boolean add(Patient p) {
        return patients.add(p);
    }

    public boolean remove(Patient p) {
        return patients.remove(p);
    }

}
