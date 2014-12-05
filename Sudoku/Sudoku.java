/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Sudoku.java
 * 
 * Description:
 * 		Controls all of the components of the main display for the Sudoku Gui application.
 * 		Sudoku subscribes to LogIn and Registration services and generates the main frame that
 * 		the board and actions will be hosted on.
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import java.util.Observable;
import java.util.Observer;

public class Sudoku implements Observer
{
	/*-----------------------------------------------------------------------------------
								Private Class Members
	-----------------------------------------------------------------------------------*/
	private User 			current_user;	// Player currently signed in
	private ServiceFrame 	service;		// Log-in and register frame
	private MainMenu		game_menu;		// Game menu
	
	/*--------------------------------------------------------------------
	 *  main()
	 *  
	 *  Description:
	 *  	Creates a new instance of Sudoku
 	-------------------------------------------------------------------*/
	public static void main( String[] args )
	{
		new Sudoku();
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		Sudoku - constructor
	 * 
	 * Description:
	 * 		Calls ServiceFrame so that new user can log in or register.
	---------------------------------------------------------------------------------------*/
	public Sudoku()
	{
		service = new ServiceFrame( this );
		game_menu = null;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		gameMenu
	 * 
	 * Description:
	 * 		Calls MainMenu with current user
	---------------------------------------------------------------------------------------*/
	public void gameMenu()
	{
		game_menu = new MainMenu( this.current_user );
		game_menu.show();
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		update()
	 * 
	 * Description:
	 * 		Listens for any notification from the objects being observed and performs tasks 
	 * 		based on what was notified/updated.
	---------------------------------------------------------------------------------------*/
	@Override
	public void update(Observable subject, Object object_changed) 
	{
		
		/*---------------------------------------
		 LogIn service completed. Set current
		 user into logged in user.
		---------------------------------------*/
		if( object_changed instanceof User && ( subject instanceof ServiceFrame ) )
		{
			current_user = (User)object_changed;
			
		    /*---------------------------------------------------------------
	        Destroy references to previous information
	        ---------------------------------------------------------------*/
			service.deleteObservers();
			service.destroy();
			service = null;
			
		    /*---------------------------------------------------------------
	        Load game menu
	        ---------------------------------------------------------------*/
			gameMenu();
		}
		
		/*---------------------------------------
		 ServiceFrame was closed. Log In was not
		 complete. Exit process
		---------------------------------------*/
		if ( object_changed instanceof String  )
		{
			System.out.println( "ServiceFrame was closed. Exit system!" );
			System.exit(1);
			
		}	
	}
}
