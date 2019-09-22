package com.clubobsidian.chatson.parse.builder.section;

import java.util.List;
import java.util.Set;

import com.clubobsidian.chatson.format.ChatsonTextDecoration;
import com.clubobsidian.chatson.parse.ChatsonToken;
import com.clubobsidian.chatson.parse.ChatsonTokenType;
import com.clubobsidian.chatson.parse.builder.ChatsonParserHelper;

import net.kyori.text.TextComponent;
import net.kyori.text.format.Style;
import net.kyori.text.format.TextColor;
import net.kyori.text.format.TextDecoration;

public class DecorationSection extends AbstractSection {

	public DecorationSection(ChatsonParserHelper builder) 
	{
		super(builder, ChatsonTokenType.DECORATION);
	}

	@Override
	public int apply() 
	{
		int index = this.helper.getIndex();
		List<ChatsonToken> tokens = this.helper.getTokens();
		List<TextComponent> components = this.helper.getComponents();
		ChatsonToken token = tokens.get(index);
		if(token.getType() == this.getType())
		{
			ChatsonTextDecoration decoration = ChatsonTextDecoration.getByChar(token.getIdentifier());
			if(decoration == ChatsonTextDecoration.RESET)
			{
				TextComponent built = this.helper.getBuilder().build();
				this.helper.getComponents().add(built);
				this.helper.newBuilder();
			}
			else
			{
				if(index == tokens.size() - 1)
					return 0;

				ChatsonToken next = tokens.get(index + 1);
				ChatsonTokenType nextType = next.getType();
				if(nextType == ChatsonTokenType.COLOR)
				{
					return 0;
				}

				int indexBefore = index - 1;
				if(indexBefore > 0)
				{
					//If there is not a color before
					ChatsonToken beforeToken = tokens.get(indexBefore); 
					if(beforeToken.getType() != ChatsonTokenType.COLOR && beforeToken.getType() != ChatsonTokenType.DECORATION)
					{
						TextComponent built = this.helper.getBuilder().build();
						if(built.children().size() > 0)
						{
							this.helper.getComponents().add(built);
							this.helper.newBuilder();
						}

						Style style = null;
						int componentSize = components.size();
						if(componentSize > 0)
						{
							TextComponent component = components.get(componentSize - 1);
							style = component.style();
						}

						if(style != null)
						{
							Set<TextDecoration> decorations = style.decorations();
							TextColor color = style.color();
							this.helper.getBuilder().color(color);
							this.helper.getBuilder().decorations(decorations, true);
						}
					}
				}

				this.helper.getBuilder().decoration(decoration.getAPITextDecoration(), true);
			}

			return 1;
		}
		
		return 0;
	}
}