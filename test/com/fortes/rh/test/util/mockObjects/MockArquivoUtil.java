package com.fortes.rh.test.util.mockObjects;

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
}
