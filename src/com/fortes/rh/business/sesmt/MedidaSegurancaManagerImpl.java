package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.MedidaSegurancaDao;
import com.fortes.rh.model.sesmt.MedidaSeguranca;

@Component
public class MedidaSegurancaManagerImpl extends GenericManagerImpl<MedidaSeguranca, MedidaSegurancaDao> implements MedidaSegurancaManager
{
	@Autowired
	MedidaSegurancaManagerImpl(MedidaSegurancaDao medidaSegurancaDao) {
			setDao(medidaSegurancaDao);
	}
	
	public Collection<MedidaSeguranca> findAllSelect(String descricao, Long empresaId) 
	{
		return getDao().findAllSelect(descricao, empresaId);
	}
}
