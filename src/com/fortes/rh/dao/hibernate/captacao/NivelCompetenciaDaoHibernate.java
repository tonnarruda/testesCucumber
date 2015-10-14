package com.fortes.rh.dao.hibernate.captacao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;

public class NivelCompetenciaDaoHibernate extends GenericDaoHibernate<NivelCompetencia> implements NivelCompetenciaDao
{
	@SuppressWarnings("unchecked")
	public Collection<NivelCompetencia> findAllSelect(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(NivelCompetencia.class);

		if(empresaId != null)
			criteria.add(Expression.eq("empresa.id", empresaId));

		criteria.addOrder(Order.asc("ordem"));
		criteria.addOrder(Order.asc("descricao"));

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
		sql.append("select id, nome, observacao, 'C' as tipocompetencia ");
		sql.append("from conhecimento as c inner join cargo_conhecimento as cc on c.id = cc.conhecimentos_id ");
		sql.append("where " + whereConhecimento);
		sql.append("union ");
		sql.append("select id, nome, observacao, 'H' as tipocompetencia "); 
		sql.append("from habilidade as h inner join cargo_habilidade as ch on h.id = ch.habilidades_id ");
		sql.append("where " + whereHabilidade);
		sql.append("union ");
		sql.append("select id, nome, observacao, 'A' as tipocompetencia "); 
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
			lista.add(new ConfiguracaoNivelCompetencia(((String)res[3]).charAt(0), ((BigInteger)res[0]).longValue(), (String)res[1], (String)res[2]));
		}

		return lista;				
	}

	public int getOrdemMaxima(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "nc");
		criteria.setProjection(Projections.max("nc.ordem"));
		criteria.add(Expression.eq("nc.empresa.id", empresaId));
		
		Integer result = (Integer) criteria.uniqueResult();
		return (int) (result == null ? 0 : result);
	}

	public boolean existePercentual(Long nivelCompetenciaId, Long empresaId, Double percentual) 
	{
		Criteria criteria = getSession().createCriteria(NivelCompetencia.class,"nc");

		if(nivelCompetenciaId != null)
			criteria.add(Expression.ne("nc.id", nivelCompetenciaId));
		
		criteria.add(Expression.eq("nc.percentual", percentual));
		criteria.add(Expression.eq("nc.empresa.id", empresaId));

		return criteria.list().size() > 0;
	}

	public boolean existeNivelCompetenciaSemPercentual(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(NivelCompetencia.class,"nc");

		criteria.add(Expression.eq("nc.empresa.id", empresaId));
		criteria.add(Expression.isNull("nc.percentual"));

		return criteria.list().size() > 0;
	}
}
