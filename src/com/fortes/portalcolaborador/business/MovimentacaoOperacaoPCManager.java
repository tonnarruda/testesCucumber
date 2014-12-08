package com.fortes.portalcolaborador.business;


import com.fortes.business.GenericManager;
import com.fortes.portalcolaborador.business.operacao.AtualizarColaborador;
import com.fortes.portalcolaborador.business.operacao.Operacao;
import com.fortes.portalcolaborador.model.AbstractAdapterPC;
import com.fortes.portalcolaborador.model.MovimentacaoOperacaoPC;

public interface MovimentacaoOperacaoPCManager extends GenericManager<MovimentacaoOperacaoPC> 
{
	void enfileirar(Class<? extends Operacao> operacao, AbstractAdapterPC adapterPC);
	void enfileirar(Class<? extends Operacao> operacao, String parametros);
	void processarOperacoes();
}
