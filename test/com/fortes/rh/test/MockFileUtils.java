package com.fortes.rh.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;

public class MockFileUtils {
	
	public static final Logger logger = Logger.getLogger(MockFileUtils.class);

	public static void copyURLToFile(URL arg0, File arg1) throws IOException {
		logger.warn("Executando MockFileUtils.copyURLToFile()...");
	}
	
}
