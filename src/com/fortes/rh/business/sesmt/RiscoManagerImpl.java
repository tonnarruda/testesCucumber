package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Risco;

public class RiscoManagerImpl extends GenericManagerImpl<Risco, RiscoDao> implements RiscoManager
{
	@SuppressWarnings("unchecked")
	public Collection<Epi> findEpisByRisco(Long riscoId)
	{
		Collection<Epi> epis = new ArrayList<Epi>();
		try
		{
			List lista = getDao().findEpisByRisco(riscoId);

			Epi epi;
			for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
			{
				Object[] array = it.next();
				epi = new Epi();
				epi.setId((Long) array[0]);
				epi.setNome((String) array[1]);

				epis.add(epi);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return epis;
	}

	public Collection<Risco> findAllSelect(Long empresaId) 
	{
		return getDao().findAllSelect(empresaId);
	}
}