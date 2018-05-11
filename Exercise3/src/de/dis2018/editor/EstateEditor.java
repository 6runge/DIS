package de.dis2018.editor;

import java.util.Set;

import de.dis2018.core.EstateService;
import de.dis2018.data.House;
import de.dis2018.data.EstateAgent;
import de.dis2018.data.Apartment;
import de.dis2018.menu.AppartmentSelectionMenu;
import de.dis2018.menu.HouseSelectionMenu;
import de.dis2018.menu.Menu;
import de.dis2018.util.FormUtil;

/**
 * Class for the menus for managing estates
 */
public class EstateEditor {
	//Estate service to be used
	private EstateService service;
	
	///Will be registered as manager for the estates
	private EstateAgent manager;
	
	public EstateEditor(EstateService service, EstateAgent manager) {
		this.service = service;
		this.manager = manager;
	}
	
	/**
	 * Shows the estate management
	 */
	public void showEstateMenu() {
		//Menüoptionen
		final int NEW_HOUSE = 0;
		final int EDIT_HOUSE = 1;
		final int DELETE_HOUSE = 2;
		final int NEW_APPARTMENT = 3;
		final int EDIT_APPARTMENT = 4;
		final int DELETE_APPARTMENT = 5;
		final int BACK = 6;
		
		//Estate agent management menu
		Menu maklerMenu = new Menu("Estate Management");
		maklerMenu.addEntry("New House", NEW_HOUSE);
		maklerMenu.addEntry("Edit House", EDIT_HOUSE);
		maklerMenu.addEntry("Delete House", DELETE_HOUSE);
		
		maklerMenu.addEntry("New Apartment", NEW_APPARTMENT);
		maklerMenu.addEntry("Edit Apartment", EDIT_APPARTMENT);
		maklerMenu.addEntry("Delete Apartment", DELETE_APPARTMENT);
		
		maklerMenu.addEntry("Back to Main Menu", BACK);
		
		//Process input
		while(true) {
			int response = maklerMenu.show();
			
			switch(response) {
				case NEW_HOUSE:
					newHouse();
					break;
				case EDIT_HOUSE:
					editHouse();
					break;
				case DELETE_HOUSE:
					deleteHouse();
					break;
				case NEW_APPARTMENT:
					newAppartment();
					break;
				case EDIT_APPARTMENT:
					editApartment();
					break;
				case DELETE_APPARTMENT:
					deleteApartment();
					break;
				case BACK:
					return;
			}
		}
	}
	
	/**
	 * Abfrage der Daten für ein neues House
	 */
	public void newHouse() {
		House h = new House();
		
		h.setCity(FormUtil.readString("City"));
		h.setPostalcode(FormUtil.readInt("Postalcode"));
		h.setStreet(FormUtil.readString("Street"));
		h.setStreetnumber(FormUtil.readString("Streetnumber"));
		h.setSquareArea(FormUtil.readInt("Square Area"));
		h.setFloors(FormUtil.readInt("Floors"));
		h.setPrice(FormUtil.readInt("Price"));
		h.setGarden(FormUtil.readBoolean("Garden"));
		h.setManager(this.manager);
		
		service.addHouse(h);
	}
	
	/**
	 * Edits a house after the user has selected it
	 */
	public void editHouse() {
		//Search all houses managed by the estate agent
		Set<House> haeuser = service.getAllHousesForEstateAgent(manager);
		
		//Selection menu for the house to be edited
		HouseSelectionMenu hsm = new HouseSelectionMenu("List of managed houses", haeuser);
		int id = hsm.show();
		
		//If the entry "back" was not selected, edit house
		if(id != HouseSelectionMenu.BACK) {
			//Load selected house
			House h = service.getHouseById(id);
			
			System.out.println("House "+h.getStreet()+" "+h.getStreetnumber()+", "+h.getPostalcode()+" "+h.getCity()+
					" is being edited. Empty fields or input of 0 leaves field unchanged");
			
			//Retrieve new data
			String newCity = FormUtil.readString("City ("+h.getCity()+")");
			int newPostalcode = FormUtil.readInt("Postalcode ("+h.getPostalcode()+")");
			String newStreet = FormUtil.readString("Street ("+h.getStreet()+")");
			String newHouseNummer = FormUtil.readString("Streetnumber ("+h.getStreetnumber()+")");
			int newSquareArea = FormUtil.readInt("SquareArea ("+h.getSquareArea()+")");
			int newFloors = FormUtil.readInt("Floors ("+h.getFloors()+")");
			int newPrice = FormUtil.readInt("Price ("+h.getPrice()+")");
			boolean newGarden = FormUtil.readBoolean("Garden ("+(h.isGarden() ? "y" : "n")+")");
			
			//Neue Daten setzen
			if(!newCity.equals(""))
				h.setCity(newCity);
			
			if(!newStreet.equals(""))
				h.setStreet(newStreet);
			
			if(!newHouseNummer.equals(""))
				h.setStreetnumber(newHouseNummer);
			
			if(newPostalcode != 0)
				h.setPostalcode(newPostalcode);
			
			if(newSquareArea != 0)
				h.setSquareArea(newSquareArea);
			
			if(newFloors != 0)
				h.setFloors(newFloors);
			
			if(newPrice != 0)
				h.setPrice(newPrice);
			
			h.setGarden(newGarden);
		}
	}
	
	/**
	 * Shows the list of managed houses and deletes the corresponding 
	 * house after selection
	 */
	public void deleteHouse() {
		//Search all houses managed by the estate agent
		Set<House> haeuser = service.getAllHousesForEstateAgent(manager);
		
		//Selection menu for the house to be edited
		HouseSelectionMenu hsm = new HouseSelectionMenu("List of managed houses", haeuser);
		int id = hsm.show();
		
		//If the entry "back" was not selected, delete House
		if(id != HouseSelectionMenu.BACK) {
			House h = service.getHouseById(id);
			service.deleteHouse(h);
		}
	}
	
	/**
	 * Requesting the data for a new apartment
	 */
	public void newAppartment() {
		Apartment w = new Apartment();
		
		w.setCity(FormUtil.readString("City"));
		w.setPostalcode(FormUtil.readInt("Postalcode"));
		w.setStreet(FormUtil.readString("Street"));
		w.setStreetnumber(FormUtil.readString("Streetnumber"));
		w.setSquareArea(FormUtil.readInt("Square Area"));
		w.setFloor(FormUtil.readInt("Floor"));
		w.setRent(FormUtil.readInt("Rent"));
		w.setKitchen(FormUtil.readBoolean("Kitchen"));
		w.setBalcony(FormUtil.readBoolean("Balcony"));
		w.setManager(this.manager);
		
		service.addApartment(w);
	}
	
	/**
	 * Edits an appartment after the user has selected it
	 */
	public void editApartment() {
		//Search all apartments managed by the estate agent
		Set<Apartment> apartmenten = service.getAllApartmentsForEstateAgent(manager);
		
		//Selection menu for the apartment to be edited
		AppartmentSelectionMenu asm = new AppartmentSelectionMenu("List of managed apartments", apartmenten);
		int id = asm.show();
		
		//If the entry "back" was not selected, edit apartment
		if(id != AppartmentSelectionMenu.BACK) {
			//Load apartment
			Apartment w = service.getApartmentByID(id);
			
			System.out.println("Apartment "+w.getStreet()+" "+w.getStreetnumber()+", "+w.getPostalcode()+" "+w.getCity()
					+ " is being edited. Empty fields remain unchanged.");
			
			//Retrieve new data
			String newcity = FormUtil.readString("City ("+w.getCity()+")");
			int newPostalcode = FormUtil.readInt("Postalcode ("+w.getPostalcode()+")");
			String newStreet = FormUtil.readString("Street ("+w.getStreet()+")");
			String newHouseNummer = FormUtil.readString("Streetnumber ("+w.getStreetnumber()+")");
			int newSquareArea = FormUtil.readInt("Square Area ("+w.getSquareArea()+")");
			int newFloor = FormUtil.readInt("Floor ("+w.getFloor()+")");
			int newRent = FormUtil.readInt("Rent ("+w.getRent()+")");
			boolean newEbk = FormUtil.readBoolean("Kitchen ("+(w.isKitchen() ? "y" : "n")+")");
			boolean newBalcony = FormUtil.readBoolean("Balcony ("+(w.isBalcony() ? "y" : "n")+")");
			
			//Neue Daten setzen
			if(!newcity.equals(""))
				w.setCity(newcity);
			
			if(!newStreet.equals(""))
				w.setStreet(newStreet);
			
			if(!newHouseNummer.equals(""))
				w.setStreetnumber(newHouseNummer);
			
			if(newPostalcode != 0)
				w.setPostalcode(newPostalcode);
			
			if(newSquareArea != 0)
				w.setSquareArea(newSquareArea);
			
			if(newFloor != 0)
				w.setFloor(newFloor);
			
			if(newRent != 0)
				w.setRent(newRent);
			
			w.setKitchen(newEbk);
			w.setBalcony(newBalcony);
		}
	}
	
	/**
	 * Shows the list of managed apartments and deletes the 
	 * corresponding apartment after selection.
	 */
	public void deleteApartment() {
		//Search all apartments managed by the estate agent
		Set<Apartment> apartments = service.getAllApartmentsForEstateAgent(manager);
		
		//Selection menu for the apartment to be edited
		AppartmentSelectionMenu asm = new AppartmentSelectionMenu("List of managed apartments", apartments);
		int id = asm.show();
		
		//If the entry "back" was not selected, delete apartment
		if(id != HouseSelectionMenu.BACK) {
			Apartment w = service.getApartmentByID(id);
			service.deleteApartment(w);
		}
	}
}
