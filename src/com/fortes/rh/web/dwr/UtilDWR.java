package com.fortes.rh.web.dwr;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.Mail;
import com.fortes.rh.web.ws.AcPessoalClient;


public class UtilDWR
{
	private EmpresaManager empresaManager;
	private Mail mail;
	private AcPessoalClient acPessoalClient;

	public String getToken(String acUsuario, String acSenha, String acUrlSoap, String codigoAC, String grupoAC)
	{
		String token = "";
		Empresa empresa = null;

		if(acSenha.trim().equals(""))
		{
			empresa = empresaManager.findByCodigoAC(codigoAC, grupoAC);

			empresa.setAcUsuario(acUsuario);
			empresa.setAcUrlSoap(acUrlSoap);
		}
		else
		{
			empresa = new Empresa();
			empresa.setAcUsuario(acUsuario);
			empresa.setAcSenha(acSenha);
			empresa.setAcUrlSoap(acUrlSoap);
		}

		try
		{
			token = acPessoalClient.getToken(empresa);
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

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public void setMail(Mail mail)
	{
		this.mail = mail;
	}
}
