package com.fortes.rh.dao.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.DescricaoNaturezaLesao;

public interface DescricaoNaturezaLesaoDao extends GenericDao<DescricaoNaturezaLesao> 
{
	Collection<DescricaoNaturezaLesao> findAllSelect();
}
