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

		ZipOutputStream zipOutputStream = zip.compress(new java.io.File[] { file }, "file", ".fortesrh", true);

		assertNotNull("Test 1", zipOutputStream);

		Exception exp = null;

		try
		{
			zipOutputStream = zip.compress(new java.io.File[] { null }, "file", ".fortesrh", true);
		}
		catch (Exception e)
		{
			exp = e;
		}

		assertNull("Test 2", exp);
		assertNotNull("Test 3", zipOutputStream);

	}

	public void testIdentificaDiretorioDoArquivoOriginal(){
        String dir = ajustaBarrasDeAcordoComOS(this.getClass().getResource(".").getPath());
		String path = dir + "arquivo.xml.fortesrh";
		dir = dir.substring(0, dir.lastIndexOf(File.separator));
		String diretorioDoArquivo = zip.identificaDiretorioDoArquivoOriginal(path);
		assertEquals(dir, diretorioDoArquivo);
	}

	private String ajustaBarrasDeAcordoComOS(String path) {
		return path
			.replace("/", File.separator)
			.replace("\\", File.separator);
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

		zip.compress(new java.io.File[] { file }, "file", ".fortesrh", true);

		zip.unzip("file.fortesrh", "file.txt");
		zip.unzip("file.fortesrh", "");

	}

}

