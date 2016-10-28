package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraDao;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;

@Component
public class ConfiguracaoCampoExtraDaoHibernate extends GenericDaoHibernate<ConfiguracaoCampoExtra> implements ConfiguracaoCampoExtraDao
{

	public Collection<ConfiguracaoCampoExtra> findAllSelect(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoCampoExtra.class, "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.ativoColaborador"), "ativoColaborador");
		p.add(Projections.property("c.ativoCandidato"), "ativoCandidato");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.descricao"), "descricao");
		p.add(Projections.property("c.titulo"), "titulo");
		p.add(Projections.property("c.ordem"), "ordem");
		p.add(Projections.property("c.posicao"), "posicao");
		p.add(Projections.property("c.empresa.id"), "empresaId");
		p.add(Projections.property("c.tipo"), "tipo");
		
		if(empresaId == null)
			criteria.add(Expression.isNull("c.empresa.id"));//pega o modelo de configuracao
		else
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.setProjection(p);
		criteria.addOrder(Order.asc("c.posicao"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoCampoExtra.class));
		
		return criteria.list();
	}

	public Collection<ConfiguracaoCampoExtra> findDistinct() 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.empresa", "e", Criteria.FULL_JOIN);
		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("c.ativoColaborador"), "ativoColaborador");
		p.add(Projections.property("c.ativoCandidato"), "ativoCandidato");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.descricao"), "descricao");
		p.add(Projections.property("c.titulo"), "titulo");
		p.add(Projections.property("c.ordem"), "ordem");
		p.add(Projections.property("c.tipo"), "tipo");
		p.add(Projections.property("c.posicao"), "posicao");
		
		criteria.setProjection(Projections.distinct(p));
		criteria.add(Expression.isNotNull("c.empresa.id"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public void removeAllNotModelo() {
		String queryHQL = "delete from ConfiguracaoCampoExtra where empresa.id is not null";

		getSession().createQuery(queryHQL).executeUpdate();
	}

	public String[] findAllNomes() 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("c.nome"), "nome");
		
		criteria.setProjection(Projections.distinct(p));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (String[]) criteria.list().toArray(new String[]{});
	}

}
