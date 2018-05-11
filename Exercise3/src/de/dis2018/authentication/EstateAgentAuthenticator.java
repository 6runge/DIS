package de.dis2018.authentication;

import de.dis2018.core.EstateService;
import de.dis2018.data.EstateAgent;
import de.dis2018.util.FormUtil;

/**
 * Authenticates an agent
 */
public class EstateAgentAuthenticator implements Authenticator {
	private EstateService service;
	private EstateAgent lastAuthenticatedMakler;
	
	/**
	 * Constructor
	 * @param service Estate agent service to find the appropriate broker
	 */
	public EstateAgentAuthenticator(EstateService service) {
		this.service = service;
	}
	
	/**
	 * Returns the agent object to the last successfully authenticated agent
	 */
	public EstateAgent getLastAuthenticatedEstateAgent() {
		return this.lastAuthenticatedMakler;
	}
	
	/**
	 * Requests agent login and password and checks the input
	 */
	public boolean authenticate() {
		boolean ret;
		
		String login = FormUtil.readString("Username");
		String password = FormUtil.readPassword("Password");
		
		EstateAgent m = service.getEstateAgentByLogin(login);
		
		if(m == null)
			ret = false;
		else
			ret = password.equals(m.getPassword());
		
		lastAuthenticatedMakler = m;
		
		if(!ret)
			FormUtil.showMessage("Wrong username or password!");
		
		return ret;
	}
}
