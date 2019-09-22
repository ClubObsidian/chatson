package com.clubobsidian.chatson.parse.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.clubobsidian.chatson.parse.ChatsonToken;
import com.clubobsidian.chatson.parse.builder.section.AbstractSection;
import com.clubobsidian.chatson.parse.builder.section.ColorSection;
import com.clubobsidian.chatson.parse.builder.section.DecorationSection;
import com.clubobsidian.chatson.parse.builder.section.SpecialSection;
import com.clubobsidian.chatson.parse.builder.section.TextSection;

import net.kyori.text.TextComponent;

public class ChatsonParserHelper {

	private List<ChatsonToken> tokens;
	private List<TextComponent> components;
	private int index;
	private int size;
	private TextComponent.Builder builder;
	private Collection<AbstractSection> sections;
	public ChatsonParserHelper(List<ChatsonToken> tokens)
	{
		this.tokens = Collections.unmodifiableList(tokens);
		this.components = new ArrayList<>();
		this.index = 0;
		this.size = this.tokens.size();
		this.builder = TextComponent.builder();
		this.sections = this.loadSections();
	}
	
	public List<ChatsonToken> getTokens()
	{
		return this.tokens;
	}
	
	public List<TextComponent> getComponents()
	{
		return this.components;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public int getSize()
	{
		return this.size;
	}
	
	public TextComponent.Builder getBuilder()
	{
		return this.builder;
	}
	
	public void newBuilder()
	{
		this.builder = TextComponent.builder();
	}
	
	public ChatsonParserHelper build()
	{	
		if(!this.isDone())
		{
			for(AbstractSection section : this.sections)
			{
				if(this.isDone())
					break;
				System.out.println(section.getClass().getName());
				this.index += section.apply();
			}
			
			return this.build();
		}
		
		TextComponent built = this.builder.build();
		if(built.children().size() > 0)
		{
			this.components.add(built);
		}
		return this;
	}

	public boolean isDone()
	{
		if(this.size == 0)
			return true;
		
		System.out.println("index: " + index + " size: " + (size - 1));
		return this.index >= this.size;
	}
	
	private Collection<AbstractSection> loadSections()
	{
		List<AbstractSection> sections = new ArrayList<>();
		sections.add(new ColorSection(this));
		sections.add(new TextSection(this));
		sections.add(new DecorationSection(this));
		sections.add(new SpecialSection(this));
		
		return sections;
	}
}
