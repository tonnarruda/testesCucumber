package com.fortes.rh.web.action.security;

import org.apache.log4j.Logger;

import remprot.RPClient;

import com.fortes.rh.util.DateUtil;
import com.opensymphony.xwork.ActionSupport;

@SuppressWarnings("serial")
public class Autenticador extends ActionSupport
{
	private String codigoResposta;
	private String codigoOperacional;
	private String cnpj;
	private String nome;
	private String ultimoReset;

	private static Logger logger = Logger.getLogger(Autenticador.class);
	//TODO remprot
	public String codigoOperacional()
	{
		RPClient client = com.fortes.rh.util.Autenticador.getRemprot();  

		try {
			codigoOperacional = client.requestKey(cnpj, nome, false);
			ultimoReset = DateUtil.formataDiaMesAno(client.getLastResetDate());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ActionSupport.SUCCESS;
	}

	public String validaCodigoResposta()
	{
		RPClient client = com.fortes.rh.util.Autenticador.getRemprot();
		client.applyResponse(cnpj, nome, codigoResposta);
		
		client.loadLicense();

		if (client.getRegistered())
			return ActionSupport.SUCCESS;
		else
		{
			addActionError("Erro ao liberar licença: código do erro: " + String.valueOf(client.getErrors()));
			logger.info("Erro do RemProt ao liberar licença: " + String.valueOf(client.getErrors()));
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

	public String getUltimoReset() {
		return ultimoReset;
	}
}
