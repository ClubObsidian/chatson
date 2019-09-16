package com.clubobsidian.chatson.format;

public enum ChatsonTextSpecial {

	HOVER('h'),
	RUN_COMMAND('q'),
	SUGGEST_COMMAND('w'),
	URL('u');
	
	
	private char charCode;
	private ChatsonTextSpecial(char charCode)
	{
		this.charCode = charCode;
	}
	
	public char getCharCode()
	{
		return this.charCode;
	}
	
	public static ChatsonTextSpecial getByChar(char search)
	{
		for(ChatsonTextSpecial decoration : ChatsonTextSpecial.values())
		{
			if(decoration.getCharCode() == search)
			{
				return decoration;
			}
		}
		
		return null;
	}
}
