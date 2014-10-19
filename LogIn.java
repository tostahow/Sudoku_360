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
-------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------------------
										    Imports
-------------------------------------------------------------------------------------------------*/
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;


public class LogIn extends Observable implements ActionListener
{	
	/*-----------------------------------------------------------------------------------
									Private Class Members
	-----------------------------------------------------------------------------------*/
	private User log_user;								// User that logged in
	private Boolean go_back;							// back button was pressed
	private JPanel log_panel;							// panel for log in
	
	private JButton log_in_button;						// button to log in with
	private JButton back_button;						// back button
	
	private JPasswordField pw_field;    				// password field
	private JTextField username_field;					// field for User name
	
	private final Font text_font = 
	new Font( "SansSerif", Font.ROMAN_BASELINE, 50 );	// font for text
	private final Color panel_color = Color.ORANGE;		// color of the panels on frame
	
	private boolean success;							// flag set if log in credentials are valid
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		LogIn - Constructor
	 * 
	 * Description:
	 * 		adds a new observer on creation
	 --------------------------------------------------------------------------------------*/
	public LogIn(Observer listener)
	{
		addObserver(listener);
		create_panel();
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		update_user()
	 * 
	 * Description:
	 * 		When a user is found to match the password settings. Make sure to set the local
	 * 		version of user to the logged in user and notify the listeners that a new user
	 * 		has access to the system.
	 --------------------------------------------------------------------------------------*/
	private void update_user( User logged_in_user )
	{
		this.log_user = logged_in_user;
		setChanged();
		notifyObservers( this.log_user );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		back_to_caller()
	 * 
	 * Description:
	 * 		return to calling class
	 --------------------------------------------------------------------------------------*/
	private void back_to_caller( Boolean flag )
	{
		this.go_back = flag;
		setChanged();
		notifyObservers(this.go_back);
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		create_panel()
	 * 
	 * Description:
	 * 		The panel users will use when attempting to log in. This panel consists of a
	 * 		fields for the user name and password along with a JButton that will attempt
	 * 		to find the user within the database so that they can play.
	 --------------------------------------------------------------------------------------*/
	private void create_panel()
	{

		/*---------------------------------------------------------------
		 						Instance Variables
		---------------------------------------------------------------*/
		JPanel button_panel;		// panel which holds back/log button
		GridLayout g_layout;		// layout of panel that holds buttons
		JLabel pw_label;			// label for password field
		JLabel username_label;		// name label;
	
		/*---------------------------------------------------------------
								Initialize Variables
		---------------------------------------------------------------*/
		back_button = new JButton( "Back" );
		button_panel = new JPanel();
		g_layout = new GridLayout( 1, 0 );
		log_panel = new JPanel();
		log_in_button = new JButton( "Log In" );
		username_field = new JTextField();
		username_label = new JLabel( "User Name:" );
		pw_label = new JLabel( "Password:" );
		pw_field = new JPasswordField();
		
		/*---------------------------------------
		 Set Fonts for each component
		---------------------------------------*/
		back_button.setFont( text_font );
		username_field.setFont( text_font );
		username_label.setFont( text_font );
		pw_label.setFont( text_font );
		pw_field.setFont( text_font );		
		log_in_button.setFont( text_font );
		
		/*---------------------------------------
		 Set field limits for text fields
		---------------------------------------*/
		username_field.setDocument( new TextFieldLimit( 10, FieldType.USERNAME ) );
		pw_field.setDocument( new TextFieldLimit( 30, FieldType.PASSWORD ) );
		
		/*---------------------------------------
		 Set background color for panel and 
		 button.
		---------------------------------------*/		
		log_panel.setBackground( panel_color );
		
		/*---------------------------------------------------------------
							  Set up Log in Panel
		---------------------------------------------------------------*/
		g_layout.setHgap( 200 );
		button_panel.setBackground( panel_color );
		button_panel.setLayout( g_layout );
		button_panel.add( log_in_button );
		button_panel.add( back_button );
		
		
		log_panel.setLayout( new GridLayout( 0, 1 ) );
		log_panel.add( username_label );
		log_panel.add( username_field );
		log_panel.add( pw_label );
		log_panel.add( pw_field );
		log_panel.add( button_panel );
	
		/*---------------------------------------
		 When log_in button is pressed. Check to
		 see if user name and password entries
		 are valid. If entries are valid request
		 the log in service.
		---------------------------------------*/
		log_in_button.addActionListener(this);
		
		back_button.addActionListener(this);
		
	}
	
	// Return the log in panel
	public JPanel get_panel()
	{
		return this.log_panel;
	}
	
	// clears all fields
	public void clear_fields()
	{
		username_field.setText("");
		pw_field.setText("");
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		log_in_request()
	 * 
	 * Description:
	 * 		Acquires user_name and password from the log in panel, and attempts to find the
	 * 		user within the database so the user can play the game.
	 --------------------------------------------------------------------------------------*/
	private boolean log_in_request( String user_name, char[] password ) 
	{
		User potential_user = Database.find_user( user_name );
		boolean valid;
		
		if( potential_user ==  null )
		{
			valid = false;
		}
		else
		{
			valid = Password.verify_password( potential_user, password );
			
			if( valid == true )
			{
				update_user( potential_user );
			}
		}
		return valid;
	}
	
	/*---------------------------------------
	 Method: actionPerformed
	 
	 Description:
	 	Handles different buttons pressed.
	---------------------------------------*/
	public void actionPerformed( ActionEvent e )
	{
		/*-------------------------------------------
		 Back button was pressed return to caller
		 ------------------------------------------*/
		if( e.getSource() == back_button )
		{
			Boolean flag = new Boolean( false );
			clear_fields();
			back_to_caller( flag );
		}
		
		if( e.getSource() == log_in_button )
		{
			/*---------------------------------------
			 If password or username field are empty
			 inform user, and do not proces request
			 to log in.
			---------------------------------------*/
			if( ( username_field.getText().length() < 6) 
			||  ( pw_field.getPassword().length < 6 ) )
				{
				
				JOptionPane.showMessageDialog
				( 		
	        		null, 
	        		"User Name or Password did not meet minimum length of 6"
	        		+ " of accepted characters.", 
	        		"Log In Fail", 
	        		JOptionPane.ERROR_MESSAGE 
				);
				
				return;
				}
			
			/*---------------------------------------
			 Call log_in_request which will attempt
			 to find the user within the system.
			---------------------------------------*/
			success = log_in_request( username_field.getText(), 
									  pw_field.getPassword() 
									);
			/*---------------------------------------
			 if the log_in_request fails. Reset 
			 the entry fields.
			---------------------------------------*/
			if( success == false )
				{
				
				/*---------------------------------------
				 Use option pane to alert user of failed
				 attempt.
				---------------------------------------*/
				JOptionPane.showMessageDialog
					( 		
		        		null, 
		        		"That user name and password combo did not match any"
		        		+ " users in the database.", 
		        		"Log In Fail", 
		        		JOptionPane.ERROR_MESSAGE 
					);
				
				clear_fields();
				
				}
			/*---------------------------------------
			 Show Success message!
			---------------------------------------*/
			else
				{
				
				JOptionPane.showMessageDialog
					(
					null,
					"Success",
					"Success",
					JOptionPane.OK_OPTION
					);
				
				clear_fields();
				}
		}
	}
}
