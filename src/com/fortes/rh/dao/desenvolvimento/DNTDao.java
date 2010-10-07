package com.fortes.rh.dao.desenvolvimento;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.DNT;

public interface DNTDao extends GenericDao<DNT>
{
	DNT getUltimaDNT(Long empresaId);
}