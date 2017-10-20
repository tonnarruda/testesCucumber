package com.fortes.rh.business.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalho;

public interface AgenteCausadorAcidenteTrabalhoManager extends GenericManager<AgenteCausadorAcidenteTrabalho>
{
	Collection<AgenteCausadorAcidenteTrabalho> findAllSelect();
}
