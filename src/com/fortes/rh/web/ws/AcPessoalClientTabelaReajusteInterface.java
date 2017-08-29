package com.fortes.rh.web.ws;

import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TSituacao;

public interface AcPessoalClientTabelaReajusteInterface
{
	void aplicaReajuste(Collection<HistoricoColaborador> historicosAc, Empresa empresa) throws Exception;
	void saveHistoricoColaborador(Collection<HistoricoColaborador> historicosAc, Empresa empresa, Double valorAntigo, boolean ehRealinhamento) throws Exception;
	void deleteHistoricoColaboradorAC(Empresa empresa, TSituacao... situacao) throws Exception;
	void setAcPessoalClient(AcPessoalClient acPessoalClient);
	public Boolean existeHistoricoContratualComPendenciaNoESocial(Empresa empresa, String colaboradorCodigoAC) throws Exception;
	public Boolean situacaoContratualEhInicioVinculo(Empresa empresa, String colaboradorCodigoAC, Date dataSituacao) throws Exception;
}