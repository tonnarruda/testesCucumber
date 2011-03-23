package com.fortes.rh.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import com.fortes.rh.util.ArquivoUtil;

public class Log4jInitListener implements ServletContextListener {

	public static final String FORTESRH_LOGGING_DIR = "fortesrh.logging.dir";
	
	String loggingDir = ArquivoUtil.getLoggingPath();
	String log4jConfigPath = "log4j-init.properties";

	public void contextInitialized(ServletContextEvent ctx) {
		Properties configuracao = this.carregaArquivoDeConfiguracaoDoLog4j();
		configuracao.put(FORTESRH_LOGGING_DIR, loggingDir); // seta diretorio de destino
		PropertyConfigurator.configure(configuracao);
	}
	
	/**
	 * Carrega configuração do Log4j.
	 */
	private Properties carregaArquivoDeConfiguracaoDoLog4j() {
		Properties configuracao = new Properties();
		InputStream is = null;
		try {
			is = this.getClass().getClassLoader().getResourceAsStream(log4jConfigPath);
			configuracao.load(is);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao tentar carregar arquivo de configuração do Log4J: " + e.getMessage());
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return configuracao;
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		//
	}
}
