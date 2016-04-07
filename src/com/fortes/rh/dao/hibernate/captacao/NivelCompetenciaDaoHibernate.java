package com.fortes.rh.dao.hibernate.captacao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.dao.DataAccessResourceFailureException;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;

@SuppressWarnings("unchecked")
public class NivelCompetenciaDaoHibernate extends GenericDaoHibernate<NivelCompetencia> implements NivelCompetenciaDao
{
	public Collection<NivelCompetencia> findAllSelect(Long empresaId, Long nivelCompetenciaHistoricoId, Date data) 
	{
		Criteria criteria = getSession().createCriteria(NivelCompetencia.class, "nc");
		criteria.createCriteria("nc.configHistoricoNiveis", "chn", Criteria.INNER_JOIN);
		criteria.createCriteria("chn.nivelCompetenciaHistorico", "nch", Criteria.INNER_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("nc.id"), "id");
		p.add(Projections.property("nc.descricao"), "descricao");
		p.add(Projections.property("chn.ordem"), "ordem");
		p.add(Projections.property("chn.percentual"), "percentual");

		criteria.setProjection(p);

		if(empresaId != null)
			criteria.add(Expression.eq("nc.empresa.id", empresaId));
		
		if(nivelCompetenciaHistoricoId != null)
			criteria.add(Expression.eq("chn.nivelCompetenciaHistorico.id", nivelCompetenciaHistoricoId));
		else if(data != null)
			criteria.add(subQueryNHC(data));

		criteria.addOrder(Order.asc("chn.ordem"));
		criteria.addOrder(Order.asc("nc.descricao"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(NivelCompetencia.class));
		
		return criteria.list();
	}
	
	public Collection<NivelCompetencia> findAllSelect(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(NivelCompetencia.class, "nc");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("nc.id"), "id");
		p.add(Projections.property("nc.descricao"), "descricao");
		
		criteria.setProjection(p);

		criteria.add(Expression.eq("nc.empresa.id", empresaId));
		
		criteria.addOrder(Order.asc("nc.descricao"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(NivelCompetencia.class));
		
		return criteria.list();
	}
	
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
			lista.add(new ConfiguracaoNivelCompetencia(((String)res[3]).charAt(0), ((BigInteger)res[0]).longValue(), (String)res[1], (String)res[2], 1));
		}

		return lista;				
	}

	private Criteria criteriaNivelConfiguracao(Date data) throws DataAccessResourceFailureException, IllegalStateException, HibernateException 
	{
		Criteria criteria = getSession().createCriteria(ConfigHistoricoNivel.class, "chn");
		criteria.createCriteria("chn.nivelCompetencia", "nc", Criteria.INNER_JOIN);
		criteria.createCriteria("chn.nivelCompetenciaHistorico", "nch", Criteria.INNER_JOIN);
		criteria.add(subQueryNHC(data));
		return criteria;
	}
	
	public int getOrdemMaxima(Long empresaId, Long nivelCompetenciaHistoricoId) 
	{
		Criteria criteria = getSession().createCriteria(ConfigHistoricoNivel.class, "chn");
		criteria.createCriteria("chn.nivelCompetencia", "nc", Criteria.INNER_JOIN);
		criteria.createCriteria("chn.nivelCompetenciaHistorico", "nch", Criteria.INNER_JOIN);
		criteria.add(Expression.eq("nc.empresa.id", empresaId));
		criteria.add(Expression.eq("nch.id", nivelCompetenciaHistoricoId));
		criteria.setProjection(Projections.max("chn.ordem"));
		Integer result = (Integer) criteria.uniqueResult();
		return (int) (result == null ? 0 : result);
	}
	
	public Double getOrdemMaximaByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId) 
	{
		Criteria criteria = getSession().createCriteria(ConfigHistoricoNivel.class, "chn");
		
		criteria.setProjection(Projections.max("chn.ordem"));
		criteria.add(Expression.eq("chn.nivelCompetenciaHistorico.id", nivelCompetenciaHistoricoId));
		
		Integer result = (Integer) criteria.uniqueResult();
		return (Double) (result == null ? 0.0 : result);
	}

	public boolean existePercentual(Long nivelCompetenciaId, Long empresaId, Double percentual) 
	{
		Criteria criteria = criteriaNivelConfiguracao(null);

		if(nivelCompetenciaId != null)
			criteria.add(Expression.ne("nc.id", nivelCompetenciaId));
		
		criteria.add(Expression.eq("chn.percentual", percentual));
		criteria.add(Expression.eq("nc.empresa.id", empresaId));

		return criteria.list().size() > 0;
	}

	public boolean existeNivelCompetenciaSemPercentual(Long empresaId) 
	{
		Criteria criteria = criteriaNivelConfiguracao(null);

		criteria.add(Expression.eq("nc.empresa.id", empresaId));
		criteria.add(Expression.isNull("chn.percentual"));

		return criteria.list().size() > 0;
	}
	
	private Criterion subQueryNHC(Date data)
	{
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(NivelCompetenciaHistorico.class, "nch2")
				.setProjection(Projections.max("nch2.data"))
				.add(Restrictions.eqProperty("nch2.empresa.id", "nc.empresa.id"))
				.add(Restrictions.le("nch2.data", data!=null?data:new Date()));
		
		return Subqueries.propertyEq("nch.data", subQueryHc);
	}
}
