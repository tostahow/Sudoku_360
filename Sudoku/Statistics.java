/*-------------------------------------------------------------------------------------------------
 * Document:
 *      Statistics.java
 * 
 * Description:
 *      All of the statistical information a user might be interested in seeing displayed in
 *      a separate frame.
 *      
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Statistics.java
 * 
 * Description:
 * 		Extends JFrame so that User Statistics can be seen from the main menu
-------------------------------------------------------------------------------------------------*/
public class Statistics extends JFrame implements WindowListener
{
	/*-----------------------------------------------------------------------------------
									Private Class Members
	-----------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	
	private boolean showing;				// flag to determine if frame is showing
	private GridLayout stats_grid;			// grid layout to load information onto
	private JPanel stats_panel;				// panel to hold components for user stats
	private JLabel name_label;				// name label
	private JLabel name;					// user name
	private JLabel score_label;				// score label
	private JLabel score;					// user score
	private JLabel maps_completed_label;	// maps completed label
	private JLabel maps_completed;			// user # of maps completed

	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		Statistics - Constructor
	 * 
	 * Description:
	 * 		Call super constructor with user name and init frame variables
	 --------------------------------------------------------------------------------------*/
	public Statistics( User user )
	{
		super( user.getName() + " Statistics");
		initVariables();
		addInformation( user );
		
	    /*---------------------------------------------------------------
        set attributes for frame
        ---------------------------------------------------------------*/
		this.add( stats_panel );
		this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		this.setSize( 500, 500 );
		this.setResizable( false );
		this.addWindowListener(this);
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		initVariables
	 * 
	 * Description:
	 * 		initialize all variables for the statistics panel
	 --------------------------------------------------------------------------------------*/
	public void initVariables()
	{
		showing = false;
		stats_panel = new JPanel();
		stats_grid = new GridLayout( 3, 2 );
		name_label = new CustomLabel("Username: ");
		maps_completed_label = new CustomLabel("Maps Completed: ");
		score_label = new CustomLabel("Total Score: ");
		name = new CustomLabel("Name");
		score = new CustomLabel("Score");
		maps_completed = new CustomLabel("Maps Completed");
		
	    /*---------------------------------------------------------------
        add all components to stats panel
        ---------------------------------------------------------------*/
		stats_panel.setLayout( stats_grid );
		stats_panel.add( name_label );
		stats_panel.add( name );
		stats_panel.add( maps_completed_label );
		stats_panel.add( maps_completed );
		stats_panel.add( score_label );
		stats_panel.add( score );
		stats_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		addInformation	
	 * 
	 * Description:
	 * 		add user information to the fields for the panel
	 --------------------------------------------------------------------------------------*/
	public void addInformation( User user )
	{
		name.setText( user.getName() );
		maps_completed.setText( "" + user.getMapsCompleted() );
		score.setText( "" + user.getScore() );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		updateUserInformation
	 * 
	 * Description:
	 * 		updates user information
	 --------------------------------------------------------------------------------------*/
	public void updateUserInformation( User user )
	{
		addInformation( user );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setShowing
	 * 
	 * Description:
	 *		sets the frame to be showing, and makes it visible 		
	 --------------------------------------------------------------------------------------*/
	public void setShowing( boolean flag )
	{
		showing = flag;
		this.setVisible( showing );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		isShowing
	 * 
	 * Description:
	 * 		returns whether or not the panel is showing.
	 --------------------------------------------------------------------------------------*/
	public boolean isShowing()
	{
		return showing;
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		setShowing(false);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
