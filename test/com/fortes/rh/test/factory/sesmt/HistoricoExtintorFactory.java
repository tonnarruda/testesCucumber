package com.fortes.rh.test.factory.sesmt;

import java.util.Date;

import com.fortes.rh.model.sesmt.HistoricoExtintor;

public class HistoricoExtintorFactory
{
	public static HistoricoExtintor getEntity()
	{
		HistoricoExtintor historicoExtintor = new HistoricoExtintor();
		historicoExtintor.setData(new Date());

		return historicoExtintor;
	}

	public static HistoricoExtintor getEntity(Long id)
	{
		HistoricoExtintor historicoExtintor = getEntity();
		historicoExtintor.setId(id);

		return historicoExtintor;
	}
}