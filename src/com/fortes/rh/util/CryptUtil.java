package com.fortes.rh.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptUtil 
{
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	
    public static String encrypt(String strToEncrypt, String key)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.ENCRYPT_MODE, makeKey(key), ivSpec);
            final byte[] encryptedByte = Base64.encodeBase64(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            return new String(encryptedByte, "UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
    }

    public static String decrypt(String strToDecrypt, String key)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, makeKey(key), ivSpec);
            final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt.getBytes())));
            return decryptedString;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
	public static Key makeKey(String key) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] keyBytes = md.digest(key.getBytes());
			return new SecretKeySpec(keyBytes, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}
}