package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiItemVO;

/**
 * @author Tiago Lopes
 */
public interface SolicitacaoEpiDao extends GenericDao<SolicitacaoEpi>
{
	Collection<SolicitacaoEpi> findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, char situacaoSolicitacaoEpi);
	Integer getCount(Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, char situacaoSolicitacaoEpi);
	SolicitacaoEpi findByIdProjection(Long solicitacaoEpiId);
	Collection<SolicitacaoEpi> findVencimentoEpi(Long empresaId, Date data, boolean exibirVencimentoCA, Long[] tipoEPIIds, Long[] areasIds, Long[] estabelecimentoIds, char agruparPor);
	public Collection<SolicitacaoEpiItemEntrega> findEntregaEpi(Long empresaId, Date dataIni, Date dataFim, Long[] epiCheck, Long[] colaboradorCheck, char agruparPor);
	public Collection<SolicitacaoEpiItemVO> findEpisWithItens(Long empresaId, Date dataIni, Date dataFim, char situacao);
}