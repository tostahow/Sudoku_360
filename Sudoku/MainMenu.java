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
 * 		Travis Ostahowski, Xavier Tariq, Garett Winkler, Scott Parkman
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
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainMenu implements Observer, ActionListener, WindowListener 
{
	/*-----------------------------------------------------------------------------------
								Private Class Members
	-----------------------------------------------------------------------------------*/
	private User user;							// User that has logged in.	
	private SudokuDisplay game;					// Game object
	private CustomButton play_game_button; 		// Plays the SudokuDisplay Game
	private CustomButton exit_button; 			// Exits Software
	private CustomButton info_button;			// Loads information frame
	private CustomButton see_stats_button;		// opens Stats frame
	
	private CustomButton load_board_button; 	// Opens a file chooser for loading a game board.
	private CustomButton load_save_button;		// Opens a file chooser for loading saved game board.
	
	private JPanel menu_panel;					// Panel which holds Menu components
	private CustomRadioButton size_nine;		// Radio Button for 9x9 Map
	private CustomRadioButton size_sixteen;		// Radio Button for 16x16 Map
	private CustomRadioButton easy, medium; 	// Radio Buttons for Easy/Medium
	private CustomRadioButton hard, evil;		// Radio Buttons for Hard/Evil
	
	private CustomRadioButton playWithAI;       // Radio Button for playing with the AI
	private CustomRadioButton dontPlayWithAI;   // Radio Button for not playing with the AI	
	
	private ButtonGroup diff_group;				// Groups the Difficulty Buttons
	private ButtonGroup size_group;				// Groups the Size Buttons
	private ButtonGroup ai_group;				// Groups the ai option Buttons
	
	private JPanel file_panel; 					// Panel which holds the file options.
	private JPanel current_panel;
	
	private JFrame main_frame;					// Frame generated to hold Main menu
	private JFrame info_frame;					// Frame for showing game info
	private Statistics stats;					// Frame for User Stats
	private boolean info_open;					// determine whether or not info frame is open
	
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
		stats = new Statistics( user );
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
        JPanel ai_panel;			// panel for ai options
        
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
        main_frame.setSize( 1175, 770 );
        main_frame.setResizable(true);
        main_frame.setLocation( 300 , 125 );
        main_frame.addWindowListener(this);
        
        /*---------------------------------------------------------------
        Initializing Variables
        ---------------------------------------------------------------*/
        b_layout = new BorderLayout();
        button_panel = new JPanel();
        size_panel = new JPanel();
        diff_panel = new JPanel();
        ai_panel = new JPanel();
        file_panel = new JPanel();        
        play_game_button = new CustomButton( "Play!", false );
        see_stats_button = new CustomButton( "Stats", false );
        exit_button = new CustomButton( "Exit!", false );
        info_button = new CustomButton( "Info", false );
        menu_panel = new JPanel();
        size_group = new ButtonGroup();
        diff_group = new ButtonGroup();
        ai_group = new ButtonGroup();
        
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
        
        dontPlayWithAI = new CustomRadioButton( "Don't play with AI" );
        playWithAI = new CustomRadioButton( " Play with AI" );
        
        ai_group.add(dontPlayWithAI);
        ai_group.add(playWithAI);
        
        Border ai_border = BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.black), "AI Options" );
        ai_panel.setBorder( ai_border );
        ai_panel.setLayout( new FlowLayout() );
        ai_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
        ai_panel.add( dontPlayWithAI );
        ai_panel.add( playWithAI );
        
        load_board_button = new CustomButton( "Load Board", false );
        load_save_button = new CustomButton( "Resume Board", false );
        
        GridLayout file_layout = new GridLayout( 1, 2 );
        file_layout.setHgap( 100 );
        file_layout.setVgap( 250 );        
        
        Border file_border = BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.black), "File Options" );
        file_panel.setBorder( file_border );
        file_panel.setLayout( file_layout );
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
        
        option_layout = new GridLayout( 5, 1 );
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
        button_panel.add( info_button );
        button_panel.add( exit_button );
        
        option_panel.add( size_panel );
        option_panel.add( diff_panel );
        option_panel.add( ai_panel );
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
        menu_panel.add( new JLabel( "" ), BorderLayout.EAST );
        menu_panel.add( new JLabel( "" ), BorderLayout.EAST);
        menu_panel.add( new JLabel( "" ), BorderLayout.WEST );
        
        /*---------------------------------------
        Set action listeners for buttons
       ---------------------------------------*/
        play_game_button.addActionListener( this );
        see_stats_button.addActionListener( this );
        exit_button.addActionListener( this );
        load_board_button.addActionListener( this );
        load_save_button.addActionListener( this );
        info_button.addActionListener( this );
        
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
	 * 		showInfo()
	 * 
	 * Description:
	 * 		show the information frame for the MainMenu
	 --------------------------------------------------------------------------------------*/
	public void showInfo() 
	{
		info_open = true;
		  
		/*---------------------------------------------------------------
		Info Frame has already been created.
		---------------------------------------------------------------*/
		    
		if( info_frame != null )
		{
			System.out.println("Setting Info Frame Visible");
			info_frame.setVisible(true);
		}
	   
		/*---------------------------------------------------------------
		Info Frame has yet to be created. Create the frame.
		---------------------------------------------------------------*/
		else
		{
		  info_frame = new JFrame("Info");
		     
		  /*---------------------------------------------------------------
		  Set up panel for Info frame
		  ---------------------------------------------------------------*/
		
		 
		 JPanel info_panel = new JPanel( new FlowLayout() );
		 JTextArea information = new JTextArea( SudokuCommon.INFO );
		 information.setBackground( SudokuCommon.BACKGROUND_COLOR );
		 information.setFont( SudokuCommon.PENCIL_FONT );
		 information.setEditable( false );
		 info_panel.add( information );
		 info_panel.setBackground( SudokuCommon.COMPONENT_COLOR );
		 
		 JScrollPane info_scroll = new JScrollPane( info_panel );
		 info_scroll.setSize( 400, 400 );
		 info_scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		    
		  
		 /*---------------------------------------------------------------
		 Setting up Info frame
		 ---------------------------------------------------------------*/
		     
	     info_frame.add( info_scroll );
	     info_frame.setLocation(300, 150);
	     info_frame.setSize( 500, 500 );
	     info_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);         
		     
		     
		 /*---------------------------------------------------------------
		 Show the info_frame
		 ---------------------------------------------------------------*/  
		   
	     info_frame.addWindowListener(this);
	     info_frame.setVisible(true);
	    
		}
		  
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
	 * 		getDesiredBoardSize()
	 * 
	 * Description:
	 * 		Gather the BoardSize options from the radio buttons
	 --------------------------------------------------------------------------------------*/
	public boolean getDesiredAI()
	{
		if ( playWithAI.isSelected() )
			return true;
		else
			return false;
	}
	
	/*---------------------------------------------------------------------------------------
	 *  						 All Listener Functions
	 --------------------------------------------------------------------------------------*/
	@Override
	public void actionPerformed( ActionEvent e ) 
	{		
	    /*---------------------------------------------------------------
		Create a new game and display it within frame
        ---------------------------------------------------------------*/
		if( e.getSource() == play_game_button )
		{
			game = new SudokuDisplay(this, getDesiredBoardSize(), getDesiredDifficulty(), getDesiredAI() );
			current_panel = game.getGamePanel();
            main_frame.getContentPane().remove( menu_panel );
            main_frame.add( current_panel );
            main_frame.setVisible( true );
		}
		
	    /*---------------------------------------------------------------
		Open Statistics frame if it isn't already open
        ---------------------------------------------------------------*/
		if( e.getSource() == see_stats_button )
		{
			if( stats.isShowing() == false )
				stats.setShowing( true );
			else
			{
				JOptionPane.showMessageDialog(
		            null,
		            "Stats panel is already open!",
		            "Stats Open",
		            JOptionPane.OK_OPTION
		            );
			}
		}
		
		if ( e.getSource() == info_button )
		{
			if ( info_open == false )
				showInfo();
			else
			{
				JOptionPane.showMessageDialog(
		            null,
		            "Information panel is already open!",
		            "Info Open",
		            JOptionPane.OK_OPTION
		            );
			}
		}
		
	    /*---------------------------------------------------------------
		Exit software.
        ---------------------------------------------------------------*/
		if( e.getSource() == exit_button )
		{   
			main_frame.dispatchEvent( new WindowEvent( main_frame, WindowEvent.WINDOW_CLOSING ) );
		}
		
	    /*---------------------------------------------------------------
		Load Button was pressed. Open File Chooser so user can choose
		puzzles to load
        ---------------------------------------------------------------*/
		if( e.getSource() == load_board_button)
		{
			boolean closed = false;
		    File file = null;
		    
		    JFileChooser fileOpen = new JFileChooser();
		    FileFilter filter = new FileNameExtensionFilter( "txt files", "txt" );
		    fileOpen.addChoosableFileFilter( filter );
		    int ret = fileOpen.showDialog( file_panel, "Open Sudoku board" );

            if (ret == JFileChooser.APPROVE_OPTION) 
            {
                file = fileOpen.getSelectedFile();
            }
            else
            {
            	closed = true;
            }
            
            if( !closed )
            {
	            game = new SudokuDisplay( this, getDesiredDifficulty(), file, getDesiredAI() );
	            current_panel = game.getGamePanel();
	            main_frame.getContentPane().remove( menu_panel );
	            main_frame.add( current_panel );
	            main_frame.setVisible( true );
            }
		    
		}
		
	    /*---------------------------------------------------------------
		Resume saved game button pressed. Open a file chooser dialog to
		select a saved game
        ---------------------------------------------------------------*/
		if( e.getSource() == load_save_button)
		{
		    File file = null;
		    boolean closed =  false;
		    
		    JFileChooser fileOpen = new JFileChooser();
		    FileFilter filter = new FileNameExtensionFilter("save files", "save");
		    fileOpen.addChoosableFileFilter(filter);
		    int ret = fileOpen.showDialog(file_panel, "Open Sudoku saved game");

            if (ret == JFileChooser.APPROVE_OPTION) 
            {
                file = fileOpen.getSelectedFile();
            }
            else
            {
            	closed = true;
            }
            
            if( !closed )
            {
	            game = new SudokuDisplay(this, file);
	            current_panel = game.getGamePanel();
	            main_frame.getContentPane().remove( menu_panel );
	            main_frame.add( current_panel );
	            main_frame.setVisible( true );
            }
			    
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
	public void windowClosing( WindowEvent e ) 
	{
		if( e.getSource() == main_frame )
		{
			JOptionPane.showMessageDialog(null, "Updating Database");
			Database.write_database();
			System.exit(0);
		}
		
		if( e.getSource() == info_frame )
			info_open = false;
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
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
	public void update( Observable subject, Object object_changed ) 
	{
		/*-----------------------------------------------------------------------------------
		A message was sent from Sudoku display. Either quit or win was performed.
		-----------------------------------------------------------------------------------*/
		if ( subject instanceof SudokuDisplay )
		{
			if ( object_changed instanceof String )
			{
				/*-----------------------------------------------------------------------------------
				Quit the game
				-----------------------------------------------------------------------------------*/
				if ( ( ( String )object_changed ).equals( "Quit" ) )
				{
					main_frame.dispatchEvent( new WindowEvent( main_frame, WindowEvent.WINDOW_CLOSING ) );
				}
			}

			if ( object_changed instanceof WinInfo )
			{
			    /*---------------------------------------------------------------
				Board has been solved. Reload main menu panel.
		        ---------------------------------------------------------------*/
				if ( ( (WinInfo) object_changed ).didWin == true )
				{
					main_frame.remove( current_panel );
					main_frame.add( menu_panel );
					main_frame.repaint();
					main_frame.setVisible( true );
					user.incrementMapsCompleted( ((WinInfo) object_changed).board_size );
					stats.updateUserInformation( user );
					game = null;
				}
			}
			
		    /*---------------------------------------------------------------
	        Update User score
	        ---------------------------------------------------------------*/
	        if ( object_changed instanceof Integer)
	        {
	            user.setScore( (int) object_changed );
	            stats.updateUserInformation( user );
	        }
	        
	        /*---------------------------------------------------------------
	        Update User time
	        ---------------------------------------------------------------*/
	        if ( object_changed instanceof Long )
	        {
	            user.updateTimePlayed( (long) object_changed );
	            stats.updateUserInformation( user );
	        }
		}
	}

}
