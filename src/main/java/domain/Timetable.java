package domain;

import java.util.Collection;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "day") })
public class Timetable extends DomainEntity {

    private Date startShift;
    private Date endShift;
    private Integer day;

    @NotNull
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    public Date getStartShift() {
        return startShift;
    }

    public void setStartShift(Date startShift) {
        this.startShift = startShift;
    }

    @NotNull
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    public Date getEndShift() {
        return endShift;
    }

    public void setEndShift(Date endShift) {
        this.endShift = endShift;
    }

    @NotNull
    @Range(min = 1, max = 7)
    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    //RelationShip.....................

    private Specialist specialist;
    private Collection<Appointment> appointments;

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
    @OneToMany(mappedBy = "timetable")
    public Collection<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Collection<Appointment> appointments) {
        this.appointments = appointments;
    }

}
