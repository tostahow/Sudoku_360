/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		SudokuCommon.java
 * 
 * Description:
 * 		Contains commonly used variables and functions throughout the Sudoku software
 * 		run time.
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
			new Font( "TimesNewRomans", Font.ITALIC, 32 );
	public static final Font PENCIL_FONT =
			new Font( "SanSerif", Font.ITALIC, 18 );
	
	public static final Color BACKGROUND_COLOR = Color.ORANGE;	//Background color of game.
	public static final Color GUESS_FIELD_COLOR = Color.red;	// Guess field color
	public static final Color PENCIL_COLOR = Color.white;		//Pencil field color
	public static final Color PEN_COLOR = Color.BLACK;			//Pen color
	
	
}
