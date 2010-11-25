package com.fortes.f2rh;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
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
		GetMethod get = new GetMethod("http://10.1.2.9:3000/rh_curriculos.json");
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


	public String find_f2rh_by_ids(ConfigF2RH config) {
		// TODO Auto-generated method stub
		return null;
	}

}
