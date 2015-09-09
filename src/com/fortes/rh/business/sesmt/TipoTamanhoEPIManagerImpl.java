package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.TipoTamanhoEPIDao;
import com.fortes.rh.model.sesmt.TipoTamanhoEPI;

public class TipoTamanhoEPIManagerImpl extends GenericManagerImpl<TipoTamanhoEPI, TipoTamanhoEPIDao> implements TipoTamanhoEPIManager
{
	public void salvarTamanhoEPIs(Long tipoEPIId, Collection<TipoTamanhoEPI> tipoTamanhoEPIs) 
	{
		removeByTipoEPI(tipoEPIId);
		
		if (tipoTamanhoEPIs != null)
		{
			for (TipoTamanhoEPI tipoTamanhoEPI : tipoTamanhoEPIs) {
				tipoTamanhoEPI.setProjectionTipoEPIId(tipoEPIId);
				getDao().save(tipoTamanhoEPI);
			}
		}
	}

	public void removeByTipoEPI(Long tipoEPIId)
	{
		getDao().removeByTipoEPI(tipoEPIId);
	}
}