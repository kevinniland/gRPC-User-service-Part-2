package ie.gmit.ds;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 *  This class is used solely to for 'sending' user data. Initially, I had just one class for sending and receiving 
 *  user data through/from requests. However, as certain variables were being returned (albeit they were null) when
 *  they shouldn't have been returned, the initial User class was split into this class (User) for 'sending' User data
 *  and UserHash for receiving user data
 */
@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class UserSendData {
	@NotNull
	private int userId;

	@NotNull
	private String userName;

	@NotNull
	private String userEmail;

	@NotNull
	private String userPassword;

	public UserSendData() {
		// Needed for Jackson deserialisation
		super();
	}

	public UserSendData(int userId, String userName, String userEmail, String userPassword) {
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@JsonProperty
	@XmlElement // (name = "userId")
	public int getUserId() {
		return userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty
	@XmlElement // (name = "userName")
	public String getUserName() {
		return userName;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@JsonProperty
	@XmlElement // (name = "userEmail")
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@JsonProperty
	@XmlElement // (name = "userPassword")
	public String getUserPassword() {
		return userPassword;
	}
}
