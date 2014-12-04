/*-------------------------------------------------------------------------------------------------
 * Document:
 *      SudokuBackEnd.java
 * 
 * Description:
 *      Contains all of the back end handling of data, such as puzzle generation, checking,
 *      solving and other features for the puzzles..
 *      
  * Author:
 * 		Xavier Tariq
-------------------------------------------------------------------------------------------------*/
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

// NOTE: Comments and code are WIP.
// Much of this algorithm is based/taken from here: http://stackoverflow.com/questions/15690254/how-to-generate-a-complete-sudoku-board-algorithm-error
// More alterations and updates are needed in order to suit the needs of this project.

public class SudokuBackEnd
{
    private int blockSize; // Length of each block.
    private int fieldSize; // Length of the entire board. It will be equal to blockSize^2.
    private int[][] board; // Double int array for the generated Sudoku board.
    private BoardSize board_size;
    private int numbers_shown;
    private int hints;
    private boolean win;
    private Random rand;
    
    // Double ArrayList of HashSet<Integer> to store the tried numbers. Only used during board generation.
    private ArrayList<ArrayList<HashSet<Integer>>> triedNumbers;
    
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
    
    public void setHints( Difficulty difficulty )
    {
    	
    }
    // Reset the board's state. Use this if it's assumed that the board will change dimensions.    
    private void initBaseBoard()
    {
    	if( board_size == BoardSize.NINE)
    		blockSize = 3;
    	else
    		blockSize = 4;
    	
    	fieldSize = blockSize*blockSize;
    	
        board = new int[fieldSize][fieldSize];
        triedNumbers = new ArrayList<ArrayList<HashSet<Integer>>>();
        
        for (int i = 0; i < fieldSize; i++)
        {
            triedNumbers.add(new ArrayList<HashSet<Integer>>());
            
            for (int j = 0; j < fieldSize; j++)
            {                
                board[i][j] = 0;
                triedNumbers.get(i).add(new HashSet<Integer>());
            }
        }     
        
    }
    
    // Reset the board's state. Use this if it's assumed that the board will not change dimensions.    
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
    
    // Debug function for printing the answer board's content.
    public void printBoardContents()
    {
        for (int i = 0; i < fieldSize; i++)
        {
            for (int j = 0; j < fieldSize; j++)
            {
                System.out.print(board[i][j] + " ");
            }
            
            System.out.println();
        }
    }
    
    // Generate a new Sudoku puzzle using the passed parameter as the length of each square of the new board.
    public void generateNewPuzzle()
    {
    	resetBoard();
        // Call the recursive algorithm for generating the board.
        generateFullField(1, 1);
    }
    
    public void generatePuzzleBasedOnFile(String[] boardData)
    {
        resetBoard();
        
        String[][] tempBoard = new String[boardData.length][boardData.length];
        
        for (int i = 0; i < boardData.length; i++)
        {
            tempBoard[i] = boardData[i].split("[ ]+");
        }
        
        for (int i = 0; i < tempBoard.length; i++)
        {
            for (int j = 0; j < tempBoard[i].length; j++)
            {
                board[i][j] = Character.getNumericValue(tempBoard[i][j].charAt(0));
            }
        }
    }
    
    // Recursive algorithm for generation of the entire board.
    private void generateFullField(int row, int column) 
    {
        if (!isFilled(fieldSize, fieldSize)) 
        {
            while (numberOfTriedNumbers(row, column) < returnFieldSize()) 
            {
                int candidate = 0;
                
                do 
                {
                    candidate = getRandomIndex();
                } 
                while (numberHasBeenTried(candidate, row, column));
                
                if (checkNumberField(candidate, row, column)) 
                {
                    set(candidate, row, column);
                    int[] nextCell = nextCell(row, column);
                    if (nextCell[0] <= fieldSize && nextCell[1] <= fieldSize) 
                    {
                        generateFullField(nextCell[0], nextCell[1]);
                    }
                } 
                else 
                {
                    tryNumber(candidate, row, column);
                }
            }
            if (!isFilled(fieldSize, fieldSize)) 
            {
                //field.reset(row, column);
                board[row - 1][column - 1] = 0;
                triedNumbers.get(row - 1).get(column - 1).clear();                
            }
        }
    }
    
    // Check if the number field at this particular row and column is valid for this candidate number.
    // To confirm, check the box, row, and column that this number belongs to.
    private boolean checkNumberField(int number, int row, int column) 
    {
        return (checkNumberBox(number, row, column)
                && checkNumberRow(number, row)
                && checkNumberColumn(number, column));
    }
    
    // Verifies if the number is valid in this number box.
    public boolean checkNumberBox(int number, int row, int column) 
    {
        int r = row;
        int c = column;
        
        if (r % blockSize == 0) 
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

    // Verify if the number is valid in this row.
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

    
    // Verify if the number is valid in this column.
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

    // Check if this board square is filled or not.
    private boolean isFilled(int row, int column) 
    {
        if (board[row - 1][column - 1] == 0)
        {
            return false;
        }

        return true;
    }
    
    // Randomly generate a number. Used for the board generation.
    private int getRandomIndex() 
    {
        return (int) (Math.random() * 100) % fieldSize + 1;
    }
    
    // Return the size of the list of tried numbers for this square.
    private int numberOfTriedNumbers(int row, int column) 
    {
        //return field[row - 1][column - 1].numberOfTried();
        return triedNumbers.get(row - 1).get(column - 1).size();
    }
    
    // Check to see if this number has been tried before in this square.
    public boolean numberHasBeenTried(int number, int row, int column) 
    {
        //return field[row - 1][column - 1].isTried(number);
        return triedNumbers.get(row - 1).get(column - 1).contains(number);
    }
    
    // Return the next cell to be acted upon by the puzzle generation algorithm.
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
        
    // Add the number to the list of tried numbers of this square.
    private void tryNumber(int number, int row, int column) 
    {
        triedNumbers.get(row - 1).get(column - 1).add(number);
    }
    
    // Set the value of this square to be a certain number. Then, add this same number to the list of tried 
    // numbers.
    public void set(int number, int row, int column) 
    {
        board[row - 1][column - 1] = number;
        triedNumbers.get(row - 1).get(column - 1).add(number);
    }
    
    public int returnBlockSize() 
    {
        return blockSize;
    }

    public int returnFieldSize() 
    {
        return fieldSize;
    }

    public int numberOfCells() 
    {
        return fieldSize * fieldSize;
    }
    
    public void setGivenHelp( Difficulty diff )
    {
    	if( board_size == BoardSize.NINE )
    	{
	    	switch(diff)
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
	    	switch(diff)
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
    
    public int[][] getBoard()
    {
    	return this.board;
    }
    
    public void setBoard(int[][] b)
    {
    	board = b;
    }
    
    public int getHints()
    {
    	return this.hints;
    }
    
    public void setHints(int h)
    {
        hints = h;
    }
    
    public int getRandomValue()
    {
    	return rand.nextInt( this.fieldSize );
    }
    
    public boolean hint( Cell[][] cells )
    {	
    	boolean hint_given = false;
    	int i = getRandomValue();
    	int j = getRandomValue();
    	
    	if( hints == 0 )
    		return hint_given;
    	
    	if( win == true )
    		return hint_given;
    	
    	while( !hint_given  )
    	{
    		if( !cells[i][j].getPenField().equals(SudokuCommon.values[board[i][j]]) )
    		{
    			cells[i][j].setPenField( board[i][j] );
    			cells[i][j].setLocked(true);
    			hint_given = true;
    			hints--;
    		}
        	i = getRandomValue();
        	j = getRandomValue();
    	}
    	return hint_given;
    	
    }
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
    		
    		if( cells[i][j].getPenField().equals("") )
    		{
    			cells[i][j].setPenField( board[i][j] );
    			cells[i][j].setLocked(true);
    			cells_filled++;
    		}
    	}
    }
    
    public int scoreBoard( Cell[][] cells )
    {
    	int score = 0;
    	win = true;
    	for( int i = 0; i < fieldSize; i++ )
    	{
    		for( int j = 0; j < fieldSize; j++)
    		{
    			if( cells[i][j].getPenField().equals(SudokuCommon.values[board[i][j]]) )
    			{
    				if( !cells[i][j].isLocked() )
    					score += 50;
    			}
    			else
    			{
    				win = false;
    			}
    		}
    	}
    	return score;	
    }
    
    public void solve( Cell[][] cells )
    {
    	for( int i = 0; i < fieldSize; i++ )
    		for( int j = 0; j < fieldSize; j++ )
    		{
    			cells[i][j].setPenField( board[i][j] );
    			cells[i][j].setLocked(true);
    		}
    	win = true;
    }
    
    public boolean isWin()
    {
    	return this.win;
    }
}