package com.fortes.rh.dao.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissional;

public interface SituacaoGeradoraDoencaProfissionalDao extends GenericDao<SituacaoGeradoraDoencaProfissional> 
{
	Collection<SituacaoGeradoraDoencaProfissional> findAllSelect();
}
