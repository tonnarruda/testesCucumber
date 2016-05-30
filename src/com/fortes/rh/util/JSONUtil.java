package com.fortes.rh.util;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class JSONUtil {

	public static JSONObject getObject(String url) throws Exception
	{
		InputStream is = null;
		JSONObject jsonObject = null;
		try {
			is = new URL(url).openStream();
			jsonObject = new JSONObject(IOUtils.toString(is));
		} finally {
			if(is != null)
				is.close();
		}
		
		return jsonObject;
	}
}
