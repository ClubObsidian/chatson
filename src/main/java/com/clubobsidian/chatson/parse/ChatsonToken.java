package com.clubobsidian.chatson.parse;

public class ChatsonToken {

	private ChatsonTokenType type;
	private String data;
	public ChatsonToken(ChatsonTokenType type, String data)
	{
		this.type = type;
		this.data = data;
	}
	
	public ChatsonToken(ChatsonTokenType type, char data)
	{
		this(type, String.valueOf(data));
	}
	
	public ChatsonTokenType getType()
	{
		return this.type;
	}
	
	public String getData()
	{
		return this.data;
	}
}