package de.dis2018.editor;

import java.util.Iterator;
import java.util.Set;

import de.dis2018.core.EstateService;
import de.dis2018.data.House;
import de.dis2018.data.PurchaseContract;
import de.dis2018.data.EstateAgent;
import de.dis2018.data.TenancyContract;
import de.dis2018.data.Person;
import de.dis2018.data.Apartment;
import de.dis2018.menu.AppartmentSelectionMenu;
import de.dis2018.menu.HouseSelectionMenu;
import de.dis2018.menu.Menu;
import de.dis2018.menu.PersonSelectionMenu;
import de.dis2018.util.FormUtil;
import de.dis2018.util.Helper;

/**
 * Class for the menus for managing contracts
 */
public class ContractEditor {
	//Estate service to be used
	private EstateService service;
	
	//Estate agents who manage estates for which contracts may be concluded.
	private EstateAgent manager;
	
	public ContractEditor(EstateService service, EstateAgent manager) {
		this.service = service;
		this.manager = manager;
	}
	
	/**
	 * Contract menu
	 */
	public void showContractMenu() {
		//Menu options
		final int NEW_TENANCY_CONTRACT = 0;
		final int NEW_PURCHASE_CONTRACT = 1;
		final int SHOW_CONTRACTS = 2;
		final int BACK = 3;
		
		//Contract management
		Menu maklerMenu = new Menu("Contract Management");
		maklerMenu.addEntry("New Tenancy Contract", NEW_TENANCY_CONTRACT);
		maklerMenu.addEntry("New Purchase Contract", NEW_PURCHASE_CONTRACT);
		maklerMenu.addEntry("Show Contracts", SHOW_CONTRACTS);
		
		maklerMenu.addEntry("Back to Main Menu", BACK);
		
		//Process input
		while(true) {
			int response = maklerMenu.show();
			
			switch(response) {
				case NEW_TENANCY_CONTRACT:
					newTenancyContract();
					break;
				case NEW_PURCHASE_CONTRACT:
					newPurchaseContract();
					break;
				case SHOW_CONTRACTS:
					showContracts();
					break;
				case BACK:
					return;
			}
		}
	}
	
	public void showContracts() {
		//Show tenancy contracts
		System.out.println("Tenancy contracts\n-----------------");
		Set<TenancyContract> mvs = service.getAllTenancyContractsForEstateAgent(manager);
		Iterator<TenancyContract> itmv = mvs.iterator();
		while(itmv.hasNext()) {
			TenancyContract mv = itmv.next();
			System.out.println("Tenancy contract "+mv.getContractNo()+"\n"+
					        "\tSigned in "+mv.getPlace()+" on " + Helper.dateToString(mv.getDate())+"\n"+
							"\tTenant :  "+mv.getContractPartner().getFirstname()+" "+mv.getContractPartner().getName()+"\n"+
							"\tApartment:"+mv.getApartment().getStreet()+" "+mv.getApartment().getStreetnumber()+", "+mv.getApartment().getPostalcode()+" "+mv.getApartment().getCity()+"\n"+
							"\tPrice:    "+mv.getApartment().getRent()+" Euro, additional costs : "+mv.getAdditionalCosts()+" Euro\n" +
							"\tStart date: "+Helper.dateToString(mv.getStartDate())+", duration: "+mv.getDuration()+" months\n");
							
		}
		
		System.out.println("");
		
		//Show purchase contracts
		System.out.println("Purchase contracts\n-----------------");
		Set<PurchaseContract> kvs = service.getAllPurchaseContractsForEstateAgent(manager);
		Iterator<PurchaseContract> itkv = kvs.iterator();
		while(itkv.hasNext()) {
			PurchaseContract kv = itkv.next();
			System.out.println("Purchase contract "+kv.getContractNo()+"\n"+
							"\tSigned in "+kv.getPlace()+" on " + Helper.dateToString(kv.getDate())+"\n"+
							"\tBuyer:    "+kv.getContractPartner().getFirstname()+" "+kv.getContractPartner().getName()+"\n"+
							"\tHouse:    "+kv.getHouse().getStreet()+" "+kv.getHouse().getStreetnumber()+", "+kv.getHouse().getPostalcode()+" "+kv.getHouse().getCity()+"\n"+
							"\tPrice:    "+kv.getHouse().getPrice()+" Euro\n"+
							"\tNo of Installments: "+kv.getNoOfInstallments()+", Inrest rate: "+kv.getIntrestRate()+"%\n");
		}
	}
	
	
	/**
	 * Menu for creating a new tenancy contract
	 */
	public void newTenancyContract() {
		//Find all the estate agent's apartments
		Set<Apartment> apartments = service.getAllApartmentsForEstateAgent(manager);
		
		//Selection menu for the apartments  
		AppartmentSelectionMenu asm = new AppartmentSelectionMenu("Select apartment for contract", apartments);
		int wid = asm.show();
		
		//If no abort: Selection of the person
		if(wid != AppartmentSelectionMenu.BACK) {
			//Load all persons
			Set<Person> personen = service.getAllPersons();
			
			//Menu to select the person
			PersonSelectionMenu psm = new PersonSelectionMenu("Select person for contract", personen);
			int pid = psm.show();
			
			//If no abort: Request contract data and create contract
			if(pid != PersonSelectionMenu.BACK) {
				TenancyContract m = new TenancyContract();
		
				m.setApartment(service.getApartmentByID(wid));
				m.setContractPartner(service.getPersonById(pid));
				m.setContractNo(FormUtil.readInt("Contract No"));
				m.setDate(FormUtil.readDate("Date (dd.MM.yyyy)"));
				m.setPlace(FormUtil.readString("City"));
				m.setStartDate(FormUtil.readDate("Start Date (dd.MM.yyyy)"));
				m.setDuration(FormUtil.readInt("Duration in months"));
				m.setAdditionalCosts(FormUtil.readInt("Additional Costs"));
				
				service.addTenancyContract(m);
				
				System.out.println("Tenancy contract with the ID \"+k.getId()+\" was created");
			}
		}
	}
	
	/**
	 * Menu for creating a new purchase contract
	 */
	public void newPurchaseContract() {
		//Find all the estate agent's houses
		Set<House> houses = service.getAllHousesForEstateAgent(manager);
		
		//Selection menu for the House
		HouseSelectionMenu asm = new HouseSelectionMenu("Select house for contract.", houses);
		int hid = asm.show();
		
		//If no abort: Selection of the person
		if(hid != AppartmentSelectionMenu.BACK) {
			//Load all persons
			Set<Person> personen = service.getAllPersons();
			
			//Menu to select the person
			PersonSelectionMenu psm = new PersonSelectionMenu("Select person for contract", personen);
			int pid = psm.show();
			
			//If no abort: Request contract data and create contract
			if(pid != PersonSelectionMenu.BACK) {
				PurchaseContract k = new PurchaseContract();
		
				k.setHouse(service.getHouseById(hid));
				k.setContractPartner(service.getPersonById(pid));
				k.setContractNo(FormUtil.readInt("Contract No"));
				k.setDate(FormUtil.readDate("Date (dd.MM.yyyy)"));
				k.setPlace(FormUtil.readString("City"));
				k.setNoOfInstallments(FormUtil.readInt("No Of Installments"));
				k.setIntrestRate(FormUtil.readInt("Intrest Rate"));
				
				service.addPurchaseContract(k);
				
				System.out.println("Purchase contract with the ID "+k.getId()+" was created.");
			}
		}
	}
}
