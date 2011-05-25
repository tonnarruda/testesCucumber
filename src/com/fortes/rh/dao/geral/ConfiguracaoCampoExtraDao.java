package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;

public interface ConfiguracaoCampoExtraDao extends GenericDao<ConfiguracaoCampoExtra> 
{
	Collection<ConfiguracaoCampoExtra> findAllSelect(Long empresaId);

	Collection<ConfiguracaoCampoExtra> findDistinct();

	void removeAllNotModelo();

	String[] findAllNomes();
	
}
