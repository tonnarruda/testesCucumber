package com.fortes.rh.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DocX extends XWPFDocument
{
	public static final String TABLE_HEADER_BG_COLOR = "DDDDDD";
	
	public DocX() {
		super();
	}

	public DocX(InputStream is) throws IOException {
		super(is);
	}

	public DocX(OPCPackage pkg) throws IOException {
		super(pkg);
	}

	public XWPFRun addParagraph(String text, String style, Integer firstLineIndentation, boolean addBreak)
	{
		XWPFParagraph para;
		XWPFRun run;
		
		para = super.createParagraph();
		
		if (!StringUtil.isBlank(style))
        	para.setStyle(style);
		
		if (firstLineIndentation != null)
			para.setIndentationFirstLine(firstLineIndentation);
        
		run = para.createRun();
		run.setText(text);
		
		if (addBreak)
			run.addBreak();
        
        return run;
	}

	public XWPFRun addParagraph(String text, String style)
	{
		return this.addParagraph(text, style, null, false);
	}
	
	public XWPFTableRow addTableHeader(XWPFTable table, String[] titles, Integer[] widths)
	{
		XWPFTableRow row = table.getRow(0);
		
		for (int i = 0; i < titles.length; i++) 
		{
			if (i > 0)
				row.addNewTableCell();
			
			row.getCell(i).setText(titles[i]);
	        row.getCell(i).setColor(TABLE_HEADER_BG_COLOR);
	        row.getCell(i).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(widths[i]));
		}
		
		return row;
	}
	
	public XWPFTableRow addTableRow(XWPFTable table, String... contents)
	{
		XWPFTableRow row = table.createRow();
		
		for (int i = 0; i < contents.length; i++) 
			row.getCell(i).setText(contents[i]);
		
		return row;
	}
}
