package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.ComposicaoSesmt;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.ComposicaoSesmtManager;
import com.fortes.rh.dao.sesmt.ComposicaoSesmtDao;

public class ComposicaoSesmtManagerImpl extends GenericManagerImpl<ComposicaoSesmt, ComposicaoSesmtDao> implements ComposicaoSesmtManager
{

	public Collection<ComposicaoSesmt> findAllSelect(Long empresaId) {
		return getDao().findAllSelect(empresaId);
	}

	public ComposicaoSesmt findByIdProjection(Long composicaoSesmtId) {
		return getDao().findByIdProjection(composicaoSesmtId);
	}
}
