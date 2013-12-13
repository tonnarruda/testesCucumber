package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.EpiPcmat;

public interface EpiPcmatManager extends GenericManager<EpiPcmat>
{
	Collection<EpiPcmat> findByPcmat(Long pcmatId);
}
