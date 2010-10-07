package com.fortes.rh.test.factory.geral;

import java.util.Date;

import com.fortes.rh.model.geral.HistoricoBeneficio;

public class HistoricoBeneficioFactory
{
	public static HistoricoBeneficio getEntity()
	{
		HistoricoBeneficio historicoBeneficio = new HistoricoBeneficio();

		historicoBeneficio.setId(null);
		historicoBeneficio.setParaColaborador(50D);
		historicoBeneficio.setParaDependenteDireto(50D);
		historicoBeneficio.setParaDependenteIndireto(50D);
		historicoBeneficio.setValor(100D);
		historicoBeneficio.setData(new Date());

		return historicoBeneficio;
	}
}
