package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.AreaFormacaoDao;
import com.fortes.rh.model.geral.AreaFormacao;

@Component
@SuppressWarnings("unchecked")
public class AreaFormacaoDaoHibernate extends GenericDaoHibernate<AreaFormacao> implements AreaFormacaoDao
{
	public Collection<AreaFormacao> findByCargo(Long cargoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new AreaFormacao(a.id, a.nome) ");
		hql.append("from Cargo as c ");
		hql.append("join c.areaFormacaos as a ");
		hql.append("	where c.id = :cargoId ");
		hql.append("order by a.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("cargoId", cargoId);

		return query.list();
	}
	public Collection<AreaFormacao> findByFiltro(int page, int pagingSize, AreaFormacao areaFormacao)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"af");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("af.id"), "id");
		p.add(Projections.property("af.nome"), "nome");
		
		criteria.setProjection(p);
		
		if (areaFormacao != null && StringUtils.isNotBlank(areaFormacao.getNome()))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + areaFormacao.getNome() + "%", StandardBasicTypes.STRING));

		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);
		criteria.addOrder(Order.asc("af.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaFormacao.class));

		return criteria.list();
		
	}
	public Integer getCount(AreaFormacao areaFormacao)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"af");
		criteria.setProjection(Projections.rowCount());
		
		if (areaFormacao != null && StringUtils.isNotBlank(areaFormacao.getNome()))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + areaFormacao.getNome() + "%", StandardBasicTypes.STRING));
	
		return (Integer)criteria.uniqueResult();
	}
}