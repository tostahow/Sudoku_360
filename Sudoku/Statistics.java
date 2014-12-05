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
	private JLabel size3_maps_completed;    // user # of 9 by 9 maps completed
	private JLabel size4_maps_completed;    // user # of 16 by 16 maps completed
	private JLabel time_label;				// label for time played
	private JLabel time;					// total time played

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
	    /*---------------------------------------------------------------
		Initialize all variables/components
        ---------------------------------------------------------------*/
		showing = false;
		stats_panel = new JPanel();
		stats_grid = new GridLayout( 5, 2 );
		name_label = new CustomLabel("Username: ");
		maps_completed_label = new CustomLabel("Maps Completed: ");
		score_label = new CustomLabel("Total Score: ");
		name = new CustomLabel("Name");
		score = new CustomLabel("Score");
		size3_maps_completed = new CustomLabel("9 by 9 Grid: ");
		size4_maps_completed = new CustomLabel("16 by 16 Grid: ");
		maps_completed = new CustomLabel("Maps Completed");
		time_label = new CustomLabel( "Time Played:" );
		time = new CustomLabel("Time");
		
	    /*---------------------------------------------------------------
        add all components to stats panel
        ---------------------------------------------------------------*/
		stats_panel.setLayout( stats_grid );
		stats_panel.add( name_label );
		stats_panel.add( name );
		stats_panel.add( maps_completed_label );
		stats_panel.add( maps_completed );
		stats_panel.add( size3_maps_completed );
		stats_panel.add( size4_maps_completed );
		stats_panel.add( score_label );
		stats_panel.add( score );
		stats_panel.add( time_label );
		stats_panel.add( time );
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
		size3_maps_completed.setText( "9x9 Maps: " + user.getMapsCompleted(BoardSize.NINE) );
		size4_maps_completed.setText( "16x16 Maps: " + user.getMapsCompleted(BoardSize.SIXTEEN) );
		time.setText( "" + user.getTimePlayed() + " seconds");		
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
