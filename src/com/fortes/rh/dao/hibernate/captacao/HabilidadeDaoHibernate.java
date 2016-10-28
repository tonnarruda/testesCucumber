package com.fortes.rh.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.model.captacao.Habilidade;

@Component
public class HabilidadeDaoHibernate extends GenericDaoHibernate<Habilidade> implements HabilidadeDao
{
	public Collection<Habilidade> findAllSelect(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Habilidade.class, "h");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("h.id"), "id");
		p.add(Projections.property("h.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("h.empresa.id", empresaId));

		criteria.addOrder(Order.asc("h.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Habilidade.class));

		return criteria.list();
	}
	
	public Collection<Habilidade> findByCargo(Long cargoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Habilidade(h.id, h.nome) ");
		hql.append("from Cargo as c ");
		hql.append("join c.habilidades as h ");
		hql.append("	where c.id = :cargoId ");
		hql.append("order by h.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("cargoId", cargoId);

		return query.list();
	}
	
	public Collection<Habilidade> findByAreasOrganizacionalIds(Long[] areaOrganizacionalIds, Long empresasId)
	{
		if(areaOrganizacionalIds == null || areaOrganizacionalIds.length == 0)
			return new ArrayList();

		Criteria criteria = getSession().createCriteria(Habilidade.class, "h");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("h.id"), "id");
		p.add(Projections.property("h.nome"), "nome");
		criteria.setProjection(p);

		criteria.createCriteria("h.areaOrganizacionals","ao");
		criteria.add(Expression.in("ao.id", areaOrganizacionalIds));
		criteria.add(Expression.eq("empresa.id", empresasId));

		criteria.addOrder(Order.asc("h.nome"));
		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Habilidade.class));
		return criteria.list();
	}

	public Collection<Habilidade> findSincronizarHabilidades(Long empresaOrigemId)
	{
		Criteria criteria = getSession().createCriteria(Habilidade.class, "h");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("h.id"), "id");
		p.add(Projections.property("h.nome"), "nome");
		p.add(Projections.property("h.observacao"), "observacao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("h.empresa.id", empresaOrigemId));

		criteria.addOrder(Order.asc("h.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Habilidade.class));

		return criteria.list();
	}

	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception {
		if(areaIds != null && areaIds.length > 0)
		{
			String[] sql = new String[] {"delete from habilidade_areaorganizacional where areaorganizacionals_id in ("+StringUtils.join(areaIds, ",")+");"};
			JDBCConnection.executeQuery(sql);
		}
		
	}

	public Habilidade findByIdProjection(Long habilidadeId)
	{
		Criteria criteria = CHAHelperDaoHibernate.montafindByIdProjection(habilidadeId, getSession(), getEntityClass());
		
		return (Habilidade) criteria.uniqueResult();
	}
}
