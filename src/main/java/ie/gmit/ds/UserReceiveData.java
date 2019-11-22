package ie.gmit.ds;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;


/*
 *  This class is used solely to for validation/logging in a user. Since we log in a user using just their user ID
 *  user password, we only need variables and the related getters for the user ID and user password
 */

@XmlRootElement
public class UserReceiveData {
	private int userId;
	private String userName;
	private String userEmail;
	private String userHashedPassword;
	private String userSalt;

	public UserReceiveData(int userId, String userName, String userEmail, String userHashedPassword, String userSalt) {
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userHashedPassword = userHashedPassword;
		this.userSalt = userSalt;
	}

	// Unlike the 'User' class, no need to have @JsonProperty annotations - this
	// class doesn't

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@JsonProperty
	@XmlElement 
	public int getUserId() {
		return userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty
	@XmlElement 
	public String getUserName() {
		return userName;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@JsonProperty
	@XmlElement 
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserHashedPassword(String userHashedPassword) {
		this.userHashedPassword = userHashedPassword;
	}

	@JsonProperty
	@XmlElement
	public String getUserHashedPassword() {
		return userHashedPassword;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}

	@JsonProperty
	@XmlElement 
	public String getUserSalt() {
		return userSalt;
	}
}
