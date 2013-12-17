package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.EpcPcmat;

public interface EpcPcmatManager extends GenericManager<EpcPcmat>
{
	Collection<EpcPcmat> findByPcmat(Long pcmatId);
	void clonar(Long pcmatOrigemId, Long pcmatDestinoId);
}
