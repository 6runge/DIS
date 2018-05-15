package de.dis2018.data;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import de.dis2018.util.Helper;

/**
 * Person-Bean
 */
@Entity
@Table(name = "person")
public class Person {
	private int id;
	private String firstname;
	private String name;
	private String address;
	private Set<TenancyContract> tenancyContracts; 
	private Set<PurchaseContract> purchaseContracts; 
	
	public Person() {
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
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
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
	@OneToMany(mappedBy = "contractPartner", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public Set<TenancyContract> getTenancyContracts() {
		return tenancyContracts;
	}

	public void setTenancyContracts(Set<TenancyContract> tenancyContracts) {
		this.tenancyContracts = tenancyContracts;
	}
	@OneToMany(mappedBy = "contractPartner", cascade=CascadeType.ALL)
	public Set<PurchaseContract> getPurchaseContracts() {
		return purchaseContracts;
	}
	
	public void setPurchaseContracts(Set<PurchaseContract> purchaseContracts) {
		this.purchaseContracts = purchaseContracts;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((getFirstname() == null) ? 0 : getFirstname().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
		
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || !(obj instanceof Person))
			return false;
	
		Person other = (Person)obj;
	
		if(other.getId() != getId() ||
				!Helper.compareObjects(this.getFirstname(), other.getFirstname()) ||
				!Helper.compareObjects(this.getName(), other.getName()) ||
				!Helper.compareObjects(this.getAddress(), other.getAddress()))
		{
			return false;
		}
		
		return true;
	}
}
