package com.fortes.rh.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.HistoricoCandidatoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.util.StringUtil;

@SuppressWarnings("unchecked")
public class HistoricoCandidatoDaoHibernate extends GenericDaoHibernate<HistoricoCandidato> implements HistoricoCandidatoDao
{
	public Collection<HistoricoCandidato> findByCandidato(Candidato candidato)
	{
		Criteria criteria = getSession().createCriteria(HistoricoCandidato.class, "hc");
		criteria.createCriteria("hc.etapaSeletiva", "es", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.candidatoSolicitacao", "cs", Criteria.LEFT_JOIN);
		criteria.createCriteria("cs.solicitacao", "s", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.solicitante", "sol", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.areaOrganizacional", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.apto"), "apto");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("hc.responsavel"), "responsavel");
		p.add(Projections.property("hc.observacao"), "observacao");
		p.add(Projections.property("es.id"), "etapaSeletivaId");
		p.add(Projections.property("es.nome"), "etapaSeletivaNome");
		p.add(Projections.property("s.id"), "solicitacaoId");
		p.add(Projections.property("s.quantidade"), "solicitacaoQuantidade");
		p.add(Projections.property("s.descricao"), "solicitacaoDescricao");
		p.add(Projections.property("sol.nome"), "solicitacaoSolicitanteNome");
		p.add(Projections.property("c.nome"), "projectionCargoNomeMercado");
		p.add(Projections.property("a.id"), "solicitacaoAreaId");
		p.add(Projections.property("a.nome"), "solicitacaoAreaNome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cs.candidato.id", candidato.getId()));
		criteria.addOrder(Order.desc("data"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoCandidato.class));

		return criteria.list();
	}

	public Collection<HistoricoCandidato> findByCandidato(Collection<CandidatoSolicitacao> candidatosSolicitacaos)
	{
		if (candidatosSolicitacaos == null || candidatosSolicitacaos.isEmpty())
			return new ArrayList<HistoricoCandidato>();

		Criteria criteria = getSession().createCriteria(HistoricoCandidato.class, "hc");
		criteria.createCriteria("hc.etapaSeletiva", "es");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.apto"), "apto");
		p.add(Projections.property("es.id"), "etapaSeletivaId");

		criteria.setProjection(p);

		criteria.add(Expression.in("candidatoSolicitacao", candidatosSolicitacaos));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoCandidato.class));

		return criteria.list();
	}

	public Collection<HistoricoCandidato> findList(CandidatoSolicitacao candidatoSolicitacao)
	{
		Criteria criteria = getSession().createCriteria(HistoricoCandidato.class, "hc");
		criteria.createCriteria("hc.etapaSeletiva", "es", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("hc.responsavel"), "responsavel");
		p.add(Projections.property("hc.apto"), "apto");
		p.add(Projections.property("hc.observacao"), "observacao");
		p.add(Projections.property("es.id"), "etapaSeletivaId");
		p.add(Projections.property("es.nome"), "etapaSeletivaNome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("hc.candidatoSolicitacao", candidatoSolicitacao));
		criteria.addOrder(Order.desc("hc.data"));
		criteria.addOrder(Order.desc("es.ordem"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoCandidato.class));

		return criteria.list();
	}

	public Collection<HistoricoCandidato> findByPeriodo(Map parametros)
	{
		Criteria criteria = getSession().createCriteria(HistoricoCandidato.class, "hc");
		criteria.createCriteria("hc.etapaSeletiva", "es");
		criteria.createCriteria("hc.candidatoSolicitacao", "cs");
		criteria.createCriteria("cs.solicitacao", "s");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("es.id"), "etapaSeletivaId");
		p.add(Projections.property("es.nome"), "etapaSeletivaNome");
		criteria.setProjection(p);

		criteria.add(Expression.ge("hc.data",(Date) parametros.get("dataIni")));
		criteria.add(Expression.le("hc.data",(Date) parametros.get("dataFim")));
		criteria.add(Expression.isNotNull("s.solicitante"));

		criteria.addOrder(Order.asc("hc.data"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoCandidato.class));

		return criteria.list();
	}

	public Collection<HistoricoCandidato> findQtdParticipantes(String ano, Long cargoId, Long[] etapaIds)
	{	
//		EXEMPLO da consulta
//		select e.id,e.nome,count(hc.id),date_part('month', hc.data),hc.apto
//		from historicocandidato hc 
//		join etapaseletiva e on e.id=hc.etapaseletiva_id
//		join candidatosolicitacao cs on cs.id=hc.candidatosolicitacao_id
//		join solicitacao s on s.id=cs.solicitacao_id
//		join faixasalarial fs on fs.id=s.faixasalarial_id
//		where fs.cargo_id=202
//		and date_part('year',hc.data) = '2010'
//		group by e.id,e.ordem,e.nome,date_part('month', hc.data),hc.apto
//		order by e.ordem, date_part('month', hc.data),hc.apto

		StringBuilder hql = new StringBuilder();
		hql.append("select new HistoricoCandidato(e.id, e.nome, count(hc.id), month(hc.data), hc.apto) ");
		hql.append("from HistoricoCandidato as hc ");
		hql.append("left join hc.etapaSeletiva as e ");
		hql.append("left join hc.candidatoSolicitacao as cs ");
		hql.append("left join cs.solicitacao as s ");
		hql.append("left join s.faixaSalarial as fs ");

		hql.append("where fs.cargo.id = :cargoId ");
		hql.append("and  date_part('year', hc.data) = :ano ");
		hql.append("and  e.id in ( :etapaIds ) ");
		
		hql.append("group by e.id, e.ordem, e.nome, month(hc.data), hc.apto ");
		hql.append("order by e.ordem, month(hc.data), hc.apto ");
		

		Query query = getSession().createQuery(hql.toString());
		query.setLong("cargoId", cargoId);
		query.setDouble("ano", Double.parseDouble(ano));
		query.setParameterList("etapaIds", etapaIds, Hibernate.LONG);

		return query.list();
	}

	public HistoricoCandidato findByIdProjection(Long historicoId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoCandidato.class, "hc");
		criteria.createCriteria("hc.etapaSeletiva", "es");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("hc.apto"), "apto");
		p.add(Projections.property("hc.responsavel"), "responsavel");
		p.add(Projections.property("hc.observacao"), "observacao");
		p.add(Projections.property("hc.horaIni"), "horaIni");
		p.add(Projections.property("hc.horaFim"), "horaFim");
		p.add(Projections.property("es.id"), "etapaSeletivaId");
		p.add(Projections.property("es.nome"), "etapaSeletivaNome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("hc.id",historicoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoCandidato.class));

		return (HistoricoCandidato) criteria.list().toArray()[0];
	}

	public String[] findResponsaveis() 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct hc.responsavel ");
		hql.append("from HistoricoCandidato as hc ");
		hql.append("order by hc.responsavel ");
		
		Query query = getSession().createQuery(hql.toString());
		return StringUtil.converteCollectionToArrayString(query.list());
	}

	public boolean updateAgenda(Long id, Date data, String horaIni, String horaFim, String observacao) 
	{
		String hql = "update  HistoricoCandidato hc set hc.data = :data, hc.horaIni = :horaIni,  " +
				"hc.horaFim = :horaFim, hc.observacao = :observacao  where hc.id = :id  ";
		
		Query query = getSession().createQuery(hql);
		
		query.setLong("id", id);
		query.setDate("data", data);
		query.setString("horaIni", horaIni);
		query.setString("horaFim", horaFim);
		query.setString("observacao", observacao);
		
		return query.executeUpdate() == 1;
	}

	public Collection<HistoricoCandidato> getEventos(String responsavel, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoCandidato.class, "hc");
		criteria.createCriteria("hc.candidatoSolicitacao", "cs", Criteria.LEFT_JOIN);
		criteria.createCriteria("cs.solicitacao", "s", Criteria.LEFT_JOIN);
		criteria.createCriteria("cs.candidato", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.etapaSeletiva", "es");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hc.id"), "id");
		p.add(Projections.property("hc.data"), "data");
		p.add(Projections.property("hc.observacao"), "observacao");
		p.add(Projections.property("hc.responsavel"), "responsavel");
		p.add(Projections.property("hc.horaIni"), "horaIni");
		p.add(Projections.property("hc.horaFim"), "horaFim");
		p.add(Projections.property("c.id"), "candidatoId");
		p.add(Projections.property("c.nome"), "candidatoNome");
		p.add(Projections.property("es.nome"), "etapaSeletivaNome");
		
		if(StringUtils.isNotBlank(responsavel))
			criteria.add(Expression.like("hc.responsavel", "%" + responsavel + "%").ignoreCase());
		if(empresaId != null)
			criteria.add(Expression.eq("s.empresa.id", empresaId));
		
		criteria.setProjection(p);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoCandidato.class));

		return criteria.list();
	}
}