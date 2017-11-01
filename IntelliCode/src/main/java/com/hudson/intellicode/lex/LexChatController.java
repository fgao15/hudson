package com.hudson.intellicode.lex;

import com.hudson.intellicode.config.Configuration;
import com.hudson.intellicode.spi.IChatController;

public class LexChatController implements IChatController {

	private LexCommunicator lex = null;
	public LexChatController(Configuration config) {
		lex = new LexCommunicator(config);
	}
	
	@Override
	public String send(String inputText) {
		return lex.service(inputText);
	}

}
