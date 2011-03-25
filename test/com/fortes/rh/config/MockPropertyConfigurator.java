package com.fortes.rh.config;

import java.util.Properties;

import org.apache.log4j.Logger;

public class MockPropertyConfigurator {
	
	private static Logger logger = Logger.getLogger(MockPropertyConfigurator.class);
	public static Properties properties;

	public static void configure(Properties properties) {
		MockPropertyConfigurator.properties = properties;
		logger.warn("Executando MockPropertyConfigurator.configure()..");
	}
	
}
