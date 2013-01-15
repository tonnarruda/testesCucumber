package com.fortes.rh.business.cargosalario;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.ReajusteIndice;

public interface ReajusteIndiceManager extends GenericManager<ReajusteIndice>
{
	void insertReajustes(Long tabelaReajusteColaboradorId, Long[] indicesIds, char dissidioPor, Double valorDissidio) throws Exception;
}