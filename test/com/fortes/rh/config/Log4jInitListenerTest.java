package com.fortes.rh.config;

import java.util.Properties;

import javax.servlet.ServletContextEvent;

import junit.framework.TestCase;
import mockit.Mockit;

import org.apache.log4j.PropertyConfigurator;

import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.util.ArquivoUtil;

public class Log4jInitListenerTest extends TestCase {

	Log4jInitListener listener;
	private ServletContextEvent ctx;
	
	public void setUp() {
		Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
		Mockit.redefineMethods(PropertyConfigurator.class, MockPropertyConfigurator.class);
		listener = new Log4jInitListener();
	}
	
	public void testContextInitialized() {
		
		listener.contextInitialized(ctx);
		
		assertEquals("diretorio de logs do fortesrh",
				MockArquivoUtil.DIRETORIO_DE_LOGS_DO_FORTESRH, 
				getDiretorioDeLogsDoFortesRH());
	}
	
	private static String getDiretorioDeLogsDoFortesRH() {
		Properties properties = MockPropertyConfigurator.properties;
		return properties.get(Log4jInitListener.FORTESRH_LOGGING_DIR).toString();
	}

}
