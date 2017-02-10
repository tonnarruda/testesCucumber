package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

@SuppressWarnings("unchecked")
@Component
public class ColaboradorAvaliacaoPraticaDaoHibernate extends GenericDaoHibernate<ColaboradorAvaliacaoPratica> implements ColaboradorAvaliacaoPraticaDao
{
	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId, Long colaboradorCertificacaoId, Long avaliacaoPraticaId, Boolean ordenarPorDataAscOuDesc, Boolean colabCertificacaoIsNull) 
	{
		Criteria criteria = getSession().createCriteria(ColaboradorAvaliacaoPratica.class, "cap");
		criteria.createCriteria("cap.avaliacaoPratica", "ap", Criteria.INNER_JOIN);
		
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
		criteria.setProjection(p);

		criteria.add(Expression.eq("cap.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("cap.certificacao.id",certificacaoId));
		
		if(colaboradorCertificacaoId != null)
			criteria.add(Expression.eq("cap.colaboradorCertificacao.id", colaboradorCertificacaoId));
		else if (colabCertificacaoIsNull)
			criteria.add(Expression.isNull("cap.colaboradorCertificacao.id"));
		
		if(avaliacaoPraticaId != null)
			criteria.add(Expression.eq("cap.avaliacaoPratica.id", avaliacaoPraticaId));
		
		if(ordenarPorDataAscOuDesc != null){
			if(ordenarPorDataAscOuDesc)
				criteria.addOrder(Order.asc("cap.data"));
			else
				criteria.addOrder(Order.desc("cap.data"));
		}
		
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

	public Collection<ColaboradorAvaliacaoPratica> findByCertificacaoIdAndColaboradoresIds(Long certificacaoId, Long[] colaboradoresIds) {
		Criteria criteria = getSession().createCriteria(ColaboradorAvaliacaoPratica.class, "cap");
		criteria.createCriteria("cap.avaliacaoPratica", "ap", Criteria.INNER_JOIN);
		criteria.createCriteria("cap.colaboradorCertificacao", "cc", Criteria.LEFT_JOIN);
		
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
		p.add(Projections.property("cc.id"), "colaboradorCertificacaoId");
		criteria.setProjection(Projections.distinct(p));
		
		criteria.add(Expression.eq("cap.certificacao.id",certificacaoId));
		criteria.add(Expression.in("cap.colaborador.id", colaboradoresIds));

	    criteria.addOrder(Order.asc("cap.colaborador.id"));
	    criteria.addOrder(Order.desc("cap.data"));
	    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	    criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorAvaliacaoPratica.class));

		return criteria.list();
	}

	public void setColaboradorCertificacoNull(Long[] colaboradorCertificacoesIds) {
		Query query = getSession().createQuery("update ColaboradorAvaliacaoPratica set colaboradorCertificacao_id = null where colaboradorcertificacao_id in (:colaboradorCertificacoesIds) ");
		query.setParameterList("colaboradorCertificacoesIds", colaboradorCertificacoesIds, StandardBasicTypes.LONG);
		query.executeUpdate();
	}

	public void removeByCertificacaoId(Long certificacaoId) {
		String hql = "delete from ColaboradorAvaliacaoPratica cap where cap.certificacao.id = :certificacaoId ";
		Query query = getSession().createQuery(hql);
		query.setLong("certificacaoId", certificacaoId);
		query.executeUpdate();
	}
}
