package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.*;

/*
 *  This class is used solely to for validation/logging in a user. Since we log in a user using just their user ID
 *  user password, we only need variables and the related getters for the user ID and user password
 */
@XmlRootElement
public class UserLogin {
	private int userId;
	private String userPassword;

	public UserLogin() {
		super();
	}

	public UserLogin(int userId, String userPassword) {
		this.userId = userId;
		this.userPassword = userPassword;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@JsonProperty
	@XmlElement
	public int getUserId() {
		return userId;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@JsonProperty
	@XmlElement
	public String getUserPassword() {
		return userPassword;
	}
}
