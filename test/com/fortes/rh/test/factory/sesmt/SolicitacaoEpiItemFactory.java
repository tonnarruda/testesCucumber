package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;

public class SolicitacaoEpiItemFactory
{
	public static SolicitacaoEpiItem getEntity()
	{
		SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();
		solicitacaoEpiItem.setQtdSolicitado(2);

		return solicitacaoEpiItem;
	}

	public static SolicitacaoEpiItem getEntity(Long id)
	{
		SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();
		solicitacaoEpiItem.setId(id);
		solicitacaoEpiItem.setQtdSolicitado(2);

		return solicitacaoEpiItem;
	}

	public static SolicitacaoEpiItem getEntity(SolicitacaoEpi solicitacaoEpi, Epi epi, Integer quantidadeSolicitada) {
		SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();
		solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItem.setQtdSolicitado(quantidadeSolicitada);
		solicitacaoEpiItem.setEpi(epi);
		return solicitacaoEpiItem;
	}
}
