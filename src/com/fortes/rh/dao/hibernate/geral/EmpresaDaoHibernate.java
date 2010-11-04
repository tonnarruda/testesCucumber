/* Autor: Bruno Bachiega
 * Data: 6/06/2006
 * Requisito: RFA003 */
package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

@SuppressWarnings("unchecked")
public class EmpresaDaoHibernate extends GenericDaoHibernate<Empresa> implements EmpresaDao
{
	public Empresa findByCodigo(String codigo)
	{
		Query query = getSession().createQuery("from Empresa e where e.codigoAC = :codigo");
		query.setString("codigo", codigo);
		return (Empresa) query.uniqueResult();
	}

	public boolean getIntegracaoAC(Long id)
	{
		Query query = getSession().createQuery("select e.acIntegra from Empresa e where e.id = :id");
		query.setLong("id", id);
		return (Boolean) query.uniqueResult();
	}

	public boolean findExibirSalarioById(Long empresaId)
	{
		Query query = getSession().createQuery("select e.exibirSalario from Empresa e where e.id = :id");
		query.setLong("id", empresaId);
		return (Boolean) query.uniqueResult();
	}

	public Collection<Empresa> verifyExistsCnpj(String cnpj)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("e.cnpj", cnpj));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public String findCidade(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"e");
		criteria.createCriteria("e.cidade", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.nome"), "projectionCidadeNome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("e.id", id));
		
		return (String) criteria.uniqueResult();
	}
	
	public Collection<Empresa> findDistinctEmpresaByQuestionario(Long questionarioId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		criteria.createCriteria("cq.questionario", "q");
		criteria.createCriteria("cq.colaborador", "c");
		criteria.createCriteria("c.empresa", "emp");
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("emp.id"),"id");
		p.add(Projections.property("emp.nome"),"nome");
		
		criteria.add(Expression.eq("q.id", questionarioId));
		
		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("emp.nome"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public Empresa findByIdProjection(Long id) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.acIntegra"), "acIntegra");
		p.add(Projections.property("e.mensagemModuloExterno"), "mensagemModuloExterno");
		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return (Empresa) criteria.uniqueResult();
	}

	public Collection<Empresa> findByUsuarioPermissao(Long usuarioId, String role)
	{
		Criteria criteria = getSession().createCriteria(UsuarioEmpresa.class, "ue");
		criteria.createCriteria("ue.empresa", "e");
		criteria.createCriteria("ue.usuario", "u");
		criteria.createCriteria("ue.perfil", "p");
		criteria.createCriteria("p.papeis", "papel");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"),"nome");
		criteria.setProjection(Projections.distinct(p));
		
		criteria.add(Expression.eq("u.id", usuarioId));
		criteria.add(Expression.eq("papel.codigo", role));
		criteria.add(Expression.eq("u.acessoSistema", true));
		
		criteria.addOrder(Order.asc("e.nome"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}
}