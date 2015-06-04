package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

public class TimetableForm {

    private int id;
    private int version;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
