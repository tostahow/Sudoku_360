/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		SudokuCommon.java
 * 
 * Description:
 * 		Contains commonly used variables throughout the Sudoku software
 * 		run time.
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/
import java.awt.Color;
import java.awt.Font;

public class SudokuCommon
{
	/*---------------------------------------------------------------------------------------
									Commonly Used Variables
	---------------------------------------------------------------------------------------*/
	public static final Font TITLE_FONT = 									// Font for Titles
			new Font("TimesNewRomans", Font.ITALIC, 64);		
	
	public static final Font TEXT_FONT = 									// Font for Texts
			new Font( "SansSerif", Font.ROMAN_BASELINE, 40 );
	public static final Font PEN_FONT =										//Font for Pen Fields
			new Font( "TimesNewRomans", Font.BOLD, 24 );
	public static final Font PENCIL_FONT =									//Font for Pencil Fields
			new Font( "SanSerif", Font.BOLD, 15 );
	
	public static final Color BACKGROUND_COLOR = Color.ORANGE;				// Background color of game.
	public static final Color BUTTON_ACTIVATED_COLOR = new Color(128,0,0);	// Guess field color
	public static final Color BUTTON_ACTIVATED_TEXT = Color.white;
	public static final Color COMPONENT_COLOR = new Color(128,0,0);			// Pencil field color
	public static final Color PEN_COLOR = Color.white;						// Pen color
	
	public static String[] values = 										// Values for Sudoku Board
	{"", "1", "2", "3", "4", 
		"5", "6", "7", "8", 
		"9", "A", "B", "C", 
		"D", "E", "F", "G" 
	};
	
	// All of the text for the information button
	public static final String INFO = "Welcome!\n"
			+ "The rules of Sudoku are as follows:\n"
			+ "1: Within every row must be exactly one of each number\n\t0-9 (A-G if in 16 X 16 mode)\n\n"
			+ "2: Within every collum must be exactly one of each number\n\t0-9 (A-G if in 16 X 16 mode)\n\n"
			+ "3: Within every 3X3 (4X4) sub-grid must be exactly one of\n\teach number 0-9 (A-G if in 16 X 16 mode)\n\n"
			+ "4: The game board will come pre-populated with some values.\n\n"
			+ "5: Size Option Descriptions:\n\t9x9 - Traditional Sudoku\n\t16x16 - Hexadoku (256 squares)\n\t\n\n"
			+ "6: Difficulty Option Descriptions:\n\t"
			+ 		"Easy - For the faint hearted sun devils.\n\t35 pre filled numbers and 5 hints\n\n\t"
			+ 		"Medium - Still tougher than UofA. \n\t30 pre filled numbers and 4 hints\n\n\t"
			+ 		"Hard - Things are heating up.\n\t20 pre filled numbers and 2 hints\n\n\t"
			+ 		"Evil - INFERNO.\n\t18 pre filled numbers and 1 hint\n\n"
			+ "7: Sparky's Aid - Sparky to the rescue!\n\t"
			+ "You can choose \"Play with AI\" and Sparky will help you\n\tfill in boards\n\n"
			+ "8: Menu Buttons Description:\n\t"
			+ 		"Play - Play new Sudoku game\n\n\t"
			+ 		"Stats - View Player statistics\n\n\t"
			+ 		"Info - View Information about game\n\n\t"
			+ 		"Exit - Exit Game.\n\n\t"
			+ 		"Load Board - Load Sudoku Board\n\n\t"
			+ 		"Resume Board - Resume saved Sudoku\n\n"
			+ "9: Game Buttons Description:\n\t"
			+ 		"Pen Mode - Fill in square\n\n\t"
			+ 		"Pencil Mode - Store guesses\n\n\t"
			+ 		"Eraser Mode - Erase filled squares\n\n\t"
			+ 		"Solve Now - Give up.\n\n\t"
			+ 		"Hint - Get a hint\n\n\t"
			+ 		"Save&Quit - Save board and exit\n\n\t"
			+		"Quit - quit game\n\n\t"
			+ 		"Pause - pause game\n\n\t"
			+		"Score! - update game score\n\n"
			+ "Your job as the puzzler is to derive the rest of the board.\nUse your mouse and keyboard to fill in cells in pen mode\n"
			+ "Use pencil mode to fill in guesses as to what might be the cells value\n(these will not impact your score)"
			+ "Good luck!";
	
}
