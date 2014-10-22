/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		UserService.java
 * 
 * Description:
 * 		Base class of Register and Log In and comes packed with different functions
 * 		that are typical to User Services.
-------------------------------------------------------------------------------------------------*/

import java.util.Observer;
import java.util.Observable;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

abstract class UserService extends Observable 
{
	private JPanel service_panel;
	private User client;
	
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
		service_panel = null;
		client = null;
	}
	
	/*---------------------------------------------------------------------------------------
	 *						         Getters and Setters
	 --------------------------------------------------------------------------------------*/
	public void setPanel( JPanel panel )
	{
		service_panel = panel;
	}
	
	public JPanel getPanel()
	{
		return service_panel;
	}
	
	public void setUser( User new_user )
	{
		client = new_user;
	}
	
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
		notifyObservers("");
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
