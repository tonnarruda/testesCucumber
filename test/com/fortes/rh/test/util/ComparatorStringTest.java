package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.util.ComparatorString;

public class ComparatorStringTest extends TestCase
{
	ComparatorString comparatorString = new ComparatorString();

	public void testCompare()
	{
		assertEquals(0, comparatorString.compare("a", "a"));
		assertEquals(-1, comparatorString.compare(null, "a"));
	}

	public void testCompareException()
	{
		assertEquals(-1, comparatorString.compare("a", null));
	}
}
