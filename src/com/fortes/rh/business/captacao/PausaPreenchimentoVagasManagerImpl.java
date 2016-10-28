/* Autor: Marcelo Carvalheiro
 * Data: 15/06/2015
 */
package com.fortes.rh.business.captacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.PausaPreenchimentoVagasDao;
import com.fortes.rh.model.captacao.PausaPreenchimentoVagas;

@Component
public class PausaPreenchimentoVagasManagerImpl extends GenericManagerImpl<PausaPreenchimentoVagas, PausaPreenchimentoVagasDao> implements PausaPreenchimentoVagasManager {

	@Autowired
	PausaPreenchimentoVagasManagerImpl(PausaPreenchimentoVagasDao fooDao) {
        setDao(fooDao);
    }
	
	public PausaPreenchimentoVagas findUltimaPausaBySolicitacaoId(Long solicitacaoId) {
		return getDao().findUltimaPausaBySolicitacaoId(solicitacaoId);
	}

	public void removeBySolicitacaoId(Long solicitacaoId) {
		getDao().removeBySolicitacaoId(solicitacaoId);
		
	}
	
}