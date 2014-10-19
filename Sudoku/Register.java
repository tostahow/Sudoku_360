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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class Register extends Observable implements ActionListener
{
	/*-----------------------------------------------------------------------------------
									Private Class Members
	-----------------------------------------------------------------------------------*/
	private User new_user;									// registered user
	private Boolean go_back;								// go back to calling function
	
	private JPanel reg_panel;								// reg panel		
	private JButton	back_button;							// back button
	private JButton reg_button;								// register button
	private JTextField username_field;						// username_field
	private JPasswordField pw_field;						// field for password
	private JPasswordField v_pw_field;						// verification field
	
	private final Font text_font = 
	new Font( "SansSerif", Font.ROMAN_BASELINE, 50 );		// Text font
	private final Color panel_color = Color.ORANGE;			// color of the panels on frame
	
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
		addObserver( listener );
		create_panel();
	}	
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		back_to_caller()
	 * 
	 * Description:
	 * 		Let listeners know that the back button was pressed
	 --------------------------------------------------------------------------------------*/
	private void back_to_caller( Boolean flag )
	{
		this.go_back = flag;
		setChanged();
		notifyObservers( this.go_back );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		register_user()
	 * 
	 * Description:
	 * 		Let listeners know that there is a successful user registration.
	 --------------------------------------------------------------------------------------*/
	private void register_user( User user_to_register )
	{
		this.new_user = user_to_register;
		Database.add( this.new_user );
		setChanged();
		notifyObservers( this.new_user );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		create_panel()
	 * 
	 * Description:
	 * 		The panel users will use when attempting to register. This panel consists of a
	 * 		fields for the user name and password along with a JButton that will attempt
	 * 		to create the user within the database so that they can play.
	 --------------------------------------------------------------------------------------*/
	private void create_panel()
	{
		/*---------------------------------------------------------------
		 						Instance Variables
		---------------------------------------------------------------*/
		JPanel button_panel;     	// panel to hold reg/back buttons
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
		back_button.setFont( text_font );
		username_field.setFont( text_font );
		username_label.setFont( text_font );
		pw_label.setFont( text_font );
		pw_field.setFont( text_font );
		reg_button.setFont( text_font );
		v_pw_label.setFont( text_font );
		v_pw_field.setFont( text_font );
		
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
		button_panel.setBackground( panel_color );
		reg_panel.setBackground( panel_color );
		
		/*---------------------------------------------------------------
							  Set up Log in Panel
		---------------------------------------------------------------*/
		g_layout.setHgap(200);
		button_panel.setBackground( panel_color );
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
		
	}
	
	// Return the log in panel
	public JPanel get_panel()
	{
		return this.reg_panel;
	}
	
	// clears all fields
	public void clear_fields()
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
				register_user( reg_user );
				
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
			Boolean flag = new Boolean( false );
			clear_fields();
			back_to_caller( flag );
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
				
				JOptionPane.showMessageDialog
				( 		
       		null, 
       		"Entere User Name or Password must exceed 6 characters", 
       		"Register Fail", 
       		JOptionPane.ERROR_MESSAGE 
				);
				
				v_pw_field.setText("");
				pw_field.setText("");
				
				return;
				}
			
			if( ( !Arrays.equals( pw_field.getPassword(), v_pw_field.getPassword() ) ) )
				{
				JOptionPane.showMessageDialog
					( 		
	       		null, 
	       		"That entered passwords do not match!", 
	       		"Password Fail", 
	       		JOptionPane.ERROR_MESSAGE 
					);
	
				clear_fields();
				
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
				JOptionPane.showMessageDialog
					( 		
		        		null, 
		        		"That user name already exist", 
		        		"Register Fail", 
		        		JOptionPane.ERROR_MESSAGE 
					);
				
				clear_fields();
				}
			/*---------------------------------------
			 if the register_request succeeds.
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
