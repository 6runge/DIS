package de.dis2018.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Small helper class for menus Previously, menu options must be 
 * added using addEntry. The show() method displays the menu and 
 * returns the constant specified with the option.
 * 
 * Example:
 * Menu m = new Menu("Main Menu");
 * m.addEntry("Working hard", 0);
 * m.addEntry("Rest", 1);
 * m.addEntry("Go home", 2);
 * int choice = m.show();
 * 
 * The menu is then displayed:
 * Main Menu:
 * [1] Working hard
 * [2] Rest
 * [3] Go home
 * --
 * 
 * If the user now selects the first entry by entering 1 and then 
 * pressing the Enter key, then 0 is returned.
 */
public class Menu {
	private String title;
	private ArrayList<String> labels = new ArrayList<String>();
	private ArrayList<Integer> returnValues = new ArrayList<Integer>();
	
	/**
	 * Constructor.
	 * @param title Title of the menu e.g. "Main menu".
	 */
	public Menu(String title) {
		super();
		this.title = title;
	}
	
	/**
	 * Adds a menu entry to the menu
	 * @param label Name of the entry
	 * @param returnValue Constant returned when this entry is selected.
	 */
	public void addEntry(String label, int returnValue) {
		this.labels.add(label);
		this.returnValues.add(new Integer(returnValue));
	}
	
	/**
	 * Displays the menu
	 * @return  The constant of the selected menu item
	 */
	public int show()  {
		int selection = -1;
		
		System.out.println();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		while(selection == -1) {
			System.out.println(title+":");
			
			for(int i = 0; i < labels.size(); ++i) {
				System.out.println("["+(i+1)+"] "+labels.get(i));
			}
			
			System.out.print("-- ");
			try {
				selection = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
			
			} catch (IOException e) {
				
			}
			
			if(selection < 1 || selection > returnValues.size()) {
				System.err.println("Invalid input!");
				selection = -1;
			} 
		}
		
		return returnValues.get(selection-1);
	}
}
