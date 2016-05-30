package com.fortes.rh.security.licenca;

import org.json.JSONException;
import org.json.JSONObject;

public class JClient {
	private String codigoProduto;
	private Boolean registrado;
	private Integer qtdAcessosSimultaneos;
	private String razaoSocial;
	private String mensagemDeErro;

	public JClient(JSONObject jsonObject) {

		try {
			codigoProduto = jsonObject.getString("LicenseProductCode");
			registrado = "Enabled".equals(jsonObject.getString("CustomerStatus"));
			qtdAcessosSimultaneos = new Integer(jsonObject.getString("LicenseConcurrentUsers"));
			razaoSocial = jsonObject.getString("LicenseVendorName");
			mensagemDeErro = jsonObject.getString("ErrorMessage");
		} catch (JSONException e) {
			e.printStackTrace();
		}
				
	}
	
	public String getCodigoProduto()
	{
		return codigoProduto;
	}

	public Boolean getRegistrado()
	{
		return registrado;
	}

	public Integer getQtdAcessosSimultaneos()
	{
		return qtdAcessosSimultaneos;
	}

	public String getRazaoSocial()
	{
		return razaoSocial;
	}

	public String getMensagemDeErro()
	{
		return mensagemDeErro;
	}
}
