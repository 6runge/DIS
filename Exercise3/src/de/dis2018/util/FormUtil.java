package de.dis2018.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Small helper class for reading in form data
 */
public class FormUtil {
	/**
	 * Reads a string from the standard input
	 * @param label Line that is displayed before the input.
	 * @return read line
	 */
	public static String readString(String label) {
		String ret = null;
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.print(label+": ");
			ret = stdin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	/**
	 * Reads a password from the standard input
	 * @param label Line that is displayed before the input.
	 * @return read-in line
	 */
	public static String readPassword(String label) {
		String ret = null;
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.print(label+": ");
			ret = stdin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * Displays a message and waits for confirmation from the user
	 * @param msg message
	 */
	public static void showMessage(String msg) {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.print(msg);
			stdin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads in an integer from the standard input
	 * @param label Line that is displayed before the input.
	 * @return read-in integer
	 */
	public static int readInt(String label) {
		int ret = 0;
		boolean finished = false;

		while(!finished) {
			String line = readString(label);
			
			try {
				ret = Integer.parseInt(line);
				finished = true;
			} catch (NumberFormatException e) {
				System.err.println("Invalid input: Please enter a number!");
			}
		}
		
		return ret;
	}
	
	/**
	 * Reads in a Date from the standard input
	 * @param label Line that is displayed before the input.
	 * @return read-in integer
	 */
	public static Date readDate(String label) {
		SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy");
		Date ret = null;
		boolean finished = false;

		while(!finished) {
			String line = readString(label);
			

			
			try {
				ret = parser.parse(line);
				finished = true;
			} catch (ParseException e) {
				System.err.println("Invalid input: Please enter a date in the format dd.MM.yyyy!");
			}
		}
		
		return ret;
	}
	
	/**
	 * Ask a yes/no question and returns the result
	 * @param label Line that is displayed before the input.
	 * @return read-in boolean
	 */
	public static boolean readBoolean(String label) {
		String line = null;
		boolean finished = false;
		boolean ret = false;
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		try {
			while(!finished) {
				System.out.print(label+" [y/n]: ");
				line = stdin.readLine().toLowerCase();
				
				if(line.equals("y") || line.equals("yes")) {
					ret = true;
					finished = true;
				} else if(line.equals("n") || line.equals("no")) {
					ret = false;
					finished = true;
				} else {
					System.err.println("Please enter yes or no or y or n!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
