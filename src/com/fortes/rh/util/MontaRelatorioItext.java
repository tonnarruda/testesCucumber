package com.fortes.rh.util;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;

@SuppressWarnings("unused")
public class MontaRelatorioItext
{
	public static PdfPCell montaCelula(String texto, boolean borda, Float corLinha, boolean noWrap, Integer alinhamento, Float paddingLeft, boolean negrito)
	{
		Paragraph paragrafo;
		PdfPCell cell = new PdfPCell();

		if(negrito)
			paragrafo = new Paragraph(texto, FontFactory.getFont(FontFactory.HELVETICA, 12f, Font.BOLD));
		else
			paragrafo = new Paragraph(texto, FontFactory.getFont(FontFactory.HELVETICA, 12f));

		//diminui altura da celula
		cell.setPaddingTop(-4.5f);

		if(paddingLeft != null)
			cell.setPaddingLeft(paddingLeft);

		//add texto em um paragrafo com alinhamento
		if(texto != null)
		{
			paragrafo.setAlignment(alinhamento);
			cell.addElement(paragrafo);
		}

		//insere borda na linha
		if(!borda)
			cell.setBorder(0);

		//cor da linha tende para o preto
		if(corLinha != null)
			cell.setGrayFill(corLinha);

		//Aumenta o tamanho da celula para caber o texto
		cell.setNoWrap(false);

		return cell;
	}

	public static PdfPCell montaCelulaEmBranco()
	{
		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);

		return cell;
	}
}
