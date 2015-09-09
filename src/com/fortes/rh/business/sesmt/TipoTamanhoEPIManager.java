package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.TipoTamanhoEPI;

public interface TipoTamanhoEPIManager extends GenericManager<TipoTamanhoEPI>
{
	void salvarTamanhoEPIs(Long tipoEPIId, Collection<TipoTamanhoEPI> tipoTamanhoEPIs);
	void removeByTipoEPI(Long tipoEPIId);
}