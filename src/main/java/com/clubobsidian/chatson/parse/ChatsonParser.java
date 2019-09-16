package com.clubobsidian.chatson.parse;

import com.clubobsidian.chatson.format.ChatsonTextColor;
import com.clubobsidian.chatson.format.ChatsonTextDecoration;
import com.clubobsidian.chatson.format.ChatsonTextSpecial;

import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;

public class ChatsonParser {

	private String text;
	public ChatsonParser(String text)
	{
		this.text = text;
	}
	
	public TextComponent parseTextComponent()
	{
		TextComponent.Builder builder = TextComponent.builder();
		ChatsonTokenizer tokenizer = new ChatsonTokenizer(this.text);
		for(ChatsonToken token : tokenizer.tokenize())
		{
			ChatsonTokenType type = token.getType();
			if(type == ChatsonTokenType.TEXT)
			{
				builder.append(token.getData());
			}
			else if(type == ChatsonTokenType.COLOR)
			{
				ChatsonTextColor color = ChatsonTextColor.getByChar(token.getData().toCharArray()[0]);
				builder.color(color.getAPITextColor());
			}
			else if(type == ChatsonTokenType.DECORATION)
			{
				ChatsonTextDecoration decoration = ChatsonTextDecoration.getByChar(token.getData().toCharArray()[0]);
				if(decoration == ChatsonTextDecoration.RESET)
				{
					builder.resetStyle();
				}
				else
				{
					builder.decoration(decoration.getAPITextDecoration(), true);
				}
			}
			else if(type == ChatsonTokenType.SPECIAL)
			{
				ChatsonTextSpecial special = ChatsonTextSpecial.getByChar(token.getData().toCharArray()[0]);
				if(special == ChatsonTextSpecial.HOVER)
				{
					builder.hoverEvent(HoverEvent.showText(TextComponent.of(token.getData())));
				}
				else if(special == ChatsonTextSpecial.RUN_COMMAND)
				{
					builder.clickEvent(ClickEvent.runCommand(token.getData()));
				}
				else if(special == ChatsonTextSpecial.SUGGEST_COMMAND)
				{
					builder.clickEvent(ClickEvent.suggestCommand(token.getData()));
				}
				else if(special == ChatsonTextSpecial.URL)
				{
					builder.clickEvent(ClickEvent.openUrl(token.getData()));
				}
			}
		}
		
		return builder.build();
	}
}
