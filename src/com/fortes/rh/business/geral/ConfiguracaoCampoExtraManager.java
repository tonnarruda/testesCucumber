package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;

public interface ConfiguracaoCampoExtraManager extends GenericManager<ConfiguracaoCampoExtra>
{
	Collection<ConfiguracaoCampoExtra> findAllSelect(Long empresaId);
	boolean atualizaTodas();
	void removeAllNotModelo();

}
