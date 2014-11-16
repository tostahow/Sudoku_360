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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.Timer;

public class SudokuDisplay extends Observable implements ActionListener
{	
	private BoardSize board_size;
	private Difficulty difficulty;
	private SudokuBackEnd back_end;
	private Board board;
	
	
	private JButton pencil_button;
	private JButton eraser_button;
	private JButton pen_button;
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
	
	public SudokuDisplay( Observer listener, BoardSize size, Difficulty difficulty )
	{
		addObserver( listener );
		board_size = size;
		this.difficulty = difficulty;
		
		display_panel = new JPanel();
		display_panel.setLayout( new BorderLayout() );
		
		loadStatPanel();
		loadButtonPanels();
		loadBoardPanel();
	}
	
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
		score.setFont(SudokuCommon.PEN_FONT);
		time_label.setFont( SudokuCommon.PEN_FONT );
		time.setFont(SudokuCommon.PEN_FONT);
		score_button.setFont( SudokuCommon.PEN_FONT );
		
		score_button.addActionListener(this);
		info_panel.add( score_label );
		info_panel.add( score );
		info_panel.add( time_label );
		info_panel.add( time );
		info_panel.add( score_button );
		
		display_panel.add(info_panel, BorderLayout.NORTH );
	}
	
	public void loadButtonPanels()
	{
		GridLayout button_grid = new GridLayout(1,6);
		button_panel = new JPanel( button_grid );
		
		pencil_button = new JButton("Pencil Mode");
		pen_button = new JButton("Pen Mode");
		quit_button = new JButton("Quit");
		score_button = new JButton("Solve Now");
		back_button = new JButton("Main Menu");
		eraser_button = new JButton("Eraser Mode");
		
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
	
	public void loadBoardPanel()
	{
		board = new Board( this.board_size, this.difficulty );
		board.enablePenMode();
		activate(pen_button);
		display_panel.add( board, BorderLayout.CENTER );
	}
	
	// restart timer
	
	public JPanel getGamePanel()
	{
		return display_panel;
	}
	
	public void activate( JButton mode_button )
	{
		mode_button.setBackground( SudokuCommon.BUTTON_ACTIVATED_COLOR );
		mode_button.setForeground( SudokuCommon.BUTTON_ACTIVATED_TEXT );
	}
	
	public void deactivateModes()
	{
		pen_button.setBackground( null );
		pen_button.setForeground( null );
		pencil_button.setBackground( null );
		pencil_button.setForeground( null );
		eraser_button.setBackground( null );
		eraser_button.setForeground( null );
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == pen_button )
		{
			deactivateModes();
			activate(pen_button);
			board.enablePenMode();
			display_panel.repaint();
			display_panel.setVisible(true);
		}
		if( e.getSource() == pencil_button )
		{
			deactivateModes();
			activate( pencil_button );
			board.enablePencilMode();
			
			display_panel.repaint();
			display_panel.setVisible(true);
		}
		if( e.getSource() == eraser_button )
		{
			deactivateModes();
			activate(eraser_button);
			board.enableEraserMode();
			display_panel.repaint();
			display_panel.setVisible(true);
		}
		if( e.getSource() == quit_button )
		{
			
		}
		if( e.getSource() == back_button )
		{
			
		}
		
	}
}
