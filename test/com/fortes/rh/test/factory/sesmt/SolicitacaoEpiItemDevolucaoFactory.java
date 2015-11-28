package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;

public class SolicitacaoEpiItemDevolucaoFactory {
	
	public static SolicitacaoEpiItemDevolucao getEntity()
	{
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao = new SolicitacaoEpiItemDevolucao();
		solicitacaoEpiItemDevolucao.setId(null);
		return solicitacaoEpiItemDevolucao;
	}

	public static SolicitacaoEpiItemDevolucao getEntity(Long id)
	{
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao = getEntity();
		solicitacaoEpiItemDevolucao.setId(id);

		return solicitacaoEpiItemDevolucao;
	}

	public static Collection<SolicitacaoEpiItemDevolucao> getCollection()
	{
		Collection<SolicitacaoEpiItemDevolucao> solicitacaoEpiItemDevolucaos = new ArrayList<SolicitacaoEpiItemDevolucao>();
		solicitacaoEpiItemDevolucaos.add(getEntity());

		return solicitacaoEpiItemDevolucaos;
	}
	
	public static Collection<SolicitacaoEpiItemDevolucao> getCollection(Long id)
	{
		Collection<SolicitacaoEpiItemDevolucao> solicitacaoEpiItemDevolucaos = new ArrayList<SolicitacaoEpiItemDevolucao>();
		solicitacaoEpiItemDevolucaos.add(getEntity(id));
		
		return solicitacaoEpiItemDevolucaos;
	}
	
	public static SolicitacaoEpiItemDevolucao getEntity(Date dataDevolucao, int qtdDevolvida, SolicitacaoEpiItem solicitacaoEpiItem)
	{
		SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao = getEntity();
		solicitacaoEpiItemDevolucao.setDataDevolucao(dataDevolucao);
		solicitacaoEpiItemDevolucao.setQtdDevolvida(qtdDevolvida);
		solicitacaoEpiItemDevolucao.setSolicitacaoEpiItem(solicitacaoEpiItem);
		return solicitacaoEpiItemDevolucao;
	}
}
