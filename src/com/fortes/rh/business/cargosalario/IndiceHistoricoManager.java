package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.IndiceHistorico;

public interface IndiceHistoricoManager extends GenericManager<IndiceHistorico>
{
	Collection<IndiceHistorico> findAllSelect(Long indiceId);
	IndiceHistorico findByIdProjection(Long indiceHistoricoId);
	boolean verifyData(Long indiceHistoricoId, Date data, Long indiceId);
	Double findUltimoSalarioIndice(Long indiceId);
	Collection<IndiceHistorico> findByPeriodo(Long indiceId, Date data, Date dataProximo);
	boolean remove(Date data, Long indiceId);
	boolean existsAnteriorByDataIndice(Date data, Long indiceId);
	void updateValor(Date data, Long indiceId, Double valor);
}