/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		SudokuCommon.java
 *
 * Description:
 * 		Contains commonly used variables and functions throughout the Sudoku software
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
									Final Static Variables
	---------------------------------------------------------------------------------------*/

	public static final Font TITLE_FONT = 						//Font for Titles
			new Font("TimesNewRomans", Font.ITALIC, 85);

	public static final Font TEXT_FONT = 						//Font for Texts
			new Font( "SansSerif", Font.ROMAN_BASELINE, 50 );
	public static final Font PEN_FONT =
			new Font( "TimesNewRomans", Font.BOLD, 24 );
	public static final Font PENCIL_FONT =
			new Font( "SanSerif", Font.BOLD, 15 );


	public static final Color BACKGROUND_COLOR = Color.ORANGE;	//Background color of game.
	public static final Color BUTTON_ACTIVATED_COLOR = new Color(128,0,0);		// Guess field color
	public static final Color BUTTON_ACTIVATED_TEXT = Color.white;
	public static final Color COMPONENT_COLOR = new Color(128,0,0);		//Pencil field color
	public static final Color PEN_COLOR = Color.white;			//Pen color

	public static String[] values = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g" };

	public static final String INFO = "Welcome!\n"
									+ "The rules of Sudoku are as follows:\n"
									+ "1: Within every row must be exactly one of each number\n\t0-9 (A-G if in 16 X 16 mode)\n"
									+ "2: Within every collum must be exactly one of each number\n\t0-9 (A-G if in 16 X 16 mode)\n"
									+ "3: Within every 3X3 (4X4) sub-grid must be exactly one of\n\teach number 0-9 (A-G if in 16 X 16 mode)\n"
									+ "4: The game board will come pre-populated with some values.\n"
									+ "Your job as the puzzler is to derive the rest of the board.\nUse your mouse and keyboard to fill in cells in pen mode\n"
									+ "Use pencil mode to fill in guesses as to what might be the cells value\n(these will not impact your score)"
									+ "Good luck!";
}
