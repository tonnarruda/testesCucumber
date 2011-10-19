package com.fortes.rh.dao.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;

public interface FaturamentoMensalDao extends GenericDao<FaturamentoMensal> 
{

	Collection<FaturamentoMensal> findAllSelect(Long empresaId);

	Collection<FaturamentoMensal> findByPeriodo(Date inicio, Date fim, Long empresaId);

}
