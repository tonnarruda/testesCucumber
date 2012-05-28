package com.fortes.model.type;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil
{
	
	private final static Log logger = LogFactory.getLog(FileUtil.class);
	
	public static byte[] getFileBytes(File file)
	{
		if (file == null)
			return null;

		/* Cria um InputStream */
		byte[] bytes = new byte[(int) file.length()];
		FileInputStream in = null;

		try
		{
			in = new FileInputStream(file);

			/* Lê o arquivo */
			if (in.read(bytes) == bytes.length)
				return bytes;
		} catch (FileNotFoundException exc)
		{
			exc.printStackTrace();
		} catch (IOException exc)
		{
			exc.printStackTrace();
		} finally
		{
			/* Fecha o arquivo */
			try
			{
				in.close();
			} catch (IOException exc)
			{
				exc.printStackTrace();
			}
		}

		return null;
	}

	public static java.io.File bytesToFile(byte[] fileBytes, String filePath) {
		FileOutputStream fos = null;
		try {
			java.io.File file = new java.io.File(filePath);
			fos = new FileOutputStream(file);
			fos.write(fileBytes);
			return file;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Erro ao escrever arquivo " + filePath +  " em disco: " + e.getMessage(), e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					throw new RuntimeException("Erro ao fechar stream do arquivo " + filePath +  ": " + e.getMessage(), e);
				}
			}
		}
	}

	public static String getFileExtension(String contentType)
	{
		String result = "";
		int pos = contentType.indexOf('/');

		if (pos > 0)
		{
			result = "." + contentType.substring(pos + 1);
		}

		return result;
	}
	
//	public static String getMimeType(File file) {
//		return new MimetypesFileTypeMap().getContentType(file);
//	}
	
}
