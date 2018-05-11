package de.dis2018;

import de.dis2018.authentication.EstateAgentAuthenticator;
import de.dis2018.authentication.PropertiesFileAuthenticator;
import de.dis2018.core.EstateService;
import de.dis2018.editor.EstateEditor;
import de.dis2018.editor.EstateAgentEditor;
import de.dis2018.editor.PersonEditor;
import de.dis2018.editor.ContractEditor;
import de.dis2018.menu.Menu;

/**
 * Main class showing the main menu
 */
public class Main {
	private static EstateService service;
	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
		service = new EstateService();
		showMainMenu();
		System.exit(0);
	}
	
	/**
	 * Shows the main menu
	 */
	public static void showMainMenu() {
		//Menu options
		final int MENU_ESTATE = 0;
		final int MENU_PERSON= 1;
		final int MENU_AGENT = 2;
		final int MENU_CONTRACT = 3;
		final int QUIT = 4;
		
		//Create menu
		Menu mainMenu = new Menu("Main menu");
		mainMenu.addEntry("Agent-Management", MENU_ESTATE);
		mainMenu.addEntry("Person-Management", MENU_PERSON);
		mainMenu.addEntry("Estate-Management", MENU_AGENT);
		mainMenu.addEntry("Contract-Menu", MENU_CONTRACT);
		mainMenu.addEntry("QUIT", QUIT);
		
		//authentication options
		PropertiesFileAuthenticator pfa = new PropertiesFileAuthenticator("admin.properties");
		EstateAgentAuthenticator ma = new EstateAgentAuthenticator(service);
		
		//Test data
		service.addTestData();
		
		//Processed input
		while(true) {
			int response = mainMenu.show();
			
			switch(response) {
				case MENU_ESTATE:
					if(pfa.authenticate()) {
						EstateAgentEditor me = new EstateAgentEditor(service);
						me.showEstateAgentMenu();
					}
					break;
				case MENU_PERSON:
					if(ma.authenticate()) {
						PersonEditor pe = new PersonEditor(service);
						pe.showPersonMenu();
					}
					break;
				case MENU_AGENT:
					if(ma.authenticate()) {
						EstateEditor ie = new EstateEditor(service, ma.getLastAuthenticatedEstateAgent());
						ie.showEstateMenu();
					}
					break;
				case MENU_CONTRACT:
					if(ma.authenticate()) {
						ContractEditor ve = new ContractEditor(service, ma.getLastAuthenticatedEstateAgent());
						ve.showContractMenu();
					}
					break;
				case QUIT:
					return;
			}
		}
	}
}
