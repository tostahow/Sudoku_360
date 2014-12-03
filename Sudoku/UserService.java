/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		UserService.java
 * 
 * Description:
 * 		Base class of Register and Log In and comes packed with different functions
 * 		that are typical to User Services.
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import java.util.Observer;
import java.util.Observable;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

abstract class UserService extends Observable 
{
	private JPanel service_panel;
	private User client;
	private TextFieldLimit user_name_limit;
	private TextFieldLimit password_limit;
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		UserService() - constructor
	 * 
	 * Description:
	 * 		Add new listener and set panel and user objects to null;
	 --------------------------------------------------------------------------------------*/
	public UserService( Observer listener )
	{
		addObserver( listener );
		user_name_limit = new TextFieldLimit( 10, FieldType.USERNAME );
		password_limit = new TextFieldLimit( 30, FieldType.PASSWORD );
		service_panel = null;
		client = null;
	}
	/*---------------------------------------------------------------------------------------
	 *						         Getters and Setters
	 --------------------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getUserNameLimit
	 * 
	 * Description:
	 * 		returns user name text field limit
	 --------------------------------------------------------------------------------------*/
	public TextFieldLimit getUserNameLimit()
	{
		return user_name_limit;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getPasswordLimit
	 * 
	 * Description:
	 * 		returns password text field limit
	 --------------------------------------------------------------------------------------*/
	public TextFieldLimit getPasswordLimit()
	{
		return password_limit;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setPanel
	 * 
	 * Description:
	 * 		sets service panel to passed in panel argument
	 --------------------------------------------------------------------------------------*/
	public void setPanel( JPanel panel )
	{
		service_panel = panel;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getPanel
	 * 
	 * Description:
	 * 		returns service panel for different user services
	 --------------------------------------------------------------------------------------*/
	public JPanel getPanel()
	{
		return service_panel;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setUser
	 * 
	 * Description:
	 * 		set user to a new user
	 --------------------------------------------------------------------------------------*/
	public void setUser( User new_user )
	{
		client = new_user;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getUser
	 * 
	 * Description:
	 * 		returns user
	 --------------------------------------------------------------------------------------*/
	public User getUser()
	{
		return client;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		goBack()
	 * 
	 * Description:
	 * 		Let listeners know that the back button was pressed
	 --------------------------------------------------------------------------------------*/
	public void goBack()
	{
		setChanged();
		notifyObservers( "" );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		errorMessage()
	 * 
	 * Description:
	 * 		Alert user know that there was an error, include passed in message.
	 --------------------------------------------------------------------------------------*/
	public void errorMessage( String message )
	{
		JOptionPane.showMessageDialog
		( 		
    		null, 
    		message, 
    		"Service Failure!", 
    		JOptionPane.ERROR_MESSAGE 
		);
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		successMessage()
	 * 
	 * Description:
	 * 		Alert user know that there was success, include passed in message.
	 --------------------------------------------------------------------------------------*/
	public void successMessage( String message )
	{
		JOptionPane.showMessageDialog
		( 		
    		null, 
    		message, 
    		"Service Success!", 
    		JOptionPane.OK_OPTION
		);
	}
	
	/*---------------------------------------------------------------------------------------
	 * 	   Abstract Classes to be implemented and redefined for each derived class 
	 --------------------------------------------------------------------------------------*/
	abstract void updateUser( User new_user );
	abstract void clearFields();
}
