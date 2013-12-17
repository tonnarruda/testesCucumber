package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.FasePcmat;

public interface FasePcmatManager extends GenericManager<FasePcmat>
{
	Collection<FasePcmat> findByPcmat(Long pcmatId);
	FasePcmat findByIdProjection(Long id);
	void clonar(Long pcamtOrigemId, Long pcmatDestinoId);
}
