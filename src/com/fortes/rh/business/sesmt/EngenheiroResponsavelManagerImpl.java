package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.EngenheiroResponsavelDao;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;

public class EngenheiroResponsavelManagerImpl extends GenericManagerImpl<EngenheiroResponsavel, EngenheiroResponsavelDao> implements EngenheiroResponsavelManager
{
	public EngenheiroResponsavel findByIdProjection(Long engenheiroResponsavelId)
	{
		return getDao().findByIdProjection(engenheiroResponsavelId);
	}
	
	public Collection<EngenheiroResponsavel> getEngenheirosAteData(Long empresaId, Date data)
	{
		Collection<EngenheiroResponsavel> engenheiroResponsavels = getDao().findAllSelect(empresaId);
		Collection<EngenheiroResponsavel> resultado = new ArrayList<EngenheiroResponsavel>();
		
		for (EngenheiroResponsavel engenheiroResponsavel : engenheiroResponsavels)
		{
			if (engenheiroResponsavel.getInicio().compareTo(data) <= 0)
			{
				if (engenheiroResponsavel.getFim() != null && engenheiroResponsavel.getFim().compareTo(data) > 0)
					engenheiroResponsavel.setFim(null);
				
				resultado.add(engenheiroResponsavel);
			}
			else
				break;
		}
		
		return resultado;
	}

}