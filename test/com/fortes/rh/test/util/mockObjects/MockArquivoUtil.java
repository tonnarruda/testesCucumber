package com.fortes.rh.test.util.mockObjects;

import java.util.Collection;
import java.util.Map;

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

}
