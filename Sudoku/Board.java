/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Board.java
 * 
 * Description:
 * 		Arranges cells in manner such that they can be used in a Sudoku Puzzle
 * 		Assists with establishing functionality of Sudoku Cells
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class Board extends JPanel
{
	/*-----------------------------------------------------------------------------------
								Private Class Members
	-----------------------------------------------------------------------------------*/
	private static final long 
	serialVersionUID = 1L;			//Serialized Value
	
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
	public Board( BoardSize b_size, Difficulty difficulty )
	{
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
    public Board( BoardSize b_size, Difficulty difficulty, Cell[][] c )
    {
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
				cells[i][j] = new Cell( board_size );
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
						cell_square[i][j].add( cells[k+i*cell_square_dim][l+j*cell_square_dim] );
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
        Generate All Cells
        ---------------------------------------------------------------*/
        for( int i = 0; i < cells_dim; i++)
        {
            for( int j = 0; j < cells_dim; j++)
            {
                cells[i][j] = new Cell( board_size );                
                //cells[i][j].pen_mode = c[i][j].pen_mode;
                //cells[i][j].pencil_mode = c[i][j].pencil_mode;
                //cells[i][j].eraser_mode = c[i][j].eraser_mode;
                cells[i][j].setLocked(c[i][j].isLocked());
                cells[i][j].setPenFilled(c[i][j].isPenFilled());
                cells[i][j].setEraserCount(c[i][j].getEraserCount());
                cells[i][j].cell_type = c[i][j].cell_type;

                // Init the pen field and its text.
				if (!c[i][j].pen_field.str.equals(""))
				{
					if (c[i][j].isLocked() == true)
					{
						cells[i][j].setLocked(false);		                

		                if (Character.isDigit(c[i][j].pen_field.str.charAt(0)))
		                	cells[i][j].setPenField( Integer.parseInt(c[i][j].pen_field.str) );
		                else
		                	cells[i][j].setPenField( Integer.parseInt( "" + ((Character.toUpperCase(c[i][j].pen_field.str.charAt(0))) - 65 + 10)) );
		                
		    			cells[i][j].setLocked(true);
					}
					else
					{
		                if (Character.isDigit(c[i][j].pen_field.str.charAt(0)))
		                	cells[i][j].setPenField( Integer.parseInt(c[i][j].pen_field.str) );
		                else
		                	cells[i][j].setPenField( Integer.parseInt( "" + ((Character.toUpperCase(c[i][j].pen_field.str.charAt(0))) - 65 + 10)) );
		                
						//cells[i][j].setPenField( Integer.parseInt(c[i][j].pen_field.str) );
					}
				}
				
				// Init the pencil field and its text.
				if (!c[i][j].pencil_field.str.equals(""))
				{
					cells[i][j].setPencilField( c[i][j].pencil_field.str );
				}

                
                /*
                // Getting hints.
                if (!c[i][j].pen_field.str.equals(""))
                {
                	cells[i][j].setPenField( Integer.parseInt(c[i][j].pen_field.str) );
                	cells[i][j].setLocked(true);
                }
                */
                
    			
    			//cells[i][j].setLocked(true);
                
                /*
                cells[i][j].pen_mode = c[i][j].pen_mode;
                cells[i][j].pencil_mode = c[i][j].pencil_mode;
                cells[i][j].eraser_mode = c[i][j].eraser_mode;
                cells[i][j].locked = c[i][j].locked;
                cells[i][j].pen_filled = c[i][j].pen_filled;
                cells[i][j].eraser_count = c[i][j].eraser_count;
                
                if (c[i][j].pen_field.font == SudokuCommon.PEN_FONT)
                {
                    cells[i][j].pen_field = new CellField( "", true );
                }
                else
                {
                    cells[i][j].pen_field = new CellField( "", false );
                }
                
                if (c[i][j].pencil_field.font == SudokuCommon.PEN_FONT)
                {
                    cells[i][j].pencil_field = new CellField( "", true );
                }
                else
                {
                    cells[i][j].pencil_field = new CellField( "", false );
                }    
                
                //cells[i][j].pen_field = c[i][j].pen_field;
                cells[i][j].pen_field.str = c[i][j].pen_field.str;
                cells[i][j].pen_field.bgColor = c[i][j].pen_field.bgColor;
                cells[i][j].pen_field.fgColor = c[i][j].pen_field.fgColor;
                cells[i][j].pen_field.font = c[i][j].pen_field.font;
                cells[i][j].pen_field.editable = c[i][j].pen_field.editable;
                
                
                
                cells[i][j].pencil_field = c[i][j].pencil_field;
                
                cells[i][j].cell_type = c[i][j].cell_type;
                
                */
                
                
                
                //System.out.print("|" + c[i][j].pen_field.str + "");
                //System.out.print(c[i][j].pen_field.getText() + "| ");
                
                //cells[i][j] = c[i][j];
                
                //cells[i][j].pen_field.setText(cells[i][j].pen_field.str); //PROB HERE, not accepting new texts
                //cells[i][j].setPenField(Integer.parseInt(cells[i][j].pen_field.str));
                //cells[i][j].setLocked(!cells[i][j].pen_field.editable);
                
                cells[i][j].repaint();
                
                
                //System.out.print("|" + c[i][j].pen_field.str + "");
                //System.out.print(cells[i][j].pen_field.getText() + "| ");
                
            }
            
           // System.out.print("\n");
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
                        cell_square[i][j].add( cells[k+i*cell_square_dim][l+j*cell_square_dim] );
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
		
		pen_mode = false;
		pencil_mode = false;
		eraser_mode = true;
		
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j].setPencilMode(false);
				cells[i][j].setPenMode(false);
				cells[i][j].setEraserMode(true);
			}
	}
	
	public void setWin()
	{
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j].setLocked(true);
			}
		this.setBackground(Color.white);
	}
	
	public void clearBoard()
	{
		this.setBackground(Color.black);
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j].setLocked(false);
				cells[i][j].clear();
			}
	}
	
}