package de.dis2018.data;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import de.dis2018.util.Helper;

/**
 * Estate Agent-Bean
 */
@Entity
@Table(name = "estateagent")
public class EstateAgent {
	private int id;
	private String name;
	private String address;
	private String login;
	private String password;
	static int currentId = 0;
	private Set<Estate> estates;
	
	public EstateAgent() {
	}
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "LOGIN")
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@OneToMany
	public Set<Estate> getEstates() {
		return estates;
	}

	public void setEstates(Set<Estate> estates) {
		this.estates = estates;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
		result = prime * result + ((getLogin() == null) ? 0 : getLogin().hashCode());
		result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
		
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || !(obj instanceof EstateAgent))
			return false;
	
		EstateAgent other = (EstateAgent)obj;
	
		if(other.getId() != getId() ||
				!Helper.compareObjects(getName(), other.getName()) ||
				!Helper.compareObjects(getAddress(), other.getAddress()) ||
				!Helper.compareObjects(getLogin(), other.getLogin()) ||
				!Helper.compareObjects(getPassword(), other.getPassword()))
		{
			return false;
		}
		
		return true;
	}
}
