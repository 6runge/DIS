package de.dis2018.editor;

import de.dis2018.core.EstateService;
import de.dis2018.data.Person;
import de.dis2018.menu.Menu;
import de.dis2018.menu.PersonSelectionMenu;
import de.dis2018.util.FormUtil;

/**
 * Class for the menus for managing persons
 */
public class PersonEditor {
	//Estate service to be used
	private EstateService service;
	
	public PersonEditor(EstateService service) {
		this.service = service;
	}
	
	/**
	 * Shows Person Management
	 */
	public void showPersonMenu() {
		//Menu options
		final int NEW_PERSON = 0;
		final int EDIT_PERSON = 1;
		final int DELETE_PERSON = 2;
		final int BACK = 3;
		
		//Person Management
		Menu maklerMenu = new Menu("Person Management");
		maklerMenu.addEntry("New Person", NEW_PERSON);
		maklerMenu.addEntry("Edit Person", EDIT_PERSON);
		maklerMenu.addEntry("Delete Person", DELETE_PERSON);
		maklerMenu.addEntry("Back to Main Menu", BACK);
		
		//Process input
		while(true) {
			int response = maklerMenu.show();
			
			switch(response) {
				case NEW_PERSON:
					newPerson();
					break;
				case EDIT_PERSON:
					editPerson();
					break;
				case DELETE_PERSON:
					deletePerson();
					break;
				case BACK:
					return;
			}
		}
	}
	
	/**
	 * Creates a new person after the user has entered 
	 * the corresponding data.
	 */
	public void newPerson() {
		Person p = new Person();
		
		p.setFirstname(FormUtil.readString("Firstname"));
		p.setName(FormUtil.readString("Name"));
		p.setAddress(FormUtil.readString("Address"));
		service.addPerson(p);
		
		System.out.println(p.getFirstname()+" "+p.getName()+" with the ID "+p.getId()+" was created.");
	}
	
	/**
	 * Edits a person after the user has selected it.
	 */
	public void editPerson() {
		//Person selection menu
		Menu personSelectionMenu = new PersonSelectionMenu("Edit Person", service.getAllPersons());
		int id = personSelectionMenu.show();
		
		//Edit person?
		if(id != PersonSelectionMenu.BACK) {
			//Load person
			Person p = service.getPersonById(id);
			System.out.println(p.getFirstname()+" "+p.getName()+" is being edited. Empty fields remain unchanged.");
			
			//Reading in new data
			String newFirstname = FormUtil.readString("Firstname ("+p.getFirstname()+")");
			String newName = FormUtil.readString("Name ("+p.getName()+")");
			String newAddresss = FormUtil.readString("Address ("+p.getAddress()+")");
			
			//Set new data
			if(!newFirstname.equals(""))
				p.setFirstname(newFirstname);
			if(!newName.equals(""))
				p.setName(newName);
			if(!newAddresss.equals(""))
				p.setAddress(newAddresss);
		}
	}
	
	/**
	 * Deletes a person after the user has entered 
	 * the corresponding ID.
	 */
	public void deletePerson() {
		//Selection of the person
		Menu personSelectionMenu = new PersonSelectionMenu("Delete Person", service.getAllPersons());
		int id = personSelectionMenu.show();
		
		//Delete, if "back" has not been selected
		if(id != PersonSelectionMenu.BACK) {
			Person p = service.getPersonById(id);
			service.deletePerson(p);
		}
	}
}
