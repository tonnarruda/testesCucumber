package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.HistoricoExtintor;

public interface HistoricoExtintorDao extends GenericDao<HistoricoExtintor>
{
	Collection<HistoricoExtintor> findByExtintor(Long extintorId);
	void removeByExtintor(Long extintorId);
	Collection<HistoricoExtintor> findAllHistoricosAtuais(Integer page, Integer pagingSize,	Date data, String localizacao, String extintorTipo, Long estabelecimentoId, Long empresaId);
	public Integer countAllHistoricosAtuais(Date data, String localizacao, String extintorTipo, Long estabelecimentoId, Long empresaId);
}