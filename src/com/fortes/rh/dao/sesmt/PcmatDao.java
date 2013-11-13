package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Pcmat;

public interface PcmatDao extends GenericDao<Pcmat> 
{
	Collection<Pcmat> findAllSelect(String nomeObra, Long empresaId);
}
