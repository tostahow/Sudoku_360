/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Cel.java
 * 
 * Description:
 * 		Contains Functionality for individual cells on the board.
 * 		Handles different inputs for the game modes (pencil, pen, and eraser).
 * 		Contains all logic for locking and setting cell values.
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
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Cell extends Observable implements KeyListener, MouseListener, Serializable
{
	/*-----------------------------------------------------------------------------------
								Private Class Members
	-----------------------------------------------------------------------------------*/
	private static final long 
	serialVersionUID = -7117662583558897501L;	// Serialized ID
	
	private boolean pen_mode;					// pen mode flag
	private boolean pencil_mode;				// pencil mode flag
	private boolean eraser_mode;				// eraser mode flag
	private boolean locked;						// locked flag
	private boolean pen_filled;                 // pen filled flag
	
	private JPanel display_panel;               // panel to hold all elements
	
	private int eraser_count;                   // # of eraser uses
	private CellField pen_field;				// Pen Field
	private CellField pencil_field;			    // Pencil Field
	private FieldType cell_type;				// Cell_type
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		Cell() - constructor
	 * 
	 * Description:
	 * 		Set Cell Type, Initialize Variables and Fields and Generate panel
	 --------------------------------------------------------------------------------------*/
	public Cell( Observer listener, BoardSize board_size )
	{
	    addObserver( listener );
		
	    display_panel = new JPanel();
		
		setCellType( board_size );
		initVariables();
		initFields();
		generatePanel();
		
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		initVariables()
	 * 
	 * Description:
	 * 		initialize private member variables
	 --------------------------------------------------------------------------------------*/
	private void initVariables()
	{
		this.pen_field = new CellField( "", true );
		this.pencil_field = new CellField( "", false);
		this.pen_mode = true;
		this.pen_filled = false;
		this.pencil_mode = false;
		this.eraser_mode = false;
		this.eraser_count = 0;
		this.locked = false;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setCellType()
	 * 
	 * Description:
	 * 		set cell type depending on board size
	 --------------------------------------------------------------------------------------*/
	public void setCellType( BoardSize board_size )
	{
		if( board_size == BoardSize.NINE )
			cell_type = FieldType.CELL9;
		else
			cell_type = FieldType.CELL16;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setPenField
	 * 
	 * Description:
	 * 		set the text of pen field so long as the cell is not locked
	 --------------------------------------------------------------------------------------*/
	public void setPenField( int value )
	{
		if( this.locked != true )
			this.pen_field.setText( SudokuCommon.values[value] );
	}
	
	
    /*---------------------------------------------------------------------------------------
     * Method:
     *      setPenFilled
     * 
     * Description:
     *      set whether this Cell was filled in by a pen.
     --------------------------------------------------------------------------------------*/
    public void setPenFilled( boolean flag )
    {
        this.pen_filled = flag;
    }
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setPencilField
	 * 
	 * Description:
	 * 		set the text of pencil field so long as the cell is not locked
	 --------------------------------------------------------------------------------------*/
	public void setPencilField( String value )
	{
		if( this.locked != true )
			this.pencil_field.setText( value );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setPencilMode
	 * 
	 * Description:
	 * 		if pencil mode is enabled, increase the font size as visual clue for user.
	 * 		if pencil mode is disabled, decrease the font to normal size
	 --------------------------------------------------------------------------------------*/
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
			
		    /*---------------------------------------------------------------
	        update pencil mode, and determine whether or not pencil field
	        is editable 
	        ---------------------------------------------------------------*/
			this.pencil_mode = flag;
			pencil_field.setEditable( flag );
		}
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setPenMode()
	 * 
	 * Description:
	 * 		if the cell has yet to be penned in and is not locked. Allow user to edit pen
	 * 		field.
	 --------------------------------------------------------------------------------------*/
	public void setPenMode( boolean flag )
	{
		if( this.locked != true && this.pen_filled == false)
		{
			this.pen_mode = flag;
			this.pen_field.setEditable( flag );
		}
		this.pen_mode = flag;
	}
	
    /*---------------------------------------------------------------------------------------
     * Method:
     *      setEraserCount
     * 
     * Description:
     *      set the number of erases that happened in this Cell
     --------------------------------------------------------------------------------------*/
    public void setEraserCount( int count )
    {
        this.eraser_count = count;
    }

	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setEraserMode
	 * 
	 * Description:
	 * 		when eraser mode is set user can erase penned in members
	 --------------------------------------------------------------------------------------*/
	public void setEraserMode( boolean flag )
	{
		if( this.locked != true )
			this.eraser_mode = flag;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setLocked
	 * 
	 * Description:
	 * 		Locks the cell completely.
	 --------------------------------------------------------------------------------------*/
	public void setLocked( boolean flag )
	{
		this.locked = flag;
		
	    /*---------------------------------------------------------------
        If Cell is to be locked. Change color and set the cell to be
        un-editable.
        ---------------------------------------------------------------*/
		if( locked )
		{
			pen_field.setForeground( Color.orange );
			pencil_field.setText("");
			pen_field.setEditable( !flag );
			pencil_field.setEditable( !flag );
		}
	    /*---------------------------------------------------------------
        Unlock the cell and make it editable once again.
        ---------------------------------------------------------------*/
		else
		{
			pen_field.setBackground( SudokuCommon.COMPONENT_COLOR );
			pen_field.setForeground( SudokuCommon.PEN_COLOR );
			pencil_field.setBackground( SudokuCommon.COMPONENT_COLOR );
			pencil_field.setForeground(  SudokuCommon.PEN_COLOR );
			pen_field.setEditable(!flag);
			pencil_field.setEditable(flag);
		}
		
	}
	
	/*---------------------------------------------------------------------------------------
     * Method:
     *      isPenFilled()
     * 
     * Description:
     *      returns true if this was filled in by a pen
     --------------------------------------------------------------------------------------*/
	public boolean isPenFilled()
	{
		    return this.pen_filled;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		isPenMode()
	 * 
	 * Description:
	 * 		returns true if pen mode is set
	 --------------------------------------------------------------------------------------*/
	public boolean isPenMode()
	{
		return this.pen_mode;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		isPencilMode()
	 * 
	 * Description:
	 * 		returns true if pencil mode is set
	 --------------------------------------------------------------------------------------*/
	public boolean isPencilMode()
	{
		return this.pencil_mode;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		isEraserMode()
	 * 
	 * Description:
	 * 		return true if eraser mode is set
	 --------------------------------------------------------------------------------------*/
	public boolean isEraserMode()
	{
		return this.eraser_mode;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		isLocked()
	 * 
	 * Description:
	 * 		return true if the cell is locked
	 --------------------------------------------------------------------------------------*/
	public boolean isLocked()
	{
		return this.locked;
	}
	 
	/*---------------------------------------------------------------------------------------
     * Method:
     *      getPenField()
     * 
     * Description:
     *      return the display panel
     --------------------------------------------------------------------------------------*/
    public JPanel getDisplayPanel()
    {
        return display_panel;
    }
    
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getPenField()
	 * 
	 * Description:
	 * 		return text of pen field
	 --------------------------------------------------------------------------------------*/
	public String getPenField()
	{
		return pen_field.getText();
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getEraserCount()
	 * 
	 * Description:
	 * 		return # of erases for cell
	 --------------------------------------------------------------------------------------*/
	public int getEraserCount()
	{
		return this.eraser_count;
	}
	
 	/*---------------------------------------------------------------------------------------
     * Method:
     *      getPenFieldObject()
     * 
     * Description:
     *      return pen field object
     --------------------------------------------------------------------------------------*/
    public CellField getPenFieldObject()
    {
        return pen_field;
    }
    
    /*---------------------------------------------------------------------------------------
     * Method:
     *      getPencilField()
     * 
     * Description:
     *      return text of pencil field
     --------------------------------------------------------------------------------------*/
    public String getPencilField()
    {
        return pencil_field.getText();
    }
    
    /*---------------------------------------------------------------------------------------
     * Method:
     *      getPencilFieldObject()
     * 
     * Description:
     *      return pencil field object
     --------------------------------------------------------------------------------------*/
    public CellField getPencilFieldObject()
    {
        return pencil_field;
    }
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		initFields()
	 * 
	 * Description:
	 * 		initialize pen and pencil fields
	 --------------------------------------------------------------------------------------*/
	private void initFields()
	{
		this.pen_field.addKeyListener( this );
		this.pen_field.addMouseListener( this );
		this.pencil_field.addMouseListener( this );
		
	    /*---------------------------------------------------------------
        Limit what values can be entered in each field
        ---------------------------------------------------------------*/
		pen_field.setDocument( new TextFieldLimit( 1, cell_type ) );
		pencil_field.setDocument( new TextFieldLimit( 6, cell_type ) );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		generatePanel()
	 * 
	 * Description:
	 * 		populate the panel that is a Cell with pen and pencil fields
	 --------------------------------------------------------------------------------------*/
	private void generatePanel()
	{
	    display_panel.setLayout( new BorderLayout() );
	    display_panel.setBorder( BorderFactory.createLineBorder( Color.black ) );
	    display_panel.setPreferredSize( new Dimension(50, 50) );
		
	    /*---------------------------------------------------------------
        add fields to cell's display panel
        ---------------------------------------------------------------*/
		display_panel.add( pen_field, BorderLayout.CENTER );
		display_panel.add( pencil_field, BorderLayout.NORTH );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		clear()
	 * 
	 * Description:
	 * 		clear all attributes to be in their original states
	 --------------------------------------------------------------------------------------*/
	public void clear()
	{
	    /*---------------------------------------------------------------
        Set text for field to nothing
        ---------------------------------------------------------------*/
		this.pen_field.setText("");
		this.pencil_field.setText("");
		
	    /*---------------------------------------------------------------
        reset all values to their initial states
        ---------------------------------------------------------------*/
		pen_filled = false;
		pencil_mode = false;
		pen_mode = true;
		eraser_mode = false;
		locked = false;
		eraser_count = 0;
		
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setPaused()
	 * 
	 * Description:
	 * 		set cells in paused state
	 --------------------------------------------------------------------------------------*/
	public void setPaused( boolean flag )
	{
	    /*---------------------------------------------------------------
        if paused is set, make cells gray
        ---------------------------------------------------------------*/
		if( flag )
		{
			pen_field.setBackground( Color.gray );
			pen_field.setForeground( Color.gray );
			pencil_field.setBackground( Color.gray );
			pencil_field.setForeground( Color.gray );
		}
	    /*---------------------------------------------------------------
        paused is not set, set cells to normal state
        ---------------------------------------------------------------*/
		else
		{
			if( locked )
			{
				pen_field.setBackground( SudokuCommon.COMPONENT_COLOR );
				pen_field.setForeground( Color.orange );
				pencil_field.setBackground( SudokuCommon.COMPONENT_COLOR );
				pencil_field.setForeground( Color.orange );
			}
			else
			{
				pen_field.setBackground( SudokuCommon.COMPONENT_COLOR );
				pen_field.setForeground( SudokuCommon.PEN_COLOR );
				pencil_field.setBackground( SudokuCommon.COMPONENT_COLOR );
				pencil_field.setForeground( SudokuCommon.PEN_COLOR );
			}
		}
	}
	
	/*---------------------------------------------------------------------------------------
	 *  						 All Listener Functions
	 --------------------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		keyPressed()
	 * 
	 * Description:
	 * 		Do not allow user to erase any keys using the backspace button.
	 * 		Do not allow user to alter pen field value with shift left/right
	 --------------------------------------------------------------------------------------*/
	@Override
	public void keyPressed(KeyEvent e) {
		
	    /*---------------------------------------------------------------
        Do not allow backspace ( keyCode() == 8 ) unless
        eraser mode is set
        ---------------------------------------------------------------*/
		if( (  ( (int)e.getKeyCode() == 8 ) && !eraser_mode ) )
		{
			System.out.println( "Eraser Locked is Set!" );
			e.consume();
		}
	    /*---------------------------------------------------------------
        Do not allow left or right input
        ---------------------------------------------------------------*/
		else if( e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT )
		{
			System.out.println( "Left and Right Ignored!" );
			e.consume();
		}
		
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		keyReleased()
	 * 
	 * Description:
	 * 		If pen value is not "" lock the pen field so that user cannot edit value
	 * 		unless eraser is used
	 --------------------------------------------------------------------------------------*/
	@Override
	public void keyReleased(KeyEvent e) 
	{
	    /*---------------------------------------------------------------
       	Once key is entered and released do not allow player to edit it
       	Notify observers that the user penned in this Cell.
        ---------------------------------------------------------------*/
		if( !this.pen_field.getText().equals("") && !this.locked)
		{
		    boolean wasFilledBefore = this.pen_filled;
		    
			this.pen_field.setText( pen_field.getText().toUpperCase() );
			this.pen_filled = true;
			this.pen_field.setEditable(false);
			
			if (!wasFilledBefore)
			{
			    setChanged();
                notifyObservers( "Penned" );
			}
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

	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		mouseEntered()
	 * 
	 * Description:
	 * 		change background and foreground when mousing over fields, depending on mode.
	 --------------------------------------------------------------------------------------*/
	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		if( !locked && isPenMode() )
		{
			pen_field.setBackground( SudokuCommon.BACKGROUND_COLOR );
			pen_field.setForeground( null );
			pen_field.setCaretColor( null );
		}
		else if( !locked && isPencilMode() )
		{
			pencil_field.setBackground( SudokuCommon.BACKGROUND_COLOR );
			pencil_field.setForeground( null );
			pencil_field.setCaretColor( null );
		}
		
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		mouseExited()
	 * 
	 * Description:
	 * 		reset fields to normal appearance on exit of field.
	 --------------------------------------------------------------------------------------*/
	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		if( !locked && isPenMode() )
		{
			pen_field.setBackground( SudokuCommon.COMPONENT_COLOR );
			pen_field.setForeground( SudokuCommon.PEN_COLOR );
			pen_field.setCaretColor( Color.white );
		}
		else if( !locked && isPencilMode() )
		{
			pencil_field.setBackground( SudokuCommon.COMPONENT_COLOR );
			pencil_field.setForeground( SudokuCommon.PEN_COLOR );
			pencil_field.setCaretColor( Color.white );
		}		
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		mousePressed()
	 * 
	 * Description:
	 * 		If eraser mode is set, and the cell which is clicked has a value set in its pen
	 * 		field. Erase the value and increment the eraser counter for keeping track
	 * 		of # of erases to help determine score.
	 --------------------------------------------------------------------------------------*/
	@Override
	public void mousePressed(MouseEvent arg0) 
	{
	    /*---------------------------------------------------------------
        Erase the value in pen field, so long as it is not locked
        ---------------------------------------------------------------*/
		if( eraser_mode && !( this.getPenField().equals("") ) && !locked ) 
		{
		
			setPenField( 0 );
		    /*---------------------------------------------------------------
		    Change background to give visual cue that value was erased
	        ---------------------------------------------------------------*/
			this.pen_field.setBackground( SudokuCommon.BACKGROUND_COLOR );
			this.pen_filled = false;
			eraser_count = eraser_count + 1;
			
			System.out.println( "Eraser Count = " + eraser_count );
		}
	}

	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		mouseReleased()
	 * 
	 * Description:
	 * 		reset background to end visual cue on erase
	 --------------------------------------------------------------------------------------*/
	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
	    /*---------------------------------------------------------------
       	if eraser mode is set, reset the background to end visual cue
        ---------------------------------------------------------------*/
		if( eraser_mode )
		{
			this.pen_field.setBackground( SudokuCommon.COMPONENT_COLOR );
		}
		
	}
}
