package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.ReajusteFaixaSalarialDao;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;

@Component
@SuppressWarnings("unchecked")
public class ReajusteFaixaSalarialDaoHibernate extends GenericDaoHibernate<ReajusteFaixaSalarial> implements ReajusteFaixaSalarialDao
{
	public Collection<ReajusteFaixaSalarial> findByTabelaReajusteColaboradorId(	Long tabelaReajusteColaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "rfs");
		criteria.createCriteria("rfs.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("rfs.id"), "id");
		p.add(Projections.property("rfs.valorAtual"), "valorAtual");
		p.add(Projections.property("rfs.valorProposto"), "valorProposto");
		p.add(Projections.property("fs.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");
		p.add(Projections.property("c.id"), "projectionCargoId");
		p.add(Projections.property("c.nome"), "projectionCargoNome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("rfs.tabelaReajusteColaborador.id", tabelaReajusteColaboradorId));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("fs.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ReajusteFaixaSalarial.class));
		
		return criteria.list();
	}

	public void updateValorProposto(Long reajusteFaixaSalarialId, Double valorProposto) 
	{
		String hql = "update ReajusteFaixaSalarial rfs set rfs.valorProposto = :valorProposto where rfs.id = :reajusteFaixaSalarialId";

		Query query = getSession().createQuery(hql);

		query.setLong("reajusteFaixaSalarialId", reajusteFaixaSalarialId);
		query.setDouble("valorProposto", valorProposto);

		query.executeUpdate();
	}

	public ReajusteFaixaSalarial findByIdProjection(Long id) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "rfs");
		criteria.createCriteria("rfs.tabelaReajusteColaborador", "trc");
		criteria.createCriteria("rfs.faixaSalarial", "fs");
		criteria.createCriteria("fs.cargo", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("rfs.id"), "id");
		p.add(Projections.property("rfs.valorAtual"), "valorAtual");
		p.add(Projections.property("rfs.valorProposto"), "valorProposto");
		p.add(Projections.property("trc.id"), "projectionTabelaReajusteColaboradorId");
		p.add(Projections.property("trc.nome"), "projectionTabelaReajusteColaboradorNome");
		p.add(Projections.property("fs.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");
		p.add(Projections.property("c.id"), "projectionCargoId");
		p.add(Projections.property("c.nome"), "projectionCargoNome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("rfs.id", id));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ReajusteFaixaSalarial.class));
		
		return (ReajusteFaixaSalarial) criteria.uniqueResult();
	}

	public boolean verificaPendenciasPorFaixa(Long faixaSalarialId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "rfs");
		criteria.createCriteria("rfs.tabelaReajusteColaborador", "trc");
		criteria.setProjection(Projections.rowCount());

		criteria.add(Expression.eq("rfs.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.eq("trc.aprovada", false));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Integer> result = criteria.list();
		
		return (Integer) result.get(0) > 0;
	}

	public Collection<ReajusteFaixaSalarial> findByTabelaReajusteCargoFaixa(Long tabelaReajusteId, Collection<Long> cargosIds, Collection<Long> faixaSalariaisIds) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "rfs");
		criteria.createCriteria("rfs.tabelaReajusteColaborador", "tr");
		criteria.createCriteria("rfs.faixaSalarial", "fs");
		criteria.createCriteria("fs.cargo", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("rfs.id"), "id");
		p.add(Projections.property("rfs.valorAtual"), "valorAtual");
		p.add(Projections.property("rfs.valorProposto"), "valorProposto");
		p.add(Projections.property("c.nome"), "projectionCargoNome");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("tr.id", tabelaReajusteId));
		
		if(faixaSalariaisIds != null && !faixaSalariaisIds.isEmpty())
			criteria.add(Expression.in("fs.id", faixaSalariaisIds));
		else if(cargosIds != null && !cargosIds.isEmpty())
			criteria.add(Expression.in("c.id", cargosIds));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public void removeByTabelaReajusteColaborador(Long tabelaReajusteColaboradorId) 
	{
		String hql = "delete from ReajusteFaixaSalarial where tabelaReajusteColaborador.id = :tabelaReajusteColaboradorId";
		Query query = getSession().createQuery(hql);
		query.setLong("tabelaReajusteColaboradorId", tabelaReajusteColaboradorId);
		query.executeUpdate();
	}
}