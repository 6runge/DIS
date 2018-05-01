package de.dis2011.data;

import java.util.HashMap;
import java.util.Map;
import de.dis2011.FormUtil;

/**
 * Makler-Bean
 * 
 * Beispiel-Tabelle:
 * CREATE TABLE makler(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
 * name varchar(255),
 * address varchar(255),
 * login varchar(40) UNIQUE,
 * password varchar(40));
 */
public class EstateAgent {
	private int id = -1;
	private String name;
	private String address;
	private String login;
	private String password;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Lädt einen Makler aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
	public static EstateAgent load(int id) {
			DomainRepository repo = new DomainRepository();
			Map<String,Object> result = repo.load("estateagent", "Id",id);
			if (result != null) {
				EstateAgent ts = new EstateAgent();
				ts.setId(id);
				ts.setName((String) result.get("name"));
				ts.setAddress((String) result.get("address"));
				ts.setLogin((String) result.get("login"));
				ts.setPassword((String) result.get("password"));
				return ts;
			}
			return null;
	}
	
	/**
	 * Lädt einen Makler aus der Datenbank
	 * @param login login des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
	public static EstateAgent loadByLogin(String login) {
			DomainRepository repo = new DomainRepository();
			Map<String,Object> result = repo.loadByString("estateagent", "login", login);
			if (result != null) {
				EstateAgent ts = new EstateAgent();
				Object id = result.get("id");
				if (id instanceof Integer) {
					ts.setId((Integer) result.get("id"));
				}
				else {
					System.out.println("The ID for agent "+login+" is of an invalid data type.");
				}
				ts.setName((String) result.get("name"));
				ts.setAddress((String) result.get("address"));
				ts.setLogin(login);
				ts.setPassword((String) result.get("password"));
				return ts;
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
		keysVals.put("name",getName());
		keysVals.put("address", getAddress());
		keysVals.put("login", getLogin());
		keysVals.put("password", getPassword());
		id = repo.save("estateagent","Id",getId(),keysVals);
	}
	
	public void show() {
		System.out.println("Makler mit der ID "+id+":");
		System.out.println("Name: " + getName());
		System.out.println("Adresse: " + getAddress());
		System.out.println("Login: " + getLogin());
		System.out.println("Passwort: " + getPassword());
	}
	
	public void read() {
		setName(FormUtil.readString("Name"));
		setAddress(FormUtil.readString("Adresse"));
		setLogin(FormUtil.readString("Login"));
		while(EstateAgent.loadByLogin(getLogin()) != null) {
			System.out.println("Login-Name bereits vergeben!");
			setLogin(FormUtil.readString("Login"));
		}
			
		setPassword(FormUtil.readString("Passwort"));
	}

	public void delete() {
		DomainRepository repo = new DomainRepository();
		repo.delete("estateagent", "Id", id);
		
	}
}
