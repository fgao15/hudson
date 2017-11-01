package com.hudson.intellicode.core;

import com.hudson.intellicode.config.Configuration;
import com.hudson.intellicode.lex.LexChatController;
import com.hudson.intellicode.spi.IChatController;

public class ChatControllerFactory {

	public static IChatController getChatController(Configuration config)
	{
		return new LexChatController(config);
	}
}
