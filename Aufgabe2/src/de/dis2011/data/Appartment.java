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
			int estateId = repo.findIdByForeignId("estate","AppartmentId",id);
			Appartment appartment = (Appartment) Estate.load(estateId);
			Map<String,Object> result = repo.load("house", "Id",id);
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
		Estate estate;
		int appId = getId();
		if (appId != -1) {
			int estateId = repo.findIdByForeignId("estate","AppartmentId",appId); 
			estate = Estate.load(estateId);
			if (estate == null) {
				System.out.println("Domain Model incoherent. Missing EstateRecord(parent) for HouseID: "+appId);
				return;
			}
			estate.setId(estateId);			
		} else {
			estate = new Estate();
		}
		estate.save();
		setId(repo.save("house","Id",appId,keysVals));
		
	}
	
	public void show() {
		super.show();
		System.out.println("Appartment Zusatzdaten:");
//		System.out.println("floors: "+getFloors());
//		System.out.println("price: "+getPrice()); 
//		System.out.println("garden: "+isGarden());

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
		repo.delete("house", "Id", getId());
		int estateId = repo.findIdByForeignId("estate","HouseId",getId());
		repo.delete("estate", "Id", estateId);
		
	}

}
