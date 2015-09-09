package com.fortes.rh.dao.sesmt;


import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.TamanhoEPI;

public interface TamanhoEPIDao extends GenericDao<TamanhoEPI> {
	
	Collection<TamanhoEPI> findAllByTipoEPIId(Long EPIId);
	
}