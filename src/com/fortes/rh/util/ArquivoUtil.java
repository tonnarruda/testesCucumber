package com.fortes.rh.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.mozilla.universalchardet.UniversalDetector;

import com.fortes.model.type.File;
import com.fortes.model.type.FileUtil;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.opensymphony.webwork.ServletActionContext;

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
	 * está sendo usado pelo teste para fechar versão NÃO APAGAR
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

	public static String getContents(java.io.File aFile) 
	{
	    //...checks on aFile are elided
	    StringBuilder contents = new StringBuilder();
	    
	    try {
	      //use buffering, reading one line at a time
	      //FileReader always assumes default encoding is OK!
	      BufferedReader input =  new BufferedReader(new FileReader(aFile));
	      try {
	        String line = null; //not declared within while loop
	        /*
	        * readLine is a bit quirky :
	        * it returns the content of a line MINUS the newline.
	        * it returns null only for the END of the stream.
	        * it returns an empty String if two newlines appear in a row.
	        */
	        while (( line = input.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    
	    return contents.toString();
	  }
	
	public static String getReportSource(String report)
	{
		String path = ServletActionContext.getServletContext().getRealPath("/WEB-INF/report/") + java.io.File.separator + report;
		path = path.replace("\\", "/").replace("%20", " ");
		path = path.replace('/', java.io.File.separatorChar);
    	java.io.File file = new java.io.File(path);

		return getContents(file);
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

	public static String getPathExterno()
	{
		StringBuilder path = new StringBuilder(getRhHome());
		path.append(java.io.File.separatorChar);
		path.append("externo");
		path.append(java.io.File.separatorChar);

		return path.toString();
	}

	public static String getPathExternoEmpresa(Long empresaId)
	{
		StringBuilder path = new StringBuilder(getRhHome());
		path.append(java.io.File.separatorChar);
		path.append("externo_" + empresaId);
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
	
    public static void copiar(java.io.File base, java.io.File destino) throws IOException
    {
		if(base.isDirectory())
		{
			if(!destino.exists())
			{
			   destino.mkdir();
			}
		
			String files[] = base.list();
		
			for (String file : files) 
			{
			   java.io.File srcFile = new java.io.File(base, file);
			   java.io.File destFile = new java.io.File(destino, file);
			   copiar(srcFile, destFile);
			}
		}
		else
		{
			InputStream in = new FileInputStream(base);
			OutputStream out = new FileOutputStream(destino); 
			
			byte[] buffer = new byte[1024];
			
			int length;
			
			while ((length = in.read(buffer)) > 0)
			{
			   out.write(buffer, 0, length);
			}
			
			in.close();
			out.close();
		}
	}

    public static void montaRelatorio(Empresa empresa, String subject, String body, Collection<String> emailsCollection, Map<String,Object> parametros, Collection dataSource, Mail mail, String jasperName) throws Exception 
	{
		try
		{
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(parametros.get("SUBREPORT_DIR") + jasperName);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(dataSource));

			byte[] output = JasperExportManager.exportReportToPdf(jasperPrint);
			
			ByteArrayDataSource file = new ByteArrayDataSource(output, "application/pdf"){
				@Override
				public String getName() {
					return "anexo.pdf";
				}
			};
			
			DataSource[] files = new DataSource[]{file};
			String[] emails = new String[emailsCollection.size()];
			emails = emailsCollection.toArray(emails);
			mail.send(empresa, subject, files, body, emails);		
		}
		catch (JRException e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
}