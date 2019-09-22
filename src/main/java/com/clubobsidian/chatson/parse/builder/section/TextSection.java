package com.clubobsidian.chatson.parse.builder.section;

import com.clubobsidian.chatson.parse.ChatsonToken;
import com.clubobsidian.chatson.parse.ChatsonTokenType;
import com.clubobsidian.chatson.parse.builder.ChatsonParserHelper;

public class TextSection extends AbstractSection {

	public TextSection(ChatsonParserHelper builder) 
	{
		super(builder, ChatsonTokenType.TEXT);
	}

	@Override
	public int apply() 
	{
		ChatsonToken token = this.helper.getTokens().get(this.helper.getIndex());
		if(token.getType() == this.getType())
		{
			this.helper.getBuilder().append(token.getData());
			return 1;
		}
		
		return 0;
	}
}