package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.IndiceDao;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.geral.Empresa;

@Component
@SuppressWarnings("unchecked")
public class IndiceDaoHibernate extends GenericDaoHibernate<Indice> implements IndiceDao
{
	public Indice findByIdProjection(Long indiceId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "i");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("i.id"), "id");
		p.add(Projections.property("i.nome"), "nome");
		p.add(Projections.property("i.codigoAC"), "codigoAC");

		criteria.setProjection(p);

		criteria.add(Expression.eq("i.id", indiceId));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (Indice) criteria.uniqueResult();
	}

	public Indice findByCodigo(String codigo, String grupoAC)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "i");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("i.id"), "id");
		p.add(Projections.property("i.nome"), "nome");
		p.add(Projections.property("i.codigoAC"), "codigoAC");
		p.add(Projections.property("i.grupoAC"), "grupoAC");

		criteria.setProjection(p);

		criteria.add(Expression.eq("i.codigoAC", codigo));
		criteria.add(Expression.eq("i.grupoAC", grupoAC));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (Indice) criteria.uniqueResult();
	}

	public boolean remove(String codigo, String grupoAC)
	{
		String hql = "delete from Indice i where i.codigoAC = :codigo and i.grupoAC = :grupoac";
		
		Query query = getSession().createQuery(hql);
		query.setString("codigo", codigo);
		query.setString("grupoac", grupoAC);

		int result = query.executeUpdate();

		return result == 1;
	}

	public Indice findHistoricoAtual(Long indiceId, Date dataHistorico)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Indice(i.id,i.nome, hi.id, hi.data, hi.valor) ");
		hql.append("from Indice i ");
		hql.append("left join i.indiceHistoricos hi with hi.data = (select max(hi2.data) ");
		hql.append("                                            from IndiceHistorico hi2 ");
		hql.append("                                           where hi2.indice.id = i.id ");
		hql.append("                                             and hi2.data <= :data) ");
		hql.append("where i.id = :indiceId ");
		hql.append("order by hi.data ");

		Query query = getSession().createQuery(hql.toString());
		
		query.setDate("data", dataHistorico);
		query.setLong("indiceId", indiceId);

		return (Indice) query.uniqueResult();
	}

	public Indice findIndiceByCodigoAc(String indiceCodigoAC, String grupoAC)
	{
		Criteria criteria = getSession().createCriteria(Indice.class, "i");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("i.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("i.codigoAC", indiceCodigoAC));
		criteria.add(Expression.eq("i.grupoAC", grupoAC));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Indice.class));
		criteria.setMaxResults(1);

		return (Indice) criteria.uniqueResult();
	}

	public Collection<Indice> findSemCodigoAC(Empresa empresa) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "i");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("i.id"), "id");
		p.add(Projections.property("i.nome"), "nome");

		criteria.setProjection(p);
		
		criteria.add(Expression.isNull("i.codigoAC"));
		
		if(empresa.getGrupoAC() != null && !empresa.getGrupoAC().equals(""))
			criteria.add(Expression.eq("i.grupoAC", empresa.getGrupoAC()));

		criteria.addOrder(Order.asc("i.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();	
	}

	public Collection<Indice> findComHistoricoAtual(Long[] indicesIds) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Indice(i.id,i.nome, hi.id, hi.data, hi.valor) ");
		hql.append("from Indice i ");
		hql.append("left join i.indiceHistoricos hi with hi.data = (select max(hi2.data) ");
		hql.append("                                            from IndiceHistorico hi2 ");
		hql.append("                                           where hi2.indice.id = i.id ");
		hql.append("                                             and hi2.data <= :hoje) ");
		hql.append("where i.id in (:indicesIds) ");
		hql.append("order by hi.data ");

		Query query = getSession().createQuery(hql.toString());
		
		query.setDate("hoje", new Date());
		query.setParameterList("indicesIds", indicesIds, StandardBasicTypes.LONG);

		return query.list();
	}

	public Collection<Indice> findComHistoricoAtual(Empresa empresa) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Indice(i.id,i.nome, hi.id, hi.data, hi.valor) ");
		hql.append("from Indice i ");
		hql.append("left join i.indiceHistoricos hi with hi.data = (select max(hi2.data) ");
		hql.append("                                            from IndiceHistorico hi2 ");
		hql.append("                                           where hi2.indice.id = i.id ");
		hql.append("                                             and hi2.data <= :hoje) ");

		if (empresa.isAcIntegra())
			hql.append("where i.grupoAC = :grupoAC ");
		
		hql.append("order by i.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		
		if (empresa.isAcIntegra())
			query.setString("grupoAC", empresa.getGrupoAC());

		return query.list();
	}
	
	public Collection<Indice> findIndices(int page, int pagingSize, String nome) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "i");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("i.id"), "id");
		p.add(Projections.property("i.nome"), "nome");
		p.add(Projections.property("i.codigoAC"), "codigoAC");

		criteria.setProjection(p);

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Expression.ilike("i.nome","%"+ nome +"%"));
		}
		
		if(pagingSize > 0) {
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	public Integer getCount(String nome){
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "i");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("i.id"), "id");
		p.add(Projections.property("i.nome"), "nome");
		p.add(Projections.property("i.codigoAC"), "codigoAC");

		criteria.setProjection(p);

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Expression.ilike("i.nome","%"+ nome +"%"));
		}
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		Collection<Indice> indices = criteria.list();
		
		if (indices == null || indices.isEmpty()) {
			return 0;
		} else {
			return indices.size();
		}
	}
}