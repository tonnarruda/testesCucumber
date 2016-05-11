package com.fortes.rh.web.ws;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;

public interface AcPessoalClientColaboradorOcorrencia
{
	boolean criarColaboradorOcorrencia(TOcorrenciaEmpregado colaboradorOcorrencia, Empresa empresa) throws Exception;
	boolean removerColaboradorOcorrencia(TOcorrenciaEmpregado colaboradorOcorrencia, Empresa empresa) throws Exception;
	boolean atualizarColaboradorOcorrencia(TOcorrenciaEmpregado ocorrenciaEmpregadoAntiga, TOcorrenciaEmpregado ocorrenciaEmpregadoNova, Empresa empresa) throws Exception;
}
