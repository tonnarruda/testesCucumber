package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco;

public interface HistoricoFuncaoDao extends GenericDao<HistoricoFuncao>
{
	Collection<HistoricoFuncao> findHistoricoByFuncoesId(Collection<Long> idsFuncoes, Date dataAdmissao, Date dataPpp);
	Collection<HistoricoFuncao> getHistoricosByDateFuncaos(Collection<Long> funcaoIds, Date data);
	void removeByFuncoes(Long[] funcaoIds);
	HistoricoFuncao findByIdProjection(Long historicoFuncaoId);
	HistoricoFuncao findUltimoHistoricoAteData(Long id, Date data);
	Collection<HistoricoFuncao> findEpis(Collection<Long> funcaoIds, Date data);
	HistoricoFuncao findByData(Date data, Long historicoFuncaoId, Long funcaoId);
	Collection<Funcao> findByFuncoes(Date data, Long[] funcoesCheck);
	Collection<HistoricoFuncao> findByFuncao(Long funcaoId);
	public HistoricoFuncao findByFuncaoAndData(Long funcaoId, Date data);
	List<DadosAmbienteOuFuncaoRisco> findDadosNoPeriodo(Long funcaoId, Date dataIni, Date dataFim);
}