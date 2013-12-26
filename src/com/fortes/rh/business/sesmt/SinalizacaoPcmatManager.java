package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.SinalizacaoPcmat;

public interface SinalizacaoPcmatManager extends GenericManager<SinalizacaoPcmat>
{
	Collection<SinalizacaoPcmat> findByPcmat(Long pcmatId);
}
