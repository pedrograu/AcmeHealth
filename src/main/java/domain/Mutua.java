package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Mutua extends DomainEntity {

    private String token;


    @NotBlank
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
