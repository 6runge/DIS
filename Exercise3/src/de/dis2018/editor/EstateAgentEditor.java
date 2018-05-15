package de.dis2018.editor;

import de.dis2018.core.EstateService;
import de.dis2018.data.EstateAgent;
import de.dis2018.menu.EstateAgentSelectionMenu;
import de.dis2018.menu.Menu;
import de.dis2018.util.FormUtil;

/**
 * Class for the menus for managing estates
 */
public class EstateAgentEditor {
	///Estate service, to be used
	private EstateService service;
	
	public EstateAgentEditor(EstateService service) {
		this.service = service;
	}
	
	/**
	 * Shows the estate agent management
	 */
	public void showEstateAgentMenu() {
		//Menu options
		final int NEW_ESTATE_AGENT = 0;
		final int EDIT_ESTATE_AGENT = 1;
		final int DELETE_ESTATE_AGENT = 2;
		final int BACK = 3;
		
		//Estate agent management menu
		Menu maklerMenu = new Menu("Estate Agent Management");
		maklerMenu.addEntry("New Estate Agent", NEW_ESTATE_AGENT);
		maklerMenu.addEntry("Edit Estate Agent", EDIT_ESTATE_AGENT);
		maklerMenu.addEntry("Delete Estate Agent", DELETE_ESTATE_AGENT);
		maklerMenu.addEntry("Back to Main Menu", BACK);
		
		//Process input
		while(true) {
			int response = maklerMenu.show();
			
			switch(response) {
				case NEW_ESTATE_AGENT:
					newEstateAgent();
					break;
				case EDIT_ESTATE_AGENT:
					editEstateAgent();
					break;
				case DELETE_ESTATE_AGENT:
					deleteEstateAgent();
					break;
				case BACK:
					return;
			}
		}
	}
	
	/**
	 *  Creates a new estate agent after the 
	 *  user has entered the corresponding data.
	 */
	public void newEstateAgent() {
		EstateAgent m = new EstateAgent();
		
		m.setName(FormUtil.readString("Name"));
		m.setAddress(FormUtil.readString("Address"));
		String login = FormUtil.readString("Login");
		while (login.isEmpty() || loginUnavailable(login)) {
			login = FormUtil.readString("Login unavailable. Select a new one");
		}
		m.setLogin(login);
		m.setPassword(FormUtil.readString("Password"));
		service.addEstateAgent(m);
		
		System.out.println("Estate agent with ID "+m.getId()+" was created.");
	}
	
	/**
	 * Edits a estate agent after the user has selected it
	 */
	public void editEstateAgent() {
		//Menu for selecting the estate agent
		Menu maklerSelectionMenu = new EstateAgentSelectionMenu("Edit estate agent", service.getAllEstateAgents());
		int id = maklerSelectionMenu.show();
		
		//If not selected "back", edit estate agent
		if(id != EstateAgentSelectionMenu.BACK) {
			//Load estate agent
			EstateAgent m = service.getEstateAgentByID(id);
			System.out.println("Estate agent "+m.getName()+"  is being edited. Empty fields remain unchanged.");
			
			//Get the new data
			String new_name = FormUtil.readString("Name ("+m.getName()+")");
			String new_address = FormUtil.readString("Addresss ("+m.getAddress()+")");
			String new_login = FormUtil.readString("Login ("+m.getLogin()+")");
			while (loginUnavailable(new_login)) {
				new_login = FormUtil.readString("Login unavailable. Select a new one");
			}
			String new_password = FormUtil.readString("Password ("+m.getPassword()+")");
			
			//Set new data
			if(!new_name.equals(""))
				m.setName(new_name);
			if(!new_address.equals(""))
				m.setAddress(new_address);
			if(!new_login.equals(""))
				m.setLogin(new_login);
			if(!new_password.equals(""))
				m.setPassword(new_password);
			service.updateEstateAgent(m);
		}
	}
	
	private boolean loginUnavailable(String login) {
		return !(service.getEstateAgentByLogin(login) == null);
	}

	/**
	 *  Deletes an estate agent after the user has selected it.
	 */
	public void deleteEstateAgent() {
		//Menu for selecting the estate agent
		Menu maklerSelectionMenu = new EstateAgentSelectionMenu("Delete estate agent", service.getAllEstateAgents());
		int id = maklerSelectionMenu.show();
		
		//If not selected "back", delete estate agent
		if(id != EstateAgentSelectionMenu.BACK) {
			EstateAgent m = service.getEstateAgentByID(id);
			service.deleteEstateAgent(m);
		}
	}
}
