package com.fortes.portalcolaborador.business;


import com.fortes.business.GenericManager;
import com.fortes.portalcolaborador.model.AbstractAdapterPC;
import com.fortes.portalcolaborador.model.TransacaoPC;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;

public interface TransacaoPCManager extends GenericManager<TransacaoPC> 
{
	void enfileirar(AbstractAdapterPC adapterPC, URLTransacaoPC urlTransacaoPC, Long empresaId);
	void processarFila();
	public String testarConexao();
}
