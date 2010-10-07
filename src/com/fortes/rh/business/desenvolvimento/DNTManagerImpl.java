package com.fortes.rh.business.desenvolvimento;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.DNTDao;
import com.fortes.rh.model.desenvolvimento.DNT;

public class DNTManagerImpl extends GenericManagerImpl<DNT, DNTDao> implements DNTManager
{
	public DNT getUltimaDNT(Long empresaId)
	{
		return getDao().getUltimaDNT(empresaId);
	}
}