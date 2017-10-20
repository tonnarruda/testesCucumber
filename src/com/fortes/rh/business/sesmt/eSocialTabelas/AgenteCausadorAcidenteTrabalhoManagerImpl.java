package com.fortes.rh.business.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalhoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalho;

public class AgenteCausadorAcidenteTrabalhoManagerImpl extends GenericManagerImpl<AgenteCausadorAcidenteTrabalho, AgenteCausadorAcidenteTrabalhoDao> implements AgenteCausadorAcidenteTrabalhoManager
{
	public Collection<AgenteCausadorAcidenteTrabalho> findAllSelect() {
		return getDao().findAllSelect();
	}
}
