package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Atitude;

public interface AtitudeDao extends GenericDao<Atitude> 
{
	public Collection<Atitude> findAllSelect(Long empresaId);
	public Collection<Atitude> findByCargo(Long cargoId);
}
