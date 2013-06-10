package com.fortes.rh.web.action.security;

import org.apache.log4j.Logger;

import remprot.RPClient;
import remprot.RPWebClient;
import remprot.RPWebPacket;

import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionSupport;

@SuppressWarnings("serial")
public class Autenticador extends MyActionSupport
{
	private String codigoResposta;
	private String codigoOperacional;
	private String cnpj;
	private String nome;
	private String ultimoReset;
	private int ticket;

	private static Logger logger = Logger.getLogger(Autenticador.class);
	
	public String codigoOperacional()
	{
		RPClient client = com.fortes.rh.util.Autenticador.getRemprot();  

		try {
			codigoOperacional = client.requestKey(cnpj, nome, true);
			ultimoReset = DateUtil.formataDiaMesAno(client.getLastResetDate());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ActionSupport.SUCCESS;
	}
	
	public String validaCodigoResposta()
	{
		// TODO remprot - Tratar os erros com mensagens bem explicadas
		RPClient client = getClientRemprot(codigoResposta);

		if (com.fortes.rh.util.Autenticador.isRegistrado())
			return ActionSupport.SUCCESS;
		else
		{
			addActionError("Erro ao liberar licença. (Código do erro: " + String.valueOf(client.getErrors())+")");
			logger.info("Erro do RemProt ao liberar licença: " + String.valueOf(client.getErrors()));
			return ActionSupport.INPUT;
		}
	}
	
	public String geraTicket()
	{
		RPClient client = com.fortes.rh.util.Autenticador.getRemprot();  
		
		try {
			codigoOperacional = client.requestKey(cnpj, nome, true);
			
			RPWebClient rpWebClient = new RPWebClient();
			RPWebPacket rpWebPacket = rpWebClient.WebStoreOperational(nome, cnpj, codigoOperacional);
			ticket = rpWebPacket.TicketNo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ActionSupport.SUCCESS;
	}
	
	public String validaTicket()
	{
		try {
			RPWebClient rpWebClient = new RPWebClient();
			RPWebPacket rpWebPacket = rpWebClient.WebRetrieveAnswer(ticket);
			
			if(!rpWebPacket.Successful){
				addActionError("Ticket ainda não validado. Aguarde a validação do técnico e clique em OK.");
				return ActionSupport.INPUT;
			}
			
			RPClient client = getClientRemprot(rpWebPacket.AnswerCode);
			
			if (!com.fortes.rh.util.Autenticador.isRegistrado())
			{
				addActionError("Impossível liberar licença. (Código do erro: " + String.valueOf(client.getErrors())+ ")");
				logger.info("Erro do RemProt ao liberar licença: " + String.valueOf(client.getErrors()));
				return ActionSupport.INPUT;
			}
			
		} catch (Exception e) {
			addActionError("Erro ao liberar licença. Entre em contato com o suporte." );
			e.printStackTrace();
			return ActionSupport.INPUT;
		}
		
		return ActionSupport.SUCCESS;
	}
	
	private RPClient getClientRemprot(String codigoResposta)
	{
		RPClient client = com.fortes.rh.util.Autenticador.getRemprot();
		client.applyResponse(cnpj, nome, codigoResposta);
		client.loadLicense();
		
		return client;
	}
	
	public String registraNovaLicenca() 
	{
		return Action.SUCCESS;
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

	
	public int getTicket()
	{
		return ticket;
	}

	
	public void setTicket(int ticket)
	{
		this.ticket = ticket;
	}
}
