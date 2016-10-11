package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.RealizacaoExame;

public interface RealizacaoExameDao extends GenericDao<RealizacaoExame>
{
	Collection<Object[]> getRelatorioExame(Long estabelecimentoId, Date dataIni, Date dataFim);
	Collection<RealizacaoExame> findRealizadosByColaborador(Long empresaId, Long colaboradorId);
	Collection<Long> findIdsBySolicitacaoExame(long solicitacaoExameId);
	void remove(Long[] realizacaoExameIds);
	void marcarResultadoComoNormal(Collection<Long> realizacaoExameIds);
	Integer findQtdRealizados(Long empresaId, Date dataIni, Date dataFim);
	Collection<Exame> findQtdPorExame(Long empresaId, Date dataIni, Date dataFim);
}