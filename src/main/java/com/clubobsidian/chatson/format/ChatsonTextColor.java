package com.clubobsidian.chatson.format;

import net.kyori.text.format.TextColor;

public enum ChatsonTextColor {

	AQUA('b'),
	BLACK('0'),
	BLUE('9'),
	DARK_AQUA('3'),
	DARK_BLUE('1'),
	DARK_GRAY('8'),
	DARK_GREEN('2'),
	DARK_PURPLE('5'),
	DARK_RED('4'),
	GOLD('6'),
	GRAY('7'),
	GREEN('a'),
	LIGHT_PURPLE('d'),
	RED('c'),
	WHITE('f'),
	YELLOW('e');
	
	private char charCode;
	private ChatsonTextColor(char charCode)
	{
		this.charCode = charCode;
	}
	
	public char getCharCode()
	{
		return this.charCode;
	}
	
	public TextColor getAPITextColor()
	{
		return TextColor.valueOf(this.name());
	}
	
	public static ChatsonTextColor getByChar(char search)
	{
		for(ChatsonTextColor color : ChatsonTextColor.values())
		{
			if(color.getCharCode() == search)
			{
				return color;
			}
		}
		
		return null;
	}
}