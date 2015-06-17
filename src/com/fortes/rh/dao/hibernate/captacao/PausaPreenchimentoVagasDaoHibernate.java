/* Autor: Marcelo Carvalheiro
 * Data: 15/06/2015
 */
package com.fortes.rh.dao.hibernate.captacao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.PausaPreenchimentoVagasDao;
import com.fortes.rh.model.captacao.PausaPreenchimentoVagas;

public class PausaPreenchimentoVagasDaoHibernate extends GenericDaoHibernate<PausaPreenchimentoVagas> implements PausaPreenchimentoVagasDao {
	
	public PausaPreenchimentoVagas findUltimaPausaBySolicitacaoId(Long solicitacaoId) {
	
		Criteria criteria = getSession().createCriteria(PausaPreenchimentoVagas.class, "p");
		
		criteria.add(Expression.eq("p.solicitacao.id", solicitacaoId));
		criteria.add(Expression.isNull("dataReinicio"));
		
		return (PausaPreenchimentoVagas) criteria.uniqueResult();
	}
	
}
