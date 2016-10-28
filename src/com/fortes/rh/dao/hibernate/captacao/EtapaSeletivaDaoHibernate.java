/* Autor: Robertson Freitas
 * Data: 19/06/2006
 * Requisito: RFA0023 */
package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.captacao.EtapaSeletivaDao;
import com.fortes.rh.model.captacao.EtapaSeletiva;

@Component
public class EtapaSeletivaDaoHibernate extends GenericDaoHibernate<EtapaSeletiva> implements EtapaSeletivaDao
{
	public void ordeneCrescentementeEntre(int ordemOriginal, int novaOrdem, EtapaSeletiva etapaSeletiva)
	{
		String queryHQL = "update EtapaSeletiva etapaSeletiva set etapaSeletiva.ordem = (etapaSeletiva.ordem + 1) where etapaSeletiva.ordem >= "+novaOrdem+" and etapaSeletiva.ordem < "+ordemOriginal+" and etapaSeletiva.empresa.id="+etapaSeletiva.getEmpresa().getId()+"";
		ordena(ordemOriginal, etapaSeletiva, queryHQL);
	}

	public void ordeneCrescentementeAPartirDe(int ordem, EtapaSeletiva etapaSeletiva)
	{
		String queryHQL = "update EtapaSeletiva etapaSeletiva set etapaSeletiva.ordem = (etapaSeletiva.ordem + 1) where etapaSeletiva.ordem >= "+ordem+" and etapaSeletiva.empresa.id="+etapaSeletiva.getEmpresa().getId()+"";
		ordena(ordem, etapaSeletiva, queryHQL);
	}

	public void ordeneDecrescentementeEntre(int ordemOriginal, int novaOrdem, EtapaSeletiva etapaSeletiva)
	{
		String queryHQL = "update EtapaSeletiva etapaSeletiva set etapaSeletiva.ordem = (etapaSeletiva.ordem - 1) where etapaSeletiva.ordem <= "+novaOrdem+" and etapaSeletiva.ordem > "+ordemOriginal+" and etapaSeletiva.empresa.id="+etapaSeletiva.getEmpresa().getId()+"";
		ordena(ordemOriginal, etapaSeletiva, queryHQL);
	}

	public void ordeneDecrescentementeApartirDe(int ordem, EtapaSeletiva etapaSeletiva)
	{
		String queryHQL = "update EtapaSeletiva etapaSeletiva set etapaSeletiva.ordem = (etapaSeletiva.ordem - 1) where etapaSeletiva.ordem > "+ordem+" and etapaSeletiva.empresa.id="+etapaSeletiva.getEmpresa().getId()+"";
		ordena(ordem, etapaSeletiva, queryHQL);
	}

	private void ordena(int ordemOriginal, EtapaSeletiva etapaSeletiva, String queryHQL)
	{
		if (!find(new String[]{"ordem", "empresa.id"}, new Object[]{ordemOriginal, etapaSeletiva.getEmpresa().getId()}).isEmpty())
		{
			Session newSession = getSession();
			newSession.createQuery(queryHQL).executeUpdate();
		}
	}

	public EtapaSeletiva findPrimeiraEtapa(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(EtapaSeletiva.class);
		criteria.addOrder(Order.asc("ordem"));
		criteria.add(Expression.eq("empresa.id", empresaId));
		criteria.setMaxResults(1);
		return (EtapaSeletiva) criteria.uniqueResult();
	}



	public Integer getCount(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(EtapaSeletiva.class, "es");
		criteria.setProjection(Projections.rowCount());

		montaConsulta(criteria, empresaId);

		return (Integer) criteria.list().get(0);
	}

	/**
	 * Adiciona as linhas semenlhantes da criteria para o getCount() e findAllSelect()
	 */
	private void montaConsulta(Criteria criteria, Long empresaId)
	{
		criteria.add(Expression.eq("es.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}

	@SuppressWarnings("unchecked")
	public Collection<EtapaSeletiva> findAllSelect(int page, int pagingSize, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(EtapaSeletiva.class, "es");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("es.id"), "id");
		p.add(Projections.property("es.nome"), "nome");
		p.add(Projections.property("es.ordem"), "ordem");
		criteria.setProjection(p);

		criteria.addOrder(Order.asc("es.ordem"));

		montaConsulta(criteria, empresaId);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(EtapaSeletiva.class));

		// Se page e pagingSize = 0, chanada do método sobrecarregado sem paginação
		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<EtapaSeletiva> findByIdProjection(Long empresaId, Long[] etapaIds)
	{
		Criteria criteria = getSession().createCriteria(EtapaSeletiva.class, "es");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("es.id"), "id");
		p.add(Projections.property("es.nome"), "nome");
		p.add(Projections.property("es.ordem"), "ordem");
		criteria.setProjection(p);

		criteria.add(Expression.eq("es.empresa.id", empresaId));

		if(etapaIds != null && etapaIds.length != 0)
			criteria.add(Expression.in("es.id", etapaIds));
		
		criteria.addOrder(Order.asc("es.ordem"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(EtapaSeletiva.class));

		return criteria.list();
	}

	public EtapaSeletiva findByEtapaSeletivaId(Long etapaSeletivaId, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(EtapaSeletiva.class, "es");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("es.id"), "id");
		p.add(Projections.property("es.nome"), "nome");
		p.add(Projections.property("es.ordem"), "ordem");
		p.add(Projections.property("es.empresa.id"), "empresaId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("es.id", etapaSeletivaId));
		criteria.add(Expression.eq("es.empresa.id", empresaId));
		criteria.addOrder(Order.asc("es.ordem"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(EtapaSeletiva.class));

		return (EtapaSeletiva) criteria.uniqueResult();
	}

	public EtapaSeletiva findByIdProjection(Long etapaSeletivaId)
	{
		Criteria criteria = getSession().createCriteria(EtapaSeletiva.class, "es");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("es.id"), "id");
		p.add(Projections.property("es.ordem"), "ordem");
		criteria.setProjection(p);

		criteria.add(Expression.eq("es.id", etapaSeletivaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(EtapaSeletiva.class));

		return (EtapaSeletiva) criteria.uniqueResult();
	}
	//teste samuel
	public Collection<EtapaSeletiva> findByCargo(Long cargoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new EtapaSeletiva(ec.id, ec.nome) ");
		hql.append("from Cargo as c ");
		hql.append("join c.etapaSeletivas as ec ");
		hql.append("where c.id = :cargoId ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("cargoId", cargoId);

		return query.list();
	}
	
	public void deleteVinculoComCargo(Long etapaSeletivaId)
	{
		String[] sql = new String[] {"delete from cargo_etapaseletiva where etapaseletivas_id = " + etapaSeletivaId};
		JDBCConnection.executeQuery(sql);
	}
}