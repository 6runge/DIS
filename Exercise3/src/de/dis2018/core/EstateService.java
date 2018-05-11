package de.dis2018.core;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import de.dis2018.data.House;
import de.dis2018.data.Estate;
import de.dis2018.data.PurchaseContract;
import de.dis2018.data.EstateAgent;
import de.dis2018.data.TenancyContract;
import de.dis2018.data.Person;
import de.dis2018.data.Apartment;

/**
 *  Class for managing all database entities.
 * 
 * TODO: All data is currently stored in memory. 
 * The aim of the exercise is to gradually outsource data management to the 
 * database. When the work is done, all sets in this class become obsolete!
 */
public class EstateService {
	//TODO All these sets should be commented out after successful implementation.
	private Set<EstateAgent> estateAgents = new HashSet<EstateAgent>();
	private Set<Person> persons = new HashSet<Person>();
	private Set<House> houses = new HashSet<House>();
	private Set<Apartment> apartments = new HashSet<Apartment>();
	private Set<TenancyContract> tenancyContracts = new HashSet<TenancyContract>();
	private Set<PurchaseContract> purchaseContracts = new HashSet<PurchaseContract>();
	
	//Hibernate Session
	private SessionFactory sessionFactory;
	
	public EstateService() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	/**
	 * Find an estate agent with the given id
	 * @param id The ID of the agent
	 * @return Agent with ID or null
	 */
	public EstateAgent getEstateAgentByID(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query query = session.createQuery("FROM EstateAgent WHERE id = :id");
		query.setParameter("id", id);
		EstateAgent agent = (EstateAgent) query.list().get(0);
		session.getTransaction().commit();
		session.close();
		return agent;
	}
	
	/**
	 * Find estate agent with the given login.
	 * @param login The login of the estate agent
	 * @return Estate agent with the given ID or null
	 */
	public EstateAgent getEstateAgentByLogin(String login) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query query = session.createQuery("FROM EstateAgent WHERE login = :login");
		query.setParameter("login", login);
		EstateAgent agent = (EstateAgent) query.list().get(0);
		session.getTransaction().commit();
		session.close();
		return agent;
	}
	
	/**
	 * Returns all estateAgents
	 */
	public Set<EstateAgent> getAllEstateAgents() {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query query = session.createQuery("FROM EstateAgent");
		Set<EstateAgent> agents = new HashSet<EstateAgent>(query.list());
		session.getTransaction().commit();
		session.close();
		return agents;		
	}
	
	/**
	 * Find an person with the given id
	 * @param id The ID of the person
	 * @return Person with ID or null
	 */
	public Person getPersonById(int id) {
		Iterator<Person> it = persons.iterator();
		
		while(it.hasNext()) {
			Person p = it.next();
			
			if(p.getId() == id)
				return p;
		}
		
		return null;
	}
	
	/**
	 * Adds an estate agent
	 * @param ea The estate agent
	 */
	public void addEstateAgent(EstateAgent ea) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.save(ea);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Deletes an estate agent
	 * @param ea The estate agent
	 */
	public void deleteEstateAgent(EstateAgent ea) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.delete(ea);
		session.getTransaction().commit();
		session.close();	
	}
	
	/**
	 * @param ea
	 */
	public void updateEstateAgent(EstateAgent ea) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.update(ea);
		session.getTransaction().commit();
		session.close();		
		
	}

	/**
	 * Adds a person
	 * @param p The person
	 */
	public void addPerson(Person p) {
		persons.add(p);
	}
	
	/**
	 * Returns all persons
	 */
	public Set<Person> getAllPersons() {
		return persons;
	}
	
	/**
	 * Deletes a person
	 * @param p The person
	 */
	public void deletePerson(Person p) {
		persons.remove(p);
	}
	
	/**
	 * Adds a house
	 * @param h The house
	 */
	public void addHouse(House h) {
		houses.add(h);
	}
	
	/**
	 * Returns all houses of an estate agent
	 * @param ea the estate agent
	 * @return All houses managed by the estate agent
	 */
	public Set<House> getAllHousesForEstateAgent(EstateAgent ea) {
		Set<House> ret = new HashSet<House>();
		Iterator<House> it = houses.iterator();
		
		while(it.hasNext()) {
			House h = it.next();
			
			if(h.getManager().equals(ea))
				ret.add(h);
		}
		
		return ret;
	}
	
	/**
	 * Find a house with a given ID
	 * @param  id the house id
	 * @return The house or null if not found
	 */
	public House getHouseById(int id) {
		Iterator<House> it = houses.iterator();
		
		while(it.hasNext()) {
			House h = it.next();
			
			if(h.getId() == id)
				return h;
		}
		
		return null;
	}
	
	/**
	 * Deletes a house
	 * @param h The house
	 */
	public void deleteHouse(House h) {
		houses.remove(h);
	}
	
	/**
	 * Adds an apartment
	 * @param w the aparment
	 */
	public void addApartment(Apartment w) {
		apartments.add(w);
	}
	
	/**
	 * Returns all apartments of an estate agent
	 * @param ea The estate agent
	 * @return All apartments managed by the estate agent
	 */
	public Set<Apartment> getAllApartmentsForEstateAgent(EstateAgent ea) {
		Set<Apartment> ret = new HashSet<Apartment>();
		Iterator<Apartment> it = apartments.iterator();
		
		while(it.hasNext()) {
			Apartment w = it.next();
			
			if(w.getManager().equals(ea))
				ret.add(w);
		}
		
		return ret;
	}
	
	/**
	 * Find an apartment with given ID
	 * @param id The ID
	 * @return The apartment or zero, if not found
	 */
	public Apartment getApartmentByID(int id) {
		Iterator<Apartment> it = apartments.iterator();
		
		while(it.hasNext()) {
			Apartment w = it.next();
			
			if(w.getId() == id)
				return w;
		}
		
		return null;
	}
	
	/**
	 * Deletes an apartment
	 * @param p The apartment
	 */
	public void deleteApartment(Apartment w) {
		apartments.remove(w);
	}
	
	
	/**
	 * Adds a tenancy contract
	 * @param t The tenancy contract
	 */
	public void addTenancyContract(TenancyContract t) {
		tenancyContracts.add(t);
	}
	
	/**
	 * Adds a purchase contract
	 * @param p The purchase contract
	 */
	public void addPurchaseContract(PurchaseContract p) {
		purchaseContracts.add(p);
	}
	
	/**
	 * Finds a tenancy contract with a given ID
	 * @param id Die ID
	 * @return The tenancy contract or zero if not found
	 */
	public TenancyContract getTenancyContractByID(int id) {
		Iterator<TenancyContract> it = tenancyContracts.iterator();
		
		while(it.hasNext()) {
			TenancyContract m = it.next();
			
			if(m.getId() == id)
				return m;
		}
		
		return null;
	}
	
	/**
	 * Finds a purchase contract with a given ID
	 * @param id The id of the purchase contract
	 * @return The purchase contract or null if not found
	 */
	public PurchaseContract getPurchaseContractById(int id) {
		Iterator<PurchaseContract> it = purchaseContracts.iterator();
		
		while(it.hasNext()) {
			PurchaseContract k = it.next();
			
			if(k.getId() == id)
				return k;
		}
		
		return null;
	}
	
	/**
	 * Returns all tenancy contracts for apartments of the given estate agent
	 * @param m The estate agent
	 * @return All contracts belonging to apartments managed by the estate agent
	 */
	public Set<TenancyContract> getAllTenancyContractsForEstateAgent(EstateAgent ea) {
		Set<TenancyContract> ret = new HashSet<TenancyContract>();
		Iterator<TenancyContract> it = tenancyContracts.iterator();
		
		while(it.hasNext()) {
			TenancyContract v = it.next();
			
			if(v.getApartment().getManager().equals(ea))
				ret.add(v);
		}
		
		return ret;
	}
	
	/**
	 * Returns all purchase contracts for houses of the given estate agent
	 * @param m The estate agent
	 * @return All purchase contracts belonging to houses managed by the given estate agent
	 */
	public Set<PurchaseContract> getAllPurchaseContractsForEstateAgent(EstateAgent ea) {
		Set<PurchaseContract> ret = new HashSet<PurchaseContract>();
		Iterator<PurchaseContract> it = purchaseContracts.iterator();
		
		while(it.hasNext()) {
			PurchaseContract k = it.next();
			
			if(k.getHouse().getManager().equals(ea))
				ret.add(k);
		}
		
		return ret;
	}
	
	/**
	 * Finds all tenancy contracts relating to the apartments of a given estate agent	 
	 * @param ea The estate agent
	 * @return set of tenancy contracts
	 */
	public Set<TenancyContract> getTenancyContractByEstateAgent(EstateAgent ea) {
		Set<TenancyContract> ret = new HashSet<TenancyContract>();
		Iterator<TenancyContract> it = tenancyContracts.iterator();
		
		while(it.hasNext()) {
			TenancyContract mv = it.next();
			
			if(mv.getApartment().getManager().getId() == ea.getId())
				ret.add(mv);
		}
		
		return ret;
	}
	
	/**
	 * Finds all purchase contracts relating to the houses of a given estate agent
	 * @param  ea The estate agent
	 * @return set of purchase contracts
	 */
	public Set<PurchaseContract> getPurchaseContractByEstateAgent(EstateAgent ea) {
		Set<PurchaseContract> ret = new HashSet<PurchaseContract>();
		Iterator<PurchaseContract> it = purchaseContracts.iterator();
		
		while(it.hasNext()) {
			PurchaseContract k = it.next();
			
			if(k.getHouse().getManager().getId() == ea.getId())
				ret.add(k);
		}
		
		return ret;
	}

	
	/**
	 * Deletes a tenancy contract
	 * @param tc the tenancy contract
	 */
	public void deleteTenancyContract(TenancyContract tc) {
		apartments.remove(tc);
	}
	
	/**
	 * Deletes a purchase contract
	 * @param tc the purchase contract
	 */
	public void deletePurchaseContract(PurchaseContract pc) {
		apartments.remove(pc);
	}
	
	/**
	 * Adds some test data
	 */
	public void addTestData() {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		
		EstateAgent m = new EstateAgent();
		m.setName("Max Mustermann");
		m.setAddress("Am Informatikum 9");
		m.setLogin("max");
		m.setPassword("max");
		
		//TODO: This estate agent is kept in memory and the DB
		this.addEstateAgent(m);
		session.save(m);
		session.getTransaction().commit();

		session.beginTransaction();
		
		Person p1 = new Person();
		p1.setAddress("Informatikum");
		p1.setName("Mustermann");
		p1.setFirstname("Erika");
		
		
		Person p2 = new Person();
		p2.setAddress("Reeperbahn 9");
		p2.setName("Albers");
		p2.setFirstname("Hans");
		
		session.save(p1);
		session.save(p2);
		
		//TODO: These persons are kept in memory and the DB
		this.addPerson(p1);
		this.addPerson(p2);
		session.getTransaction().commit();
		
		
		session.beginTransaction();
		House h = new House();
		h.setCity("Hamburg");
		h.setPostalcode(22527);
		h.setStreet("Vogt-Kölln-Street");
		h.setStreetnumber("2a");
		h.setSquareArea(384);
		h.setFloors(5);
		h.setPrice(10000000);
		h.setGarden(true);
		h.setManager(m);
		
		session.save(h);
		
		//TODO: This house is held in memory and the DB
		this.addHouse(h);
		session.getTransaction().commit();
		
		// Create Hibernate Session
		session = sessionFactory.openSession();
		session.beginTransaction();
		EstateAgent m2 = (EstateAgent)session.get(EstateAgent.class, m.getId());
		Set<Estate> immos = m2.getEstates();
		Iterator<Estate> it = immos.iterator();
		
		while(it.hasNext()) {
			Estate i = it.next();
			System.out.println("Estate: "+i.getCity());
		}
		session.close();
		
		Apartment w = new Apartment();
		w.setCity("Hamburg");
		w.setPostalcode(22527);
		w.setStreet("Vogt-Kölln-Street");
		w.setStreetnumber("3");
		w.setSquareArea(120);
		w.setFloor(4);
		w.setRent(790);
		w.setKitchen(true);
		w.setBalcony(false);
		w.setManager(m);
		this.addApartment(w);
		
		w = new Apartment();
		w.setCity("Berlin");
		w.setPostalcode(22527);
		w.setStreet("Vogt-Kölln-Street");
		w.setStreetnumber("3");
		w.setSquareArea(120);
		w.setFloor(4);
		w.setRent(790);
		w.setKitchen(true);
		w.setBalcony(false);
		w.setManager(m);
		this.addApartment(w);
		
		PurchaseContract pc = new PurchaseContract();
		pc.setHouse(h);
		pc.setContractPartner(p1);
		pc.setContractNo(9234);
		pc.setDate(new Date(System.currentTimeMillis()));
		pc.setPlace("Hamburg");
		pc.setNoOfInstallments(5);
		pc.setIntrestRate(4);
		this.addPurchaseContract(pc);
		
		TenancyContract tc = new TenancyContract();
		tc.setApartment(w);
		tc.setContractPartner(p2);
		tc.setContractNo(23112);
		tc.setDate(new Date(System.currentTimeMillis()-1000000000));
		tc.setPlace("Berlin");
		tc.setStartDate(new Date(System.currentTimeMillis()));
		tc.setAdditionalCosts(65);
		tc.setDuration(36);
		this.addTenancyContract(tc);
	}
}
