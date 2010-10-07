package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ConfiguracaoCampoExtraDao;

public class ConfiguracaoCampoExtraDaoHibernate extends GenericDaoHibernate<ConfiguracaoCampoExtra> implements ConfiguracaoCampoExtraDao
{

	public Collection<ConfiguracaoCampoExtra> findAllSelect() 
{
		Criteria criteria = getSession().createCriteria(ConfiguracaoCampoExtra.class, "c");
		
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.ativo"), "ativo");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.descricao"), "descricao");
		p.add(Projections.property("c.titulo"), "titulo");
		p.add(Projections.property("c.ordem"), "ordem");
		p.add(Projections.property("c.tipo"), "tipo");
		
		criteria.setProjection(p);
		criteria.addOrder(Order.asc("c.id"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoCampoExtra.class));
		
		return criteria.list();
	}

}
