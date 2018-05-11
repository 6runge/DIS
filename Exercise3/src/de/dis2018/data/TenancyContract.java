package de.dis2018.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.dis2018.util.Helper;

/**
 * Tenancy Contract-Bean
 */
@Entity
@Table(name = "tenancycontract")
public class TenancyContract extends Contract {
	private Date startDate;
	private int duration;
	private int additionalCosts;
	private Apartment apartment;

	public TenancyContract() {
		super();
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getAdditionalCosts() {
		return additionalCosts;
	}
	public void setAdditionalCosts(int additionalCosts) {
		this.additionalCosts = additionalCosts;
	}
	
	@OneToOne
	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		
		result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
		result = prime * result + getDuration();
		result = prime * result + getAdditionalCosts();
		
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || !(obj instanceof TenancyContract))
			return false;
	
		TenancyContract other = (TenancyContract)obj;
	
		if(other.getContractNo() != getContractNo() ||
				!Helper.compareObjects(this.getDate(), other.getDate()) ||
				!Helper.compareObjects(this.getPlace(), other.getPlace()) ||
				other.getDuration() != getDuration() ||
				other.getAdditionalCosts() != getAdditionalCosts() ||
				!Helper.compareObjects(other.getStartDate(), getStartDate()))
		{
			return false;
		}
		
		return true;
	}
}
