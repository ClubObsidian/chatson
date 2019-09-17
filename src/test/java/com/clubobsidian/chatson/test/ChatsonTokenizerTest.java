package com.clubobsidian.chatson.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.clubobsidian.chatson.format.ChatsonTextDecoration;
import com.clubobsidian.chatson.format.ChatsonTextSpecial;
import com.clubobsidian.chatson.parse.ChatsonToken;
import com.clubobsidian.chatson.parse.ChatsonTokenType;
import com.clubobsidian.chatson.parse.ChatsonTokenizer;

public class ChatsonTokenizerTest {

	@Test
	public void testTokenizerColor()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("&ctest");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 2);
		assertTrue(tokens.get(0).getType() == ChatsonTokenType.COLOR);
		assertTrue(tokens.get(1).getType() == ChatsonTokenType.TEXT);
	}
	
	@Test
	public void testTokenizerDecoration()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("&ltest");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 2);
		assertTrue(tokens.get(0).getType() == ChatsonTokenType.DECORATION);
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
	}
	
	@Test
	public void testTokenizerRunCommand()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("help me&q/help");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 3);
		assertTrue(tokens.get(0).getData().equals("help me"));
		assertTrue(tokens.get(1).getIdentifier() == ChatsonTextSpecial.RUN_COMMAND.getCharCode());
		assertTrue(tokens.get(2).getData().equals("/help"));
	}
	
	@Test
	public void testTokenizerSuggestCommand()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("help me&w/help");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 3);
		assertTrue(tokens.get(0).getData().equals("help me"));
		assertTrue(tokens.get(1).getIdentifier() == ChatsonTextSpecial.SUGGEST_COMMAND.getCharCode());
		assertTrue(tokens.get(2).getData().equals("/help"));
	}
	
	@Test
	public void testTokenizerURL()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("Click me to go to Github!&uhttps://www.github.com");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 3);
		assertTrue(tokens.get(0).getData().equals("Click me to go to Github!"));
		assertTrue(tokens.get(1).getIdentifier() == ChatsonTextSpecial.URL.getCharCode());
		assertTrue(tokens.get(2).getData().equals("https://www.github.com"));
	}
	
	@Test
	public void testTokenizerInvalidFormattingCode()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("&zInvalid code!");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 1);
		assertTrue(tokens.get(0).getData().equals("&zInvalid code!"));
	}
	
	@Test
	public void testTokenizerInvalidFormattingCodeWithPrefix()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("a&zInvalid code!");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 1);
		assertTrue(tokens.get(0).getData().equals("a&zInvalid code!"));
	}
	
	@Test
	public void testTokenizerEmpty()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 0);
	}
	
	@Test
	public void testTokenizerEscapeAnd()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("&&");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		assertTrue(tokens.size() == 1);
		assertTrue(tokens.get(0).getData().equals("&"));
	}
	
	@Test
	public void testTokenizerEndAnd()
	{
		ChatsonTokenizer tokenizer = new ChatsonTokenizer("test&");
		List<ChatsonToken> tokens = tokenizer.tokenize();
		System.out.println(tokens.get(0).getData());
		assertTrue(tokens.size() == 1);
		assertTrue(tokens.get(0).getData().equals("test&"));
	}
}