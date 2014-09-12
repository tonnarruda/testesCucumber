package com.fortes.rh.thread;

import java.util.ArrayList;
import java.util.Collection;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.URLTransacaoPC;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;

@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class EnfileiraColaboradorComHistoricosPCThread extends Thread{

	private Empresa empresa;
	
	public EnfileiraColaboradorComHistoricosPCThread(Empresa empresa){
		this.empresa = empresa;
	}
	
	public void run() {
		
		try {
			HistoricoColaboradorManager hisColaboradorManager = (HistoricoColaboradorManager) SpringUtil.getBeanOld("historicoColaboradorManager");
			Collection<HistoricoColaborador> historicosColaboradores = hisColaboradorManager.findPendenciasPortal(null, new Long[]{empresa.getId()});
			hisColaboradorManager.enfilerarColaboradoresComHistoricosPC(new Long[]{empresa.getId()}, new ArrayList(historicosColaboradores), URLTransacaoPC.COLABORADOR_ATUALIZAR);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			enviarEmailinconsistenciaPC();
		}
    }

	public void enviarEmailinconsistenciaPC() {
		try {
			String subject = "[RH] - Email de inconsistência do Portal do Colaborador";
			
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
