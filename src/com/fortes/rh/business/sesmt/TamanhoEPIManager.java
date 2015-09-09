package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.TamanhoEPI;
import com.fortes.web.tags.CheckBox;

public interface TamanhoEPIManager extends GenericManager<TamanhoEPI> {
	Collection<CheckBox> populaCheckOrderDescricao(Long tipoEPIId);
}