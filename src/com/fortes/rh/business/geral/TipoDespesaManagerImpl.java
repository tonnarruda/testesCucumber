package com.fortes.rh.business.geral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.TipoDespesaDao;
import com.fortes.rh.model.geral.TipoDespesa;

@Component
public class TipoDespesaManagerImpl extends GenericManagerImpl<TipoDespesa, TipoDespesaDao> implements TipoDespesaManager
{
	@Autowired
	TipoDespesaManagerImpl(TipoDespesaDao dao) {
		setDao(dao);
	}
}
