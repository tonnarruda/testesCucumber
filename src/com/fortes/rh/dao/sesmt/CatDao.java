package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Cat;

public interface CatDao extends GenericDao<Cat>
{
	Collection<Cat> findByColaborador(Colaborador colaborador);
	Collection<Cat> findCatsColaboradorByDate(Colaborador colaborador, Date data);
	Collection<Cat> findAllSelect(Long empresaId, Date inicio, Date fim, Long[] estabelecimentoIds, String nomeBusca, Long[] areaIds);
	Collection<Object[]> getCatsRelatorio(Long estabelecimentoId, Date inicio, Date fim);
	Cat findUltimoCat(Long empresaId);
	Map<Integer,Integer> findQtdPorDiaSemana(Long empresaId, Date dataIni, Date dataFim);
	Map<String,Integer> findQtdPorHorario(Long empresaId, Date dataIni, Date dataFim);
	Cat findByIdProjectionSimples(Long catId);
	Cat findByIdProjectionDetalhada(Long catId);
}