package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import de.dis2011.data.DB2ConnectionManager;

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
			ResultSet rs = DomainRepository.load("estateagent", id);
			try {
				if (rs.next()) {
					EstateAgent ts = new EstateAgent();
					ts.setId(id);
					ts.setName(rs.getString("name"));
					ts.setAddress(rs.getString("address"));
					ts.setLogin(rs.getString("login"));
					ts.setPassword(rs.getString("password"));

					rs.close();
					return ts;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		id = repo.save("estateagent",getId(),keysVals);
	}
}
