package com.fortes.rh.test.factory.sesmt.eSocialTabelas;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.eSocialTabelas.DescricaoNaturezaLesao;

public class DescricaoNaturezaLesaoFactory
{
	public static DescricaoNaturezaLesao getEntity()
	{
		DescricaoNaturezaLesao descricaoNaturezaLesao = new DescricaoNaturezaLesao();
		descricaoNaturezaLesao.setId(null);
		return descricaoNaturezaLesao;
	}

	public static DescricaoNaturezaLesao getEntity(Long id)
	{
		DescricaoNaturezaLesao descricaoNaturezaLesao = getEntity();
		descricaoNaturezaLesao.setId(id);

		return descricaoNaturezaLesao;
	}

	public static Collection<DescricaoNaturezaLesao> getCollection()
	{
		Collection<DescricaoNaturezaLesao> descricaoNaturezaLesaos = new ArrayList<DescricaoNaturezaLesao>();
		descricaoNaturezaLesaos.add(getEntity());

		return descricaoNaturezaLesaos;
	}
	
	public static Collection<DescricaoNaturezaLesao> getCollection(Long id)
	{
		Collection<DescricaoNaturezaLesao> descricaoNaturezaLesaos = new ArrayList<DescricaoNaturezaLesao>();
		descricaoNaturezaLesaos.add(getEntity(id));
		
		return descricaoNaturezaLesaos;
	}
}
