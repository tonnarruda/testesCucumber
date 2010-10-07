package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.TipoEPI;

public interface TipoEPIManager extends GenericManager<TipoEPI>
{
	public Collection<TipoEPI> findCollectionTipoEPI(Long empresId);
	public void clonar(TipoEPI tipoEPIInteresse, Long empresaDestinoId);
	public Long findTipoEPIId(Long epiId);
	public TipoEPI findTipoEPI(Long epiId);
}