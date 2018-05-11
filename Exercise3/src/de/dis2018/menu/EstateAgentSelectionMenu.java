package de.dis2018.menu;

import java.util.Iterator;
import java.util.Set;

import de.dis2018.data.EstateAgent;

/**
 *  A small menu showing all estate agents from a set for selection
 */
public class EstateAgentSelectionMenu extends Menu {
	public static final int BACK = -1;
	
	public EstateAgentSelectionMenu(String title, Set<EstateAgent> makler) {
		super(title);
		
		Iterator<EstateAgent> it = makler.iterator();
		while(it.hasNext()) {
			EstateAgent m = it.next();
			addEntry(m.getName(), m.getId());
		}
		addEntry("Back", BACK);
	}
}
