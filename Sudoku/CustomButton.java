/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		CustomButton.java
 * 
 * Description:
 * 		Custom version of JButton with special attributes for Sudoku Game.
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomButton extends JButton implements MouseListener
{
	/*-----------------------------------------------------------------------------------
									Private Class Members
	-----------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	private boolean in_game;							// is the button used in game
	private boolean activated;							// flag for button being activated
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		CustomButton - Constructor
	 * 
	 * Description:
	 * 		Utilizes super constructor for JButton while applying special attributes to
	 * 		buttons
	 --------------------------------------------------------------------------------------*/
	public CustomButton( String name, boolean in_game )
	{
	    /*---------------------------------------------------------------
        call JButton constructor with name of button
        ---------------------------------------------------------------*/
		super( name );
		activated = false;
		this.in_game = in_game;
	    /*---------------------------------------------------------------
        If the button is not in game. Apply non game attributes
        ---------------------------------------------------------------*/
		if( !( in_game ) )
		{
			this.setForeground( SudokuCommon.PEN_COLOR );
			this.setBackground( SudokuCommon.COMPONENT_COLOR );
			this.setFont( SudokuCommon.TEXT_FONT );
		}
	    /*---------------------------------------------------------------
        button is in game, apply game attributes
        ---------------------------------------------------------------*/
		else
		{
			this.setForeground( null );
			this.setBackground( SudokuCommon.BACKGROUND_COLOR );
			this.setFont( SudokuCommon.PEN_FONT );
		}
		
	    /*---------------------------------------------------------------
        Creates a true button feel
        ---------------------------------------------------------------*/
		this.setBorder( BorderFactory.createLineBorder( Color.black, 4 ) );
		this.setFocusable( false );
		this.addMouseListener( this );
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
		activated = true;
		this.setBackground( SudokuCommon.COMPONENT_COLOR );
		this.setForeground( SudokuCommon.PEN_COLOR );
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
		activated = false;
		this.setBackground( SudokuCommon.BACKGROUND_COLOR );
		this.setForeground( null );
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if( in_game && !activated )
		{
			this.setBackground( SudokuCommon.COMPONENT_COLOR );
			this.setForeground( SudokuCommon.PEN_COLOR );	
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if( in_game && !activated )
		{
			this.setBackground( SudokuCommon.BACKGROUND_COLOR );
			this.setForeground( null );
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
