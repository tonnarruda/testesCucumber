package com.fortes.rh.test.util;

import junit.framework.TestCase;
import mockit.Mockit;

import com.fortes.model.type.FileUtil;
import com.fortes.rh.test.util.mockObjects.MockFileUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.util.ArquivoUtil;
import com.opensymphony.webwork.ServletActionContext;

@SuppressWarnings("static-access")
public class ArquivoUtilTest extends TestCase
{
    private ArquivoUtil arquivoUtil;
    private static String pathToCurriculos = "com/fortes/rh/test/util/curriculos/";

    protected void setUp()
    {

    }

    public void testGetRhHome()
    {
    	//Tem que testar se o arquivo fortes_home.properties não existe
    	assertNotNull(arquivoUtil.getRhHome());
    }
    
    public void testGetLoggingPath()
    {
    	assertEquals(System.getenv("FORTES_HOME") + java.io.File.separatorChar + "RH"  + java.io.File.separatorChar + "logging", arquivoUtil.getLoggingPath());
    }

    public void testGetDbBackupPath()
    {
    	assertEquals(System.getenv("FORTES_HOME") + java.io.File.separatorChar + "RH"  + java.io.File.separatorChar + "backup_db", arquivoUtil.getDbBackupPath());
    }

    public void testGetContents ()
    {
    	String systemConfigPath = ArquivoUtil.getRhHome() + java.io.File.separatorChar + "system.conf";
    	java.io.File aFile = new java.io.File(systemConfigPath);
    	assertNotNull(arquivoUtil.getContents(aFile));

    	systemConfigPath = ArquivoUtil.getRhHome() + java.io.File.separatorChar + "babau.conf";
    	assertEquals("", arquivoUtil.getContents(new java.io.File(systemConfigPath)));
    }
    
    public void testGetSystemConf()
    {
    	assertNotNull(arquivoUtil.getSystemConf());
    }
    
    public void testGetReportSource()
    {
    	Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
    	assertNotNull(arquivoUtil.getReportSource("babau.jrxml"));
    }
    
    public void testDeletaArquivos()
    {
    	arquivoUtil.deletaArquivos("curriculos", new String[]{"a"});
    }
    
    public void testSalvaArquivo()
    {
    	com.fortes.model.type.File file = new com.fortes.model.type.File();
    	file.setName("teste.txt");
    	
    	assertNull(arquivoUtil.salvaArquivo("curriculos", file, true));
    	
    	Mockit.redefineMethods(FileUtil.class, MockFileUtil.class);
    	
    	assertNotNull(arquivoUtil.salvaArquivo("curriculos", file, true));
    }
    
    public void testGetPathExterno()
    {
    	assertNotNull(ArquivoUtil.getPathExterno());
    }

    //NAO APAGAR, ta sendo testado no momento que a versão é fechada, esse teste quebra no Hudsom e Coverage
//    public void testGetResourceBytes()
//    {
//    	byte[] bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "windows-1252.txt");
//    	
//    	assertNotNull(bytes);    	
//    }
//    
//    public void testRetornaTipoCharSet() throws Exception
//    {
//    	byte[] bytes;
//
//    	// Este arquivo é detectado como WINDOWS-1252 pois não possui caracteres que possam diferencia-lo
//    	// de um ISO-8859-1 (Latin1)
//    	bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "ISO-8859-1.txt");
//    	assertEquals("WINDOWS-1252", ArquivoUtil.retornaTipoCharSet(bytes));    	
//
//    	bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "windows-1252.txt");
//    	assertEquals("WINDOWS-1252", ArquivoUtil.retornaTipoCharSet(bytes));
//    	
//    	bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "curriculo1.txt");
//    	assertEquals("WINDOWS-1252", ArquivoUtil.retornaTipoCharSet(bytes));
//    	
//    	//bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "windows-1252_chars.txt");
//    	//assertEquals("WINDOWS-1252", ArquivoUtil.retornaTipoCharSet(bytes));
//
//    	bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "UTF-8.txt");
//    	assertEquals("UTF-8", ArquivoUtil.retornaTipoCharSet(bytes));    	
//
//    	bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "UTF_windows-1252_chars.txt");
//    	assertEquals("UTF-8", ArquivoUtil.retornaTipoCharSet(bytes));
//    }
//
//    //NAO APAGAR, ta sendo testado no momento que a versão é fechada, esse teste quebra no Hudsom e Coverage
//    public void testConvertToLatin1Compatible() throws Exception
//    {
//    	byte[] bytes;
//
//    	bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "UTF_windows-1252_chars.txt");
//		String converted = ArquivoUtil.convertToLatin1Compatible(bytes);		
//		String target = new String (ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "Latin1-compat.txt"));
//
//		assertEquals(converted.trim(), target.trim());
//    	
//		bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "windows-1252_chars.txt");
//		converted = ArquivoUtil.convertToLatin1Compatible(bytes);		
//		target = new String (ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "Latin1-compat.txt"));
//		
//    	assertEquals(converted.trim(), target.trim());
//    }
//FIM DO COMENTARIO


}