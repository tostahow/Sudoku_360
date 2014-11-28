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
	static Agent testingAgent = new Agent();
	static LogIn test_log_in;
	static Register test_register;
	static String usrName, pswd;

	public static void main(String args[])
	{
		int regCase = -1;
		test_register = new Register( testingAgent );
		while(!(testRegMenu(regCase) && !testRegMenu(regCase)))//redundancy test for case where username already exists
			regCase++;
		System.out.println("Successfull register test: Unit Case " + regCase);
		System.out.println("Username: " + usrName);
		System.out.println("Password: " + pswd);
		test_register.back_button.doClick();
		int loginCase = -1;
		test_log_in = new LogIn( testingAgent );
		while(!testLoginMenu(loginCase))
			loginCase++;
		System.out.println("Successfull login test: complete");
		testMainMenu();
		testStatWindow();
	}

	public static boolean testRegMenu(int C) // Test the register component of "ServiceFrame.java", testing cases expected to fail first
	{
		switch(C) // Registration test cases
		{
		case 0: // Register >> username out of bounds (5 letters)
			usrName = "aaaaa";
			pswd = "pppppp";
		break;
		case 1: // Register >> username out of bounds (11 letters)
			usrName = "aaaaaaaaaaa";
			pswd = "pppppp";
		break;
		case 2: // Register >> username contains special characters
			usrName = "a@aaaaa";
			pswd = "pppppp";
		break;
		case 3: // Register >> password out of bounds (5 letters)
			usrName = "aaaaa";
			pswd = "ppppp";
		break;
		case 4: // Register >> password out of bounds (31 letters)
			usrName = "aaaaaa";
			pswd = "ppppppppppppppppppppppppppppppp";
		break;
		default: // Expected Successfull Registeeration
			usrName = "Success" + C;
			pswd = "P@ssword" + C;
		}
		test_register.username_field.setText(usrName);
		test_register.pw_field.setText(pswd);
		test_register.v_pw_field.setText(pswd);
		test_register.reg_button.doClick();
		return test_register.success;
	}

	public static boolean testLoginMenu(int C) // Test the login component of "ServiceFrame.java", testing cases expected to fail first
	{
		switch(C) // Login test cases
		{
		case 0: // Login >> username out of bounds (5 letters)
			test_log_in.username_field.setText("aaaaa");
			test_log_in.pw_field.setText(pswd);
		break;
		case 1: // Login >> username out of bounds (11 characters)
			test_log_in.username_field.setText("aaaaaaaaaaa");
			test_log_in.pw_field.setText(pswd);
		break;
		case 2: // Login >> password out of bounds (5 letters)
			test_log_in.username_field.setText(usrName);
			test_log_in.pw_field.setText("ppppp");
		break;
		case 3: // Login >> password out of bounds (31 letters)
			test_log_in.username_field.setText(usrName);
			test_log_in.pw_field.setText("ppppppppppppppppppppppppppppppp");
		break;
		case 4:// Login >> password wrong
			test_log_in.username_field.setText(usrName);
			test_log_in.pw_field.setText("WrongP@ss");
		break;
		default:
			test_log_in.username_field.setText(usrName);
			test_log_in.pw_field.setText(pswd);
		break;
		}
		test_log_in.log_in_button.doClick();
		return test_log_in.success;
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