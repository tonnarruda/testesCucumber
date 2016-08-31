package com.fortes.rh.security.licenca;

import org.json.JSONException;
import org.json.JSONObject;

public class JClient {
	private String codigoProduto;
	private String nomeProduto;
	private Boolean registrado;
	private Integer qtdAcessosSimultaneos;
	private Integer modulosDisponiveis;
	private String razaoSocial;
	private String nomeCliente;
	private String dataVencimento;
	private Integer qtdColaboradores;
	private String mensagemDeErro;
	private JSONObject jsonObject;

	public JClient() {
		super();
	}

	public JClient(JSONObject jsonObject) {

		try {
			codigoProduto = jsonObject.get("LicenseProductCode").toString();
			nomeProduto = jsonObject.get("LicenseProductName").toString();
			registrado = "Enabled".equals(jsonObject.get("CustomerStatus").toString());
			qtdAcessosSimultaneos = (jsonObject.get("LicenseConcurrentUsers").toString().equals("null") ? 0 : jsonObject.getInt("LicenseConcurrentUsers"));
			modulosDisponiveis = (jsonObject.get("LicenseEnabledModules").toString().equals("null") ? 0 : jsonObject.getInt("LicenseEnabledModules"));
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

	public void setCodigoProduto(String codigoProduto)
	{
		this.codigoProduto = codigoProduto;
	}

	public String getNomeProduto()
	{
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto)
	{
		this.nomeProduto = nomeProduto;
	}

	public Boolean getRegistrado()
	{
		return registrado;
	}

	public void setRegistrado(Boolean registrado)
	{
		this.registrado = registrado;
	}

	public Integer getQtdAcessosSimultaneos()
	{
		return qtdAcessosSimultaneos;
	}

	public void setQtdAcessosSimultaneos(Integer qtdAcessosSimultaneos)
	{
		this.qtdAcessosSimultaneos = qtdAcessosSimultaneos;
	}

	public String getRazaoSocial()
	{
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial)
	{
		this.razaoSocial = razaoSocial;
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

	public Integer getQtdColaboradores()
	{
		return qtdColaboradores;
	}

	public void setQtdColaboradores(Integer qtdColaboradores)
	{
		this.qtdColaboradores = qtdColaboradores;
	}

	public String getMensagemDeErro()
	{
		return mensagemDeErro;
	}

	public void setMensagemDeErro(String mensagemDeErro)
	{
		this.mensagemDeErro = mensagemDeErro;
	}

	public JSONObject getJsonObject()
	{
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	public Integer getModulosDisponiveis()
	{
		return modulosDisponiveis;
	}

	public void setModulosDisponiveis(Integer modulosDisponiveis)
	{
		this.modulosDisponiveis = modulosDisponiveis;
	}
}
