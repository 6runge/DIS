package de.dis2018.core;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.proxy.HibernateProxy;
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
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query query = session.createQuery("FROM Person WHERE id = :id");
		query.setParameter("id", id);
		Person person = (Person) query.list().get(0);
		session.getTransaction().commit();
		session.close();
		return person;
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
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.save(p);
		session.getTransaction().commit();
		session.close();
	}
	/**
	 * @param ea
	 */
	public void updatePerson(Person p) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.update(p);
		session.getTransaction().commit();
		session.close();			
	}	
	/**
	 * Returns all persons
	 */
	public Set<Person> getAllPersons() {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query query = session.createQuery("FROM Person");
		Set<Person> persons = new HashSet<Person>(query.list());
		session.getTransaction().commit();
		session.close();
		return persons;	
	}
	
	/**
	 * Deletes a person
	 * @param p The person
	 */
	public void deletePerson(Person p) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.delete(p);
		session.getTransaction().commit();
		session.close();	
	}
	
	/**
	 * Adds a house
	 * @param h The house
	 */
	public void addHouse(House h) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.save(h);
		session.getTransaction().commit();
		session.close();
	}
	/**
	 * @param ea
	 */
	public void updateHouse(House h) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.update(h);
		session.getTransaction().commit();
		session.close();			
	}	
	/**
	 * Returns all houses of an estate agent
	 * @param ea the estate agent
	 * @return All houses managed by the estate agent
	 */
	public Set<House> getAllHousesForEstateAgent(EstateAgent ea) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query query = session.createQuery("FROM House WHERE manager = :manager");
		query.setParameter("manager", ea);
		Set<House> hs = new HashSet<House>(query.list());
		session.getTransaction().commit();
		session.close();
		return hs;	
	}
	
	/**
	 * Find a house with a given ID
	 * @param  id the house id
	 * @return The house or null if not found
	 */
	public House getHouseById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query<House> query = session.createQuery("FROM House WHERE id = :id");
		query.setParameter("id", id);
		House h = (House) query.list().get(0);
		session.getTransaction().commit();
		session.close();
		return h;
	}
	
	/**
	 * Deletes a house
	 * @param h The house
	 */
	public void deleteHouse(House h) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.delete(h);
		session.getTransaction().commit();
		session.close();	
	}
	
	/**
	 * Adds an apartment
	 * @param w the aparment
	 */
	public void addApartment(Apartment w) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.save(w);
		session.getTransaction().commit();
		session.close();
	}
	/**
	 * @param ea
	 */
	public void updateApartment(Apartment w) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.update(w);
		session.getTransaction().commit();
		session.close();			
	}	
	/**
	 * Returns all apartments of an estate agent
	 * @param ea The estate agent
	 * @return All apartments managed by the estate agent
	 */
	public Set<Apartment> getAllApartmentsForEstateAgent(EstateAgent ea) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query query = session.createQuery("FROM Apartment WHERE manager = :manager");
		query.setParameter("manager", ea);
		Set<Apartment> ws = new HashSet<Apartment>(query.list());
		session.getTransaction().commit();
		session.close();
		return ws;	
	}
	
	/**
	 * Find an apartment with given ID
	 * @param id The ID
	 * @return The apartment or zero, if not found
	 */
	public Apartment getApartmentByID(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query query = session.createQuery("FROM Apartment WHERE id = :id");
		query.setParameter("id", id);
		Apartment w = (Apartment) query.list().get(0);
		session.getTransaction().commit();
		session.close();
		return w;
	}
	
	/**
	 * Deletes an apartment
	 * @param p The apartment
	 */
	public void deleteApartment(Apartment w) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.delete(w);
		session.getTransaction().commit();
		session.close();	
	}
	
	
	/**
	 * Adds a tenancy contract
	 * @param t The tenancy contract
	 */
	public void addTenancyContract(TenancyContract t) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.save(t);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Adds a purchase contract
	 * @param p The purchase contract
	 */
	public void addPurchaseContract(PurchaseContract p) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.save(p);
		session.getTransaction().commit();
		session.close();
	}
	/**
	 * @param ea
	 */
	public void updateTenancyContract(TenancyContract tc) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.update(tc);
		session.getTransaction().commit();
		session.close();			
	}
	/**
	 * @param ea
	 */
	public void updatePurchaseContract(PurchaseContract pc) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.update(pc);
		session.getTransaction().commit();
		session.close();			
	}
	/**
	 * Finds a tenancy contract with a given ID
	 * @param id Die ID
	 * @return The tenancy contract or zero if not found
	 */
	public TenancyContract getTenancyContractByID(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query query = session.createQuery("FROM TenancyContract WHERE id = :id");
		query.setParameter("id", id);
		TenancyContract tc = (TenancyContract) query.list().get(0);
		session.getTransaction().commit();
		session.close();
		return tc;
	}
	
	/**
	 * Finds a purchase contract with a given ID
	 * @param id The id of the purchase contract
	 * @return The purchase contract or null if not found
	 */
	public PurchaseContract getPurchaseContractById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query query = session.createQuery("FROM PurchaseContract WHERE id = :id");
		query.setParameter("id", id);
		PurchaseContract pc = (PurchaseContract) query.list().get(0);
		session.getTransaction().commit();
		session.close();
		return pc;
	}
	
	/**
	 * Returns all tenancy contracts for apartments of the given estate agent
	 * @param m The estate agent
	 * @return All contracts belonging to apartments managed by the estate agent
	 */
	public Set<TenancyContract> getAllTenancyContractsForEstateAgent(EstateAgent ea) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query query = session.createQuery("FROM TenancyContract AS tc WHERE tc.apartment.manager = :manager");
		query.setParameter("manager", ea);
		Set<TenancyContract> tcs = new HashSet<TenancyContract>(query.list());
		session.getTransaction().commit();
		session.close();
		return tcs;	
	}
	
	/**
	 * Returns all purchase contracts for houses of the given estate agent
	 * @param m The estate agent
	 * @return All purchase contracts belonging to houses managed by the given estate agent
	 */
	public Set<PurchaseContract> getAllPurchaseContractsForEstateAgent(EstateAgent ea) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		Query query = session.createQuery("FROM PurchaseContract AS pc WHERE pc.house.manager = :manager");
		query.setParameter("manager", ea);
		Set<PurchaseContract> pcs = new HashSet<PurchaseContract>(query.list());
		session.getTransaction().commit();
		session.close();
		return pcs;	
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
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.delete(tc);
		session.getTransaction().commit();
		session.close();	
	}
	
	/**
	 * Deletes a purchase contract
	 * @param tc the purchase contract
	 */
	public void deletePurchaseContract(PurchaseContract pc) {
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		session.delete(pc);
		session.getTransaction().commit();
		session.close();	
	}
	
	/**
	 * Adds some test data
	 */
	@Transactional
	public void addTestData() {
		
		EstateAgent m = new EstateAgent();
		m.setName("Max Mustermann");
		m.setAddress("Am Informatikum 9");
		m.setLogin("max");
		m.setPassword("max");
		
		this.addEstateAgent(m);

		Person p1 = new Person();
		p1.setAddress("Informatikum");
		p1.setName("Mustermann");
		p1.setFirstname("Erika");
		
		
		Person p2 = new Person();
		p2.setAddress("Reeperbahn 9");
		p2.setName("Albers");
		p2.setFirstname("Hans");

		this.addPerson(p1);
		this.addPerson(p2);

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
		System.out.println("id1 "+h.getId());
		this.addHouse(h);
		System.out.println(h.getId());		
		EstateAgent m2 = this.getEstateAgentByID(m.getId());
		Hibernate.initialize(m2);
		Set<Estate> immos = m2.getEstates();
		Iterator<Estate> it = immos.iterator();
		
		while(it.hasNext()) {
			Estate i = it.next();
			System.out.println("Estate: "+i.getCity());
		}
		
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
		pc.setHouse(getHouseById(1));
		pc.setContractPartner(p1);
		pc.setContractNo(9234);
		pc.setDate(new Date(System.currentTimeMillis()));
		pc.setPlace("Hamburg");
		pc.setNoOfInstallments(5);
		pc.setIntrestRate(4);
		this.addPurchaseContract(pc);
		System.out.println("idafterpc "+pc.getHouse().getId());
		
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
