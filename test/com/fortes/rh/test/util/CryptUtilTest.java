package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.util.CryptUtil;

public class CryptUtilTest extends TestCase
{
	public void testEncryptDecrypt() throws Exception 
	{
		String encrypted = "6l3u6nm0P/bTJIUD0Nxmqw==";
		String decrypted = "Hello World!";
		String key 		 = "123";
		
		assertEquals("Hello World!", CryptUtil.decrypt(encrypted, key));
		assertEquals("6l3u6nm0P/bTJIUD0Nxmqw==", CryptUtil.encrypt(decrypted, key));
	}
}