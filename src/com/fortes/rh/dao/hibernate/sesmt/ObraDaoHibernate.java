package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ObraDao;
import com.fortes.rh.model.sesmt.Obra;

@Component
public class ObraDaoHibernate extends GenericDaoHibernate<Obra> implements ObraDao
{
	@SuppressWarnings("unchecked")
	public Collection<Obra> findAllSelect(String nome, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(Obra.class, "o");
		criteria.createCriteria("o.endereco.cidade", "ci", Criteria.LEFT_JOIN);
		criteria.createCriteria("o.endereco.uf", "u", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("o.id"), "id");
		p.add(Projections.property("o.nome"), "nome");
		p.add(Projections.property("o.endereco.logradouro"), "enderecoLogradouro");
		p.add(Projections.property("o.endereco.numero"), "enderecoNumero");
		p.add(Projections.property("o.endereco.bairro"), "enderecoBairro");
		p.add(Projections.property("o.endereco.cep"), "enderecoCep");
		p.add(Projections.property("u.id"), "enderecoUfId");
		p.add(Projections.property("u.sigla"), "enderecoUfSigla");
		p.add(Projections.property("ci.id"), "enderecoCidadeId");
		p.add(Projections.property("ci.nome"), "enderecoCidadeNome");
		criteria.setProjection(p);
		
		if (StringUtils.isNotBlank(nome))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + nome + "%", StandardBasicTypes.STRING));

		criteria.add(Expression.eq("o.empresa.id", empresaId));

		criteria.addOrder(Order.asc("o.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Obra.class));

		return criteria.list();
	}

	public Obra findByIdProjecion(Long id) 
	{
		Criteria criteria = getSession().createCriteria(Obra.class, "o");
		criteria.createCriteria("o.endereco.cidade", "ci", Criteria.LEFT_JOIN);
		criteria.createCriteria("o.endereco.uf", "u", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("o.id"), "id");
		p.add(Projections.property("o.nome"), "nome");
		p.add(Projections.property("o.tipoObra"), "tipoObra");
		p.add(Projections.property("o.endereco.logradouro"), "enderecoLogradouro");
		p.add(Projections.property("o.endereco.numero"), "enderecoNumero");
		p.add(Projections.property("o.endereco.bairro"), "enderecoBairro");
		p.add(Projections.property("o.endereco.cep"), "enderecoCep");
		p.add(Projections.property("u.id"), "enderecoUfId");
		p.add(Projections.property("u.sigla"), "enderecoUfSigla");
		p.add(Projections.property("ci.id"), "enderecoCidadeId");
		p.add(Projections.property("ci.nome"), "enderecoCidadeNome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("o.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Obra.class));

		return (Obra) criteria.uniqueResult();
	}
}
