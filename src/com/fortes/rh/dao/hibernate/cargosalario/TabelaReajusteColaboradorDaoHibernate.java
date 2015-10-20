package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;

public class TabelaReajusteColaboradorDaoHibernate extends GenericDaoHibernate<TabelaReajusteColaborador> implements TabelaReajusteColaboradorDao
{
	@Override
	public void update(TabelaReajusteColaborador tabelaReajusteColaborador)
	{		
		String queryHQL = "update TabelaReajusteColaborador t set t.nome = :nome, t.data = :data, t.observacao = :obs, t.aprovada = :aprovada, t.empresa.id = :empresaId, t.dissidio = :dissidio, t.tipoReajuste = :tipoReajuste where t.id = :tabelaReajusteColaboradorId";

		Session session = getSession();
		Query query = session.createQuery(queryHQL);

		query.setString("nome", tabelaReajusteColaborador.getNome());
		query.setDate("data", tabelaReajusteColaborador.getData());
		query.setString("obs", tabelaReajusteColaborador.getObservacao());
		query.setBoolean("aprovada", tabelaReajusteColaborador.isAprovada());
		query.setBoolean("dissidio", tabelaReajusteColaborador.isDissidio());
		query.setCharacter("tipoReajuste", tabelaReajusteColaborador.getTipoReajuste());
		
		if(tabelaReajusteColaborador.getEmpresa() != null && tabelaReajusteColaborador.getEmpresa().getId() != null)
			query.setLong("empresaId", tabelaReajusteColaborador.getEmpresa().getId());
		else
			query.setParameter("empresaId", null, Hibernate.LONG);

		query.setLong("tabelaReajusteColaboradorId", tabelaReajusteColaborador.getId());

		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<TabelaReajusteColaborador> findAllSelect(Long empresaId, Character tipoReajuste, Boolean aprovada)
	{
		Criteria criteria = getSession().createCriteria(TabelaReajusteColaborador.class, "trc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("trc.id"), "id");
		p.add(Projections.property("trc.nome"), "nome");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("trc.empresa.id", empresaId));

		if (aprovada != null)
			criteria.add(Expression.eq("trc.aprovada", aprovada));
		
		if (tipoReajuste != null)
			criteria.add(Expression.eq("trc.tipoReajuste", tipoReajuste));
			
		criteria.addOrder(Order.asc("trc.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(TabelaReajusteColaborador.class));
		
		return criteria.list();
	}

	public Integer getCount(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(TabelaReajusteColaborador.class,"trc");
		criteria.setProjection(Projections.rowCount());

		montaConsulta(criteria, empresaId);

		return (Integer) criteria.list().get(0);
	}

	private void montaConsulta(Criteria criteria, Long empresaId)
	{
		criteria.add(Expression.eq("trc.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}

	@SuppressWarnings("unchecked")
	public Collection<TabelaReajusteColaborador> findAllList(int page, int pagingSize, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(TabelaReajusteColaborador.class, "trc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("trc.id"), "id");
		p.add(Projections.property("trc.nome"), "nome");
		p.add(Projections.property("trc.data"), "data");
		p.add(Projections.property("trc.aprovada"), "aprovada");
		p.add(Projections.property("trc.tipoReajuste"), "tipoReajuste");

		criteria.setProjection(p);

		montaConsulta(criteria, empresaId);
		criteria.addOrder(Order.desc("trc.data"));
		criteria.addOrder(Order.desc("trc.id"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(TabelaReajusteColaborador.class));

		// Se page e pagingSize = 0, chanada do método sobrecarregado sem paginação
		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		return criteria.list();
	}

	public TabelaReajusteColaborador findByIdProjection(Long tabelaReajusteColaboradorId)
	{
		Criteria criteria = getSession().createCriteria(TabelaReajusteColaborador.class, "trc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("trc.id"), "id");
		p.add(Projections.property("trc.nome"), "nome");
		p.add(Projections.property("trc.observacao"), "observacao");
		p.add(Projections.property("trc.data"), "data");
		p.add(Projections.property("trc.aprovada"), "aprovada");
		p.add(Projections.property("trc.dissidio"), "dissidio");
		p.add(Projections.property("trc.tipoReajuste"), "tipoReajuste");

		criteria.setProjection(p);
		criteria.add(Expression.eq("trc.id", tabelaReajusteColaboradorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(TabelaReajusteColaborador.class));

		return (TabelaReajusteColaborador) criteria.uniqueResult();
	}

	public void updateSetAprovada(Long tabelaReajusteColaboradorId, boolean aprovada)
	{
		String queryHQL = "update TabelaReajusteColaborador t set t.aprovada = :aprovada where t.id = :tabelaReajusteColaboradorId";

		Session session = getSession();
		Query query = session.createQuery(queryHQL);

		query.setBoolean("aprovada", aprovada);
		query.setLong("tabelaReajusteColaboradorId", tabelaReajusteColaboradorId);

		query.executeUpdate();
	}
}