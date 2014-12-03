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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private JButton save_and_quit_button;
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
	 * 		Set up all Components to generate board
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
		loadBoardPanel(false);
	}
	
	 /*---------------------------------------------------------------------------------------
     * Method:
     *      SudokuDisplay() - Alternate Constructor for loading a custom game board
     * 
     * Description:
     *      Set up all Components to generate board. This time, use the passed in file to
     *      generate the board, and difficulty to tweak the board.
     --------------------------------------------------------------------------------------*/
	public SudokuDisplay( Observer listener, Difficulty difficulty, File file )
	{
	    addObserver( listener );

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
        loadBoardPanel(false);
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
		
		try 
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			
			board_size = (BoardSize) in.readObject();
			difficulty = (Difficulty) in.readObject();
			
			System.out.println("BSize: " + board_size);
			System.out.println("DIF: " + difficulty);			
			
			back_end = new SudokuBackEnd( this.board_size, this.difficulty );
			back_end.setBoard((int[][]) in.readObject());
			
			c = (Cell[][]) in.readObject();
			
			game_score = (int) in.readInt();
			numHints = (int) in.readInt();
			
			System.out.println("Hints: " + numHints);
	
			in.close();
		} 
		catch (ClassNotFoundException | IOException e1) 
		{
			e1.printStackTrace();
		}
		
        display_panel = new JPanel();
        display_panel.setLayout( new BorderLayout() );        
        back_end.printBoardContents();
        
        loadStatPanel();
        loadButtonPanels();
        loadBoardPanel(c);

        back_end.setHints(numHints);
        
        // Update the UI fields.
        score.setText( "" + game_score );
        hint.setText( "" + back_end.getHints() );
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
            // TBD. Need to go back to menu if file is corrupt.
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
		GridLayout button_grid = new GridLayout(1, 7);
		button_panel = new JPanel( button_grid );
		
		pencil_button = new CustomButton("Pencil Mode", true);
		pen_button = new CustomButton("Pen Mode", true);
		save_and_quit_button = new CustomButton("Save & Quit", true);
		quit_button = new CustomButton("Quit", true);
		solve_button = new CustomButton("Solve Now", true);
		hint_button = new CustomButton("Hint", true);
		eraser_button = new CustomButton("Eraser Mode", true);
		
		pencil_button.addActionListener(this);
		pen_button.addActionListener(this);
		save_and_quit_button.addActionListener(this);
		quit_button.addActionListener(this);
		solve_button.addActionListener(this);
		hint_button.addActionListener(this);
		eraser_button.addActionListener(this);
		
		button_panel.add( pen_button );
		button_panel.add( pencil_button );
		button_panel.add( eraser_button );
		button_panel.add( solve_button );
		button_panel.add( hint_button );
		button_panel.add( save_and_quit_button );
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
	public void loadBoardPanel(boolean isLoadingSave)
	{	
		board = new Board( this.board_size, this.difficulty );
		board.enablePenMode();
		pen_button.activateButton();
		
		if (!isLoadingSave)
		{
			back_end.populateBoard(board.getCells());
		}
		
		display_panel.add( board, BorderLayout.CENTER );
	}

    public void loadBoardPanel(Cell[][] c)
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
	 * Method:
	 * 		saveAndQuitGame()
	 * 
	 * Description:
	 * 		save the game's state and quit the game
	 --------------------------------------------------------------------------------------*/
	public void saveAndQuitGame()
	{
		timer_thread = null;
		
		// Don't allow game state saving if the game is already complete.
		if (!back_end.isWin())
		{		
    		ObjectOutputStream out;
    		
    		// Write the size of the board, difficulty, answer board contents, player input contents and number of
    		// hints to the disk.
    		try 
    		{
    			out = new ObjectOutputStream(new FileOutputStream(new File("DUMMY_" + board_size + "_" + difficulty + ".save")));
    			out.writeObject(board_size);
    			out.writeObject(difficulty);
    		    out.writeObject(back_end.getBoard());
    		    
    		    Cell[][] c = board.getCells();
    		    Cell[][] cells = new Cell[c.length][c.length];		    
    		    
    	        for( int i = 0; i < cells.length; i++)
    	        {
    	            for( int j = 0; j < cells[i].length; j++)
    	            {
    	            	cells[i][j] = new Cell( board_size );
    	                //cells[i][j].pen_mode = c[i][j].pen_mode;
    	                //cells[i][j].pencil_mode = c[i][j].pencil_mode;
    	                //cells[i][j].eraser_mode = c[i][j].eraser_mode;
    	                cells[i][j].setLocked(c[i][j].isLocked());
    	                cells[i][j].setPenFilled(c[i][j].isPenFilled());
    	                cells[i][j].setEraserCount(c[i][j].getEraserCount());
    	            	
    	                /*
    		            if (c[i][j].pen_field.getFont() == SudokuCommon.PEN_FONT)
    		            {
    		                cells[i][j].pen_field = new CellField( "", true );
    		            }
    		            else
    		            {
    		                cells[i][j].pen_field = new CellField( "", false );
    		            }
    		            
    		            if (c[i][j].pencil_field.getFont() == SudokuCommon.PEN_FONT)
    		            {
    		                cells[i][j].pencil_field = new CellField( "", true );
    		            }
    		            else
    		            {
    		                cells[i][j].pencil_field = new CellField( "", false );
    		            }
    		            */
    		            
    		            /*
    	                cells[i][j].pen_field.setText( c[i][j].pen_field.getText() );
    	                cells[i][j].pen_field.setEditable( (c[i][j].pen_field.isEditable()) );
    	                cells[i][j].pen_field.setDocument( new TextFieldLimit(1, c[i][j].cell_type) );
    	                cells[i][j].pen_field.setForeground( c[i][j].pen_field.getForeground() );
    	                cells[i][j].pen_field.setBackground( c[i][j].pen_field.getBackground() );
    	                
    	                cells[i][j].pencil_field.setText( c[i][j].pencil_field.getText() );
    	                cells[i][j].pencil_field.setEditable( (c[i][j].pencil_field.isEditable()) );                
    	                cells[i][j].pencil_field.setDocument( new TextFieldLimit(6, c[i][j].cell_type) );
    	                cells[i][j].pencil_field.setForeground( c[i][j].pencil_field.getForeground() );
    	                cells[i][j].pencil_field.setBackground( c[i][j].pencil_field.getBackground() );
    	                */
    
    	                // Store the text of the pen field into str.
    	                cells[i][j].pen_field.str = c[i][j].pen_field.getText();
    	                //cells[i][j].pen_field.editable = c[i][j].pen_field.isEditable();
    	                //cells[i][j].pen_field.fgColor = c[i][j].pen_field.getForeground();
    	                //cells[i][j].pen_field.bgColor = c[i][j].pen_field.getBackground();
    	                //cells[i][j].pen_field.font = c[i][j].pen_field.getFont();
    	                
    	                // Store the text of the pencil field into str.
    	                cells[i][j].pencil_field.str = c[i][j].pencil_field.getText();
    	                //cells[i][j].pencil_field.editable = c[i][j].pencil_field.isEditable();
    	                //cells[i][j].pencil_field.fgColor = c[i][j].pencil_field.getForeground();
    	                //cells[i][j].pencil_field.bgColor = c[i][j].pencil_field.getBackground();
    	                //cells[i][j].pencil_field.font = c[i][j].pencil_field.getFont();
    	                
    	                cells[i][j].cell_type = c[i][j].cell_type;
    	            }
    	        }
    	        
    		    out.writeObject(cells); // Problem is here. While saving, the display gets messed up.
    		    
    		    out.writeInt(game_score);
    		    out.writeInt(back_end.getHints());
    		    
    		    out.close();
    		}
    		catch (IOException e) 
    		{
    			e.printStackTrace();
    		}
		}		
		
		// Alert that changes have occurred, and notify obsevers that the program's ready to quit.
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
        Save the current game's state, and quit the game.
        ---------------------------------------------------------------*/
		if( e.getSource() == save_and_quit_button )
		{
			System.out.println("Calling Quit from Menu Frame!");
			saveAndQuitGame();
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
	        display_panel.repaint();
	        display_panel.setVisible(true);
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
