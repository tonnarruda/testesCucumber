package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.SituacaoSolicitacaoEpi;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;

public interface SolicitacaoEpiManager extends GenericManager<SolicitacaoEpi>
{
	Collection<SolicitacaoEpi> findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, SituacaoSolicitacaoEpi situacao);
	Integer getCount(Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, SituacaoSolicitacaoEpi situacao);
	void save(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado) throws Exception;
	void update(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado);
	void entrega(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado) throws Exception;
	Collection<SolicitacaoEpi> findRelatorioVencimentoEpi(Long empresaId, Date vencimento, char agruparPor, boolean exibirVencimentoCA, String[] tipoEPICheck) throws ColecaoVaziaException;
	public Collection<SolicitacaoEpi> findRelatorioEntregaEpi(Long empresaId, Date dataIni, Date dataFim, String[] epiCheck) throws ColecaoVaziaException;
}