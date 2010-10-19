package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.web.tags.CheckBox;

public interface TipoEPIManager extends GenericManager<TipoEPI>
{
	public Collection<TipoEPI> findCollectionTipoEPI(Long empresId);
	public void clonar(TipoEPI tipoEPIInteresse, Long empresaDestinoId);
	public Long findTipoEPIId(Long epiId);
	public TipoEPI findTipoEPI(Long epiId);
	public Collection<CheckBox> getByEmpresa(Long empresaId);
}