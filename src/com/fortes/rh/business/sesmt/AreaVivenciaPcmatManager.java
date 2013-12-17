package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;

public interface AreaVivenciaPcmatManager extends GenericManager<AreaVivenciaPcmat>
{
	Collection<AreaVivenciaPcmat> findByPcmat(Long pcmatId);
	void clonar(Long pcmatOrigemId, Long pcmatDestinoId);
}
