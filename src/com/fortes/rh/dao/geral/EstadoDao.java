package com.fortes.rh.dao.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Estado;

public interface EstadoDao extends GenericDao<Estado>
{
	Estado findBySigla(String sigla);
}