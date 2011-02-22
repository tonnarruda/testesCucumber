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
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.model.geral.Estabelecimento;

public class EstabelecimentoDaoHibernate extends GenericDaoHibernate<Estabelecimento> implements EstabelecimentoDao
{
	/**
	 * @author Igo Coelho
	 * @param codigo do AC Pessoal
	 * @param id da Empresa do RH
	 * @since 09/06/2008
	 */
	public boolean remove(String codigo, Long id)
	{
		String hql = "delete from Estabelecimento e where e.codigoAC = :codigo and e.empresa.id = :id";
		Query query = getSession().createQuery(hql);
		query.setString("codigo", codigo);
		query.setLong("id", id);
		int result = query.executeUpdate();
		return result == 1;
	}

	/**
	 * @author Igo Coelho
	 * @param codigo do AC Pessoal
	 * @param codigo da Empresa no AC Pessoal
	 * @since 11/06/2008
	 */
	public Estabelecimento findByCodigo(String codigo, String empCodigo)
	{
		String hql = "select est from Estabelecimento est left join est.empresa emp where est.codigoAC = :codigo and emp.codigoAC = :empCodigo";
		Query query = getSession().createQuery(hql);
		query.setString("codigo", codigo);
		query.setString("empCodigo", empCodigo);
		return (Estabelecimento) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<Estabelecimento> findAllSelect(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");
		criteria.createCriteria("e.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("emp.nome"), "projectionEmpresaNome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.empresa.id", empresaId));

		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));

		return criteria.list();
	}

	public Estabelecimento findEstabelecimentoCodigoAc(Long estabelecimentoId)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.codigoAC"), "codigoAC");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", estabelecimentoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));

		return (Estabelecimento) criteria.uniqueResult();
	}

	public Collection<Estabelecimento> findEstabelecimentos(Long[] estabelecimentoIds)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.codigoAC"), "codigoAC");
		
		criteria.setProjection(p);
		
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			criteria.add(Expression.in("e.id", estabelecimentoIds));
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));
		
		return criteria.list();
	}

	public boolean verificaCnpjExiste(String complemento, Long id, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");
		criteria.createCriteria("e.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("e.id"), "id");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.complementoCnpj", complemento));
		criteria.add(Expression.eq("emp.id", empresaId));
		criteria.add(Expression.not(Expression.eq("e.id", id)));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));

		return !criteria.list().isEmpty();

	}

	public Estabelecimento findEstabelecimentoByCodigoAc(String estabelecimentoCodigoAC, String empresaCodigoAC)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");
		criteria.createCriteria("e.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");

		criteria.setProjection(p);
		criteria.add(Expression.eq("e.codigoAC", estabelecimentoCodigoAC));
		criteria.add(Expression.eq("emp.codigoAC", empresaCodigoAC));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));

		return (Estabelecimento) criteria.uniqueResult();
	}

	public Collection<Estabelecimento> findAllSelect(Long[] empresaIds)
	{
		Criteria criteria = getSession().createCriteria(Estabelecimento.class,"e");
		criteria.createCriteria("e.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("emp.nome"), "projectionEmpresaNome");

		criteria.setProjection(p);

		criteria.add(Expression.in("e.empresa.id", empresaIds));

		criteria.addOrder(Order.asc("emp.nome"));
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));

		return criteria.list();
	}
}