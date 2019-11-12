package ie.gmit.ds;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	@NotNull
    private int userId;
	
	@NotBlank
    private String userName;
    
	@NotBlank
    private String userEmail;
    
	@NotBlank
    private String userPassword;


    public User() {
        // Needed for Jackson deserialisation
    }

    public User(int userId, String userName, String userEmail, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    @JsonProperty
	public int getUserId() {
		return userId;
	}

    @JsonProperty
	public String getUserName() {
		return userName;
	}

    @JsonProperty
	public String getUserEmail() {
		return userEmail;
	}

    @JsonProperty
	public String getUserPassword() {
		return userPassword;
	}
}
