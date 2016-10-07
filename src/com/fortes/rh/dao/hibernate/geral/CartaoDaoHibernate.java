package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.CartaoDao;
import com.fortes.rh.model.geral.Cartao;

public class CartaoDaoHibernate extends GenericDaoHibernate<Cartao> implements CartaoDao
{
	private ProjectionList projectionCartao() {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.nome"),"empresaNome");
		p.add(Projections.property("e.emailRemetente"),"empresaEmailRemetente");
		p.add(Projections.property("e.emailRespRH"),"empresaEmailRespRH");
		p.add(Projections.property("e.logoUrl"),"empresaLogoUrl");
		p.add(Projections.property("c.imgUrl"), "imgUrl");
		p.add(Projections.property("c.mensagem"), "mensagem");
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.tipoCartao"), "tipoCartao");
		return p;
	}

	public Cartao findByEmpresaIdAndTipo(Long empresaId, String tipoCartao) {
		Criteria criteria = getSession().createCriteria(Cartao.class, "c");
		criteria = criteria.createCriteria("c.empresa", "e");
		
		criteria.add(Expression.eq("c.tipoCartao", tipoCartao));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.setProjection(projectionCartao());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cartao.class));
		
		return (Cartao) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Cartao> findByEmpresaId(Long empresaId) {
		Criteria criteria = getSession().createCriteria(Cartao.class, "c");
		criteria = criteria.createCriteria("c.empresa", "e");

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.setProjection(projectionCartao());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cartao.class));
		
		return criteria.list();
	}
}