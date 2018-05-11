package de.dis2018.authentication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import de.dis2018.util.FormUtil;

/**
 * Authenticates a user based on data from a properties file. 
 * The file must contain the two entries username=... and password=....
 */
public class PropertiesFileAuthenticator implements Authenticator {
	private String username;
	private String password;
	
	/**
	 * Constructor.
	 * @param propertiesFile The file name of the properties file containing user data.
	 */
	public PropertiesFileAuthenticator(String propertiesFile) {
		try {
			Properties properties = new Properties();
			URL url = ClassLoader.getSystemResource(propertiesFile);
			FileInputStream stream = new FileInputStream(new File(url.toURI()));
			properties.load(stream);
			stream.close();
			
			this.username = properties.getProperty("username");
			this.password = properties.getProperty("password");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prompts for user name and password and compares with the data 
	 * from the properties file
	 */
	public boolean authenticate() {
		String username = FormUtil.readString("Username");
		String password = FormUtil.readPassword("Password");
		
		if(this.username.equals(username) && this.password.equals(password)) {
			return true;
		} else {
			FormUtil.showMessage("Wrong username or password!");
			return false;
		}
	}

}
