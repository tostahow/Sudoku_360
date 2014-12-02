/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		Password.java
 * 
 * Description:
 * 		Password is used to ensure that users' password are stored securely stored within the
 * 		Sudoku software.
 *
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import java.util.*;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.NoSuchProviderException;

public class Password {

	public Password() 
	{
		
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		generate_Salt()
	 * 
	 * Description:
	 * 		generates a random salt for each person which is used to create very secure
	 * 		pw hash.
	 --------------------------------------------------------------------------------------*/
	public static String generate_Salt() throws NoSuchAlgorithmException, NoSuchProviderException
	{
	    /*---------------------------------------------------------------
	    Generates a random salting string for each user. This salt
	    string increases the difficulty to find a unique user hash
	    exponentially.
        ---------------------------------------------------------------*/
		Random randomizer = SecureRandom.getInstance( "SHA1PRNG", "SUN" );	
		byte[] salt = new byte[ 16 ];
		randomizer.nextBytes( salt );
		return salt.toString();
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		get_SHA_256_secure_password
	 * 
	 * Description:
	 * 		Generate a securely hashed password using the SHA_256 algorithm
	 --------------------------------------------------------------------------------------*/
	public static String get_SHA_256_secure_password( char[] password, String salt_string )
	{
		String hashed_password = null;			// password after being hashed
		String temp_password = "";				// temporarily constructed password
		
	    /*---------------------------------------------------------------
        Try generating SHA-256 Hash. This is in place to protect the
        software from not being able to apply the specific hashing
        algorithm.
        ---------------------------------------------------------------*/
		try
		{
		    /*---------------------------------------------------------------
	        Construct a Temporary Password from passed in password array
	        ---------------------------------------------------------------*/
			for(int i = 0; i < password.length; i++)
				temp_password += password[ i ];
			
		    /*---------------------------------------------------------------
	        Set up message digest for SHA-256 standard. More secure than
	        SHA-1
	        ---------------------------------------------------------------*/
			MessageDigest md = MessageDigest.getInstance( "SHA-256" );
			md.update( salt_string.getBytes() );
			
		    /*---------------------------------------------------------------
	        Read in password bytes to prepare for being hashed
	        ---------------------------------------------------------------*/
			byte[] bytes = md.digest( temp_password.getBytes() );
			
		    /*---------------------------------------------------------------
	        Set up new string builder for hasing the entire password
	        ---------------------------------------------------------------*/
			StringBuilder sb = new StringBuilder();
			
		    /*---------------------------------------------------------------
	        Loop over all bytes and rebuild secure hash using string builder
	        ---------------------------------------------------------------*/
			for(int i = 0; i < bytes.length; i++ )
				{
				sb.append( Integer.toString( ( bytes[ i ] & 0xff) + 0x100, 16 ).substring( 1 ) );
				}
			
		    /*---------------------------------------------------------------
	        Set hashed password to the string built by sb, string builder
	        ---------------------------------------------------------------*/
			hashed_password = sb.toString();
		}
	    /*---------------------------------------------------------------
        Catch a no such algorithm exception and print stack trace
        ---------------------------------------------------------------*/
		catch( NoSuchAlgorithmException e )
		{
			
			e.printStackTrace();
			
		}
		
		return hashed_password;
	}

	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		verify_password
	 * 
	 * Description:
	 * 		Verifies if the user entered password, and random salt match the hashed password
	 * 		stored for the player object. 
	 --------------------------------------------------------------------------------------*/
	public static boolean verify_password( final User player, final char[] password )
	{
		boolean result = false;
		String storedPwHash = player.getPasswordHash();
		String salt = player.getSalt();
		
	    /*---------------------------------------------------------------
        Generate password hash using entered password, and unique user
        salt. The salt helps in making players accounts much harder to
        crack into.
        ---------------------------------------------------------------*/
		String generated_hash = get_SHA_256_secure_password( password, salt );
		

		if( ( generated_hash != null )  && ( storedPwHash != null )
		&&	( generated_hash.equals( storedPwHash ) )	
		  )
		{
		    /*---------------------------------------------------------------
	        If generated hash and storedPwHash match, verify password = true
	        ---------------------------------------------------------------*/
			result = true;
		}
		
		return result;
	}	


}
