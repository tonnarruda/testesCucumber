package com.fortes.rh.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class CryptUtil 
{
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	
	public static String encrypt(String plainText, String encryptionKey) throws Exception
	{
		String encryptionKey16x = padMultiple16(encryptionKey);
		String plainText16x = padMultiple16(plainText);
		
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec key = new SecretKeySpec(encryptionKey16x.getBytes("UTF-8"), "AES");
		IvParameterSpec iv = new IvParameterSpec(ivBytes);
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		
		return new String(Base64.encodeBase64(cipher.doFinal(plainText16x.getBytes("UTF-8"))), "UTF-8");
	}

	public static String decrypt(String cipherText, String encryptionKey) throws Exception 
	{
		String encryptionKey16x = padMultiple16(encryptionKey);
		String cipherText16x = padMultiple16(cipherText);
		
		byte[] bytes = Base64.decodeBase64(cipherText16x.getBytes());
		
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec key = new SecretKeySpec(encryptionKey16x.getBytes("UTF-8"), "AES");
		IvParameterSpec iv = new IvParameterSpec(ivBytes);
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		
		return new String(cipher.doFinal(bytes), "UTF-8").trim();
	}
	
	public static String padMultiple16(String str)
	{
		if (str.length() % 16 == 0)
			return str;
		
		int size = str.length() + 16 - (str.length() % 16);
		return StringUtils.rightPad(str, size, '\0');
	}
}