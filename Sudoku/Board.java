/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Board.java
 * 
 * Description:
 * 		Arranges cells in manner such that they can be used in a puzzle
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class Board extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private Difficulty diff;
	private BoardSize board_size;
	private Cell[][] cells;
	private JPanel[][] cell_square;
	private int cells_dim;
	private int cell_square_dim;
	
	public Board( BoardSize b_size, Difficulty difficulty )
	{
		this.setBackground( Color.BLACK );
		this.board_size = b_size;
		this.diff = difficulty;
		setDimensions();
		generateBoard();
	}
	
	public Cell[][] getCells()
	{
		return cells;
	}
	public Difficulty getDifficulty()
	{
		return diff;
	}
	
	public BoardSize getBoardSize()
	{
		return board_size;
	}
	
	public void generateBoard()
	{
		this.cells = new Cell[ this.cells_dim ][ this.cells_dim ];
		this.cell_square = new JPanel[ this.cell_square_dim ][ this.cell_square_dim ];
		int count = 1;
		for( int i = 0; i < cells_dim; i++)
		{
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j] = new Cell( board_size , count );
			}
			
		}
		
		//set Cell_Square attributes
		for( int i = 0; i < cell_square_dim ; i++ )
		{
			for( int j = 0; j < cell_square_dim; j++ )
			{
				cell_square[i][j] = new JPanel( new GridLayout( cell_square_dim, cell_square_dim ) );
				cell_square[i][j].setBorder( BorderFactory.createLineBorder(Color.BLACK) );
			}
		}
		
		this.setLayout(new GridLayout( cell_square_dim, cell_square_dim, cell_square_dim + 2, cell_square_dim + 2));
		
		for( int i = 0; i < cell_square_dim; i++)
		{
			for( int j = 0; j < cell_square_dim; j++ )
			{
				for( int k = 0; k < cell_square_dim; k++ )
				{
					for( int l = 0; l < cell_square_dim; l++ )
					{
						cell_square[i][j].add( cells[l+i*cell_square_dim][k+j*cell_square_dim] );
					}
						
				}
				this.add( cell_square[i][j] );
			}
		}
		
	}
	
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
	
	public void enablePenMode()
	{
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j].setPencilMode(false);
				cells[i][j].setPenMode(true);
				cells[i][j].setEraserMode(false);
			}
		this.repaint();
		this.setVisible(true);
	}
	
	public void enablePencilMode()
	{
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j].setPencilMode(true);
				cells[i][j].setPenMode(false);
				cells[i][j].setEraserMode(false);
			}
		this.repaint();
		this.setVisible(true);
	}
	
	public void enableEraserMode()
	{
		for( int i = 0; i < cells_dim; i++)
			for( int j = 0; j < cells_dim; j++)
			{
				cells[i][j].setPencilMode(false);
				cells[i][j].setPenMode(false);
				cells[i][j].setEraserMode(true);
			}
		this.repaint();
		this.setVisible(true);
	}
	
}