package com.fortes.rh.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.fortes.rh.util.ArquivoUtil;

public class SetupListener implements ServletContextListener
{

	private static final String SYS_PATH = "sys.path";

	private static final String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";

	private static final String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";

	private static final String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";

	private static final String HIBERNATE_DIALECT = "hibernate.dialect";

	private static final String HIBERNATE_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";

	private Logger logger = Logger.getLogger(SetupListener.class);

	String syspath;

	String systemConfigPath;

	private Locale pt_BR = new Locale("pt", "BR");

	/**
	 * Inicializa o contexto.
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		Locale.setDefault(pt_BR); // define idioma pt_BR como padrão
		
		String pastaSystemConf = getPastaSystemConf(servletContextEvent);
		
		ArquivoUtil.setContextName("fortes"+getPastaSystemConf(servletContextEvent));
		ArquivoUtil.setRhHome(pastaSystemConf);
		systemConfigPath = ArquivoUtil.getRhHome() + File.separatorChar + "system.conf";

		logger.info("------------- RH -------------");
		logger.info("Define Idioma Padrão para 'pt_BR'.");
		logger.info("Arquivo de configuração local: " + systemConfigPath);

		try
		{
			Properties configuracaoDoCliente = this.carregaConfiguracaoDoCliente();
			this.syspath = configuracaoDoCliente.getProperty(SYS_PATH);
			this.defineConfiguracaoDoHibernate(configuracaoDoCliente);
			this.updateDataBase(configuracaoDoCliente);
			logger.info("Executado com sucesso!");
		} catch (Exception e)
		{
			logger.fatal("Erro no processo de atualização: " + e.getMessage());
		} finally
		{
			logger.info("-------------------------------");
		}
	}

	private String getPastaSystemConf(ServletContextEvent servletContextEvent)
	{
		String context = null;
		try {
			context = servletContextEvent.getServletContext().getAttribute("javax.servlet.context.tempdir").toString();
			context = context.substring(context.lastIndexOf(File.separatorChar+"fortes")+7);
		} catch (Exception e) {
			logger.fatal("Erro ao identificar o contexto da aplicação. contexto = "+context);
			System.exit(0);
		}
		return context;
	}

	/**
	 * Atualiza banco de dados com as modificaçoes necessarias.
	 */
	private void updateDataBase(Properties configuracaoDoCliente) throws Exception
	{
		JDBCConnection jdbcConn = new JDBCConnection(configuracaoDoCliente);
		String versao = jdbcConn.getVersao();
		logger.info("Iniciando processo de atualização.");
		
		if(versao.split("\\.")[1].equals("1")){
			String queries[] = ScriptReader.getComandos(new File(this.getUpdateSqlFullPathOld()), versao);
			jdbcConn.execute(queries);
			versao = "1.1.176.208";
		}
		
		String queries[] = ScriptReader.getComandos(new File(this.getUpdateSqlFullPath()), versao);
		jdbcConn.execute(queries);
	}

	/**
	 * Define configuracao do Hibernate em tempo de execução.
	 */
	private void defineConfiguracaoDoHibernate(Properties configuracaoDoCliente)
	{
		logger.info("Iniciando carregamento de informações para conexão com o banco de dados.");
		logger.info("Arquivo de configuração do Hibernate: " + this.getHibernateConfigFullPath());
		Properties configuracao = this.carregaConfiguracaoDoHibernate();
		logger.info("Arquivo carregado com sucesso.");
		logger.info("Salvando arquivo de configuração do Hibernate.");
		this.efetuaMergeNaConfiguracaoDoHibernate(configuracao, configuracaoDoCliente);
		this.salvaConfiguracaoDoHibernate(configuracao);
		logger.info("Arquivo salvo com sucesso.");
	}

	/**
	 * Efetua merge na configuração do Hibernate de acordo com a configuração do
	 * cliente.
	 */
	private void efetuaMergeNaConfiguracaoDoHibernate(Properties configuracao, Properties configuracaoDoCliente)
	{
		configuracao.setProperty(HIBERNATE_CONNECTION_DRIVER_CLASS, configuracaoDoCliente.getProperty(HIBERNATE_CONNECTION_DRIVER_CLASS).trim());
		configuracao.setProperty(HIBERNATE_DIALECT, configuracaoDoCliente.getProperty(HIBERNATE_DIALECT).trim());
		configuracao.setProperty(HIBERNATE_CONNECTION_URL, configuracaoDoCliente.getProperty(HIBERNATE_CONNECTION_URL).trim());
		configuracao.setProperty(HIBERNATE_CONNECTION_USERNAME, configuracaoDoCliente.getProperty(HIBERNATE_CONNECTION_USERNAME).trim());
		configuracao.setProperty(HIBERNATE_CONNECTION_PASSWORD, configuracaoDoCliente.getProperty(HIBERNATE_CONNECTION_PASSWORD).trim());
	}

	/**
	 * Salva configuração do Hibernate.
	 */
	private void salvaConfiguracaoDoHibernate(Properties configuracao)
	{
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(new File(this.getHibernateConfigFullPath()));
			configuracao.store(fos, "Salvando arquivo para inicialização do sistema.");
		} catch (Exception e)
		{
			throw new RuntimeException("Erro ao tentar salvar arquivo de configuração: " + e.getMessage());
		} finally
		{
			try
			{
				if(fos != null)
					fos.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void salvaConfiguracaoDoCliente(Properties configuracao)
	{
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(new File(systemConfigPath));
			configuracao.store(fos, null);
		} catch (Exception e)
		{
			throw new RuntimeException("Erro ao tentar salvar arquivo de system.conf: " + e.getMessage());
		} finally
		{
			try
			{
				if(fos != null)
					fos.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Carrega configuração do Hibernate.
	 */
	private Properties carregaConfiguracaoDoHibernate()
	{
		Properties configuracao = new Properties();
		FileInputStream is = null;
		try
		{
			is = new FileInputStream(new File(this.getHibernateConfigFullPath()));
			configuracao.load(is);
		} catch (Exception e)
		{
			throw new RuntimeException("Erro ao tentar carregar arquivo de configuração: " + e.getMessage());
		} finally
		{
			try
			{
				if(is != null)
					is.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return configuracao;
	}

	/**
	 * Carrega configuração do cliente.
	 */
	private Properties carregaConfiguracaoDoCliente()
	{
		Properties configuracao = new Properties();
		FileInputStream is = null;
		try
		{
			is = new FileInputStream(new File(systemConfigPath));
			configuracao.load(is);
		} catch (Exception e)
		{
			throw new RuntimeException("Erro ao tentar carregar arquivo de configuração: " + e.getMessage());
		} finally
		{
			try
			{
				if(is != null)
					is.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return configuracao;
	}

	/**
	 * Retorna caminho completo do update.sql.
	 */
	private String getUpdateSqlFullPath()
	{
		return syspath + File.separatorChar + "WEB-INF" + File.separatorChar + "metadata" + File.separatorChar + "update.sql";
	}
	
	private String getUpdateSqlFullPathOld()
	{
		return syspath + File.separatorChar + "WEB-INF" + File.separatorChar + "metadata" + File.separatorChar + "updateOld.sql";
	}

	private String getMetadataSqlFullPath()
	{
		return syspath + File.separatorChar + "WEB-INF" + File.separatorChar + "metadata" + File.separatorChar;
	}
	
	/**
	 * Retorna caminho completo do hibernate.properties.
	 */
	private String getHibernateConfigFullPath()
	{
		return syspath + File.separatorChar + "WEB-INF" + File.separatorChar + "classes" + File.separatorChar + "hibernate.properties";
	}

	/**
	 * Limpa recursos.
	 */
	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		this.syspath = null;
	}

	public String updateConf(String senhaBD) throws Exception
	{
		Properties configuracaoDoCliente = carregaConfiguracaoDoCliente();
		configuracaoDoCliente.setProperty("hibernate.connection.password", senhaBD);
		salvaConfiguracaoDoCliente(configuracaoDoCliente);
		
		JDBCConnection jdbcConn = new JDBCConnection(configuracaoDoCliente);
		return jdbcConn.getVersao();
	}

	//utilizado na tela bdError.ftl, caso o banco fortesrh não exista
//	public String criarBD() throws Exception
//	{
//		try
//		{
//			Properties configuracaoDoCliente = carregaConfiguracaoDoCliente();
//			this.syspath = configuracaoDoCliente.getProperty(SYS_PATH);
//			JDBCConnection jdbcConn = new JDBCConnection(configuracaoDoCliente);
//			
//			if(jdbcConn.getQtdTabelas().equals(0))
//			{
//				logger.info("Iniciando processo de criação do BD...");
//				String queries[] = ScriptReader.getComandos(new File(this.getMetadataSqlFullPath() + "create.sql"), "0");
//				if(queries.length == 0)
//					throw new Exception("Arquivo create.sql não carregado.");
//					
//				jdbcConn.execute(queries);
//				
//				logger.info("... inserindo registros padrões.");
//				queries = ScriptReader.getComandos(new File(this.getMetadataSqlFullPath() + "create_data.sql"), "0");
//
//				if(queries.length == 0)
//					throw new Exception("Arquivo create_data.sql não carregado.");
//				
//				jdbcConn.execute(queries);
//				
//				logger.info("BD fortesrh GERADO COM SUCESSO!!!");
//			}
//			else
//				throw new Exception("Banco de dados já existe.");
//
//			return jdbcConn.getVersao();
//			
//		} catch (Exception e)
//		{
//			throw e;
//		}
//	}

}