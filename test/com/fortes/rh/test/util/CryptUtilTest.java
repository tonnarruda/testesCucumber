package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.util.CryptUtil;

public class CryptUtilTest extends TestCase
{
	public void testEncrypt()
	{
		String encrypted = CryptUtil.encrypt("teste", "0123456789012345");
		assertEquals("qfQPHRFDenMcztNYlemLig==", encrypted);
	}
	
	public void testDecrypt()
	{
		String decrypted = CryptUtil.decrypt("qfQPHRFDenMcztNYlemLig==", "0123456789012345");
		assertEquals("teste", decrypted);
	}
}