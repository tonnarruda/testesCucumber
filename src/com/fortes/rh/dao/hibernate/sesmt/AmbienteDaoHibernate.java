package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.sesmt.Ambiente;

@SuppressWarnings("unchecked")
public class AmbienteDaoHibernate extends GenericDaoHibernate<Ambiente> implements AmbienteDao
{
	public Integer getCount(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Ambiente.class,"a");
		criteria.setProjection(Projections.rowCount());

		criteria.add(Expression.eq("a.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (Integer) criteria.list().get(0);
	}

	public Collection<Ambiente> findAmbientes(int page, int pagingSize, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Ambiente.class,"a");
		criteria.createCriteria("a.estabelecimento", "e", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");
		p.add(Projections.property("e.nome"), "projectionEstabelecimentoNome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("a.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Ambiente.class));
		criteria.addOrder(Order.asc("a.nome"));

		//	Se page e pagingSize = 0, chamada do método sobrecarregado sem paginação
		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		return criteria.list();
	}

	public Collection<Ambiente> findByEstabelecimento(Long estabelecimentoId)
	{
		Criteria criteria = getSession().createCriteria(Ambiente.class,"a");
		criteria.createCriteria("a.estabelecimento", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", estabelecimentoId));
		
		criteria.addOrder(Order.asc("a.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Ambiente.class));

		return criteria.list();
	}

	public Ambiente findByIdProjection(Long ambienteId)
	{
		Criteria criteria = getSession().createCriteria(Ambiente.class,"a");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.nome"), "nome");
		p.add(Projections.property("a.empresa.id"), "projectionEmpresaId");
		p.add(Projections.property("a.estabelecimento.id"), "projectionEstabelecimentoId");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("a.id", ambienteId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Ambiente.class));
		
		return (Ambiente) criteria.uniqueResult();
	}

	public Collection<Ambiente> findByIds(Collection<Long> ambienteIds, Date data, Long estabelecimentoId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct new Ambiente(a.id, a.nome, hist.descricao, hist.tempoExposicao) from Ambiente a join a.historicoAmbientes hist ");
		hql.append("where hist.data = (select max(ha2.data) " +
								"from HistoricoAmbiente ha2 " +
								"where ha2.ambiente.id = hist.ambiente.id and ha2.data <= :data) ");
		
		if(ambienteIds != null && !ambienteIds.isEmpty())
			hql.append("and hist.ambiente.id in (:ambienteIds) ");

		hql.append("and a.estabelecimento.id in (:estabelecimentoId) ");
		hql.append("order by a.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		
		if(ambienteIds != null && !ambienteIds.isEmpty())
			query.setParameterList("ambienteIds", ambienteIds, Hibernate.LONG);

		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setDate("data", data);
		
		return query.list();
	}
	
	public int getQtdColaboradorByAmbiente(Long ambienteId, Date data, String sexo)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select count(*) as qtd ");
		hql.append("from HistoricoColaborador hc ");
		hql.append("	join hc.colaborador c ");
		hql.append("	join hc.ambiente a ");
		hql.append("where   hc.data = (select max(hc2.data) from HistoricoColaborador hc2 where hc2.data <=:data and hc2.status = :status and hc2.colaborador.id = hc.colaborador.id) ");
		hql.append("	and c.pessoal.sexo = :sexo ");
		hql.append("	and a.id = :ambienteId ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setString("sexo", sexo);
		query.setLong("ambienteId", ambienteId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return (Integer)query.uniqueResult();
	}

	public void deleteByEstabelecimento(Long[] estabelecimentoIds) throws Exception {
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
		{
			String hql = "delete Ambiente where estabelecimento.id in (:estabelecimentoIds)";
			Query query = getSession().createQuery(hql);

			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
			query.executeUpdate();		
		}
	}
}