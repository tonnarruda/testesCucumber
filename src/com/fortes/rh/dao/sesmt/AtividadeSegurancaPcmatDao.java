package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.AtividadeSegurancaPcmat;

public interface AtividadeSegurancaPcmatDao extends GenericDao<AtividadeSegurancaPcmat> 
{
	Collection<AtividadeSegurancaPcmat> findByPcmat(Long pcmatId);
}
