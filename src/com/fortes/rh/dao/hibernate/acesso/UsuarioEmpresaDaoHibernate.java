package com.fortes.rh.dao.hibernate.acesso;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;

@SuppressWarnings("unchecked")
public class UsuarioEmpresaDaoHibernate extends GenericDaoHibernate<UsuarioEmpresa> implements UsuarioEmpresaDao
{
	public Collection<UsuarioEmpresa> findAllBySelectUsuarioEmpresa(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(UsuarioEmpresa.class, "ue");
		criteria.createCriteria("ue.empresa", "emp");
		criteria.createCriteria("ue.usuario", "u");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("u.id"), "usuarioId");
		p.add(Projections.property("u.nome"), "usuarioNome");
		p.add(Projections.property("ue.id"), "id");

		criteria.setProjection(p);

		criteria.add(Expression.eq("emp.id", empresaId));
		criteria.addOrder(Order.asc("u.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioEmpresa.class));

		return criteria.list();
	}

	public void removeAllUsuario(Usuario usuario)
	{
		String hql = "delete UsuarioEmpresa ue where ue.usuario.id = :id";

		Query query = getSession().createQuery(hql);

		query.setLong("id", usuario.getId());

		query.executeUpdate();
	}

	/**
	 * Busca os usuários pelo codigoAC da empresa *OU* id da empresa
	 */
	public Collection<UsuarioEmpresa> findUsuariosByEmpresaRoleSetorPessoal(String empresaCodigoAC, String grupoAC, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(UsuarioEmpresa.class, "ue");
		criteria.createCriteria("ue.empresa", "emp");
		criteria.createCriteria("ue.usuario", "u");
		criteria.createCriteria("ue.perfil", "per");
		criteria.createCriteria("per.papeis", "p");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ue.id"), "id");
		p.add(Projections.property("u.id"), "usuarioId");
		p.add(Projections.property("emp.id"), "empresaId");

		criteria.setProjection(p);

		if (StringUtils.isNotBlank(empresaCodigoAC))
		{
			criteria.add(Expression.eq("emp.codigoAC", empresaCodigoAC));
			criteria.add(Expression.eq("emp.grupoAC", grupoAC));
		}
		else if (empresaId != null)
			criteria.add(Expression.eq("emp.id", empresaId));
		
		
		criteria.add(Expression.eq("p.codigo", "RECEBE_ALERTA_SETORPESSOAL"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioEmpresa.class));

		return criteria.list();
	}
	
	public Collection<UsuarioEmpresa> findUsuariosByEmpresaRole(Long empresaId, String role)
	{
		Criteria criteria = getSession().createCriteria(UsuarioEmpresa.class, "ue");
		criteria.createCriteria("ue.empresa", "emp");
		criteria.createCriteria("ue.usuario", "u");
		criteria.createCriteria("ue.perfil", "per");
		criteria.createCriteria("per.papeis", "p");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ue.id"), "id");
		p.add(Projections.property("u.id"), "usuarioId");
		p.add(Projections.property("emp.id"), "empresaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("emp.id", empresaId));
		criteria.add(Expression.eq("p.codigo", role));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioEmpresa.class));

		return criteria.list();
	}

	public Collection<UsuarioEmpresa> findByUsuario(Long usuarioId)
	{
		Criteria criteria = getSession().createCriteria(UsuarioEmpresa.class, "ue");
		criteria.createCriteria("ue.empresa", "emp", Criteria.LEFT_JOIN);
		criteria.createCriteria("ue.usuario", "u", Criteria.LEFT_JOIN);
		criteria.createCriteria("ue.perfil", "per", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ue.id"), "id");
		p.add(Projections.property("u.id"), "usuarioId");
		p.add(Projections.property("emp.id"), "empresaId");
		p.add(Projections.property("per.id"), "perfilId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("u.id", usuarioId));

		criteria.addOrder(Order.asc("emp.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioEmpresa.class));

		return criteria.list();
	}
}