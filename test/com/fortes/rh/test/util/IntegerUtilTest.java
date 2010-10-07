package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.util.IntegerUtil;

public class IntegerUtilTest extends TestCase
{
	public void testArrayStringToArrayInteger()
	{
		String[] strings = null;

		assertEquals("Test 1", 0, IntegerUtil.arrayStringToArrayInteger(strings).length);

		strings = new String[5];
		strings[0] = "10";
		strings[1] = "15";
		strings[2] = "afds";

		Integer[] longs = IntegerUtil.arrayStringToArrayInteger(strings);

		assertEquals("Test 2", strings.length, longs.length);
		assertEquals("Test 3", Integer.valueOf(10), longs[0]);
		assertEquals("Test 4", null, longs[2]);
	}
}
