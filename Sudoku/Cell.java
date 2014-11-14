/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Cel.java
 * 
 * Description:
 * 		Contains the different fields (pen/pencil) that Users can store
 *		data in. Contains functionality of an individual cell on the board.
 *
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Cell extends JPanel implements KeyListener
{
	private static final long serialVersionUID = -7117662583558897501L;
	
	private boolean pen_locked;
	private boolean pencil_locked;
	private boolean full_lock;
	
	private JTextField pen_field;
	private JTextField pencil_field;
	private FieldType  cell_type;
	
	public Cell( BoardSize board_size, int id )
	{
		super();
		
		if( board_size == BoardSize.NINE )
			cell_type = FieldType.CELL9;
		else
			cell_type = FieldType.CELL16;
		
		initFields();
		cell_type = null;
		this.pen_locked = true;
		this.pencil_locked = false;
		this.full_lock = false;
		generatePanel();
		
	}
	
	public void setPenField( String value )
	{
		if( this.full_lock != true || this.pen_locked != true )
			this.pen_field.setText( value );
	}

	/*
	public void setPencilField( String value )
	{
		
		this.guesses = this.guesses + value;
		this.pencil_field.setText( guesses );
	}*/
	
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
		this.remove(pencil_field);
		pen_field.setBackground( Color.LIGHT_GRAY );
		pen_field.setEditable(!flag);
		pen_field.setEditable(!flag);
		this.repaint();
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
	
	private void initFields()
	{
		
		this.pen_field = new JTextField("");
		this.pen_field.setBorder( BorderFactory.createRaisedBevelBorder() );
		this.pen_field.setHorizontalAlignment( JTextField.CENTER );
		this.pen_field.addKeyListener(this);
		
		this.pencil_field = new JTextField("");
		this.pencil_field.setHorizontalAlignment( JTextField.CENTER );
		this.pencil_field.setBorder( BorderFactory.createRaisedBevelBorder() );
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
		this.add( pencil_field, BorderLayout.NORTH );
		
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
