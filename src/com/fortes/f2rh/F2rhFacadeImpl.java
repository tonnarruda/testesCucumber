package com.fortes.f2rh;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

public class F2rhFacadeImpl implements F2rhFacade {

	@SuppressWarnings({ "deprecation", "unchecked" })
	public Collection<Curriculo> obterCurriculos(ConfigF2RH config) {
		
		Collection<Curriculo> curriculos = new ArrayList<Curriculo>();
		ArrayList<Curriculo> cus = new ArrayList<Curriculo>();
		
		try {
			JsonConfig jsonConfig = new JsonConfig();

			JSONArray arr = JSONArray.fromObject(config.getJson(), jsonConfig);
			cus = (ArrayList<Curriculo>) JSONArray.toList(arr, Curriculo.class);
			//cu = (Curriculo) JSONObject.toBean(json, Curriculo.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		curriculos.addAll(cus);
		return curriculos;
	}
	

	public String find_f2rh(ConfigF2RH config) {
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod( config.getUrl() );
		get.setQueryString(prepareParams(config.getConsulta()));
		String obj = "";
		try {
			client.executeMethod( get );
			obj = get.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			get.releaseConnection();
		}
		return  obj;
	}
	
	public NameValuePair[] prepareParams(String[] query) {
		ArrayList<NameValuePair> lista = new ArrayList<NameValuePair>();
		for (String string : query) {
			String[] pair = string.split("=");
			if(pair.length == 2) {
				NameValuePair nvpair = new NameValuePair(pair[0], pair[1]);
				lista.add(nvpair);				
			}
		}
		NameValuePair[] params = new NameValuePair[]{};
		params = lista.toArray(params);
		return params;
	}

}
