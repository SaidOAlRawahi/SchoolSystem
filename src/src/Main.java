package src;

import java.util.*;

public class Main {
	
	static School mySchool = new School();
	static Stack<Menu> menues = new Stack<Menu>();
	static Scanner sc = new Scanner(System.in);
	static int longestNameLength = 0;
	
	public static void main(String[] args) {
		
		MainMenu mainMenu = new MainMenu();
		menues.push(mainMenu);
		
		while(!menues.isEmpty()) {
			menues.peek().showMenu();
			int input = getInput(menues.peek(),true);
			menues.peek().menuItems.get(input).triggerAction();
		}
		
		System.out.println("Goodbye");
		System.gc();
        System.runFinalization();
		
	}
	
	static int getInput(Menu menu, boolean isMenu) {
		while(true) {
			String input = sc.next();
			
			try {
				Integer inputInt = Integer.parseInt(input);
				
				if(!isMenu) {
					if(inputInt > 100 || inputInt < 0) {
						System.out.println("Grades are not in range of 0-100");
					}
					else {
						return inputInt;
					}
				}
				
				else if (inputInt <= 0 || inputInt >= menu.menuItems.size()+1) {
					System.out.println("Invalid Input");
				}
				else {
					return inputInt-1;
				}
			}
			catch(Exception e) {
				System.out.println("Invalid Input");
			}
		}
	}
	
	static boolean repeatProcess(String text) {
		while(true) {
			System.out.print("Do You Want To Enter More " + text+ "? (Y/N): ");
			switch(sc.next()) {
			case "y":
			case "Y":
				return true;
				
			case "n":
			case "N":
				return false;
				
			default:
				System.out.println("Invalid Input");
				break;
			}
		}
	}
}