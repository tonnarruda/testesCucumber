package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;

public class GrupoOcupacionalDaoHibernate extends GenericDaoHibernate<GrupoOcupacional> implements GrupoOcupacionalDao
{
	public Integer getCount(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(GrupoOcupacional.class,"g");
		criteria.setProjection(Projections.rowCount());

		if(empresaId != null)
			criteria.add(Expression.eq("g.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (Integer) criteria.list().get(0);
	}

	@SuppressWarnings("unchecked")
	public Collection<GrupoOcupacional> findAllSelect(int page, int pagingSize, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(GrupoOcupacional.class,"g");
		criteria.createCriteria("g.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("g.id"), "id");
		p.add(Projections.property("g.nome"), "nome");

		criteria.setProjection(p);

		if(empresaId != null)
			criteria.add(Expression.eq("e.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.addOrder(Order.asc("g.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(GrupoOcupacional.class));

		// Se page e pagingSize = 0, chanada do método sobrecarregado sem paginação
		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		return criteria.list();
	}

	public GrupoOcupacional findByIdProjection(Long grupoOcupacionalId)
	{
		Criteria criteria = getSession().createCriteria(GrupoOcupacional.class,"g");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("g.id"), "id");
		p.add(Projections.property("g.nome"), "nome");
		p.add(Projections.property("g.empresa.id"), "projectionEmpresaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("g.id", grupoOcupacionalId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(GrupoOcupacional.class));

		return (GrupoOcupacional) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<GrupoOcupacional> findByEmpresasIds(Long... empresaIds) 
	{
		Criteria criteria = getSession().createCriteria(GrupoOcupacional.class, "g");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("g.id"), "id");
		p.add(Projections.property("g.nome"), "nome");
		p.add(Projections.property("g.empresa.id"), "projectionEmpresaId");

		criteria.setProjection(p);

		criteria.add(Expression.in("g.empresa.id", empresaIds));
		criteria.addOrder(Order.asc("g.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(GrupoOcupacional.class));

		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<GrupoOcupacional> findAllSelectByAreasResponsavelCoresponsavel(Long empresaId, Long[] areasIds)
	{
		Criteria criteria = getSession().createCriteria(GrupoOcupacional.class,"g");
		criteria.createCriteria("g.empresa", "e", Criteria.INNER_JOIN);
		criteria.createCriteria("g.cargos", "cg", Criteria.INNER_JOIN);
		criteria.createCriteria("cg.areasOrganizacionais", "a", Criteria.INNER_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("g.id"), "id");
		p.add(Projections.property("g.nome"), "nome");

		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.eq("e.id", empresaId));
		criteria.add(Expression.in("a.id", areasIds));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.addOrder(Order.asc("g.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(GrupoOcupacional.class));

		return criteria.list();
	}

    @SuppressWarnings("unchecked")
    public Collection<GrupoOcupacional> findGruposUsadosPorCargosByEmpresaId(Long empresaId) {
        Criteria criteria = getSession().createCriteria(GrupoOcupacional.class,"g");
        criteria.createCriteria("g.cargos", "cg", Criteria.INNER_JOIN);

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("g.id"), "id");
        p.add(Projections.property("g.nome"), "nome");

        criteria.setProjection(Projections.distinct(p));

        criteria.add(Expression.eq("g.empresa.id", empresaId));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(GrupoOcupacional.class));

        return criteria.list();
    }

    public void deletarGruposInseridosENaoUtilizadosAposImportarCadastroEntreEmpresas(Long[] gruposOcupacionaisIds, Long empresaId) {
        StringBuilder hql = new StringBuilder("delete from GrupoOcupacional where id in(:gruposOcupacionaisIds) ");
        hql.append("and id not in(select grupoOcupacional.id from Cargo where empresa.id = :empresaId and grupoOcupacional.id is not null)");
        
        Query query = getSession().createQuery(hql.toString());
        query.setParameterList("gruposOcupacionaisIds", gruposOcupacionaisIds);
        query.setLong("empresaId", empresaId);

        query.executeUpdate();
    }
}