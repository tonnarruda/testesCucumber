package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.util.CollectionUtil;

public class ExtintorDWR
{
	private ExtintorManager extintorManager;

	@SuppressWarnings("unchecked")
	public Map getExtintorByEstabelecimento(String estabelecimentoId, String mensagemHeader)
	{
		// Descrição pode ser "Todos" (filtro listagem), "Selecione..." (edit)
		Extintor extintorTmp = new Extintor(mensagemHeader);
		extintorTmp.setId(-1L);

		Collection<Extintor> colecaoRetorno = new ArrayList<Extintor>();
		colecaoRetorno.add(extintorTmp);

		if (estabelecimentoId != null && !estabelecimentoId.equals(""))
		{
			Collection<Extintor> extintores = extintorManager.findByEstabelecimento(Long.parseLong(estabelecimentoId), true);

			if (!extintores.isEmpty())
			{
				colecaoRetorno.addAll(extintores);
			}
		}else{
			Collection<Extintor> extintores = extintorManager.findByEstabelecimento(null, true);
			
			if (!extintores.isEmpty())
			{
				colecaoRetorno.addAll(extintores);
			}
		}

		return CollectionUtil.convertCollectionToMap(colecaoRetorno, "getId", "getDescricao", Extintor.class);
	}

	public void setExtintorManager(ExtintorManager extintorManager)
	{
		this.extintorManager = extintorManager;
	}
}
