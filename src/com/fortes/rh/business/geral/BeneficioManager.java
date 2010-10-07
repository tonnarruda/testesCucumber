/* autor: Moesio Medeiros
 * data: 05/06/2006
 * Requisito: RFA007 Benefícios
 */

package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.HistoricoBeneficio;

public interface BeneficioManager extends GenericManager<Beneficio>
{
	public Collection<Beneficio> getBeneficiosByHistoricoColaborador(Long historicoId);
	Beneficio findBeneficioById(Long id);
	public void saveHistoricoBeneficio(Beneficio beneficio, HistoricoBeneficio historico) throws Exception;
}