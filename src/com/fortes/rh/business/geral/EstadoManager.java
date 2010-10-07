package com.fortes.rh.business.geral;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Estado;

public interface EstadoManager extends GenericManager<Estado>
{
	Estado findBySigla(String sigla);
}