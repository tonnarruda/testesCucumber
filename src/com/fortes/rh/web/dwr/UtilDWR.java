package com.fortes.rh.web.dwr;

import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.util.Mail;
import com.fortes.rh.web.ws.AcPessoalClient;


public class UtilDWR
{
	private Mail mail;
	private AcPessoalClient acPessoalClient;
	private GrupoACManager grupoACManager;

	public String getToken(String grupoAC)
	{ 
		String token = "";
		GrupoAC grupo = grupoACManager.findByCodigo(grupoAC);
		
		if(grupo == null)
			return "Grupo AC não encontrado";

		try
		{
			token = acPessoalClient.getToken(grupo);
			token = "Conexão efetuada com sucesso.";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e.getMessage().equals("Usuário Não Autenticado!"))
				token = "Usuário Não Autenticado.";
			else
				token = "Serviço não disponível.";
		}

		return token;
	}

	public String enviaEmail(String email)
	{
		String msg = "";

		try
		{
			mail.send(null, "Teste do envio de email do Fortes RH", "Este email foi enviado de forma automática, não responda.", null, email);
			msg = "Email enviado com sucesso.";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			msg = e.getMessage();
			if(msg == null)
				msg = "Erro no login ou senha do usuário.";
		}

		return msg;
	}

	public void setAcPessoalClient(AcPessoalClient acPessoalClient)
	{
		this.acPessoalClient = acPessoalClient;
	}

	public void setMail(Mail mail)
	{
		this.mail = mail;
	}

	public void setGrupoACManager(GrupoACManager grupoACManager) {
		this.grupoACManager = grupoACManager;
	}
}
