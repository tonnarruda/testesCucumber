package com.fortes.rh.thread;

import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.SpringUtil;

@SuppressWarnings({ "deprecation" })
public class EnviaEmailSolicitanteThread extends Thread{

	private Empresa empresa;
	private Solicitacao solicitacao;
	Usuario usuario;
	
	public EnviaEmailSolicitanteThread(Solicitacao solicitacao, Empresa empresa, Usuario usuario){
		this.empresa = empresa;
		this.solicitacao = solicitacao;
		this.usuario = usuario;
	}
	
	public void run() {
		try {
			SolicitacaoManager solicitacaoManager = (SolicitacaoManager) SpringUtil.getBeanOld("solicitacaoManager");
			solicitacaoManager.emailSolicitante(solicitacao, empresa, usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
