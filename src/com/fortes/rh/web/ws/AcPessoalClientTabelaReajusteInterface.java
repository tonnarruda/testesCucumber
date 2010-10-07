package com.fortes.rh.web.ws;

import java.util.Collection;

import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TSituacao;

public interface AcPessoalClientTabelaReajusteInterface
{
	void aplicaReajuste(Collection<HistoricoColaborador> historicosAc, Empresa empresa) throws Exception;
	void saveHistoricoColaborador(Collection<HistoricoColaborador> historicosAc, Empresa empresa, Double valorAntigo, boolean ehRealinhamento) throws Exception;
	void deleteHistoricoColaboradorAC(Empresa empresa, TSituacao... situacao) throws Exception;
	void setAcPessoalClient(AcPessoalClient acPessoalClient);
}