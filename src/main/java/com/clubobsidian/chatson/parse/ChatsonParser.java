package com.clubobsidian.chatson.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.clubobsidian.chatson.format.ChatsonTextColor;
import com.clubobsidian.chatson.format.ChatsonTextDecoration;
import com.clubobsidian.chatson.format.ChatsonTextSpecial;

import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;
import net.kyori.text.format.Style;
import net.kyori.text.format.TextColor;
import net.kyori.text.format.TextDecoration;

public class ChatsonParser {

	private String text;
	public ChatsonParser(String text)
	{
		this.text = text;
	}
	
	public TextComponent parseTextComponent()
	{
		List<TextComponent> components = new ArrayList<>();
		TextComponent.Builder builder = TextComponent.builder();
		ChatsonTokenizer tokenizer = new ChatsonTokenizer(this.text);
		List<ChatsonToken> tokens = tokenizer.tokenize();
		int size = tokens.size();
		
		
		for(int i = 0; i < tokens.size(); i++)
		{
			ChatsonToken token = tokens.get(i);
			ChatsonTokenType type = token.getType();

			if(type == ChatsonTokenType.TEXT)
			{
				builder.append(token.getData());
			}
			else if(type == ChatsonTokenType.COLOR)
			{
				if(builder.build().children().size() > 0)
				{
					components.add(builder.build());
					builder = TextComponent.builder();
				}
				
				ChatsonTextColor color = ChatsonTextColor.getByChar(token.getIdentifier());
				builder.color(color.getAPITextColor());
			}
			else if(type == ChatsonTokenType.DECORATION)
			{
				ChatsonTextDecoration decoration = ChatsonTextDecoration.getByChar(token.getIdentifier());
				if(decoration == ChatsonTextDecoration.RESET)
				{
					components.add(builder.build());
					builder = TextComponent.builder();
				}
				else
				{
					
					ChatsonToken next = tokens.get(i + 1);
					ChatsonTokenType nextType = next.getType();
					if(nextType == ChatsonTokenType.COLOR)
					{
						continue;
					}
					
					int indexBefore = i - 1;
					if(indexBefore > 0)
					{
						//If there is not a color before
						ChatsonToken beforeToken = tokens.get(indexBefore); 
						if(beforeToken.getType() != ChatsonTokenType.COLOR && beforeToken.getType() != ChatsonTokenType.DECORATION)
						{
							if(builder.build().children().size() > 0)
							{
								components.add(builder.build());
								builder = TextComponent.builder();
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
								builder.color(color);
								builder.decorations(decorations, true);
							}
						}
					}
					
					builder.decoration(decoration.getAPITextDecoration(), true);
				}
			}
			else if(type == ChatsonTokenType.SPECIAL)
			{
				ChatsonTextSpecial special = ChatsonTextSpecial.getByChar(token.getIdentifier());
				if(special == ChatsonTextSpecial.HOVER)
				{
					List<TextComponent> hoverComponents = new ArrayList<>();
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

					builder.hoverEvent(HoverEvent.showText(builtHoverComponent));
				} 
				else if(i + 1 < size)
				{
					ChatsonToken nextToken = tokens.get(i + 1);
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

					i++;
				}
			}
		}

		components.add(builder.build());

		TextComponent.Builder combined = TextComponent.builder();
		for(TextComponent component : components)
		{
			combined.append(component);
		}

		return combined.build();
	}
}
