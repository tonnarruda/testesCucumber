package com.fortes.rh.dao.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalho;

public interface AgenteCausadorAcidenteTrabalhoDao extends GenericDao<AgenteCausadorAcidenteTrabalho> 
{
	Collection<AgenteCausadorAcidenteTrabalho> findAllSelect();
}
