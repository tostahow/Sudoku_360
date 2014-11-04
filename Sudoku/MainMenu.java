import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;



public class MainMenu implements ActionListener, WindowListener 
{
	private User user;
	private boolean stats_open;
	private Board board;
	private JButton play_game_button, exit_button, see_stats_button;
	private JPanel menu_panel;
	private JRadioButton size_nine;
	private JRadioButton size_sixteen;
	private JRadioButton easy, medium, hard, evil;
	private ButtonGroup diff_group, size_group;
	
	private JFrame main_frame;
	private JFrame stats_frame;
	
	
	public MainMenu( User new_user )
	{
		this.user = new_user;
		main_frame = new JFrame();
		System.out.print("User:" + user.get_name() + " logged in");
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
        
        main_frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        main_frame.setTitle("Sudoku Main Menu");
        main_frame.setSize( 1500, 900 );
        main_frame.setResizable(false);
        main_frame.setLocation( 300 , 125 );
        main_frame.addWindowListener(this);
        
        /*---------------------------------------------------------------
        Initializing Variables
        ---------------------------------------------------------------*/
        board = new Board(BoardSize.SIXTEEN, Difficulty.EASY);
        b_layout = new BorderLayout();
        button_panel = new JPanel();
        size_panel = new JPanel();
        diff_panel = new JPanel();
        play_game_button = new JButton( "Play!" );
        see_stats_button = new JButton( "See Stats" );
        exit_button = new JButton( "Exit!" );
        menu_panel = new JPanel();
        size_group = new ButtonGroup();
        diff_group = new ButtonGroup();
        
        title = new JLabel( "Sudoku" );
        title.setHorizontalAlignment( SwingConstants.CENTER );
        
        size_nine = new JRadioButton("9x9");
        size_sixteen = new JRadioButton("16x16");
        
        size_nine.setBackground(SudokuCommon.BACKGROUND_COLOR);
        size_nine.setFont( SudokuCommon.TEXT_FONT );
        size_sixteen.setBackground(SudokuCommon.BACKGROUND_COLOR);
        size_sixteen.setFont( SudokuCommon.TEXT_FONT );
        
        size_group.add(size_nine);
        size_group.add(size_sixteen);
        
        size_panel.setLayout(new FlowLayout() );
        size_panel.setBackground(SudokuCommon.BACKGROUND_COLOR);
        size_panel.add(size_nine);
        size_panel.add(size_sixteen);
        
        easy = new JRadioButton("Easy");
        medium = new JRadioButton("Medium");
        hard = new JRadioButton("Hard");
        evil = new JRadioButton("Evil");
        
        easy.setFont(SudokuCommon.TEXT_FONT);
        easy.setBackground(SudokuCommon.BACKGROUND_COLOR);
        medium.setFont(SudokuCommon.TEXT_FONT);
        medium.setBackground(SudokuCommon.BACKGROUND_COLOR);
        hard.setFont(SudokuCommon.TEXT_FONT);
        hard.setBackground(SudokuCommon.BACKGROUND_COLOR);
        evil.setFont(SudokuCommon.TEXT_FONT);
        evil.setBackground(SudokuCommon.BACKGROUND_COLOR);
        
        diff_group.add(easy);
        diff_group.add(medium);
        diff_group.add(hard);
        diff_group.add(evil);
        
        diff_panel.setLayout( new FlowLayout() );
        diff_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
        diff_panel.add(easy);
        diff_panel.add(medium);
        diff_panel.add(hard);
        diff_panel.add(evil);
        
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
        
        option_layout = new GridLayout( 3, 1);
        option_panel = new JPanel( option_layout );
        option_panel.setBackground(SudokuCommon.BACKGROUND_COLOR);
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
        button_panel.setLayout( button_layout );
        button_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
        button_panel.add( play_game_button );
        button_panel.add( see_stats_button );
        button_panel.add( exit_button );
        
        option_panel.add( size_panel );
        option_panel.add( diff_panel );
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
        
        /*---------------------------------------
        Add the menu panel to the main frame
        so that it is displayed
       ---------------------------------------*/
       main_frame.add( menu_panel );
	}
	
	public void show()
	{
		main_frame.setVisible(true);
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
		
		//Set up all frame settings
		stats_frame = new JFrame( user.get_name() + " Stats" );
		JPanel stats_panel = new JPanel();
		GridLayout stats_grid = new GridLayout(3,2);
		JLabel score_label = new JLabel( "Score: " );
		JLabel score = new JLabel( "" + user.get_score() );
		JLabel maps_comp_label = new JLabel( "Maps Completed: " );
		JLabel maps = new JLabel( "" + user.get_maps_completed() );
		JLabel name_label = new JLabel( "Name: " );
		JLabel name = new JLabel( "" + user.get_name() );
		
		//add labels 
		stats_panel.setLayout( stats_grid );
		stats_panel.add( name_label );
		stats_panel.add( name );
		stats_panel.add( score_label );
		stats_panel.add( score );
		stats_panel.add( maps_comp_label );
		stats_panel.add( maps );
		
		//set frame specifications
		stats_frame.add(stats_panel);
		stats_frame.setSize( 300 , 300 );
		stats_frame.setLocation(300, 150);
		stats_frame.setResizable(false);
		stats_frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		
		//add window listeners
		stats_frame.addWindowListener(this);
		stats_frame.setVisible(true);
	}
	/*---------------------------------------------------------------------------------------
	 *  									All Listener Functions
	 --------------------------------------------------------------------------------------*/
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JPanel new_panel;
		
		/*-------------------------------------------
		 *  Play button was pressed. Create board,
		 *  for now.
		-------------------------------------------*/
		if( e.getSource() == play_game_button )
		{
            main_frame.getContentPane().remove( menu_panel );
            new_panel = board;
            main_frame.add( new_panel );
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

}