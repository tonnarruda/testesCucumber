package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.CriterioAvaliacaoCompetenciaDao;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.util.LongUtil;

public class CriterioAvaliacaoCompetenciaDaoHibernate extends GenericDaoHibernate<CriterioAvaliacaoCompetencia> implements CriterioAvaliacaoCompetenciaDao
{
	public Collection<CriterioAvaliacaoCompetencia> findByCompetencia(Long competenciaId, Character tipoCompetencia)
	{
		StringBuilder hql = new StringBuilder("select new CriterioAvaliacaoCompetencia(criterioAvaliacaoCompetencia.id, criterioAvaliacaoCompetencia.descricao ) ");
		
		if (tipoCompetencia == TipoCompetencia.CONHECIMENTO)
			hql.append("from Conhecimento comp ");
		else if (tipoCompetencia == TipoCompetencia.HABILIDADE) 
			hql.append("from Habilidade comp ");
		else if (tipoCompetencia == TipoCompetencia.ATITUDE) 
			hql.append("from Atitude comp ");
		
		hql.append("inner join comp.criteriosAvaliacaoCompetencia criterioAvaliacaoCompetencia ");
		hql.append("where comp.id = :competenciaId ");

		hql.append("order by criterioAvaliacaoCompetencia.id ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("competenciaId", competenciaId);

		return query.list();
	}
	
	public void removeByCompetencia(Long competenciaId, Character tipoCompetencia, Long[] criteriosQuePermanecem) {
		StringBuilder hql = new StringBuilder("delete from CriterioAvaliacaoCompetencia ");
		
		if (tipoCompetencia == TipoCompetencia.CONHECIMENTO)
			hql.append("where conhecimento.id = :competenciaId ");
		else if (tipoCompetencia == TipoCompetencia.HABILIDADE) 
			hql.append("where habilidade.id = :competenciaId ");
		else if (tipoCompetencia == TipoCompetencia.ATITUDE) 
			hql.append("where atitude.id = :competenciaId ");
		
		if (LongUtil.arrayIsNotEmpty(criteriosQuePermanecem))
			hql.append("and id not in (:criteriosQuePermanecem)");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("competenciaId", competenciaId);
		
		if (LongUtil.arrayIsNotEmpty(criteriosQuePermanecem))
			query.setParameterList("criteriosQuePermanecem", criteriosQuePermanecem);

		query.executeUpdate();
	}
}