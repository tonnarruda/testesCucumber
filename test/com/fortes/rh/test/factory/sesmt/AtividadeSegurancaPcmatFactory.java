package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.AtividadeSegurancaPcmat;

public class AtividadeSegurancaPcmatFactory
{
	public static AtividadeSegurancaPcmat getEntity()
	{
		AtividadeSegurancaPcmat atividadeSegurancaPcmat = new AtividadeSegurancaPcmat();
		atividadeSegurancaPcmat.setId(null);
		return atividadeSegurancaPcmat;
	}

	public static AtividadeSegurancaPcmat getEntity(Long id)
	{
		AtividadeSegurancaPcmat atividadeSegurancaPcmat = getEntity();
		atividadeSegurancaPcmat.setId(id);

		return atividadeSegurancaPcmat;
	}

	public static Collection<AtividadeSegurancaPcmat> getCollection()
	{
		Collection<AtividadeSegurancaPcmat> atividadeSegurancaPcmats = new ArrayList<AtividadeSegurancaPcmat>();
		atividadeSegurancaPcmats.add(getEntity());

		return atividadeSegurancaPcmats;
	}
	
	public static Collection<AtividadeSegurancaPcmat> getCollection(Long id)
	{
		Collection<AtividadeSegurancaPcmat> atividadeSegurancaPcmats = new ArrayList<AtividadeSegurancaPcmat>();
		atividadeSegurancaPcmats.add(getEntity(id));
		
		return atividadeSegurancaPcmats;
	}
}
