package com.fortes.rh.business.geral;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import com.fortes.rh.util.ArquivoUtil;


public class MorroManagerImpl implements MorroManager
{
	private final String PATH = ArquivoUtil.getRhHome() + File.separatorChar;
	
	public File getPrintScreen() throws Exception 
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle screenRect = new Rectangle(screenSize);
		
		Robot robot = new Robot();
		BufferedImage screenCapturedImage = robot.createScreenCapture(screenRect);
		
		File printErro = new File(PATH + "Erro.jpg");
		ImageIO.write(screenCapturedImage, "jpg", printErro);
		
		return printErro;
	}

	public File getErrorFile(String mensagem, String url, String versao, String clienteCnpj, String clienteNome) throws Exception 
	{
		File logErro = new File(PATH + "Erro.txt");  
		FileOutputStream fos = new FileOutputStream(logErro);  
		StringBuffer texto = new StringBuffer();
		
		texto.append("Mensagem=" + mensagem + "\n");
		texto.append("URL=" + url + "\n");
		texto.append("Produto=RH" + "\n");
		texto.append("Versao=" + versao + "\n");
		texto.append("LicenciadoCNPJ=" + clienteCnpj + "\n");
		texto.append("LicenciadoNome=" + clienteNome + "\n");
		texto.append("VersaoJava=" + System.getProperty("java.version", "Não identificado") + "\n");
		texto.append("Especificacao=" + System.getProperty("java.vm.specification.vendor", "Não identificado") + "\n");
		texto.append("SistemaOperacional=" + System.getProperty("os.name", "Não identificado") + "\n");
		texto.append("MemoriaLivre=" + Runtime.getRuntime().freeMemory() + "\n");
		texto.append("MemoriaTotal=" + Runtime.getRuntime().totalMemory() + "\n");
		
//	    File[] roots = File.listRoots();
//	    int i = 1;
//	    for (File root : roots) 
//	    	texto.append("Disco" + i++ + "=Raiz: " + root.getAbsolutePath() + ", Espaco Total: " + root.getTotalSpace() + " bytes, Espaco Livre: " + root.getFreeSpace() + " bytes, Espaco Utilizavel: " + root.getUsableSpace() + " bytes\n");
		
		fos.write(texto.toString().getBytes());  
		fos.close();
		
		return logErro;
	}
	
	public int enviar(PostMethod filePost, File zip, String clienteNome) throws Exception 
	{
		Part[] parts = { 	
				new FilePart(zip.getName(), zip), 
				new StringPart("FileName", zip.getName()), 
				new StringPart("Description", "Erro identificado no cliente " + clienteNome), 
				new StringPart("Att", "suporte.rh@grupofortes.com.br"), 
				new StringPart("ReplyTo", "")
			};

		filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
		
		HttpClient httpClient = new HttpClient();
		int status = httpClient.executeMethod(filePost);
		
//		String retorno = filePost.getResponseBodyAsString();
//		String msgStatus = HttpStatus.getStatusText(status);
		
		return status;
	}
}