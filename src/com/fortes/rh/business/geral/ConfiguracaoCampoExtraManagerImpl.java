package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;

public class ConfiguracaoCampoExtraManagerImpl extends GenericManagerImpl<ConfiguracaoCampoExtra, ConfiguracaoCampoExtraDao> implements ConfiguracaoCampoExtraManager
{

	public Collection<ConfiguracaoCampoExtra> findAllSelect(Long empresaId) {
		return getDao().findAllSelect(empresaId);
	}

	public boolean atualizaTodas() 
	{
		Collection<ConfiguracaoCampoExtra> campos = getDao().findDistinct();
		return campos.size() == 32;
	}

	public void removeAllNotModelo() 
	{
		getDao().removeAllNotModelo();
	}
		
}
