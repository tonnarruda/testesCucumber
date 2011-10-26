package com.fortes.rh.security.spring.aop.callback;

import com.fortes.rh.model.dicionario.OperacaoAuditoria;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class AuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		GeraDadosAuditados dadosAuditados = null;
		
		if (metodo.getOperacao().equals("Inserção"))
			dadosAuditados = OperacaoAuditoria.INSERCAO.getDadosAuditados(metodo);
		
		else if (metodo.getOperacao().equals("Atualização"))
			dadosAuditados = OperacaoAuditoria.ATUALIZACAO.getDadosAuditados(metodo);

		else if (metodo.getOperacao().equals("Remoção"))
			dadosAuditados = OperacaoAuditoria.REMOCAO.getDadosAuditados(metodo);
		
		metodo.processa();
			
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), dadosAuditados.getChave(), dadosAuditados.gera());
	}
	
}
