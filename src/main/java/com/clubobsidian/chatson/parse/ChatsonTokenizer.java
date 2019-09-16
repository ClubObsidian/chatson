package com.clubobsidian.chatson.parse;

import java.util.ArrayList;
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
	
	public List<ChatsonToken> tokenize()
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
							tokens.add(new ChatsonToken(ChatsonTokenType.TEXT, ' ', buffer.toString()));
							buffer = new StringBuilder();
						}
						
						if(type == ChatsonTokenType.COLOR || type == ChatsonTokenType.DECORATION || type == ChatsonTokenType.SPECIAL)
						{
							tokens.add(new ChatsonToken(type, nextChar));
							i++;
							continue;
						}
					}
				}
			}
			else
			{
				buffer.append(chars[i]);
			}
		}
		if(buffer.length() > 0)
		{
			tokens.add(new ChatsonToken(ChatsonTokenType.TEXT, ' ', buffer.toString()));
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