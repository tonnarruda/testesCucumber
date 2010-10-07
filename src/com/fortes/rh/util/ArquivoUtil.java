package com.fortes.rh.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import org.mozilla.universalchardet.UniversalDetector;

import com.fortes.model.type.File;
import com.fortes.model.type.FileUtil;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class ArquivoUtil
{
	private static String FORTES_HOME = "FORTES_HOME";
	private static String RH_HOME = null;

	public static String getRhHome()
	{
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
	
	public static String getLoggingPath() {
		return ArquivoUtil.getRhHome() + java.io.File.separatorChar + "logging";
	}
	
	public static String getDbBackupPath() {
		return ArquivoUtil.getRhHome() + java.io.File.separatorChar + "backup_db";
	}
	
	public static Properties getSystemConf() {
		String systemConfigPath = ArquivoUtil.getRhHome() + java.io.File.separatorChar + "system.conf";
		Properties configuracao = new Properties();
		FileInputStream is = null;
		try {
			is = new FileInputStream(new java.io.File(systemConfigPath));
			configuracao.load(is);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao tentar carregar arquivo de configuração: " + e.getMessage());
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

	public static java.io.File salvaArquivo(String pasta, File arquivo, boolean renomear)
	{
		String path = "";

		if (pasta != null)
			path = getRhHome() + java.io.File.separatorChar + "anexos" + java.io.File.separatorChar + pasta + java.io.File.separatorChar;

		String nomeArquivo = arquivo.getName();
		java.io.File arquivoSalvo = null;

		if (renomear)
			nomeArquivo = new Date().getTime() + arquivo.getName().substring(arquivo.getName().lastIndexOf("."));

		try
		{
			arquivoSalvo = FileUtil.bytesToFile(arquivo.getBytes(), path + java.io.File.separatorChar + nomeArquivo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return arquivoSalvo;
	}

	public static void deletaArquivos(String pasta, String[] arquivos)
	{
		String path = "";

		if (pasta != null)
			path = getRhHome() + java.io.File.separatorChar + "anexos" + java.io.File.separatorChar + pasta + java.io.File.separatorChar;

		for (int i = 0; i < arquivos.length; i++)
		{
			java.io.File arquivo = new java.io.File(path + java.io.File.separatorChar + arquivos[i]);

			if (arquivo.exists() && arquivo.isFile())
				arquivo.delete();
		}
	}

	/*
	 * Retorna o array de bytes de resourcePath.
	 * O caminho do recurso será relativo a localização de baseClass. 
	 */
	public static byte[] getSrcResourceBytes(String resourcePath)
	{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String basePath = loader.getResource(".").getFile().replace("\\", "/").replace("%20", " ");
		String testPath = basePath.replace("web/WEB-INF/classes/", "") + "test/";
		String path = testPath + resourcePath;
		path = path.replace('/', java.io.File.separatorChar);
    	java.io.File file = new java.io.File(path);

    	byte[] bytes = null;
    	FileInputStream input = null;
		try {
			input = new FileInputStream(file);
	    	bytes = new byte[(int) file.length()];
	    	input.read(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bytes;
	}
	
	public static String getTexto(File arquivo)
	{
		StringBuilder texto = new StringBuilder();

		try
		{
			if (arquivo != null)
			{
				
				FileReader fileReader = new FileReader(arquivo.getFileArchive());
				BufferedReader reader = new BufferedReader(fileReader);

				String linha;
				while ((linha = reader.readLine()) != null)
					texto.append(linha + "\n");

				reader.close();
				fileReader.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return texto.toString();
	}

	public static String getTextoInputStream(File arquivo, String encoding)
	{
		StringBuilder texto = new StringBuilder();

		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo.getFileArchive()), encoding));

			String linha;
			while ((linha = reader.readLine()) != null)
				texto.append(linha + "\n");

			reader.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return texto.toString();
	}

	public static String retornaTipoCharSet(byte[] bytes)
	{
		String encoding = "UTF-8"; // padrao
		
		// Detectando com a juniversalchardet (detecta windows-1252, entre outros)
		UniversalDetector dt = new UniversalDetector(null);
		dt.handleData(bytes, 0, bytes.length);
		dt.dataEnd();
		String detectedc = dt.getDetectedCharset();
		
		if (detectedc != null)
			//// Força o encoding para ISO-8859-1 (LATIN1) para evitar erros com a função "normalizar" no postgres
			////encoding = (detectedc.equals("WINDOWS-1252"))?"ISO-8859-1":detectedc;
			encoding = detectedc;
		else
		{
			// Detectando com a icu4j
			CharsetDetector tipoCharSet = new CharsetDetector();
		    tipoCharSet.setText(bytes);
			CharsetMatch a = tipoCharSet.detect();
			encoding = a.getName();
			System.out.println(a.getConfidence());
		}
		
	    return encoding;
	}

	public static java.io.File getArquivo(String arquivo, String pasta)
	{
		String path = getRhHome() + java.io.File.separatorChar + "anexos" + java.io.File.separatorChar + pasta;
		java.io.File file = new java.io.File(path + java.io.File.separatorChar + arquivo);
		return file;
	}

	public static String getPathLogoEmpresa()
	{
		StringBuilder path = new StringBuilder(getRhHome());
		path.append(java.io.File.separatorChar);
		path.append("anexos");
		path.append(java.io.File.separatorChar);
		path.append("logoEmpresas");
		path.append(java.io.File.separatorChar);

		return path.toString();
	}

	public static String getPath(String pasta)
	{
		return getRhHome() + java.io.File.separatorChar + "anexos" + java.io.File.separatorChar + pasta + java.io.File.separatorChar;
	}

	public static String getPathExterno()
	{
		StringBuilder path = new StringBuilder(getRhHome());
		path.append(java.io.File.separatorChar);
		path.append("externo");
		path.append(java.io.File.separatorChar);

		return path.toString();
	}
	
	public static String convertToLatin1Compatible(byte[] bytes)
	{
		String charSetName = ArquivoUtil.retornaTipoCharSet(bytes);
		Charset charsetFrom = Charset.forName(ArquivoUtil.retornaTipoCharSet(bytes));
		Charset charsetTo = Charset.forName("ISO-8859-1");

		ByteBuffer inputBuffer = ByteBuffer.wrap(bytes);
		CharBuffer data = charsetFrom.decode(inputBuffer);
		
		ByteBuffer outputBuffer;
		
		if ("WINDOWS-1252".equalsIgnoreCase(charSetName) || charSetName.equalsIgnoreCase("UTF-8"))
		{
			String strTmp = data.toString();

			// ver http://en.wikipedia.org/wiki/Windows-1252 (differences from ISO-8859-1 marked with thick borders and asterisks (*))
			strTmp = strTmp.replaceAll("\u2014|\u2013", "-");
			strTmp = strTmp.replaceAll("\u2022", "*");
			strTmp = strTmp.replaceAll("\u201E|\u201C|\u201D", "\"");
			strTmp = strTmp.replaceAll("\u201A|\u2018|\u2019", "'");
			
			// encode ISO-8559-1
			outputBuffer = charsetTo.encode(strTmp);
		}
		else
		{
			// encode ISO-8559-1			
			outputBuffer = charsetTo.encode(data);
		}
		
		String result = "";
		try
		{
			result = new String(outputBuffer.array(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
}