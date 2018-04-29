package de.dis2011.data;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import de.dis2011.FormUtil;

public class TenancyContract extends Contract {

	private Appartment apartment;
	private Person tenant;
	private Date startDate;
	private int duration;
	private int additionalCost;

	public Appartment getApartment() {
		return apartment;
	}

	public void setApartment(Appartment apartment) {
		this.apartment = apartment;
	}

	public Person getTenant() {
		return tenant;
	}

	public void setTenant(Person tenant) {
		this.tenant = tenant;
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

	public int getAdditionalCost() {
		return additionalCost;
	}

	public void setAdditionalCost(int additionalCost) {
		this.additionalCost = additionalCost;
	}

	/**
	 * Lädt einen Makler aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
	public static TenancyContract load(int id) {
			
			DomainRepository repo = new DomainRepository();
			int contractId = repo.findIdByForeignId("contract","tenancyContractId",id);
			TenancyContract tenancyContract = new TenancyContract();
			tenancyContract.getContractProperties(contractId);
			Map<String,Object> result = repo.load("tenancyContract", "Id",id);
			if (result != null) {				
				tenancyContract.setId(id);
				Appartment apartment = Appartment.load((Integer) result.get("apartmentid"));
				tenancyContract.setApartment(apartment);
				Person person = Person.load((Integer) result.get("tenantid"));
				tenancyContract.setTenant(person);		
				tenancyContract.setStartDate((Date) result.get("startdate"));
				tenancyContract.setDuration((Integer) result.get("duration"));
				tenancyContract.setAdditionalCost((Integer) result.get("additionalcost"));
				return tenancyContract;
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
		keysVals.put("apartmentid",getApartment().getId());
		keysVals.put("tenantid",getTenant().getId()); 
		keysVals.put("startdate",getStartDate());
		keysVals.put("duration",getDuration());
		keysVals.put("additionalcost",getAdditionalCost());
		
		int contractId = 0;
		int tenancyContractId = getId();
		if (tenancyContractId != -1) {
			contractId = repo.findIdByForeignId("contract","tenancycontractid",tenancyContractId); 
			if (contractId == 0) {
				System.out.println("Domain Model incoherent. Missing ContractRecord(parent) for TenancyContractID: "+tenancyContractId);
				return;
			}		
		} else {
			contractId = -1; 
		
		}
		tenancyContractId = repo.save("tenancycontract","Id",tenancyContractId,keysVals);
		setId(contractId);
		setTenancyContractId(tenancyContractId);
		super.save();
		setId(tenancyContractId);
		
	}
	
	public void show() {
		super.show();
		System.out.println("TenancyContract Zusatzdaten:");
		System.out.println("----Tenant:---------");
		getTenant().show();
		System.out.println("-------------");
		System.out.println("----Apartment:---------");
		getApartment().show();
		System.out.println("-------------");		
		System.out.println("Start Date: "+getStartDate()); 
		System.out.println("duration: "+getDuration());
		System.out.println("Additional Cost: "+getAdditionalCost());
		System.out.println("=====================================================");
		System.out.println();
	}
	
	public void read() {
		super.read();
		Person tenant = Person.load(FormUtil.readInt("Tenant Id:"));
		setTenant(tenant);
		Appartment apartment = Appartment.load(FormUtil.readInt("Apartment Id:"));
		setStartDate(FormUtil.readDate("Start date"));;
		setApartment(apartment);
		setDuration(FormUtil.readInt("duration"));
		setAdditionalCost(FormUtil.readInt("AdditionalCost"));
		
	}

	public void delete() {
		DomainRepository repo = new DomainRepository();
		int contractId = repo.findIdByForeignId("contract","tenancyContractId",getId());
		repo.delete("contract", "Id", contractId);
		repo.delete("tenancycontract", "Id", getId());
		
		
	}

}
