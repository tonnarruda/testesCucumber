package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;

public class ConfiguracaoCampoExtraManagerImpl extends GenericManagerImpl<ConfiguracaoCampoExtra, ConfiguracaoCampoExtraDao> implements ConfiguracaoCampoExtraManager
{

	@TesteAutomatico
	public Collection<ConfiguracaoCampoExtra> findAllSelect(Long empresaId) {
		return getDao().findAllSelect(empresaId);
	}

	public boolean atualizaTodas() 
	{
		Collection<ConfiguracaoCampoExtra> campos = getDao().findDistinct();
		return campos.size() == 16;
	}

	@TesteAutomatico
	public void removeAllNotModelo() 
	{
		getDao().removeAllNotModelo();
	}

	@TesteAutomatico
	public String[] findAllNomes() {
		return getDao().findAllNomes();
	}
}
