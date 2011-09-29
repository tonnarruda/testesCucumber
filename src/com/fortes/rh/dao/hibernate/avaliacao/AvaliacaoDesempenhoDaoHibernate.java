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
		p.add(Projections.property("emp.nome"), "projectionAvaliacaoEmpresaNome");

		criteria.setProjection(p);

		if(tipoModeloAvaliacao != null)
			criteria.add(Expression.eq("avaliacao.tipoModeloAvaliacao", tipoModeloAvaliacao));			
	
		if(empresaId != null && empresaId > -1)
			criteria.add(Expression.eq("emp.id", empresaId));

		if(ativa != null)
			criteria.add(Expression.eq("avaliacao.ativo", ativa));
		
		criteria.addOrder(Order.asc("a.titulo"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		criteria.createCriteria("cq.avaliacaoDesempenho", "ad");
		criteria.createCriteria("cq.avaliacao", "a");
		criteria.createCriteria("a.empresa", "e");
		
		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.distinct(Projections.property("ad.id")), "id");
		p.add(Projections.property("ad.titulo"), "titulo");
		p.add(Projections.property("ad.inicio"), "inicio");
		p.add(Projections.property("ad.fim"), "fim");
		p.add(Projections.property("e.nome"), "empresaNomeProjection");

		criteria.setProjection(p);
		
		if(avaliadorId != null)
			criteria.add(Expression.eq("cq.avaliador.id", avaliadorId));
		
		if(liberada != null)
			criteria.add(Expression.eq("ad.liberada", liberada));
		
		if(empresaId != null && !empresaId.equals(-1L))
			criteria.add(Expression.eq("a.empresa.id", empresaId));

		criteria.addOrder(Order.asc("ad.titulo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public AvaliacaoDesempenho findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"ad");
		criteria.createCriteria("ad.avaliacao", "avaliacao");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ad.id"), "id");
		p.add(Projections.property("ad.inicio"), "inicio");
		p.add(Projections.property("ad.fim"), "fim");
		p.add(Projections.property("ad.titulo"), "titulo");
		p.add(Projections.property("ad.anonima"), "anonima");
		p.add(Projections.property("ad.liberada"), "liberada");
		p.add(Projections.property("ad.permiteAutoAvaliacao"), "permiteAutoAvaliacao");
		p.add(Projections.property("ad.avaliacao.id"), "projectionAvaliacaoId");
		p.add(Projections.property("avaliacao.empresa.id"), "projectionAvaliacaoEmpresaId");
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
	
	public Collection<AvaliacaoDesempenho> findTituloModeloAvaliacao(Long empresaId, String tituloBusca, Long avaliacaoId) {
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

		if(empresaId != null)
			criteria.add(Expression.eq("emp.id", empresaId));
		
		if(tituloBusca != null && !tituloBusca.trim().equals(""))
			criteria.add(Expression.like("a.titulo", "%"+ tituloBusca +"%").ignoreCase() );
		
		if(avaliacaoId != null)
			criteria.add(Expression.eq("avaliacao.id", avaliacaoId));

		criteria.addOrder(Order.asc("a.titulo"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<AvaliacaoDesempenho> findIdsAvaliacaoDesempenho(Long avaliacaoId) 
	{
		Criteria criteria = getSession().createCriteria(AvaliacaoDesempenho.class, "ad");
		criteria.createCriteria("ad.avaliacao", "a", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ad.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("a.id", avaliacaoId));
		criteria.addOrder(Order.asc("ad.id"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
}