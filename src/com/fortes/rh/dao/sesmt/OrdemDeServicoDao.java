package com.fortes.rh.dao.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.OrdemDeServico;

public interface OrdemDeServicoDao extends GenericDao<OrdemDeServico> 
{
	public OrdemDeServico findOrdemServicoProjection(Long id);

}
