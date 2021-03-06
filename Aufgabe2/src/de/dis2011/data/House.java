package de.dis2011.data;

import java.util.HashMap;
import java.util.Map;

import de.dis2011.FormUtil;

public class House extends Estate {

	private int floors;
	private int price;
	private boolean garden;

	public int getFloors() {
		return floors;
	}

	public void setFloors(int floors) {
		this.floors = floors;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isGarden() {
		return garden;
	}

	public void setGarden(boolean garden) {
		this.garden = garden;
	}

	/**
	 * Lädt einen Makler aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
	public static House load(int id) {
			
			DomainRepository repo = new DomainRepository();
			int estateId = repo.findIdByForeignId("estate","HouseId",id);
			House house = (House) Estate.load(estateId);
			Map<String,Object> result = repo.load("house", "Id",id);
			if (result != null) {				
				house.setId(id);
				house.setFloors((Integer) result.get("floors"));
				house.setPrice((Integer) result.get("price"));
				house.setGarden(!((Integer) result.get("garden") == 0));
				return house;
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
		keysVals.put("floors",getFloors());
		keysVals.put("price",getPrice()); 
		keysVals.put("garden",isGarden()?1:0);
		Estate estate;
		int houseId = getId();
		if (houseId != -1) {
			int estateId = repo.findIdByForeignId("estate","HouseId",houseId); 
			estate = Estate.load(estateId);
			if (estate == null) {
				System.out.println("Domain Model incoherent. Missing EstateRecord(parent) for HouseID: "+houseId);
				return;
			}
			estate.setId(estateId);			
		} else {
			estate = new Estate();
		}
		estate.save();
		setId(repo.save("house","Id",houseId,keysVals));
		
	}
	
	public void show() {
		super.show();
		System.out.println("Haus Zusatzdaten:");
		System.out.println("floors: "+getFloors());
		System.out.println("price: "+getPrice()); 
		System.out.println("garden: "+isGarden());

	}
	
	public void read() {
		super.read();
		setFloors(FormUtil.readInt("floors"));
		setPrice(FormUtil.readInt("price"));
		setGarden(FormUtil.readString("garden (y/n)").toLowerCase() == "y"); 

	}

	public void delete() {
		DomainRepository repo = new DomainRepository();
		repo.delete("house", "Id", getId());
		int estateId = repo.findIdByForeignId("estate","HouseId",getId());
		repo.delete("estate", "Id", estateId);
		
	}

	
	
	public House() {
		// TODO Auto-generated constructor stub
	}

}
