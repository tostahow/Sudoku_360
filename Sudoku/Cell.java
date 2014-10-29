/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Cel.java
 * 
 * Description:
 * 		Contains the different fields (pen/pencil) that Users can store
 *		data in. Contains functionality of an individual cell on the board.
-------------------------------------------------------------------------------------------------*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Cell extends JPanel implements KeyListener
{
	private boolean pen_locked;
	private boolean pencil_locked;
	private boolean full_lock;
	private boolean is_error;
	private boolean is_highlighted;
	private String guesses;
	private int cell_id;
	
	private JTextField pen_field;
	private JTextField pencil_field;
	
	public Cell( FieldType cell_type, int id )
	{
		super();
		initFields( cell_type );
		this.cell_id = id;
		this.pen_locked = true;
		this.pencil_locked = false;
		this.full_lock = false;
		this.is_highlighted = false;
		this.guesses = "";
		generatePanel();
		
	}
	
	public void setPenField( String value )
	{
		if( this.full_lock != true || this.pen_locked != true )
			this.pen_field.setText( value );
	}
	
	public void setPencilField( String value )
	{
		
		System.out.print("Should be adding");
		this.guesses = this.guesses + value;
		this.pencil_field.setText( guesses );
	}
	
	public void setPencilLock( boolean flag )
	{
		if( this.full_lock != true )
		{
			this.pencil_locked = flag;
			pen_field.setEditable(!flag);
		}
	}
	
	public void setPenLock( boolean flag )
	{
		if( this.full_lock != true )
		{
			this.pen_locked = flag;
			this.pen_field.setEditable(!flag);
		}
	}
	
	public void setFullLock( boolean flag )
	{
		this.full_lock = flag;
		pen_field.setEditable(!flag);
		pen_field.setEditable(!flag);
	}
	
	private void setHighlighted( boolean flag )
	{
		this.is_highlighted = flag;
	}
	
	private void setError( boolean flag )
	{
		this.is_error = flag;
	}
	
	public boolean getPenLock()
	{
		return this.pen_locked;
	}
	
	public boolean getPencilLock()
	{
		return this.pencil_locked;
	}
	public boolean getFullLock()
	{
		return this.full_lock;
	}
	public boolean getHighlighted()
	{
		return this.is_highlighted;
	}
	
	public boolean getError()
	{
		return this.is_error;
	}
	
	private void initFields( FieldType cell_type )
	{
		this.pen_field = new JTextField("");
		this.pencil_field = new JTextField("");
		
		this.pen_field.setBorder( BorderFactory.createRaisedBevelBorder() );
		this.pen_field.setHorizontalAlignment( JTextField.CENTER );
		this.pen_field.addKeyListener(this);
		this.pencil_field.addKeyListener(this);
		
		pen_field.setDocument( new TextFieldLimit(1, cell_type) );
		pencil_field.setDocument( new TextFieldLimit(6, cell_type) );
		
		this.pen_field.setBackground( SudokuCommon.BACKGROUND_COLOR );
		this.pencil_field.setBackground( SudokuCommon.GUESS_FIELD_COLOR );
		
		this.pen_field.setFont( SudokuCommon.PEN_FONT );
		this.pencil_field.setFont( SudokuCommon.PENCIL_FONT );
		
		this.pen_field.setForeground( SudokuCommon.PEN_COLOR );
		this.pencil_field.setForeground( SudokuCommon.PENCIL_COLOR );
	}
	private void generatePanel()
	{
		setLayout( new BorderLayout() );
		this.add( pen_field, BorderLayout.CENTER );
		//this.add( pencil_field, BorderLayout.NORTH );
		
		setBorder( BorderFactory.createLineBorder(Color.black) );
		this.setPreferredSize( new Dimension(50, 50) );
	}

	@Override
	public void keyPressed(KeyEvent e) {	
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
