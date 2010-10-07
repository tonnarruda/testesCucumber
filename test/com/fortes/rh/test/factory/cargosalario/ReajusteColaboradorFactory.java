package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.ReajusteColaborador;

public class ReajusteColaboradorFactory
{
	public static ReajusteColaborador getReajusteColaborador()
	{
		ReajusteColaborador reajusteColaborador = new ReajusteColaborador();

		reajusteColaborador.setId(null);
		reajusteColaborador.setFaixaSalarialAtual(null);
		reajusteColaborador.setFaixaSalarialProposta(null);
		reajusteColaborador.setSalarioAtual(22D);
		reajusteColaborador.setSalarioProposto(33D);
		reajusteColaborador.setTabelaReajusteColaborador(null);

		return reajusteColaborador;
	}

	public static ReajusteColaborador getReajusteColaborador(Long id)
	{
		ReajusteColaborador reajusteColaborador = getReajusteColaborador();
		reajusteColaborador.setId(id);

		return reajusteColaborador;
	}

	public static Collection<ReajusteColaborador> getCollection()
	{
		Collection<ReajusteColaborador> collection = new ArrayList<ReajusteColaborador>();
		collection.add(getReajusteColaborador());
		return collection;
	}
}
