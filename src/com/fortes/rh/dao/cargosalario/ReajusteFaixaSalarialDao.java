package com.fortes.rh.dao.cargosalario;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;

public interface ReajusteFaixaSalarialDao extends GenericDao<ReajusteFaixaSalarial>
{
	Collection<ReajusteFaixaSalarial> findByTabelaReajusteColaboradorId(Long tabelaReajusteColaboradorId);
	void updateValorProposto(Long reajusteFaixaSalarialId, Double valorProposto);
	ReajusteFaixaSalarial findByIdProjection(Long id);
	boolean verificaPendenciasPorFaixa(Long faixaSalarialId);
	Collection<ReajusteFaixaSalarial> findByTabelaReajusteCargoFaixa(Long tabelaReajusteId, Collection<Long> cargosIds, Collection<Long> faixaSalariaisIds);
	void removeByTabelaReajusteColaborador(Long tabelaReajusteColaboradorId);
}