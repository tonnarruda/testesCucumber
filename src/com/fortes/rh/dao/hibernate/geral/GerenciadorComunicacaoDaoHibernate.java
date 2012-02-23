package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.model.geral.GerenciadorComunicacao;

@SuppressWarnings("unchecked")
public class GerenciadorComunicacaoDaoHibernate extends GenericDaoHibernate<GerenciadorComunicacao> implements GerenciadorComunicacaoDao
{
	public Collection<GerenciadorComunicacao> findByOperacaoId(Integer operacaoId, Long empresaId) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "gc");
		
		criteria.add(Expression.eq("gc.operacao", operacaoId));
		criteria.add(Expression.eq("gc.empresa.id", empresaId));
		
		return criteria.list();	
	}

	public boolean verifyExists(GerenciadorComunicacao gerenciadorComunicacao){
		Criteria criteria = getSession().createCriteria(getEntityClass(), "gc");
	
		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.id());

		criteria.setProjection(p);

		if (gerenciadorComunicacao.getId() != null)
			criteria.add(Expression.ne("gc.id", gerenciadorComunicacao.getId()));
		
		criteria.add(Expression.eq("gc.empresa.id", gerenciadorComunicacao.getEmpresa().getId()));
		criteria.add(Expression.eq("gc.operacao", gerenciadorComunicacao.getOperacao()));
		criteria.add(Expression.eq("gc.meioComunicacao", gerenciadorComunicacao.getMeioComunicacao()));
		criteria.add(Expression.eq("gc.enviarPara", gerenciadorComunicacao.getEnviarPara()));
		
		return criteria.list().size() > 0;	
	}
}
