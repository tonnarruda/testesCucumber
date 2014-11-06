package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiItemVO;

public interface SolicitacaoEpiManager extends GenericManager<SolicitacaoEpi>
{
	Collection<SolicitacaoEpi> findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, char situacao, Long tipoEpi, String situacaoColaborador, String[] estabelecimentoCheck);
	Integer getCount(Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, char situacao, Long tipoEpi, String situacaoColaborador, String[] estabelecimentoCheck);
	void save(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, String[] selectMotivoSolicitacaoEpi, Date dataEntrega, boolean entregue) throws Exception;
	void update(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, String[] selectMotivoSolicitacaoEpi);
	Collection<SolicitacaoEpi> findRelatorioVencimentoEpi(Long empresaId, Date vencimento, char agruparPor, boolean exibirVencimentoCA, String[] tipoEPICheck, String[] areasCheck, String[] estabelecimentoCheckList) throws ColecaoVaziaException;
	public Collection<SolicitacaoEpiItemEntrega> findRelatorioEntregaEpi(Long empresaId, Date dataIni, Date dataFim, String[] epiCheck, String[] colaboradorCheck, char agruparPor, boolean exibirDesligados) throws ColecaoVaziaException;
	Collection<SolicitacaoEpiItemVO> findEpisWithItens(Long empresaId, Date dataIni, Date dataFim, char situacao, Colaborador colaborador, Long tipoEpi, String situacaoColaborador, String[] estabelecimentoCheck);
	Collection<SolicitacaoEpi> findByColaboradorId(Long colaboradorId);
}