package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiItemVO;

public interface SolicitacaoEpiManager extends GenericManager<SolicitacaoEpi>
{
	Collection<SolicitacaoEpi> findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, String situacao, Long tipoEpi, String situacaoColaborador, String[] estabelecimentoCheck, char ordem);
	Integer getCount(Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, String situacao, Long tipoEpi, String situacaoColaborador, String[] estabelecimentoCheck, char ordem);
	void save(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, String[] selectMotivoSolicitacaoEpi, Date dataEntrega, boolean entregue, String[] selectTamanhoEpi) throws Exception;
	void update(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, String[] selectMotivoSolicitacaoEpi, String[] selectTamanhoEpi);
	Collection<SolicitacaoEpi> findRelatorioVencimentoEpi(Long empresaId, Date vencimento, char agruparPor, boolean exibirVencimentoCA, String[] tipoEPICheck, String[] areasCheck, String[] estabelecimentoCheckList) throws ColecaoVaziaException;
	public Collection<SolicitacaoEpiItemEntrega> findRelatorioEntregaEpi(Long empresaId, Date dataIni, Date dataFim, String[] epiCheck, String[] areaIds, String[] colaboradorCheck, char agruparPor, boolean exibirDesligados) throws ColecaoVaziaException;
	Collection<SolicitacaoEpiItemVO> findEpisWithItens(Long empresaId, Date dataIni, Date dataFim, String situacao, Colaborador colaborador, Long tipoEpi, String situacaoColaborador, String[] estabelecimentoCheck, char ordem);
	Collection<SolicitacaoEpi> findByColaboradorId(Long colaboradorId);
	Collection<SolicitacaoEpiItemDevolucao> findRelatorioDevolucaoEpi(Long empresaId, Date dataIni, Date dataFim, String[] epiCheck, String[] areasCheck, String[] colaboradorCheck, char agruparPor, boolean exibirDesligados) throws ColecaoVaziaException ;
}