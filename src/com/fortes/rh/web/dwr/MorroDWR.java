package com.fortes.rh.web.dwr;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import remprot.RPClient;

import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.util.Zip;

public class MorroDWR
{
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	public String enviar(String mensagem, String stackTrace) 
	{
		PostMethod filePost = new PostMethod("http://10.1.3.126/morro.php"); // http://www.fortesinformatica.com.br/cgi-bin/filebox/send
		String retorno = "";
		
		try {
			ParametrosDoSistema params = parametrosDoSistemaManager.findById(1L);
			String path = ArquivoUtil.getRhHome() + File.separatorChar;
			
			// monta nome do arquivo
			RPClient client = Autenticador.getRemprot(params.getServidorRemprot());
			String nomeCliente = StringUtil.retiraAcento(client.getCustomerName()).replace(" ", "_");
			
			String data = DateUtil.formataDate(new Date(), "yyyyMMdd_HHmm");
			String nomeArquivo = path + "ERRO_RH_RH_" + data + "_" + nomeCliente;
	
			
			// geracao do print
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			
			Dimension screenSize = toolkit.getScreenSize();
			Rectangle screenRect = new Rectangle(screenSize);
			
			Robot robot = new Robot();
			BufferedImage screenCapturedImage = robot.createScreenCapture(screenRect);
			
			File printErro = new File(path + "Erro.jpg");
			ImageIO.write(screenCapturedImage, "jpg", printErro);
	
	
			// geracao do arquivo texto	
			File logErro = new File(path + "Erro.txt");  
			FileOutputStream fos = new FileOutputStream(logErro);  
			StringBuffer texto = new StringBuffer();
			
			texto.append("Mensagem=" + mensagem + "\n");
			texto.append("Produto=RH" + "\n");
			texto.append("Versao=" + params.getAppVersao() + "\n");
			texto.append("LicenciadoCNPJ=" + client.getCustomerId() + "\n");
			texto.append("LicenciadoNome=" + client.getCustomerName() + "\n");
			texto.append("VersaoJava=" + System.getProperty("java.version", "Não identificado") + "\n");
			texto.append("Especificacao=" + System.getProperty("java.vm.specification.vendor", "Não identificado") + "\n");
			texto.append("SistemaOperacional=" + System.getProperty("os.name", "Não identificado") + "\n");
			texto.append("MemoriaLivre=" + Runtime.getRuntime().freeMemory() + "\n");
			texto.append("MemoriaTotal=" + Runtime.getRuntime().totalMemory() + "\n");
			
		    File[] roots = File.listRoots();
		    int i = 1;
		    for (File root : roots) 
		    	texto.append("Disco" + i++ + "=Raiz: " + root.getAbsolutePath() + ", Espaco Total: " + root.getTotalSpace() + " bytes, Espaco Livre: " + root.getFreeSpace() + " bytes, Espaco Utilizavel: " + root.getUsableSpace() + " bytes\n");
			
			fos.write(texto.toString().getBytes());  
			fos.close();
			
			
			// cria o zip
			ZipOutputStream zipOS = new Zip().compress(new File[] { printErro, logErro }, nomeArquivo, ".zip", false);
			zipOS.close();
			File zip = new File(nomeArquivo + ".zip");
			
			
			// envia
			Part[] parts = { 	
								new FilePart(zip.getName(), zip), 
								new StringPart("FileName", zip.getName()), 
								new StringPart("Description", "Erro identificado no cliente " + client.getCustomerName()), 
								new StringPart("Att", ""), 
								new StringPart("ReplyTo", "")
							};
			
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
			
			HttpClient httpClient = new HttpClient();
			int status = httpClient.executeMethod(filePost);
	
			if (status == HttpStatus.SC_OK)
				retorno = "Enviado com sucesso"; //filePost.getResponseBodyAsString();
	        else
	        	retorno = "Falha no envio"; //HttpStatus.getStatusText(status);
			
			
			// remove arquivos temporarios
			printErro.delete();
			logErro.delete();
			zip.delete();
		
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			filePost.releaseConnection();
		}
		
		return retorno;
	}
	
	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
}
