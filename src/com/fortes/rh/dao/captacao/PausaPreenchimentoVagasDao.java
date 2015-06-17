/* Autor: Marcelo Carvalheiro
 * Data: 15/06/2015
 */
package com.fortes.rh.dao.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.PausaPreenchimentoVagas;

public interface PausaPreenchimentoVagasDao extends GenericDao<PausaPreenchimentoVagas> {
	
	PausaPreenchimentoVagas findUltimaPausaBySolicitacaoId(Long solicitacaoId);
	
}