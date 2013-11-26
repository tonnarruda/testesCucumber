package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;

public interface RiscoFasePcmatDao extends GenericDao<RiscoFasePcmat> 
{
	Collection<RiscoFasePcmat> findByFasePcmat(Long fasePcmatId);
}
