package com.fortes.rh.test.util;

import java.text.ParseException;

import junit.framework.TestCase;

import com.fortes.rh.util.ConverterUtil;


public class ConverterUtilTest extends TestCase
{
	@SuppressWarnings("unused")
	protected void setUp(){
		ConverterUtil converterUtil = new ConverterUtil();
	}

	public void testConvertDoubleToString(){
		assertEquals("2,00", ConverterUtil.convertDoubleToString(2D));
		assertEquals("2,33", ConverterUtil.convertDoubleToString(2.332D));
		assertEquals("2,34", ConverterUtil.convertDoubleToString(2.335D));
	}

	public void testConvertStringToDouble() throws ParseException{
		assertEquals(2D, ConverterUtil.convertStringToDouble("2,00"));
	}
	
	public void testFormataPercentual() throws ParseException{
		assertEquals("0,25%", ConverterUtil.formataPercentual(0.25, "#.##%"));
		assertEquals("(12,26%)", ConverterUtil.formataPercentual(12.26, "(#.##%)"));
		assertEquals("0,00%",ConverterUtil.formataPercentual(0.0, "#.##%"));
		assertNull(ConverterUtil.formataPercentual(null, "#.##%"));
	}

}