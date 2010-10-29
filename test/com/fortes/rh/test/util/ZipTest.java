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
        String dir = this.getClass().getResource(".").getPath();
		String path = dir + "arquivo.xml.fortesrh";
		dir = dir.substring(0, dir.lastIndexOf("/"));
		String diretorioDoArquivo = zip.identificaDiretorioDoArquivoOriginal(path);
		assertEquals(dir, diretorioDoArquivo);
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

