package com.fortes.rh.business.sesmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.AfastamentoDao;
import com.fortes.rh.model.sesmt.Afastamento;

@Component
public class AfastamentoManagerImpl extends GenericManagerImpl<Afastamento, AfastamentoDao> implements AfastamentoManager
{
	@Autowired
	AfastamentoManagerImpl(AfastamentoDao fooDao) {
		setDao(fooDao);
	}
}