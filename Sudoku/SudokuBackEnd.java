/*-------------------------------------------------------------------------------------------------
 * Document:
 *      SudokuBackEnd.java
 * 
 * Description:
 *      Contains all of the back end handling of data, such as puzzle generation, checking,
 *      solving and other features for the puzzles..
 *      
 * Author:
 * 		Xavier Tariq and Travis Ostahowski
-------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/*-------------------------------------------------------------------------------------------------
										  NOTE
Much of this algorithm is based/taken from here: 
http://stackoverflow.com/questions/15690254/how-to-generate-a-complete-sudoku-board-algorithm-error
More alterations and updates are needed in order to suit the needs of this project.	
-------------------------------------------------------------------------------------------------*/
public class SudokuBackEnd
{
	/*-----------------------------------------------------------------------------------
								Private Class Members
	-----------------------------------------------------------------------------------*/
    private int blockSize; 				// Length of each block.
    private int fieldSize; 				// Length of the entire board. It will be equal to blockSize^2.
    private int[][] board; 				// Double int array for the generated Sudoku board.
    private BoardSize board_size;		// Size of board
    private int numbers_shown;			// numbers revealed to player
    private int hints;					// number of hints
    private boolean win;				// flag for winning states
    private Random rand;				// random generator
    
	// ArrayList of HashSet<Integer> to store the tried numbers. 
	// Only used during board generation.
    private ArrayList<ArrayList<HashSet<Integer>>> triedNumbers;
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		SudokuBackEnd() - Constructor
	 * 
	 * Description:
	 * 		Sets all attributes and initialize a base board
	 --------------------------------------------------------------------------------------*/
    public SudokuBackEnd( BoardSize size, Difficulty board_difficulty )
    {
    	board_size = size;
    	numbers_shown = 0;
    	hints = 0;
    	win = false;
    	rand = new Random();
    	setGivenHelp( board_difficulty );
        initBaseBoard();
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setHints
	 * 
	 * Description:
	 * 		set number of hints based on difficulty
	 --------------------------------------------------------------------------------------*/
    public void setHints( Difficulty difficulty )
    {
    	
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		initBaseBoard()
	 * 
	 * Description:
	 * 		reset base board. Use if it is assumed board dimension have changed.
	 --------------------------------------------------------------------------------------*/    
    private void initBaseBoard()
    {
    	if( board_size == BoardSize.NINE )
    		blockSize = 3;
    	else
    		blockSize = 4;
    	
    	fieldSize = blockSize*blockSize;
    	
        board = new int[ fieldSize ][ fieldSize ];
        triedNumbers = new ArrayList<ArrayList<HashSet<Integer>>>();
        
        for (int i = 0; i < fieldSize; i++)
        {
            triedNumbers.add( new ArrayList<HashSet<Integer>>() );
            
            for (int j = 0; j < fieldSize; j++)
            {                
                board[i][j] = 0;
                triedNumbers.get(i).add( new HashSet<Integer>() );
            }
        }     
        
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		resetBoard()
	 * 
	 * Description:
	 * 		Reset board to initial state
	 --------------------------------------------------------------------------------------*/    
    private void resetBoard()
    {
        for (int i = 0; i < fieldSize; i++)
        {
            for (int j = 0; j < fieldSize; j++)
            {
                board[i][j] = 0;
                triedNumbers.get(i).get(j).clear();
            }
        }        
    }    
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		printBoardContents()
	 * 
	 * Description:
	 * 		prints board for debugging purposes
	 --------------------------------------------------------------------------------------*/
    public void printBoardContents()
    {
        for (int i = 0; i < fieldSize; i++)
        {
            for (int j = 0; j < fieldSize; j++)
            {
                System.out.print( board[i][j] + " " );
            }
            
            System.out.println();
        }
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		generateNewPuzzle()
	 * 
	 * Description:
	 * 		Generates a new sudoku puzzle with passed in arguments from constructor
	 --------------------------------------------------------------------------------------*/
    public void generateNewPuzzle()
    {
    	resetBoard();
    	
    	/*---------------------------------------------------------------
        Call the recursive algorithm for generating the board.
        ---------------------------------------------------------------*/
        generateFullField( 1, 1 );
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		generateNewPuzzleBasedOnFile()
	 * 
	 * Description:
	 * 		Generates a new Sudoku puzzle based on passed in file data
	 --------------------------------------------------------------------------------------*/
    public void generatePuzzleBasedOnFile( String[] boardData )
    {
        resetBoard();
        
        /*---------------------------------------------------------------
        temporary board values to be copied into new board
        ---------------------------------------------------------------*/
        String[][] tempBoard = new String[ boardData.length ][ boardData.length ];
        
        /*---------------------------------------------------------------
        Seperate all board values by dilimiter
        ---------------------------------------------------------------*/
        for (int i = 0; i < boardData.length; i++)
        {
            tempBoard[i] = boardData[i].split( "[ ]+" );
        }
        
        /*---------------------------------------------------------------
        Get values from tempBoard and copy them into board
        ---------------------------------------------------------------*/
        for (int i = 0; i < tempBoard.length; i++)
        {
            for (int j = 0; j < tempBoard[i].length; j++)
            {
                board[i][j] = Character.getNumericValue( tempBoard[i][j].charAt(0) );
            }
        }
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		generateFullField()
	 * 
	 * Description:
	 * 		recursive algorithm for generating boards
	 --------------------------------------------------------------------------------------*/
    private void generateFullField( int row, int column ) 
    {
    	/*---------------------------------------------------------------
        If board is not filled
        ---------------------------------------------------------------*/
        if ( !isFilled( fieldSize, fieldSize ) ) 
        {
        	/*---------------------------------------------------------------
            while more numbers need to be tried
            ---------------------------------------------------------------*/
            while ( numberOfTriedNumbers( row, column ) < returnFieldSize() ) 
            {
                int candidate = 0;
                
                /*---------------------------------------------------------------
                Get a random index that has not been tried
                ---------------------------------------------------------------*/
                do 
                {
                    candidate = getRandomIndex();
                } 
                while ( numberHasBeenTried( candidate, row, column ) );
                
                /*---------------------------------------------------------------
                number field is available
                ---------------------------------------------------------------*/
                if (checkNumberField( candidate, row, column ) ) 
                {
                	/*---------------------------------------------------------------
                    set candidate into row and column
                    ---------------------------------------------------------------*/
                    set( candidate, row, column );
                    int[] nextCell = nextCell( row, column );
                    
                    if ( nextCell[0] <= fieldSize && nextCell[1] <= fieldSize ) 
                    {
                        generateFullField( nextCell[ 0 ], nextCell[ 1 ] );
                    }
                } 
                else 
                {
                    tryNumber( candidate, row, column );
                }
            }
            if ( !isFilled( fieldSize, fieldSize ) ) 
            {
                board[ row - 1 ][ column - 1 ] = 0;
                triedNumbers.get( row - 1 ).get( column - 1 ).clear();                
            }
        }
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		checkNumberField()
	 * 
	 * Description:
	 * 		Check if the number field at this particular row and column is valid for this 
	 * 		candidate number. To confirm, check the box, row, and column that this 
	 * 		number belongs to.
	 --------------------------------------------------------------------------------------*/
    private boolean checkNumberField( int number, int row, int column ) 
    {
        return ( checkNumberBox( number, row, column )
                && checkNumberRow( number, row )
                && checkNumberColumn( number, column )
                );
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		checkNumberBox()
	 * 
	 * Description:
	 * 		Check if the number can be placed in box
	 --------------------------------------------------------------------------------------*/
    public boolean checkNumberBox( int number, int row, int column ) 
    {
        int r = row;
        int c = column;
        
        
        if ( r % blockSize == 0 ) 
        {
            r -= blockSize - 1;
        } 
        else 
        {
            r = (r / blockSize) * blockSize + 1;
        }
        
        if (c % blockSize == 0) 
        {
            c -= blockSize - 1;
        } 
        else 
        {
            c = (c / blockSize) * blockSize + 1;
        }
        
        for (int i = r; i < r + blockSize; ++i) 
        {
            for (int j = c; j < c + blockSize; ++j) 
            {
                if (isFilled(i - 1 + 1, j - 1 + 1) && board[i - 1][j - 1] == number) 
                {
                    return false;
                }
            }
        }
        return true;
    }

    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		checkNumberRow()
	 * 
	 * Description:
	 * 		Checks if number is valid in row
	 --------------------------------------------------------------------------------------*/
    public boolean checkNumberRow(int number, int row) 
    {
        for (int i = 0; i < fieldSize; ++i) 
        {
            if (isFilled(row - 1 + 1, i + 1) && board[row - 1][i] == number) 
            {
                return false;
            }
        }
        return true;
    }

    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		checkNumberColumn()
	 * 
	 * Description:
	 * 		Checks if number is valid in column
	 --------------------------------------------------------------------------------------*/
    public boolean checkNumberColumn(int number, int column) 
    {
        for (int i = 0; i < fieldSize; ++i) 
        {
            if (isFilled(i + 1, column - 1 + 1) && board[i][column - 1] == number) 
            {
                return false;
            }
        }
        return true;
    }

    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		isFilled()
	 * 
	 * Description:
	 * 		Determines whether or not the row/column is filled
	 --------------------------------------------------------------------------------------*/
    private boolean isFilled( int row, int column ) 
    {
        if (board[ row - 1 ][ column - 1 ] == 0 )
        {
            return false;
        }

        return true;
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getRandomIndex()
	 * 
	 * Description:
	 * 		randomly generates an index
	 --------------------------------------------------------------------------------------*/
    private int getRandomIndex() 
    {
        return (int) ( ( Math.random() * 100 ) % fieldSize + 1 );
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		numberOfTriedNumbers()
	 * 
	 * Description:
	 * 		return size of list containing tried numbers
	 --------------------------------------------------------------------------------------*/
    private int numberOfTriedNumbers( int row, int column ) 
    {
        return triedNumbers.get( row - 1 ).get( column - 1 ).size();
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		numberHasBeenTried()
	 * 
	 * Description:
	 * 		Check if the number has been tried before at row and column
	 --------------------------------------------------------------------------------------*/
    public boolean numberHasBeenTried(int number, int row, int column) 
    {
        return triedNumbers.get( row - 1 ).get( column - 1 ).contains( number );
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		nextCell()
	 * 
	 * Description:
	 * 		return next cell to be examined by algorithm
	 --------------------------------------------------------------------------------------*/
    public int[] nextCell(int row, int column) 
    {
        int r = row;
        int c = column;
        
        if (c < fieldSize) 
        {
            ++c;
        }
        else 
        {
            c = 1;
            ++r;
        }
        
        int[] index = {r, c};
        
        return index;
    }
        
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		tryNumber()
	 * 
	 * Description:
	 * 		add number to list of tried numbers
	 --------------------------------------------------------------------------------------*/
    private void tryNumber( int number, int row, int column ) 
    {
        triedNumbers.get( row - 1 ).get( column - 1 ).add( number );
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		set()
	 *
	 * Description:
	 * 		set value of square and add value to numbers tried list
	 --------------------------------------------------------------------------------------*/
    public void set(int number, int row, int column) 
    {
        board[row - 1][column - 1] = number;
        triedNumbers.get(row - 1).get(column - 1).add(number);
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		returnBlockSize()
	 * 
	 * Description:
	 * 		return block size.
	 --------------------------------------------------------------------------------------*/
    public int returnBlockSize() 
    {
        return blockSize;
    }

    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		returnFieldSize()
	 * 
	 * Description:
	 * 		return field size.
	 --------------------------------------------------------------------------------------*/
    public int returnFieldSize() 
    {
        return fieldSize;
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		numberOfCells()
	 * 
	 * Description:
	 * 		return total # of cells.
	 --------------------------------------------------------------------------------------*/
    public int numberOfCells() 
    {
        return fieldSize * fieldSize;
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setGivenHelp()
	 * 
	 * Description:
	 * 		sets number of values initially revealed and number of hints given
	 *		depending on difficulty.
	 --------------------------------------------------------------------------------------*/
    public void setGivenHelp( Difficulty diff )
    {
    	if( board_size == BoardSize.NINE )
    	{
	    	switch( diff )
	    	{
	    	case EASY:
	    		numbers_shown = 35;
	    		hints = 5;
	    		break;
	    	case MEDIUM:
	    		hints = 4;
	    		numbers_shown = 30;
	    		break;
	    	case HARD:
	    		hints = 2;
	    		numbers_shown = 20;
	    		break;
	    	case EVIL:
	    		hints = 0;
	    		numbers_shown = 15;
	    		break;
	    	}
    	}
    	else
    	{
	    	switch( diff )
	    	{
	    	case EASY:
	    		hints = 8;
	    		numbers_shown = 65;
	    		break;
	    	case MEDIUM:
	    		hints = 7;
	    		numbers_shown = 60;
	    		break;
	    	case HARD:
	    		hints = 4;
	    		numbers_shown = 50;
	    		break;
	    	case EVIL:
	    		hints = 1;
	    		numbers_shown = 45;
	    		break;
	    	}
    	}
    	
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getBoard()
	 * 
	 * Description:
	 * 		returns 2d int sudoku board.
	 --------------------------------------------------------------------------------------*/
    public int[][] getBoard()
    {
    	return this.board;
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setBoard()
	 * 
	 * Description:
	 * 		set 2d int sudoku board to passed in argument
	 --------------------------------------------------------------------------------------*/
    
    public void setBoard( int[][] b )
    {
    	board = b;
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getHints()
	 * 
	 * Description:
	 * 		returns number of hints remaining
	 --------------------------------------------------------------------------------------*/
    public int getHints()
    {
    	return this.hints;
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setHints()
	 * 
	 * Description:
	 * 		sets # of hints to passed in argument.
	 --------------------------------------------------------------------------------------*/
    public void setHints( int h )
    {
        hints = h;
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getRadomValue()
	 * 
	 * Description:
	 * 		get a random value within field size.
	 --------------------------------------------------------------------------------------*/
    public int getRandomValue()
    {
    	return rand.nextInt( this.fieldSize );
    }
    
    /*---------------------------------------------------------------------------------------
     * Method:
     *      doAIMove()
     * 
     * Description:
     *      perform an AI player's move, using the hint algorithm
     --------------------------------------------------------------------------------------*/
    public boolean doAIMove( Cell[][] cells )
    {
        hints++;
        
        boolean check = false;
        
        /*-----------------------------------------------------------------
        Verify that there's actually a space left on the board.
        -----------------------------------------------------------------*/
        for ( int i = 0; i < cells.length; i++ )
        {
            for ( int j = 0; j < cells[i].length; j++ )
            {
                if ( cells[i][j].getPenField().equals("") )
                {
                    check = true;
                }
            }
        }
        
        /*-----------------------------------------------------------------
        If there are no spots left on the board, exit out of the AI routine
        -----------------------------------------------------------------*/
        if (!check)
        {
            return false;
        }
        
        /*-----------------------------------------------------------------
        The AI makes its move here
        -----------------------------------------------------------------*/
        return hint( cells, false );
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		hint()
	 * 
	 * Description:
	 * 		give user a hint by filling an empty cell, or incorrect cell
	 --------------------------------------------------------------------------------------*/
    public boolean hint( Cell[][] cells, boolean overridePlayerEntries )
    {	
    	boolean hint_given = false;
    	int i = getRandomValue();
    	int j = getRandomValue();
    	
    	if( hints == 0 )
    		return hint_given;
    	
    	if( win == true )
    		return hint_given;
    	
    	while ( !hint_given  )
    	{
    		if ( !cells[i][j].getPenField().equals( SudokuCommon.values[ board[i][j] ] ) )
    		{
    		    if ( overridePlayerEntries || cells[i][j].getPenField().equals("") )
    		    {
                    System.out.println(cells[i][j].getPenField());
                    cells[i][j].setPenField( board[i][j] );
                    cells[i][j].setLocked( true );
                    hint_given = true;
                    hints--;
    		    }
    		}
    		
        	i = getRandomValue();
        	j = getRandomValue();
    	}
    	
    	return hint_given;    	
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		populateBoard()
	 * 
	 * Description:
	 * 		fill in passed in 2d array of cells with board contents. "Randomly"
	 --------------------------------------------------------------------------------------*/
    public void populateBoard( Cell[][] cells )
    {
    	Random rand = new Random();
    	int cells_filled = 0;
    	int i = 0;
    	int j = 0;
    	while( cells_filled < numbers_shown )
    	{
    		i = rand.nextInt( this.fieldSize );
    		j = rand.nextInt( this.fieldSize );
    		
    		if( cells[i][j].getPenField().equals( "" ) )
    		{
    			cells[i][j].setPenField( board[i][j] );
    			cells[i][j].setLocked( true );
    			cells_filled++;
    		}
    	}
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		scoreBoard()
	 * 
	 * Description:
	 * 		score passed in 2d array of cells based on # of correct entries, rows,
	 * 		and columns. Factor in # of times erased and # of hints used to determine score
	 --------------------------------------------------------------------------------------*/
    public int scoreBoard( Cell[][] cells )
    {
    	int score = 0;
    	win = true;
    	for( int i = 0; i < fieldSize; i++ )
    	{
    		for( int j = 0; j < fieldSize; j++)
    		{
    			if( !cells[i][j].isLocked() )
    			{
    				
	    			if( cells[i][j].getPenField().equals( SudokuCommon.values[ board[i][j] ] ) )
	    			{
						score += 50;
	    			}
	    			else
	    			{
	    				win = false;
	    			}
    			}
    		}
    	}
    	return score;	
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		solve()
	 * 
	 * Description:
	 * 		fill in 2d array of cells with contents of board fully.
	 --------------------------------------------------------------------------------------*/
    public void solve( Cell[][] cells )
    {
    	for( int i = 0; i < fieldSize; i++ )
    		for( int j = 0; j < fieldSize; j++ )
    		{
    			cells[i][j].setPenField( board[i][j] );
    			cells[i][j].setLocked( true );
    		}
    	win = true;
    }
    
    /*---------------------------------------------------------------------------------------
	 * Method:
	 * 		isWin()
	 * 
	 * Description:
	 * 		Determine whether or not board is in winning state.
 	--------------------------------------------------------------------------------------*/
    public boolean isWin()
    {
    	return this.win;
    }
}