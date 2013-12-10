package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;

public interface AreaVivenciaPcmatDao extends GenericDao<AreaVivenciaPcmat> 
{
	Collection<AreaVivenciaPcmat> findByPcmat(Long pcmatId);
}
