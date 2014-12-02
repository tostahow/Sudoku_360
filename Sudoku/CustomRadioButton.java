/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		CustomRadioButton.java
 * 
 * Description:
 * 		Custom version of JRadioButton with special attributes for Sudoku game.
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import javax.swing.JRadioButton;

public class CustomRadioButton extends JRadioButton 
{
	private static final long serialVersionUID = 1L;
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		CustomRadioButton - Constructor
	 * 
	 * Description:
	 * 		Utilizes super constructor for JRadioButton while applying special attributes to
	 * 		in game Radio Buttons
	 --------------------------------------------------------------------------------------*/
	public CustomRadioButton( String name )
	{
	    /*---------------------------------------------------------------
        Call JRadioButton constructor with name of custom radio button
        ---------------------------------------------------------------*/
		super( name );
		
        this.setFont( SudokuCommon.TEXT_FONT );
        this.setBackground( SudokuCommon.BACKGROUND_COLOR );
        this.setFocusable( false );
		
	}
}
