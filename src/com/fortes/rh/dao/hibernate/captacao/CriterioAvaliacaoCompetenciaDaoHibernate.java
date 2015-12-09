package com.fortes.rh.dao.hibernate.captacao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.CriterioAvaliacaoCompetenciaDao;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.util.LongUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CriterioAvaliacaoCompetenciaDaoHibernate extends GenericDaoHibernate<CriterioAvaliacaoCompetencia> implements CriterioAvaliacaoCompetenciaDao
{
	public Collection<CriterioAvaliacaoCompetencia> findByCompetenciaAndCNCFId(Long competenciaId, Long configuracaonivelcompetenciafaixasalarialId, Character tipoCompetencia)
	{
		StringBuilder sql = new StringBuilder();		
		
		sql.append("select distinct cr.id as criterio_id,cr.descricao as criterio_descricao ");
		if (tipoCompetencia == TipoCompetencia.CONHECIMENTO)
			sql.append("from conhecimento comp inner join criterioAvaliacaoCompetencia cr on comp.id=cr.conhecimento_id ");
		else if (tipoCompetencia == TipoCompetencia.HABILIDADE) 
			sql.append("from habilidade comp inner join criterioAvaliacaoCompetencia cr on comp.id=cr.habilidade_id ");
		else if (tipoCompetencia == TipoCompetencia.ATITUDE) 
			sql.append("from atitude comp inner join criterioAvaliacaoCompetencia cr on comp.id=cr.atitude_id ");
		
		sql.append("inner join configuracaoNivelCompetencia cnc on cnc.competencia_id = comp.id and cnc.tipocompetencia= :tipoCompetencia ");
		sql.append("inner join configuracaonivelcompetenciafaixasalarial cncf on cncf.id = cnc.configuracaonivelcompetenciafaixasalarial_id ");
		sql.append("inner join nivelcompetenciahistorico nch on cncf.nivelcompetenciahistorico_id = nch.id ");
		sql.append("where comp.id = :competenciaId and cncf.id = :configuracaonivelcompetenciafaixasalarialId  and not exists ");
		sql.append("																			(select id from confighistoriconivel where nivelcompetenciahistorico_id = nch.id and percentual is null)");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setCharacter("tipoCompetencia", tipoCompetencia);
		query.setLong("competenciaId", competenciaId);
		query.setLong("configuracaonivelcompetenciafaixasalarialId", configuracaonivelcompetenciafaixasalarialId);
		
		List resultado = query.list();
		Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = new ArrayList<CriterioAvaliacaoCompetencia>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia = new CriterioAvaliacaoCompetencia();
			
			Object[] res = it.next();
			
			criterioAvaliacaoCompetencia.setId(((BigInteger)res[0]).longValue());
			criterioAvaliacaoCompetencia.setDescricao(res[1].toString());
			criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia);
		}
		
		return criterioAvaliacaoCompetencias;
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

	public boolean existeCriterioAvaliacaoCompetencia(Long empresaId) {
		ProjectionList projectionList = Projections.projectionList().create();
		projectionList.add(Projections.property("cac.id"), "id");
		
		Criteria criteria = getSession().createCriteria(CriterioAvaliacaoCompetencia.class,"cac");
		criteria.createCriteria("cac.conhecimento", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("cac.habilidade", "h", Criteria.LEFT_JOIN);
		criteria.createCriteria("cac.atitude", "a", Criteria.LEFT_JOIN);
		criteria.add(Expression.or(Expression.or(Expression.eq("c.empresa.id", empresaId),Expression.eq("h.empresa.id", empresaId)), Expression.eq("a.empresa.id", empresaId)));
		criteria.setMaxResults(1);
		criteria.setProjection(projectionList);
		return criteria.list().size() > 0;

	}

	public Collection<CriterioAvaliacaoCompetencia> findByCompetencia(Long competenciaId, Character tipoCompetencia) {
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
}