package com.clubobsidian.chatson.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.clubobsidian.chatson.Chatson;
import com.clubobsidian.chatson.parse.ChatsonParser;

import net.kyori.text.Component;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.format.TextDecoration;

public class ChatsonParserTest {

	@Test
	public void testParserColor()
	{
		ChatsonParser parser = new ChatsonParser("&ctest");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 1);
		Component child = children.get(0);
		assertTrue(child.color() == TextColor.RED);
		assertTrue(child instanceof TextComponent);
		TextComponent text = (TextComponent) child.children().get(0);
		assertTrue(text.content().equals("test"));
	}
	
	@Test
	public void testMultipleColors()
	{
		ChatsonParser parser = new ChatsonParser("&ctest1&btest2&atest3");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 3);
		
		Component child = children.get(0);
		assertTrue(child.color() == TextColor.RED);
		assertTrue(child instanceof TextComponent);
		TextComponent textOne = (TextComponent) child.children().get(0);
		assertTrue(textOne.content().equals("test1"));
		
		Component childTwo = children.get(1);
		assertTrue(childTwo.color() == TextColor.AQUA);
		assertTrue(childTwo instanceof TextComponent);
		TextComponent textTwo = (TextComponent) childTwo.children().get(0);
		assertTrue(textTwo.content().equals("test2"));
		
		Component childThree = children.get(2);
		assertTrue(childThree .color() == TextColor.GREEN);
		assertTrue(childThree  instanceof TextComponent);
		TextComponent text = (TextComponent) childThree.children().get(0);
		assertTrue(text.content().equals("test3"));
	}
	
	@Test
	public void testParserDecoration()
	{
		ChatsonParser parser = new ChatsonParser("&ltest");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 1);
		Component child = children.get(0);
		assertTrue(child.decorations().size() == 1);
		assertTrue(child.decorations().toArray()[0] == TextDecoration.BOLD);
		assertTrue(child instanceof TextComponent);
		TextComponent text = (TextComponent) child.children().get(0);
		assertTrue(text.content().equals("test"));
	}
	
	@Test
	public void testParserTwoDecorations()
	{
		ChatsonParser parser = new ChatsonParser("&o&ltest");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 1);
		Component child = children.get(0);
		assertTrue(child.decorations().size() == 2);
		assertTrue(child.decorations().toArray()[0] == TextDecoration.BOLD);
		assertTrue(child.decorations().toArray()[1] == TextDecoration.ITALIC);
		assertTrue(child instanceof TextComponent);
		TextComponent text = (TextComponent) child.children().get(0);
		assertTrue(text.content().equals("test"));
	}
	
	
	@Test
	public void testParserColorAndDecoration()
	{
		ChatsonParser parser = new ChatsonParser("&c&ltest");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 1);
		Component child = children.get(0);
		assertTrue(child.color() == TextColor.RED);
		assertTrue(child.decorations().size() == 1);
		assertTrue(child.decorations().toArray()[0] == TextDecoration.BOLD);
		assertTrue(child instanceof TextComponent);
		TextComponent text = (TextComponent) child.children().get(0);
		assertTrue(text.content().equals("test"));
	}
	
	@Test
	public void testParserHoverColor()
	{
		ChatsonParser parser = new ChatsonParser("&c&ltest&h&atest&btest2");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		
		System.out.println(Chatson.getJson("&c&ltest&h&atest&btest2"));
	}
}