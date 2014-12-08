package com.fortes.portalcolaborador.business;


import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.portalcolaborador.model.MovimentacaoOperacaoPC;
import com.fortes.portalcolaborador.model.TransacaoPC;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;

public interface TransacaoPCManager extends GenericManager<TransacaoPC> 
{
	void enfileirar(URLTransacaoPC urlTransacaoPC, String parametros);
	void processarFila();
	public String testarConexao();
	void processarOperacoes(Collection<MovimentacaoOperacaoPC> movimentacoesOperacaoPC);
}
