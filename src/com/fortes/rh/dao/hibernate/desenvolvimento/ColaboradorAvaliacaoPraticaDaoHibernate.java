package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

@SuppressWarnings("unchecked")
public class ColaboradorAvaliacaoPraticaDaoHibernate extends GenericDaoHibernate<ColaboradorAvaliacaoPratica> implements ColaboradorAvaliacaoPraticaDao
{
	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId, Long colaboradorCertificacaoId) 
	{
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cap.id"), "id");
		p.add(Projections.property("cap.data"), "data");
		p.add(Projections.property("cap.nota"), "nota");
		p.add(Projections.property("cap.colaboradorCertificacao.id"), "colaboradorCertificacaoId");
		p.add(Projections.property("cap.certificacao.id"), "certificacaoId");
		p.add(Projections.property("cap.colaborador.id"), "colaboradorId");
		p.add(Projections.property("ap.id"), "avaliacaoPraticaId");
		p.add(Projections.property("ap.notaMinima"), "avaliacaoPraticaNotaMinima");
		p.add(Projections.property("ap.titulo"), "avaliacaoPraticaTitulo");
		
		Criteria criteria = getSession().createCriteria(ColaboradorAvaliacaoPratica.class, "cap");
		criteria.createCriteria("cap.avaliacaoPratica", "ap", Criteria.INNER_JOIN)
		.add(Expression.eq("cap.colaborador.id", colaboradorId))
		.add(Expression.eq("cap.certificacao.id",certificacaoId));
		
		if(colaboradorCertificacaoId != null)
			criteria.add(Expression.eq("cap.colaboradorCertificacao.id", colaboradorCertificacaoId));
		else
			criteria.add(Expression.isNull("cap.colaboradorCertificacao.id"));
		
		criteria.setProjection(p);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorAvaliacaoPratica.class));
		return criteria.list();
	}

	public Collection<ColaboradorAvaliacaoPratica> findColaboradorAvaliacaoPraticaQueNaoEstaCertificado(Long colaboradorId, Long certificacaoId) {
		Criteria criteria = getSession().createCriteria(ColaboradorAvaliacaoPratica.class, "cap");
		criteria.add(Expression.eq("colaborador.id", colaboradorId))
		.add(Expression.eq("certificacao.id",certificacaoId))
		.add(Expression.isNull("colaboradorCertificacao.id"))
		.addOrder(Order.desc("cap.data"));
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cap.id"), "id");
		p.add(Projections.property("cap.data"), "data");
		p.add(Projections.property("cap.nota"), "nota");
		p.add(Projections.property("cap.avaliacaoPratica.id"), "avaliacaoPraticaId");
		p.add(Projections.property("cap.certificacao.id"), "certificacaoId");
		p.add(Projections.property("cap.colaborador.id"), "colaboradorId");

		criteria.setProjection(p);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorAvaliacaoPratica.class));
		return criteria.list();
	}

	public void removeColaboradorAvaliacaoPraticaByColaboradorCertificacaoId(Long colaboradorCertificacaoId) {
		String hql = "delete from ColaboradorAvaliacaoPratica cap where cap.colaboradorCertificacao.id = :colaboradorCertificacaoId ";
		
		Query query = getSession().createQuery(hql);
		query.setLong("colaboradorCertificacaoId", colaboradorCertificacaoId);
		query.executeUpdate();		
	}
}
