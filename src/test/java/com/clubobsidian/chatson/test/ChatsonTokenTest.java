package com.clubobsidian.chatson.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.clubobsidian.chatson.parse.ChatsonToken;
import com.clubobsidian.chatson.parse.ChatsonTokenType;

public class ChatsonTokenTest {
	
	@Test
	public void testTokenType()
	{
		ChatsonToken token = new ChatsonToken(ChatsonTokenType.COLOR, 'c');
		assertTrue(token.getType() == ChatsonTokenType.COLOR);
		assertTrue(token.getData().equals(" "));
	}
	
	@Test
	public void testTokenIdentifier()
	{
		ChatsonToken token = new ChatsonToken(ChatsonTokenType.COLOR, 'c');
		assertTrue(token.getIdentifier() == 'c');
	}
	
	@Test
	public void testTokenData()
	{
		ChatsonToken token = new ChatsonToken(ChatsonTokenType.COLOR, "test");
		assertTrue(token.getData().equals("test"));
	}
	
	@Test
	public void testToString()
	{
		ChatsonToken token = new ChatsonToken(ChatsonTokenType.COLOR, "test");
		assertTrue(token.toString().equals("ChatsonToken [type=COLOR, identifier= , data=test]"));
	}
}