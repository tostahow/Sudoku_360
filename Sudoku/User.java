/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		User.java
 * 
 * Description:
 * 		User is used to store information for specific users' achievements and statistics
-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import java.io.*;

public class User implements Serializable 
{
	/*-----------------------------------------------------------------------------------
									Private Class Members
	-----------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;	// serial ID
	
	private long total_score;							// total user score
	private long time_played;							// total time played
	private String name;								// user name
	private String password_hash;						// generated password hash
	private String password_salt;						// unique salt for user
	private int maps_completed;							// Sudoku maps completed
	private int size3_maps_completed;					// 9x9 Sudoku maps completed
	private int size4_maps_completed;					// 16x16 Sudoku maps completed
	
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		User() - constructor
	 * 
	 * Description:
	 * 		Creates new user with name and stores the password hash and salt for user
	 * 		so that user can log in again
	 --------------------------------------------------------------------------------------*/
	public User(String user_name, String pw_hash, String pw_salt) 
	{
		name = user_name;
		password_hash = pw_hash;
		password_salt = pw_salt;
		maps_completed = 0;
		total_score = 0;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getName()
	 * 
	 * Description:
	 * 		return name
	 --------------------------------------------------------------------------------------*/
	public String getName()
	{
		return name;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getScore()
	 * 
	 * Description:
	 * 		return total score
	 --------------------------------------------------------------------------------------*/
	public long getScore()
	{
		return total_score;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getMapsCompleted()
	 * 
	 * Description:
	 * 		return number of maps completed
	 --------------------------------------------------------------------------------------*/
	public int getMapsCompleted()
	{
		return maps_completed;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getMapsCompleted()
	 * 
	 * Description:
	 * 		return number of maps completed with the particular BoardSize
	 --------------------------------------------------------------------------------------*/
	public int getMapsCompleted(BoardSize b)
	{
		if (b == BoardSize.NINE)
		{
			return size3_maps_completed;
		}
		else if (b == BoardSize.SIXTEEN)
		{
			return size4_maps_completed;
		}
		
		return maps_completed;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getSalt()
	 * 
	 * Description:
	 * 		get uniquely generated salt for user to check if hashed passwords match
	 --------------------------------------------------------------------------------------*/
	public String getSalt()
	{
		return password_salt;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getPasswordHash()
	 * 
	 * Description:
	 * 		get stored hash of users password
	 --------------------------------------------------------------------------------------*/
	public String getPasswordHash()
	{
		return password_hash;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		setScore()
	 * 
	 * Description:
	 * 		update total score
	 --------------------------------------------------------------------------------------*/
	public void setScore( int score )
	{
		total_score += score;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		getTimePlayed()
	 * 
	 * Description:
	 * 		gets total time user has played for
	 --------------------------------------------------------------------------------------*/
	public long getTimePlayed()
	{
		return time_played;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		incrementMapsCompleted
	 * 
	 * Description:
	 * 		update the # of maps completed
	 --------------------------------------------------------------------------------------*/
	public void incrementMapsCompleted()
	{
		maps_completed++;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		incrementMapsCompleted
	 * 
	 * Description:
	 * 		update the # of maps completed
	 --------------------------------------------------------------------------------------*/
	public void incrementMapsCompleted(BoardSize b)
	{
		if (b == BoardSize.NINE)
			size3_maps_completed++;
		else if (b == BoardSize.SIXTEEN)
			size4_maps_completed++;
		
		maps_completed++;
	}
	
	public void updateTimePlayed( long time )
	{
		time_played += time;
	}
}
