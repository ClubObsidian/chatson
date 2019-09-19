package com.clubobsidian.chatson.format;

import net.kyori.text.format.TextDecoration;

public enum ChatsonTextDecoration {

	BOLD('l'),
	ITALIC('o'),
	OBFUSCATED('k'),
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
	
	public static ChatsonTextDecoration getByChar(char search)
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