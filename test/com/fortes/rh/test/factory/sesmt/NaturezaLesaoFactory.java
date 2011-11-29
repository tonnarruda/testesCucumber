package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.NaturezaLesao;

public class NaturezaLesaoFactory
{
	public static NaturezaLesao getEntity()
	{
		NaturezaLesao naturezaLesao = new NaturezaLesao();
		naturezaLesao.setId(null);
		return naturezaLesao;
	}

	public static NaturezaLesao getEntity(Long id)
	{
		NaturezaLesao naturezaLesao = getEntity();
		naturezaLesao.setId(id);

		return naturezaLesao;
	}

	public static Collection<NaturezaLesao> getCollection()
	{
		Collection<NaturezaLesao> naturezaLesaos = new ArrayList<NaturezaLesao>();
		naturezaLesaos.add(getEntity());

		return naturezaLesaos;
	}
	
	public static Collection<NaturezaLesao> getCollection(Long id)
	{
		Collection<NaturezaLesao> naturezaLesaos = new ArrayList<NaturezaLesao>();
		naturezaLesaos.add(getEntity(id));
		
		return naturezaLesaos;
	}
}
