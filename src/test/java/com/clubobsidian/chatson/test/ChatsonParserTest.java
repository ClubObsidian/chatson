package com.clubobsidian.chatson.test;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.clubobsidian.chatson.Chatson;
import com.clubobsidian.chatson.parse.ChatsonParser;

import net.kyori.text.Component;
import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
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
	public void testParserDecorationBeforeColor()
	{
		ChatsonParser parser = new ChatsonParser("&l&ctest");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 1);
		Component child = children.get(0);
		assertTrue(child.color() == TextColor.RED);
		assertTrue(child.decorations().size() == 0);
	}
	
	@Test
	public void testParserDanglingDecoration()
	{
		ChatsonParser parser = new ChatsonParser("test&l");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 1);
		Component child = children.get(0);
		assertTrue(child.decorations().size() == 0);
	}
	
	//System.out.println(Chatson.toJson("&aHello&h&aHello &lWorld"));
	
	@Test
	public void testParserDecorationAfterColorCopy()
	{
		ChatsonParser parser = new ChatsonParser("&aHello &lWorld");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 2);
		assertTrue(children.get(0).color() == TextColor.GREEN);
		assertTrue(children.get(0).decorations().size() == 0);
		assertTrue(children.get(1).color() == TextColor.GREEN);
		assertTrue(children.get(1).decorations().size() == 1);
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
	public void testParserHoverDecorationBeforeColor()
	{
		ChatsonParser parser = new ChatsonParser("&c&ltest&h&l&ctest1");
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
		assertTrue(decorations.size() == 0);
	}
	
	@Test
	public void testParserHoverDanglingDecoration()
	{
		ChatsonParser parser = new ChatsonParser("&c&ltest&h&ctest1&l");
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
		assertTrue(decorations.size() == 0);
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
	public void testParserHoverDecorationAfterColorCopy()
	{
		ChatsonParser parser = new ChatsonParser("&c&ltest&h&atest1&ltest2");
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
		assertTrue(hoverChildren.get(0).decorations().size() == 0);
		assertTrue(hoverChildren.get(1).color() == TextColor.GREEN);
		assertTrue(hoverChildren.get(1).decorations().size() == 1);
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
		assertTrue(textHoverComponent.content().equals("test1"));
	}
	
	@Test
	public void testParserRunCommand()
	{
		ChatsonParser parser = new ChatsonParser("test&q/help");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 1);
		Component child = children.get(0);
		TextComponent text = (TextComponent) child.children().get(0);
		assertTrue(text.content().equals("test"));
		ClickEvent click = child.clickEvent();
		assertTrue(click.action() == ClickEvent.Action.RUN_COMMAND);
		assertTrue(click.value().equals("/help"));
	}
	
	@Test
	public void testParserSuggestCommand()
	{
		ChatsonParser parser = new ChatsonParser("test2&w/help");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 1);
		Component child = children.get(0);
		TextComponent text = (TextComponent) child.children().get(0);
		assertTrue(text.content().equals("test2"));
		ClickEvent click = child.clickEvent();
		assertTrue(click.action() == ClickEvent.Action.SUGGEST_COMMAND);
		assertTrue(click.value().equals("/help"));
	}
	
	@Test
	public void testParserOpenUrl()
	{
		ChatsonParser parser = new ChatsonParser("github&uhttps://www.github.com");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 1);
		Component child = children.get(0);
		TextComponent text = (TextComponent) child.children().get(0);
		assertTrue(text.content().equals("github"));
		ClickEvent click = child.clickEvent();
		assertTrue(click.action() == ClickEvent.Action.OPEN_URL);
		assertTrue(click.value().equals("https://www.github.com"));
	}
	
	@Test
	public void testParserChangePage()
	{
		ChatsonParser parser = new ChatsonParser("Go to page 3&p3");
		TextComponent component = parser.parseTextComponent();
		List<Component> children = component.children();
		assertTrue(children.size() == 1);
		Component child = children.get(0);
		TextComponent text = (TextComponent) child.children().get(0);
		assertTrue(text.content().equals("Go to page 3"));
		ClickEvent click = child.clickEvent();
		assertTrue(click.action() == ClickEvent.Action.CHANGE_PAGE);
		assertTrue(click.value().equals("3"));
	}
}