package com.fortes.rh.business.cargosalario;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;

public interface ReajusteFaixaSalarialManager extends GenericManager<ReajusteFaixaSalarial>
{
	void insertReajustes(Long tabelaReajusteColaboradorId, Long[] faixasSalariaisIds, char dissidioPor, double valorDissidio) throws Exception;
	void updateValorProposto(Long reajusteFaixaSalarialId, Double valorAtual, char dissidioPor, Double valorDissidio) throws Exception;
	Collection<ReajusteFaixaSalarial> findByTabelaReajusteColaboradorId(Long tabelaReajusteColaboradorId);
	ReajusteFaixaSalarial findByIdProjection(Long id);
	boolean verificaPendenciasPorFaixa(Long faixaSalarialId);
}