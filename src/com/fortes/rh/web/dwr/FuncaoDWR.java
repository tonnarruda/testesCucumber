package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.util.CollectionUtil;

@SuppressWarnings("unchecked")
public class FuncaoDWR
{
	private FuncaoManager funcaoManager;

	public Map getFuncaoByFaixaSalarial(Long faixaId)
	{
		Collection<Funcao> funcaos = new ArrayList<Funcao>();

		if(faixaId != 0)
		{
			funcaos = funcaoManager.findFuncaoByFaixa(faixaId);
		}

		Collection<Funcao> funcaoLista = new ArrayList<Funcao>();

		Funcao funcaoVazio = new Funcao();
		funcaoVazio.setId(-1L);
		funcaoVazio.setNome("Nenhuma");

		funcaoLista.add(funcaoVazio);
		funcaoLista.addAll(funcaos);

		return  new CollectionUtil<Funcao>().convertCollectionToMap(funcaoLista,"getId","getNome");
	}

	public void setFuncaoManager(FuncaoManager funcaoManager)
	{
		this.funcaoManager = funcaoManager;
	}
}