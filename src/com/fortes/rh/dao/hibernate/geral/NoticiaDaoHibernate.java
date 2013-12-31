package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.NoticiaDao;
import com.fortes.rh.model.geral.Noticia;

public class NoticiaDaoHibernate extends GenericDaoHibernate<Noticia> implements NoticiaDao
{
	@SuppressWarnings("unchecked")
	public Collection<Noticia> findByUsuario(Long usuarioId) 
	{
		StringBuilder hql = new StringBuilder("select new Noticia(n.id, n.texto, n.link, n.criticidade, un.id) ");
		hql.append("from Noticia n ");
		hql.append("left join n.usuarioNoticias un with un.usuario.id = :usuarioId ");
		hql.append("order by n.criticidade, n.id");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("usuarioId", usuarioId);
		
		return query.list();
	}
}