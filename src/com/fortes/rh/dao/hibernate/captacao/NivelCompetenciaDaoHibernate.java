package com.fortes.rh.dao.hibernate.captacao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;

public class NivelCompetenciaDaoHibernate extends GenericDaoHibernate<NivelCompetencia> implements NivelCompetenciaDao
{
	@SuppressWarnings("unchecked")
	public Collection<NivelCompetencia> findAllSelect(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(NivelCompetencia.class,"nc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("nc.id"), "id");
		p.add(Projections.property("nc.ordem"), "ordem");
		p.add(Projections.property("nc.descricao"), "descricao");

		criteria.setProjection(p);

		if(empresaId != null)
			criteria.add(Expression.eq("nc.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(NivelCompetencia.class));
		criteria.addOrder(Order.asc("nc.ordem"));

		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetencia> findByCargoOrEmpresa(Long cargoId, Long empresaId) 
	{
		String whereConhecimento = "cc.cargo_id = :cargoId ";
		String whereHabilidade = "ch.cargo_id = :cargoId ";
		String whereAtitude = "ca.cargo_id = :cargoId ";
		
		if(cargoId == null)
		{
			whereConhecimento = "c.empresa_id = :empresaId ";
			whereHabilidade = "h.empresa_id = :empresaId ";
			whereAtitude = "a.empresa_id = :empresaId ";			
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("select id, nome, 'C' as tipocompetencia ");
		sql.append("from conhecimento as c inner join cargo_conhecimento as cc on c.id = cc.conhecimentos_id ");
		sql.append("where " + whereConhecimento);
		sql.append("union ");
		sql.append("select id, nome, 'H' as tipocompetencia "); 
		sql.append("from habilidade as h inner join cargo_habilidade as ch on h.id = ch.habilidades_id ");
		sql.append("where " + whereHabilidade);
		sql.append("union ");
		sql.append("select id, nome, 'A' as tipocompetencia "); 
		sql.append("from atitude as a inner join cargo_atitude as ca on a.id = ca.atitudes_id ");
		sql.append("where " + whereAtitude);
		sql.append("order by nome");

		Query query = getSession().createSQLQuery(sql.toString());
		
		if(cargoId == null)
			query.setLong("empresaId", empresaId);
		else
			query.setLong("cargoId", cargoId);
		
		Collection<Object[]> resultado = query.list();
		Collection<ConfiguracaoNivelCompetencia> lista = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			lista.add(new ConfiguracaoNivelCompetencia(((String)res[2]).charAt(0), ((BigInteger)res[0]).longValue(), (String)res[1]));
		}

		return lista;				
	}
}
