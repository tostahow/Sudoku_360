import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.Color;


public class CustomButton extends JButton 
{
	private static final long serialVersionUID = 1L;

	public CustomButton( String name, boolean in_game )
	{
		super( name );
		if( !(in_game) )
		{
			this.setForeground( SudokuCommon.PEN_COLOR );
			this.setBackground( SudokuCommon.COMPONENT_COLOR );
			this.setFont( SudokuCommon.TEXT_FONT );
		}
		else
		{
			this.setForeground( null );
			this.setBackground( SudokuCommon.BACKGROUND_COLOR );
			this.setFont( SudokuCommon.PEN_FONT );
		}
		this.setBorder( BorderFactory.createLineBorder( Color.black, 4));
		this.setFocusable(false);
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
		this.setBackground( SudokuCommon.BACKGROUND_COLOR );
		this.setForeground( null );
	}

}
