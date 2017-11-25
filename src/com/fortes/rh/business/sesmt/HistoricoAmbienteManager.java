package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco;

public interface HistoricoAmbienteManager extends GenericManager<HistoricoAmbiente>
{
	void saveOrUpdate(HistoricoAmbiente historicoAmbiente, String[] riscoChecks, Collection<RiscoAmbiente> riscosAmbientes, String[] epcCheck) throws FortesException, Exception;
	boolean removeByAmbiente(Long ambienteId);
	Collection<HistoricoAmbiente> findByAmbiente(Long ambienteId);
	void removeCascade(Long id);
	HistoricoAmbiente findUltimoHistorico(Long ambienteId);
	List<DadosAmbienteOuFuncaoRisco> findDadosNoPeriodo(Long id, Date dataIni, Date dataFim);
	HistoricoAmbiente findUltimoHistoricoAteData(Long ambienteId, Date dataMaxima);
	Collection<HistoricoAmbiente> findRiscosAmbientes(Collection<Long> ambienteIds, Date data);
	HistoricoAmbiente findByData(Date data, Long historicoAmbienteId, Long ambienteId);
	boolean existeHistoricoAmbienteByData(Long estabelecimentoId, Long ambienteId, Date data);
	void deleteByEstabelecimentos(Long[] estabelecimentoIds) throws Exception;
}