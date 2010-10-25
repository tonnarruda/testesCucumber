package com.fortes.rh.test.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

import junit.framework.TestCase;

import com.fortes.rh.util.Zip;

public class ZipTest extends TestCase
{
	Zip zip;

	protected void setUp()
	{
		 zip = new Zip();
	}
	
	@Override
	protected void tearDown() throws Exception {
		new File("file.txt").delete();
		new File("file.fortesrh").delete();
	}

	public void testCompress() throws IOException
	{
		File file = new File("file.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write("aString");
        out.close();

		ZipOutputStream zipOutputStream = zip.compress(new java.io.File[] { file }, "file", ".fortesrh");

		assertNotNull("Test 1", zipOutputStream);

		Exception exp = null;

		try
		{
			zipOutputStream = zip.compress(new java.io.File[] { null }, "file", ".fortesrh");
		}
		catch (Exception e)
		{
			exp = e;
		}

		assertNull("Test 2", exp);
		assertNotNull("Test 3", zipOutputStream);

	}

	public void testIdentificaDiretorioDoArquivoOriginal(){
		String path = ajustaBarrasDeAcordoComOS("c:\\java\\eclipse\\arquivo.xml.fortesrh");
		String diretorioDoArquivo = zip.identificaDiretorioDoArquivoOriginal(path);
		assertEquals(ajustaBarrasDeAcordoComOS("c:\\java\\eclipse"), diretorioDoArquivo);
	}
	
	private String ajustaBarrasDeAcordoComOS(String path) {
		return path.replace("\\", File.separator);
	}

	public void testIdentificaDiretorioDoArquivoOriginalNull()
	{
		String path = null;
		assertEquals(null, zip.identificaDiretorioDoArquivoOriginal(path));
	}

	public void testUnzip() throws IOException
	{
		File file = new File("file.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write("aString");
        out.close();

		zip.compress(new java.io.File[] { file }, "file", ".fortesrh");

		zip.unzip("file.fortesrh", "file.txt");
		zip.unzip("file.fortesrh", "");

	}

}
