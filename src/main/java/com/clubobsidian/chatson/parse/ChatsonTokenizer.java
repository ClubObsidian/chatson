package com.clubobsidian.chatson.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.clubobsidian.chatson.format.ChatsonTextColor;
import com.clubobsidian.chatson.format.ChatsonTextDecoration;
import com.clubobsidian.chatson.format.ChatsonTextSpecial;

public class ChatsonTokenizer {

	private String text;
	public ChatsonTokenizer(String text)
	{
		this.text = text;
	}
	
	public Collection<ChatsonToken> tokenize()
	{
		List<ChatsonToken> tokens = new ArrayList<>();
		StringBuilder buffer = new StringBuilder();
		char[] chars = text.toCharArray();
		int len = chars.length;
		for(int i = 0; i < len; i++)
		{
			if(chars[i] == '&')
			{
				if(i + 1 < len)
				{
					char nextChar = chars[i + 1];
					if(nextChar == '&')
					{
						buffer.append('&');
						i++;
						continue;
					}
					
					ChatsonTokenType type = this.getType(nextChar);
					if(type != ChatsonTokenType.TEXT)
					{
						if(buffer.length() > 0)
						{
							tokens.add(new ChatsonToken(ChatsonTokenType.TEXT, buffer.toString()));
							buffer = new StringBuilder();
						}
						
						if(type == ChatsonTokenType.COLOR || type == ChatsonTokenType.DECORATION)
						{
							tokens.add(new ChatsonToken(type, nextChar));
							i++;
							continue;
						}
						else if(type == ChatsonTokenType.SPECIAL)
						{
							for(int j = i + 2; j < len; j++)
							{
								if(chars[j] == '&' && j + 1 < len && chars[j + 1] == 'r')
								{
									tokens.add(new ChatsonToken(type, buffer.toString()));
									buffer = new StringBuilder();
									i += j - i;
								}
								else if(j == len -1)
								{
									tokens.add(new ChatsonToken(type, buffer.toString()));
									return tokens;
								}
								else
								{
									buffer.append(chars[j]);
								}
							}
						}
					}
				}
			}
			else
			{
				buffer.append(chars[i]);
			}
		}
		
		return tokens;
	}
	
	private ChatsonTokenType getType(char ch)
	{
		if(ChatsonTextColor.getByChar(ch) != null)
		{
			return ChatsonTokenType.COLOR;
		}
		else if(ChatsonTextDecoration.getByChar(ch) != null)
		{
			return ChatsonTokenType.DECORATION;
		}
		else if(ChatsonTextSpecial.getByChar(ch) != null)
		{
			return ChatsonTokenType.SPECIAL;
		}
		
		return ChatsonTokenType.TEXT;
	}
}