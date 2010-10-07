package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;

public interface HistoricoFuncaoDao extends GenericDao<HistoricoFuncao>
{
	Collection<HistoricoFuncao> findHistoricoByFuncoesId(Collection<Long> idsFuncoes, Date dataAdmissao, Date dataPpp);
	Collection<HistoricoFuncao> getHistoricosByDateFuncaos(Collection<Long> funcaoIds, Date data);
//	List getHistoricoNaData(Long empresaId, Date data);
	void removeByFuncoes(Long[] funcaoIds);
	HistoricoFuncao findByIdProjection(Long historicoFuncaoId);
	HistoricoFuncao findUltimoHistoricoAteData(Long id, Date data);
	Collection<HistoricoFuncao> findEpis(Collection<Long> funcaoIds, Date data);
}