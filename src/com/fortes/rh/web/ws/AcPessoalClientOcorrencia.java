package com.fortes.rh.web.ws;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TOcorrencia;

public interface AcPessoalClientOcorrencia
{
	String criarTipoOcorrencia(TOcorrencia tocorrencia, Empresa empresa) throws Exception;
	boolean removerTipoOcorrencia(TOcorrencia ocorrencia, Empresa empresa) throws Exception;
}
