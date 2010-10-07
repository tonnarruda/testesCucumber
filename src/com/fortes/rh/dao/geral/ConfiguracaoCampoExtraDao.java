package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.sesmt.Epc;

public interface ConfiguracaoCampoExtraDao extends GenericDao<ConfiguracaoCampoExtra> 
{
	Collection<ConfiguracaoCampoExtra> findAllSelect();
	
}
