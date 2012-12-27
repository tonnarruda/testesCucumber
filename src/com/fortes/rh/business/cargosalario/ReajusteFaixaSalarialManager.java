package com.fortes.rh.business.cargosalario;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;

public interface ReajusteFaixaSalarialManager extends GenericManager<ReajusteFaixaSalarial>
{
	void insertColetivo(Long tabelaReajusteColaboradorId, Long[] faixasSalariaisIds, char dissidioPor, double valorDissidio) throws Exception;
}