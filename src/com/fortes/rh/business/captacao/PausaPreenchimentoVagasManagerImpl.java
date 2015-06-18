/* Autor: Marcelo Carvalheiro
 * Data: 15/06/2015
 */
package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.PausaPreenchimentoVagasDao;
import com.fortes.rh.model.captacao.PausaPreenchimentoVagas;

public class PausaPreenchimentoVagasManagerImpl extends GenericManagerImpl<PausaPreenchimentoVagas, PausaPreenchimentoVagasDao> implements PausaPreenchimentoVagasManager {

	public PausaPreenchimentoVagas findUltimaPausaBySolicitacaoId(Long solicitacaoId) {
		return getDao().findUltimaPausaBySolicitacaoId(solicitacaoId);
	}

	public void removeBySolicitacaoId(Long solicitacaoId) {
		getDao().removeBySolicitacaoId(solicitacaoId);
		
	}
	
}