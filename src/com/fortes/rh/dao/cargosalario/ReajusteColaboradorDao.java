package com.fortes.rh.dao.cargosalario;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;

public interface ReajusteColaboradorDao extends GenericDao<ReajusteColaborador>
{
	Collection<ReajusteColaborador> findByIdEstabelecimentoAreaGrupo(Long tabelaReajusteColaboradorId, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, int filtraPor);
	void deleteByColaboradoresTabelaReajuste(Long[] colaboradorIds, Long tabelaReajusteColaboradorId);
	ReajusteColaborador findByIdProjection(Long reajusteColaboradorId);
	ReajusteColaborador getSituacaoReajusteColaborador(Long reajusteColaboradorId);
	void updateFromHistoricoColaborador(HistoricoColaborador historicoColaborador);
}