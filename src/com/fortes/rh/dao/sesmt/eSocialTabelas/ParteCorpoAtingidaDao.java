package com.fortes.rh.dao.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.ParteCorpoAtingida;

public interface ParteCorpoAtingidaDao extends GenericDao<ParteCorpoAtingida> 
{
	Collection<ParteCorpoAtingida> findAllSelect();
}
