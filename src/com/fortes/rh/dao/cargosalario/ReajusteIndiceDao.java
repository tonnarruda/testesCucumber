package com.fortes.rh.dao.cargosalario;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.ReajusteIndice;
import com.fortes.rh.model.geral.Empresa;

public interface ReajusteIndiceDao extends GenericDao<ReajusteIndice>
{
	Collection<Indice> findPendentes(Empresa empresa);
	Collection<ReajusteIndice> findByTabelaReajusteColaboradorId(Long tabelaReajusteColaboradorId);
	ReajusteIndice findByIdProjection(Long reajusteIndiceId);
	void updateValorProposto(Long reajusteIndiceId, Double valorProposto);
}