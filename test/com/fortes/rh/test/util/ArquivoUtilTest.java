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
	
	/**
	 * Os testes dos métodos testGetResourceBytes, testRetornaTipoCharSet, testConvertToLatin1Compatible estão no arquivo TestsNoIncludeAllUnitTest.java
	 */
	
    private ArquivoUtil arquivoUtil;

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
    
    public void testGetPathReport()
    {
    	assertNotNull(ArquivoUtil.getPathReport());
    }
    
    public void testGetPathBackGroundRelatorio()
    {
    	assertNotNull(ArquivoUtil.getPathBackGroundCartao("teste"));
    }
}