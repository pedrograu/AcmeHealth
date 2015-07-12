package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class PatientForm3 {


    private int id;
    private int version;
    private String secondPassword;
    private String newPassword;
    private String oldPassword;



    
    
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
    
    
    @NotNull
	public String getSecondPassword() {
		return secondPassword;
	}
	public void setSecondPassword(String secondPassword) {
		this.secondPassword = secondPassword;
	}
	
	@Size(min = 5, max = 32)
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	@Size(min = 5, max = 32)
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	} 
   

}
