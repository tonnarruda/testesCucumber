package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.CandidatoEleicaoDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.sesmt.CandidatoEleicao;

@SuppressWarnings("unchecked")
public class CandidatoEleicaoDaoHibernate extends GenericDaoHibernate<CandidatoEleicao> implements CandidatoEleicaoDao
{
	public Collection<CandidatoEleicao> findByEleicao(Long eleicaoId, boolean somenteEleitos, boolean retornoAC)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new CandidatoEleicao(c.id, c.qtdVoto, c.eleito, e.id, cand.id, cand.nome, cand.nomeComercial, cargo.nome) ");
		hql.append("from CandidatoEleicao as c ");
		hql.append("left join c.eleicao as e ");
		hql.append("left join c.candidato as cand ");
		hql.append("left join cand.historicoColaboradors as h ");
		hql.append("left join h.faixaSalarial as fs ");
		hql.append("left join fs.cargo as cargo ");

		hql.append("where e.id = :eleicaoId ");
		
		if (somenteEleitos)
			hql.append("and c.eleito = true ");
		
		hql.append("and h.data = (select max(hc2.data) ");
		hql.append("from HistoricoColaborador as hc2 ");
		hql.append("where hc2.colaborador.id = cand.id ");
		hql.append("and hc2.data <= :hoje ");

		if(retornoAC)
			hql.append("and h.status <> :status ");

		hql.append(") order by cand.nome desc");

		Query query = getSession().createQuery(hql.toString());

		query.setLong("eleicaoId", eleicaoId);
		query.setDate("hoje", new Date());
		
		if(retornoAC)
			query.setInteger("status", StatusRetornoAC.CANCELADO);
		
		return query.list();
	}

	public CandidatoEleicao findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.qtdVoto"), "qtdVoto");
		p.add(Projections.property("c.eleito"), "eleito");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (CandidatoEleicao) criteria.uniqueResult();
	}
	
	public CandidatoEleicao findCandidatoEleicao(Long candidatoEleicaoId)
	{
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ce.id"), "id");
		p.add(Projections.property("ce.eleito"), "eleito");
		p.add(Projections.property("cand.id"), "projectionCandidatoId");
		p.add(Projections.property("cand.nome"), "projectionCandidatoNome");

		Criteria criteria = getSession().createCriteria(CandidatoEleicao.class,"ce");
		criteria.createCriteria("ce.candidato", "cand");
		criteria.setProjection(p);
		criteria.add(Expression.eq("ce.id", candidatoEleicaoId));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (CandidatoEleicao) criteria.uniqueResult();
	}
	
	public CandidatoEleicao findByColaboradorIdAndEleicaoId(Long colaboradorId, Long eleicaoId)
	{
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ce.id"), "id");
		p.add(Projections.property("cand.id"), "projectionCandidatoId");
		p.add(Projections.property("cand.nome"), "projectionCandidatoNome");

		Criteria criteria = getSession().createCriteria(CandidatoEleicao.class,"ce");
		criteria.createCriteria("ce.candidato", "cand");
		criteria.setProjection(p);
		criteria.add(Expression.eq("cand.id", colaboradorId));
		criteria.add(Expression.eq("ce.eleicao.id", eleicaoId));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (CandidatoEleicao) criteria.uniqueResult();
	}

	public void setEleito(boolean eleito, Long[] ids)
	{
		String hql = "update CandidatoEleicao set eleito = :eleito where id in (:ids)";

		Query query = getSession().createQuery(hql);

		query.setParameterList("ids", ids, Hibernate.LONG);
		query.setBoolean("eleito", eleito);

		query.executeUpdate();
	}

	public void setQtdVotos(int qtd, Long id)
	{
		String hql = "update CandidatoEleicao set qtdVoto = :qtd where id = :id";

		Query query = getSession().createQuery(hql);

		query.setLong("id", id);
		query.setInteger("qtd", qtd);

		query.executeUpdate();
	}

	public void removeByEleicao(Long eleicaoId)
	{
		String hql = "delete from CandidatoEleicao where eleicao_id = :eleicaoId";

		Query query = getSession().createQuery(hql);

		query.setLong("eleicaoId", eleicaoId);

		query.executeUpdate();
	}

	public Collection<CandidatoEleicao> findByColaborador(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");
		criteria = criteria.createCriteria("c.candidato", "cand");
		criteria = criteria.createCriteria("c.eleicao", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.qtdVoto"), "qtdVoto");
		p.add(Projections.property("c.eleito"), "eleito");
		p.add(Projections.property("c.eleito"), "eleito");
		p.add(Projections.property("e.id"), "projectionEleicaoId");
		p.add(Projections.property("e.inscricaoCandidatoIni"), "projectionEleicaoInscricaoCandidatoIni");
		p.add(Projections.property("cand.id"), "projectionCandidatoId");
		p.add(Projections.property("cand.nome"), "projectionCandidatoNome");
		p.add(Projections.property("cand.nomeComercial"), "projectionCandidatoNomeComercial");
		criteria.setProjection(p);

		criteria.add(Expression.eq("cand.id", colaboradorId));

		criteria.addOrder(Order.asc("e.inscricaoCandidatoIni"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}