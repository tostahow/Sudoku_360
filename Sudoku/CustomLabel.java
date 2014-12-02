/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		CustomLabel.java
 * 
 * Description:
 * 		Custom version of JLabel with special attributes for Sudoku game.
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import javax.swing.JLabel;

public class CustomLabel extends JLabel 
{
	private static final long serialVersionUID = 1L;
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		CustomLabel - Constructor
	 * 
	 * Description:
	 * 		Utilizes super constructor for JLabel while applying special attributes to
	 * 		in game labels
	 --------------------------------------------------------------------------------------*/
	public CustomLabel( String name )
	{
	    /*---------------------------------------------------------------
        Call JLabel constructor with name of custom label
        ---------------------------------------------------------------*/
		super( name );
		
	    /*---------------------------------------------------------------
        Set Label Font and Background
        ---------------------------------------------------------------*/
		this.setFont( SudokuCommon.PEN_FONT );
		this.setBackground( SudokuCommon.BACKGROUND_COLOR );
	}
}
