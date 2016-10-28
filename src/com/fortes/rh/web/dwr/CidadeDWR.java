package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.util.CollectionUtil;

@Component
public class CidadeDWR
{
	@Autowired
	private CidadeManager cidadeManager;

	public Map getCidades(String ufId)
	{
		if(ufId != null && !ufId.equals("-1"))
		{
			Collection<Cidade> cidades = cidadeManager.findAllSelect(Long.valueOf(ufId));
			Collection<Cidade> cidadesLista = new ArrayList<Cidade>();

			if(!cidades.isEmpty())
			{
				Cidade cidadeVazio = new Cidade();
				cidadeVazio.setNome("Selecione...");
				cidadeVazio.setId(-1L);

				cidadesLista.add(cidadeVazio);

				cidadesLista.addAll(cidades);
			}

			return CollectionUtil.convertCollectionToMap(cidadesLista, "getId", "getNome", Cidade.class);
		}

		return new HashMap();

	}
	
	public Map getCidadesCheckList(String ufId, Long[] cidadesCheck)
	{
		if(ufId != null && !ufId.equals("-1"))
		{
			Collection<Cidade> cidades = cidadeManager.findAllSelect(Long.valueOf(ufId));
			Collection<Cidade> cidadesLista = new ArrayList<Cidade>();

			if(!cidades.isEmpty())
				cidadesLista.addAll(cidades);

			return CollectionUtil.convertCollectionToMap(cidadesLista, "getId", "getNome", Cidade.class);
		}

		return new HashMap();
	}

	public void setCidadeManager(CidadeManager cidadeManager)
	{
		this.cidadeManager = cidadeManager;
	}

}
