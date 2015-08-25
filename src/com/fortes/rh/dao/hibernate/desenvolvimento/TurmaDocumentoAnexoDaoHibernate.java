package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.TurmaDocumentoAnexoDao;
import com.fortes.rh.model.desenvolvimento.TurmaDocumentoAnexo;

public class TurmaDocumentoAnexoDaoHibernate extends GenericDaoHibernate<TurmaDocumentoAnexo> implements TurmaDocumentoAnexoDao
{
	public void removeByTurma(Long turmaId) {
		String hql = "delete from TurmaDocumentoAnexo where turma.id = :turmaId ";

		Query query = getSession().createQuery(hql);
		query.setLong("turmaId", turmaId);

		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<TurmaDocumentoAnexo> findByColaborador(Long colaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(TurmaDocumentoAnexo.class, "tda");
		criteria.createCriteria("tda.documentoAnexos", "da");
		criteria.createCriteria("tda.turma", "t");
		criteria.createCriteria("t.colaboradorTurmas", "ct");

		criteria.add(Expression.and(Expression.le("t.dataPrevIni", new Date()), Expression.ge("t.dataPrevFim", new Date())));
		criteria.add(Expression.eq("ct.colaborador.id", colaboradorId));

		criteria.addOrder(Order.asc("da.descricao"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}
}