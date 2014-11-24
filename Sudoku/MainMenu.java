/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		MainMenu.java
 * 
 * Description:
 * 		MainMenu includes the functionality for the Main Menu.
 * 		The Main Menu includes options such as difficulty options, board size options.\
 * 		The Main Menu includes buttons for playing the game, seeing user stats, and quiting game.
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------------------
										    Imports
-------------------------------------------------------------------------------------------------*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;



public class MainMenu implements Observer, ActionListener, WindowListener 
{
	/*-----------------------------------------------------------------------------------
								Private Class Members
	-----------------------------------------------------------------------------------*/
	private User user;					// User that has logged in.	
	private boolean stats_open; 		// Set when stat panel is open
	private SudokuDisplay game;			// Game object
	private CustomButton play_game_button; 	// Plays the SudokuDisplay Game
	private CustomButton exit_button; 		// Exits Software
	private CustomButton see_stats_button;	// opens Stats frame
	
	private CustomButton load_board_button; // Opens a file chooser for loading a game board.
	private CustomButton load_save_button;
	
	private JPanel menu_panel;			// Panel which holds Menu components
	private JRadioButton size_nine;		// Radio Button for 9x9 Map
	private JRadioButton size_sixteen;	// Radio Button for 16x16 Map
	private JRadioButton easy, medium; 	// Radio Buttons for Easy/Medium
	private JRadioButton hard, evil;	// Radio Buttons for Hard/Evil
	private ButtonGroup diff_group;		// Groups the Difficulty Buttons
	private ButtonGroup size_group;		// Groups the Size Buttons
	
	private JPanel file_panel; // Panel which holds the file options.
	private JPanel current_panel;
	
	private JFrame main_frame;			// Frame generated to hold Main menu
	private JFrame stats_frame;			// Frame for User Stats
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		MainMenu - Constructor
	 * 
	 * Description:
	 * 		Initialize all Menu Components
	 --------------------------------------------------------------------------------------*/
	public MainMenu( User new_user )
	{
		this.user = new_user;
		main_frame = new JFrame();
		current_panel = new JPanel();
		initMenu();
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		initMenu()
	 * 
	 * Description:
	 * 		initialize all main menu gui settings
	 --------------------------------------------------------------------------------------*/
	private void initMenu()
	{
	    /*---------------------------------------------------------------
        Instance Variables
        ---------------------------------------------------------------*/
        JPanel button_panel;        // panel that holds buttons
        JPanel size_panel;			// panel for size options
        JPanel diff_panel;			// panel for difficulties
        
        JPanel option_panel;		// panel to hold all options
        JLabel title;
        BorderLayout b_layout;      // layout of panel
        GridLayout button_layout;   // orientation of buttons
        GridLayout option_layout;	// list of all options
        
	    /*---------------------------------------------------------------
        Setting up main_frame attributes
        ---------------------------------------------------------------*/
        main_frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        main_frame.setTitle("Sudoku Main Menu");
        main_frame.setSize( 1500, 900 );
        main_frame.setResizable(false);
        main_frame.setLocation( 300 , 125 );
        main_frame.addWindowListener(this);
        
        /*---------------------------------------------------------------
        Initializing Variables
        ---------------------------------------------------------------*/
        b_layout = new BorderLayout();
        button_panel = new JPanel();
        size_panel = new JPanel();
        diff_panel = new JPanel();
        file_panel = new JPanel();
        play_game_button = new CustomButton( "Play!", false );
        see_stats_button = new CustomButton( "See Stats", false );
        exit_button = new CustomButton( "Exit!", false );
        menu_panel = new JPanel();
        size_group = new ButtonGroup();
        diff_group = new ButtonGroup();
        
        title = new JLabel( "Sudoku" );
        title.setHorizontalAlignment( SwingConstants.CENTER );
        
        size_nine = new CustomRadioButton( "9x9" );
        size_sixteen = new CustomRadioButton( "16x16" );
                
        size_group.add( size_nine );
        size_group.add( size_sixteen );
        
        Border size_border = BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.black), "Size Options" );
        size_panel.setBorder( size_border );
        size_panel.setLayout( new FlowLayout() );
        size_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
        size_panel.add( size_nine );
        size_panel.add( size_sixteen );
        
        easy = new CustomRadioButton( "Easy" );
        medium = new CustomRadioButton( "Medium" );
        hard = new CustomRadioButton( "Hard" );
        evil = new CustomRadioButton( "Evil" );
        
        diff_group.add( easy );
        diff_group.add( medium );
        diff_group.add( hard );
        diff_group.add( evil );
        
        Border diff_border = BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.black), "Difficulty Options" );
        diff_panel.setBorder( diff_border );
        diff_panel.setLayout( new FlowLayout() );
        diff_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
        diff_panel.add( easy );
        diff_panel.add( medium );
        diff_panel.add( hard );
        diff_panel.add( evil );
        
        load_board_button = new CustomButton( "Load Board", false );
        load_save_button = new CustomButton( "Resume Saved Game", false );
        
        Border file_border = BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.black), "File Options" );
        file_panel.setBorder( file_border );
        file_panel.setLayout( new FlowLayout() );
        file_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
        file_panel.add( load_board_button );
        file_panel.add( load_save_button );
        
        
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
        button_layout = new GridLayout( 1, 3 );
        button_layout.setHgap( 5 );
        button_layout.setVgap( 5 );
        
        option_layout = new GridLayout( 4, 1);
        option_panel = new JPanel( option_layout );
        option_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
        /*---------------------------------------
        Set background for components
        ---------------------------------------*/
        title.setBackground( SudokuCommon.BACKGROUND_COLOR );
        menu_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
        
        /*---------------------------------------
        Set fonts for components
        ---------------------------------------*/
        title.setFont( SudokuCommon.TITLE_FONT );
        
        /*---------------------------------------
        Button panel will consist of two buttons
        in a grid layout with 1 row
        ---------------------------------------*/
        button_panel.setLayout( button_layout );
        button_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
        button_panel.add( play_game_button );
        button_panel.add( see_stats_button );
        button_panel.add( exit_button );
        
        option_panel.add( size_panel );
        option_panel.add( diff_panel );
        option_panel.add( file_panel );
        option_panel.add( button_panel );
        /*---------------------------------------
        Add title to the entry panel north, and
        place buttons into the center
        ---------------------------------------*/
        menu_panel.setLayout( b_layout );
        menu_panel.add( title, BorderLayout.NORTH );
        menu_panel.add( option_panel, BorderLayout.CENTER );
        
        /*---------------------------------------
        Add empty labels to pad the empty sides
        of the border layout. This is used for
        symmetry
       ---------------------------------------*/
        menu_panel.add( new JLabel( "" ), BorderLayout.SOUTH );
        menu_panel.add( new JLabel(""), BorderLayout.EAST );
        menu_panel.add( new JLabel(""), BorderLayout.EAST);
        menu_panel.add( new JLabel(""), BorderLayout.WEST );
        
        /*---------------------------------------
        Set action listeners for buttons
       ---------------------------------------*/
        play_game_button.addActionListener( this );
        see_stats_button.addActionListener( this );
        exit_button.addActionListener( this );
        load_board_button.addActionListener( this );
        
        /*---------------------------------------
        Add the menu panel to the main frame
        so that it is displayed
       ---------------------------------------*/
       main_frame.add( menu_panel );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		show()
	 * 
	 * Description:
	 * 		show the frame for the MainMenu
	 --------------------------------------------------------------------------------------*/
	public void show()
	{
		main_frame.setVisible(true);
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getDesiredDifficulty()
	 * 
	 * Description:
	 * 		Gather the Difficulty from the radio buttons
	 --------------------------------------------------------------------------------------*/
	public Difficulty getDesiredDifficulty()
	{
		if( medium.isSelected() )
			return Difficulty.MEDIUM;
		else if( hard.isSelected() )
			return Difficulty.HARD;
		else if( evil.isSelected() )
			return Difficulty.EVIL;
		else
			return Difficulty.EASY;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getDesiredBoardSize()
	 * 
	 * Description:
	 * 		Gather the BoardSize options from the radio buttons
	 --------------------------------------------------------------------------------------*/
	public BoardSize getDesiredBoardSize()
	{
		if( size_sixteen.isSelected() )
			return BoardSize.SIXTEEN;
		else
			return BoardSize.NINE;
	}
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		showStats()
	 * 
	 * Description:
	 * 		create a frame showing user statistics
	 --------------------------------------------------------------------------------------*/
	public void showStats()
	{
		stats_open = true;	// ensures that user cant make multiple frames.
		
	    /*---------------------------------------------------------------
        Stats Frame has already been created.
        ---------------------------------------------------------------*/
		if( stats_frame != null )
		{
			System.out.println("Setting Stats Frame Visible!");
			stats_frame.setVisible(true);
		}
	    /*---------------------------------------------------------------
        Stats Frame has yet to be created. Create the frame.
        ---------------------------------------------------------------*/
		else
		{
		    /*---------------------------------------------------------------
	        Local Variables
	        ---------------------------------------------------------------*/
			stats_frame = new JFrame( user.getName() + " Stats" );
			JPanel stats_panel = new JPanel();
			GridLayout stats_grid = new GridLayout(3,2);
			JLabel score_label = new JLabel( "Score: " );
			JLabel score = new JLabel( "" + user.getScore() );
			JLabel maps_comp_label = new JLabel( "Maps Completed: " );
			JLabel maps = new JLabel( "" + user.getMapsCompleted() );
			JLabel name_label = new JLabel( "Name: " );
			JLabel name = new JLabel( "" + user.getName() );
			
		    /*---------------------------------------------------------------
	        Set up panel for stats frame
	        ---------------------------------------------------------------*/
			stats_panel.setLayout( stats_grid );
			stats_panel.add( name_label );
			stats_panel.add( name );
			stats_panel.add( score_label );
			stats_panel.add( score );
			stats_panel.add( maps_comp_label );
			stats_panel.add( maps );
			
		    /*---------------------------------------------------------------
	        Setting up stats_frame attributes
	        ---------------------------------------------------------------*/
			stats_frame.add(stats_panel);
			stats_frame.setSize( 300 , 300 );
			stats_frame.setLocation(300, 150);
			stats_frame.setResizable(false);
			stats_frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
			
		    /*---------------------------------------------------------------
	        show the stat_frame
	        ---------------------------------------------------------------*/
			stats_frame.addWindowListener(this);
			stats_frame.setVisible(true);
		}
	}
	
	/*---------------------------------------------------------------------------------------
	 *  						 All Listener Functions
	 --------------------------------------------------------------------------------------*/
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		/*-------------------------------------------
		 *  Play button was pressed. Create board,
		 *  for now.
		-------------------------------------------*/
		if( e.getSource() == play_game_button )
		{
			game = new SudokuDisplay(this, getDesiredBoardSize(), getDesiredDifficulty() );
			current_panel = game.getGamePanel();
            main_frame.getContentPane().remove( menu_panel );
            main_frame.add( current_panel );
            main_frame.setVisible( true );
		}
		
		/*-------------------------------------------
		 *  See stats button pressed. Create frame
		 *  showing user stats
		-------------------------------------------*/
		if( e.getSource() == see_stats_button )
		{
			if( stats_open == false ) 
				showStats();
			else
				System.out.println("Stats already open");
		}
		
		/*-------------------------------------------
		 *  Exit button was pressed. Close window
		-------------------------------------------*/
		if( e.getSource() == exit_button )
		{   
			main_frame.dispatchEvent( new WindowEvent( main_frame, WindowEvent.WINDOW_CLOSING ) );
		}
		
		if( e.getSource() == load_board_button)
		{
		    File file = null;
		    
		    JFileChooser fileOpen = new JFileChooser();
		    FileFilter filter = new FileNameExtensionFilter("txt files", "txt");
		    fileOpen.addChoosableFileFilter(filter);
		    int ret = fileOpen.showDialog(file_panel, "Open Sudoku board");

            if (ret == JFileChooser.APPROVE_OPTION) 
            {
                file = fileOpen.getSelectedFile();
            }
            
            game = new SudokuDisplay(this, getDesiredDifficulty(), file);
            current_panel = game.getGamePanel();
            main_frame.getContentPane().remove( menu_panel );
            main_frame.add( current_panel );
            main_frame.setVisible( true );
		    
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
	public void windowClosing(WindowEvent e ) 
	{
		if( e.getSource() == main_frame )
		{
			JOptionPane.showMessageDialog(null, "Updating Database");
			Database.write_database();
			System.exit(0);
		}
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() == stats_frame )
		{
			stats_open = false;
		}
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

	@Override
	public void update(Observable subject, Object object_changed) 
	{
		if( ( subject instanceof SudokuDisplay ) && ( object_changed instanceof String ) )
		{
			if( ((String)object_changed).equals("Quit") )
			{
				main_frame.dispatchEvent( new WindowEvent( main_frame, WindowEvent.WINDOW_CLOSING ) );
			}
			
			if( ((String)object_changed).equals("Win") )
			{
				main_frame.remove(current_panel);
				main_frame.add(menu_panel);
				main_frame.repaint();
				main_frame.setVisible(true);
				user.incrementMapsCompleted();
				game = null;
			}
		}
		
		if( ( subject instanceof SudokuDisplay ) && ( object_changed instanceof Integer ) )
		{
			user.setScore( (int)object_changed );
			System.out.println( "User Score Updated to " + user.getScore() );
		}
	}

}
