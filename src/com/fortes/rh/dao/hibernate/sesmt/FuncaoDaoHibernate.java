package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.sesmt.Funcao;

@SuppressWarnings("unchecked")
public class FuncaoDaoHibernate extends GenericDaoHibernate<Funcao> implements FuncaoDao
{
	public Integer getCount(Long cargoId)
	{
		Criteria criteria = getSession().createCriteria(Funcao.class, "f");
		criteria.setProjection(Projections.rowCount());

		montaConsulta(criteria, cargoId);

		return (Integer) criteria.list().get(0);
	}

	public Collection<Funcao> findByCargo(int page, int pagingSize, Long cargoId)
	{
		Criteria criteria = getSession().createCriteria(Funcao.class, "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.nome"), "nome");
		criteria.setProjection(p);

		montaConsulta(criteria, cargoId);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Funcao.class));

		// Se page e pagingSize = 0, chamada do método sobrecarregado sem paginação
		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		criteria.addOrder(Order.asc("f.nome"));
		return criteria.list();
	}

	private void montaConsulta(Criteria criteria, Long cargoId)
	{
		criteria.add(Expression.eq("f.cargo.id", cargoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}

	public Collection<Funcao> findByEmpresa(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Funcao.class, "f");
		criteria.createCriteria("cargo", "c");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.nome"), "nome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Funcao.class));

		return criteria.list();
	}

	public Collection<Funcao> findFuncaoByFaixa(Long faixaId)
	{
		Criteria criteria = getSession().createCriteria(Funcao.class, "f");
		criteria.createCriteria("f.cargo", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.faixaSalarials", "fs", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.nome"), "nome");
		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.eq("fs.id", faixaId));
		criteria.addOrder(Order.asc("f.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Funcao.class));

		return criteria.list();
	}

	public Funcao findByIdProjection(Long funcaoId)
	{
		Criteria criteria = getSession().createCriteria(Funcao.class, "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.nome"), "nome");
		p.add(Projections.property("f.cargo.id"), "projectionCargoId");
		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.eq("f.id", funcaoId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Funcao.class));

		return (Funcao) criteria.uniqueResult();
	}

	public Collection<Funcao> findFuncoesDoAmbiente(Long ambienteId, Date data) 
	{
		StringBuilder hql = new StringBuilder();
		
		hql.append("select new Funcao(f.id, f.nome, hf.descricao) ");
		hql.append("from HistoricoColaborador hc ");
		hql.append("	join hc.colaborador c ");
		hql.append("	join hc.ambiente a ");
		hql.append("	join hc.funcao f ");
		hql.append("	join f.historicoFuncaos hf ");
		hql.append("where   hc.data = (select max(hc2.data) from HistoricoColaborador hc2 where hc2.data <=:data  and hc2.status = :status and hc2.colaborador.id = hc.colaborador.id) ");
		hql.append("	and hf.data =   ( select max(hf2.data) from HistoricoFuncao hf2 where hf2.data <= :data and hf2.funcao.id = hf.funcao.id) ");
		hql.append("	and a.id = :ambienteId ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setLong("ambienteId", ambienteId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public Collection<Long> findFuncaoAtualDosColaboradores(Date data, Long estabelecimentoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select f.id ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("join hc.funcao f ");
		hql.append("join hc.colaborador co ");
		
		hql.append("where hc.estabelecimento.id = :estabelecimentoId ");
		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("                from HistoricoColaborador as hc2 ");
		hql.append("                where hc2.colaborador.id = co.id ");
		hql.append("                and hc2.data <= :data  and hc2.status = :status ) ");
		hql.append("group by f.id ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setDate("data", data);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public Collection<String> findColaboradoresSemFuncao(Date data, Long estabelecimentoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select co.nome ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.funcao f ");
		hql.append("join hc.colaborador co ");
		
		hql.append("where hc.estabelecimento.id = :estabelecimentoId ");
		hql.append("and f.id is null ");
		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("                from HistoricoColaborador as hc2 ");
		hql.append("                where hc2.colaborador.id = co.id ");
		hql.append("                and hc2.data <= :data and hc2.status = :status ) ");
		hql.append("group by co.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setDate("data", data);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		return query.list();
	}
	
	public int getQtdColaboradorByFuncao(Long funcaoId, Long estabelecimentoId, Date data, String sexo)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select count(*) as qtd ");
		hql.append("from HistoricoColaborador hc ");
		hql.append("	join hc.colaborador c ");
		hql.append("where   hc.data = (select max(hc2.data) from HistoricoColaborador hc2 where hc2.data <=:data  and hc2.status = :status  and hc2.colaborador.id = hc.colaborador.id) ");
		
		if (estabelecimentoId != null)
			hql.append("	and hc.estabelecimento.id = :estabelecimentoId ");
		
		hql.append("	and hc.funcao.id = :idFuncao ");
		hql.append("	and c.pessoal.sexo = :sexo ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("idFuncao", funcaoId);
		query.setString("sexo", sexo);
		
		if (estabelecimentoId != null)
			query.setLong("estabelecimentoId", estabelecimentoId);

		return (Integer)query.uniqueResult();
	}
}