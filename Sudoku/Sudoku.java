/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Sudoku.java
 * 
 * Description:
 * 		Controls all of the components of the main display for the Sudoku Gui application.
 * 		Sudoku subscribes to LogIn and Registration services and generates the main frame that
 * 		the board and actions will be hosted on.
-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Sudoku extends JFrame implements Observer, WindowListener, ActionListener
{
	
	private static final long serialVersionUID = 1L;
	/*-----------------------------------------------------------------------------------
								Private Class Members
	-----------------------------------------------------------------------------------*/
	private UserService log_in;			// LogIn object to aid with log in
    private UserService register;		// register object for registering new users
	private User current_user;			// Player currently signed in
	private SudokuDisplay board;        // Display object for displaying the game.
	
	private JButton log_in_button;		// Button used for logging in
	private JButton register_button;	// Button used for registering
	private JButton play_game_button;	// Button used for playing game
	private JButton exit_button;		// Button to exit game
	private JButton see_stats_button;	// Button to view statistics
	
	private JPanel entry_panel;			// Initial Panel
	private JPanel menu_panel;			// Menu Panel
	
	
	/*-----------------------------------------------------------------------------------
							Private Constant Class Members
	-----------------------------------------------------------------------------------*/
	private final int display_height = 750; 			// height of frame
	private final int display_width = 1000; 			// width of frame
	
	/*--------------------------------------------------------------------
	 *  main()
	 *  
	 *  Description:
	 *  	Creates a new instance of MainDisplay.
 	-------------------------------------------------------------------*/
	public static void main( String[] args )
	{
		new Sudoku();	
	}
	
	
	/*--------------------------------------------------------------------
	 *  Sudoku() - Constructor
	 *  
	 *  Description:
	 *  	Sets title of Jframe and initializes components to be used.
 	-------------------------------------------------------------------*/	
	public Sudoku( )
	{
		/*---------------------------------------------------------------
								Frame Title
		---------------------------------------------------------------*/
		super( "Tex++ Sudoku" );
		
		/*---------------------------------------------------------------
		 					Instance Variables
		---------------------------------------------------------------*/
		log_in = new LogIn( this );
		register = new Register( this );
		
		/*---------------------------------------
		 Read in registered users
		---------------------------------------*/
		this.addWindowListener(this);
		Database.read_database();
		
		/*---------------------------------------
		 * Generates the initial ui for users
		 * to log in and register.
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
		setSize( display_width, display_height );
		setLocation( 500, 280 );
		setResizable( false );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setFont( SudokuCommon.TEXT_FONT );
					
		/*---------------------------------
		 * Call entry_panel for users.
		 --------------------------------*/
		entry_panel();
		
		setVisible( true );	
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
		log_in_button = new JButton( "Log In" );
		register_button = new JButton( "Register" );
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
		 will seperate buttons
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
		log_in_button.setFont( SudokuCommon.TEXT_FONT );
		register_button.setFont( SudokuCommon.TEXT_FONT );
		
		/*---------------------------------------
		 Set both buttons to be without borders
		---------------------------------------*/
		register_button.setBorder( null );
		log_in_button.setBorder( null );
		
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
		add( entry_panel );
	}
	
	 /*---------------------------------------------------------------------------------------
     * Method:
     *      menu_panel
     * 
     * Description:
     *      The UI users are presented to after logging in or registering.. It offers users 
     *      three options: play game, see stats, or exit.
     --------------------------------------------------------------------------------------*/
	private void menu_panel()
	{
	    /*---------------------------------------------------------------
        Instance Variables
        ---------------------------------------------------------------*/
        JPanel button_panel;        // panel that holds buttons
        JLabel title;               // Title
        BorderLayout b_layout;      // layout of panel
        GridLayout g_layout;        // orientation of buttons
        
        /*---------------------------------------------------------------
        Initializing Variables
        ---------------------------------------------------------------*/
        board = new SudokuDisplay();
        b_layout = new BorderLayout();
        button_panel = new JPanel();
        play_game_button = new JButton( "Play Game" );
        see_stats_button = new JButton( "See Stats" );
        exit_button = new JButton( "Exit Game" );
        menu_panel = new JPanel();
        
        title = new JLabel( "Sudoku" );
        title.setHorizontalAlignment( SwingConstants.CENTER );
        
        /*---------------------------------------
        Set vertical and horizontal spacing
        so buttons seem well distanced
        ---------------------------------------*/
        b_layout.setVgap( 100 );
        b_layout.setHgap( 250 );
        
        /*---------------------------------------
        adjust grid of three rows, with a gap that
        will separate buttons
        ---------------------------------------*/
        g_layout = new GridLayout( 3, 1 );
        g_layout.setHgap( 500 );
        g_layout.setVgap( 50 );
                
        /*---------------------------------------
        Set background for components
        ---------------------------------------*/
        title.setBackground( SudokuCommon.BACKGROUND_COLOR );
        menu_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
        
        /*---------------------------------------
        Set fonts for components
        ---------------------------------------*/
        title.setFont( SudokuCommon.TITLE_FONT );
        play_game_button.setFont( SudokuCommon.TEXT_FONT );
        see_stats_button.setFont( SudokuCommon.TEXT_FONT );
        exit_button.setFont( SudokuCommon.TEXT_FONT );
        
        /*---------------------------------------
        Set both buttons to be without borders
        ---------------------------------------*/
        play_game_button.setBorder( null );
        see_stats_button.setBorder( null );
        exit_button.setBorder( null );
        
        /*---------------------------------------
        Button panel will consist of two buttons
        in a grid layout with 1 row
        ---------------------------------------*/
        button_panel.setLayout( g_layout );
        button_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
        button_panel.add( play_game_button );
        button_panel.add( see_stats_button );
        button_panel.add( exit_button );
        
        /*---------------------------------------
        Add title to the entry panel north, and
        place buttons into the center
        ---------------------------------------*/
        menu_panel.setLayout( b_layout );
        menu_panel.add( title, BorderLayout.NORTH );
        menu_panel.add( button_panel, BorderLayout.CENTER );
        
        /*---------------------------------------
        Add empty labels to pad the empty sides
        of the border layout. This is used for
        symmetry
       ---------------------------------------*/
        menu_panel.add( new JLabel( "" ), BorderLayout.SOUTH );
        menu_panel.add( new JLabel( "" ), BorderLayout.EAST );
        menu_panel.add( new JLabel( "" ), BorderLayout.WEST );
        
        /*---------------------------------------
        Set action listeners for buttons
       ---------------------------------------*/
        play_game_button.addActionListener( this );
        
        exit_button.addActionListener( this );
        
        /*---------------------------------------
        Add the menu panel to the main frame
        so that it is displayed
       ---------------------------------------*/
       add( menu_panel );
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
	public void update(Observable subject, Object object_changed) 
	{
		
		/*---------------------------------------
		 LogIn service completed. Set current
		 user into logged in user.
		---------------------------------------*/
		if( object_changed instanceof User && ( subject instanceof LogIn || subject instanceof Register ) )
			{
			System.out.println( "User Loaded" );
			current_user = (User)object_changed;
			
			/*---------------------------------------
			 Avoid any dangling references.
			---------------------------------------*/
			log_in.deleteObservers();
			log_in = null;
			register.deleteObservers();
			register = null;
			
			/*---------------------------------------
			 Display Game menu - coming.
			---------------------------------------*/
			getContentPane().removeAll();
			menu_panel();
			setVisible( true );
			System.out.println( "User: " + current_user.get_name() );
			repaint();
			
			}
		/*---------------------------------------
		 Back button was pressed. Remove_panels
		 and reload entry panel.
		---------------------------------------*/
		if ( object_changed instanceof String   )
			{
			System.out.println( "Going Back to menu!" );
			
			getContentPane().removeAll();
			add( entry_panel );
			repaint();
			setVisible( true );
			
			}
		
		if( object_changed instanceof String && subject instanceof SudokuDisplay )
		{
			System.out.println("Going Back to menu!");
			getContentPane().removeAll();
			add( menu_panel );
			repaint();
			setVisible(true);
		}
		
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
		
		if( e.getSource() == log_in_button )
		{
			getContentPane().remove( entry_panel );
			new_panel = log_in.getPanel();
			add( new_panel );
			repaint();
			setVisible( true );
		}
		
		if( e.getSource() == register_button )
		{
			getContentPane().remove( entry_panel );
			new_panel = register.getPanel();
			add( new_panel );
			repaint();
			setVisible( true );
		}
		
		if( e.getSource() == play_game_button )
		{
            getContentPane().remove( menu_panel );
            new_panel = null;
            add( new_panel );
            setVisible( true );
		}
		
		if( e.getSource() == exit_button )
		{   
			dispatchEvent( new WindowEvent(this, WindowEvent.WINDOW_CLOSING) );
		}
	}
	
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		windowClosing
	 * 
	 * Description:
	 * 		Window is closing, write new info to database.
	 --------------------------------------------------------------------------------------*/
	@Override
	public void windowClosing(WindowEvent arg0) 
	{
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "Updating Database");
		Database.write_database();
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
