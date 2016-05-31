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
			codigoProduto = jsonObject.get("LicenseProductCode").toString();
			registrado = "Enabled".equals(jsonObject.get("CustomerStatus").toString());
			qtdAcessosSimultaneos = (Integer) (jsonObject.get("LicenseConcurrentUsers").toString().equals("null") ? 0 : jsonObject.get("LicenseConcurrentUsers").toString());
			razaoSocial = jsonObject.get("LicenseVendorName").toString();
			mensagemDeErro = jsonObject.get("ErrorMessage").toString();
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
