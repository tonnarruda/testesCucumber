package com.fortes.rh.test.factory.sesmt.eSocialTabelas;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalho;

public class AgenteCausadorAcidenteTrabalhoFactory
{
	public static AgenteCausadorAcidenteTrabalho getEntity()
	{
		AgenteCausadorAcidenteTrabalho agenteCausadorAcidenteTrabalho = new AgenteCausadorAcidenteTrabalho();
		agenteCausadorAcidenteTrabalho.setId(null);
		return agenteCausadorAcidenteTrabalho;
	}

	public static AgenteCausadorAcidenteTrabalho getEntity(Long id)
	{
		AgenteCausadorAcidenteTrabalho agenteCausadorAcidenteTrabalho = getEntity();
		agenteCausadorAcidenteTrabalho.setId(id);

		return agenteCausadorAcidenteTrabalho;
	}

	public static Collection<AgenteCausadorAcidenteTrabalho> getCollection()
	{
		Collection<AgenteCausadorAcidenteTrabalho> agenteCausadorAcidenteTrabalhos = new ArrayList<AgenteCausadorAcidenteTrabalho>();
		agenteCausadorAcidenteTrabalhos.add(getEntity());

		return agenteCausadorAcidenteTrabalhos;
	}
	
	public static Collection<AgenteCausadorAcidenteTrabalho> getCollection(Long id)
	{
		Collection<AgenteCausadorAcidenteTrabalho> agenteCausadorAcidenteTrabalhos = new ArrayList<AgenteCausadorAcidenteTrabalho>();
		agenteCausadorAcidenteTrabalhos.add(getEntity(id));
		
		return agenteCausadorAcidenteTrabalhos;
	}
}
