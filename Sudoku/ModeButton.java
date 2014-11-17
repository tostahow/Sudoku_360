/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		ModeButton.java
 * 
 * Description:
 * 		Specific Button for GUI with interactive settings
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------------------
										    Imports
-------------------------------------------------------------------------------------------------*/
import javax.swing.JButton;

public class ModeButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		ModeButton - Constructor
	 * 
	 * Description:
	 * 		Call super constructor to make new button with passed in name
	 --------------------------------------------------------------------------------------*/
	public ModeButton( String name )
	{
		super(name);
		this.setFocusable(false);
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		activateButton()
	 * 
	 * Description:
	 * 		set background and foreground to show an active button
	 --------------------------------------------------------------------------------------*/
	public void activateButton()
	{
		this.setBackground( SudokuCommon.BUTTON_ACTIVATED_COLOR );
		this.setForeground( SudokuCommon.BUTTON_ACTIVATED_TEXT );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		deactivateButton()
	 * 
	 * Description:
	 * 		set background and foreground to show a deactivated button
	 --------------------------------------------------------------------------------------*/
	public void deactivateButton()
	{
		this.setBackground( null );
		this.setForeground( null );
	}

}
