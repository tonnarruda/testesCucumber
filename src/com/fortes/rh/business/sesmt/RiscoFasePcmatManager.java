package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;

public interface RiscoFasePcmatManager extends GenericManager<RiscoFasePcmat>
{
	Collection<RiscoFasePcmat> findByFasePcmat(Long fasePcmatId);
	void removeByFasePcmatRisco(Long fasePcmatId, Collection<Long> riscosIds);
}
