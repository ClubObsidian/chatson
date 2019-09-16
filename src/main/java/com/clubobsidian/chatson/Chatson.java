package com.clubobsidian.chatson;

import com.clubobsidian.chatson.parse.ChatsonParser;

import net.kyori.text.TextComponent;
import net.kyori.text.serializer.gson.GsonComponentSerializer;

public final class Chatson {
	
	private Chatson() {}
	
	public static String getJson(String text)
	{
		return GsonComponentSerializer.INSTANCE.serialize(getTextComponent(text));
	}
	
	public static TextComponent getTextComponent(String text)
	{
		return new ChatsonParser(text).parseTextComponent();
	}
}