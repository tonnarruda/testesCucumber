package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estado;

public interface CidadeManager extends GenericManager<Cidade>
{
	Collection<Cidade> findAllSelect(Long estadoId);
	Collection<Cidade> findAllByUf(String uf);
	Cidade findByCodigoAC(String codigoAC, String sigla);
	Cidade findByIdProjection(Long id);
	Collection<Cidade> findByEstado(Estado estado);
	Cidade findByNome(String nome, Long estadoId);
	Collection<Cidade> findSemCodigoAC();
	String findCodigoACDuplicado();
}