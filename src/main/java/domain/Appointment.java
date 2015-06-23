package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "isFinish") })
public class Appointment extends Slot {

    private String result;
    private String purpose;
    private boolean isFinish;

    @NotNull
    public boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    // Relationships ----------------------------------------------------------

    private MedicalHistory medicalHistory;
    private Collection<Prescription> prescriptions;
    private Offer offer;
    private Patient patient;
    private Specialist specialist;



    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(MedicalHistory medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Valid
    @ManyToOne(optional = true)
    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @Valid
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appointment")
    public Collection<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Collection<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }


}
