package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.FasePcmat;

public interface FasePcmatDao extends GenericDao<FasePcmat> 
{
	Collection<FasePcmat> findAllSelect(String descricao, Long empresaId);
}
