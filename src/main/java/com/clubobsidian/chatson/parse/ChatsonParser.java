package com.clubobsidian.chatson.parse;

import java.util.List;

import com.clubobsidian.chatson.parse.builder.ChatsonParserHelper;

import net.kyori.text.TextComponent;

public class ChatsonParser {

	private String text;
	public ChatsonParser(String text)
	{
		this.text = text;
	}
	
	public TextComponent parseTextComponent()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer(this.text);
		List<ChatsonToken> tokens = tokenizer.tokenize();
		ChatsonParserHelper helper = new ChatsonParserHelper(tokens);
		helper.build();

		TextComponent.Builder combined = TextComponent.builder();
		for(TextComponent component : helper.getComponents())
		{
			combined.append(component);
		}
		
		return combined.build();
	}
}
