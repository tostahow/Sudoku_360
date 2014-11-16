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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Cell extends JPanel implements KeyListener, MouseListener
{
	private static final long serialVersionUID = -7117662583558897501L;
	
	private boolean pen_mode;
	private boolean pencil_mode;
	private boolean eraser_mode;
	private boolean locked;
	private boolean pen_filled;
	
	private int eraser_count;
	
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
		
		initVariables();
		initFields();
		generatePanel();
		
	}
	
	private void initVariables()
	{
		this.pen_mode = true;
		this.pen_filled = false;
		this.pencil_mode = false;
		this.eraser_mode = false;
		this.eraser_count = 0;
		this.locked = false;
	}
	public void setPenField( String value )
	{
		if( this.locked != true )
			this.pen_field.setText( value );
	}
	
	public void setPencilMode( boolean flag )
	{
		if( this.locked != true )
		{
			if( flag )
			{
				this.pencil_field.setFont( SudokuCommon.PEN_FONT );
			}
			else
			{
				this.pencil_field.setFont( SudokuCommon.PENCIL_FONT );
			}
			
			this.pencil_mode = flag;
			pencil_field.setEditable(flag);
		}
	}
	
	public void setPenMode( boolean flag )
	{
		if( this.locked != true && this.pen_filled == false)
		{
			this.pen_mode = flag;
			this.pen_field.setEditable(flag);
		}
	}
	
	public void setEraserMode( boolean flag )
	{
		if( this.locked != true )
			this.eraser_mode = flag;
	}
	
	public void setLocked( boolean flag )
	{
		this.locked = flag;
		this.remove( pencil_field );
		pen_field.setBackground( Color.LIGHT_GRAY );
		pen_field.setEditable(!flag);
		this.repaint();
	}
	
	public boolean isPenMode()
	{
		return this.pen_mode;
	}
	
	public boolean isPencilMode()
	{
		return this.pencil_mode;
	}
	public boolean isLocked()
	{
		return this.locked;
	}
	
	public String getPenField()
	{
		return pen_field.getText();
	}
	
	private void initFields()
	{
		
		this.pen_field = new JTextField("");
		this.pen_field.setBorder( null );
		this.pen_field.setHorizontalAlignment( JTextField.CENTER );
		this.pen_field.addKeyListener(this);
		this.pen_field.addMouseListener(this);
		
		this.pencil_field = new JTextField("");
		this.pencil_field.setHorizontalAlignment( JTextField.CENTER );
		this.pencil_field.setBorder( null );

		
		
		pen_field.setDocument( new TextFieldLimit(1, cell_type) );
		pencil_field.setDocument( new TextFieldLimit(6, cell_type) );
		
		this.pen_field.setBackground( SudokuCommon.BACKGROUND_COLOR );
		this.pencil_field.setBackground( SudokuCommon.BACKGROUND_COLOR );
		
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
		
		setBorder( BorderFactory.createLineBorder( Color.black ) );
		this.setPreferredSize( new Dimension(50, 50) );
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if( (  ( (int)e.getKeyCode() == 8 ) && !eraser_mode ) )
		{
			System.out.println("Eraser Locked is Set!");
			e.consume();
		}
		else if( e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT )
		{
			System.out.println("Left and Right Ignored!");
			e.consume();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if( !this.pen_field.getText().equals("") )
		{
			this.pen_filled = true;
			this.pen_field.setEditable(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		if( eraser_mode && !( this.getPenField().equals("") ) && !locked ) 
		{
			this.setPenField("");
			this.pen_filled = false;
			eraser_count = eraser_count + 1;
			System.out.println( "Eraser Count = " + eraser_count );
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
