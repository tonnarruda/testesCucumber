package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.CodigoCBO;

public interface CodigoCBOManager extends GenericManager<CodigoCBO>
{
	public Collection<String> buscaCodigosCBO(String codigo);
}
