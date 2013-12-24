package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.MedidaRiscoFasePcmat;

public interface MedidaRiscoFasePcmatDao extends GenericDao<MedidaRiscoFasePcmat> 
{
	void deleteByRiscoFasePcmat(Long riscoFasePcmatId);
	Collection<MedidaRiscoFasePcmat> findByRiscoFasePcmat(Long riscoFasePcmatId);
	Collection<MedidaRiscoFasePcmat> findByPcmat(Long pcmatId);
}
