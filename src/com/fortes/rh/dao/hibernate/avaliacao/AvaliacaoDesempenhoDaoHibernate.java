package com.fortes.rh.dao.hibernate.avaliacao;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

@SuppressWarnings("unchecked")
public class AvaliacaoDesempenhoDaoHibernate extends GenericDaoHibernate<AvaliacaoDesempenho> implements AvaliacaoDesempenhoDao
{
	public Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa, Character tipoModeloAvaliacao) {
		
		Criteria criteria = getSession().createCriteria(AvaliacaoDesempenho.class, "a");
		criteria.createCriteria("a.avaliacao", "avaliacao", Criteria.LEFT_JOIN);
		criteria.createCriteria("a.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.inicio"), "inicio");
		p.add(Projections.property("a.fim"), "fim");
		p.add(Projections.property("a.titulo"), "titulo");
		p.add(Projections.property("a.liberada"), "liberada");
		p.add(Projections.property("avaliacao.id"), "projectionAvaliacaoId");
		p.add(Projections.property("avaliacao.titulo"), "projectionAvaliacaoTitulo");
		p.add(Projections.property("emp.id"), "empresaId");
		p.add(Projections.property("emp.nome"), "empresaNome");

		criteria.setProjection(p);

		if(tipoModeloAvaliacao != null)
			criteria.add(Expression.eq("avaliacao.tipoModeloAvaliacao", tipoModeloAvaliacao));			
	
		if(empresaId != null && empresaId > -1)
			criteria.add(Expression.eq("emp.id", empresaId));

		if(ativa != null)
			criteria.add(Expression.or(Expression.eq("avaliacao.ativo", ativa), Expression.isNull("avaliacao.id")));
		
		criteria.addOrder(Order.asc("a.titulo"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	public Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada, Long... empresasIds)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		criteria.createCriteria("cq.avaliacaoDesempenho", "ad");
		criteria.createCriteria("cq.avaliacao", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("ad.empresa", "e");
		
		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.distinct(Projections.property("ad.id")), "id");
		p.add(Projections.property("ad.titulo"), "titulo");
		p.add(Projections.property("ad.inicio"), "inicio");
		p.add(Projections.property("ad.fim"), "fim");
		p.add(Projections.property("e.nome"), "empresaNome");

		criteria.setProjection(p);
		
		if(avaliadorId != null)
			criteria.add(Expression.eq("cq.avaliador.id", avaliadorId));
		
		if(liberada != null)
			criteria.add(Expression.eq("ad.liberada", liberada));
		
		if(empresasIds != null && empresasIds.length > 0)
			criteria.add(Expression.in("ad.empresa.id", empresasIds));

		criteria.addOrder(Order.asc("ad.titulo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public AvaliacaoDesempenho findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"ad");
		criteria.createCriteria("ad.avaliacao", "avaliacao", Criteria.LEFT_JOIN);
		criteria.createCriteria("ad.empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ad.id"), "id");
		p.add(Projections.property("ad.inicio"), "inicio");
		p.add(Projections.property("ad.fim"), "fim");
		p.add(Projections.property("ad.titulo"), "titulo");
		p.add(Projections.property("ad.anonima"), "anonima");
		p.add(Projections.property("ad.liberada"), "liberada");
		p.add(Projections.property("ad.permiteAutoAvaliacao"), "permiteAutoAvaliacao");
		p.add(Projections.property("ad.exibeResultadoAutoAvaliacao"), "exibeResultadoAutoAvaliacao");
		p.add(Projections.property("ad.avaliacao.id"), "projectionAvaliacaoId");
		p.add(Projections.property("e.id"), "empresaId");
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
	
	public Integer findCountTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String tituloBusca, Long avaliacaoId, Boolean liberada) {
		Criteria criteria = geraCriteria(page, pagingSize, periodoInicial, periodoFinal, empresaId, tituloBusca, avaliacaoId, liberada);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.rowCount());
		criteria.setProjection(p);
		
		return (Integer) criteria.uniqueResult();
	}
	
	public Collection<AvaliacaoDesempenho> findTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String tituloBusca, Long avaliacaoId, Boolean liberada) {
		Criteria criteria = geraCriteria(page, pagingSize, periodoInicial, periodoFinal, empresaId, tituloBusca, avaliacaoId, liberada);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.inicio"), "inicio");
		p.add(Projections.property("a.fim"), "fim");
		p.add(Projections.property("a.titulo"), "titulo");
		p.add(Projections.property("a.liberada"), "liberada");
		p.add(Projections.property("avaliacao.id"), "projectionAvaliacaoId");
		p.add(Projections.property("avaliacao.titulo"), "projectionAvaliacaoTitulo");
		p.add(Projections.property("emp.id"), "empresaId");
		criteria.setProjection(p);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	private Criteria geraCriteria(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String tituloBusca, Long avaliacaoId, Boolean liberada) {
		Criteria criteria = getSession().createCriteria(AvaliacaoDesempenho.class, "a");
		criteria.createCriteria("a.avaliacao", "avaliacao", Criteria.LEFT_JOIN);
		criteria.createCriteria("a.empresa", "emp");

		if(empresaId != null)
			criteria.add(Expression.eq("emp.id", empresaId));
		
		if(tituloBusca != null && !tituloBusca.trim().equals(""))
			criteria.add(Expression.like("a.titulo", "%"+ tituloBusca +"%").ignoreCase() );
		
		if(avaliacaoId != null)
			criteria.add(Expression.eq("avaliacao.id", avaliacaoId));

		if(liberada != null)
			criteria.add(Expression.eq("a.liberada", liberada));

		if(periodoInicial != null && periodoFinal != null)
		{
			Disjunction disjunction = Expression.disjunction();
			disjunction.add(Expression.or(
								Expression.and(Expression.ge("a.inicio", periodoInicial), Expression.le("a.inicio", periodoFinal)),
								Expression.and(Expression.ge("a.fim", periodoInicial), Expression.le("a.fim", periodoFinal))
								)
							);
			disjunction.add(Expression.or(
								Expression.and(Expression.le("a.inicio", periodoInicial), Expression.ge("a.fim", periodoFinal)),
								Expression.and(Expression.le("a.inicio", periodoInicial), Expression.ge("a.fim", periodoFinal))
								)
							);
			criteria.add(disjunction);
		}
		
		if (pagingSize != null)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
			
			criteria.addOrder(Order.desc("a.inicio"));
			criteria.addOrder(Order.asc("a.titulo"));
		}
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria;
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

	public Collection<AvaliacaoDesempenho> findAvaliacaoDesempenhoBloqueadaComConfiguracaoCompetencia(Long configuracaoNivelCompetenciaFaixaSalarialId) {
		Criteria criteria = getSession().createCriteria(ConfiguracaoCompetenciaAvaliacaoDesempenho.class, "ccad");
		criteria.createCriteria("ccad.avaliacaoDesempenho", "av", Criteria.INNER_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("av.id"), "id");
		p.add(Projections.property("av.titulo"), "titulo");
		p.add(Projections.property("av.inicio"), "inicio");
		p.add(Projections.property("av.fim"), "fim");
		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.eq("av.liberada", false));
		criteria.add(Expression.eq("ccad.configuracaoNivelCompetenciaFaixaSalarial.id", configuracaoNivelCompetenciaFaixaSalarialId));
		
		criteria.addOrder(Order.asc("av.titulo"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AvaliacaoDesempenho.class));
		return criteria.list();
	}
}