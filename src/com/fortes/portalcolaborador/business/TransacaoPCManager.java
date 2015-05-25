package com.fortes.portalcolaborador.business;


import java.io.IOException;
import java.util.Collection;

import org.apache.commons.httpclient.HttpException;

import com.fortes.business.GenericManager;
import com.fortes.portalcolaborador.model.MovimentacaoOperacaoPC;
import com.fortes.portalcolaborador.model.TransacaoPC;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;

public interface TransacaoPCManager extends GenericManager<TransacaoPC> 
{
	void enfileirar(URLTransacaoPC urlTransacaoPC, String parametros);
	void processarFila();
	Integer enviar(TransacaoPC transacaoPC, String pcToken) throws HttpException, IOException;
	String testarConexao();
	void processarOperacoes(Collection<MovimentacaoOperacaoPC> movimentacoesOperacaoPC) throws Exception;
}
