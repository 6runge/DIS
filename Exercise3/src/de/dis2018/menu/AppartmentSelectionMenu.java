package de.dis2018.menu;

import java.util.Iterator;
import java.util.Set;

import de.dis2018.data.Apartment;

/**
 * A small menu showing all apartments from a set for selection
 */
public class AppartmentSelectionMenu extends Menu {
	public static final int BACK = -1;
	
	public AppartmentSelectionMenu(String title, Set<Apartment> apartmenten) {
		super(title);
		
		Iterator<Apartment> it = apartmenten.iterator();
		while(it.hasNext()) {
			Apartment w = it.next();
			addEntry(w.getStreet()+" "+w.getStreetnumber()+", "+w.getPostalcode()+" "+w.getCity(), w.getId());
		}
		addEntry("Back", BACK);
	}
}
