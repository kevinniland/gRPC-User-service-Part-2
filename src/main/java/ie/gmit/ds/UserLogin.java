package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.*;

/*
 *  This class is used solely to for validation/logging in a user. Since we log in a user using just their user ID
 *  user password, we only need variables and the related getters for the user ID and user password
 */
@XmlRootElement
public class UserLogin {
	private int uId;
	private String uPassword;

	public UserLogin() {
		super();
	}

	public UserLogin(int userId, String userPassword) {
		this.uId = userId;
		this.uPassword = userPassword;
	}

	public void setUserId(int userId) {
		this.uId = userId;
	}

	@JsonProperty
	@XmlElement
	public int getUserId() {
		return uId;
	}

	public void setUserPassword(String userPassword) {
		this.uPassword = userPassword;
	}

	@JsonProperty
	@XmlElement
	public String getUserPassword() {
		return uPassword;
	}
}
