package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.model.AbstractModel;
import com.fortes.rh.dao.geral.QuantidadeLimiteColaboradoresPorCargoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;

public class QuantidadeLimiteColaboradoresPorCargoDaoHibernate extends GenericDaoHibernate<QuantidadeLimiteColaboradoresPorCargo> implements QuantidadeLimiteColaboradoresPorCargoDao
{
	public void save(AreaOrganizacional areaOrganizacional, Cargo cargo, int limite)
	{
		String hql = "insert into QuantidadeLimiteColaboradoresPorCargo(areaorganizacional.id, cargo.id, limite) select :areaId, :cargoId, :limite";
		Query query = getSession().createQuery(hql);

		query.setLong("areaId", areaOrganizacional.getId());
		query.setLong("cargoId", cargo.getId());
		query.setInteger("limite", limite);

		query.executeUpdate();
	}

	public Collection<QuantidadeLimiteColaboradoresPorCargo> findByEntidade(Long id, Class<? extends AbstractModel> entidade) 
	{
		String idEntidade = (entidade == AreaOrganizacional.class ? "areaOrganizacional.id" : "cargo.id");
				
		Criteria criteria = getSession().createCriteria(getEntityClass(), "q");
		criteria.createCriteria("q.cargo", "c", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("q.id"), "id");
		p.add(Projections.property("q.limite"), "limite");
		p.add(Projections.property("c.id"), "projectionCargoId");
		p.add(Projections.property("c.nome"), "projectionCargoNome");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq(idEntidade, id));
		
		criteria.addOrder(Order.asc("c.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(QuantidadeLimiteColaboradoresPorCargo.class));

		return criteria.list();
	}

	public void deleteByArea(Long... areaIds) 
	{
		String hql = "delete QuantidadeLimiteColaboradoresPorCargo where areaOrganizacional.id in (:areaId)";

		Query query = getSession().createQuery(hql);
		query.setParameterList("areaId", areaIds, Hibernate.LONG);

		query.executeUpdate();
	}

	public void deleteByCargo(Long cargoId) 
	{
		String hql = "delete QuantidadeLimiteColaboradoresPorCargo where cargo.id = :cargoId";
		
		Query query = getSession().createQuery(hql);
		query.setLong("cargoId", cargoId);
		
		query.executeUpdate();
	}
	
	public QuantidadeLimiteColaboradoresPorCargo findLimite(Long cargoId, Collection<Long> areasIds) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "q");
		criteria.createCriteria("q.areaOrganizacional", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("q.cargo", "c", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("q.limite"), "limite");
		p.add(Projections.property("c.id"), "projectionCargoId");
		p.add(Projections.property("c.nome"), "projectionCargoNome");
		p.add(Projections.property("a.id"), "projectionAreaOrganizacionalId");
		p.add(Projections.alias(Projections.sqlProjection("monta_familia_area(a1_.id) as areaOrganizacionalNome", new String[] {"areaOrganizacionalNome"}, new Type[] {Hibernate.TEXT}), "projectionAreaOrganizacionalNome"));

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
