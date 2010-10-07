package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;

public interface ExameDao extends GenericDao<Exame>
{
	Exame findByIdProjection(Long exameId);
	Collection<Exame> findByHistoricoFuncao(Long historicoFuncaoId);
	Collection<Long> findBySolicitacaoExame(Long solicitacaoExameId);
	Collection<ExamesPrevistosRelatorio> findExamesPeriodicosPrevistos(Long empresaId, Date data, Long[] exameIds, Long[] estabelecimentoIds, Long[] areaIds, Long[] colaboradorIds, boolean imprimirAfastados);
	Collection<ExamesRealizadosRelatorio> findExamesRealizados(Long empresaId, String nomeBusca, Date inicio, Date fim, String motivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds, String vinculo);
}