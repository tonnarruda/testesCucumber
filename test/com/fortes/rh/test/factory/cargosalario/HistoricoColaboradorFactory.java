package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;

public class HistoricoColaboradorFactory
{
	public static HistoricoColaborador getEntity()
	{
		HistoricoColaborador historicoColaborador = new HistoricoColaborador();

		historicoColaborador.setId(null);
		historicoColaborador.setMotivo("C");
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador.setSalario(1D);
		historicoColaborador.setColaborador(null);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);

		return historicoColaborador;
	}

	public static HistoricoColaborador getEntity(Long id)
	{
		HistoricoColaborador historicoColaborador = getEntity();
		historicoColaborador.setId(id);
		
		return historicoColaborador;
	}

	public static Collection<HistoricoColaborador> getCollection()
	{
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(getEntity());
		return historicoColaboradors;
	}

}
