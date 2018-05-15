package de.dis2018.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import de.dis2018.util.Helper;

/**
 * Contract-Bean
 */
@MappedSuperclass
public abstract class Contract {
	private int contractNo = -1;
	private Date date;
	private String place;
	int id;
	private Person contractPartner;
	
	public int getContractNo() {
		return contractNo;
	}
	public void setContractNo(int contractNo) {
		this.contractNo = contractNo;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
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
	@OneToOne
	public Person getContractPartner() {
		return contractPartner;
	}

	public void setContractPartner(Person contractPartner) {
		this.contractPartner = contractPartner;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
		result = prime * result + ((getPlace() == null) ? 0 : getPlace().hashCode());
		
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || !(obj instanceof Contract))
			return false;
	
		Contract other = (Contract)obj;
	
		if(other.getContractNo() != getContractNo() ||
				!Helper.compareObjects(this.getDate(), other.getDate()) ||
				!Helper.compareObjects(this.getPlace(), other.getPlace()))
		{
			return false;
		}
		
		return true;
	}
}
