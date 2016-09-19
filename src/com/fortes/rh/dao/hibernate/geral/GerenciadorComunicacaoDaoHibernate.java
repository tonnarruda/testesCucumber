package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GerenciadorComunicacao;

@SuppressWarnings("unchecked")
public class GerenciadorComunicacaoDaoHibernate extends GenericDaoHibernate<GerenciadorComunicacao> implements GerenciadorComunicacaoDao
{
	public Collection<GerenciadorComunicacao> findByOperacaoId(Integer operacaoId, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "gc");
		criteria = criteria.createCriteria("gc.empresa", "e");
		
		criteria.add(Expression.eq("gc.operacao", operacaoId));
		if (empresaId != null)
			criteria.add(Expression.eq("gc.empresa.id", empresaId));
		
		return criteria.list();	
	}

	public Collection<Empresa> findEmpresasByOperacaoId(Integer operacaoId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "gc");
		criteria = criteria.createCriteria("gc.empresa", "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"),"nome");
		p.add(Projections.property("e.emailRemetente"),"emailRemetente");
		p.add(Projections.property("e.emailRespRH"),"emailRespRH");
		p.add(Projections.property("e.logoUrl"),"logoUrl");
		p.add(Projections.property("e.imgAniversarianteUrl"), "imgAniversarianteUrl");
		p.add(Projections.property("e.mensagemCartaoAniversariante"), "mensagemCartaoAniversariante");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("gc.operacao", operacaoId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Empresa.class));
		
		return criteria.list();
	}
	
	public boolean verifyExists(GerenciadorComunicacao gerenciadorComunicacao)
	{
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
	
	public void removeByOperacao(Integer[] operacoes){
		String hql = "delete from GerenciadorComunicacao where operacao in(:operacoes) ";
		Query query = getSession().createQuery(hql);
		query.setParameterList("operacoes", operacoes);
		query.executeUpdate();
	}
}
