package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;

public class SolicitacaoEpiItemEntregaFactory
{
	public static SolicitacaoEpiItemEntrega getEntity()
	{
		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega = new SolicitacaoEpiItemEntrega();
		solicitacaoEpiItemEntrega.setId(null);
		solicitacaoEpiItemEntrega.setDataEntrega(new Date());
		solicitacaoEpiItemEntrega.setQtdEntregue(2);
		return solicitacaoEpiItemEntrega;
	}

	public static SolicitacaoEpiItemEntrega getEntity(Long id)
	{
		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega = getEntity();
		solicitacaoEpiItemEntrega.setId(id);

		return solicitacaoEpiItemEntrega;
	}

	public static Collection<SolicitacaoEpiItemEntrega> getCollection()
	{
		Collection<SolicitacaoEpiItemEntrega> solicitacaoEpiItemEntregas = new ArrayList<SolicitacaoEpiItemEntrega>();
		solicitacaoEpiItemEntregas.add(getEntity());

		return solicitacaoEpiItemEntregas;
	}
	
	public static Collection<SolicitacaoEpiItemEntrega> getCollection(Long id)
	{
		Collection<SolicitacaoEpiItemEntrega> solicitacaoEpiItemEntregas = new ArrayList<SolicitacaoEpiItemEntrega>();
		solicitacaoEpiItemEntregas.add(getEntity(id));
		
		return solicitacaoEpiItemEntregas;
	}

	public static SolicitacaoEpiItemEntrega getEntity(Date dataEntrega,	SolicitacaoEpiItem solicitacaoEpiItem, Integer quantidadeEntregue) {
		SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega = new SolicitacaoEpiItemEntrega();
		solicitacaoEpiItemEntrega.setDataEntrega(dataEntrega);
		solicitacaoEpiItemEntrega.setQtdEntregue(quantidadeEntregue);
		solicitacaoEpiItemEntrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
		return solicitacaoEpiItemEntrega;
	}
}
