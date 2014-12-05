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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.util.Observable;
import java.util.Observer;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JPanel;
import javax.swing.Timer;


public class SudokuDisplay extends Observable implements ActionListener
{	
	/*-----------------------------------------------------------------------------------
								Private Class Members
	-----------------------------------------------------------------------------------*/
	private BoardSize board_size;				// size of current board
	private Difficulty difficulty;				// difficulty of board
	private SudokuBackEnd back_end;				// back end sudoku board for functionality
	private Board board;						// board panel of cells
	private File file;							// file to load
			
	private Timer timer;						// timer to keep track of time
	
	private long elapsed_time;					// elapsed time
	private long start_time;					// starting time of game
	private int game_score;						// current game score
	private int running_score;					// running score of this session
	private boolean paused;						// whether or not game is paused
	private boolean isAIRunning;
	
	private CustomButton pencil_button;			// pencil mode button
	private CustomButton eraser_button;			// eraser mode button
	private CustomButton pen_button;			// pen mode button
	private CustomButton save_and_quit_button; 	// save and quit button
	private CustomButton quit_button;			// quit button
	private CustomButton pause_button;			// pause button
	private CustomButton score_button;			// score button
	private CustomButton solve_button;			// solve button
	private CustomButton hint_button;			// hint button	
	
	private CustomLabel score;					// displays score
	private CustomLabel time;					// displays elapsed time
	private CustomLabel hint;					// displays remaining hints
	private CustomLabel score_label;			// "Score:"
	private CustomLabel time_label;				// "Elapsed Time:"
	private CustomLabel hint_label;				// "Hints:"
	
	private JPanel display_panel;				// panel to hold all elements
	private JPanel info_panel;					// houses score, hints, time, etc.
	private JPanel button_panel;				// houses all buttons
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		SudokuDisplay() - Constructor
	 * 
	 * Description:
	 * 		Set up all Components to generate board, and ensure game score is 0 and begin
	 * 		the timer.
	 --------------------------------------------------------------------------------------*/
	public SudokuDisplay( Observer listener, BoardSize size, Difficulty difficulty, boolean isAIOn )
	{
		addObserver( listener );
		board_size = size;
		this.difficulty = difficulty;
		paused = false;
		isAIRunning = isAIOn;
		start_time = 0;
		elapsed_time = 0;
		game_score = 0;
		running_score = 0;
		
	    /*---------------------------------------------------------------
        Create new panel to hold all elements of sudoku game
        ---------------------------------------------------------------*/
		display_panel = new JPanel();
		display_panel.setLayout( new BorderLayout() );
		
	    /*---------------------------------------------------------------
        Create a new back end board for all functionality
        ---------------------------------------------------------------*/
		back_end = new SudokuBackEnd( this.board_size, this.difficulty );
		back_end.generateNewPuzzle();
		back_end.printBoardContents();
		
	    /*---------------------------------------------------------------
        Load statistic panel, button panels and board panels
        ---------------------------------------------------------------*/
		loadStatPanel();
		loadButtonPanels();
		loadBoardPanel(false);
		initTimer();
		startTimer();
	}
	
	 /*---------------------------------------------------------------------------------------
     * Method:
     *      SudokuDisplay() - Alternate Constructor for loading custom game board
     * 
     * Description:
     *      Set Up all Components to generate board and load a file to act as a new 
     *      sudoku board.
     --------------------------------------------------------------------------------------*/
	public SudokuDisplay( Observer listener, Difficulty difficulty, File file, boolean isAIOn )
	{
	    addObserver( listener );
	    
	    this.difficulty = difficulty;	    
	    paused = false;
	    isAIRunning = isAIOn;
	    
	    this.file = file;
	    elapsed_time = 0;
	    start_time = 0;
	    game_score = 0;
	    running_score = 0;
	    
	    String[] data = readBoardFile( file );
	    	    
        display_panel = new JPanel();
        display_panel.setLayout( new BorderLayout() );
        
        back_end = new SudokuBackEnd( this.board_size, this.difficulty );
        back_end.generatePuzzleBasedOnFile( data );
        back_end.printBoardContents();
        
        loadStatPanel();
        loadButtonPanels();
        loadBoardPanel(false);
        initTimer();
        startTimer();
	}
	
	 /*---------------------------------------------------------------------------------------
	 * Method:
	 *      SudokuDisplay() - Alternate Constructor for loading a game save for a particular
	 *      board.
	 * 
	 * Description:
	 *      Set up all Components to generate board. This time, use the passed in file to
	 *      to load a game state (board size, board and Cell contents, score, time, hints,
	 *      etc.)
	 --------------------------------------------------------------------------------------*/
	public SudokuDisplay( Observer listener, File file )
	{
		Cell[][] c = null;
		int numHints = 0;
		paused = false;
		running_score = 0;
		
		addObserver( listener );
		
        /*---------------------------------------------------------------
        Atttempt to read in saved game board
        ---------------------------------------------------------------*/
		try 
		{
			ObjectInputStream in = new ObjectInputStream( new FileInputStream( file ) );
			
            /*---------------------------------------------------------------
            read in board size and difficulty
            ---------------------------------------------------------------*/
			board_size = (BoardSize) in.readObject();
			difficulty = (Difficulty) in.readObject();
			
			System.out.println("BSize: " + board_size);
			System.out.println("DIF: " + difficulty);			
			
			back_end = new SudokuBackEnd( this.board_size, this.difficulty );
			back_end.setBoard( (int[][]) in.readObject() );
			
            /*---------------------------------------------------------------
           	read in saved cells and other saved attributes
            ---------------------------------------------------------------*/
			c = (Cell[][]) in.readObject();
			
			running_score = game_score = (int) in.readInt();
			numHints = (int) in.readInt();
			start_time = elapsed_time = (long) in.readLong();
			isAIRunning = (boolean) in.readBoolean();
			
			System.out.println( "Hints: " + numHints );
	
			in.close();
		} 
		catch (ClassNotFoundException | IOException e1) 
		{
			e1.printStackTrace();
		}
		
        /*---------------------------------------------------------------
        Set up new display panel
        ---------------------------------------------------------------*/
        display_panel = new JPanel();
        display_panel.setLayout( new BorderLayout() );        
        back_end.printBoardContents();
        
        /*---------------------------------------------------------------
        Load components
        ---------------------------------------------------------------*/
        loadStatPanel();
        loadButtonPanels();
        loadBoardPanel( c );

        back_end.setHints( numHints );
        
        /*---------------------------------------------------------------
        Update UI components
        ---------------------------------------------------------------*/
        score.setText( "" + game_score );
        hint.setText( "" + back_end.getHints() );
        time.setText( "" +  elapsed_time );
        initTimer();
        startTimer();
	}
	
	 /*---------------------------------------------------------------------------------------
	 * Method:
	 *      startTimer()
	 * 
	 * Description:
	 *      start the timer!
	 --------------------------------------------------------------------------------------*/
	public void startTimer()
	{
		timer.start();
	}
	
	 /*---------------------------------------------------------------------------------------
	 * Method:
	 *      startTimer()
	 * 
	 * Description:
	 *      start the timer!
	 --------------------------------------------------------------------------------------*/
	public void stopTimer()
	{
		timer.stop();
	}
	
	 /*---------------------------------------------------------------------------------------
	 * Method:
	 *      initTimer()
	 * 
	 * Description:
	 *      create new timer
	 --------------------------------------------------------------------------------------*/
	public void initTimer()
	{
		timer = new Timer( 1000, 
				new ActionListener()
				{
					public void actionPerformed( ActionEvent ae )
					{
						elapsed_time++;
						time.setText("" +  elapsed_time );
					}
				}
			);
	}
	
	 /*---------------------------------------------------------------------------------------
	 * Method:
	 *      readBoardFile()
	 * 
	 * Description:
	 *      Converts the passed in file to a String.
	 --------------------------------------------------------------------------------------*/
    public String[] readBoardFile( File file ) 
    {
	    /*---------------------------------------------------------------
        						Local Variables
        ---------------------------------------------------------------*/
        StringBuffer fileBuffer = null;
        String fileString = null;
        String line = null;
        
	    /*---------------------------------------------------------------
        Attempt to read a board file, catch any exceptions
        ---------------------------------------------------------------*/
        try 
        {
            FileReader in = new FileReader( file );
            BufferedReader brd = new BufferedReader( in );
            fileBuffer = new StringBuffer();

            while ((line = brd.readLine()) != null) 
            {
                fileBuffer.append( line ).append( System.getProperty("line.separator") );
            }

            in.close();
            fileString = fileBuffer.toString();
        }
	    /*---------------------------------------------------------------
        Catch IO error but return nothing
        ---------------------------------------------------------------*/
        catch (IOException e) 
        {
            return null;
        }
        
	    /*---------------------------------------------------------------
        Split lines based on different delimiters
        ---------------------------------------------------------------*/
        String[] lines = fileString.split( "\\r?\\n" );
        
	    /*---------------------------------------------------------------
        Set board size
        ---------------------------------------------------------------*/
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
            System.out.println
            	( 
	            "Error, there is an unexpected number of rows "
	            + "present in this file."
            	);
        }
        
	    /*---------------------------------------------------------------
        Return array of characters for creating new board
        ---------------------------------------------------------------*/
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
	    /*---------------------------------------------------------------
        Set up a new 1 x 7 grid layout for panel housing stats components
        ---------------------------------------------------------------*/
		GridLayout info_grid = new GridLayout( 1, 8 );
		info_panel = new JPanel( info_grid );
		
	    /*---------------------------------------------------------------
        Set up labels and buttons
        ---------------------------------------------------------------*/
		score_label = new CustomLabel( "Score: " );
		score = new CustomLabel( "0" );
		time_label = new CustomLabel( "Time elapsed: " );
		time = new CustomLabel( "0" );
		score_button = new CustomButton( "Update Score!", true );
		pause_button = new CustomButton( "Pause", true );
		hint_label = new CustomLabel( "Hints Left: " );
		hint = new CustomLabel( "" + back_end.getHints() );
		
		score_button.addActionListener( this );
		pause_button.addActionListener( this );
		
	    /*---------------------------------------------------------------
        Add all components to the information panel
        ---------------------------------------------------------------*/
		info_panel.add( score_label );
		info_panel.add( score );
		info_panel.add( time_label );
		info_panel.add( time );
		info_panel.add( hint_label );
		info_panel.add( hint );
		info_panel.add( pause_button );
		info_panel.add( score_button );
		info_panel.setBackground( SudokuCommon.BACKGROUND_COLOR );
		
	    /*---------------------------------------------------------------
        Add information panel to northern border of game display
        ---------------------------------------------------------------*/
		display_panel.add( info_panel, BorderLayout.NORTH );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		loadButtonPanels()
	 * 
	 * Description:
	 * 		load all of the in game buttons to be used during game play.
	 --------------------------------------------------------------------------------------*/
	public void loadButtonPanels()
	{
	    /*---------------------------------------------------------------
        Set up grid layout for button panel
        ---------------------------------------------------------------*/
		GridLayout button_grid = new GridLayout(1,7);
		button_panel = new JPanel( button_grid );
		
	    /*---------------------------------------------------------------
        Set up each button for button panel
        ---------------------------------------------------------------*/
		pencil_button = new CustomButton( "Pencil Mode", true );
		pen_button = new CustomButton( "Pen Mode", true );
		quit_button = new CustomButton( "Quit", true );
		save_and_quit_button = new CustomButton( "Save & Quit", true );
		solve_button = new CustomButton( "Solve Now", true );
		hint_button = new CustomButton( "Hint", true );
		eraser_button = new CustomButton( "Eraser Mode", true );
		
	    /*---------------------------------------------------------------
        Add action listeners
        ---------------------------------------------------------------*/
		pencil_button.addActionListener( this );
		pen_button.addActionListener( this );
		quit_button.addActionListener( this );
		save_and_quit_button.addActionListener( this );
		solve_button.addActionListener( this );
		hint_button.addActionListener( this );
		eraser_button.addActionListener( this );
		
	    /*---------------------------------------------------------------
        Add all components to the button panel
        ---------------------------------------------------------------*/
		button_panel.add( pen_button );
		button_panel.add( pencil_button );
		button_panel.add( eraser_button );
		button_panel.add( solve_button );
		button_panel.add( hint_button );
		button_panel.add( save_and_quit_button );
		button_panel.add( quit_button );
		
	    /*---------------------------------------------------------------
        Add button panel to southern border of game display
        ---------------------------------------------------------------*/
		display_panel.add( button_panel, BorderLayout.SOUTH );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		loadBoardPanel()
	 * 
	 * Description:
	 * 		create new Sudoku board and activate pen_mode
	 --------------------------------------------------------------------------------------*/
	public void loadBoardPanel( boolean isLoadingSave )
	{	
		board = new Board( this.board_size, this.difficulty );
		board.enablePenMode();
		pen_button.activateButton();
		
		if (!isLoadingSave)
		{
			back_end.populateBoard( board.getCells() );
		}
		
		display_panel.add( board, BorderLayout.CENTER );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		loadBoardPanel()
	 * 
	 * Description:
	 * 		loads in a new board based on 2d array of cells
	 --------------------------------------------------------------------------------------*/
	public void loadBoardPanel( Cell[][] c )
    {   
        board = new Board( this.board_size, this.difficulty, c );
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
		timer = null;
		setChanged();
		notifyObservers( "Quit" );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		saveAndQuitGame()
	 * 
	 * Description:
	 * 		save the game's state and quit the game. If sucessful, return true, otherwise 
	 *      return false
	 --------------------------------------------------------------------------------------*/
	public boolean saveAndQuitGame()
	{	
		/*---------------------------------------------------------------
        Don't allow game state saving if the game is already complete.
        ---------------------------------------------------------------*/
		if ( !back_end.isWin() )
		{		 
			/*---------------------------------------------------------------
	        Get path and file name user wants to save to
	        ---------------------------------------------------------------*/
    		ObjectOutputStream out;
    		
            JFileChooser fileOpen = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter( "save files", "save" );
            fileOpen.addChoosableFileFilter( filter );
            File file = null;
         
            int ret = fileOpen.showSaveDialog( new JPanel() );
            
            if ( ret == JFileChooser.APPROVE_OPTION ) 
            {
              	/*---------------------------------------------------------------
                Make sure to attach the ".save" extension to the end of the file 
                if it doesn't have it already.
                ---------------------------------------------------------------*/
                if ( !fileOpen.getSelectedFile().getName().endsWith( ".save" ) ) 
                {
                    file = new File( fileOpen.getSelectedFile() + ".save" );
                }
                else
                {
                    file = fileOpen.getSelectedFile();
                }
            } 
            else 
            {
            	timer.start();
                return false;
            }
    		
            /*---------------------------------------------------------------
            Write the size of the board, difficulty, answer board contents, 
            player input contents and number of hints to the disk.
            ---------------------------------------------------------------*/
    		try 
    		{
    			out = new ObjectOutputStream( new FileOutputStream(file) );
    			out.writeObject( board_size );
    			out.writeObject( difficulty );
    		    out.writeObject( back_end.getBoard() );
    		    
    		    Cell[][] c = board.getCells();
    		    Cell[][] cells = new Cell[ c.length ][ c.length ];		    
    		    
    	        for( int i = 0; i < cells.length; i++)
    	        {
    	            for( int j = 0; j < cells[i].length; j++)
    	            {
    	            	cells[i][j] = new Cell( board_size );
    	                cells[i][j].setLocked( c[ i ][ j ].isLocked() );
    	                cells[i][j].setPenFilled( c[ i ][ j ].isPenFilled() );
    	                cells[i][j].setEraserCount( c[ i ][ j ].getEraserCount() );
    
    	                /*---------------------------------------------------------------
    	                Store the text of the pen field into str.
    	                ---------------------------------------------------------------*/
    	                cells[i][j].getPenFieldObject().str = c[i][j].getPenField();
    	                /*---------------------------------------------------------------
    	                Store the text of the pencil field into str.
    	                ---------------------------------------------------------------*/
    	                cells[i][j].getPencilFieldObject().str = c[i][j].getPencilField();
    	            }
    	        }
    	        
    		    out.writeObject( cells );
    		    
    		    out.writeInt( game_score );
    		    out.writeInt( back_end.getHints() );
    		    out.writeLong( elapsed_time );
    		    out.writeBoolean( isAIRunning );
    		    out.close();
    		}
    		catch (IOException e) 
    		{
    			e.printStackTrace();
    		}
		}		
		
		/*---------------------------------------------------------------
        Update user time and quit the game
        ---------------------------------------------------------------*/
		updateTime( elapsed_time );
		quitGame();
		
		return true;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		winGame()
	 * 
	 * Description:
	 * 		alert listening frame that the game has been won
	 --------------------------------------------------------------------------------------*/
	public void winGame()
	{
		stopTimer();
        JOptionPane.showMessageDialog
        (
            null,
            "Congratulations! You scored " + game_score + " points in " + elapsed_time + " seconds!",
            "Results",
            JOptionPane.OK_OPTION
        );
        
        updateTime( elapsed_time );
		board.clearBoard();
		setChanged();
		notifyObservers( new WinInfo(true, board_size) );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		updateScore()
	 * 
	 * Description:
	 * 		update user score if there has been an increase or change
	 --------------------------------------------------------------------------------------*/
	public void updateTime( long time_elapsed )
	{
		setChanged();
		notifyObservers( time_elapsed - start_time );
	}
		
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		updateScore()
	 * 
	 * Description:
	 * 		update user score if there has been an increase or change
	 --------------------------------------------------------------------------------------*/
	public void updateScore( Integer new_score )
	{
		int updated_score = 0;
	    /*---------------------------------------------------------------
        if new score is not less or equal to current running total for
        game session
        ---------------------------------------------------------------*/
		if( !( new_score <= running_score ) )
		{
			updated_score = new_score - running_score;
			game_score += ( updated_score );
			running_score = new_score;
			this.score.setText( "" + game_score );
			boolean win = back_end.isWin();
			
		    /*---------------------------------------------------------------
	        Determine whether or not board is in win state
	        ---------------------------------------------------------------*/
			if( win == true )
			{
				System.out.println( "Player wins!" );
				board.setWin();
				setChanged();
				notifyObservers( updated_score );
				winGame();
			}
		    /*---------------------------------------------------------------
	        Board not in win state.
	        ---------------------------------------------------------------*/
			else
			{
				setChanged();
				notifyObservers( updated_score );
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
		if( e.getSource() == pen_button && !paused )
		{
			deactivateModes();
			pen_button.activateButton();
			board.enablePenMode();
		}
		
	    /*---------------------------------------------------------------
        If pencil_button is pressed, activate the button and enable
        pencil mode for board
        ---------------------------------------------------------------*/
		if( e.getSource() == pencil_button && !paused )
		{
			deactivateModes();
			pencil_button.activateButton();
			board.enablePencilMode();
		}
		
	    /*---------------------------------------------------------------
        if eraser_button is pressed, activate the button and enable
        eraser mode for board
        ---------------------------------------------------------------*/
		if( e.getSource() == eraser_button && !paused )
		{
			deactivateModes();
			eraser_button.activateButton();
			board.enableEraserMode();
		}
		
	    /*---------------------------------------------------------------
        Quit the game
        ---------------------------------------------------------------*/
		if( e.getSource() == quit_button && !paused )
		{ 
			System.out.println( "Calling Quit from Menu Frame!" );
			quitGame();
	        display_panel.repaint();
	        display_panel.setVisible( true );
		}
		
	    /*---------------------------------------------------------------
        Save the current game's state, and quit the game.
        ---------------------------------------------------------------*/
		if( e.getSource() == save_and_quit_button && !paused )
		{
			System.out.println( "Calling Quit from Menu Frame!" );
			
			stopTimer();
			
			if ( saveAndQuitGame() )
			{
			    display_panel.repaint();
	            display_panel.setVisible(true);
			}
		}
		
	    /*---------------------------------------------------------------
        Give user hints
        ---------------------------------------------------------------*/
		if( e.getSource() == hint_button && !paused )
		{
			boolean flag = back_end.hint( board.getCells() );
			if( !flag )
			{
				System.out.println( "No More Hints Left" );
				return;
			}
			
			hint.setText( "" + back_end.getHints() );
		}
		
	    /*---------------------------------------------------------------
        Score the current map
        ---------------------------------------------------------------*/
		if( e.getSource() == score_button && !paused )
		{
			System.out.println( "Score Button Pressed!" );
			updateScore( back_end.scoreBoard( board.getCells() ) );
		}
		
	    /*---------------------------------------------------------------
        Solve the current map for user
        ---------------------------------------------------------------*/
		if( e.getSource() == solve_button && !paused )
		{
		    JOptionPane.showMessageDialog(
		            null,
		            "Board will be populated with correct answers. You will be scored for # of correct entries",
		            "Solving now..",
		            JOptionPane.OK_OPTION
		            );
		    
		    updateTime( elapsed_time );
		    updateScore( back_end.scoreBoard( board.getCells() ) );
		    back_end.solve( board.getCells() );
		    winGame();
		}
		
	    /*---------------------------------------------------------------
        Pause the game, or restart the game
        ---------------------------------------------------------------*/
		if( e.getSource() == pause_button )
		{
		    /*---------------------------------------------------------------
	       	Game is paused. Deactivate button, and restart timer
	        ---------------------------------------------------------------*/
			if( paused )
			{
				paused = false;
				pause_button.deactivateButton();
				board.pauseBoard( false );
				deactivateModes();
				pen_button.activateButton();
				board.enablePenMode();
				display_panel.repaint();
				startTimer();
			}
		    /*---------------------------------------------------------------
	        Pause the game and activate the pause button
	        ---------------------------------------------------------------*/
			else
			{
				paused = true;
				pause_button.activateButton();
				board.disableBoard();
				board.pauseBoard( true );
				stopTimer();
				display_panel.repaint();
			}
		}
		
	}
}
