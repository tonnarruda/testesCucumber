package com.fortes.rh.test.util.mockObjects;

import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import com.fortes.model.type.File;

public class MockArquivoUtil
{
	public static java.io.File salvaArquivo(String pasta, File arquivo, boolean renomear)
	{
		return new java.io.File("arquivo");
	}
	
	public static void deletaArquivos(String pasta, String[] arquivos)
	{

	}

	public static String getPathLogoEmpresa()
	{
		return "path/da/logo";
	}
	
	public static String retornaTipoCharSet(byte[] bytes)
	{
		return "UTF-8";
	}
	
	public static final String DIRETORIO_DE_LOGS_DO_FORTESRH = "/diretorio/de/logs/do/fortesrh/";

	public static String getLoggingPath() {
		return DIRETORIO_DE_LOGS_DO_FORTESRH;
	}

    public static DataSource[] montaRelatorio(Map<String,Object> parametros, Collection dataSource, String jasperName) throws Exception
    {
    	return new DataSource[]{new ByteArrayDataSource("", "application/pdf")};
    }
    
	private static String FORTES_HOME = "FORTES_HOME";
	private static String RH_HOME = null;

	public static String getRhHome()
	{
		System.getProperties();
		
		if(RH_HOME == null)
		{
			//Configuração feita para instalar mais de um RH no mesmo tomcat, basta criar fortes_home.properties com o "name" da var de ambiente 
			try
			{
				ResourceBundle bundle = ResourceBundle.getBundle("fortes_home");
				FORTES_HOME = bundle.getString("name");
			} catch (Exception e)
			{
			}
			
			RH_HOME = System.getenv(FORTES_HOME) + java.io.File.separatorChar + "RH";
		}
		
		return RH_HOME;
	}
    
	public static java.io.File getArquivo(String arquivo, String pasta)
	{
		String path = getRhHome() + java.io.File.separatorChar + "externo" + java.io.File.separatorChar;
		java.io.File file = new java.io.File(path + java.io.File.separatorChar + arquivo);
		return file;
	}

}
