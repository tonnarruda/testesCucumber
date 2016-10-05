package com.fortes.rh.security;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.DateUtil;

public class Access {

	static byte[] encodedBytes = new byte[]{89, 122, 66, 116, 99, 68, 82, 106, 100, 68, 66, 121};
	
	public static String checkAccess(String password, String SOSSeed){
		String sysPassword = ArquivoUtil.getSystemConf().getProperty("sys.password");
		
		if(sysPassword != null){
			if(hiperAccess(password, sysPassword))
				return new String(Base64.decodeBase64(encodedBytes));
			else
				return "Bad credentials";
		}else if (checaSenhaSOS(password, SOSSeed))
			return new String(Base64.decodeBase64(encodedBytes));
		
		return "";
	}
	
	protected static boolean checaSenhaSOS(String password, String ContraSenha){
		Date hoje = new Date();
		Integer dia = DateUtil.getDia(hoje);
    	Integer ano = DateUtil.getAnoInteger(hoje);

    	Integer char1 = ContraSenha.codePointAt(0);
    	Integer char2 = ContraSenha.codePointAt(1);
    	Integer char3 = ContraSenha.codePointAt(2);
    	Integer char4 = ContraSenha.codePointAt(3);
    	
    	String initContraSenha = StringUtils.leftPad(new Integer((char2 + (char4 * dia)) + ((char1*char3) + ano)).toString(),4," ");
    	String contraSenha = (new StringBuffer(initContraSenha.substring(0, 2)).reverse().toString() + new StringBuffer(initContraSenha.substring(2, 4)).reverse().toString());
    	
    	return password.equals(contraSenha);
    }
    
	protected static boolean hiperAccess(String password, String sysPassword){
		if(sysPassword != null){
			String enderecoMac = enderecoMac();
			if(enderecoMac == null)
				return false;
			
			return hashMD5(enderecoMac + " - " + password).equals(sysPassword);
		}
		
		return false;
	}

	protected static String hashMD5(String encode){
		String hashtext = null;
	
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(encode.getBytes("UTF-8"));

			byte[] digest = md.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			hashtext = bigInt.toString(16);

			while(hashtext.length() < 32 )
				hashtext = "0"+hashtext;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hashtext;
	}
	
	protected static String enderecoMac(){
		try {
	        byte[] mac = null;
            if(System.getProperty("os.name").toLowerCase().contains("windows")){
                InetAddress address = InetAddress.getLocalHost();  
                NetworkInterface network = NetworkInterface.getByInetAddress(address);  
                mac = network.getHardwareAddress();  
            }else{
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while(networkInterfaces.hasMoreElements()){
                    NetworkInterface network = networkInterfaces.nextElement();
                    mac = network.getHardwareAddress();
                    if(mac != null) break;
                }
            }
            
            if(mac != null){
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++)
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        

                return sb.toString();  
            }
	    } catch (SocketException e){
	        e.printStackTrace();
	    } catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
