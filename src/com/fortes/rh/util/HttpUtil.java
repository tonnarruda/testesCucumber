package com.fortes.rh.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpUtil
{
	public static String getHtmlViaPost(String url) throws HttpException, IOException
	{
		HttpClient client = new HttpClient();
	    client.getParams().setParameter("http.useragent", "Test Client");

	    PostMethod method = new PostMethod(url);

	    client.executeMethod(method);
	    return method.getResponseBodyAsString();
	}
	
	public static String getHtmlViaGet(String url)
	{
		return StringUtil.getHTML(url);
	}
}
