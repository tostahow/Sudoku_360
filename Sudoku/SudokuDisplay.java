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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	private File file;
	
	private Thread timer_thread;
	private long start_time;
	private long elapsed_time;
	private int game_score;
	
	private CustomButton pencil_button;
	private CustomButton eraser_button;
	private CustomButton pen_button;
	private JButton quit_button;
	private JButton score_button;
	private JButton solve_button;
	private JButton hint_button;
	
	private CustomLabel score;
	private CustomLabel time;
	private CustomLabel hint;
	private CustomLabel score_label;
	private CustomLabel time_label;
	private CustomLabel hint_label;
	
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
		game_score = 0;
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
		
		back_end = new SudokuBackEnd( this.board_size, this.difficulty );
		back_end.generateNewPuzzle();
		back_end.printBoardContents();
		
		loadStatPanel();
		loadButtonPanels();
		loadBoardPanel();
	}
	
	 /*---------------------------------------------------------------------------------------
     * Method:
     *      SudokuDisplay() - Alternate Constructor
     * 
     * Description:
     *      Set Up all Components to generate board
     --------------------------------------------------------------------------------------*/
	public SudokuDisplay( Observer listener, Difficulty difficulty, File file )
	{
	    addObserver( listener );
	    
	    //board_size = BoardSize.NINE;
	    this.difficulty = difficulty;	    
	    
	    this.file = file;
	    game_score = 0;
	    
	    String[] data = readBoardFile(file);
	    	    
        display_panel = new JPanel();
        display_panel.setLayout( new BorderLayout() );
        
        back_end = new SudokuBackEnd( this.board_size, this.difficulty );
        //back_end.generateNewPuzzle();
        back_end.generatePuzzleBasedOnFile(data);
        back_end.printBoardContents();
        
        loadStatPanel();
        loadButtonPanels();
        loadBoardPanel();
	}
	
	 /*---------------------------------------------------------------------------------------
     * Method:
     *      readBoardFile()
     * 
     * Description:
     *      Converts the passed in file to a String.
     --------------------------------------------------------------------------------------*/
    public String[] readBoardFile(File file) 
    {
        StringBuffer fileBuffer = null;
        String fileString = null;
        String line = null;

        try 
        {
            FileReader in = new FileReader(file);
            BufferedReader brd = new BufferedReader(in);
            fileBuffer = new StringBuffer();

            while ((line = brd.readLine()) != null) 
            {
                fileBuffer.append(line).append(System.getProperty("line.separator"));
            }

            in.close();
            fileString = fileBuffer.toString();
        } 
        catch (IOException e) 
        {
            return null;
        }

        String[] lines = fileString.split("\\r?\\n");
        
        if (lines.length == 9)
        {
            board_size = BoardSize.NINE;
        }
        else if (lines.length == 16)
        {
            board_size = BoardSize.SIXTEEN;
        }
        else
        {
            System.out.println("Error, there is an unexpected number of rows present in this file.");
        }
        
        return lines;
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
		GridLayout info_grid = new GridLayout(1,7);
		info_panel = new JPanel( info_grid );
		
		score_label = new CustomLabel("Score: ");
		score = new CustomLabel("0");
		time_label = new CustomLabel("Time elapsed: ");
		time = new CustomLabel("0.00");
		score_button = new CustomButton( "Update Score!", true );
		hint_label = new CustomLabel("Hints Left: ");
		hint = new CustomLabel("" + back_end.getHints());
		
		
		score_button.addActionListener( this );
		info_panel.add( score_label );
		info_panel.add( score );
		info_panel.add( time_label );
		info_panel.add( time );
		info_panel.add( hint_label );
		info_panel.add( hint );
		info_panel.add( score_button );
		info_panel.setBackground(SudokuCommon.BACKGROUND_COLOR);
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
		
		pencil_button = new CustomButton("Pencil Mode", true);
		pen_button = new CustomButton("Pen Mode", true);
		quit_button = new CustomButton("Quit", true);
		solve_button = new CustomButton("Solve Now", true);
		hint_button = new CustomButton("Hint", true);
		eraser_button = new CustomButton("Eraser Mode", true);
		
		pencil_button.addActionListener(this);
		pen_button.addActionListener(this);
		quit_button.addActionListener(this);
		solve_button.addActionListener(this);
		hint_button.addActionListener(this);
		eraser_button.addActionListener(this);
		
		button_panel.add( pen_button );
		button_panel.add( pencil_button );
		button_panel.add( eraser_button );
		button_panel.add( solve_button );
		button_panel.add( hint_button );
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
		
		back_end.populateBoard(board.getCells());
		
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
	
	public void winGame()
	{
		board.clearBoard();
		setChanged();
		notifyObservers("Win");
	}
	
	public void updateScore( Integer new_score )
	{
		if( !(new_score <= game_score) )
		{
			game_score = new_score;
			this.score.setText( "" + game_score );
			boolean win = back_end.isWin();
			if( win == true )
			{
				System.out.println("Player wins!");
				board.setWin();
				setChanged();
				notifyObservers( new_score );
				winGame();
			}
			else
			{
				setChanged();
				notifyObservers( new_score );
			}
		}
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
        Give user hints
        ---------------------------------------------------------------*/
		if( e.getSource() == hint_button )
		{
			boolean flag = back_end.hint( board.getCells() );
			
			if( !flag )
			{
				System.out.println("No More Hints Left");
				return;
			}
			
			hint.setText( "" + back_end.getHints() );
		}
		
		if( e.getSource() == score_button )
		{
			System.out.println("Score Button Pressed!");
			updateScore( back_end.scoreBoard( board.getCells() ) );
		}
		
		if( e.getSource() == solve_button )
		{
			back_end.solve( board.getCells() );
			updateScore(1);
		}
		
	}
}
