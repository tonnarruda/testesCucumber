package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.GastoEmpresaDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.GastoEmpresa;

@Component
public class GastoEmpresaDaoHibernate extends GenericDaoHibernate<GastoEmpresa> implements GastoEmpresaDao
{
	@SuppressWarnings("unchecked")
	public List filtroRelatorioByAreas(LinkedHashMap parametros)
	{
		StringBuilder hql = new StringBuilder(" select a.nome, g.nome, g.grupoGasto.nome, ge.mesAno, sum(gi.valor), a.id ");
		hql.append(" from GastoEmpresa ge ");
		hql.append(" left join ge.gastoEmpresaItems as gi ");
		hql.append(" left join gi.gasto g ");
		hql.append(" left join g.grupoGasto gg ");
		hql.append(" left join ge.colaborador c ");
		hql.append(" left join c.historicoColaboradors hc ");
		hql.append(" left join hc.areaOrganizacional a ");
		hql.append(" where gg.empresa = :empresaId ");
		hql.append(" and ge.mesAno >= :dataIni ");
		hql.append(" and ge.mesAno <= :dataFim ");
		hql.append(" and a.id in (:areasId) ");
		hql.append(" and hc.data >= (select max(hc2.data) ");
		hql.append("                 from HistoricoColaborador as hc2 ");
		hql.append("                 where hc2.colaborador.id = c.id ");
		hql.append("                 and hc2.data <= ge.mesAno and hc2.status = :status ) ");
		hql.append(" group by a.id, a.nome, g.grupoGasto.nome, g.nome, ge.mesAno ");
		hql.append(" order by a.id, a.nome, g.grupoGasto.nome, g.nome, ge.mesAno ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataIni", (Date) parametros.get("dataIni"));
		query.setDate("dataFim", (Date) parametros.get("dataFim"));
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setParameterList("areasId", (Collection<Long>) parametros.get("areas"), StandardBasicTypes.LONG);
		query.setLong("empresaId", (Long) parametros.get("empresaId"));

		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List filtroRelatorioByColaborador(LinkedHashMap parametros)
	{
		StringBuilder hql = new StringBuilder(" select a.nome, g.nome, g.grupoGasto.nome, ge.mesAno, sum(gi.valor), a.id ");
		hql.append(" from GastoEmpresa ge ");
		hql.append(" left join ge.gastoEmpresaItems as gi ");
		hql.append(" left join gi.gasto g ");
		hql.append(" left join g.grupoGasto gg ");
		hql.append(" left join ge.colaborador c ");
		hql.append(" left join c.historicoColaboradors hc ");
		hql.append(" left join hc.areaOrganizacional a ");
		hql.append(" where gg.empresa = :empresaId ");
		hql.append(" and ge.mesAno >= :dataIni ");
		hql.append(" and ge.mesAno <= :dataFim ");
		hql.append(" and c.id = :colaboradorId ");
		hql.append(" and hc.data >= (select max(hc2.data) ");
		hql.append("                 from HistoricoColaborador as hc2 ");
		hql.append("                 where hc2.colaborador.id = c.id ");
		hql.append("                 and hc2.data <= ge.mesAno and hc2.status = :status ) ");
		hql.append(" group by a.id, a.nome, g.grupoGasto.nome, g.nome, ge.mesAno ");
		hql.append(" order by a.id, a.nome, g.grupoGasto.nome, g.nome, ge.mesAno ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataIni", (Date) parametros.get("dataIni"));
		query.setDate("dataFim", (Date) parametros.get("dataFim"));
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		Colaborador colaborador = new Colaborador();
		colaborador = (Colaborador) parametros.get("colaborador");

		query.setLong("colaboradorId", colaborador.getId());
		query.setLong("empresaId", (Long) parametros.get("empresaId"));

		return query.list();
	}

	public Integer getCount(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(GastoEmpresa.class,"ge");
		criteria.setProjection(Projections.rowCount());

		montaConsulta(criteria, empresaId);

		return (Integer) criteria.list().get(0);
	}

	@SuppressWarnings("unchecked")
	public Collection<GastoEmpresa> findAllList(int page, int pagingSize, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(GastoEmpresa.class,"ge");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("ge.id"), "id");
		p.add(Projections.property("c.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("ge.mesAno"), "mesAno");

		criteria.setProjection(p);

		montaConsulta(criteria, empresaId);

		criteria.addOrder(Order.asc("c.nomeComercial"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(GastoEmpresa.class));

		// Se page e pagingSize = 0, chanada do método sobrecarregado sem paginação
		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		return criteria.list();
	}

	private void montaConsulta(Criteria criteria, Long empresaId)
	{
		criteria.createCriteria("colaborador", "c");

		criteria.add(Expression.eq("ge.empresa.id",empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}
}