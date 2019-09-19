package com.clubobsidian.chatson;

import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.chatson.parse.ChatsonParser;

import net.kyori.text.TextComponent;
import net.kyori.text.serializer.gson.GsonComponentSerializer;

public final class Chatson {
	
	private Chatson() {}
	
	public static String toJson(String chatson)
	{
		return GsonComponentSerializer.INSTANCE.serialize(toTextComponent(chatson));
	}
	
	public static List<String> toJson(List<String> chatson)
	{
		List<String> lines = new ArrayList<>();
		for(String line : chatson)
		{
			lines.add(toJson(line));
		}
		
		return lines;
	}
	
	public static TextComponent toTextComponent(String chatson)
	{
		return new ChatsonParser(chatson).parseTextComponent();
	}
}