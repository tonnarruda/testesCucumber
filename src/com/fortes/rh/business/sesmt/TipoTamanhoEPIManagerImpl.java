package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.TipoTamanhoEPIDao;
import com.fortes.rh.model.sesmt.TipoTamanhoEPI;

@Component
public class TipoTamanhoEPIManagerImpl extends GenericManagerImpl<TipoTamanhoEPI, TipoTamanhoEPIDao> implements TipoTamanhoEPIManager
{
	@Autowired
	TipoTamanhoEPIManagerImpl(TipoTamanhoEPIDao tipoTamanhoEPIDao) {
		setDao(tipoTamanhoEPIDao);
	}
	
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