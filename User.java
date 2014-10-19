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


/*---------------------------------------------------------------------------------------
 							! More Work Needed to be Done !
 --------------------------------------------------------------------------------------*/

public class User implements Serializable {

	private static final long serialVersionUID = 2874928106330753461L;
	private long total_score;
	private String name;
	private String password_hash;
	private String password_salt;
	private int maps_completed;
	
	public User(String user_name, String pw_hash, String pw_salt) 
	{
		name = user_name;
		password_hash = pw_hash;
		password_salt = pw_salt;
		maps_completed = 0;
		total_score = 0;
	}
	
	public String get_name()
	{
		return name;
	}
	public long get_score()
	{
		return total_score;
	}
	public int get_maps_completed()
	{
		return maps_completed;
	}
	public String get_salt()
	{
		return password_salt;
	}
	public String get_pw_hash()
	{
		return password_hash;
	}
}
