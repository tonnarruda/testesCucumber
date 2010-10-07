package com.fortes.rh.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

// TODO: Mover para o fortes-commons??
public class Zip
{
	/**
	 *
	 * @param files       :: Arquivos a serem compactados
	 * @param zipFileName :: Nome do arquivo Zip gerado.
	 *
	 * @return
	 */
	public ZipOutputStream compress(File[] files, String zipFileName, String extensao)
	{
	    // Create a buffer for reading the files
	    byte[] buf = new byte[2048];

        ZipOutputStream out = null;
	    try
	    {
	        // Create the ZIP file
	        out = new ZipOutputStream(new FileOutputStream(zipFileName + extensao));

	        // Compress the files
	        for (File file : files)
	        {
	        	FileInputStream in = new FileInputStream(file);
	            // Add ZIP entry to output stream.
	            out.putNextEntry(new ZipEntry(file.getPath()));//file.getAbsolutePath()??

	            // Transfer bytes from the file to the ZIP file
	            int len;
	            while ((len = in.read(buf)) > 0)
	            {
	                out.write(buf, 0, len);
	            }

	            // Complete the entry
	            out.closeEntry();
	            in.close();
			}
	        // Complete the ZIP file
	        out.flush();
	        out.finish();//Write the contents of the ZIP output stream without closing the underlying stream
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
	    return out;
	}

	  @SuppressWarnings("unchecked")
	public void unzip(String nameZip, String destino)
	  {
		  final int BUFFER = 2048;
	      try
	      {
	    	  String diretorioDoArquivoCompactado = identificaDiretorioDoArquivoOriginal(nameZip);
	          BufferedOutputStream dest = null;
	          CheckedInputStream checksum = new CheckedInputStream(new FileInputStream(nameZip), new Adler32());
	          ZipInputStream zis = new ZipInputStream(new BufferedInputStream(checksum));
	          ZipEntry entry;

	          while((entry = zis.getNextEntry()) != null)
	          {
	             System.out.println("Extraindo: " +entry);

	             int count;
	             byte data[] = new byte[BUFFER];

	             File file;
	             if(destino.equals(""))
	            	 file = new File(diretorioDoArquivoCompactado + File.separator + entry.getName());
	             else
	             {
	            	 file = new File(destino+"/"+entry.getName());
	            	 File diretorio = new File(file.getParent());
	            	 diretorio.mkdirs();
	             }

	           	 file.createNewFile();

	             FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
	             dest = new BufferedOutputStream(fos,BUFFER);

	             while ((count = zis.read(data, 0,BUFFER)) != -1)
	             {
	                dest.write(data, 0, count);
	             }

	             dest.flush();
	             dest.close();
	          }

	          zis.close();
	          System.out.println("###########################################");
	          System.out.println("Nome do arquivo compactado: " + nameZip);
	          System.out.println("Nome do arquivo descompactado: "+destino+"/"+nameZip.substring(nameZip.lastIndexOf("/")+1,nameZip.indexOf(".")));
	          System.out.println("###########################################");

	      }
	      catch(Exception e)
	      {
	          e.printStackTrace();
	      }
	  }

	public String identificaDiretorioDoArquivoOriginal(String path)
	{
		if(path == null || path.trim().equals(""))
			return null;

		return path.substring(0, path.lastIndexOf(File.separator));
	}
}
