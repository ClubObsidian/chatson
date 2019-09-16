package com.clubobsidian.chatson.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
		for(int i = 0; i < chars.length; i++)
		{
			
		}
	}
	
	private ChatsonTokenType getType(char ch)
	{
		
	}
}