package com.fortes.rh.business.portalcolaborador;


import com.fortes.business.GenericManager;
import com.fortes.rh.model.dicionario.URLTransacaoPC;
import com.fortes.rh.model.portalcolaborador.TransacaoPC;

public interface TransacaoPCManager extends GenericManager<TransacaoPC> 
{
	void enfileirar(Object objeto, Class<?> classe, URLTransacaoPC urlTransacaoPC);
}
