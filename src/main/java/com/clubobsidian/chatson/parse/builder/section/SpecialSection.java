package com.clubobsidian.chatson.parse.builder.section;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.clubobsidian.chatson.format.ChatsonTextColor;
import com.clubobsidian.chatson.format.ChatsonTextDecoration;
import com.clubobsidian.chatson.format.ChatsonTextSpecial;
import com.clubobsidian.chatson.parse.ChatsonToken;
import com.clubobsidian.chatson.parse.ChatsonTokenType;
import com.clubobsidian.chatson.parse.builder.ChatsonParserHelper;

import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;
import net.kyori.text.format.Style;
import net.kyori.text.format.TextColor;
import net.kyori.text.format.TextDecoration;

public class SpecialSection extends AbstractSection {

	public SpecialSection(ChatsonParserHelper builder) 
	{
		super(builder, ChatsonTokenType.SPECIAL);
	}

	@Override
	public int apply() 
	{
		int index = this.helper.getIndex();
		int size = this.helper.getSize();
		List<ChatsonToken> tokens = this.helper.getTokens();
		ChatsonToken token = tokens.get(index);
		if(token.getType() == this.getType())
		{
			ChatsonTextSpecial special = ChatsonTextSpecial.getByChar(token.getIdentifier());
			if(special == ChatsonTextSpecial.HOVER)
			{
				/*List<TextComponent> hoverComponents = new ArrayList<>();
				TextComponent.Builder hoverBuilder = TextComponent.builder();
				for(int j = i + 1; j < size; j++)
				{
					ChatsonToken nextToken = tokens.get(j);
					ChatsonTokenType nextType = nextToken.getType();
					char nextIdentifier = nextToken.getIdentifier();
					
					if(nextType == ChatsonTokenType.TEXT)
					{
						hoverBuilder.append(nextToken.getData());
					}
					else if(nextType == ChatsonTokenType.COLOR)
					{
						if(hoverBuilder.build().children().size() > 0)
						{
							hoverComponents.add(hoverBuilder.build());
							hoverBuilder = TextComponent.builder();
						}
						
						hoverBuilder.color(ChatsonTextColor.getByChar(nextIdentifier).getAPITextColor());
					}
					else if(nextType == ChatsonTokenType.SPECIAL)
					{
						if(hoverBuilder.build().children().size() > 0)
						{
							hoverComponents.add(hoverBuilder.build());
							hoverBuilder = TextComponent.builder();
							j--;
							i += j - i;
							break;
						}
					}
					else if(nextType == ChatsonTokenType.DECORATION)
					{
						ChatsonTextDecoration decoration = ChatsonTextDecoration.getByChar(nextIdentifier);
						if(decoration == ChatsonTextDecoration.RESET)
						{
							//Go back one and break out so it can be handled by the outside loop
							j--;
							i += j - i;
							break;
						}
						else
						{
							boolean skip = false;
							if(j == tokens.size() - 1)
								skip = true;

							if(!skip)
							{
								ChatsonToken after = tokens.get(j + 1);
								ChatsonTokenType afterType = after.getType();
								if(afterType == ChatsonTokenType.COLOR)
									skip = true;
								
								if(!skip)
								{
									int indexBefore = j - 1;
									if(indexBefore > 0)
									{
										//If there is not a color before
										ChatsonToken beforeToken = tokens.get(indexBefore); 
										if(beforeToken.getType() != ChatsonTokenType.COLOR && beforeToken.getType() != ChatsonTokenType.DECORATION)
										{
											if(hoverBuilder.build().children().size() > 0)
											{
												hoverComponents.add(hoverBuilder.build());
												hoverBuilder = TextComponent.builder();
											}
											
											Style style = null;
											int componentSize = hoverComponents.size();
											if(componentSize > 0)
											{
												TextComponent component = hoverComponents.get(componentSize - 1);
												style = component.style();
											}
											
											if(style != null)
											{
												Set<TextDecoration> decorations = style.decorations();
												TextColor color = style.color();
												hoverBuilder.color(color);
												hoverBuilder.decorations(decorations, true);
											}
										}
									}
									
									hoverBuilder.decoration(decoration.getAPITextDecoration(), true);
								}
							}
						}
					}

					if(j == size - 1) //Check to see if we are the end of the loop and increment
					{
						i += j - i;
					}
				}

				
				TextComponent hoverComponent = hoverBuilder.build();
				hoverComponents.add(hoverComponent);
				
				hoverBuilder = TextComponent.builder();
				for(TextComponent component : hoverComponents)
				{
					hoverBuilder.append(component);
				}
				
				TextComponent builtHoverComponent = hoverBuilder.build();
				
				builder.hoverEvent(HoverEvent.showText(builtHoverComponent));*/
			} 
			else if(index + 1 < size)
			{
				ChatsonToken nextToken = tokens.get(index + 1);
				TextComponent.Builder builder = this.helper.getBuilder();
				if(special == ChatsonTextSpecial.RUN_COMMAND)
				{
					builder.clickEvent(ClickEvent.runCommand(nextToken.getData()));
				}
				else if(special == ChatsonTextSpecial.SUGGEST_COMMAND)
				{
					builder.clickEvent(ClickEvent.suggestCommand(nextToken.getData()));
				}
				else if(special == ChatsonTextSpecial.URL)
				{
					builder.clickEvent(ClickEvent.openUrl(nextToken.getData()));
				}
				else if(special == ChatsonTextSpecial.CHANGE_PAGE)
				{
					builder.clickEvent(ClickEvent.changePage(nextToken.getData()));
				}
				
				return 1;
			}
		}
		return 0;
	}

}
