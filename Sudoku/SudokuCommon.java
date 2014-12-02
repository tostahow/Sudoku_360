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
			new Font("TimesNewRomans", Font.ITALIC, 85);		
	
	public static final Font TEXT_FONT = 									// Font for Texts
			new Font( "SansSerif", Font.ROMAN_BASELINE, 50 );
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
	
}
