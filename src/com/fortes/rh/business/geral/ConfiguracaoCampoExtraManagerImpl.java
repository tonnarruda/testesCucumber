package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraDao;

public class ConfiguracaoCampoExtraManagerImpl extends GenericManagerImpl<ConfiguracaoCampoExtra, ConfiguracaoCampoExtraDao> implements ConfiguracaoCampoExtraManager
{

	public Collection<ConfiguracaoCampoExtra> findAllSelect() {
		return getDao().findAllSelect();
	}
		
}
