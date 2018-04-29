package de.dis2011.data;

import java.sql.Date;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.dis2011.FormUtil;

public class Contract {
	private int id = -1;

	private Integer contractNo;
	private Date date; 
	private String place;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	

	public Integer getContractNo() {
		return contractNo;
	}

	public void setContractNo(Integer contractNo) {
		this.contractNo = contractNo;
	}

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

	public void getContractProperties(int contractId) {
		Contract contract = Contract.load(contractId);// TODO Auto-generated method stub
		setContractNo(contract.getContractNo());
		setDate(contract.getDate()); 
		setPlace(contract.getPlace());
	}
	/**
	 * Lädt einen Makler aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
	public static Contract load(int id) {
			DomainRepository repo = new DomainRepository();
			Map<String,Object> result = repo.load("Contract","Id",id);		
			if (result != null) {
				Contract contract = new Contract();
				contract.setId(id);
				contract.setContractNo((Integer) result.get("contractno"));
				Date d = Date.valueOf((String) result.get("date"));
				contract.setDate(d); 
				contract.setPlace((String) result.get("place"));

				return contract;
			}
			return null;
	}
	
	/**
	 * Speichert den Makler in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() {
		// TODO check for unique login
		DomainRepository repo = new DomainRepository();
		HashMap<String,Object> keysVals = new HashMap<String,Object>();
		keysVals.put("contractNo",getContractNo());
		keysVals.put("date",getDate());
		keysVals.put("place",getPlace());
		id = repo.save("contract","Id",getId(),keysVals);
	}
	
	public void show() {
		System.out.println("Contract mit der ID "+id+":");
		System.out.println("contractNo: "+getContractNo());
		System.out.println("Date: "+getDate());
		System.out.println("place: "+getPlace());

	}
	
	public void read() {
		setContractNo(FormUtil.readInt("contractNo"));
		setDate(FormUtil.readDate("date"));
		setPlace(FormUtil.readString("place"));

	}

	public void delete() {
		DomainRepository repo = new DomainRepository();
		repo.delete("contract", "Id", id);
		
	}

	public static List<Contract> loadAll() {
		DomainRepository repo = new DomainRepository();
		List<Contract> result = new LinkedList<Contract>();
		List<HashMap<String,Object>> contractData = repo.loadAll("contract");
		for (HashMap<String,Object> contractRow: contractData) {
			Contract contract = new Contract();
			contract.setId((Integer) contractRow.get("id"));
			contract.setContractNo((Integer) contractRow.get("contractno"));
			Date d = (Date) contractRow.get("date");
			contract.setDate(d); 
			contract.setPlace((String) contractRow.get("place"));
			result.add(contract);
		}
		return result;
	}

}
