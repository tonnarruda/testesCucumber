package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.AnuncioDao;
import com.fortes.rh.model.captacao.Anuncio;

@SuppressWarnings("unchecked")
public class AnuncioDaoHibernate extends GenericDaoHibernate<Anuncio> implements AnuncioDao
{
	public Collection<Anuncio> findAnunciosSolicitacaoAberta(Long empresaIdExterno)
	{
		Criteria criteria = getSession().createCriteria(Anuncio.class, "a");
		criteria.createAlias("a.solicitacao", "s");

		criteria.add(Expression.eq("s.encerrada", false));
		criteria.add(Expression.eq("s.empresa.id", empresaIdExterno));
		criteria.add(Expression.eq("a.exibirModuloExterno", true ));
		

		criteria.addOrder(Order.asc("a.titulo"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

	public void removeBySolicitacao(Long solicitacaoId)
	{
		String hql = "delete Anuncio a where a.solicitacao.id = :solicitacaoId";

		Query query = getSession().createQuery(hql);

		query.setLong("solicitacaoId", solicitacaoId);
		query.executeUpdate();
	}

	public Anuncio findByIdProjection(Long anuncioId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "a");

		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.titulo"), "titulo");
		p.add(Projections.property("a.cabecalho"), "cabecalho");
		p.add(Projections.property("a.informacoes"), "informacoes");
		p.add(Projections.property("a.mostraConhecimento"), "mostraConhecimento");
		p.add(Projections.property("a.mostraBeneficio"), "mostraBeneficio");
		p.add(Projections.property("a.mostraSalario"), "mostraSalario");
		p.add(Projections.property("a.mostraCargo"), "mostraCargo");
		p.add(Projections.property("a.mostraSexo"), "mostraSexo");
		p.add(Projections.property("a.mostraIdade"), "mostraIdade");
		p.add(Projections.property("a.solicitacao.id"), "projectionSolicitacaoId");
		p.add(Projections.property("a.exibirModuloExterno"), "exibirModuloExterno");
		criteria.setProjection(p);

		criteria.add(Expression.eq("a.id", anuncioId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
	
		return (Anuncio) criteria.uniqueResult();
	}

	public Anuncio findBySolicitacao(Long solicitacaoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "a");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.titulo"), "titulo");
		p.add(Projections.property("a.cabecalho"), "cabecalho");
		p.add(Projections.property("a.informacoes"), "informacoes");
		p.add(Projections.property("a.mostraConhecimento"), "mostraConhecimento");
		p.add(Projections.property("a.mostraBeneficio"), "mostraBeneficio");
		p.add(Projections.property("a.mostraSalario"), "mostraSalario");
		p.add(Projections.property("a.mostraCargo"), "mostraCargo");
		p.add(Projections.property("a.mostraSexo"), "mostraSexo");
		p.add(Projections.property("a.mostraIdade"), "mostraIdade");
		p.add(Projections.property("a.solicitacao.id"), "projectionSolicitacaoId");
		p.add(Projections.property("a.exibirModuloExterno"), "exibirModuloExterno");
			
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("a.solicitacao.id", solicitacaoId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (Anuncio) criteria.uniqueResult();
	}
}