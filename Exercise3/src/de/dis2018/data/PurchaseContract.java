package de.dis2018.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import de.dis2018.util.Helper;

/**
 * Purchase Contract-Bean
 */
@Entity
@Table(name = "purchasecontract")
public class PurchaseContract extends Contract {
	private int noOfInstallments;
	private int intrestRate;
	private House house;
	
	public PurchaseContract() {
		super();
	}
	

	public int getNoOfInstallments() {
		return noOfInstallments;
	}
	public void setNoOfInstallments(int noOfInstallments) {
		this.noOfInstallments = noOfInstallments;
	}

	public int getIntrestRate() {
		return intrestRate;
	}
	public void setIntrestRate(int intrestRate) {
		this.intrestRate = intrestRate;
	}

    @OneToOne
    @JoinColumn(name = "house_id")
    public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		
		result = prime * result + getNoOfInstallments();
		result = prime * result + getIntrestRate();
		
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || !(obj instanceof PurchaseContract))
			return false;
	
		PurchaseContract other = (PurchaseContract)obj;
	
		if(other.getContractNo() != getContractNo() ||
				!Helper.compareObjects(this.getDate(), other.getDate()) ||
				!Helper.compareObjects(this.getPlace(), other.getPlace()) ||
				other.getNoOfInstallments() != getNoOfInstallments() ||
				other.getIntrestRate() != getIntrestRate())
		{
			return false;
		}
		
		return true;
	}
}
