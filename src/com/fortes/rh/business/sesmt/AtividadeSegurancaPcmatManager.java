package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.AtividadeSegurancaPcmat;

public interface AtividadeSegurancaPcmatManager extends GenericManager<AtividadeSegurancaPcmat>
{
	Collection<AtividadeSegurancaPcmat> findByPcmat(Long pcmatId);
	void clonar(Long pcmatOrigemId, Long pcmatDestinoId);
}
