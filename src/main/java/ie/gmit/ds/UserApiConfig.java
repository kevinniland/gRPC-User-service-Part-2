package ie.gmit.ds;

import io.dropwizard.Configuration;

/*
 *  Instead of having this service connect to the password service in the constructor of the UserApiResource
 *  (and to have some functionality in this class), the IP and port that the service will connect on can be 
 *  defined here
 */
public class UserApiConfig extends Configuration {
//	private String passwordServiceIP = "localhost";
	private int passwordServicePort = 50550;
	
//	public String getPasswordServiceIP() {
//		return passwordServiceIP;
//	}
	
	public int getPasswordServicePort() {
		return passwordServicePort;
	}
}
