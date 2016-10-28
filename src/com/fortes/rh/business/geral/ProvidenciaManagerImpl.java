package com.fortes.rh.business.geral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.ProvidenciaDao;
import com.fortes.rh.model.geral.Providencia;

@Component
public class ProvidenciaManagerImpl extends GenericManagerImpl<Providencia, ProvidenciaDao> implements ProvidenciaManager
{
	@Autowired
	ProvidenciaManagerImpl(ProvidenciaDao dao) {
		setDao(dao);
	}
}
