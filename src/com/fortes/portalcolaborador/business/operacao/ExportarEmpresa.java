package com.fortes.portalcolaborador.business.operacao;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.fortes.portalcolaborador.business.ColaboradorPCManager;
import com.fortes.portalcolaborador.business.TransacaoPCManager;
import com.fortes.portalcolaborador.model.EmailPC;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

@SuppressWarnings({"deprecation"})
public class ExportarEmpresa extends Operacao {

	private EmpresaManager empresaManager;
	private TransacaoPCManager transacaoPCManager;
	private Mail mail;
	
	@Override
	public URLTransacaoPC getUrlTransacaoPC()
	{
		return null;//essa classe não precisa de URL
	}
	
	@Override
	public void gerarTransacao(String parametros) throws Exception
	{
		Empresa empresa = null;
		try {
			JSONObject j = new JSONObject(parametros);	
			
			if(j.get("id") != null)
			{
				empresaManager = (EmpresaManager) SpringUtil.getBeanOld("empresaManager");
				transacaoPCManager = (TransacaoPCManager) SpringUtil.getBeanOld("transacaoPCManager");

				Long empresaId = Long.parseLong(((Integer) j.get("id")).toString());
				empresa = empresaManager.findEmailsEmpresa(empresaId);
				
				if(empresa != null)
				{
					ColaboradorPCManager colaboradorPCManager = (ColaboradorPCManager) SpringUtil.getBeanOld("colaboradorPCManager");
					colaboradorPCManager.enfileirarColaboradoresPCComHistoricos(URLTransacaoPC.COLABORADOR_ATUALIZAR, null, empresaId);
					emailConfirmacaoPC(empresa);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			if(empresa != null)
				emailInconsistenciaPC(empresa);
			
			throw new Exception(e);
		}
		
	}
	
	private void emailConfirmacaoPC(Empresa empresa) 
	{
		EmailPC emailPC = new EmailPC(empresa.getEmailRespRH(), "[RH] Email de confirmação do Portal do Colaborador", "Email de confirmação do Portal do Colaborador", 
				"Os dados do colaborador da empresa " + empresa.getNome() + " estão integrados com o do portal do colaborador.");
		
		transacaoPCManager.enfileirar(URLTransacaoPC.ENVIAR_EMAIL, emailPC.toJson());
	}

	private void emailInconsistenciaPC(Empresa empresa) {
		try {
			String subject = "[RH] Email de inconsistência do Portal do Colaborador";
			
			StringBuilder body = new StringBuilder();
			body.append("Ocorreu uma inconsistência no processo de envio de dados para o Portal do Colaborador.<br /><br />");
			body.append("<B> Empresa: ");
			body.append(empresa.getNome());
			body.append("<B><br /><br />");
			body.append("Favor entrar em contato com o suporte do RH.<br /><br />");
			
			mail = (Mail) SpringUtil.getBeanOld("mail");			
			mail.send(empresa, subject, body.toString(), null, empresa.getEmailRespRH());
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
