package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class FreeDayForm {
	
	private int id;
	private int version; 
	private String description;
	private Date finishMoment;
	private Date startMoment;

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date getStartMoment() {
        return startMoment;
    }

    public void setStartMoment(Date startMoment) {
        this.startMoment = startMoment;
    }
    
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFinishMoment() {
		return finishMoment;
	}

	public void setFinishMoment(Date finishMoment) {
		this.finishMoment = finishMoment;
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
