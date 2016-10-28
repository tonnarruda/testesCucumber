package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.ReajusteIndiceDao;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.ReajusteIndice;
import com.fortes.rh.model.geral.Empresa;

@Component
@SuppressWarnings("unchecked")
public class ReajusteIndiceDaoHibernate extends GenericDaoHibernate<ReajusteIndice> implements ReajusteIndiceDao
{
	public Collection<Indice> findPendentes(Empresa empresa) 
	{
		Criteria criteria = getSession().createCriteria(Indice.class, "i");
		criteria.createCriteria("i.reajusteIndices", "ri");
		criteria.createCriteria("ri.tabelaReajusteColaborador", "trc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("i.id"), "id");
		p.add(Projections.property("i.nome"), "nome");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("trc.aprovada", false));
		
		if (empresa.isAcIntegra())
			criteria.add(Expression.eq("i.grupoAC", empresa.getGrupoAC()));

		criteria.addOrder(Order.asc("i.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Indice.class));
		
		return criteria.list();
	}

	public Collection<ReajusteIndice> findByTabelaReajusteColaboradorId(Long tabelaReajusteColaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ri");
		criteria.createCriteria("ri.indice", "i", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ri.id"), "id");
		p.add(Projections.property("ri.valorAtual"), "valorAtual");
		p.add(Projections.property("ri.valorProposto"), "valorProposto");
		p.add(Projections.property("i.id"), "projectionIndiceId");
		p.add(Projections.property("i.nome"), "projectionIndiceNome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("ri.tabelaReajusteColaborador.id", tabelaReajusteColaboradorId));
		
		criteria.addOrder(Order.asc("i.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public ReajusteIndice findByIdProjection(Long reajusteIndiceId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ri");
		criteria.createCriteria("ri.indice", "i");
		criteria.createCriteria("ri.tabelaReajusteColaborador", "trc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ri.id"), "id");
		p.add(Projections.property("ri.valorAtual"), "valorAtual");
		p.add(Projections.property("ri.valorProposto"), "valorProposto");
		p.add(Projections.property("i.id"), "projectionIndiceId");
		p.add(Projections.property("i.nome"), "projectionIndiceNome");
		p.add(Projections.property("trc.id"), "projectionTabelaReajusteColaboradorId");
		p.add(Projections.property("trc.nome"), "projectionTabelaReajusteColaboradorNome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("ri.id", reajusteIndiceId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (ReajusteIndice) criteria.uniqueResult();
	}

	public void updateValorProposto(Long reajusteIndiceId, Double valorProposto) 
	{
		String hql = "update ReajusteIndice ri set ri.valorProposto = :valorProposto where ri.id = :reajusteIndiceId";

		Query query = getSession().createQuery(hql);

		query.setLong("reajusteIndiceId", reajusteIndiceId);
		query.setDouble("valorProposto", valorProposto);

		query.executeUpdate();
	}

	public Collection<ReajusteIndice> findByTabelaReajusteIndice(Long tabelaReajusteId, Collection<Long> indicesIds) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ri");
		criteria.createCriteria("ri.indice", "i");
		criteria.createCriteria("ri.tabelaReajusteColaborador", "trc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ri.id"), "id");
		p.add(Projections.property("ri.valorAtual"), "valorAtual");
		p.add(Projections.property("ri.valorProposto"), "valorProposto");
		p.add(Projections.property("i.nome"), "projectionIndiceNome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("trc.id", tabelaReajusteId));
		
		if(indicesIds != null && !indicesIds.isEmpty())
			criteria.add(Expression.in("i.id", indicesIds));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public void removeByTabelaReajusteColaborador(Long tabelaReajusteColaboradorId) 
	{
		String hql = "delete from ReajusteIndice where tabelaReajusteColaborador.id = :tabelaReajusteColaboradorId";
		Query query = getSession().createQuery(hql);
		query.setLong("tabelaReajusteColaboradorId", tabelaReajusteColaboradorId);
		query.executeUpdate();
	}
}