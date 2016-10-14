package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;

public interface ExameDao extends GenericDao<Exame>
{
	Exame findByIdProjection(Long exameId);
	Collection<Exame> findByHistoricoFuncao(Long historicoFuncaoId);
	Collection<Long> findBySolicitacaoExame(Long solicitacaoExameId);
	Collection<ExamesPrevistosRelatorio> findExamesPeriodicosPrevistos(Long empresaId, Date dataInicio, Date dataFim, Long[] exameIds, Long[] estabelecimentoIds, Long[] areaIds, Long[] colaboradorIds, boolean imprimirAfastados, boolean imprimirDesligados);
	Collection<ExamesPrevistosRelatorio> findExamesPeriodicosPrevistosNaoRealizados(Long empresaId, Date dataInicio, Date dataFim, Long[] exameIds, Long[] estabelecimentoIds, Long[] areaIds, Long[] colaboradorIds, boolean imprimirAfastados, boolean imprimirDesligados);
	Collection<ExamesRealizadosRelatorio> findExamesRealizadosColaboradores(Long empresaId, String nomeBusca, Date inicio, Date fim, String motivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds);
	Collection<ExamesRealizadosRelatorio> findExamesRealizadosCandidatos(Long empresaId, String nomeBusca, Date inicio, Date fim, String motivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds);
	Collection<ExamesRealizadosRelatorio> findExamesRealizadosRelatorioResumido(Long empresaId, Date dataInicio, Date dataFim, ClinicaAutorizada clinicaAutorizada, Long[] examesIds, String exameResultado);
	Integer getCount(Long empresaId, Exame exame);
	Collection<Exame> find(Integer page, Integer pagingSize, Long empresaId, Exame exame);
	Collection<Exame> findPriorizandoExameRelacionado(Long empresaId, Long colaboradorId);
	Collection<Exame> findByEmpresaComAsoPadrao(Long empresaId);
	Collection<Exame> findAsoPadrao();
}