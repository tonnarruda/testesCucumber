package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.QuantidadeLimiteColaboradoresPorCargoDao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;

public class QuantidadeLimiteColaboradoresPorCargoDaoHibernate extends GenericDaoHibernate<QuantidadeLimiteColaboradoresPorCargo> implements QuantidadeLimiteColaboradoresPorCargoDao
{

	public void save(AreaOrganizacional areaOrganizacional, Cargo cargo, int limite) {
		String hql = "insert into QuantidadeLimiteColaboradoresPorCargo(areaorganizacional.id, cargo.id, limite) select :areaId, :cargoId, :limite";
		Query query = getSession().createQuery(hql);

		query.setLong("areaId", areaOrganizacional.getId());
		query.setLong("cargoId", cargo.getId());
		query.setInteger("limite", limite);

		query.executeUpdate();

		
	}

	public Collection<QuantidadeLimiteColaboradoresPorCargo> findByArea(Long areaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "q");
		criteria.createCriteria("q.cargo", "c", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("q.id"), "id");
		p.add(Projections.property("q.limite"), "limite");
		p.add(Projections.property("c.id"), "projectionCargoId");
		p.add(Projections.property("c.nome"), "projectionCargoNome");

		criteria.setProjection(p);
		criteria.add(Expression.eq("q.areaOrganizacional.id", areaId));
		criteria.addOrder(Order.asc("c.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(QuantidadeLimiteColaboradoresPorCargo.class));

		return criteria.list();
	}

	public void deleteByArea(Long areaId) 
	{
		String hql = "delete QuantidadeLimiteColaboradoresPorCargo where areaOrganizacional.id = :areaId";

		Query query = getSession().createQuery(hql);
		query.setLong("areaId", areaId);

		query.executeUpdate();
	}

	public QuantidadeLimiteColaboradoresPorCargo findLimite(Long cargoId, Collection<Long> areasIds) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "q");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("q.limite"), "limite");
		p.add(Projections.property("q.areaOrganizacional.id"), "projectionAreaOrganizacionalId");

		criteria.setProjection(p);
		criteria.add(Expression.eq("q.cargo.id", cargoId));
		criteria.add(Expression.in("q.areaOrganizacional.id", areasIds));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(QuantidadeLimiteColaboradoresPorCargo.class));
		
		return (QuantidadeLimiteColaboradoresPorCargo) criteria.uniqueResult();
	}
	
	public Collection<QuantidadeLimiteColaboradoresPorCargo> findByEmpresa(Long empresaId) 
	{
        
		Criteria criteria = getSession().createCriteria(getEntityClass(), "q");
		criteria.createCriteria("q.areaOrganizacional", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("q.cargo", "c", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("q.limite"), "limite");
		p.add(Projections.property("c.id"), "projectionCargoId");
		p.add(Projections.property("c.nome"), "projectionCargoNome");
		p.add(Projections.property("a.id"), "projectionAreaOrganizacionalId");
		p.add(Projections.property("a.nome"), "projectionAreaOrganizacionalNome");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("a.empresa.id", empresaId));
		
		criteria.addOrder(Order.asc("a.nome"));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(QuantidadeLimiteColaboradoresPorCargo.class));
		
		return criteria.list();
	}
}
