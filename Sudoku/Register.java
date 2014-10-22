/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Register.java
 * 
 * Description:
 * 		Register contains many methods in order to register a user, so that the user information 
 * 		can be stored and used within the Sudoku system. When a user is registered, a notification
 * 		will be sent to any listening objects in order to delete the register object.
 * 
-------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------------------
 									 	 Imports
-------------------------------------------------------------------------------------------------*/
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Observer;

public class Register extends UserService implements ActionListener
{
	/*-----------------------------------------------------------------------------------
									Private Class Members
	-----------------------------------------------------------------------------------*/			
	private JButton	back_button;							// back button
	private JButton reg_button;								// register button
	private JTextField username_field;						// username_field
	private JPasswordField pw_field;						// field for password
	private JPasswordField v_pw_field;						// verification field
	
	private boolean success;								// Flag for successful register
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		Register
	 * 
	 * Description:
	 * 		add new observer on creation
	 --------------------------------------------------------------------------------------*/
	public Register( Observer listener )
	{
		super( listener );
		this.setPanel( generatePanel() );
	}	
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		register_user()
	 * 
	 * Description:
	 * 		Let listeners know that there is a successful user registration.
	 --------------------------------------------------------------------------------------*/
	public void updateUser( User user_to_register )
	{
		this.setUser( user_to_register );
		Database.add( this.getUser() );
		setChanged();
		notifyObservers( this.getUser() );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		generatePanel()
	 * 
	 * Description:
	 * 		The panel users will use when attempting to register. This panel consists of a
	 * 		fields for the user name and password along with a JButton that will attempt
	 * 		to create the user within the database so that they can play.
	 --------------------------------------------------------------------------------------*/
	private JPanel generatePanel()
	{
		/*---------------------------------------------------------------
		 						Instance Variables
		---------------------------------------------------------------*/
		JPanel button_panel;     	// panel to hold reg/back buttons
		JPanel reg_panel;			// registration panel
		GridLayout g_layout;		// layout for buttons
		JLabel pw_label;			// label for password field
		JLabel username_label;		// name label
		JLabel v_pw_label;			// verification label
		
		/*---------------------------------------------------------------
								Initialize Variables
		---------------------------------------------------------------*/
		back_button = new JButton( "Back" );
		g_layout = new GridLayout( 1, 0 );
		button_panel = new JPanel();
		reg_panel = new JPanel();
		reg_button = new JButton( "Register" );
		username_field = new JTextField();
		username_label = new JLabel( "User Name:" );
		pw_label = new JLabel( "Password:" );
		pw_field = new JPasswordField();
		v_pw_label = new JLabel( "Verify Password:" );
		v_pw_field = new JPasswordField();
		
		/*---------------------------------------
		 Set Fonts for each component
		---------------------------------------*/		
		back_button.setFont( SudokuCommon.TEXT_FONT );
		username_field.setFont( SudokuCommon.TEXT_FONT );
		username_label.setFont( SudokuCommon.TEXT_FONT );
		pw_label.setFont( SudokuCommon.TEXT_FONT );
		pw_field.setFont( SudokuCommon.TEXT_FONT );
		reg_button.setFont( SudokuCommon.TEXT_FONT );
		v_pw_label.setFont( SudokuCommon.TEXT_FONT );
		v_pw_field.setFont( SudokuCommon.TEXT_FONT );
		
		/*---------------------------------------
		 Set field limits for text fields
		---------------------------------------*/
		username_field.setDocument( new TextFieldLimit( 10, FieldType.USERNAME ) );
		pw_field.setDocument( new TextFieldLimit( 30, FieldType.PASSWORD ) );
		v_pw_field.setDocument( new TextFieldLimit( 30, FieldType.PASSWORD ) );
		
		/*---------------------------------------
		 Set background color for panel and 
		 button.
		---------------------------------------*/		
		button_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
		reg_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
		
		/*---------------------------------------------------------------
							  Set up Log in Panel
		---------------------------------------------------------------*/
		g_layout.setHgap( 200 );
		button_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
		button_panel.setLayout( g_layout );
		button_panel.add( reg_button );
		button_panel.add( back_button );
		
		reg_panel.setLayout( new GridLayout( 0, 1 ) );
		reg_panel.add( username_label );
		reg_panel.add( username_field );
		reg_panel.add( pw_label );
		reg_panel.add( pw_field );
		reg_panel.add( v_pw_label );
		reg_panel.add( v_pw_field );
		reg_panel.add( button_panel );
	
		
		reg_button.addActionListener(this);
		back_button.addActionListener(this);
		
		return reg_panel;
		
	}
	
	// clears all fields
	public void clearFields()
	{
		username_field.setText("");
		pw_field.setText("");
		v_pw_field.setText("");
	}
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		register_request()
	 * 
	 * Description:
	 * 		attemps to register a new user into database.
	 --------------------------------------------------------------------------------------*/
	private boolean register_request( String user_name, char[] password)
	{
		/*---------------------------------------------------------------
								Instance Variables
		---------------------------------------------------------------*/
		User reg_user;					// user that will be registered
		boolean flag;					// return success or fail
		String salt;					// random salt for user
		String hash;					// secure password hash
		
		
		if( Database.find_user(user_name) != null )
			{
			/*---------------------------------------
			 Username is already taken. Set to fail
			---------------------------------------*/
			flag = false;
			}
		else
			{
			flag = true;
			
			/*---------------------------------------
			 Attempt to create a new user with 
			 random salt and securely hashed password
			---------------------------------------*/
			try
				{
				salt = Password.generate_Salt();	
				hash = Password.get_SHA_256_secure_password(password, salt );
				reg_user = new User( user_name, hash, salt );
				
				/*---------------------------------------
				 notify listeners that update has 
				 occurred.
				---------------------------------------*/
				updateUser( reg_user );
				
				}
			/*---------------------------------------
			 Something went wrong during the pass
			 word securing process. Fail out.
			---------------------------------------*/
			catch( NoSuchProviderException | NoSuchAlgorithmException e)
				{
				System.out.print("Error when creating password elements");
				reg_user = null;
				e.getStackTrace();
				flag = false;
				}
			}	
		
		return flag;
	}	
	
	
	public void actionPerformed( ActionEvent e)
	{
		/*---------------------------------------
		 Back button request sent. Tell observers
		 to dispose of the register panel.
		---------------------------------------*/
		if( e.getSource() == back_button )
		{
			clearFields();
			goBack();
		}
		
		/*---------------------------------------
		 When log_in button is pressed. Check to
		 see if user name and password entries
		 are valid. If entries are valid request
		 the log in service.
		---------------------------------------*/
		if( e.getSource() == reg_button )
		{
			/*---------------------------------------
			 If password or username field are empty
			 inform user, and do not proces request
			 to log in.
			---------------------------------------*/
			if( ( username_field.getText().length() < 6 ) 
			||  ( pw_field.getPassword().length < 6) )
				{
				
				errorMessage("Entered user name or password must exceed 6 characters.");
				v_pw_field.setText("");
				pw_field.setText("");
				
				return;
				}
			
			if( ( !Arrays.equals( pw_field.getPassword(), v_pw_field.getPassword() ) ) )
				{
				
				errorMessage("Passwords do not match!");
				clearFields();
				
				return;
				}
			/*---------------------------------------
			 Call log_in_request which will attempt
			 to find the user within the system.
			---------------------------------------*/
			success = register_request( username_field.getText(), 
									 	pw_field.getPassword() 
								 	  );
			/*---------------------------------------
			 if the register_request fails. Reset 
			 the entry fields.
			---------------------------------------*/
			if( success == false )
				{
				
				/*---------------------------------------
				 Use option pane to alert user of failed
				 attempt.
				---------------------------------------*/
				errorMessage("User Name Exists.");
				clearFields();
				
				}
			/*---------------------------------------
			 if the register_request succeeds.
			---------------------------------------*/
			else
				{
				successMessage("Registration Successful!");
				clearFields();
				}
		}
	}
}
