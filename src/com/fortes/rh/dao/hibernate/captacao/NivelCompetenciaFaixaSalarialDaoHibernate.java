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
	
	@SuppressWarnings("unchecked")
	public Collection<NivelCompetenciaFaixaSalarial> findByFaixa(Long faixaSalarialId) 
	{
		Criteria criteria = createCriteria();

		criteria.add(Expression.eq("ncfs.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.isNull("ncfs.candidato.id"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(NivelCompetenciaFaixaSalarial.class));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<NivelCompetenciaFaixaSalarial> findByCandidato(Long candidatoId) 
	{
		Criteria criteria = createCriteria();

		criteria.add(Expression.eq("ncfs.candidato.id", candidatoId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(NivelCompetenciaFaixaSalarial.class));

		return criteria.list();

	}

	private Criteria createCriteria() 
	{
		Criteria criteria = getSession().createCriteria(NivelCompetenciaFaixaSalarial.class,"ncfs");
		criteria.createCriteria("ncfs.nivelCompetencia", "nc", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ncfs.id"), "id");
		p.add(Projections.property("ncfs.faixaSalarial.id"), "faixaSalarialIdProjection");
		p.add(Projections.property("ncfs.candidato.id"), "candidatoIdProjection");
		p.add(Projections.property("nc.id"), "nivelCompetenciaIdProjection");
		p.add(Projections.property("nc.descricao"), "projectionNivelCompetenciaDescricao");
		p.add(Projections.property("ncfs.competenciaId"), "competenciaId");
		p.add(Projections.property("ncfs.tipoCompetencia"), "tipoCompetencia");

		criteria.setProjection(p);
		return criteria;
	}

	public void deleteConfiguracaoByFaixa(Long faixaSalarialId) 
	{
		String queryHQL = "delete from NivelCompetenciaFaixaSalarial where faixaSalarial.id = :faixaSalarialId and candidato.id is null";

		getSession().createQuery(queryHQL).setLong("faixaSalarialId", faixaSalarialId).executeUpdate();		
	}

	public void deleteConfiguracaoByCandidatoFaixa(Long candidatoId, Long faixaSalarialId) 
	{
		String queryHQL = "delete from NivelCompetenciaFaixaSalarial where candidato.id = :candidatoId and faixaSalarial.id = :faixaSalarialId";
		
		getSession().createQuery(queryHQL).setLong("candidatoId", candidatoId).setLong("faixaSalarialId", faixaSalarialId).executeUpdate();		
	}

}
