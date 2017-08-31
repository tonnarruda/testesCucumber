package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco;

public interface HistoricoFuncaoManager extends GenericManager<HistoricoFuncao>
{
	void saveFuncaoHistorico(Funcao funcao, HistoricoFuncao historicoFuncao, Long[] examesChecked, Long[] episChecked, Long[] cursosChecked, String[] riscoChecks, Collection<RiscoFuncao> riscoFuncoes) throws Exception;
	Collection<HistoricoFuncao> findHistoricoFuncaoColaborador(Collection<HistoricoColaborador> historicosColaborador, Date data, Date dataDesligamento);
	Collection<HistoricoFuncao> inserirPeriodos(Collection<HistoricoFuncao> historicoFuncaos);
	Collection<HistoricoFuncao> getUltimoHistoricosByDateFuncaos(Collection<Long> funcaoIds, Date data);
	void saveHistorico(HistoricoFuncao historicoFuncao, Long[] examesChecked, Long[] episChecked, Long[] riscoChecks, Long[] cursosChecked, Collection<RiscoFuncao> riscoFuncoes) throws FortesException, Exception;
	void removeByFuncoes(Long[] funcaoIds);
	HistoricoFuncao findByIdProjection(Long historicoFuncaoId);
	HistoricoFuncao findUltimoHistoricoAteData(Long id, Date data);
	Collection<HistoricoFuncao> findEpis(Collection<Long> funcaoIds, Date data);
	HistoricoFuncao findByData(Date data, Long historicoFuncaoId, Long funcaoId);
	Collection<Funcao> findByFuncoes(Date data, Long[] funcoesCheck);
	Collection<HistoricoFuncao> findByFuncao(Long funcaoId);
	public HistoricoFuncao findByFuncaoAndData(Long funcaoId, Date data);
	List<DadosAmbienteOuFuncaoRisco> findDadosNoPeriodo(Long funcaoId, Date dataHistColaboradorIni, Date dataFim);
}