package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.util.MontaRelatorioItext;
import com.lowagie.text.pdf.PdfPCell;

public class MontaRelatorioItextTest extends TestCase
{
	MontaRelatorioItext montaRelatorioItext;

	protected void setUp() throws Exception
	{
		montaRelatorioItext = new MontaRelatorioItext();
	}

	public void testMontaCelula(){
		PdfPCell cell = MontaRelatorioItext.montaCelula("texto",false,1F,false,1,1F,false);
		assertFalse("Test 1", cell.hasBorders());
		assertEquals("Test 2", 1F, cell.grayFill());

		cell = MontaRelatorioItext.montaCelula("texto",true,1F,false,1,1F,true);
		assertTrue("Test 3", cell.hasBorders());
		assertEquals("Test 4", 1F, cell.grayFill());

	}

	public void testMontaCelulaEmBranco(){
		PdfPCell cell = MontaRelatorioItext.montaCelulaEmBranco();
		assertFalse(cell.hasBorders());
	}

}
