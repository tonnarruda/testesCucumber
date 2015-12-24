package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.IndiceHistorico;

public interface IndiceHistoricoManager extends GenericManager<IndiceHistorico>
{
	Collection<IndiceHistorico> findAllSelect(Long indiceId);
	IndiceHistorico findByIdProjection(Long indiceHistoricoId);
	boolean verifyData(Long indiceHistoricoId, Date data, Long indiceId);
	Double findUltimoSalarioIndice(Long indiceId);
	Collection<IndiceHistorico> findByPeriodo(Long indiceId, Date data, Date dataProximo, Date dataDesligamento);
	boolean remove(Date data, Long indiceId) throws FortesException;
	boolean existeHistoricoAnteriorOuIgualDaData(Date data, Long indiceId);
	boolean existeHistoricoAnteriorDaData(Date data, Long indiceId);
	void updateValor(Date data, Long indiceId, Double valor);
	void deleteByIndice(Long[] indiceIds) throws Exception;
	Collection<IndiceHistorico> findByTabelaReajusteId(Long tabelaReajusteColaboradorId);
	Collection<IndiceHistorico> findByTabelaReajusteIdData(Long tabelaReajusteColaboradorId, Date data);
}