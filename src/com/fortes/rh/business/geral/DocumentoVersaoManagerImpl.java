package com.fortes.rh.business.geral;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Component;

@Component
public class DocumentoVersaoManagerImpl implements DocumentoVersaoManager
{
	@SuppressWarnings("unchecked")
	public String lerArquivoXML(File xmlFile)
	{
		String textoXml = "";
		SAXBuilder builder = new SAXBuilder();
		try
		{
			Document document = builder.build(xmlFile);

			Element element = document.getRootElement();

			List<Element> listElements = element.getChildren();
			Iterator<Element> iterListElements = listElements.iterator();
			Iterator<Element> iterItemElements;
			Element versaoElement;
			Element itemElement;

			textoXml += "<table cellpadding='0' cellspacing='0' class=\"docVersao\">";
			int linha = 0;
			String cssClassLinha = "";

			while(iterListElements.hasNext())
			{
				if(linha % 2 == 0)
					cssClassLinha = "linhaVersao";
				else
					cssClassLinha = "";

				versaoElement = iterListElements.next();
				textoXml += "<tr class=\""+cssClassLinha+"\">";
				textoXml +=	"<td class=\"tamanho100\">" + versaoElement.getAttributeValue("data") + "</td>";
				textoXml += "<td class=\"tamanho100\">" + versaoElement.getAttributeValue("id") + "</td>";
				textoXml += "<td><ul>";
				
				iterItemElements = versaoElement.getDescendants(new ElementFilter("Item"));
				
				while(iterItemElements.hasNext())
				{
					itemElement = iterItemElements.next();
					if(itemElement.getAttribute("visivel").getValue().equalsIgnoreCase("S"))
						textoXml += "<li>" + itemElement.getTextTrim() + "</li>";
				}
				
				textoXml += "</ul></td></tr>";
				
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