package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.NoticiaDao;
import com.fortes.rh.model.geral.Noticia;

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
	public Noticia findByTexto(String texto) 
	{
		Criteria criteria = getSession().createCriteria(Noticia.class, "n");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("n.id"), "id");
		p.add(Projections.property("n.texto"), "texto");
		p.add(Projections.property("n.link"), "link");
		p.add(Projections.property("n.criticidade"), "criticidade");
		p.add(Projections.property("n.publicada"), "publicada");

		criteria.setProjection(p);
		
		criteria.add(Expression.like("n.texto", texto));

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

	public void despublicarTodas() 
	{
		getSession().createQuery("update Noticia set publicada = false where publicada = true").executeUpdate();
	}
}