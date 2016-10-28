package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.AreaVivenciaPcmatDao;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;

@Component
public class AreaVivenciaPcmatManagerImpl extends GenericManagerImpl<AreaVivenciaPcmat, AreaVivenciaPcmatDao> implements AreaVivenciaPcmatManager
{
	@Autowired
	AreaVivenciaPcmatManagerImpl(AreaVivenciaPcmatDao fooDao) {
		setDao(fooDao);
	}
	
	public Collection<AreaVivenciaPcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}

	public void clonar(Long pcmatOrigemId, Long pcmatDestinoId) 
	{
		Collection<AreaVivenciaPcmat> areasVivenciaPcmat = getDao().findByPcmat(pcmatOrigemId);
		for (AreaVivenciaPcmat areaVivenciaPcmat : areasVivenciaPcmat) 
		{
			areaVivenciaPcmat.setId(null);
			areaVivenciaPcmat.setPcmatId(pcmatDestinoId);
			save(areaVivenciaPcmat);
		}
	}
}
