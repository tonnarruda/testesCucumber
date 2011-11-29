package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.NaturezaLesao;

public interface NaturezaLesaoDao extends GenericDao<NaturezaLesao> 
{
	Collection<NaturezaLesao> findAllSelect(Long empresaId);
}
