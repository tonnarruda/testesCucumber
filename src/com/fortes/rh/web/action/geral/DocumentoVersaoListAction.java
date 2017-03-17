package com.fortes.rh.web.action.geral;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.DocumentoVersaoManager;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class DocumentoVersaoListAction extends MyActionSupportList
{
	@Autowired private DocumentoVersaoManager documentoVersaoManager;

	String textoXml = "";

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String documentoVersaoPath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/fortesrh.ver");
		File xmlFile = new File(documentoVersaoPath);
		textoXml = documentoVersaoManager.lerArquivoXML(xmlFile);

		return Action.SUCCESS;
	}

	public String getTextoXml()
	{
		return textoXml;
	}

	public void setTextoXml(String textoXml)
	{
		this.textoXml = textoXml;
	}

	//TODO TESTES
	public static void limpaDirTemp()
	{
		char barra = File.separatorChar;
		String nameDirTemp = "/FortesRH" + barra + "web" + barra + "WEB-INF" + barra + "temp";

		File dirTemp = new File(nameDirTemp);
		File[] files = dirTemp.listFiles();

		for(File file : files)
		{
			if(file.getPath().indexOf(".pdf") != -1)
				file.delete();
		}
	}
}