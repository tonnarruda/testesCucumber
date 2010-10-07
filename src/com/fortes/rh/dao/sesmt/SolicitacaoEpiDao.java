package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;

/**
 * @author Tiago Lopes
 */
public interface SolicitacaoEpiDao extends GenericDao<SolicitacaoEpi>
{
	Collection<SolicitacaoEpi> findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, Boolean entregue);
	Integer getCount(Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, Boolean entregue);
	SolicitacaoEpi findByIdProjection(Long solicitacaoEpiId);
	Collection<SolicitacaoEpi> findVencimentoEpi(Long empresaId, Date data, boolean exibirVencimentoCA);
	public Collection<SolicitacaoEpi> findEntregaEpi(Long empresaId, Long[] epiCheck);
}