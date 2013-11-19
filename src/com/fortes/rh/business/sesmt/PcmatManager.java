package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.Pcmat;

public interface PcmatManager extends GenericManager<Pcmat>
{
	Collection<Pcmat> findByObra(Long obraId);
}
