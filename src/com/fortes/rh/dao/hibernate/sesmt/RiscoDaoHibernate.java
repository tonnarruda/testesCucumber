package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.model.dicionario.GrupoRiscoESocial;
import com.fortes.rh.model.sesmt.Risco;

@SuppressWarnings("unchecked")
public class RiscoDaoHibernate extends GenericDaoHibernate<Risco> implements RiscoDao
{
	public List findEpisByRisco(Long riscoId)
	{
		String queryHQL = "select distinct e.id, e.nome from Risco r left join r.epis e where r.id = :id";

		return getSession().createQuery(queryHQL).setLong("id", riscoId).list();
	}
	
	public Collection<Risco> findAllSelect(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "risco");
		
		criteria.setFetchMode("epis", FetchMode.JOIN);
		
		criteria.add(Expression.eq("risco.empresa.id", empresaId));
		
		criteria.addOrder(Order.asc("risco.descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}

	public Collection<Risco> listRiscos(int page, int pagingSize, Risco risco) {
		Criteria criteria = getSession().createCriteria(Risco.class, "r");
		criteria.createCriteria("r.fatorDeRisco", "fr", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("r.id"), "id");
		p.add(Projections.property("r.descricao"), "descricao");
		p.add(Projections.property("r.grupoRisco"), "grupoRisco");
		p.add(Projections.property("r.grupoRiscoESocial"), "grupoRiscoESocial");
		p.add(Projections.property("fr.codigo"), "fatorDeRiscoCodigo");
		p.add(Projections.property("fr.descricao"), "fatorDeRiscoDescricao");
		criteria.setProjection(p);

		montaConsulta(risco, criteria);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Risco.class));

		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		criteria.addOrder(Order.asc("r.descricao"));
		return criteria.list();
	}

	public Integer getCount(Risco risco) {
		Criteria criteria = getSession().createCriteria(Risco.class, "r");
		criteria.setProjection(Projections.rowCount());

		montaConsulta(risco, criteria);
		return (Integer) criteria.list().get(0);
	}

	private void montaConsulta(Risco risco, Criteria criteria) {
		criteria.add(Expression.eq("r.empresa.id", risco.getEmpresa().getId()));
		
		if(StringUtils.isNotBlank(risco.getDescricao()))
			criteria.add(Restrictions.ilike("r.descricao", risco.getDescricao(), MatchMode.ANYWHERE));
		
		if(StringUtils.isNotBlank(risco.getGrupoRisco()))
			criteria.add(Restrictions.eq("r.grupoRisco", risco.getGrupoRisco()));
		
		if(StringUtils.isNotBlank(risco.getGrupoRiscoESocial())){
			if(!risco.getGrupoRiscoESocial().equals(GrupoRiscoESocial.SEM_GRUPO_CONFIGURADO) )
				criteria.add(Restrictions.eq("r.grupoRiscoESocial", risco.getGrupoRiscoESocial()));
			else
				criteria.add(Restrictions.isNull("r.grupoRiscoESocial"));
		}
	}
}