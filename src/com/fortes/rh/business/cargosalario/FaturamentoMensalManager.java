package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;

public interface FaturamentoMensalManager extends GenericManager<FaturamentoMensal>
{
	Collection<FaturamentoMensal> findAllSelect(Long empresaId);
	Collection<Object[]> findByPeriodo(Date inicio, Date fim, Long empresaId, Long[] estabelecimentosIds);
	Double somaByPeriodo(Date dataIni, Date dataFim, Long[] empresaIds);
}
