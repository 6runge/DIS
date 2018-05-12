package de.dis2018.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.InheritanceType;
import org.hibernate.annotations.GenericGenerator;

import de.dis2018.util.Helper;

/**
 * estates-Bean
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Estate {
	
	private int id = -1;
	private String city;
	private int postalcode;
	private String street;
	private String streetnumber;
	private int squareArea;
	private EstateAgent manager;
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public int getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(int postalcode) {
		this.postalcode = postalcode;
	}

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetnumber() {
		return streetnumber;
	}
	public void setStreetnumber(String streetnumber) {
		this.streetnumber = streetnumber;
	}

	public int getSquareArea() {
		return squareArea;
	}
	public void setSquareArea(int squareArea) {
		this.squareArea = squareArea;
	}
	
	public void setManager(EstateAgent manager) {
		this.manager = manager;
	}
	
	@ManyToOne
	public EstateAgent getManager() {
		return manager;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
		result = prime * result + ((getStreet() == null) ? 0 : getStreet().hashCode());
		result = prime * result + ((getStreetnumber() == null) ? 0 : getStreetnumber().hashCode());
		
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || !(obj instanceof Estate))
			return false;
	
		Estate other = (Estate)obj;
	
		if(other.getId() != getId() ||
				other.getPostalcode() != getPostalcode() ||
				other.getSquareArea() != getSquareArea() ||
				!Helper.compareObjects(this.getCity(), other.getCity()) ||
				!Helper.compareObjects(this.getStreet(), other.getStreet()) ||
				!Helper.compareObjects(this.getStreetnumber(), other.getStreetnumber()))
		{
			return false;
		}
		
		return true;
	}
}
