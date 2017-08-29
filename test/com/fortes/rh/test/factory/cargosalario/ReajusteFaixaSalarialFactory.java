package com.fortes.rh.test.factory.cargosalario;

import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;

public class ReajusteFaixaSalarialFactory {
	
	public static ReajusteFaixaSalarial getEntity(TabelaReajusteColaborador tabelaReajusteColaborador, FaixaSalarial faixaSalarial, Double valorAtual, Double valorProposto){
		ReajusteFaixaSalarial reajusteFaixaSalarial = new ReajusteFaixaSalarial();
		reajusteFaixaSalarial.setFaixaSalarial(faixaSalarial);
		reajusteFaixaSalarial.setTabelaReajusteColaborador(tabelaReajusteColaborador);
		reajusteFaixaSalarial.setValorAtual(valorAtual);
		reajusteFaixaSalarial.setValorProposto(valorProposto);
		return reajusteFaixaSalarial;
	}
}