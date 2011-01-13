package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.util.BooleanUtil;

public class BooleanUtilTest extends TestCase
{

	protected void setUp()
	{
	}

	public void testRemoveBreak()
	{
		assertNull(BooleanUtil.getValueCombo('T'));
		assertTrue(BooleanUtil.getValueCombo('S'));
		assertFalse(BooleanUtil.getValueCombo('N'));
		assertTrue(BooleanUtil.getValueCombo('s'));
		assertFalse(BooleanUtil.getValueCombo('n'));
	}
}
