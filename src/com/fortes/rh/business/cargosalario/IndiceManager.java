package com.fortes.rh.business.cargosalario;

import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;

public interface IndiceManager extends GenericManager<Indice>
{
	void save(Indice indice, IndiceHistorico indiceHistorico)throws Exception;

	Indice findByIdProjection(Long indiceId);

	Indice findByCodigo(String codigo, String grupoAC);

	boolean remove(String codigo, String grupoAC);

	Indice findHistoricoAtual(Long indiceId);

	Indice getCodigoAc(Indice indice);

	void removeIndice(Long indiceId) throws Exception;

	Indice findIndiceByCodigoAc(String indiceCodigoAC);

	Indice findHistorico(Long indiceId, Date dataHistorico);
}