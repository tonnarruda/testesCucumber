package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.web.tags.CheckBox;

public interface AreaFormacaoManager extends GenericManager<AreaFormacao>
{
	Collection<AreaFormacao> findByCargo(Long cargoId);
	Collection<CheckBox> populaCheckOrderNome();
	Collection<AreaFormacao> populaAreas(String[] areasCheck);
	Collection<AreaFormacao> findByFiltro(int page, int pagingSize,	AreaFormacao areaformcao);
	public Integer getCount(AreaFormacao areaformcaoa);
}