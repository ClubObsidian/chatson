package com.clubobsidian.chatson.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.clubobsidian.chatson.Chatson;

public class ChatsonTest {

	@Test
	public void toJsonColorTest()
	{
		String json = Chatson.toJson("&ctest");
		assertTrue(json.equals("{\"text\":\"\",\"extra\":[{\"text\":\"\",\"extra\":[{\"text\":\"test\"}],\"color\":\"red\"}]}"));
	}
	
	@Test
	public void toJsonBoldTest()
	{
		String json = Chatson.toJson("&ltest");
		assertTrue(json.equals("{\"text\":\"\",\"extra\":[{\"text\":\"\",\"extra\":[{\"text\":\"test\"}],\"bold\":true}]}"));
	}
	
	@Test
	public void toJsonColorBoldTest()
	{
		String json = Chatson.toJson("&c&ltest");
		assertTrue(json.equals("{\"text\":\"\",\"extra\":[{\"text\":\"\",\"extra\":[{\"text\":\"test\"}],\"bold\":true,\"color\":\"red\"}]}"));
	}
	
	
}