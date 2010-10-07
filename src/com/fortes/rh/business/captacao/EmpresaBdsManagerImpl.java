/* Autor: Igo Coelho
 * Data: 29/06/2006
 * Requisito: RFA020 */

package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.EmpresaBdsDao;
import com.fortes.rh.model.captacao.EmpresaBds;

public class EmpresaBdsManagerImpl extends GenericManagerImpl<EmpresaBds, EmpresaBdsDao> implements EmpresaBdsManager
{
	public Collection<EmpresaBds> findAllProjection(Long[] empresaBdsIds)
	{
		if(empresaBdsIds == null || empresaBdsIds.length == 0)
			return new ArrayList<EmpresaBds>();
		else
			return getDao().findAllProjection(empresaBdsIds);
	}

	public EmpresaBds findByIdProjection(Long empresaBdsId)
	{
		return getDao().findByIdProjection(empresaBdsId);
	}

}