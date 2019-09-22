package com.clubobsidian.chatson.parse.builder.section;

import com.clubobsidian.chatson.format.ChatsonTextColor;
import com.clubobsidian.chatson.parse.ChatsonToken;
import com.clubobsidian.chatson.parse.ChatsonTokenType;
import com.clubobsidian.chatson.parse.builder.ChatsonParserHelper;

import net.kyori.text.TextComponent;

public class ColorSection extends AbstractSection {

	public ColorSection(ChatsonParserHelper builder) 
	{
		super(builder, ChatsonTokenType.COLOR);
	}

	@Override
	public int apply() 
	{
		ChatsonToken token = this.helper.getTokens().get(this.helper.getIndex());
		if(token.getType() == this.getType())
		{
			TextComponent built = this.helper.getBuilder().build();
			if(built.children().size() > 0)
			{
				this.helper.getComponents().add(built);
				this.helper.newBuilder();
			}

			ChatsonTextColor color = ChatsonTextColor.getByChar(token.getIdentifier());
			this.helper.getBuilder().color(color.getAPITextColor());
			return 1;
		}
		
		return 0;
	}
}