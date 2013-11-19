package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.MedidaSeguranca;

public interface MedidaSegurancaDao extends GenericDao<MedidaSeguranca> 
{
	Collection<MedidaSeguranca> findAllSelect(String descricao, Long empresaId);
}
