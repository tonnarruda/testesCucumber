package com.fortes.rh.dao.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.geral.Empresa;

public interface IndiceDao extends GenericDao<Indice>
{
	Indice findByIdProjection(Long indiceId);
	Indice findByCodigo(String codigo, String grupoAC);
	boolean remove(String codigo, String grupoAC);
	Indice findHistoricoAtual(Long indiceId, Date dataHistorico);
	Indice findIndiceByCodigoAc(String indiceCodigoAC, String grupoAC);
	Collection<Indice> findSemCodigoAC(Empresa empresa);
	Collection<Indice> findComHistoricoAtual(Long[] indicesIds);
	Collection<Indice> findComHistoricoAtual(Empresa empresa);
}