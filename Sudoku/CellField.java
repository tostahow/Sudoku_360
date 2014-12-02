/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		CellField.java
 * 
 * Description:
 * 		Custom version of JTextField to be used for Sudoku cells.
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import java.awt.Color;
import javax.swing.JTextField;


public class CellField extends JTextField 
{
	private static final long serialVersionUID = 1L;
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		CellField - Constructor
	 * 
	 * Description:
	 * 		Utilizes super constructor for JTextField while applying special attributes to
	 * 		Cell text fields.
	 --------------------------------------------------------------------------------------*/
	public CellField( String name, boolean is_pen )
	{
	    /*---------------------------------------------------------------
        Call JTextField constructor with name of custom text field.
        ---------------------------------------------------------------*/
		super( name );
		
		this.setHorizontalAlignment( JTextField.CENTER );
		this.setBorder( null );
		this.setCaretColor( Color.white );
		this.setBackground( SudokuCommon.COMPONENT_COLOR );
		this.setForeground( SudokuCommon.PEN_COLOR );
		
	    /*---------------------------------------------------------------
        if this cell field is a pen field, set font to pen font
        ---------------------------------------------------------------*/
		if( is_pen )
		{
			this.setFont( SudokuCommon.PEN_FONT );
		}
		else
			this.setFont( SudokuCommon.PENCIL_FONT);
		
	}
}
