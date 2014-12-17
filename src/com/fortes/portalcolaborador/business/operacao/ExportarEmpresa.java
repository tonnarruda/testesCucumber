package com.fortes.portalcolaborador.business.operacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.fortes.portalcolaborador.business.TransacaoPCManager;
import com.fortes.portalcolaborador.model.ColaboradorPC;
import com.fortes.portalcolaborador.model.EmailPC;
import com.fortes.portalcolaborador.model.HistoricoColaboradorPC;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation"})
public class ExportarEmpresa extends Operacao {

	private HistoricoColaboradorManager hisColaboradorManager;
	
	@Override
	public URLTransacaoPC getUrlTransacaoPC()
	{
		return null;//essa classe não precisa de URL
	}
	
	@Override
	public void gerarTransacao(String parametros)
	{
		Empresa empresa = null;
		try {
			JSONObject j = new JSONObject(parametros);			
			Long empresaId = Long.parseLong((String) j.get("id"));
			
			EmpresaManager empresaManager = (EmpresaManager) SpringUtil.getBeanOld("empresaManager");
			empresa = empresaManager.findEmailsEmpresa(empresaId);

			hisColaboradorManager = (HistoricoColaboradorManager) SpringUtil.getBeanOld("historicoColaboradorManager");
			enfilerarColaboradoresComHistoricosPC(new ArrayList(hisColaboradorManager.findByEmpresaPC(empresaId)));
			
			emailConfirmacaoPC(empresa);
			
		} catch (Exception e) {
			e.printStackTrace();
			if(empresa != null)
				emailInconsistenciaPC(empresa);
		}
		
	}
	
	private void enfilerarColaboradoresComHistoricosPC(List<HistoricoColaborador> historicos) 
	{
		Collection<HistoricoColaborador> historicosMontados = hisColaboradorManager.montaSituacaoHistoricoColaborador(historicos);
		
		ColaboradorPC colaboradorPC = null;
		HistoricoColaboradorPC historicoColaboradorPC;
		Set<ColaboradorPC> colaboradorPCs = new HashSet<ColaboradorPC>();
		Collection<Long> colabIds = new ArrayList<Long>(); 
		
		for (HistoricoColaborador historico : historicosMontados) 
		{
			if(colaboradorPC == null || !colaboradorPC.getCpf().equals(historico.getColaborador().getPessoal().getCpf())){
				colaboradorPC = new ColaboradorPC(historico.getColaborador());
				colaboradorPC.setHistoricosPc(new ArrayList<HistoricoColaboradorPC>());
				colabIds.add(historico.getColaborador().getId());
			}
			
			historicoColaboradorPC = new HistoricoColaboradorPC(historico);
			colaboradorPC.getHistoricosPc().add(historicoColaboradorPC);
			
			colaboradorPCs.add(colaboradorPC);
		}
		
		TransacaoPCManager transacaoPCManager= (TransacaoPCManager) SpringUtil.getBeanOld("transacaoPCManager");
		for (ColaboradorPC colabPC : colaboradorPCs) 
			transacaoPCManager.enfileirar(URLTransacaoPC.COLABORADOR_ATUALIZAR, colabPC.toJson());
	}
	
	private void emailConfirmacaoPC(Empresa empresa) 
	{
		EmailPC emailPC = new EmailPC(empresa.getEmailRespRH(), "[RH] Email de confirmação do Portal do Colaborador", "Email de confirmação do Portal do Colaborador", 
				"Os dados do colaborador da empresa " + empresa.getNome() + " estão integrados com o do portal do colaborador.");
		
		TransacaoPCManager transacaoPCManager = (TransacaoPCManager) SpringUtil.getBeanOld("transacaoPCManager");
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
			
			Mail mail = (Mail) SpringUtil.getBeanOld("mail");			
			mail.send(empresa, subject, body.toString(), null, empresa.getEmailRespRH());
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
