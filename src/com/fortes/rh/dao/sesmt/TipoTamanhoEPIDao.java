package com.fortes.rh.dao.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.TipoTamanhoEPI;

public interface TipoTamanhoEPIDao extends GenericDao<TipoTamanhoEPI>
{
	void removeByTipoEPI(Long tipoEPIId);
}