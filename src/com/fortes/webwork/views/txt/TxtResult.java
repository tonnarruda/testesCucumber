package com.fortes.webwork.views.txt;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.fortes.rh.util.ArquivoUtil;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;

public class TxtResult extends WebWorkResultSupport {

	private final String PATH = ArquivoUtil.getRhHome() + File.separatorChar;
    protected String dataSource;
    protected String nomeArquivoComExtencao;
    
	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception 
	{
		try {
			OgnlValueStack stack = invocation.getStack();
			
			HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(ServletActionContext.HTTP_RESPONSE);
			
			String text = (String) stack.findString(dataSource);
			File file = new File(PATH + "Temp.txt");
			
			Writer writer = new BufferedWriter(new FileWriter(file));
			writer.write(text);
			writer.close();

			ServletOutputStream stream = null;
			InputStream inputStream = null;

			inputStream = new FileInputStream(PATH + "Temp.txt");

			byte[] buffer = new byte[8192];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int bytesRead;
			while (  (bytesRead = inputStream.read(buffer)) != -1)
			   baos.write(buffer, 0, bytesRead);

			response.setContentType("text/plain");
			response.addHeader("Content-Disposition", "attachment; filename=" + nomeArquivoComExtencao);

			byte[] outBuf = baos.toByteArray();
			stream = response.getOutputStream();
			stream.write(outBuf);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao exportar para TR.");
		}	    
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setNomeArquivoComExtencao(String nomeArquivoComExtencao) {
		this.nomeArquivoComExtencao = nomeArquivoComExtencao;
	}
}
