package com.fortes.rh.dao.sesmt;


import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.TipoEPI;

public interface TipoEPIDao extends GenericDao<TipoEPI>
{
	public Collection<TipoEPI> findCollectionTipoEPI(Long empresId);
	public Long findTipoEPIId(Long epiId) ;
	public TipoEPI findTipoEPI(Long tipoEPIId);
}