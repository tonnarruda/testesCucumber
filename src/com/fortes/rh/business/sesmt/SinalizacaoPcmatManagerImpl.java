package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.SinalizacaoPcmatDao;
import com.fortes.rh.model.sesmt.SinalizacaoPcmat;

@Component
public class SinalizacaoPcmatManagerImpl extends GenericManagerImpl<SinalizacaoPcmat, SinalizacaoPcmatDao> implements SinalizacaoPcmatManager
{
	@Autowired
	SinalizacaoPcmatManagerImpl(SinalizacaoPcmatDao sinalizacaoPcmatDao) {
		setDao(sinalizacaoPcmatDao);
	}
	
	public Collection<SinalizacaoPcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}
}
