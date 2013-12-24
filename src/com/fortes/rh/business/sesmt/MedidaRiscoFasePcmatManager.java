package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.MedidaRiscoFasePcmat;

public interface MedidaRiscoFasePcmatManager extends GenericManager<MedidaRiscoFasePcmat>
{
	void deleteByRiscoFasePcmat(Long riscoFasePcmatId);
	Collection<MedidaRiscoFasePcmat> findByRiscoFasePcmat(Long riscoFasePcmatId);
	void clonar(Long riscoFasePcmatOrigemId, Long riscoFasePcmatDestinoId);
	Map<Long, Collection<MedidaRiscoFasePcmat>> findByPcmatRiscos(Long pcmatId);
}
