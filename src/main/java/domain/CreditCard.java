package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard{

	private String holderName;

	private String brandName;

	private String number;

	private int expirationMonth;

	private int expirationYear;

	private int cVVcode;
	
	
	
	@NotBlank
	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	
	@NotBlank
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@CreditCardNumber
	@Column(unique=true)
	@NotBlank
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Range(min = 1, max = 12)
	public int getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	
	@Range(min = 2014, max = 9999)
	public int getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(int expirationYear) {
		this.expirationYear = expirationYear;
	}

	@Range(min = 100, max = 999)
	public int getcVVcode() {
		return cVVcode;
	}

	public void setcVVcode(int cVVcode) {
		this.cVVcode = cVVcode;
	}

	

}
