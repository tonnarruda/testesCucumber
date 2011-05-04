package com.fortes.rh.dao.hibernate.avaliacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

public class AvaliacaoDesempenhoDaoHibernate extends GenericDaoHibernate<AvaliacaoDesempenho> implements AvaliacaoDesempenhoDao
{
	@SuppressWarnings("unchecked")
	public Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa, Character tipoModeloAvaliacao) {
		
		Criteria criteria = getSession().createCriteria(AvaliacaoDesempenho.class, "a");
		criteria.createCriteria("a.avaliacao", "avaliacao");
		criteria.createCriteria("avaliacao.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.inicio"), "inicio");
		p.add(Projections.property("a.fim"), "fim");
		p.add(Projections.property("a.titulo"), "titulo");
		p.add(Projections.property("a.liberada"), "liberada");
		p.add(Projections.property("avaliacao.id"), "projectionAvaliacaoId");
		p.add(Projections.property("avaliacao.titulo"), "projectionAvaliacaoTitulo");
		p.add(Projections.property("emp.id"), "projectionAvaliacaoEmpresaId");

		criteria.setProjection(p);

		if(tipoModeloAvaliacao != null)
			criteria.add(Expression.eq("avaliacao.tipoModeloAvaliacao", tipoModeloAvaliacao));			
	
		if(empresaId != null)
			criteria.add(Expression.eq("emp.id", empresaId));

		if(ativa != null)
			criteria.add(Expression.eq("avaliacao.ativo", ativa));
		
		criteria.addOrder(Order.asc("a.titulo"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		criteria.createCriteria("cq.avaliacaoDesempenho", "ad");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.distinct(Projections.property("ad.id")), "id");
		p.add(Projections.property("ad.titulo"), "titulo");
		p.add(Projections.property("ad.inicio"), "inicio");
		p.add(Projections.property("ad.fim"), "fim");
		
		criteria.setProjection(p);
		
		if(avaliadorId != null)
			criteria.add(Expression.eq("cq.avaliador.id", avaliadorId));
		
		if(liberada != null)
			criteria.add(Expression.eq("ad.liberada", liberada));

		criteria.addOrder(Order.asc("ad.titulo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public AvaliacaoDesempenho findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"ad");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ad.id"), "id");
		p.add(Projections.property("ad.inicio"), "inicio");
		p.add(Projections.property("ad.fim"), "fim");
		p.add(Projections.property("ad.titulo"), "titulo");
		p.add(Projections.property("ad.anonima"), "anonima");
		p.add(Projections.property("ad.liberada"), "liberada");
		p.add(Projections.property("ad.permiteAutoAvaliacao"), "permiteAutoAvaliacao");
		p.add(Projections.property("ad.avaliacao.id"), "projectionAvaliacaoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("ad.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return (AvaliacaoDesempenho) criteria.uniqueResult();
	}

	public void liberarOrBloquear(Long id, boolean liberar)
	{
		String hql = "update AvaliacaoDesempenho ad  set ad.liberada = :liberada where ad.id = :id"; 

		Query query = getSession().createQuery(hql);
		query.setLong("id", id);
		query.setBoolean("liberada", liberar);
		
		query.executeUpdate();
	}
}