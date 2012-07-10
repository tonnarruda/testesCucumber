package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ComissaoMembroDao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;

@SuppressWarnings("unchecked")
public class ComissaoMembroDaoHibernate extends GenericDaoHibernate<ComissaoMembro> implements ComissaoMembroDao
{
	public Collection<ComissaoMembro> findByComissaoPeriodo(Long[] comissaoPeriodoIds)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria = criteria.createCriteria("c.colaborador", "colab", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("colab.nome"), "projectionColaboradorNome");
		p.add(Projections.property("colab.id"), "projectionColaboradorId");
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.funcao"), "funcao");
		p.add(Projections.property("c.tipo"), "tipo");
		p.add(Projections.property("c.comissaoPeriodo.id"), "projectionComissaoPeriodoId");
		p.add(Projections.property("c.colaborador.id"), "projectionColaboradorId");
		criteria.setProjection(p);

		criteria.add(Expression.in("c.comissaoPeriodo.id", comissaoPeriodoIds));

		criteria.addOrder(Order.asc("c.comissaoPeriodo.id"));
		criteria.addOrder(Order.asc("colab.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public void updateFuncaoETipo(Long id, String funcao, String tipo)
	{
		String hql = "update ComissaoMembro set funcao = :funcao, tipo = :tipo where id = :id";

		Query query = getSession().createQuery(hql);
		query.setLong("id", id);
		query.setString("funcao", funcao);
		query.setString("tipo", tipo);

		query.executeUpdate();
	}

	public void removeByComissaoPeriodo(Long[] comissaoPeriodoIds)
	{
		if (comissaoPeriodoIds != null && comissaoPeriodoIds.length >0)
		{
			String hql = "delete from ComissaoMembro c where c.comissaoPeriodo.id in (:comissaoPeriodoId)";
			Query query = getSession().createQuery(hql);
			query.setParameterList("comissaoPeriodoId",comissaoPeriodoIds,Hibernate.LONG);
			query.executeUpdate();
		}
	}

	public Collection<Colaborador> findColaboradorByComissao(Long comissaoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cm");
		criteria.createCriteria("cm.colaborador", "co");
		criteria.createCriteria("cm.comissaoPeriodo", "cp");
		criteria.createCriteria("cp.comissao", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("co.id")), "id");
		p.add(Projections.property("co.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", comissaoId));
		criteria.addOrder(Order.asc("co.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	public Collection<ComissaoMembro> findDistinctByComissaoPeriodo(Long comissaoPeriodoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cm");
		criteria.createCriteria("cm.colaborador", "co");
		criteria.createCriteria("cm.comissaoPeriodo", "cp");
		criteria.createCriteria("cp.comissao", "c");

		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.distinct(Projections.property("co.id")), "id");
		
		p.add(Projections.property("co.id"), "projectionColaboradorId");
		p.add(Projections.property("co.nome"), "projectionColaboradorNome");
		p.add(Projections.property("cm.tipo"), "tipo");
		p.add(Projections.property("cm.funcao"), "funcao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("cp.id", comissaoPeriodoId));
		
		criteria.addOrder(Order.asc("co.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ComissaoMembro.class));
		return criteria.list();
	}

	public Collection<ComissaoMembro> findByComissao(Long comissaoId, String tipoMembroComissao) {
		
		StringBuilder hql = new StringBuilder("select new ComissaoMembro(colaborador.nome, colaborador.nomeComercial, cm.tipo, cm.funcao) ");
		hql.append("from ComissaoMembro cm ");
		hql.append("join cm.colaborador colaborador ");
		hql.append("join cm.comissaoPeriodo cp ");
		hql.append("join cp.comissao comissao ");
		hql.append("where comissao.id = :comissaoId ");
		hql.append("and cp.aPartirDe = (select max(cp2.aPartirDe) from ComissaoPeriodo cp2 ");
		hql.append("				where cp2.comissao.id = comissao.id ");
		hql.append("				and cp2.aPartirDe <= :hoje ) ");
		
		if (StringUtils.isNotBlank(tipoMembroComissao))
			hql.append("and cm.tipo like :tipoMembroComissao ");
		
		hql.append("order by cm.tipo, colaborador.nome");
		
		Query query = getSession().createQuery(hql.toString());

		if (StringUtils.isNotBlank(tipoMembroComissao))
			query.setString("tipoMembroComissao", tipoMembroComissao);
		
		query.setLong("comissaoId", comissaoId);
		query.setDate("hoje", new Date());
		
		return query.list();
	}
	
	
	public Comissao findComissaoByColaborador(Long colaboradorId) {
		
		StringBuilder hql = new StringBuilder("select new Comissao(comissao.dataIni, comissao.dataFim) ");
		hql.append("from ComissaoMembro cm ");
		hql.append("join cm.comissaoPeriodo cp ");
		hql.append("join cp.comissao comissao ");
		hql.append("where cm.colaborador.id = :colaboradorId ");
		hql.append("and cp.aPartirDe = (select max(cp2.aPartirDe) from ComissaoPeriodo cp2 ");
		hql.append("				where cp2.comissao.id = comissao.id ");
		hql.append("				and cp2.aPartirDe <= :hoje ) ");
		
		Query query = getSession().createQuery(hql.toString());

		query.setLong("colaboradorId", colaboradorId);
		query.setDate("hoje", new Date());
		
		return (Comissao) query.uniqueResult();
	}
	

	public Collection<ComissaoMembro> findByColaborador(Long colaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria = criteria.createCriteria("c.colaborador", "colab");
		criteria = criteria.createCriteria("c.comissaoPeriodo", "cp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("colab.nome"), "projectionColaboradorNome");
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.funcao"), "funcao");
		p.add(Projections.property("c.tipo"), "tipo");
		p.add(Projections.property("cp.aPartirDe"), "projectionComissaoPeriodoAPartirDe");
		p.add(Projections.property("c.colaborador.id"), "projectionColaboradorId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("colab.id", colaboradorId));

		criteria.addOrder(Order.asc("cp.aPartirDe"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<Colaborador> findColaboradoresNaComissao(Long comissaoId, Collection<Long> colaboradorIds) 
	{
		StringBuilder hql = new StringBuilder("select distinct new Colaborador(co.nome, co.id) ");
		hql.append("from ComissaoMembro cm ");
		hql.append("left join cm.comissaoPeriodo cp ");
		hql.append("left join cm.colaborador co ");
		hql.append("where cm.colaborador.id in (:colaboradorId) ");
		hql.append("and cp.comissao.id = :comissaoId ");
		hql.append("and cp.aPartirDe = (select max(cp2.aPartirDe) from ComissaoPeriodo cp2 ");
		hql.append("				where cp2.comissao.id = :comissaoId )");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("comissaoId", comissaoId);
		query.setParameterList("colaboradorId", colaboradorIds, Hibernate.LONG);
		
		return query.list();
	}
}