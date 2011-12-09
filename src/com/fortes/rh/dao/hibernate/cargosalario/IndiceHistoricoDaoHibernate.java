package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
import com.fortes.rh.model.cargosalario.IndiceHistorico;

@SuppressWarnings("unchecked")
public class IndiceHistoricoDaoHibernate extends GenericDaoHibernate<IndiceHistorico> implements IndiceHistoricoDao
{
	public Collection<IndiceHistorico> findAllSelect(Long indiceId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ih");
		criteria.createCriteria("ih.indice", "i", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ih.id"), "id");
		p.add(Projections.property("ih.data"), "data");
		p.add(Projections.property("ih.valor"), "valor");
		p.add(Projections.property("i.id"), "projectionIndiceId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("i.id", indiceId));
		criteria.addOrder(Order.asc("ih.data"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public IndiceHistorico findByIdProjection(Long indiceHistoricoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ih");
		criteria.createCriteria("ih.indice", "i", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ih.id"), "id");
		p.add(Projections.property("ih.data"), "data");
		p.add(Projections.property("ih.valor"), "valor");
		p.add(Projections.property("i.id"), "projectionIndiceId");
		p.add(Projections.property("i.nome"), "projectionIndiceNome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("ih.id", indiceHistoricoId));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (IndiceHistorico) criteria.uniqueResult();
	}

	public boolean verifyData(Long indiceHistoricoId, Date data, Long indiceId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ih");
		criteria.createCriteria("ih.indice", "i", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ih.id"), "id");

		criteria.setProjection(p);

		if(indiceHistoricoId != null)
			criteria.add(Expression.not(Expression.eq("ih.id", indiceHistoricoId)));

		criteria.add(Expression.eq("ih.data", data));
		criteria.add(Expression.eq("i.id", indiceId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		Collection<IndiceHistorico> lista = criteria.list();
		return lista.size() > 0;
	}

	public Double findUltimoSalarioIndice(Long indiceId)
	{
		String hql = "select valor from IndiceHistorico where indice.id = :indiceId and data <= :dataHistorico";

		Query query = getSession().createQuery(hql);
		query.setLong("indiceId", indiceId);
		query.setDate("dataHistorico", new Date());

		List lista = query.list();

		if (lista.size() > 0)
		{
			return (Double) lista.get(0);
		}
		else {
			return null;
		}
	}

	public Collection<IndiceHistorico> findByPeriodo(Long indiceId, Date data, Date dataProximo)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ih");
		criteria.createCriteria("ih.indice", "i", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ih.data"), "data");
		p.add(Projections.property("ih.valor"), "valor");

		criteria.setProjection(p);

		criteria.add(Expression.eq("i.id", indiceId));
		criteria.add(Expression.gt("ih.data", data));
		criteria.add(Expression.le("ih.data", dataProximo));

		criteria.addOrder(Order.asc("ih.data"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public boolean remove(Date data, Long indiceId)
	{
		String hql = "delete from IndiceHistorico ih where ih.data = :data and ih.indice.id = :id";

		Query query = getSession().createQuery(hql);
		query.setDate("data", data);
		query.setLong("id", indiceId);

		int result = query.executeUpdate();

		return result == 1;
	}

	public boolean existsAnteriorByDataIndice(Date data, Long indiceId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ih");
		criteria.createCriteria("ih.indice", "i", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ih.id"), "id");

		criteria.setProjection(p);

		criteria.add(Expression.le("ih.data", data));
		criteria.add(Expression.eq("i.id", indiceId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		Collection<IndiceHistorico> lista = criteria.list();
		return lista.size() > 0;

	}

	public void updateValor(Date data, Long indiceId, Double valor)
	{
		String hql = "update IndiceHistorico ih set ih.valor=:valor where ih.data = :data and ih.indice.id = :id";

		Query query = getSession().createQuery(hql);
		query.setDate("data", data);
		query.setLong("id", indiceId);
		query.setDouble("valor", valor);

		query.executeUpdate();
	}

	public void deleteByIndice(Long[] indiceIds) throws Exception {
		if(indiceIds != null && indiceIds.length > 0)
		{
			String hql = "delete IndiceHistorico where indice.id in (:indiceIds)";
			Query query = getSession().createQuery(hql);
	
			query.setParameterList("indiceIds", indiceIds, Hibernate.LONG);
			query.executeUpdate();		
		}
	}
}