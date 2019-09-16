package com.clubobsidian.chatson.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.clubobsidian.chatson.Chatson;
import com.clubobsidian.chatson.format.ChatsonTextDecoration;
import com.clubobsidian.chatson.parse.ChatsonToken;
import com.clubobsidian.chatson.parse.ChatsonTokenType;
import com.clubobsidian.chatson.parse.ChatsonTokenizer;

public class ChatsonTokenizerTest {

	@Test
	public void testTokenizerSizeTwoColor()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("&ctest");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 2);
		assertTrue(tokens.get(0).getType() == ChatsonTokenType.COLOR);
		assertTrue(tokens.get(1).getType() == ChatsonTokenType.TEXT);
	}
	
	@Test
	public void testTokenizerHover()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("&ctest&htesthover");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue("Tokens is not size 4", tokens.size() == 4);
		assertTrue(tokens.get(0).getType() == ChatsonTokenType.COLOR);
		assertTrue(tokens.get(1).getType() == ChatsonTokenType.TEXT);
		assertTrue(tokens.get(2).getType() == ChatsonTokenType.SPECIAL);
		assertTrue(tokens.get(3).getData().equals("testhover"));
	}
	
	@Test
	public void testTokenizerHoverReset()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("&ctest&htesthover&rafter");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 6);
		assertTrue(tokens.get(0).getType() == ChatsonTokenType.COLOR);
		assertTrue(tokens.get(1).getType() == ChatsonTokenType.TEXT);
		assertTrue(tokens.get(2).getType() == ChatsonTokenType.SPECIAL);
		assertTrue(tokens.get(3).getData().equals("testhover"));
		assertTrue(tokens.get(4).getIdentifier() == ChatsonTextDecoration.RESET.getCharCode());
		assertTrue(tokens.get(5).getData().contentEquals("after"));
	}
	
	@Test
	public void testTokenizerHoverResetAndColor()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("&ctest&h&atesthover&rafter");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 7);
		
		assertTrue(tokens.get(0).getType() == ChatsonTokenType.COLOR);
		assertTrue(tokens.get(1).getType() == ChatsonTokenType.TEXT);
		assertTrue(tokens.get(2).getType() == ChatsonTokenType.SPECIAL);
		assertTrue(tokens.get(3).getType() == ChatsonTokenType.COLOR);
		assertTrue(tokens.get(4).getData().equals("testhover"));
		assertTrue(tokens.get(5).getIdentifier() == ChatsonTextDecoration.RESET.getCharCode());
		assertTrue(tokens.get(6).getData().contentEquals("after"));
		System.out.println(Chatson.getJson("&ctest&h&ahelp&r  &aasdf&q/help&r other stoof"));
	}
	
	@Test
	public void testTokenizerEscapeAnd()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("&&");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 1);
		assertTrue(tokens.get(0).getData().equals("&"));
	}
	
	
}