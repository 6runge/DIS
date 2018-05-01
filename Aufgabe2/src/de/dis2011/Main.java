package de.dis2011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.ibm.db2.jcc.b.SqlException;

import de.dis2011.data.Appartment;
import de.dis2011.data.Contract;
import de.dis2011.data.DomainRepository;
import de.dis2011.data.Estate;
import de.dis2011.data.EstateAgent;
import de.dis2011.data.House;
import de.dis2011.data.Person;
import de.dis2011.data.PurchaseContract;
import de.dis2011.data.TenancyContract;

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

	private static EstateAgent makler;
	/**
	 * Zeigt das Hauptmenü
	 */
	public static void showMainMenu() {
		//Menüoptionen
		final int MENU_MAKLER = 0;
		final int MENU_ESTATE = 1;
		final int MENU_CONTRACT = 2;
		final int QUIT = 3;

		//Erzeuge Menü
		Menu mainMenu = new Menu("Hauptmenü");
		mainMenu.addEntry("Makler-Verwaltung", MENU_MAKLER);
		mainMenu.addEntry("Immobilienverwaltung", MENU_ESTATE);
		mainMenu.addEntry("Vertragsverwaltung", MENU_CONTRACT);
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
			case MENU_CONTRACT:
				showContractMenu();
				break;								
			case QUIT:
				return;
			}
		}
	}
	
	private static void showContractMenu() {
		//Menüoptionen
		final int NEW_PERSON = 0;
		final int NEW_CONTRACT = 1;
		final int SHOW_CONTRACTS = 2;
		final int BACK = 3;
		
		//BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		//Vertragsmenü
		Menu contractMenu = new Menu("Immobilien-Verwaltung");
		contractMenu.addEntry("Neue Person", NEW_PERSON);
		contractMenu.addEntry("Neuer Vertrag", NEW_CONTRACT);
		contractMenu.addEntry("Zeige Verträge", SHOW_CONTRACTS);
		contractMenu.addEntry("Zurück zum Hauptmenü", BACK);
		
		//Verarbeite Eingabe
				while(true) {
					int response = contractMenu.show();

					switch(response) {
					case NEW_PERSON:
						newPerson();
						break;
					case NEW_CONTRACT:
						newContract();
						break;
					case SHOW_CONTRACTS:
						showContracts();
						break;
					case BACK:
						return;
					}
				}
	}

	private static void showContracts() {

		List<Contract> contracts;
		contracts = Contract.loadAll();
		
		for (Contract contract : contracts) {
			Contract contractTemp = new Contract(); 
			if (contract.getTenancyContractId() != null) {
				contractTemp = TenancyContract.load(contract.getTenancyContractId());
			} else if (contract.getPurchaseContractId() != null) {
				contractTemp = PurchaseContract.load(contract.getPurchaseContractId());
			}
			//int tempId = contractTemp.getId();
			contractTemp.setId(contract.getId());
			contractTemp.show();
		}
	}

	private static void newContract() {
		Contract contract = new Contract();
		String idGetterHelper = "PurchaseContractId";
		String type = FormUtil.readString("(T)enancy or (P)urchase?").toLowerCase();
		while (!(type.equals("t") || type.equals("p")) ) {
			System.out.println("Falsche Eingabe!");
			type = FormUtil.readString("(T)enancy or (P)urchase?").toLowerCase();
		}
		if (type.equals("t")) {
			contract = new TenancyContract();
			idGetterHelper = "TenancyContractId";
		} else {
			contract = new PurchaseContract();
		}
		contract.read();
		contract.save();

		int cId = -1;
		DomainRepository repo = new DomainRepository();
		cId = repo.findIdByForeignId("contract",idGetterHelper,contract.getId());
		System.out.println("Vertrag mit der ID "+ String.valueOf(cId) +" wurde erzeugt.");
		
	}

	private static void newPerson() {
		Person person = new Person();

		person.read();
		person.save();

		System.out.println("Person mit der ID "+person.getId()+" wurde erzeugt.");
		
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
	private static void login() {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		String login = FormUtil.readString("Makler-Login");
		makler = EstateAgent.loadByLogin(login);
		if (makler == null) {
			System.out.println("");
			System.out.println("Makler mit dem Login "+login+" konnte nicht geladen werden.");
			return;
		}
		String pwin = FormUtil.readString("Passwort");

		if(!pwin.equals(makler.getPassword())) {
			System.out.println("");
			System.out.println("Falsches Passwort");
			return;
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
		if (makler == null) {
			login();
		}
		// For debugs
	//	makler = EstateAgent.load(1);
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

	private static void deleteEstate(int id) {
		Estate estate = Estate.load(id);
		if (estate == null) {
			System.out.println("Immobilie mit der ID "+id+" existiert nicht oder konnte nicht geladen werden.");
			return;
		}
		if (estate.getHouseId() != null) {
			estate = House.load(estate.getHouseId());
		} else if (estate.getApartmentId() != null) {
			estate = Appartment.load(estate.getApartmentId());
		}		
		estate.show();
		
		String str = FormUtil.readString("Immobilie löschen? (y/n) ");
		if (str.toLowerCase().equals("y")) {

			estate.delete();
		}
	}

	/**
	 * Legt eine neue Immobilie an, nachdem der Benutzer
	 * die entprechenden Daten eingegeben hat.
	 */
	public static void newEstate() {
		String idGetterHelper = "ApartmentId";
		String type = FormUtil.readString("(H)ouse or (A)ppartment?").toLowerCase();
		while (!(type.equals("h") || type.equals("a")) ) {
			System.out.println("Falsche Eingabe!");
			type = FormUtil.readString("(H)ouse or (A)ppartment?").toLowerCase();
		}
		Estate estate;
		if (type.equals("h")) {
			estate = new House();
			idGetterHelper = "HouseId";
		} else {
			estate = new Appartment();
		}
		estate.setAgentId(makler.getId());
		estate.read();
		estate.save();

		int eId = -1;
		DomainRepository repo = new DomainRepository();
		eId = repo.findIdByForeignId("estate",idGetterHelper,estate.getId());
		System.out.println("Estate mit der ID "+eId+" wurde erzeugt.");
	}
	public static void updateEstate(int id){
		Estate estate = Estate.load(id);
		if (estate == null) {
			System.out.println("Estate mit der ID "+id+" existiert nicht oder konnte nicht geladen werden.");
			return;
		}
		if (estate.getHouseId() != null) {
			estate = House.load(estate.getHouseId());
		} else if (estate.getApartmentId() != null) {
			estate = Appartment.load(estate.getApartmentId());
		}		
		estate.show();
		estate.read();
		estate.setAgentId(makler.getId());
		estate.save();
		
		System.out.println("Estate mit der ID "+id+" wurde bearbeitet.");

	}

	public static void showEstate(int id) {
		Estate estate = Estate.load(id);
		if (estate == null) {
			System.out.println("Estate mit der ID "+id+" existiert nicht oder konnte nicht geladen werden.");
			return;
		}
		if (estate.getHouseId() != null) {
			estate = House.load(estate.getHouseId());
		} else if (estate.getApartmentId() != null) {
			estate = Appartment.load(estate.getApartmentId());
		}
		estate.show();


	}

}
