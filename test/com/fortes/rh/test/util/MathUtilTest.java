package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.util.MathUtil;

public class MathUtilTest extends TestCase
{
	@SuppressWarnings("unused")
	protected void setUp(){
		MathUtil mathUtil = new MathUtil();
	}

	public void testCalculaPMT(){
		assertEquals("Test 1", 134.29, MathUtil.calculaPMT(500, 5, 4));
	}

	public void testRound(){
		assertEquals("Test 1", 201.68, MathUtil.round(201.675, 2));
		assertEquals("Test 2", 201.67, MathUtil.round(201.674, 2));
		assertEquals("Test 3", 201.67, MathUtil.round(201.6749, 2));
		assertEquals("Test 4", 202.0, MathUtil.round(201.9749, 0));
	}

	public void testCalculaDesconto(){
		assertEquals("Test 1", 300.0, MathUtil.calculaDesconto(500.0, 200.0));
		assertEquals("Test 2", -100.0, MathUtil.calculaDesconto(100.0, 200.0));
		assertEquals("Test 3", 0.0, MathUtil.calculaDesconto(200.0, 200.0));
	}

	public void testFormataValor()
	{
		Float valor = MathUtil.formataValor(22.33F);
		assertEquals(22.33F, valor);
		valor = MathUtil.formataValor(22.222F);
		assertEquals(22.22F, valor);
		valor = MathUtil.formataValor(0F);
		assertEquals(0F, valor);
	}

	public void testFormataValorDouble()
	{
		assertEquals("22,33", MathUtil.formataValor(22.33D));
		
		assertEquals("322,22", MathUtil.formataValor(322.222D));
		
		assertEquals("22,22", MathUtil.formataValor(22.222D));
		
		assertEquals("0,00", MathUtil.formataValor(0D));
	}
	
	public void testCalculaDissidio()
	{
		//DissidioPor		1 = Porcentagem (%)		2 = Valor (R$)
		try
		{
			assertEquals(300.0, MathUtil.calculaDissidio('1', 50.0, 200.0));
			assertEquals(400.0, MathUtil.calculaDissidio('2', 200.0, 200.0));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void testCalculaDissidioException()
	{
		//DissidioPor		1 = Porcentagem (%)		2 = Valor (R$)
		Exception exception = null;
		try
		{
			assertEquals(300.0, MathUtil.calculaDissidio('0', 50.0, 200.0));
		}
		catch (Exception e) {
			exception = e;
		}

		assertNotNull(exception);
	}
	
	public void testCalculaPorcentagem()
	{
		assertEquals("100,00 %", MathUtil.calculaPorcentagem(100, 0));
		assertEquals("50,00 %", MathUtil.calculaPorcentagem(50, 50));
		assertEquals("40,00 %", MathUtil.calculaPorcentagem(40, 60));
		assertEquals("0,00 %", MathUtil.calculaPorcentagem(0, 100));
	}
}
