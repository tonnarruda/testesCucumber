package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.TipoEPIDao;
import com.fortes.rh.model.sesmt.TipoEPI;

public class TipoEPIManagerImpl extends GenericManagerImpl<TipoEPI, TipoEPIDao> implements TipoEPIManager
{
	public Collection<TipoEPI> findCollectionTipoEPI(Long empresId)
	{
		return getDao().findCollectionTipoEPI(empresId);
	}

	public TipoEPI findTipoEPI(Long epiId)
	{
		return getDao().findTipoEPI(epiId);
	}

	public Long findTipoEPIId(Long epiId)
	{
		return getDao().findTipoEPIId(epiId);
	}
	
	public void clonar(TipoEPI tipoEPIInteresse, Long empresaDestinoId)
	{
		tipoEPIInteresse.setId(null);
		tipoEPIInteresse.setEmpresaId(empresaDestinoId);
		
		getDao().save(tipoEPIInteresse);
	}
}