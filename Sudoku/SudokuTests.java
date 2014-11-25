/**************************************************
 * Document:
 * 		SudokuTests.java
 *
 * Description:
 * 		White box unit tests for Sudoku360 "java" files:
 *
 * Authors:
 * 		Scott Parkman
 *		Mallory Osugi
 *
 * Based on "Sudoku.java" by Travis Ostahowski
****************************************************/

import java.util.Observable;
import java.util.Observer;

public class SudokuTests //implements Observer // Overall strategy: Test game window-by-window, from the ground up, starting with the Login and Registration window.
{
	public static Agent testingAgent = new Agent();
	public static LogIn log_in;
	public static Register register;

	public static void main(String args[])
	{
		int regCase = 0;
		while(!testRegMenu(regCase))
			regCase++;
		int loginCase = 0;
		while(!testLoginMenu(loginCase))
			loginCase++;
		testMainMenu();
		testStatWindow();
	}

	public static boolean testRegMenu(int C) // Test the register component of "ServiceFrame.java", testing cases expected to fail first
	{
		register = new Register( testingAgent );
		switch(C)
		{
		case 0: // Register >> username out of bounds (5 and 11 letters)
			char[] pswd = {'p','p','p','p','p','p'}; // 6 character expected passable password.
			return register.register_request( "aaaaa", pswd) || register.register_request( "aaaaaaaaaaa", pswd);

		case 1: // Register >> password out of bounds (5 and 31 characters)
		break;

		case 2:
		break;

		default:
		break;
		}
		return true;
	}

	public static boolean testLoginMenu(int C) // Test the login component of "ServiceFrame.java", testing cases expected to fail first
	{
		log_in = new LogIn( testingAgent );
		switch(C) // Login test cases
		{
		case 0: // Login >> username out of bounds (5 and 11 letters)
			//return register.register_request( String user_name, char[] password);
		break;

		case 1: // Login >> password out of bounds (5 and 31 characters)
		break;

		case 2:
		break;

		default:
		break;
		}
		return true;
	}

	//
	public static void testMainMenu()
	{
	}

	//
	public static void testStatWindow()
	{
	}
}

class Agent implements Observer
{
	public User testingUser;

	public Agent()
	{
		testingUser = null;
	}

	@Override
	public void update(Observable subject, Object object_changed)
	{
		//NULL
	}

}