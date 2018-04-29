package de.dis2011.data;

import java.util.HashMap;
import java.util.Map;

import de.dis2011.FormUtil;

public class Estate {
	private int id = -1;

	private Integer agentId;
	private Integer apartmentId; 
	private Integer houseId;
	private String city;
	private int zipCode;
	private String street;
	private int streetNumber;
	private int squareArea;	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public Integer getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(Integer apartmentId) {
		this.apartmentId = apartmentId;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}

	public int getSquareArea() {
		return squareArea;
	}

	public void setSquareArea(int squareArea) {
		this.squareArea = squareArea;
	}

	/**
	 * Lädt einen Makler aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
	public static Estate load(int id) {
			DomainRepository repo = new DomainRepository();
			Map<String,Object> result = repo.load("estate","Id",id);
			System.out.println(result);
			if (result != null) {
				Estate estate = new Estate();
				estate.setId(id);
				estate.setAgentId((Integer) result.get("agentid"));
				estate.setApartmentId((Integer) result.get("apartmentid")); 
				estate.setHouseId((Integer) result.get("houseid"));
				estate.setCity((String) result.get("city"));
				estate.setZipCode((Integer) result.get("zipcode"));
				estate.setStreet((String) result.get("street"));
				estate.setStreetNumber((Integer) result.get("streetnumber"));
				estate.setSquareArea((Integer) result.get("squarearea"));	

				return estate;
			}
			return null;
	}
	
	/**
	 * Speichert den Makler in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() {
		// TODO check for unique login
		DomainRepository repo = new DomainRepository();
		HashMap<String,Object> keysVals = new HashMap<String,Object>();
		keysVals.put("agentId",getAgentId());
		keysVals.put("apartmentId",getApartmentId());
		keysVals.put("houseId",getHouseId());
		keysVals.put("city",getCity());
		keysVals.put("zipCode",getZipCode());
		keysVals.put("street",getStreet());
		keysVals.put("streetNumber",getStreetNumber());
		keysVals.put("squareArea",getSquareArea());	
		id = repo.save("estate","Id",getId(),keysVals);
	}
	
	public void show() {
		System.out.println("Estate mit der ID "+id+":");
//		System.out.println("agentId: "+getAgentId());
//		System.out.println("apartmentId: "+getApartmentId()); 
//		System.out.println("houseId: "+getHouseId());
		System.out.println("city: "+getCity());
		System.out.println("zipCode: "+getZipCode());
		System.out.println("street: "+getStreet());
		System.out.println("streetNumber: "+getStreetNumber());
		System.out.println("squareArea: "+getSquareArea());	

	}
	
	public void read() {
//		setAgentId(FormUtil.readInt("agentId"));
//		setApartmentId(FormUtil.readInt("apartmentId")); 
//		setHouseId(FormUtil.readInt("houseId"));
		setCity(FormUtil.readString("city"));
		setZipCode(FormUtil.readInt("zipCode"));
		setStreet(FormUtil.readString("street"));
		setStreetNumber(FormUtil.readInt("streetNumber"));
		setSquareArea(FormUtil.readInt("squareArea"));	

	}

	public void delete() {
		DomainRepository repo = new DomainRepository();
		repo.delete("estate", "Id", id);
		
	}

}
