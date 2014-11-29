import javax.swing.JFrame;
import javax.swing.JLabel;

/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Statistics.java
 * 
 * Description:
 * 		Extends JFrame so that User Statistics can be seen from the main menu
-------------------------------------------------------------------------------------------------*/
public class Statistics extends JFrame
{
	private JLabel name_label;
	private JLabel name;
	private JLabel score_label;
	private JLabel score;
	private JLabel maps_completed_label;
	private JLabel maps_completed;
	
	public Statistics( User user )
	{
		super(user.getName());
		
	}
}
