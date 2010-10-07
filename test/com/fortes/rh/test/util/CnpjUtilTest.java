package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.util.CnpjUtil;

public class CnpjUtilTest extends TestCase
{

	public void testFormataCnpjComDV()
	{
		String cnpjComDV = "00000002000180";

		assertEquals("00000002/0001-80", CnpjUtil.formata(cnpjComDV));
	}

	public void testFormataCnpjSemDV()
	{
		String cnpjSemDV = "000000020001";

		assertEquals("00000002/0001-80", CnpjUtil.formata(cnpjSemDV));
	}

	public void testFormataCnpjIncompleto()
	{
		String cnpjSemDV = "1234567890";

		assertNull(CnpjUtil.formata(cnpjSemDV));
	}
	
	public void testFormataNull()
	{
		assertNull(CnpjUtil.formata(null));
	}
	
	public void testCalculaDigitoVerificador()
	{
		assertEquals("58", CnpjUtil.calculaDigitoVerificador("673933570001"));
	}

}
