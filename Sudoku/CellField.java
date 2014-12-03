import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

import javax.swing.JTextField;


public class CellField extends JTextField implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String str = "";

	public CellField( String name, boolean is_pen )
	{
		super( name );
		this.setHorizontalAlignment( JTextField.CENTER );
		this.setBorder( null );
		this.setCaretColor(Color.white);
		this.setBackground( SudokuCommon.COMPONENT_COLOR );
		this.setForeground( SudokuCommon.PEN_COLOR );
		
		if( is_pen )
		{
			this.setFont( SudokuCommon.PEN_FONT );
		}
		else
			this.setFont( SudokuCommon.PENCIL_FONT);
		
	}
}
