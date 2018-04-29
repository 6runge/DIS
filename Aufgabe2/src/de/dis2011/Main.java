package de.dis2011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.dis2011.data.DomainRepository;
import de.dis2011.data.EstateAgent;

/**
 * Hauptklasse
 */
public class Main {
	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
		showMainMenu();
	}

	/**
	 * Zeigt das Hauptmenü
	 */
	public static void showMainMenu() {
		//Menüoptionen
		final int MENU_MAKLER = 0;
		final int MENU_ESTATE = 1;
		final int QUIT = 2;

		//Erzeuge Menü
		Menu mainMenu = new Menu("Hauptmenü");
		mainMenu.addEntry("Makler-Verwaltung", MENU_MAKLER);
		mainMenu.addEntry("Immobilienverwaltung", MENU_ESTATE);
		mainMenu.addEntry("Beenden", QUIT);

		//Verarbeite Eingabe
		while(true) {
			int response = mainMenu.show();

			switch(response) {
			case MENU_MAKLER:
				showMaklerMenu();
				break;
			case MENU_ESTATE:
				showEstateMenu();
				break;
			case QUIT:
				return;
			}
		}
	}

	private static void showEstateMenu() {
		//Menüoptionen
		final int NEW_ESTATE = 0;
		final int SHOW_ESTATE = 1;
		final int UPDATE_ESTATE = 2;
		final int DELETE_ESTATE = 3;
		final int BACK = 4;
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		//agent login
		String login = "";
		System.out.print("Makler-Login? ");
		try {
			login = stdin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		EstateAgent makler = EstateAgent.loadByLogin(login);
		if (makler == null) {
			System.out.println("");
			System.out.println("Makler mit dem Login "+login+" konnte nicht geladen werden.");
			return;
		}
		System.out.println("");
		System.out.print("Passwort? ");
		String pwin = "";
		try {
			pwin = stdin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!pwin.equals(makler.getPassword())) {
			System.out.println("");
			System.out.println("Falsches Passwort, du Trottel!");
		}
		//Immobilienverwaltungsmenü
		Menu estateMenu = new Menu("Immobilien-Verwaltung");
		estateMenu.addEntry("Neue Immobilie", NEW_ESTATE);
		estateMenu.addEntry("Zeige Immobilie", SHOW_ESTATE);
		estateMenu.addEntry("Bearbeite Immobilie", UPDATE_ESTATE);
		estateMenu.addEntry("Lösche Immobilie", DELETE_ESTATE);
		estateMenu.addEntry("Zurück zum Hauptmenü", BACK);

		//Verarbeite Eingabe
		while(true) {
			int response = estateMenu.show();

			switch(response) {
			case NEW_ESTATE:
				newEstate();
				break;
			case SHOW_ESTATE:
				System.out.print("Estate Id? ");
				try {
					int id = Integer.parseInt(stdin.readLine());
					showEstate(id);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case UPDATE_ESTATE:
				System.out.print("Estate Id? ");
				try {
					int id = Integer.parseInt(stdin.readLine());
					updateEstate(id);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case DELETE_ESTATE:
				System.out.print("Estate Id? ");
				try {
					int id = Integer.parseInt(stdin.readLine());
					deleteEstate(id);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case BACK:
				return;
			}
		}
	}

	/**
	 * Zeigt die Maklerverwaltung
	 * @throws NumberFormatException 
	 */
	public static void showMaklerMenu() throws NumberFormatException {
		//Menüoptionen
		final int NEW_MAKLER = 0;
		final int SHOW_MAKLER = 1;
		final int UPDATE_MAKLER = 2;
		final int DELETE_MAKLER = 3;
		final int BACK = 4;
		System.out.print("Password? ");
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		try {
			if (!stdin.readLine().equals("hardcore")) {
				System.out.println("");
				System.out.println("Wrong password, moron!");
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Maklerverwaltungsmenü
		Menu maklerMenu = new Menu("Makler-Verwaltung");
		maklerMenu.addEntry("Neuer Makler", NEW_MAKLER);
		maklerMenu.addEntry("Zeige Makler", SHOW_MAKLER);
		maklerMenu.addEntry("Bearbeite Makler", UPDATE_MAKLER);
		maklerMenu.addEntry("Lösche Makler", DELETE_MAKLER);
		maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);

		//Verarbeite Eingabe
		while(true) {
			int response = maklerMenu.show();

			switch(response) {
			case NEW_MAKLER:
				newMakler();
				break;
			case SHOW_MAKLER:
				System.out.print("Estate Agent Id? ");
				try {
					int id = Integer.parseInt(stdin.readLine());
					showMakler(id);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case UPDATE_MAKLER:
				System.out.print("Estate Agent Id? ");
				try {
					int id = Integer.parseInt(stdin.readLine());
					updateMakler(id);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case DELETE_MAKLER:
				System.out.print("Estate Agent Id? ");
				try {
					int id = Integer.parseInt(stdin.readLine());
					deleteMakler(id);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case BACK:
				return;
			}
		}
	}

	private static void deleteMakler(int id) {
		EstateAgent agent = EstateAgent.load(id);
		if (agent == null) {
			System.out.println("Makler mit der ID "+id+" existiert nicht oder konnte nicht geladen werden.");
			return;
		}	
		agent.show();
		System.out.print("Makler löschen? ");
		String str = FormUtil.readString("Makler löschen? (y/n) ");
		if (str.toLowerCase().equals("y")) {
			agent.delete();
		}
	}

	/**
	 * Legt einen neuen Makler an, nachdem der Benutzer
	 * die entprechenden Daten eingegeben hat.
	 */
	public static void newMakler() {
		EstateAgent m = new EstateAgent();

		m.read();
		m.save();

		System.out.println("Makler mit der ID "+m.getId()+" wurde erzeugt.");
	}
	public static void updateMakler(int id){
		EstateAgent agent = EstateAgent.load(id);
		if (agent == null) {
			System.out.println("Makler mit der ID "+id+" existiert nicht oder konnte nicht geladen werden.");
			return;
		}
		agent.show();
		agent.read();
		agent.save();

		System.out.println("Makler mit der ID "+agent.getId()+" wurde bearbeitet.");

	}

	public static void showMakler(int id) {
		EstateAgent agent = EstateAgent.load(id);

		if (agent == null) {
			System.out.println("Makler mit der ID "+id+" existiert nicht oder konnte nicht geladen werden.");
			return;
		}
		agent.show();


	}
}
