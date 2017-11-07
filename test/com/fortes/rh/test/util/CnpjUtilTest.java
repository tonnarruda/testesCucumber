package com.fortes.rh.test.util;

import org.junit.Assert;
import org.junit.Test;

import com.fortes.rh.util.CnpjUtil;

public class CnpjUtilTest
{

	@Test
	public void testFormataCnpjComDV()
	{
		String cnpjComDV = "00000002000180";

		Assert.assertEquals("00000002/0001-80", CnpjUtil.formata(cnpjComDV, Boolean.FALSE));
	}

	@Test
	public void testFormataCnpjCompletoComDV()
	{
		String cnpjComDV = "00000002000180";
		
		Assert.assertEquals("00.000.002/0001-80", CnpjUtil.formata(cnpjComDV, Boolean.TRUE));
	}

	@Test
	public void testFormataCnpjSemDV()
	{
		String cnpjSemDV = "000000020001";

		Assert.assertEquals("00000002/0001-80", CnpjUtil.formata(cnpjSemDV, Boolean.FALSE));
	}
	
	@Test
	public void testFormataCnpjCompletoSemDV()
	{
		String cnpjSemDV = "000000020001";
		
		Assert.assertEquals("00.000.002/0001-80", CnpjUtil.formata(cnpjSemDV, Boolean.TRUE));
	}

	@Test
	public void testFormataCnpjIncompleto()
	{
		String cnpjSemDV = "1234567890";

		Assert.assertNull(CnpjUtil.formata(cnpjSemDV, Boolean.FALSE));
	}
	
	@Test
	public void testFormataNuloVazio()
	{
		Assert.assertNull(CnpjUtil.formata(null, Boolean.TRUE));
		Assert.assertNull(CnpjUtil.formata("", Boolean.TRUE));
		Assert.assertNull(CnpjUtil.formata("  ", Boolean.TRUE));
	}
	
	@Test
	public void testCalculaDigitoVerificador()
	{
		Assert.assertEquals("58", CnpjUtil.calculaDigitoVerificador("673933570001"));
	}

}
