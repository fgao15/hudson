package com.hudson.intellicode.lex;

import org.apache.commons.text.RandomStringGenerator;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.amazonaws.services.lexruntime.model.PostTextRequest;
import com.amazonaws.services.lexruntime.model.PostTextResult;
import com.hudson.intellicode.config.Configuration;

public class LexCommunicator {

	private static final String LATEST_VERSION = "$LATEST";
	private AmazonLexRuntime lexRuntime = null;
	private String sessionId = null;
	private Configuration config = null;
	public LexCommunicator(Configuration config) {
		sessionId = generateSessionId();
		this.config = config;
		buildRuntime();
	}
	
	private void buildRuntime()
	{
		AmazonLexRuntimeClientBuilder lexRuntimeClientBuilder = AmazonLexRuntimeClientBuilder.standard();

		AWSCredentials credentials = new BasicAWSCredentials(config.getA(), config.getS());
		AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
		lexRuntimeClientBuilder.setCredentials(credentialsProvider);
		lexRuntimeClientBuilder.setRegion(Regions.US_EAST_1.getName());
		
		lexRuntime = lexRuntimeClientBuilder.build();
	}
	
	public String service(String inputText)
	{
		PostTextRequest postTextRequest = new PostTextRequest();
		postTextRequest.setBotName(config.getB());
		postTextRequest.setBotAlias(config.getV() == null ? LATEST_VERSION : config.getV());
		postTextRequest.setInputText(inputText);
		postTextRequest.setUserId(sessionId);
		PostTextResult postTestResult = lexRuntime.postText(postTextRequest);
		String intentName = postTestResult.getIntentName();
		String message = postTestResult.getMessage();
		System.out.println(intentName + ", Request: " + inputText);
		System.out.println(intentName + ", Response: " + message);
		return message;
	}
	
	private static String generateSessionId()
	{
		RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();;
		return randomStringGenerator.generate(12);
	}

	@Override
	protected void finalize() throws Throwable {
		if (lexRuntime != null)
		{
			lexRuntime.shutdown();
		}
	}
	
	
}
