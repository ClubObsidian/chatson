package com.clubobsidian.chatson.test;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

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
		assertTrue("Tokens is not size 3", tokens.size() == 3);
		assertTrue(tokens.get(0).getType() == ChatsonTokenType.COLOR);
		assertTrue(tokens.get(1).getType() == ChatsonTokenType.TEXT);
		assertTrue(tokens.get(2).getType() == ChatsonTokenType.SPECIAL);
		assertTrue(tokens.get(2).getData().equals("testhover"));
	}
	
	@Test
	public void testTokenizerHoverReset()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("&ctest&htesthover&rafter");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		System.out.println(tokens.size());
		System.out.println(tokens);
		assertTrue(tokens.size() == 5);
		assertTrue(tokens.get(0).getType() == ChatsonTokenType.COLOR);
		assertTrue(tokens.get(1).getType() == ChatsonTokenType.TEXT);
		assertTrue(tokens.get(2).getType() == ChatsonTokenType.SPECIAL);
		assertTrue(tokens.get(2).getData().equals("testhover"));
		assertTrue(tokens.get(3).getIdentifier() == ChatsonTextDecoration.RESET.getCharCode());
		assertTrue(tokens.get(4).getData().contentEquals("after"));
	}
}