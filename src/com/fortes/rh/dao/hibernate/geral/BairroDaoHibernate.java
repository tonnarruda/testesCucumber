package com.fortes.rh.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.BairroDao;
import com.fortes.rh.model.geral.Bairro;

@Component
@SuppressWarnings("unchecked")
public class BairroDaoHibernate extends GenericDaoHibernate<Bairro> implements BairroDao
{
	public Collection<Bairro> list(int page, int pagingSize, Bairro bairro)
	{
		Criteria criteria = getSession().createCriteria(Bairro.class, "b");
		criteria.createCriteria("b.cidade","c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("b.id"), "id");
		p.add(Projections.property("b.nome"), "nome");
		p.add(Projections.property("c.nome"), "projectionCidadeNome");

		criteria.setProjection(p);

		// Nome
		if(bairro != null && bairro.getNome() != null && !bairro.getNome().trim().equals(""))
			criteria.add(Expression.like("b.nome", "%"+ bairro.getNome() +"%").ignoreCase() );

		// Cidade
		if(bairro != null && bairro.getCidade() != null && bairro.getCidade().getId() != null)
			criteria.add(Expression.eq("c.id", bairro.getCidade().getId()));

		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);

		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);

		criteria.addOrder(Order.asc("b.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Bairro.class));
		return criteria.list();
	}
	
	public Integer getCount(Bairro bairro) {
		Criteria criteria = getSession().createCriteria(Bairro.class, "b");
		criteria.createCriteria("b.cidade","c");

		// Nome
		if(bairro != null && bairro.getNome() != null && !bairro.getNome().trim().equals(""))
			criteria.add(Expression.like("b.nome", "%"+ bairro.getNome() +"%").ignoreCase() );

		// Cidade
		if(bairro != null && bairro.getCidade() != null && bairro.getCidade().getId() != null)
			criteria.add(Expression.eq("c.id", bairro.getCidade().getId()));
		
		criteria.setProjection(Projections.rowCount());

		return (Integer) criteria.list().get(0);
	}

	public boolean existeBairro(Bairro bairro)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"bairro");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("bairro.id"),"id");
		criteria.setProjection(p);

		criteria.add(Restrictions.eq("cidade.id", bairro.getCidade().getId()));
		
		if(bairro.getId() != null)
			criteria.add(Restrictions.ne("bairro.id", bairro.getId()));
		criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + bairro.getNome() + "%", StandardBasicTypes.STRING));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Bairro.class));
		List result = criteria.list();

		return result.size() > 0;
	}

	public Collection<Bairro> findAllSelect(Long... cidadeIds)
	{
		Criteria criteria = getSession().createCriteria(Bairro.class, "b");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("b.id"), "id");
		p.add(Projections.property("b.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.in("b.cidade.id", cidadeIds));
		criteria.addOrder(Order.asc("b.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Bairro.class));

		return criteria.list();
	}

	public Collection<Bairro> getBairrosBySolicitacao(Long solicitacaoId)
	{
		Collection<Bairro> retorno = new ArrayList<Bairro>();

		String queryHQL = "select b.id, b.nome from Solicitacao s" +
		" left join s.bairros b" +
		" where s.id=:solicitacaoId order by b.nome";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("solicitacaoId", solicitacaoId);

		List<Object[]> objetos = query.list();

		for (Iterator<Object[]> it = objetos.iterator(); it.hasNext();)
		{
			Object[] array = it.next();
			Bairro bairro = new Bairro();
			bairro.setId((Long) array[0]);
			bairro.setNome((String) array[1]);
			retorno.add(bairro);
		}

		return retorno;
	}

	public Collection<Bairro> getBairrosByIds(Long[] bairroIds)
	{
		Criteria criteria = getSession().createCriteria(Bairro.class, "b");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("b.id"), "id");
		p.add(Projections.property("b.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.in("b.id", bairroIds));
		criteria.addOrder(Order.asc("b.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Bairro.class));

		return criteria.list();
	}

	public Bairro findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(Bairro.class, "b");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("b.id"), "id");
		p.add(Projections.property("b.nome"), "nome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("b.id", id));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Bairro.class));
		
		return (Bairro) criteria.uniqueResult();
	}

	public Collection<String> findBairrosNomes()
	{
		Criteria criteria = getSession().createCriteria(Bairro.class, "b");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("b.nome"), "nome");
		criteria.setProjection(p);
		criteria.setProjection(Projections.distinct(p));
		
		criteria.addOrder(Order.asc("b.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}



}