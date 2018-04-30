package de.dis2011.data;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import de.dis2011.FormUtil;

public class Person {
	private int id = -1;

	private String fName;
	private String lName;
	private String address;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getFName() {
		return fName;
	}

	public void setFName(String fName) {
		this.fName = fName;
	}

	public String getLName() {
		return lName;
	}

	public void setLName(String lName) {
		this.lName = lName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void getPersonProperties(int personId) {
		Person person = Person.load(personId);// TODO Auto-generated method stub
		setFName(person.getFName());
		setLName(person.getLName()); 
		setAddress(person.getAddress());
	}
	/**
	 * Lädt eine Person aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
	public static Person load(int id) {
			DomainRepository repo = new DomainRepository();
			Map<String,Object> result = repo.load("person","Id",id);			
			if (result != null) {
				Person person = new Person();
				person.setId(id);
				person.setFName((String) result.get("fname"));
				person.setLName((String) result.get("lname"));
				person.setAddress((String) result.get("address"));

				return person;
			}
			return null;
	}
	
	/**
	 * Speichert die Person in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() {
		// TODO check for unique login
		DomainRepository repo = new DomainRepository();
		HashMap<String,Object> keysVals = new HashMap<String,Object>();
		keysVals.put("fname",getFName());
		keysVals.put("lname",getLName());
		keysVals.put("address",getAddress());
		id = repo.save("person","Id",getId(),keysVals);
	}
	
	public void show() {
		System.out.println("person mit der ID "+id+":");
		System.out.println("First Name: "+getFName());
		System.out.println("Last Name: "+getLName());
		System.out.println("address: "+getAddress());

	}
	
	public void read() {
		setFName(FormUtil.readString("First Name"));
		setLName(FormUtil.readString("Last Name"));
		setAddress(FormUtil.readString("Address"));

	}

	public void delete() {
		DomainRepository repo = new DomainRepository();
		repo.delete("person", "Id", id);
		
	}
}
