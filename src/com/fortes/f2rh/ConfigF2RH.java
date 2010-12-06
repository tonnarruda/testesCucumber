package com.fortes.f2rh;

public class ConfigF2RH {

	private String url = "";//"http://10.1.2.9:3000/rh_curriculos.json";
	private String json;
	private String[] consulta;

	public ConfigF2RH() 
	{
	}
	
	public ConfigF2RH(String[] consulta) 
	{
		this.consulta = consulta;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String[] getConsulta() {
		return consulta;
	}

	public void setConsulta(String[] consulta) {
		this.consulta = consulta;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
}
