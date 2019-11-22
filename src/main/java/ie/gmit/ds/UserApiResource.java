package ie.gmit.ds;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.protobuf.ByteString;

/*
 *  Since we have declared the path "/users" at the top of our class, every other path defined within
 *  the class will be appended to the end of this path 
 */
@Path("/users")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class UserApiResource {
	UserClient userClient;

	int getId;
	String getPassword;

	String passwordHashed;
	String salt;

	int userIdInit = 1;
	String userNameInit = "kevinniland97";
	String userEmailInit = "kevinniland97@gmail.com";
	String userPasswordInit = "password";
	
	String passwordServiceIP = "localhost";

	/*
	 * https://stackoverflow.com/questions/7048745/what-is-the-difference-between-
	 * utf-8-and-iso-8859-1 https://en.wikipedia.org/wiki/ISO/IEC_8859
	 * 
	 * In regards to adding an encoding type as part of the new String
	 * passwordHashed and userSalt in this class' constructor, addUser method, and
	 * updateUserById method, I found that the encoding scheme ISO-8859-1 was needed
	 * for certain aspects of this project, specifically validation. I have tested
	 * validating i.e logging in a user, without this encoding type and also using
	 * other encoding schemes such UTF-8 and it does not seem to work
	 * 
	 * From testing, using the encoding type UTF-8 (or no encoding type for that
	 * matter), causes some operations (specifically validation in regards to this
	 * project) to not return the expected result. For example, when attempting to
	 * login as the pre-made user created in the constructor of this class, it will
	 * return false (user login was not valid) when in reality the user should have
	 * been validated
	 * 
	 * 
	 * http://hackage.haskell.org/package/bytestring
	 * https://developers.google.com/protocol-buffers/docs/reference/java/com/google
	 * /protobuf/ByteString
	 * 
	 * As to why ISO-8859-1 works, and UTF-8/no encoding type does not, I have found
	 * conflicting answers online. While one place mentions that ByteStrings are not
	 * designed for Unicode, which would then make it incompatible with UTF-8
	 * (Unicode Transformation Format), the Java docs mention several UTF related
	 * method details for ByteStrings. If the first link rings true, since UTF-8 is
	 * the dominant encoding for the World Wide Web since 2009, the respective
	 * Strings (passwordHashed and userSalt) might intrinsically use UTF-8. To be
	 * clear, this is just my thinking and may very well be wrong
	 */
	String encodingType = "ISO-8859-1";

	private HashMap<Integer, UserReceiveData> usersMap = new HashMap<>(); // Used to store user details
	private final Validator validator;

	public UserApiResource(Validator validator, int passwordServicePort) throws InterruptedException, UnsupportedEncodingException {
		this.validator = validator;

		try {
			userClient = new UserClient(passwordServiceIP, passwordServicePort);
		} catch (Exception exception) {
			System.out.println("ERROR: Unable to connect to the service on that port. Please make sure"
					+ " the port entered is the same as the port the password service is running on");
		}

		userClient.hashRequest(userIdInit, userPasswordInit);

		passwordHashed = new String(userClient.getUserPasswordHashed().toByteArray(), encodingType);
		salt = new String(userClient.getUserSalt().toByteArray(), encodingType);

//		Does not seem to work if the encoding scheme UTF-8 is used		
//		String passwordHashed = new String(userClient.getUserPasswordHashed().toByteArray(), "UTF-8");
//		String userSalt = new String(userClient.getUserSalt().toByteArray(), "UTF-8");

		// Add this user for the sake of having at least one user on 'startup'
		UserReceiveData userReceiveData = new UserReceiveData(userIdInit, userNameInit, userEmailInit, passwordHashed,
				salt);

		usersMap.put(userReceiveData.getUserId(), userReceiveData);
	}

	/*
	 * https://www.baeldung.com/java-bean-validation-not-null-empty-blank
	 * 
	 * The below code for constraints violations was adapted from the above link
	 */

	// Add a user
	@POST
	public Response addUser(UserSendData userSendData) throws InterruptedException, UnsupportedEncodingException {
		Set<ConstraintViolation<UserSendData>> constraintViolations = validator.validate(userSendData);

		if (constraintViolations.isEmpty()) {
			userClient.hashRequest(userSendData.getUserId(), userSendData.getUserPassword());

			passwordHashed = new String(userClient.getUserPasswordHashed().toByteArray(), encodingType);
			salt = new String(userClient.getUserSalt().toByteArray(), encodingType);

//			String passwordHashed = new String(userClient.getUserPasswordHashed().toByteArray(), "UTF-8");
//			String salt = new String(userClient.getUserSalt().toByteArray(), "UTF-8");

			UserReceiveData userReceiveData = new UserReceiveData(userSendData.getUserId(), userSendData.getUserName(),
					userSendData.getUserEmail(), passwordHashed, salt);

			usersMap.put(userReceiveData.getUserId(), userReceiveData);

			return Response.status(200).build();
		} else {
			return Response.status(400).entity("ERROR: User isn't valid. Please try again").build();
		}
	}

	// Get all users
	@GET
	public Collection<UserReceiveData> getUsers() {
		if (!usersMap.values().isEmpty()) {
			System.out.println("All users retrieved successfully");

			return usersMap.values();
		} else {
			System.out.println("No users found. Please add a user before trying to view a user");

			return usersMap.values();
		}
	}

	// Get user by ID
	@GET
	@Path("/{userId}")
	public UserReceiveData getUserById(@PathParam("userId") int userId) {
		return usersMap.get(userId);
	}

	// Update a user by ID
	@PUT
	@Path("/{userId}")
	public Response updateUserById(UserSendData userSendData)
			throws InterruptedException, UnsupportedEncodingException {
		Set<ConstraintViolation<UserSendData>> constraintViolations = validator.validate(userSendData);

		if (constraintViolations.isEmpty()) {
			usersMap.remove(userSendData.getUserId());

			getId = userSendData.getUserId();
			getPassword = userSendData.getUserPassword();

			userClient.hashRequest(userSendData.getUserId(), userSendData.getUserPassword());

			passwordHashed = new String(userClient.getUserPasswordHashed().toByteArray(), encodingType);
			salt = new String(userClient.getUserSalt().toByteArray(), encodingType);

//			String passwordHashed = new String(userClient.getUserPasswordHashed().toByteArray(), "UTF-8");
//			String userSalt = new String(userClient.getUserSalt().toByteArray(), "UTF-8");

			// Add this user for the sake of having at least one user on 'startup'
			UserReceiveData userReceiveData = new UserReceiveData(userSendData.getUserId(), userSendData.getUserName(),
					userSendData.getUserEmail(), passwordHashed, salt);

			usersMap.put(userReceiveData.getUserId(), userReceiveData);

			System.out.println("User updated successfully");

			return Response.status(200).entity("User updated successfully").build();
		} else {
			System.out.println("ERROR: Unable to update user");

			return Response.status(400).entity("ERROR: Unable to update user").build();
		}
	}

	// Delete user by ID
	@DELETE
	@Path("/{userId}")
	public Collection<UserReceiveData> deleteUserById(@PathParam("userId") int userId) {
		if (!usersMap.values().isEmpty()) {
			usersMap.remove(userId);

			System.out.println("User deleted successfully");

			return usersMap.values();
		} else {
			System.out.println("ERROR: Unable to delete user");

			return usersMap.values();
		}
	}

	/*
	 * Logins in a user. Done using a separate UserLogin class, which contains
	 * getters for just a user ID and user password
	 */
	@POST
	@Path("/login")
	public Response loginUser(UserLogin userLogin) throws InterruptedException, UnsupportedEncodingException {
		Set<ConstraintViolation<UserLogin>> constraintViolations = validator.validate(userLogin);

		if (constraintViolations.isEmpty()) {
			UserReceiveData userReceiveData = usersMap.get(userLogin.getUserId());

			byte[] passwordHashedArr = userReceiveData.getUserHashedPassword().getBytes();
			byte[] saltArr = userReceiveData.getUserSalt().getBytes();

			ByteString passwordByteArr = ByteString.copyFrom(passwordHashedArr);
			ByteString saltByteArr = ByteString.copyFrom(saltArr);

			boolean isLoggedIn = userClient.passwordValidation(userLogin.getUserPassword(), passwordByteArr,
					saltByteArr);

			System.out.println("User logged in successfully: " + isLoggedIn);

			return Response.status(200).entity("User logged in successfully: " + isLoggedIn).build();
		} else {
			System.out.println("User logged in successfully: " + false);

			return Response.status(400).entity("User logged in successfully: " + false).build();
		}
	}
}
