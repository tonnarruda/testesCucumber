package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.NoticiaDao;
import com.fortes.rh.model.geral.Noticia;
import com.fortes.rh.model.geral.UsuarioNoticia;
import com.fortes.rh.util.StringUtil;

public class NoticiaDaoHibernate extends GenericDaoHibernate<Noticia> implements NoticiaDao
{
	@SuppressWarnings("unchecked")
	public Collection<Noticia> findByUsuario(Long usuarioId) 
	{
		StringBuilder hql = new StringBuilder("select new Noticia(n.id, n.texto, n.link, n.criticidade, (case when un.id is not null then true else false end)) ");
		hql.append("from Noticia n ");
		hql.append("left join n.usuarioNoticias un with un.usuario.id = :usuarioId ");
		hql.append("where n.publicada = true ");
		hql.append("order by n.id desc ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("usuarioId", usuarioId);
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public Noticia find(String texto, String link, Integer criticidade) 
	{
		Criteria criteria = getSession().createCriteria(Noticia.class, "n");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("n.id"), "id");
		p.add(Projections.property("n.texto"), "texto");
		p.add(Projections.property("n.link"), "link");
		p.add(Projections.property("n.criticidade"), "criticidade");
		p.add(Projections.property("n.publicada"), "publicada");

		criteria.setProjection(p);
		
		if (!StringUtil.isBlank(texto))
			criteria.add(Expression.like("n.texto", texto));
		
		if (!StringUtil.isBlank(link))
			criteria.add(Expression.like("n.link", link));
		
		if (criticidade != null)
			criteria.add(Expression.eq("n.criticidade", criticidade));

		criteria.addOrder(Order.desc("n.id"));
		
		criteria.setMaxResults(1);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		List<Noticia> noticias = criteria.list();
		
		if (noticias.isEmpty())
			return null;
		else
			return noticias.get(0);
	}

	// TODO: SEM TESTE
	public void despublicarTodas() 
	{
		getSession().createQuery("update Noticia set publicada = false where publicada = true").executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Collection<Noticia> findUrgentesNaoLidasPorUsuario(Long usuarioId)
	{
		DetachedCriteria subQuery = DetachedCriteria.forClass(UsuarioNoticia.class, "un2")
			.setProjection(Projections.distinct(Projections.property("un2.noticia.id")))
			.add(Restrictions.eq("un2.usuario.id", usuarioId));

		Criteria criteria = getSession().createCriteria(Noticia.class, "n");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("n.id"), "id");
		p.add(Projections.property("n.link"), "link");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("n.criticidade", -1));
		criteria.add(Subqueries.propertyNotIn("n.id", subQuery));
		
		criteria.addOrder(Order.asc("n.id"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}