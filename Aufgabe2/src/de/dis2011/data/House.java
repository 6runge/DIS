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
			House house = new House();
			house.getEstateProperties(estateId);
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
	
	private void getEstateProperties(int estateId) {
		Estate estate = Estate.load(estateId);// TODO Auto-generated method stub
		this.setAgentId(estate.getAgentId());
		this.setApartmentId(estate.getApartmentId());
		this.setHouseId(estate.getHouseId());
		this.setCity(estate.getCity());
		this.setStreet(estate.getStreet());
		this.setStreetNumber(estate.getStreetNumber());
		this.setSquareArea(estate.getSquareArea());
		this.setZipCode(estate.getZipCode());
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

		int estateId = 0;
		int houseId = getId();
		if (houseId != -1) {
			estateId = repo.findIdByForeignId("estate","HouseId",houseId); 
			if (estateId == 0) {
				System.out.println("Domain Model incoherent. Missing EstateRecord(parent) for HouseID: "+houseId);
				return;
			}		
		} else {
			estateId = -1; 
		
		}
		houseId = repo.save("house","Id",houseId,keysVals);
		setId(estateId);
		setHouseId(houseId);
		super.save();
		setId(houseId);
		
		
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
		setGarden(FormUtil.readString("garden (y/n)").toLowerCase().equals("y")); 
	}

	public void delete() {
		DomainRepository repo = new DomainRepository();
		repo.delete("house", "Id", getId());
		int estateId = repo.findIdByForeignId("estate","HouseId",getId());
		repo.delete("estate", "Id", estateId);
		
	}

}
