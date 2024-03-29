/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Sudoku.java
 * 
 * Description:
 * 		Controls all of the components of the main display for the Sudoku Gui application.
 * 		Sudoku subscribes to LogIn and Registration services and generates the main frame that
 * 		the board and actions will be hosted on.
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class ServiceFrame extends Observable implements Observer, WindowListener, ActionListener
{
	/*-----------------------------------------------------------------------------------
								Private Class Members
	-----------------------------------------------------------------------------------*/
	private UserService log_in;				// LogIn object to aid with log in
    private UserService register;			// register object for registering new users
	private User current_user;				// Player currently signed in
	
	private CustomButton log_in_button;		// Button used for logging in
	private CustomButton register_button;	// Button used for registering
	
	private JPanel entry_panel;				// Initial Panel
	private JFrame host_frame;				//Frame for services to occur in
	
	/*-----------------------------------------------------------------------------------
							Private Constant Class Members
	-----------------------------------------------------------------------------------*/
	private final int display_width = 1000; 			// width of frame
	
	/*--------------------------------------------------------------------
	 *  ServiceFrame() - Constructor
	 *  
	 *  Description:
	 *  	Sets title of Jframe and initializes components to be used.
 	-------------------------------------------------------------------*/	
	public ServiceFrame( Observer listener )
	{
		addObserver( listener );
		/*---------------------------------------------------------------
		Set Frame Title
		---------------------------------------------------------------*/
		host_frame = new JFrame();
		host_frame.setTitle( "Tex++ Log-in Service" );
		
		/*---------------------------------------------------------------
		Instance Variables
		---------------------------------------------------------------*/
		log_in = new LogIn( this );
		register = new Register( this );
		
		/*---------------------------------------
		Read in registered users
		---------------------------------------*/
		host_frame.addWindowListener(this);
		Database.read_database();
		
		/*---------------------------------------
		Generates the initial ui for users
		to log in and register.
		---------------------------------------*/
		init_frame();
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		init_frame()
	 * 
	 * Description:
	 * 		Initialize the frame settings.
	 --------------------------------------------------------------------------------------*/
	private void init_frame()
	{
		/*---------------------------------------------------------------
		Set Frame Attributes
		---------------------------------------------------------------*/
		host_frame.setSize( display_width , 750 );
		host_frame.setLocation( 350, 100 );
		host_frame.setResizable( false );
		host_frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		host_frame.setFont( SudokuCommon.TEXT_FONT );
					
		/*---------------------------------
		Call entry_panel for users.
		--------------------------------*/
		entry_panel();
		
		host_frame.setVisible( true );	
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		entry_panel
	 * 
	 * Description:
	 * 		The initial UI users are presented. It offers users two options: If they are new
	 * 		users, they can register. If they are returning, they can log in. This panel
	 * 		also contains a title as well.
	 --------------------------------------------------------------------------------------*/
	private void entry_panel()
	{ 
		
		/*---------------------------------------------------------------
		 					 Instance Variables
		---------------------------------------------------------------*/
		JPanel button_panel;		// panel that holds buttons
		JLabel title;				// Title
		BorderLayout b_layout;	    // layout of panel
		GridLayout g_layout;		// orientation of buttons
		
		/*---------------------------------------------------------------
	 	Initializing Variables
		---------------------------------------------------------------*/
		b_layout = new BorderLayout();
		button_panel = new JPanel();
		log_in_button = new CustomButton( "Log In", false );
		register_button = new CustomButton( "Register", false );
		entry_panel = new JPanel();
		
		title = new JLabel( "Sudoku" );
		title.setHorizontalAlignment( SwingConstants.CENTER );
		
		/*---------------------------------------
		 Set vertical and horizontal spacing
		 so buttons seem well distanced
		---------------------------------------*/
		b_layout.setVgap( 200 );
		b_layout.setHgap( 100 );
		
		/*---------------------------------------
		 adjust grid of one row, with a gap that
		 will separate buttons
		---------------------------------------*/
		g_layout = new GridLayout( 1, 0 );
		g_layout.setHgap( 300 );
		
	
		/*---------------------------------------
		 Set background for components
		---------------------------------------*/
		title.setBackground( SudokuCommon.BACKGROUND_COLOR );
		entry_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
		
		/*---------------------------------------
		 Set fonts for components
		---------------------------------------*/
		title.setFont( SudokuCommon.TITLE_FONT );
		
		/*---------------------------------------
		 Button panel will consist of two buttons
		 in a grid layout with 1 row
		---------------------------------------*/
		button_panel.setLayout( g_layout );
		button_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
		button_panel.add( register_button );
		button_panel.add( log_in_button );
		
		/*---------------------------------------
		 Add title to the entry panel north, and
		 place buttons into the center
		---------------------------------------*/
		entry_panel.setLayout( b_layout );
		entry_panel.add( title, BorderLayout.NORTH );
		entry_panel.add( button_panel, BorderLayout.CENTER );
		
		/*---------------------------------------
		 Add empty labels to pad the empty sides
		 of the border layout. This is used for
		 symmetry
		---------------------------------------*/
		entry_panel.add( new JLabel( "" ), BorderLayout.SOUTH );
		entry_panel.add( new JLabel( "" ), BorderLayout.EAST );
		entry_panel.add( new JLabel( "" ), BorderLayout.WEST );
		
		/*---------------------------------------
		 Set action listeners for buttons
		---------------------------------------*/
		log_in_button.addActionListener(this);
		register_button.addActionListener(this);
		
		/*---------------------------------------
		 Add the entry panel to the main frame
		 so that it is displayed
		---------------------------------------*/
		host_frame.add( entry_panel );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		update()
	 * 
	 * Description:
	 * 		Listens for any notification from the objects being observed and performs tasks 
	 * 		based on what was notified/updated.
	--------------------------------------------------------------------------------------*/
	@Override
	public void update( Observable subject, Object object_changed ) 
	{
		
		/*---------------------------------------
		 LogIn service completed. Set current
		 user into logged in user.
		---------------------------------------*/
		if( object_changed instanceof User )
		{
			current_user = (User)object_changed;
			
			/*---------------------------------------
			 Avoid any dangling references.
			---------------------------------------*/
			log_in.deleteObservers();
			log_in = null;
			register.deleteObservers();
			register = null;
			
			updateUser();			
		}
		
		/*---------------------------------------
		 Back button was pressed. Remove_panels
		 and reload entry panel.
		---------------------------------------*/
		if ( object_changed instanceof String   )
		{	
			host_frame.getContentPane().removeAll();
			host_frame.add( entry_panel );
			host_frame.repaint();
			host_frame.setVisible( true );
			
		}
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		updateUser()
	 * 
	 * Description:
	 * 		send user object back to Sudoku
	--------------------------------------------------------------------------------------*/
	public void updateUser()
	{	
	    /*---------------------------------------------------------------
        Set status of object to changed
        ---------------------------------------------------------------*/
		setChanged();
		
	    /*---------------------------------------------------------------
        Notify Listening Classes that there is a new user
        ---------------------------------------------------------------*/
		notifyObservers( this.current_user );
	}
	
	/*----------------------------------------------------------------------------------------
	 									All Listeners
	 ----------------------------------------------------------------------------------------*
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		actionPerformed
	 * 
	 * Description:
	 * 		wait for actions to occur, and respond when they do.
	 --------------------------------------------------------------------------------------*/
	public void actionPerformed( ActionEvent e )
	{
		JPanel new_panel;
		
	    /*---------------------------------------------------------------
		Log in button was pressed. Load Log In Panel
        ---------------------------------------------------------------*/
		if( e.getSource() == log_in_button )
		{
			host_frame.getContentPane().remove( entry_panel );
			new_panel = log_in.getPanel();
			host_frame.add( new_panel );
			host_frame.repaint();
			host_frame.setVisible( true );
		}
		
	    /*---------------------------------------------------------------
		Register button was pressed. Load Register Panel
        ---------------------------------------------------------------*/
		if( e.getSource() == register_button )
		{
			host_frame.getContentPane().remove( entry_panel );
			new_panel = register.getPanel();
			host_frame.add( new_panel );
			host_frame.repaint();
			host_frame.setVisible( true );
		}
	}
	
	//Dispose of frame
	public void destroy()
	{
		host_frame.dispose();
	}
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		windowClosing
	 * 
	 * Description:
	 * 		Window is closing, write new info to database.
	 --------------------------------------------------------------------------------------*/
	@Override
	public void windowClosing( WindowEvent arg0 ) 
	{
		// TODO Auto-generated method stub
		System.exit(1);
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowClosed(WindowEvent arg0) {
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
