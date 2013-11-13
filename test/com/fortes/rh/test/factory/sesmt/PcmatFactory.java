package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.util.DateUtil;

public class PcmatFactory
{
	public static Pcmat getEntity()
	{
		Pcmat pcmat = new Pcmat();
		pcmat.setId(null);
		pcmat.setTipoObra("Edificação");
		pcmat.setApartirDe(DateUtil.criarDataMesAno(1, 11, 2013));
		pcmat.setDataIniObra(DateUtil.criarDataMesAno(1, 11, 2013));
		pcmat.setDataFimObra(DateUtil.criarDataMesAno(1, 11, 2014));
		
		return pcmat;
	}

	public static Pcmat getEntity(Long id)
	{
		Pcmat pcmat = getEntity();
		pcmat.setId(id);

		return pcmat;
	}

	public static Collection<Pcmat> getCollection()
	{
		Collection<Pcmat> pcmats = new ArrayList<Pcmat>();
		pcmats.add(getEntity());

		return pcmats;
	}
	
	public static Collection<Pcmat> getCollection(Long id)
	{
		Collection<Pcmat> pcmats = new ArrayList<Pcmat>();
		pcmats.add(getEntity(id));
		
		return pcmats;
	}
}
