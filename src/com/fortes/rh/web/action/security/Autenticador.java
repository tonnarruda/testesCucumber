package com.fortes.rh.web.action.security;

import remprot.RPClient;
import remprot.RPWebClient;
import remprot.RPWebPacket;

import com.fortes.rh.exception.NotConectAutenticationException;
import com.fortes.rh.exception.NotRegistredException;
import com.fortes.rh.util.DateUtil;
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
	public String codigoOperacional()
	{
		try {
			RPClient client = null; //com.fortes.rh.util.Autenticador.getRemprot();  
			codigoOperacional = client.requestKey(cnpj, nome, true);
			ultimoReset = DateUtil.formataDiaMesAno(client.getLastResetDate());
			return ActionSupport.SUCCESS;
		} catch (NotConectAutenticationException e) {
			e.printStackTrace();
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Favor entrar em contato com o suporte.<br/>Erro no processo de liberação do código operacional.");
		}

		return ActionSupport.INPUT;
	}
	
	public String validaCodigoResposta()
	{
		try {
			clientRemprot(codigoResposta);
			return ActionSupport.SUCCESS;
		} catch (NotConectAutenticationException e) {
			e.printStackTrace();
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Favor entrar em contato com o suporte.<br/>Erro no processo de validação do código de resposta.");
		}
		
		return ActionSupport.INPUT;
	}
	
	public String geraTicket()
	{
		try {
			RPClient client = null; //com.fortes.rh.util.Autenticador.getRemprot();  
			codigoOperacional = client.requestKey(cnpj, nome, true);
			RPWebClient rpWebClient = new RPWebClient();
			RPWebPacket rpWebPacket = rpWebClient.WebStoreOperational(nome, cnpj, codigoOperacional);
			ticket = rpWebPacket.TicketNo;
			return ActionSupport.SUCCESS;
		} catch (NotConectAutenticationException e) {
			e.printStackTrace();
			addActionMessage(e.getMessage());
        } catch (Exception e) {
        	e.printStackTrace();
        	addActionError("Favor entrar em contato com o suporte.<br/>Erro no processo de geração do ticket.");
		}

		return ActionSupport.INPUT;
	}
	
	public String validaTicket()
	{
		try {
			RPWebClient rpWebClient = new RPWebClient();
			RPWebPacket rpWebPacket = rpWebClient.WebRetrieveAnswer(ticket);
			
			if(!rpWebPacket.Successful)	
				throw new NotConectAutenticationException("Aguarde a validação do técnico e clique em OK. </br>" +
														  "Caso já tenha validado, verifique se os dados da licença de uso estão corretos juntamente com o técnico:</br>" +
														  "CNPJ: "+ getCnpj() + "</br>"+
														  "Razão Social: " + getNome() + "</br>"+
														  "Se houver algo errado, clique em \"Editar dados da licença de uso\" e gere um novo ticket.");

			clientRemprot(rpWebPacket.AnswerCode);
			return ActionSupport.SUCCESS;
		} catch (NotConectAutenticationException e) {
			e.printStackTrace();
			addActionWarning(e.getMessage());
		} catch (Exception e) {
			addActionError("Erro ao liberar licença. Entre em contato com o suporte." );
			e.printStackTrace();
		}
		
		return ActionSupport.INPUT;
	}
	
	private void clientRemprot(String codigoResposta) throws NotConectAutenticationException, NotRegistredException
	{
//		com.fortes.rh.util.Autenticador.getRemprot().applyResponse(cnpj, nome, codigoResposta);
//		com.fortes.rh.util.Autenticador.loadLicense();
//		com.fortes.rh.util.Autenticador.verificaRegistro();
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
