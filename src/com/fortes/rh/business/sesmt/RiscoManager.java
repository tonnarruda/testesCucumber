package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Risco;

public interface RiscoManager extends GenericManager<Risco>
{
	Collection<Risco> findAllSelect(Long empresaId);
	Collection<Epi> findEpisByRisco(Long riscoId);
}