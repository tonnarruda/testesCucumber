/* Autor: Igo Coelho
 * Data: 29/05/2006
 * Requisito: RFA32 */
package com.fortes.rh.dao.hibernate.acesso;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.CollectionUtil;

@SuppressWarnings("unchecked")
public class UsuarioDaoHibernate extends GenericDaoHibernate<Usuario> implements UsuarioDao
{
	public Usuario findByIdProjection(Long usuarioId)
	{
		Criteria criteria = getSession().createCriteria(Usuario.class, "u");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("u.id"), "id");
		p.add(Projections.property("u.login"), "login");
		p.add(Projections.property("u.senha"), "senha");
		p.add(Projections.property("u.acessoSistema"), "acessoSistema");
		p.add(Projections.property("u.caixasMensagens"), "caixasMensagens");

		criteria.setProjection(p);

		criteria.add(Expression.eq("u.id", usuarioId));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Usuario.class));
		
		return (Usuario) criteria.uniqueResult();		
	}
	
	public Usuario findByLogin(String login)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Usuario(usuario.id, usuario.nome, usuario.login, usuario.senha, usuario.ultimoLogin, usuario.acessoSistema, usuario.superAdmin, colaborador.id, colaborador.nome, colaborador.nomeComercial) ");
		hql.append("from Colaborador as colaborador ");
		hql.append("right join colaborador.usuario as usuario ");
		hql.append("where usuario.login = :login ");

		Query query = getSession().createQuery(hql.toString());
		query.setString("login", login);

		return (Usuario) query.uniqueResult();

	}

	public Collection<Usuario> findUsuarios(int page, int pagingSize,String nomeBusca,Empresa empresa)
	{
		Criteria criteria = getSession().createCriteria(UsuarioEmpresa.class, "usuEmp");
		criteria.createCriteria("usuEmp.usuario","usu");


		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("usu.id"), "id");
		p.add(Projections.property("usu.nome"), "nome");
		p.add(Projections.property("usu.acessoSistema"), "acessoSistema");
		p.add(Projections.property("usu.login"), "login");

		p.add(Projections.groupProperty("usu.id"));
		p.add(Projections.groupProperty("usu.nome"));
		p.add(Projections.groupProperty("usu.acessoSistema"));
		p.add(Projections.groupProperty("usu.login"));

		criteria.setProjection(p);

		// Nome
		if(StringUtils.isNotBlank(nomeBusca))
			criteria.add(Expression.like("usu.nome", "%"+ nomeBusca +"%").ignoreCase() );

		if(empresa != null && empresa.getId() != null)
			criteria.add(Expression.eq("usuEmp.empresa.id", empresa.getId()));

		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);

		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);

		criteria.addOrder(Order.asc("usu.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Usuario.class));
		return criteria.list();
	}

	public Integer getCountUsuario(String nomeBusca, Empresa empresa)
	{
		Criteria criteria = getSession().createCriteria(UsuarioEmpresa.class, "usuEmp");
		criteria.createCriteria("usuEmp.usuario","usu");

		// agrupa para retirar "duplicações" (usuários que possuem perfil em mais de uma empresa)
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.groupProperty("usu.id"));
		p.add(Projections.groupProperty("usu.nome"));
		p.add(Projections.groupProperty("usu.acessoSistema"));
		p.add(Projections.groupProperty("usu.login"));

		criteria.setProjection(p);

		getCountFiltro(nomeBusca, criteria, empresa);

		return (Integer) criteria.list().size();
	}

	private void getCountFiltro(String nomeBusca, Criteria criteria, Empresa empresa)
	{
		//Nome
		if(nomeBusca != null && !nomeBusca.trim().equals(""))
			criteria.add(Expression.like("usu.nome", "%"+ nomeBusca +"%").ignoreCase() );

		if(empresa != null && empresa.getId() != null)
			criteria.add(Expression.eq("usuEmp.empresa.id", empresa.getId()));
	}

	public Collection<Usuario> findAllSelect()
	{
		Criteria criteria = getSession().createCriteria(Usuario.class, "u");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("u.id"), "id");
		p.add(Projections.property("u.nome"), "nome");

		criteria.setProjection(p);

		criteria.addOrder(Order.asc("u.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Usuario.class));

		return criteria.list();
	}
	
	public Collection<Usuario> findAllSelect(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(UsuarioEmpresa.class, "ue");
		criteria.createCriteria("ue.usuario","u");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("u.id"), "id");
		p.add(Projections.property("u.nome"), "nome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("u.acessoSistema", true));
		criteria.add(Expression.eq("ue.empresa.id", empresaId));
		criteria.addOrder(Order.asc("u.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Usuario.class));
		
		return criteria.list();
	}

	public Usuario findByLogin(Usuario usuario)
	{
		Criteria criteria = getSession().createCriteria(Usuario.class, "u");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("u.id"), "id");
		p.add(Projections.property("u.nome"), "nome");
		p.add(Projections.property("u.login"), "login");

		criteria.setProjection(p);

		criteria.add(Expression.eq("u.login", usuario.getLogin()));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Usuario.class));

		return (Usuario) criteria.uniqueResult();
	}

	public void desativaAcessoSistema(Long colaboradorId)
	{
		String hql = "update Usuario u set u.acessoSistema = :acessoSistema, u.login = null, u.senha = null where u.id = (select c.usuario.id from Colaborador  c where c.id = :colaboradorId)";

		Query query = getSession().createQuery(hql);

		query.setLong("colaboradorId", colaboradorId);
		query.setBoolean("acessoSistema", false);

		query.executeUpdate();
	}
	
	public void reativaAcessoSistema(Long colaboradorId)
	{
		String hql = "update Usuario u set u.acessoSistema = :acessoSistema where u.id = (select c.usuario.id from Colaborador  c where c.id = :colaboradorId)";

		Query query = getSession().createQuery(hql);

		query.setLong("colaboradorId", colaboradorId);
		query.setBoolean("acessoSistema", true);

		query.executeUpdate();
	}

	public void setUltimoLogin(Long id) {
		String hql = "update Usuario u set u.ultimoLogin = :ultimoLogin where u.id = :id";

		Query query = getSession().createQuery(hql);

		query.setLong("id", id);
		query.setTimestamp("ultimoLogin", new Date());

		query.executeUpdate();
		
	}

	public void desativaSuperAdmin() 
	{
		String hql = "update Usuario set superadmin = false where superadmin = true";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public String[] findEmailsByUsuario(Long[] usuariosIds)
	{
		Criteria criteria = getSession().createCriteria(Colaborador.class, "c");
		criteria.createCriteria("c.usuario", "u");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.contato.email"), "emailColaborador");

		criteria.setProjection(p);
		criteria.add(Expression.in("u.id", usuariosIds));
		
		criteria.addOrder(Order.asc("c.contato.email"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return new CollectionUtil<String>().convertCollectionToArrayString(criteria.list());
	}

	public void updateConfiguracoesMensagens(Long usuarioId, String caixasMensagens) 
	{
		String hql = "update Usuario set caixasMensagens = :caixasMensagens where id = :usuarioId";
		Query query = getSession().createQuery(hql);
		query.setLong("usuarioId", usuarioId);
		query.setString("caixasMensagens", caixasMensagens);
		
		query.executeUpdate();
	}

	public Collection<Empresa> findEmpresas(String usuarioNome) 
	{
		Criteria criteria = getSession().createCriteria(Empresa.class, "e");
		criteria.createCriteria("e.usuarioEmpresas","ue");
		criteria.createCriteria("ue.usuario","u");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("u.acessoSistema", true));
		if (!usuarioNome.equals("fortes"))
			criteria.add(Expression.eq("u.login", usuarioNome));
		criteria.addOrder(Order.asc("e.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Empresa.class));
		
		return criteria.list();
	}
}