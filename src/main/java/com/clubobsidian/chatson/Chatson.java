package com.clubobsidian.chatson;

import com.clubobsidian.chatson.parse.ChatsonParser;

import net.kyori.text.TextComponent;
import net.kyori.text.serializer.gson.GsonComponentSerializer;

public final class Chatson {
	
	private Chatson() {}
	
	public static String toJson(String chatson)
	{
		return GsonComponentSerializer.INSTANCE.serialize(toTextComponent(chatson));
	}
	
	public static TextComponent toTextComponent(String chatson)
	{
		return new ChatsonParser(chatson).parseTextComponent();
	}
}