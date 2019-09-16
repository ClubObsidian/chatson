package com.clubobsidian.chatson.parse;

public class ChatsonToken {

	private ChatsonTokenType type;
	private char identifier;
	private String data;
	public ChatsonToken(ChatsonTokenType type, char identifier, String data)
	{
		this.type = type;
		this.identifier = identifier;
		this.data = data;
	}
	
	public ChatsonToken(ChatsonTokenType type, char identifier)
	{
		this(type, identifier, null);
	}
	
	public ChatsonTokenType getType()
	{
		return this.type;
	}
	
	public char getIdentifier()
	{
		return this.identifier;
	}
	
	public String getData()
	{
		return this.data;
	}

	@Override
	public String toString() 
	{
		return "ChatsonToken [type=" + this.type + ", identifier=" + this.identifier + ", data=" + this.data + "]";
	}
}