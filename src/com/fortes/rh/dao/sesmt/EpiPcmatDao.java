package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.EpiPcmat;

public interface EpiPcmatDao extends GenericDao<EpiPcmat> 
{

	Collection<EpiPcmat> findByPcmat(Long pcmatId);

}
