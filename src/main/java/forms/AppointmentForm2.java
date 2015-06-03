package forms;

import javax.validation.constraints.NotNull;



public class AppointmentForm2 {

	
	private int id;
	private int version;
	
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
}
