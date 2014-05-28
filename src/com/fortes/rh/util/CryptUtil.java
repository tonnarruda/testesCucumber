package com.fortes.rh.util;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class CryptUtil 
{
	private static byte[] ivBytes = { (byte)213, 34, (byte)142, (byte)251, 68, (byte)141, 52, 31, (byte)166, (byte)147, 127, 48, (byte)247, (byte)177, 76, 28 };
	private static byte[] saltBytes = { (byte)146, 59, 72, (byte)223, 27, 57, (byte)186, 103 };

	private static SecretKey buildSecretKey(String plainKey) throws Exception
	{
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec keySpec = new PBEKeySpec(plainKey.toCharArray(), saltBytes, 65536, 128);
		SecretKey saltedKey = factory.generateSecret(keySpec);
		
		SecretKey secretKey = new SecretKeySpec(saltedKey.getEncoded(), "AES");
		
		return secretKey;
	}
	
	public static String encrypt(String plainText, String plainKey) throws Exception
	{
		SecretKey secretKey = buildSecretKey(plainKey);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(ivBytes);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		String codedString = DatatypeConverter.printBase64Binary(encryptedBytes);
		return codedString;
	}

	public static String decrypt(String cipherText, String plainKey) throws Exception
	{
		SecretKey secretKey = buildSecretKey(plainKey);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(ivBytes);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] decodedBytes = DatatypeConverter.parseBase64Binary(cipherText);
		String plainText = new String(cipher.doFinal(decodedBytes), "UTF-8");
		return plainText;
	}
}