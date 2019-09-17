package com.clubobsidian.chatson.test;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Test;

import com.clubobsidian.chatson.Chatson;
import com.clubobsidian.chatson.parse.ChatsonParser;

import net.kyori.text.Component;
import net.kyori.text.TextComponent;
import net.kyori.text.event.HoverEvent;
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
		ChatsonParser parser = new ChatsonParser("&c&ltest&h&atest1&btest2");
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
		HoverEvent hover = child.hoverEvent();
		List<Component> hoverChildren = hover.value().children();
		assertTrue(hoverChildren.size() == 2);
		assertTrue(hoverChildren.get(0).color() == TextColor.GREEN);
		assertTrue(hoverChildren.get(1).color() == TextColor.AQUA);
	}
	
	@Test
	public void testParserHoverDecoration()
	{
		ChatsonParser parser = new ChatsonParser("&c&ltest&h&ltest1");
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
		HoverEvent hover = child.hoverEvent();
		List<Component> hoverChildren = hover.value().children();
		assertTrue(hoverChildren.size() == 1);
		Set<TextDecoration> decorations = hoverChildren.get(0).decorations();
		assertTrue(decorations.size() == 1);
		assertTrue(decorations.toArray()[0] == TextDecoration.BOLD);
	}
	
	@Test
	public void testParserHoverReset()
	{
		ChatsonParser parser = new ChatsonParser("test&htest1&r reset");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 2);
		Component child = children.get(0);
		assertTrue(child instanceof TextComponent);
		TextComponent text = (TextComponent) child.children().get(0);
		assertTrue(text.content().equals("test"));
		HoverEvent hover = child.hoverEvent();
		Component hoverComponent = hover.value().children().get(0).children().get(0);
		assertTrue(hoverComponent instanceof TextComponent);
		TextComponent textHoverComponent = (TextComponent) hoverComponent;
		System.out.println(hover);
		assertTrue(textHoverComponent.content().equals("test1"));
	}
}