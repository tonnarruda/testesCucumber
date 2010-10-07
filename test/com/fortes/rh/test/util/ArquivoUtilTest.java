package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.util.ArquivoUtil;

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
    	assertNotNull(arquivoUtil.getRhHome());
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
    
    public void testGetTextoInputStream()
    {
    	com.fortes.model.type.File file = new com.fortes.model.type.File();
    	assertEquals("", arquivoUtil.getTextoInputStream(file, "ISO-8859-2"));
    }

    public void testGetTexto()
    {
    	assertEquals("", arquivoUtil.getTexto(null));
    }

    public void testDeletaArquivos()
    {
    	arquivoUtil.deletaArquivos("curriculos", new String[]{"a"});
    }

    public void testSalvaArquivo()
    {
    	com.fortes.model.type.File file = new com.fortes.model.type.File();
    	file.setName("teste.txt");

    	assertEquals(null, arquivoUtil.salvaArquivo("curriculos", file, true));
    }

    public void testGetPathExterno()
    {
    	assertNotNull(ArquivoUtil.getPathExterno());
    }

}