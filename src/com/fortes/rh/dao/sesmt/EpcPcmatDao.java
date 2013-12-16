package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.EpcPcmat;

public interface EpcPcmatDao extends GenericDao<EpcPcmat> 
{
	Collection<EpcPcmat> findByPcmat(Long pcmatId);

}
