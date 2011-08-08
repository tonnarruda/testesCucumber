package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.NivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.model.captacao.NivelCompetenciaFaixaSalarial;

public class NivelCompetenciaFaixaSalarialDaoHibernate extends GenericDaoHibernate<NivelCompetenciaFaixaSalarial> implements NivelCompetenciaFaixaSalarialDao
{
	public void deleteConfiguracaoByFaixa(Long faixaSalarialId) 
	{
		String queryHQL = "delete from NivelCompetenciaFaixaSalarial where faixaSalarial.id = :faixaSalarialId";

		getSession().createQuery(queryHQL).setLong("faixaSalarialId", faixaSalarialId).executeUpdate();		
	}

	@SuppressWarnings("unchecked")
	public Collection<NivelCompetenciaFaixaSalarial> findByFaixa(Long faixaSalarialId) 
	{
		Criteria criteria = getSession().createCriteria(NivelCompetenciaFaixaSalarial.class,"ncfs");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ncfs.id"), "id");
		p.add(Projections.property("ncfs.faixaSalarial.id"), "faixaSalarialIdProjection");
		p.add(Projections.property("ncfs.nivelCompetencia.id"), "nivelCompetenciaIdProjection");
		p.add(Projections.property("ncfs.competenciaId"), "competenciaId");
		p.add(Projections.property("ncfs.tipoCompetencia"), "tipoCompetencia");

		criteria.setProjection(p);

		criteria.add(Expression.eq("ncfs.faixaSalarial.id", faixaSalarialId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(NivelCompetenciaFaixaSalarial.class));

		return criteria.list();
	}
}
