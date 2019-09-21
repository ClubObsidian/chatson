package com.clubobsidian.chatson.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
	
	@Test
	public void toJsonListTest()
	{
		List<String> lines = new ArrayList<>();
		lines.add("&ctest");
		lines.add("&ltest");
		lines.add("&c&ltest");
		List<String> json = Chatson.toJson(lines);
		assertTrue(json.get(0).equals("{\"text\":\"\",\"extra\":[{\"text\":\"\",\"extra\":[{\"text\":\"test\"}],\"color\":\"red\"}]}"));
		assertTrue(json.get(1).equals("{\"text\":\"\",\"extra\":[{\"text\":\"\",\"extra\":[{\"text\":\"test\"}],\"bold\":true}]}"));
		assertTrue(json.get(2).equals("{\"text\":\"\",\"extra\":[{\"text\":\"\",\"extra\":[{\"text\":\"test\"}],\"bold\":true,\"color\":\"red\"}]}"));
	}
}