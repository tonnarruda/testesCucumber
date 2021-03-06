package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.ComposicaoSesmt;

public interface ComposicaoSesmtManager extends GenericManager<ComposicaoSesmt>
{
	Collection<ComposicaoSesmt> findAllSelect(Long empresaId);

	ComposicaoSesmt findByIdProjection(Long composicaoSesmtId);

	ComposicaoSesmt findByData(Long empresaId, Date data);
}
