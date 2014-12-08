package com.fortes.portalcolaborador.business;

import java.util.Collection;

import com.fortes.portalcolaborador.model.MovimentacaoOperacaoPC;
import com.fortes.rh.util.SpringUtil;


public class GerenciadorMovimentacaoOperacao {
	
	private boolean emProcessamento;
	private static GerenciadorMovimentacaoOperacao instancia = null;
	
	private TransacaoPCManager transacaoPCManager;
	private MovimentacaoOperacaoPCManager movimentacaoOperacaoPCManager;
	
	private GerenciadorMovimentacaoOperacao() {}
	
	public static GerenciadorMovimentacaoOperacao getInstancia(){
		if (instancia == null)
			instancia = new GerenciadorMovimentacaoOperacao();
		
		return instancia;
	}
	
	public void processarOperacoes()
	{
		if(!emProcessamento){
			emProcessamento = true;

			movimentacaoOperacaoPCManager = (MovimentacaoOperacaoPCManager) SpringUtil.getBeanOld("movimentacaoOperacaoPCManager");
			transacaoPCManager = (TransacaoPCManager) SpringUtil.getBeanOld("transacaoPCManager");

			Collection<MovimentacaoOperacaoPC> movimentacoesOperacaoPC = movimentacaoOperacaoPCManager.findAll(new String[]{"id"});
			transacaoPCManager.processarOperacoes(movimentacoesOperacaoPC);
			emProcessamento = false;
		}

	}
}
