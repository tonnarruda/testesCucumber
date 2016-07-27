package com.fortes.rh.test.util;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpException;

import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.HttpUtil;

public class TestsNoIncludeAllUnitTest extends TestCase
{
    private static String pathToCurriculos = "com/fortes/rh/test/util/curriculos/";

    protected void setUp()
    {

    }

    // Testes da classe ArquivoUtil.java
    public void testGetResourceBytes()
    {
    	byte[] bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "windows-1252.txt");
    	
    	assertNotNull(bytes);    	
    }
    
    public void testRetornaTipoCharSet() throws Exception
    {
    	byte[] bytes;

    	// Este arquivo é detectado como WINDOWS-1252 pois não possui caracteres que possam diferencia-lo
    	// de um ISO-8859-1 (Latin1)
    	bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "ISO-8859-1.txt");
    	assertEquals("WINDOWS-1252", ArquivoUtil.retornaTipoCharSet(bytes));    	

    	bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "windows-1252.txt");
    	assertEquals("WINDOWS-1252", ArquivoUtil.retornaTipoCharSet(bytes));
    	
    	bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "curriculo1.txt");
    	assertEquals("WINDOWS-1252", ArquivoUtil.retornaTipoCharSet(bytes));
    	
    	//bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "windows-1252_chars.txt");
    	//assertEquals("WINDOWS-1252", ArquivoUtil.retornaTipoCharSet(bytes));

    	bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "UTF-8.txt");
    	assertEquals("UTF-8", ArquivoUtil.retornaTipoCharSet(bytes));    	

    	bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "UTF_windows-1252_chars.txt");
    	assertEquals("UTF-8", ArquivoUtil.retornaTipoCharSet(bytes));
    }

    public void testConvertToLatin1Compatible() throws Exception
    {
    	byte[] bytes;

    	bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "UTF_windows-1252_chars.txt");
		String converted = ArquivoUtil.convertToLatin1Compatible(bytes);		
		String target = new String (ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "Latin1-compat.txt"));

		assertEquals(converted.trim(), target.trim());
    	
		bytes = ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "windows-1252_chars.txt");
		converted = ArquivoUtil.convertToLatin1Compatible(bytes);		
		target = new String (ArquivoUtil.getSrcResourceBytes(pathToCurriculos + "Latin1-compat.txt"));
		
    	assertEquals(converted.trim(), target.trim());
    }

    // Testes da classe StringUtil.java
	public void testGetHTML() throws HttpException, IOException
	{
		String url = "http://m.correios.com.br/movel/buscaCepConfirma.do?cepEntrada=60743-760&tipoCep=&cepTemp=&metodo=buscarCep";
		
		assertTrue(HttpUtil.getHtmlViaPost(url).contains("respostadestaque"));
	}

}