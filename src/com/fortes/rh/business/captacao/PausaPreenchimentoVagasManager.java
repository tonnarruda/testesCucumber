/* Autor: Marcelo Carvalheiro
 * Data: 15/06/2015
 */
package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.PausaPreenchimentoVagas;

public interface PausaPreenchimentoVagasManager extends GenericManager<PausaPreenchimentoVagas> {
	
	PausaPreenchimentoVagas findUltimaPausaBySolicitacaoId(Long solicitacaoId);
	
}