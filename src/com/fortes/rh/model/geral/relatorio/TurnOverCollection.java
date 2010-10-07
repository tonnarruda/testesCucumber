package com.fortes.rh.model.geral.relatorio;

import java.util.ArrayList;
import java.util.Collection;

public class TurnOverCollection
{
	private Collection<TurnOver> turnOvers = new ArrayList<TurnOver>();

	public Double getMedia()
	{
		Double soma = 0D;
		for (TurnOver turnOverTmp : turnOvers)
		{
			soma += turnOverTmp.getTurnOver();
		}

		return soma / turnOvers.size();
	}

	public Collection<TurnOver> getTurnOvers()
	{
		return turnOvers;
	}

	public void setTurnOvers(Collection<TurnOver> turnOvers)
	{
		this.turnOvers = turnOvers;
	}


}
