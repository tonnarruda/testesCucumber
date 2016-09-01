package com.fortes.rh.test.util.mockObjects;

import com.fortes.rh.security.licenca.JClient;


public class MockAutenticadorJarvisRegistrado extends MockAutenticador
{
	private static JClient clientJarvis = null;
	
	public static JClient getClient() throws Exception
	{
		if(clientJarvis == null || clientJarvis.getCodigoProduto().equals("null")) {
			clientJarvis = new JClient();
			clientJarvis.setQtdColaboradores(100);
			clientJarvis.setCodigoProduto("47");
			clientJarvis.setRegistrado(true);
		}
		
		return clientJarvis;
	}
	
	public static boolean isRegistrado() throws Exception
	{
		return true;
	}
}