package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;

@Component
@SuppressWarnings("unchecked")
public class ComissaoDaoHibernate extends GenericDaoHibernate<Comissao> implements ComissaoDao
{

	public Collection<Comissao> findByEleicao(Long eleicaoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.dataIni"), "dataIni");
		p.add(Projections.property("c.dataFim"), "dataFim");
		p.add(Projections.property("c.eleicao.id"), "projectionEleicaoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.eleicao.id", eleicaoId));

		criteria.addOrder(Order.desc("c.dataFim"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Comissao findByIdProjection(Long comissaoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");
		criteria.createCriteria("c.eleicao", "e");
		criteria.createCriteria("e.estabelecimento", "estab", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.dataIni"), "dataIni");
		p.add(Projections.property("c.dataFim"), "dataFim");
		p.add(Projections.property("e.id"), "projectionEleicaoId");
		p.add(Projections.property("e.descricao"), "projectionEleicaoDescricao");
		p.add(Projections.property("e.posse"), "projectionEleicaoPosse");
		p.add(Projections.property("estab.nome"), "projectionEstabelecimentoNome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", comissaoId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (Comissao) criteria.uniqueResult();
	}

	public Collection<Comissao> findAllSelect(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");
		criteria.createCriteria("c.eleicao", "e");
		criteria.createCriteria("e.estabelecimento", "estab", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.dataIni"), "dataIni");
		p.add(Projections.property("c.dataFim"), "dataFim");
		p.add(Projections.property("e.id"), "projectionEleicaoId");
		p.add(Projections.property("estab.nome"), "projectionEstabelecimentoNome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("e.empresa.id", empresaId));

		criteria.addOrder(Order.desc("c.dataIni"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public boolean updateTextosComunicados(Comissao comissao) 
	{
		String hql = "update Comissao set ataPosseTexto1 = :ataPosseTexto1, ataPosseTexto2 = :ataPosseTexto2 where id = :id";

		Query query = getSession().createQuery(hql);

		query.setLong("id", comissao.getId());
		query.setString("ataPosseTexto1", comissao.getAtaPosseTexto1());
		query.setString("ataPosseTexto2", comissao.getAtaPosseTexto1());
		
		return query.executeUpdate() == 1;
	}

	public List<Colaborador> findColaboradoresByDataReuniao(Date dataReuniao, Long comissaoId) 
	{
		StringBuffer hql = new StringBuffer();
		hql.append("select new Colaborador(co.nome, co.id) "); 
		hql.append("from ComissaoPeriodo cp ");
		hql.append("inner join cp.comissao c ");
		hql.append("inner join cp.comissaoMembros cm "); 
		hql.append("inner join cm.colaborador co ");
		hql.append("left join c.comissaoPeriodos cp2 with cp2.aPartirDe = (select min(aPartirDe) from ComissaoPeriodo where comissao.id = c.id and aPartirDe > cp.aPartirDe) ");
		hql.append("where c.id = :comissaoId ");
		hql.append("and :dataReuniao >= cp.aPartirDe and :dataReuniao < coalesce(cp2.aPartirDe, c.dataFim + 1) ");
		hql.append("and (co.dataDesligamento is null or co.dataDesligamento >= :dataReuniao) ");
		hql.append("order by co.nome");
		
		Query query = getSession().createQuery(hql.toString());

		query.setLong("comissaoId", comissaoId);
		query.setDate("dataReuniao", dataReuniao);
		
		return query.list();
	}
}