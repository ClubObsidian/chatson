package com.clubobsidian.chatson;

public final class Chatson {
	
	private Chatson() {}
	
	public static String getJson(String text)
	{
		return GsonComponentSerializer.INSTANCE.serialize(getTextComponent(text));
	}
	
	public static TextComponent getTextComponent(String text)
	{
		
	}
}