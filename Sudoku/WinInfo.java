/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		WinInfo.java
 * 
 * Description:
 *      Class that handles information passing once a Sudoku round is completed
 * 
 * Author:
 * 		Xavier Tariq
-------------------------------------------------------------------------------------------------*/

public class WinInfo 
{
	public boolean didWin;
	public BoardSize board_size;
	
	public WinInfo(boolean win, BoardSize b)
	{
		didWin = win;
		board_size = b;
	}
}