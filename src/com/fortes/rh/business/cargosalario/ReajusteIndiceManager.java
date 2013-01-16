package com.fortes.rh.business.cargosalario;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.ReajusteIndice;
import com.fortes.rh.model.geral.Empresa;

public interface ReajusteIndiceManager extends GenericManager<ReajusteIndice>
{
	void insertReajustes(Long tabelaReajusteColaboradorId, Long[] indicesIds, char dissidioPor, Double valorDissidio) throws Exception;
	Collection<Indice> findPendentes(Empresa empresa);
	Collection<ReajusteIndice> findByTabelaReajusteColaboradorId(Long tabelaReajusteColaboradorId);
	ReajusteIndice findByIdProjection(Long reajusteIndiceId);
	void updateValorProposto(Long reajusteIndiceId, Double valorAtual, char dissidioPor, Double valorDissidio) throws Exception;
}