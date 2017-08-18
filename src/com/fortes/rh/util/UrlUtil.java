package com.fortes.rh.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlUtil {

	public static boolean pingUrl(String url){
		try {
		    final URLConnection connection = new URL(url).openConnection();
		    connection.connect();
		    System.out.println("Service URL: " + url + " conectado");
		    return true;
		} catch (final MalformedURLException e) {
			System.out.println("Bad URL: " + url);
		} catch (final IOException e) {
		    System.out.println("Service " + url + " Não conecta!");
		}
		return false;
	}
}