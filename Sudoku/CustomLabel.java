import javax.swing.JLabel;


public class CustomLabel extends JLabel 
{
	private static final long serialVersionUID = 1L;

	public CustomLabel( String name )
	{
		super( name );
		this.setFont(SudokuCommon.PEN_FONT);
		this.setBackground(SudokuCommon.BACKGROUND_COLOR);
	}
}
