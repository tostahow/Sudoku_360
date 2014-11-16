/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		SudokuDisplay.java
 * 
 * Description:
 * 		Front end of the Sudoku Puzzle. Generates the visual interface in which the user
 * 		can interact with.
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class SudokuDisplay extends Observable implements ActionListener
{	
	private BoardSize board_size;
	private Difficulty difficulty;
	private SudokuBackEnd back_end;
	private Board board;
	
	private Thread timer_thread;
	
	private long start_time;
	private long elapsed_time;
	
	private ModeButton pencil_button;
	private ModeButton eraser_button;
	private ModeButton pen_button;
	private JButton quit_button;
	private JButton score_button;
	private JButton solve_button;
	private JButton back_button;
	
	private JLabel score;
	private JLabel time;
	private JLabel score_label;
	private JLabel time_label;
	
	
	private JPanel display_panel;
	private JPanel info_panel;
	private JPanel button_panel;
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		SudokuDisplay() - Constructor
	 * 
	 * Description:
	 * 		Set Up all Components to generate board
	 --------------------------------------------------------------------------------------*/
	public SudokuDisplay( Observer listener, BoardSize size, Difficulty difficulty )
	{
		addObserver( listener );
		board_size = size;
		this.difficulty = difficulty;
		/*timer_thread = new Thread()
		{
			public void run()
			{
				while(true)
				{
					elapsed_time = (System.currentTimeMillis() - start_time)/1000;
					time.setText("" + elapsed_time );
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};*/
		display_panel = new JPanel();
		display_panel.setLayout( new BorderLayout() );
		
		loadStatPanel();
		loadButtonPanels();
		loadBoardPanel();
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		loadStatPanel()
	 * 
	 * Description:
	 * 		loads the basic statistic panel with score of game, elapsed time of game, and
	 * 		a button to update score.
	 --------------------------------------------------------------------------------------*/
	public void loadStatPanel()
	{
		GridLayout info_grid = new GridLayout(1,5);
		info_panel = new JPanel( info_grid );
		
		score_label = new JLabel("Score: ");
		score = new JLabel("0");
		time_label = new JLabel("Time elapsed: ");
		time = new JLabel("0.00");
		score_button = new JButton("Update Score!");
		
		score_label.setFont( SudokuCommon.PEN_FONT );
		score.setFont( SudokuCommon.PEN_FONT );
		time_label.setFont( SudokuCommon.PEN_FONT );
		time.setFont( SudokuCommon.PEN_FONT );
		score_button.setFont( SudokuCommon.PEN_FONT );
		
		score_button.addActionListener( this );
		info_panel.add( score_label );
		info_panel.add( score );
		info_panel.add( time_label );
		info_panel.add( time );
		info_panel.add( score_button );
		
		display_panel.add(info_panel, BorderLayout.NORTH );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		loadButtonPanels()
	 * 
	 * Description:
	 * 		load all of the buttons to be used during game play.
	 --------------------------------------------------------------------------------------*/
	public void loadButtonPanels()
	{
		GridLayout button_grid = new GridLayout(1,6);
		button_panel = new JPanel( button_grid );
		
		pencil_button = new ModeButton("Pencil Mode");
		pen_button = new ModeButton("Pen Mode");
		quit_button = new JButton("Quit");
		score_button = new JButton("Solve Now");
		back_button = new JButton("Main Menu");
		eraser_button = new ModeButton("Eraser Mode");
		
		pencil_button.setFont( SudokuCommon.PEN_FONT );
		quit_button.setFont( SudokuCommon.PEN_FONT );
		pen_button.setFont( SudokuCommon.PEN_FONT );
		back_button.setFont( SudokuCommon.PEN_FONT );
		score_button.setFont( SudokuCommon.PEN_FONT );
		eraser_button.setFont( SudokuCommon.PEN_FONT );
		
		
		pencil_button.addActionListener(this);
		pen_button.addActionListener(this);
		quit_button.addActionListener(this);
		score_button.addActionListener(this);
		back_button.addActionListener(this);
		eraser_button.addActionListener(this);
		
		button_panel.add( pen_button );
		button_panel.add( pencil_button );
		button_panel.add( eraser_button );
		button_panel.add( score_button );
		button_panel.add( back_button );
		button_panel.add( quit_button );
		
		display_panel.add( button_panel, BorderLayout.SOUTH );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		loadBoardPanel()
	 * 
	 * Description:
	 * 		create new Sudoku board and activate pen_mode
	 --------------------------------------------------------------------------------------*/
	public void loadBoardPanel()
	{
		board = new Board( this.board_size, this.difficulty );
		board.enablePenMode();
		pen_button.activateButton();
		display_panel.add( board, BorderLayout.CENTER );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getGamePanel()
	 * 
	 * Description:
	 * 		return panel of game
	 --------------------------------------------------------------------------------------*/
	public JPanel getGamePanel()
	{
		//start_time = System.currentTimeMillis();
		//timer_thread.start();
		return display_panel;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		deactivateModes()
	 * 
	 * Description:
	 * 		deactivate the different mode buttons
	 --------------------------------------------------------------------------------------*/
	public void deactivateModes()
	{
		pen_button.deactivateButton();
		pencil_button.deactivateButton();
		eraser_button.deactivateButton();
		
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		quitGame()
	 * 
	 * Description:
	 * 		quit the game
	 --------------------------------------------------------------------------------------*/
	public void quitGame()
	{
		timer_thread = null;
		setChanged();
		notifyObservers( "Quit" );
	}
	/*---------------------------------------------------------------------------------------
	 *  						 All Listener Functions
	 --------------------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		actionPerformed()
	 * 
	 * Description:
	 * 		depending on button pressed, a different behavior should be activated
	 --------------------------------------------------------------------------------------*/
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
	    /*---------------------------------------------------------------
        If pen_button is pressed, activate the button and enable
        pen mode for board
        ---------------------------------------------------------------*/
		if( e.getSource() == pen_button )
		{
			deactivateModes();
			pen_button.activateButton();
			board.enablePenMode();
			display_panel.repaint();
			display_panel.setVisible(true);
		}
		
	    /*---------------------------------------------------------------
        If pencil_button is pressed, activate the button and enable
        pencil mode for board
        ---------------------------------------------------------------*/
		if( e.getSource() == pencil_button )
		{
			deactivateModes();
			pencil_button.activateButton();
			board.enablePencilMode();
			
			display_panel.repaint();
			display_panel.setVisible(true);
		}
		
	    /*---------------------------------------------------------------
        if eraser_button is pressed, activate the button and enable
        eraser mode for board
        ---------------------------------------------------------------*/
		if( e.getSource() == eraser_button )
		{
			deactivateModes();
			eraser_button.activateButton();
			board.enableEraserMode();
			display_panel.repaint();
			display_panel.setVisible(true);
		}
		
	    /*---------------------------------------------------------------
        Quit the game
        ---------------------------------------------------------------*/
		if( e.getSource() == quit_button )
		{
			System.out.println("Calling Quit from Menu Frame!");
			quitGame();
		}
		
	    /*---------------------------------------------------------------
        back to main menu
        ---------------------------------------------------------------*/
		if( e.getSource() == back_button )
		{
			
		}
		
	}
}
