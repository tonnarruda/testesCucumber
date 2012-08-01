package com.fortes.rh.business.geral;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class DocumentoVersaoManagerImpl implements DocumentoVersaoManager
{
	public String lerArquivoXML(File xmlFile)
	{
		String textoXml = "";
		SAXBuilder builder = new SAXBuilder();
		try
		{
			Document document = builder.build(xmlFile);

			Element element = document.getRootElement();

			List listElements = element.getChildren();
			Iterator iterListElements = listElements.iterator();

			textoXml += "<table cellpadding='0' cellspacing='0' class=\"docVersao\">";
			int linha = 0;
			String cssClassLinha = "";

			while(iterListElements.hasNext())
			{
				if(linha % 2 == 0)
					cssClassLinha = "linhaVersao";
				else
					cssClassLinha = "";

				Element elementVersao = (Element)iterListElements.next();
				textoXml += "<tr class=\""+cssClassLinha+"\">";
				textoXml +=	"<td class=\"tamanho100\">" + elementVersao.getAttributeValue("data") + "</td>";
				textoXml += "<td class=\"tamanho100\">" + elementVersao.getAttributeValue("id") + "</td>";
				
				Element elementModule = elementVersao.getChild("Modulo");
				textoXml += "<td>" + elementModule.getValue().replace(":: ", "<br>- ").trim().substring(4) + "</td><tr>";
				linha++;
			}

			textoXml += "</table>";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return textoXml;
	}
}