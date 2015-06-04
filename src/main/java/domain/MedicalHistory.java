package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class MedicalHistory extends DomainEntity {

    private String note;
    private String bloodGroup;
    private String allergy;
    private String incompatibilities;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getIncompatibilities() {
        return incompatibilities;
    }

    public void setIncompatibilities(String incompatibilities) {
        this.incompatibilities = incompatibilities;
    }

    // Relationships ----------------------------------------------------------
    private Patient patient;
    private Collection<Appointment> appointments;

    @Valid
    @NotNull
    @OneToMany(mappedBy = "medicalHistory")
    public Collection<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Collection<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Valid
    @NotNull
    @OneToOne(optional = false)
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
