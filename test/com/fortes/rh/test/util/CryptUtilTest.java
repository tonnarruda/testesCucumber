package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.util.CryptUtil;

public class CryptUtilTest extends TestCase
{
	public void testEncryptDecrypt() throws Exception 
	{
		assertEquals("G9izcGdqxKnMWb8Qk1+A78h1wegy/K31sbQsMivtnJfgCP0Z4m8cxwtr0umF1HUc", CryptUtil.encrypt("Burn in hell, your mother fucker!", "1234567890123456"));
		assertEquals("Burn in hell, your mother fucker!", CryptUtil.decrypt("G9izcGdqxKnMWb8Qk1+A78h1wegy/K31sbQsMivtnJfgCP0Z4m8cxwtr0umF1HUc", "1234567890123456"));
	}
}