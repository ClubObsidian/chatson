package com.clubobsidian.chatson.format;

public enum ChatsonTextDecoration {

	BOLD('l'),
	ITALIC('o'),
	MAGIC('k'),
	RESET('r'),
	STRIKETHROUGH('m'),
	UNDERLINE('n');
	
	private char charCode;
	private ChatsonTextDecoration(char colorCode)
	{
		this.charCode = colorCode;
	}
	
	public char getCharCode()
	{
		return this.charCode;
	}
	
	public TextDecoration getAPITextDecoration()
	{
		return TextDecoration.valueOf(this.name());
	}
	
	public ChatsonTextDecoration getByChar(char search)
	{
		for(ChatsonTextDecoration decoration : ChatsonTextDecoration.values())
		{
			if(decoration.getCharCode() == search)
			{
				return decoration;
			}
		}
		
		return null;
	}
}