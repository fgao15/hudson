package com.hudson.intellicode.config;

import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Configuration {

	private static final Configuration DEFAULT = getConfiguration(
			Configuration.class.getResourceAsStream("/intellicode.yaml"));
	private String a;
	private String b;
	private String s;
	private String v;

	public static Configuration defaultConfiguration() {
		return DEFAULT;
	}

	public static Configuration getConfiguration(InputStream is) {
		Configuration config = null;
		try
		{
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			config = mapper.readValue(is, Configuration.class);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return config;
	}

	public String getA() {
		return a;
	}

	public String getS() {
		return s;
	}

	public String getV() {
		return v;
	}
	
	public String getB() {
		return b;
	}
}
