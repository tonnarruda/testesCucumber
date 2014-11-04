package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.MotivoSolicitacaoEpi;

public class MotivoSolicitacaoEpiFactory
{
	public static MotivoSolicitacaoEpi getEntity()
	{
		MotivoSolicitacaoEpi motivoSolicitacaoEpi = new MotivoSolicitacaoEpi();
		motivoSolicitacaoEpi.setId(null);
		return motivoSolicitacaoEpi;
	}

	public static MotivoSolicitacaoEpi getEntity(Long id)
	{
		MotivoSolicitacaoEpi motivoSolicitacaoEpi = getEntity();
		motivoSolicitacaoEpi.setId(id);

		return motivoSolicitacaoEpi;
	}

	public static Collection<MotivoSolicitacaoEpi> getCollection()
	{
		Collection<MotivoSolicitacaoEpi> motivoSolicitacaoEpis = new ArrayList<MotivoSolicitacaoEpi>();
		motivoSolicitacaoEpis.add(getEntity());

		return motivoSolicitacaoEpis;
	}
	
	public static Collection<MotivoSolicitacaoEpi> getCollection(Long id)
	{
		Collection<MotivoSolicitacaoEpi> motivoSolicitacaoEpis = new ArrayList<MotivoSolicitacaoEpi>();
		motivoSolicitacaoEpis.add(getEntity(id));
		
		return motivoSolicitacaoEpis;
	}
}
