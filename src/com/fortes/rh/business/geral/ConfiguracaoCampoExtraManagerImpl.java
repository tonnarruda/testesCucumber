package com.fortes.rh.business.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;

@Component
public class ConfiguracaoCampoExtraManagerImpl extends GenericManagerImpl<ConfiguracaoCampoExtra, ConfiguracaoCampoExtraDao> implements ConfiguracaoCampoExtraManager
{
	@Autowired
	ConfiguracaoCampoExtraManagerImpl(ConfiguracaoCampoExtraDao dao) {
		setDao(dao);
	}

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
