/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Board.java
 * 
 * Description:
 * 		Arranges cells in manner such that they can be used in a Sudoku Puzzle
 * 		Assists with establishing functionality of Sudoku Cells
 * 
 * Author:
 * 		Travis Ostahowski and Xavier Tariq
-------------------------------------------------------------------------------------------------*/
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Board extends JPanel
{
	/*-----------------------------------------------------------------------------------
								Private Class Members
	-----------------------------------------------------------------------------------*/
	private static final long 
	serialVersionUID = 1L;			//Serialized Value
	
	private Observer listener;
	private Difficulty diff;		//Difficulty Values
	private BoardSize board_size;	//BoardSize Values
	private Cell[][] cells;			//Cells which will be used for game
	private JPanel[][] cell_square; //Panels to hold the cells in a Sudoku format
	private int cells_dim;			//3 or 4 depending on 9x9 or 16x16
	private int cell_square_dim;	// dimensions for panels
	private boolean pen_mode;		// flag for pen mode
	private boolean pencil_mode;	// flag for pencil mode
	private boolean eraser_mode;	// flag for eraser mode
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		Board - Constructor
	 * 
	 * Description:
	 * 		Set Dimension of Board and Generate all of the cells into a panel
	 --------------------------------------------------------------------------------------*/
	public Board( Observer listener, BoardSize b_size, Difficulty difficulty )
	{
	    this.listener = listener;
		this.setBackground( Color.BLACK );
		this.board_size = b_size;
		this.diff = difficulty;
		
		pen_mode = false;
		pencil_mode = false;
		eraser_mode = false;
		
		setDimensions();
		generateBoard();
	}
	
	/*---------------------------------------------------------------------------------------
     * Method:
     *       Board - Alternate Constructor for an already generated cell double array.
     * 
     * Description:
     *      Set Dimension of Board and Generate all of the cells into a panel using an already
     *      initialized cell array.
     --------------------------------------------------------------------------------------*/
    public Board( Observer listener, BoardSize b_size, Difficulty difficulty, Cell[][] c )
    {
        this.listener = listener;
        this.setBackground( Color.BLACK );
        this.board_size = b_size;
        this.diff = difficulty;
        
        pen_mode = false;
        pencil_mode = false;
        eraser_mode = false;
        
        setDimensions();
        generateBoard(c);
    }
    
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getCells()
	 * 
	 * Description:
	 * 		return 2d array of Cells
	 --------------------------------------------------------------------------------------*/
	public Cell[][] getCells()
	{
		return cells;
	}
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getDifficulty
	 * 
	 * Description:
	 * 		return Difficulty Setting
	 --------------------------------------------------------------------------------------*/
	public Difficulty getDifficulty()
	{
		return diff;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getBoardSize
	 * 
	 * Description:
	 * 		return BoardSize
	 --------------------------------------------------------------------------------------*/
	public BoardSize getBoardSize()
	{
		return board_size;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		generateBoard
	 * 
	 * Description:
	 * 		arrange the cells depending on the requested dimensions
	 --------------------------------------------------------------------------------------*/
	public void generateBoard()
	{
	    /*---------------------------------------------------------------
        Generate 2d Arrays for Cells and Panels for Holding Cells
        ---------------------------------------------------------------*/
		this.cells = new Cell[ this.cells_dim ][ this.cells_dim ];
		this.cell_square = new JPanel[ this.cell_square_dim ][ this.cell_square_dim ];
		
	    /*---------------------------------------------------------------
        Generate All Cells
        ---------------------------------------------------------------*/
		for( int i = 0; i < cells_dim; i++)
		{
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j] = new Cell( listener, board_size );
			}
			
		}
		
	    /*---------------------------------------------------------------
        Set Square of Cells attributes
        ---------------------------------------------------------------*/
		for( int i = 0; i < cell_square_dim ; i++ )
		{
			for( int j = 0; j < cell_square_dim; j++ )
			{
				cell_square[i][j] = new JPanel( new GridLayout( cell_square_dim, cell_square_dim ) );
				cell_square[i][j].setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
			}
		}
		
	    /*---------------------------------------------------------------
        Set layout for board as Grid for Sudoku Puzzle
        ---------------------------------------------------------------*/
		this.setLayout(new GridLayout( cell_square_dim, cell_square_dim, cell_square_dim + 2, cell_square_dim + 2));
		this.setBackground(Color.white);
		
	    /*---------------------------------------------------------------
        Add cells to panel to give the Sudoku Puzzle Look
        ---------------------------------------------------------------*/
		for( int i = 0; i < cell_square_dim; i++)
		{
			for( int j = 0; j < cell_square_dim; j++ )
			{
				for( int k = 0; k < cell_square_dim; k++ )
				{
					for( int l = 0; l < cell_square_dim; l++ )
					{
					    /*---------------------------------------------------------------
				        This format ensure that board is being generated in correct
				        row and column order. ie 	123456789
				        						 	123456789
				        ---------------------------------------------------------------*/
						cell_square[ i ][ j ].add( cells[ ( k+i*cell_square_dim ) ][ ( l+j*cell_square_dim ) ].getDisplayPanel() );
					}
						
				}
			    /*---------------------------------------------------------------
		        Add each Square of Cells (3x3 or 4x4) to the Board Grid
		        ---------------------------------------------------------------*/
				this.add( cell_square[ i ][ j ] );
			}
		}
		
	}
	
	 /*---------------------------------------------------------------------------------------
     * Method:
     *      generateBoard(Cell[][])
     * 
     * Description:
     *      arrange the cells depending on the requested dimensions, using the Cell array c
     *      as the cells array to be used.
     --------------------------------------------------------------------------------------*/
    public void generateBoard(Cell[][] c)
    {
        /*---------------------------------------------------------------
        Generate 2d Arrays for Cells and Panels for Holding Cells
        ---------------------------------------------------------------*/
        this.cells = new Cell[ this.cells_dim ][ this.cells_dim ];
        this.cell_square = new JPanel[ this.cell_square_dim ][ this.cell_square_dim ];
        
        /*---------------------------------------------------------------
        Generate all cells using the assigned Cell array
        ---------------------------------------------------------------*/
        for( int i = 0; i < cells_dim; i++)
        {
            for( int j = 0; j < cells_dim; j++)
            {
                cells[i][j] = new Cell( listener, board_size );                
                cells[i][j].setLocked(c[i][j].isLocked());
                cells[i][j].setPenFilled(c[i][j].isPenFilled());
                cells[i][j].setEraserCount(c[i][j].getEraserCount());
                cells[i][j].setCellType(board_size);

                // Init the pen field and its text.
				if (!c[i][j].getPenFieldObject().str.equals(""))
				{
					if (c[i][j].isLocked() == true)
					{
						cells[i][j].setLocked(false);		                

		                if (Character.isDigit(c[i][j].getPenFieldObject().str.charAt(0)))
		                	cells[i][j].setPenField( Integer.parseInt(c[i][j].getPenFieldObject().str) );
		                else
		                	cells[i][j].setPenField( Integer.parseInt( "" + ((Character.toUpperCase(c[i][j].getPenFieldObject().str.charAt(0))) - 65 + 10)) );
		                
		    			cells[i][j].setLocked(true);
					}
					else
					{
		                if (Character.isDigit(c[i][j].getPenFieldObject().str.charAt(0)))
		                	cells[i][j].setPenField( Integer.parseInt(c[i][j].getPenFieldObject().str) );
		                else
		                	cells[i][j].setPenField( Integer.parseInt( "" + ((Character.toUpperCase(c[i][j].getPenFieldObject().str.charAt(0))) - 65 + 10)) );
					}
				}
				
				// Init the pencil field and its text.
				if (!c[i][j].getPencilFieldObject().str.equals(""))
				{
					cells[i][j].setPencilField( c[i][j].getPencilFieldObject().str );
				}

                cells[i][j].getDisplayPanel().repaint();                
            }
        }
        
        /*---------------------------------------------------------------
        Set Square of Cells attributes
        ---------------------------------------------------------------*/
        for( int i = 0; i < cell_square_dim ; i++ )
        {
            for( int j = 0; j < cell_square_dim; j++ )
            {
                cell_square[i][j] = new JPanel( new GridLayout( cell_square_dim, cell_square_dim ) );
                cell_square[i][j].setBorder( BorderFactory.createLineBorder(Color.BLACK) );
            }
        }
        
        /*---------------------------------------------------------------
        Set layout for board as Grid for Sudoku Puzzle
        ---------------------------------------------------------------*/
        this.setLayout(new GridLayout( cell_square_dim, cell_square_dim, cell_square_dim + 2, cell_square_dim + 2));
        this.setBackground(Color.white);
        
        /*---------------------------------------------------------------
        Add cells to panel to give the Sudoku Puzzle Look
        ---------------------------------------------------------------*/
        for( int i = 0; i < cell_square_dim; i++)
        {
            for( int j = 0; j < cell_square_dim; j++ )
            {
                for( int k = 0; k < cell_square_dim; k++ )
                {
                    for( int l = 0; l < cell_square_dim; l++ )
                    {
                        cell_square[i][j].add( cells[k+i*cell_square_dim][l+j*cell_square_dim].getDisplayPanel() );
                    }
                        
                }
                /*---------------------------------------------------------------
                Add each Square of Cells to the Board Grid
                ---------------------------------------------------------------*/
                this.add( cell_square[i][j] );
            }
        }
        
    }
    
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setCells()
	 * 
	 * Description:
	 * 		set 2d array of Cells
	 --------------------------------------------------------------------------------------*/
	public void setCells(Cell[][] c)
	{
		cells = c;
	}
	
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setDimensions()
	 * 
	 * Description:
	 * 		set dimensions for the board
	 --------------------------------------------------------------------------------------*/
	public void setDimensions()
	{
		if( this.board_size == BoardSize.NINE )
		{
			this.cells_dim = 9;
			this.cell_square_dim = 3;
		}
		else if( this.board_size == BoardSize.SIXTEEN )
		{
			this.cells_dim = 16;
			this.cell_square_dim = 4;
		}
		else
		{
			System.out.println("System Critical Error: Invalid Board Size");
			System.exit(1);
		}

	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		enablePenMode()
	 * 
	 * Description:
	 * 		Loop through cells and enable pen mode for each cell.
	 --------------------------------------------------------------------------------------*/
	public void enablePenMode()
	{
	    /*---------------------------------------------------------------
        If pen_mode is set, save resources and return before altering
        all cells.
        ---------------------------------------------------------------*/
		if( pen_mode )
		{
			return;
		}
		
	    /*---------------------------------------------------------------
	    Pen mode not set, apply pen mode
        ---------------------------------------------------------------*/
		pen_mode = true;
		pencil_mode = false;
		eraser_mode = false;
		
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j].setPencilMode(false);
				cells[i][j].setPenMode(true);
				cells[i][j].setEraserMode(false);
			}
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		pauseBoard()
	 * 
	 * Description:
	 * 		Loop through cells and pause all cells
	 --------------------------------------------------------------------------------------*/
	public void pauseBoard( boolean flag )
	{	
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[ i ][ j ].setPaused( flag );
				
				if( !flag )
				{
					cells[i][j].setPencilMode(false);
					cells[i][j].setPenMode(true);
					cells[i][j].setEraserMode(false);
				}
			}
	}
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		disableBoard()
	 * 
	 * Description:
	 * 		Loop through cells and disable modes
	 --------------------------------------------------------------------------------------*/
	public void disableBoard()
	{
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j].setPencilMode(false);
				cells[i][j].setPenMode(false);
				cells[i][j].setEraserMode(false);
			}
	}
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		enablePencilMode()
	 * 
	 * Description:
	 * 		Loop through cells and enable pencil mode for each cell
	 --------------------------------------------------------------------------------------*/
	public void enablePencilMode()
	{
	    /*---------------------------------------------------------------
        If pencil_mode is set, save resources and return before altering
        all cells.
        ---------------------------------------------------------------*/
		if( pencil_mode )
		{
			return;
		}
		
	    /*---------------------------------------------------------------
	    Pencil mode not set, apply pencil mode
        ---------------------------------------------------------------*/
		pen_mode = false;
		pencil_mode = true;
		eraser_mode = false;
		
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j].setPencilMode(true);
				cells[i][j].setPenMode(false);
				cells[i][j].setEraserMode(false);
			}
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		enableEraserMode
	 * 
	 * Description:
	 * 		Loop through cells and enable eraser mode for each cell
	 --------------------------------------------------------------------------------------*/
	public void enableEraserMode()
	{
	    /*---------------------------------------------------------------
        If eraser_mode is set, save resources and return before altering
        all cells.
        ---------------------------------------------------------------*/
		if( eraser_mode )
		{
			return;
		}
		
	    /*---------------------------------------------------------------
	    Eraser mode not set, apply eraser mode
        ---------------------------------------------------------------*/
		pen_mode = false;
		pencil_mode = false;
		eraser_mode = true;
		
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j].setPencilMode( false );
				cells[i][j].setPenMode( false );
				cells[i][j].setEraserMode( true );
			}
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setWin
	 * 
	 * Description:
	 * 		set Cells to win state
	 --------------------------------------------------------------------------------------*/
	public void setWin()
	{
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j].setLocked( true );
			}
		this.setBackground( Color.white );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		clearBoard
	 * 
	 * Description:
	 * 		clears the entire board
	 --------------------------------------------------------------------------------------*/
	public void clearBoard()
	{
		this.setBackground( Color.black );
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j].setLocked( false );
				cells[i][j].clear();
			}
	}
	
}