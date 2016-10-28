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
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.acesso.UsuarioEmpresaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;

@Component
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
		criteria.add(Expression.eq("u.acessoSistema", true));
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
	public Collection<UsuarioEmpresa> findUsuariosByEmpresaRole(String empresaCodigoAC, String grupoAC, Long empresaId, String role, Long usuarioIdDesconsiderado)
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
		
		if(usuarioIdDesconsiderado != null)
			criteria.add(Expression.not(Expression.eq("u.id", usuarioIdDesconsiderado)));
		
		criteria.add(Expression.eq("p.codigo", role));
		criteria.add(Expression.eq("u.acessoSistema", true));

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
	
	public boolean containsRole(Long usuarioId, Long empresaId, String role) {
		Criteria criteria = getSession().createCriteria(UsuarioEmpresa.class, "ue");
		criteria.createCriteria("ue.perfil", "per");
		criteria.createCriteria("per.papeis", "p");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ue.id"), "id");

		criteria.setProjection(p);

		criteria.add(Expression.eq("ue.usuario.id", usuarioId));
		
		if(empresaId != null)
			criteria.add(Expression.eq("ue.empresa.id", empresaId));
		
		criteria.add(Expression.eq("p.codigo", role));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioEmpresa.class));

		return criteria.list().size() > 0;
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
		p.add(Projections.property("emp.nome"), "empresaNome");
		p.add(Projections.property("per.id"), "perfilId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("u.id", usuarioId));

		criteria.addOrder(Order.asc("emp.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioEmpresa.class));

		return criteria.list();
	}

	public Collection<UsuarioEmpresa> findUsuarioResponsavelAreaOrganizacional(Collection<Long> areasIds) 
	{
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class, "ao");
		criteria.createCriteria("ao.responsavel", "r", Criteria.LEFT_JOIN);
		
		montaCriteriaFindUsuarioResponsavel(areasIds, criteria);
		
		return criteria.list();
	}

	public Collection<UsuarioEmpresa> findUsuarioCoResponsavelAreaOrganizacional(Collection<Long> areasIds)
	{
		Criteria criteria = getSession().createCriteria(AreaOrganizacional.class, "ao");
		criteria.createCriteria("ao.coResponsavel", "r", Criteria.LEFT_JOIN);
		
		montaCriteriaFindUsuarioResponsavel(areasIds, criteria);
		
		return criteria.list();
	}

	private void montaCriteriaFindUsuarioResponsavel(Collection<Long> areasIds, Criteria criteria)
	{
		criteria.createCriteria("r.usuario", "u", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("r.usuario")), "usuario");
		p.add(Projections.property("r.empresa"), "empresa");
		criteria.setProjection(p);

		criteria.add(Expression.in("ao.id", areasIds));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioEmpresa.class));
	}

	public Collection<UsuarioEmpresa> findPerfisEmpresas() 
	{
		Criteria criteria = getSession().createCriteria(UsuarioEmpresa.class, "ue");
		criteria.createCriteria("ue.perfil", "p", Criteria.LEFT_JOIN);
		criteria.createCriteria("ue.usuario", "u", Criteria.LEFT_JOIN);
		criteria.createCriteria("ue.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("u.id"), "usuarioId");
		p.add(Projections.property("u.nome"), "usuarioNome");
		p.add(Projections.property("p.nome"), "perfilNome");
		p.add(Projections.property("e.nome"), "empresaNome");
		criteria.setProjection(p);

		criteria.addOrder(Order.asc("u.nome"));
		criteria.addOrder(Order.asc("p.nome"));
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioEmpresa.class));
		
		return criteria.list();
	}

	public Collection<UsuarioEmpresa> findUsuariosAtivo(Collection<Long> usuarioIds, Long empresaId) 
	{
		//testar o in(usuarios) null
		Criteria criteria = getSession().createCriteria(UsuarioEmpresa.class, "ue");
		criteria.createCriteria("ue.usuario", "u", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("u.id"), "usuarioId");
		p.add(Projections.property("u.nome"), "usuarioNome");
		p.add(Projections.property("ue.id"), "id");
		p.add(Projections.property("ue.empresa.id"), "empresaId");
		criteria.setProjection(p);

		criteria.add(Expression.in("u.id", usuarioIds));
		criteria.add(Expression.eq("ue.empresa.id", empresaId));
		criteria.add(Expression.eq("u.acessoSistema", true));
		
		criteria.addOrder(Order.asc("u.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioEmpresa.class));
		
		return criteria.list();
	}

	public Collection<UsuarioEmpresa> findByColaboradorId(Long colaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.usuario", "u", Criteria.LEFT_JOIN);
		criteria.createCriteria("u.usuarioEmpresas", "ue", Criteria.LEFT_JOIN);
	
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ue.id"), "id");
		p.add(Projections.property("ue.usuario"), "usuario");
		p.add(Projections.property("ue.empresa"), "empresa");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", colaboradorId));
		criteria.add(Expression.eq("u.acessoSistema", true));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioEmpresa.class));
		
		return criteria.list();
	}
}