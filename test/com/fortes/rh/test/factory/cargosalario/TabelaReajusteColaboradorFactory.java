package com.fortes.rh.test.factory.cargosalario;

import java.util.Date;

import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoReajuste;

public class TabelaReajusteColaboradorFactory
{
	public static TabelaReajusteColaborador getEntity()
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = new TabelaReajusteColaborador();

		tabelaReajusteColaborador.setId(null);
		tabelaReajusteColaborador.setData(new Date());
		tabelaReajusteColaborador.setNome("tabelareajuste");
		tabelaReajusteColaborador.setReajusteColaboradors(null);
		tabelaReajusteColaborador.setEmpresa(null);
		tabelaReajusteColaborador.setDissidio(false);
		tabelaReajusteColaborador.setTipoReajuste(TipoReajuste.COLABORADOR);

		return tabelaReajusteColaborador;
	}

	public static TabelaReajusteColaborador getEntity(Long id)
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = getEntity();
		tabelaReajusteColaborador.setId(id);

		return tabelaReajusteColaborador;
	}
}
