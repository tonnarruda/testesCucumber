package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.geral.Empresa;

public interface IndiceManager extends GenericManager<Indice>
{
	void save(Indice indice, IndiceHistorico indiceHistorico)throws Exception;

	Indice findByIdProjection(Long indiceId);

	Indice findByCodigo(String codigo, String grupoAC);

	boolean remove(String codigo, String grupoAC);

	Indice findHistoricoAtual(Long indiceId);

	Indice getCodigoAc(Indice indice);

	void removeIndice(Long indiceId) throws Exception;

	Indice findIndiceByCodigoAc(String indiceCodigoAC, String grupoAC);

	Indice findHistorico(Long indiceId, Date dataHistorico);

	Collection<Indice> findAll(Empresa empresa);
}