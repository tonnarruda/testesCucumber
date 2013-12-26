package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.SinalizacaoPcmat;

public interface SinalizacaoPcmatDao extends GenericDao<SinalizacaoPcmat> 
{
	Collection<SinalizacaoPcmat> findByPcmat(Long pcmatId);
}
