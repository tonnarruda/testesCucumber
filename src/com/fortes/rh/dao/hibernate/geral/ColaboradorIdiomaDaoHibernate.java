package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ColaboradorIdiomaDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;

@Component
public class ColaboradorIdiomaDaoHibernate extends GenericDaoHibernate<ColaboradorIdioma> implements ColaboradorIdiomaDao
{
	public void removeColaborador(Colaborador colaborador)
	{
		String queryHQL = "delete from ColaboradorIdioma e where e.colaborador.id = :valor";

		Query query = getSession().createQuery(queryHQL);

		query.setLong("valor", colaborador.getId());

		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Collection<ColaboradorIdioma> findByColaborador(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorIdioma.class, "ci");
		criteria.createCriteria("ci.idioma","i");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ci.id"), "id");
		p.add(Projections.property("ci.nivel"), "nivel");
		p.add(Projections.property("i.nome"), "idiomaNome");
		p.add(Projections.property("i.id"), "idiomaId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("ci.colaborador.id", colaboradorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorIdioma.class));

		return criteria.list();
	}
}