package com.fortes.rh.business.desenvolvimento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.DNTDao;
import com.fortes.rh.model.desenvolvimento.DNT;

@Component
public class DNTManagerImpl extends GenericManagerImpl<DNT, DNTDao> implements DNTManager
{
	@Autowired
	DNTManagerImpl(DNTDao dao) {
		setDao(dao);
	}
	
	public DNT getUltimaDNT(Long empresaId)
	{
		return getDao().getUltimaDNT(empresaId);
	}
}