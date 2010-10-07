package com.fortes.rh.web.action.security;

import remprot.RPClient;

import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.opensymphony.xwork.ActionSupport;

@SuppressWarnings("serial")
public class Autenticador extends ActionSupport
{
	private String codigoResposta;
	private String codigoOperacional;
	private String cnpj;
	private String nome;

	ParametrosDoSistemaManager parametrosDoSistemaManager;

	//TODO remprot
	public String codigoOperacional()
	{
		RPClient client = new RPClient(33, "RH");
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);

		client.setServerAddress(parametrosDoSistema.getServidorRemprot());
		client.loadLicense();

		codigoOperacional = client.requestKey(cnpj, nome, false);

		return ActionSupport.SUCCESS;
	}

	public String validaCodigoResposta()
	{
		RPClient client = new RPClient(33, "RH");
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);

		client.setServerAddress(parametrosDoSistema.getServidorRemprot());
		client.loadLicense();

		client.applyResponse(cnpj, nome, codigoResposta);

		if (client.getRegistered())
			return ActionSupport.SUCCESS;
		else
		{
			System.out.println("Erro do RemProt: " + String.valueOf(client.getErrors()));
			return ActionSupport.INPUT;
		}
	}

	public String getCodigoOperacional()
	{
		return this.codigoOperacional;
	}

	public String getCodigoResposta()
	{
		return codigoResposta;
	}

	public void setCodigoResposta(String codigoResposta)
	{
		this.codigoResposta = codigoResposta;
	}

	public void setCodigoOperacional(String codigoOperacional)
	{
		this.codigoOperacional = codigoOperacional;
	}

	public String getCnpj()
	{
		return cnpj;
	}

	public void setCnpj(String cnpj)
	{
		this.cnpj = cnpj;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
}
