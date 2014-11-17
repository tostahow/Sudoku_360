import javax.swing.JRadioButton;


public class CustomRadioButton extends JRadioButton 
{
	private static final long serialVersionUID = 1L;

	public CustomRadioButton( String name )
	{
		super( name );
        this.setFont( SudokuCommon.TEXT_FONT );
        this.setBackground( SudokuCommon.BACKGROUND_COLOR );
        this.setFocusable( false );
		
	}
}
