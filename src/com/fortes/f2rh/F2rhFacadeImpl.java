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
		ArrayList<Curriculo> cus = new ArrayList<Curriculo>();
		try {
			JsonConfig jsonConfig = new JsonConfig();

			JSONArray arr = JSONArray.fromObject(config.getJson(), jsonConfig);
			cus = (ArrayList<Curriculo>) JSONArray.toList(arr, Curriculo.class);
			//cu = (Curriculo) JSONObject.toBean(json, Curriculo.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Collection<Curriculo> curriculos = new ArrayList<Curriculo>();
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
		 
		//String obj =  "[{\"endereco\":\"Rua Coronel Linhares 115/202\",\"nome\":\"Henrique de Albuquerque Vasconcelos Soares\",\"cep\":\"60170-240\",\"user\":{\"name\":null,\"login\":\"010.178.063-06\",\"email\":\"henriquesoares@grupofortes.com.br\"},\"nacionalidade\":\"Brasileiro\",\"escolaridade\":\"7\",\"data_nascimento\":\"1984-12-27\",\"bairro\":\"Meireles\",\"curriculo_telefones\":[{\"ddd\":\"85\",\"numero\":\"8747-2023\"}],\"estado_civil\":\"1\",\"estado\":\"CE\",\"curso\":\"Letras\",\"cidade\":\"04400\",\"sexo\":\"M\",\"observacoes_complementares\":\"\",\"salario\":0.0,\"area_formacao\":\"2\"},{\"endereco\":null,\"nome\":\"Ana Cristina Soares Silva\",\"cep\":null,\"user\":{\"name\":null,\"login\":\"772.421.813-72\",\"email\":\"ana.kriss.tina@hotmail.com\"},\"nacionalidade\":null,\"escolaridade\":null,\"data_nascimento\":\"1975-11-28\",\"bairro\":null,\"curriculo_telefones\":[],\"estado_civil\":null,\"estado\":null,\"curso\":null,\"cidade\":null,\"sexo\":null,\"observacoes_complementares\":null,\"salario\":null,\"area_formacao\":null}]";
		//String obj = "[{\"escolaridade_rh\":\"Superior Incompleto\",\"updated_at\":\"2010-10-25T16:24:27Z\",\"nome\":\"Henrique de Albuquerque Vasconcelos Soares\",\"cidade_rh\":\"Fortaleza\",\"id\":15,\"estado\":\"CE\"},{\"escolaridade_rh\":null,\"updated_at\":\"2009-10-27T13:06:24Z\",\"nome\":\"Ana Cristina Soares Silva\",\"cidade_rh\":null,\"id\":112,\"estado\":null}]";
		return  obj;
	}

}
