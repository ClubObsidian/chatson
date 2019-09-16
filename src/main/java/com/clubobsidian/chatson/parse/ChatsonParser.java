package com.clubobsidian.chatson.parse;

import java.util.List;

import com.clubobsidian.chatson.format.ChatsonTextColor;
import com.clubobsidian.chatson.format.ChatsonTextDecoration;
import com.clubobsidian.chatson.format.ChatsonTextSpecial;

import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;
import net.kyori.text.format.Style;

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
		List<ChatsonToken> tokens = tokenizer.tokenize();
		int size = tokens.size();
		
		boolean reset = false;
		for(int i = 0; i < tokens.size(); i++)
		{
			ChatsonToken token = tokens.get(i);
			ChatsonTokenType type = token.getType();
			if(type == ChatsonTokenType.TEXT)
			{
				builder.append(token.getData());
			}
			else if(type == ChatsonTokenType.COLOR)
			{
				ChatsonTextColor color = ChatsonTextColor.getByChar(token.getIdentifier());
				builder.color(color.getAPITextColor());
			}
			else if(type == ChatsonTokenType.DECORATION)
			{
				ChatsonTextDecoration decoration = ChatsonTextDecoration.getByChar(token.getIdentifier());
				if(decoration == ChatsonTextDecoration.RESET)
				{
					reset = true;
					
					//builder.append("");
					//builder.resetStyle();
					//builder.append(TextComponent.empty());
					//builder.resetStyle();
				}
				else
				{
					builder.decoration(decoration.getAPITextDecoration(), true);
				}
			}
			else if(type == ChatsonTokenType.SPECIAL)
			{
				ChatsonTextSpecial special = ChatsonTextSpecial.getByChar(token.getIdentifier());
				if(special == ChatsonTextSpecial.HOVER)
				{
					TextComponent.Builder hoverBuilder = TextComponent.builder();
					for(int j = i + 1; j < size; j++)
					{
						ChatsonToken nextToken = tokens.get(j);
						ChatsonTokenType nextType = nextToken.getType();
						char nextIdentifier = nextToken.getIdentifier();
						System.out.println(nextType);
						if(nextType == ChatsonTokenType.TEXT)
						{
							hoverBuilder.append(nextToken.getData());
						}
						else if(nextType == ChatsonTokenType.COLOR)
						{
							hoverBuilder.color(ChatsonTextColor.getByChar(nextIdentifier).getAPITextColor());
						}
						else if(nextType == ChatsonTokenType.DECORATION)
						{
							ChatsonTextDecoration decoration = ChatsonTextDecoration.getByChar(nextIdentifier);
							if(decoration == ChatsonTextDecoration.RESET)
							{
								//Go back one and break out so it can be handled by the outside loop
								j--;
								i += j - i;
								break;
							}
							else
							{
								hoverBuilder.decoration(decoration.getAPITextDecoration(), true);
							}
						}
					}
					
					TextComponent hoverComponent = hoverBuilder.build();
					
					builder.hoverEvent(HoverEvent.showText(hoverComponent));
				} 
				else if(i + 1 < size)
				{
					ChatsonToken nextToken = tokens.get(i + 1);
					if(special == ChatsonTextSpecial.RUN_COMMAND)
					{
						builder.clickEvent(ClickEvent.runCommand(nextToken.getData()));
						i++;
					}
					else if(special == ChatsonTextSpecial.SUGGEST_COMMAND)
					{
						builder.clickEvent(ClickEvent.suggestCommand(nextToken.getData()));
						i++;
					}
					else if(special == ChatsonTextSpecial.URL)
					{
						builder.clickEvent(ClickEvent.openUrl(token.getData()));
						i++;
					}
				}
			}
		}

		return builder.build();
	}
}
