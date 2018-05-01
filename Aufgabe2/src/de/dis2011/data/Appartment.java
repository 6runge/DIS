package de.dis2011.data;

import java.util.HashMap;
import java.util.Map;

import de.dis2011.FormUtil;

public class Appartment extends Estate {
	private int floor;
	private int rent;
	private int rooms;
	private boolean balcony;
	private boolean kitchen;
	
	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}

	public boolean isKitchen() {
		return kitchen;
	}

	public void setKitchen(boolean kitchen) {
		this.kitchen = kitchen;
	}

	/**
	 * Lädt einen Makler aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
	public static Appartment load(int id) {
			
			DomainRepository repo = new DomainRepository();
			int estateId = repo.findIdByForeignId("estate","ApartmentId",id);
			Appartment appartment = new Appartment();

			Map<String,Object> result = repo.load("apartment", "Id",id);
			if (result != null) {				
				appartment.setId(id);
				appartment.setFloor((Integer) result.get("floor"));
				appartment.setRent((Integer) result.get("rent"));
				appartment.setRooms((Integer) result.get("rooms"));
				appartment.setBalcony(!((Integer) result.get("balcony") == 0));
				appartment.setKitchen(!((Integer) result.get("kitchen") == 0));				
				
				return appartment;
			}
			return null;
	}
	
	/**
	 * Speichert den Makler in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() {
		DomainRepository repo = new DomainRepository();
		HashMap<String,Object> keysVals = new HashMap<String,Object>();
		keysVals.put("floor",getFloor());
		keysVals.put("rent",getRent()); 
		keysVals.put("rooms",getRooms());
		keysVals.put("balcony",isBalcony()?1:0);
		keysVals.put("kitchen",isKitchen()?1:0);
		
		int estateId = 0;
		int apartmentId = getId();
		if (apartmentId != -1) {
			estateId = repo.findIdByForeignId("estate","ApartmentId",apartmentId); 
			if (estateId == 0) {
				System.out.println("Domain Model incoherent. Missing EstateRecord(parent) for AppartmentID: "+apartmentId);
				return;
			}		
		} else {
			estateId = -1; 
		
		}
		apartmentId = repo.save("apartment","Id",apartmentId,keysVals);
		setId(estateId);
		setApartmentId(apartmentId);
		super.save();
		setId(apartmentId);
		
	}
	
	public void show() {
		super.show();
		System.out.println("Appartment Zusatzdaten:");
		System.out.println("floor: "+getFloor());
		System.out.println("rent: "+getRent()); 
		System.out.println("balcony: "+isBalcony());

	}
	
	public void read() {
		super.read();
		setFloor(FormUtil.readInt("floor"));
		setRent(FormUtil.readInt("rent"));
		setRooms(FormUtil.readInt("rooms"));
		setBalcony(FormUtil.readString("balcony (y/n)").toLowerCase() == "y"); 
		setKitchen(FormUtil.readString("kitchen (y/n)").toLowerCase() == "y");

	}

	public void delete() {
		DomainRepository repo = new DomainRepository();
		int estateId = repo.findIdByForeignId("estate","ApartmentId",getId());
		repo.delete("estate", "Id", estateId);
		repo.delete("apartment", "Id", getId());
		
		
	}

}
