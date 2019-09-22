package com.clubobsidian.chatson.parse.builder.section;

import com.clubobsidian.chatson.parse.ChatsonTokenType;
import com.clubobsidian.chatson.parse.builder.ChatsonParserHelper;

public abstract class AbstractSection {

	protected ChatsonParserHelper helper;
	private ChatsonTokenType type;
	public AbstractSection(ChatsonParserHelper helper, ChatsonTokenType type)
	{
		this.helper = helper;
		this.type = type;
	}
	
	public ChatsonTokenType getType()
	{
		return this.type;
	}
	
	public abstract int apply();

}