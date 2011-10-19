package com.fortes.rh.business.cargosalario;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;

public interface FaturamentoMensalManager extends GenericManager<FaturamentoMensal>
{

	Collection<FaturamentoMensal> findAllSelect(Long empresaId);
}
