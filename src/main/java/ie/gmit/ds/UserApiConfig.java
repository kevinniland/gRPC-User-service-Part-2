package ie.gmit.ds;

import java.util.Scanner;

import io.dropwizard.Configuration;

/*
 *  Instead of having this service connect to the password service in the constructor of the UserApiResource
 *  (and to have some functionality in this class), the IP and port that the service will connect on can be 
 *  defined here
 */
public class UserApiConfig extends Configuration {
	private Scanner scanner = new Scanner(System.in);

	private int passwordServicePort;
	private boolean isConnected = false;

	public int getPasswordServicePort() {
		return passwordServicePort;
	}

	/* 
	 * Tried adding a way to catch the exception thrown by entering a port number between 50,000 and 59,999
	 * but application hanged no matter what I tried. As a result, you MUST enter the correct port number
	 */
	public UserApiConfig() {
		do {
			while ((passwordServicePort < 50000 || passwordServicePort > 59999)) {
				System.out.println("\nEnter port number the password service is running on: ");
				passwordServicePort = scanner.nextInt();

				if (passwordServicePort < 50000 || passwordServicePort > 59999) {
					System.out.println("\nERROR: Invlaid port number entered. Please try again\n");
				} else {
					isConnected = true;
					
					System.out.println("\nSuccessfully connected to password service");
				}
			}
		} while (isConnected == false);
	}
}
