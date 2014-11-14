/*-------------------------------------------------------------------------------------------------
 * Document:
 * 		TextFieldLimit.java
 * 
 * Description:
 * 		TextFieldLimit is used to ensure that Users do not enter string literals that do not
 * 		correspond to specified limits.
 * 
 * 
 * Author:
 * 		Travis Ostahowski
-------------------------------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------------------------------
 										Imports
-------------------------------------------------------------------------------------------------*/
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;



public class TextFieldLimit extends PlainDocument 
{
	private static final long serialVersionUID = 1L;
	private int max_length;		// max limit of field
	private FieldType field_type; // Enum for username, password, cell9, cell16
	private String[] invalid_username_characters = 
		{ // List of Invalid Username Character
			" ", "*", "/", "}", "]",
		   "\\", "|", ",", "?",".",
			">", "<", ";", ":", "=",
			"+", "-", "_", ")", "(",
			"*", "&", "^", "%", "$",
			"#", "@", "!", "~", "`", "{","["
			
		};
	
	private String valid_cell9_characters = "123456789";
	private String valid_cell16_characters = "123456789abcdefgABCDEFG";
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		TextFieldLimit( int )
	 * 
	 * Description:
	 * 		Constructor that sets max length to the passed in limit.
	 --------------------------------------------------------------------------------------*/
	TextFieldLimit( int limit, FieldType type )
	{
		super();
		this.max_length = limit;
		this.field_type = type;
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		insertString
	 * 
	 * Description:
	 * 		Handles the input of strings to the field. This function ensures that strings
	 * 		cannot exceed specified max length.
	 --------------------------------------------------------------------------------------*/
	public void insertString( int offset, String str, AttributeSet attr ) throws BadLocationException
	{
		if( str == null )
			return;
		
		if( field_type == FieldType.USERNAME)
		{	
			if(  ( getLength() + str.length() ) <= max_length && userNameValid( str ) )
				super.insertString(offset, str, attr);
		}
		
		if( field_type == FieldType.PASSWORD )
		{
			if( (getLength() + str.length() ) <= max_length )
				super.insertString(offset, str, attr);
		}
		
		if( field_type == FieldType.CELL9 || field_type == FieldType.CELL16 )
		{
			if( (getLength() + str.length() ) <= max_length && isValidCellEntry( str ) )
				super.insertString(offset, str, attr);
		}
		
	}
	
	/*---------------------------------------------------------------------------------------
	 * Method:
	 * 		userNameValid
	 * 
	 * Description:
	 * 		ensures that entered user names do not contain any undesired characters.
	 --------------------------------------------------------------------------------------*/
	
	private boolean userNameValid( String str )
	{
		for( int i= 0; i < invalid_username_characters.length; i++ )
		{
			if( str.contains(invalid_username_characters[i]) )
				return false;
		}
		
		return true;
	}
	
	
	private boolean isValidCellEntry( String str )
	{
		if( this.field_type == FieldType.CELL9 )
		{
			for(int i = 0; i < str.length(); i ++ )
			{
				if( !this.valid_cell9_characters.contains( "" + str.charAt(i) ) )
					return false;
			}
		}
		else
		{
			for(int i = 0; i < str.length(); i ++ )
			{
				if( !this.valid_cell16_characters.contains( "" + str.charAt(i) ) )
					return false;
			}
		}
			
		
		return true;
	}
	
	
}
