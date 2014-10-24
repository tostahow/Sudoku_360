/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Cel.java
 * 
 * Description:
 * 		Contains the different fields (pen/pencil) that Users can store
 *		data in. Contains functionality of an individual cell on the board.
-------------------------------------------------------------------------------------------------*/
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Cell
{
	private boolean pen_locked;
	private boolean pencil_locked;
	private boolean is_error;
	private boolean is_highlighted;
	private String guesses;
	
	private JPanel cell_panel;
	private JTextField pen_field;
	private JTextField pencil_field;
	
	private final Color pen_color = Color.black;
	private final Color pencil_color = Color.white;
	
	
	public Cell( boolean penLock, boolean pencilLock, boolean highlighted )
	{
		super();
		this.pen_locked = penLock;
		this.pencil_locked = pencilLock;
		this.is_highlighted = highlighted;
		this.guesses = "";
	}
	
	public void setPanel( JPanel panel )
	{
		this.cell_panel = panel;
	}
	
	public void setPenField( String value )
	{
		this.pen_field.setText( value );
	}
	
	public void setPencilField( String value )
	{
		this.guesses = this.guesses + " " + value;
		this.pencil_field.setText( guesses );
	}
	
}
