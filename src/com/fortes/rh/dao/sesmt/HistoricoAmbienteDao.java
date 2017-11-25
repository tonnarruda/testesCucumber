package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco;

public interface HistoricoAmbienteDao extends GenericDao<HistoricoAmbiente>
{
	boolean removeByAmbiente(Long ambienteId);
	Collection<HistoricoAmbiente> findByAmbiente(Long ambienteId);
	HistoricoAmbiente findUltimoHistorico(Long id);
	List<DadosAmbienteOuFuncaoRisco> findDadosNoPeriodo(Long ambienteId, Date dataIni, Date dataFim);
	HistoricoAmbiente findUltimoHistoricoAteData(Long ambienteId, Date dataMaxima);
	Collection<HistoricoAmbiente> findRiscosAmbientes(Collection<Long> ambienteIds, Date data);
	HistoricoAmbiente findByData(Date data, Long historicoAmbienteId, Long ambienteId);
	boolean existeHistoricoAmbienteByData(Long estabelecimentoId,Long ambienteId, Date data);
	void deleteByEstabelecimentos(Long[] estabelecimentoIds) throws Exception ;
}