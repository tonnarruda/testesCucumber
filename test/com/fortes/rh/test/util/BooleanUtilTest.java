package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.util.BooleanUtil;

public class BooleanUtilTest extends TestCase
{

	protected void setUp()
	{
	}

	public void testGetValueCombo()
	{
		assertNull(BooleanUtil.getValueCombo('T'));
		assertTrue(BooleanUtil.getValueCombo('S'));
		assertFalse(BooleanUtil.getValueCombo('N'));
		assertTrue(BooleanUtil.getValueCombo('s'));
		assertFalse(BooleanUtil.getValueCombo('n'));
	}

	public void testSetValueCombo()
	{
		assertEquals('S', BooleanUtil.setValueCombo(true));
		assertEquals('N', BooleanUtil.setValueCombo(false));
		assertEquals('T', BooleanUtil.setValueCombo(null));
	}

	public void testGetDescricao()
	{
		assertEquals("Sim", BooleanUtil.getDescricao('S'));
		assertEquals("Não", BooleanUtil.getDescricao('N'));
		assertEquals("Todas", BooleanUtil.getDescricao('T'));//utilizado no relatorio da area, "todas" 
	}
}
