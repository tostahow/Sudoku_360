/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		LogIn.java
 * 
 * Description:
 * 		LogIn contains many methods in order to log in a user, so that the user information 
 * 		can be updated and accessed within the Sudoku system. When a user is registered, 
 *      a notification will be sent to any listening objects in order to delete the LogIn
 *      object.
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------------------
										    Imports
-------------------------------------------------------------------------------------------------*/
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

public class LogIn extends UserService implements ActionListener
{	
	/*-----------------------------------------------------------------------------------
									Private Class Members
	-----------------------------------------------------------------------------------*/
	private CustomButton log_in_button;						// button to log in with
	private CustomButton back_button;						// back button
	private JPasswordField pw_field;    					// password field
	private JTextField username_field;						// field for User name
	private boolean success;								// flag set if log in credentials are valid
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		LogIn - Constructor
	 * 
	 * Description:
	 * 		adds a new observer on creation
	 --------------------------------------------------------------------------------------*/
	public LogIn(Observer listener)
	{
		super( listener );
		this.setPanel( generatePanel() );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		updateUser()
	 * 
	 * Description:
	 * 		When a user is found to match the password settings. Make sure to set the local
	 * 		version of user to the logged in user and notify the listeners that a new user
	 * 		has access to the system.
	 --------------------------------------------------------------------------------------*/
	public void updateUser( User logged_in_user )
	{
		this.setUser( logged_in_user );
		setChanged();
		notifyObservers( this.getUser() );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		generatePanel()
	 * 
	 * Description:
	 * 		The panel users will use when attempting to log in. This panel consists of a
	 * 		fields for the user name and password along with a JButton that will attempt
	 * 		to find the user within the database so that they can play.
	 --------------------------------------------------------------------------------------*/
	private JPanel generatePanel()
	{
		/*---------------------------------------------------------------
		 						Instance Variables
		---------------------------------------------------------------*/
		JPanel log_panel;			// panel to be created
		JPanel button_panel;		// panel which holds back/log button
		GridLayout g_layout;		// layout of panel that holds buttons
		JLabel pw_label;			// label for password field
		JLabel username_label;		// name label;
	
		/*---------------------------------------------------------------
		Initialize Variables
		---------------------------------------------------------------*/
		back_button = new CustomButton( "Back", false );
		button_panel = new JPanel();
		g_layout = new GridLayout( 1, 0 );
		log_panel = new JPanel();
		log_in_button = new CustomButton( "Log In", false );
		username_field = new JTextField();
		username_label = new JLabel( "User Name:" );
		pw_label = new JLabel( "Password:" );
		pw_field = new JPasswordField();
		
	    /*---------------------------------------------------------------
		Set fonts for user name and password fields
        ---------------------------------------------------------------*/
		username_field.setFont( SudokuCommon.TEXT_FONT );
		username_label.setFont( SudokuCommon.TEXT_FONT );
		pw_label.setFont( SudokuCommon.TEXT_FONT );
		pw_field.setFont( SudokuCommon.TEXT_FONT );		
		
	    /*---------------------------------------------------------------
		Set text field limits for log in user name and password fields
        ---------------------------------------------------------------*/
		username_field.setDocument( this.getUserNameLimit() );
		pw_field.setDocument( this.getPasswordLimit() );
		
	    /*---------------------------------------------------------------
		Set background color for log in panel
        ---------------------------------------------------------------*/	
		log_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
		
	    /*---------------------------------------------------------------
		Set attributes for board panel and log panel, and add components
        ---------------------------------------------------------------*/
		g_layout.setHgap( 200 );
		button_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
		button_panel.setLayout( g_layout );
		button_panel.add( log_in_button );
		button_panel.add( back_button );
		
		
		log_panel.setLayout( new GridLayout( 0, 1 ) );
		log_panel.add( username_label );
		log_panel.add( username_field );
		log_panel.add( pw_label );
		log_panel.add( pw_field );
		log_panel.add( button_panel );
		
	    /*---------------------------------------------------------------
		When log_in button is pressed. Check to see if user name and 
		password entries are valid. If entries are valid request the 
		log in service.
        ---------------------------------------------------------------*/
		log_in_button.addActionListener(this);
		back_button.addActionListener(this);
		
		return log_panel;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		clearFields()
	 * 
	 * Description:
	 * 		Clears all applicable fields. Must be implemented.
	 --------------------------------------------------------------------------------------*/
	
	public void clearFields()
	{
		username_field.setText( "" );
		pw_field.setText( "" );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		log_in_request()
	 * 
	 * Description:
	 * 		Acquires user_name and password from the log in panel, and attempts to find the
	 * 		user within the database so the user can play the game.
	 --------------------------------------------------------------------------------------*/
	private boolean logInRequest( String user_name, char[] password ) 
	{
		boolean valid;
		
	    /*---------------------------------------------------------------
        Attempt to find user that is attempting to log in
        ---------------------------------------------------------------*/
		User potential_user = Database.find_user( user_name );
		
	    /*---------------------------------------------------------------
        User not found
        ---------------------------------------------------------------*/
		if( potential_user ==  null )
		{
			valid = false;
		}
	    /*---------------------------------------------------------------
        User found
        ---------------------------------------------------------------*/
		else
		{
		    /*---------------------------------------------------------------
	        Check if entered password matches the potential users password
	        ---------------------------------------------------------------*/
			valid = Password.verify_password( potential_user, password );
			
		    /*---------------------------------------------------------------
	        Passwords match
	        ---------------------------------------------------------------*/
			if( valid == true )
			{
				updateUser( potential_user );
			}
		}

		return valid;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		actionPerformed
	 * 
	 * Description:
	 * 		action listener fired. Do different operations depending on source
	 --------------------------------------------------------------------------------------*/
	public void actionPerformed( ActionEvent e )
	{
		
	    /*---------------------------------------------------------------
       	Back Button was pressed, return to calling class.
        ---------------------------------------------------------------*/		 
		if( e.getSource() == back_button )
		{
			clearFields();
			goBack();
		}
		
		if( e.getSource() == log_in_button )
		{

		    /*---------------------------------------------------------------
	        If password or user name field are empty inform user, and do not 
	        process request to log in.
	        ---------------------------------------------------------------*/
			if( ( username_field.getText().length() < 6) 
			||  ( pw_field.getPassword().length < 6 ) )
				{
				errorMessage( "user name or password does not meet 6 character limit" );
				return;
				}
			
		    /*---------------------------------------------------------------
			Call log_in_request which will attempt to find the user within 
			the system.
	        ---------------------------------------------------------------*/
			success = logInRequest( username_field.getText(), 
									  pw_field.getPassword() 
									);
			
		    /*---------------------------------------------------------------
			If the log_in_request fails. Reset the entry fields.
	        ---------------------------------------------------------------*/
			if( success == false )
				{
				
			    /*---------------------------------------------------------------
				Use option pane to alert user of failed attempt.
		        ---------------------------------------------------------------*/
				errorMessage( "user name and password combo does not match stored records" );
				clearFields();
				
				}
		    /*---------------------------------------------------------------
			Show Success Message
	        ---------------------------------------------------------------*/
			else
				{
				
				successMessage( "Log In Successful" );
				clearFields();
				
				}
		}
	}
}

