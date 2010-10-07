package com.fortes.rh.dao.cargosalario;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.Indice;

public interface IndiceDao extends GenericDao<Indice>
{
	Indice findByIdProjection(Long indiceId);

	Indice findByCodigo(String codigo);

	boolean remove(String codigo);

	Indice findHistoricoAtual(Long indiceId, Date dataHistorico);

	Indice findIndiceByCodigoAc(String indiceCodigoAC);
}