package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Specialist extends Customer {

    // Relationships ----------------------------------------------------------
    private Collection<Patient> patients;
    private Collection<Timetable> timetables;
    private Specialty specialty;
    private Collection<Offer> offers;
    private Collection<Appointment> appointments;
    private Collection<FreeDay> freeDays;

    
    @Valid
    @NotNull
    @OneToMany(mappedBy = "specialist")
    public Collection<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Collection<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "specialist")
    public Collection<FreeDay> getFreeDays() {
        return freeDays;
    }

    public void setFreeDays(Collection<FreeDay> freeDays) {
        this.freeDays = freeDays;
    }


    @Valid
    @NotNull
    @OneToMany(mappedBy = "specialist")
    public Collection<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(Collection<Timetable> timetables) {
        this.timetables = timetables;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "specialist")
    public Collection<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Collection<Offer> offers) {
        this.offers = offers;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "specialist")
    public Collection<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Collection<Patient> patients) {
        this.patients = patients;
    }

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }


}
