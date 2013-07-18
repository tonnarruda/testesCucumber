package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.HistoricoExtintor;

public interface HistoricoExtintorManager extends GenericManager<HistoricoExtintor>
{
	void update(HistoricoExtintor historicoExtintor);
	Collection<HistoricoExtintor> findByExtintor(Long extintorId);
	void removeByExtintor(Long extintorId);
	Collection<HistoricoExtintor> findAllHistoricosAtuais(Integer page, Integer pagingSize, Date data, String localizacao, String extintorTipo, Long estabelecimentoId, Long empresaId);
	void insertNewHistoricos(Long[] extintorsCheck, HistoricoExtintor historicoExtintor);
	Integer countAllHistoricosAtuais(Date data, String localizacao, String extintorTipo, Long estabelecimentoId, Long empresaId);
}