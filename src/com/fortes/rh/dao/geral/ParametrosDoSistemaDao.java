package com.fortes.rh.dao.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ParametrosDoSistema;

public interface ParametrosDoSistemaDao extends GenericDao<ParametrosDoSistema>
{
	ParametrosDoSistema findByIdProjection(Long id);
	String findModulos(Long id);
	void updateModulos(String papeis);
	void disablePapeisIds();
}