package com.fortes.web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.fortes.model.type.File;
import com.opensymphony.webwork.ServletActionContext;

public class HttpResponseFileWriter implements FileWriter
{
	public void write(File file) 
	{
		//FileStoreType fileStoreType = file.getFileStoreType();
		
		//if (fileStoreType == null || fileStoreType.equals(FileStoreType.SAVE_ON_DATABASE)) 
		//{			
	        writeFileToResponse(file.getContentType(), file.getBytes());
		//}
		//else if (fileStoreType.equals(FileStoreType.SAVE_ON_DISK_SECURED))
		//{
	    //    java.io.File f = new java.io.File(file.getLocation());
	    //    writeFileToResponse(file.getContentType(), FileUtil.getFileBytes(f));	        
		//}
		//else
		//{
		//	redirectToFile(file.getLocation());
		//}
	}
	
	private void writeFileToResponse(String contentType, byte[] data)
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		
        response.addHeader("Expires", "0");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Content-type", contentType);
        response.addHeader("Content-Transfer-Encoding", "binary");
        try
		{
			response.getOutputStream().write(data);
		} 
        catch (IOException e)
		{
			new RuntimeException(e);
		}
	}
	
/*
	private void redirectToFile(String absoluteFilePath)
	{
		java.io.File f1 = new java.io.File(absoluteFilePath);		
		java.io.File f2 = new java.io.File(ServletActionContext.getServletContext().getRealPath("/"));
		String relativeFilePath = f2.toURI().relativize(f1.toURI()).toString();
		HttpServletResponse response = ServletActionContext.getResponse();
        response.addHeader("Location", relativeFilePath);
	}
*/
}
