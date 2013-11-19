package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.MedidaSeguranca;

public interface MedidaSegurancaManager extends GenericManager<MedidaSeguranca>
{
	Collection<MedidaSeguranca> findAllSelect(String descricao, Long empresaId);
}
