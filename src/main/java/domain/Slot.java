package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "startMoment"),@Index(columnList = "finishMoment") })
public abstract class Slot extends DomainEntity {

    private Date startMoment;
    private Date finishMoment;


    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getStartMoment() {
        return startMoment;
    }

    public void setStartMoment(Date startMoment) {
        this.startMoment = startMoment;
    }
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getFinishMoment() {
        return finishMoment;
    }

    public void setFinishMoment(Date finishMoment) {
        this.finishMoment = finishMoment;
    }

    //RelationShips..................................

//    private Specialist specialist;
//    
//    @Valid
//    @NotNull
//    @ManyToOne(optional=false)
//    public Specialist getSpecialist() {
//        return specialist;
//    }
//
//    public void setSpecialist(Specialist specialist) {
//        this.specialist = specialist;
//    }
}
