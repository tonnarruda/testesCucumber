package com.fortes.rh.test.factory.desenvolvimento;

import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;

public class PrioridadeTreinamentoFactory
{
	public static PrioridadeTreinamento getEntity()
	{
		PrioridadeTreinamento prioridadeTreinamento = new PrioridadeTreinamento();
		prioridadeTreinamento.setId(null);
		return prioridadeTreinamento;
	}

	public static PrioridadeTreinamento getEntity(long id)
	{
		PrioridadeTreinamento prioridadeTreinamento = getEntity();
		prioridadeTreinamento.setId(id);
		return prioridadeTreinamento;
	}
}
