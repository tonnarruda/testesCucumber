package com.fortes.rh.dao.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.IndiceHistorico;

public interface IndiceHistoricoDao extends GenericDao<IndiceHistorico>
{
	Collection<IndiceHistorico> findAllSelect(Long indiceId);

	IndiceHistorico findByIdProjection(Long indiceHistoricoId);

	boolean verifyData(Long indiceHistoricoId, Date data, Long indiceId);

	Double findUltimoSalarioIndice(Long indiceId);

	Collection<IndiceHistorico> findByPeriodo(Long indiceId, Date data, Date dataProximo);

	boolean remove(Date data, Long indiceId);

	boolean existsAnteriorByDataIndice(Date data, Long indiceId);

	void updateValor(Date data, Long indiceId, Double valor);

	void deleteByIndice(Long[] indiceIds) throws Exception;

	Collection<IndiceHistorico> findByTabelaReajusteId(Long tabelaReajusteColaboradorId);

	Collection<IndiceHistorico> findByTabelaReajusteIdData(Long tabelaReajusteId, Date data);
}