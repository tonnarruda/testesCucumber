package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ComposicaoSesmtDao;
import com.fortes.rh.model.sesmt.ComposicaoSesmt;

@Component
public class ComposicaoSesmtManagerImpl extends GenericManagerImpl<ComposicaoSesmt, ComposicaoSesmtDao> implements ComposicaoSesmtManager
{
	
	@Autowired
	ComposicaoSesmtManagerImpl(ComposicaoSesmtDao composicaoSesmtDao) {
		setDao(composicaoSesmtDao);
	}

	public Collection<ComposicaoSesmt> findAllSelect(Long empresaId) {
		return getDao().findAllSelect(empresaId);
	}

	public ComposicaoSesmt findByIdProjection(Long composicaoSesmtId) {
		return getDao().findByIdProjection(composicaoSesmtId);
	}

	public ComposicaoSesmt findByData(Long empresaId, Date data) {
		return getDao().findByData(empresaId, data);
	}
}
