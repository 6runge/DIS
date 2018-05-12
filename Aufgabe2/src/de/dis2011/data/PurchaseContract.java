package de.dis2011.data;

import java.util.HashMap;
import java.util.Map;

import de.dis2011.FormUtil;

public class PurchaseContract extends Contract {

	private House house;
	private Person buyer;
	private int noOfInstallments;
	private int interestRate;


	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public Person getBuyer() {
		return buyer;
	}

	public void setBuyer(Person buyer) {
		this.buyer = buyer;
	}

	public int getNoOfInstallments() {
		return noOfInstallments;
	}

	public void setNoOfInstallments(int noOfInstallments) {
		this.noOfInstallments = noOfInstallments;
	}

	public int getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(int interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * Lädt einen Vertrag aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Vertrags-Instanz
	 */
	public static PurchaseContract load(int id) {

		DomainRepository repo = new DomainRepository();
		int contractId = repo.findIdByForeignId("contract","purchaseContractId",id);
		PurchaseContract purchaseContract = new PurchaseContract();
		purchaseContract.getContractProperties(contractId);
		Map<String,Object> result = repo.load("purchaseContract", "Id",id);
		if (result != null) {				
			purchaseContract.setId(id);
			House house = House.load((Integer) result.get("houseid"));
			purchaseContract.setHouse(house);
			Person person = Person.load((Integer) result.get("buyerid"));
			purchaseContract.setBuyer(person);		
			purchaseContract.setNoOfInstallments((Integer) result.get("noofinstallments"));
			purchaseContract.setInterestRate((Integer) result.get("interestrate"));
			return purchaseContract;
		}
		return null;
	}

	/**
	 * Speichert den Vertrag in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() {
		DomainRepository repo = new DomainRepository();
		HashMap<String,Object> keysVals = new HashMap<String,Object>();
		int houseId = -1;
		int buyerId = -1;
		House house = getHouse();
		Person buyer = getBuyer();
		if (house != null) {
			houseId = house.getId();
		}
		if (buyer != null) {
			buyerId = buyer.getId();
		}
		keysVals.put("houseid", houseId);
		keysVals.put("buyerId", buyerId); 
		keysVals.put("noofinstallments",getNoOfInstallments());
		keysVals.put("interestrate",getInterestRate());

		int contractId = 0;
		int purchaseContractId = getId();
		if (purchaseContractId != -1) {
			contractId = repo.findIdByForeignId("contract","purchaseContractid",purchaseContractId); 
			if (contractId == 0) {
				System.out.println("Domain Model incoherent. Missing ContractRecord(parent) for purchaseContractID: "+purchaseContractId);
				return;
			}		
		} else {
			contractId = -1; 

		}
		purchaseContractId = repo.save("purchaseContract","Id",purchaseContractId,keysVals);
		setId(contractId);
		setPurchaseContractId(purchaseContractId);
		super.save();
		setId(purchaseContractId);

	}

	public void show() {
		super.show();
		System.out.println("purchaseContract Zusatzdaten:");
		System.out.println("----Buyer:---------");
		if (getBuyer() != null) {
			getBuyer().show();
		}
		System.out.println("-------------");
		System.out.println("----House:---------");
		if (getBuyer() != null) { 
			getHouse().show();
		}
		System.out.println("-------------");		
		System.out.println("Number of installments: "+getNoOfInstallments()); 
		System.out.println("Interest Rate: "+getInterestRate());
		System.out.println("=====================================================");
		System.out.println();
	}

	public void read() {
		super.read();
		Person buyer = null;
		int buyerId = -1;
		int houseId = -1;
		House house = null;
		while (house == null) {
			houseId = FormUtil.readInt("House Id");
			house = House.load(houseId);
			if (house == null) {
				System.out.println("Invalid id. Try again!");
			}
		}
		while (buyer == null) {
			buyerId = FormUtil.readInt("Buyer Id");
			buyer = Person.load(buyerId);
			if (buyer == null) {
				System.out.println("Invalid id. Try again!");
			}
		}
		setBuyer(Person.load(buyerId));
		setHouse(House.load(houseId));
		setNoOfInstallments(FormUtil.readInt("Number of installments"));
		setInterestRate(FormUtil.readInt("Interest Rate"));
	}

	public void delete() {
		DomainRepository repo = new DomainRepository();
		int contractId = repo.findIdByForeignId("contract","purchaseContractId",getId());
		repo.delete("contract", "Id", contractId);
		repo.delete("purchaseContract", "Id", getId());


	}

}