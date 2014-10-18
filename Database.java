/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Database.java
 * 
 * Description:
 * 		Database handles the loading, accessing, and writing of user information within the
 * 		software.
-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import java.util.ArrayList;
import java.io.*;

public class Database
{
	private static ArrayList< User > database = new ArrayList< User >();
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		Database() - contstructor
	 * 
	 * Description:
	 * 		Yet to have definition - may not need it.
	 --------------------------------------------------------------------------------------*/
	public Database()
	{
	}
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		add()
	 * 
	 * Description:
	 * 		Add a user into the static ArrayList
	 --------------------------------------------------------------------------------------*/
	public static void add( User user_object )
	{
        database.add( user_object );
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		display()
	 * 
	 * Description:
	 * 		Display all statistical information stored for each user.
	 --------------------------------------------------------------------------------------*/
	public static void display()
	{
		if(database.size() == 0)
			return;
		
		for(int i = 0; i < database.size(); i++)
			{
			System.out.print( database.get( i ).get_name() );
			}
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		find_user()
	 * 
	 * Description:
	 * 		returns the User object that matches the user_name
	 --------------------------------------------------------------------------------------*/
	public static User find_user( String user_name )
	{
		if( database == null )
		{
			return null;
		}
		
		for(int i = 0; i < database.size(); i++)
		{
			if( user_name.equals( database.get(i).get_name() ) )
			{
				return database.get(i);
			}
		}
		
		return null;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		read_database()
	 * 
	 * Description:
	 * 		Contains all of the functionality so that the software can read serialized
	 * 		user objects in from a file that contains the list of users that are registered 
	 * 		are stored locally.
	 --------------------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public static void read_database( File file )
	{
		try
			{
			FileInputStream file_input = new FileInputStream( file );
			ObjectInputStream object_input = new ObjectInputStream( file_input );
			try
				{
					database = (ArrayList< User >)object_input.readObject();
				}
			catch( ClassNotFoundException e )
				{
				System.out.println("Error encountered during input serialization");
				e.printStackTrace();
				}
			
			object_input.close();
			file_input.close();
			}
		catch( IOException e )
			{
			System.out.println("Error encountered while trying to read users.data");
			}   
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		write_database()
	 * 
	 * Description:
	 * 		Contains all of the functionality so that the software can write serialized
	 * 		user objects out to a file so that the list of users that are registered are
	 * 		stored locally.
	 --------------------------------------------------------------------------------------*/
	public static void write_database( File file )
	{
		try
		{
		FileOutputStream file_out = new FileOutputStream( file );
		ObjectOutputStream object_out = new ObjectOutputStream( file_out );
		
		object_out.writeObject( database );
		
		object_out.close();
		file_out.close();
		
		}
	catch( IOException e )
		{
		System.out.println("Error encountered while trying to write to users.data");
		}
	}

}
