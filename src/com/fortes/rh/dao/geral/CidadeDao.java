package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Cidade;

public interface CidadeDao extends GenericDao<Cidade>
{
	Collection<Cidade> findAllSelect(Long estadoId);
	Collection<Cidade> findAllByUf(String uf);
	Cidade findByCodigoAC(String codigoAC, String sigla);
	Cidade findByIdProjection(Long id);
	Cidade findByNome(String nome, Long estadoId);
	Collection<Cidade> findSemCodigoAC();
}