package com.fortes.rh.security.licenca;

import org.json.JSONException;
import org.json.JSONObject;

public class JClient {
	private String codigoProduto;
	private String nomeProduto;
	private Boolean registrado;
	private Integer qtdAcessosSimultaneos;
	private String razaoSocial;
	private String nomeCliente;
	private String dataVencimento;
	private Integer qtdColaboradores;
	private String mensagemDeErro;
	private JSONObject jsonObject;

	public JClient(JSONObject jsonObject) {

		try {
			codigoProduto = jsonObject.get("LicenseProductCode").toString();
			nomeProduto = jsonObject.get("LicenseProductName").toString();
			registrado = "Enabled".equals(jsonObject.get("CustomerStatus").toString());
			qtdAcessosSimultaneos = (jsonObject.get("LicenseConcurrentUsers").toString().equals("null") ? 0 : jsonObject.getInt("LicenseConcurrentUsers"));
			razaoSocial = jsonObject.get("LicenseVendorName").toString();
			nomeCliente = jsonObject.get("CustomerName").toString();
			dataVencimento = jsonObject.get("LicenseExpiryDate").toString();
			qtdColaboradores = (jsonObject.get("LicenseBusinessSize").toString().equals("null") ? 0 : jsonObject.getInt("LicenseBusinessSize"));
			mensagemDeErro = jsonObject.get("ErrorMessage").toString();
			this.jsonObject = jsonObject;
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

	public String getNomeCliente()
	{
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente)
	{
		this.nomeCliente = nomeCliente;
	}

	public String getDataVencimento()
	{
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento)
	{
		this.dataVencimento = dataVencimento;
	}

	public String getNomeProduto()
	{
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto)
	{
		this.nomeProduto = nomeProduto;
	}

	public JSONObject getJsonObject()
	{
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	public Integer getQtdColaboradores()
	{
		return qtdColaboradores;
	}

	public void setQtdColaboradores(Integer qtdColaboradores)
	{
		this.qtdColaboradores = qtdColaboradores;
	}

}
