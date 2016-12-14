package com.fortes.rh.test.factory.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.desenvolvimento.Lnt;

public class LntFactory
{
	public static Lnt getEntity()
	{
		Lnt lnt = new Lnt();
		lnt.setId(null);
		lnt.setDescricao("Descrição LNT");
		return lnt;
	}

	public static Lnt getEntity(Long id)
	{
		Lnt lnt = getEntity();
		lnt.setId(id);

		return lnt;
	}
	
	public static Lnt getEntity(Long id, String descricao, Date inicio, Date fim, Date finalizada)
	{
		Lnt lnt = getEntity(id);
		lnt.setDescricao(descricao);
		lnt.setDataInicio(inicio);
		lnt.setDataFim(fim);
		lnt.setDataFinalizada(finalizada);
		return lnt;
	}

	public static Collection<Lnt> getCollection()
	{
		Collection<Lnt> lnts = new ArrayList<Lnt>();
		lnts.add(getEntity());

		return lnts;
	}
	
	public static Collection<Lnt> getCollection(Long id)
	{
		Collection<Lnt> lnts = new ArrayList<Lnt>();
		lnts.add(getEntity(id));
		
		return lnts;
	}

	public static Lnt getEntity(String descricao, Date finalizada) {
		Lnt lnt = getEntity();
		lnt.setDescricao(descricao);
		lnt.setDataFinalizada(finalizada);
		return lnt;
	}

	public static Lnt getEntity(Date dataInicio, Date dataFim)
	{
		Lnt lnt = getEntity();
		lnt.setDataInicio(dataInicio);
		lnt.setDataFim(dataFim);
		
		return lnt;
	}
}
