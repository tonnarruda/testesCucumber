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
import com.fortes.rh.util.LongUtil;
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
	
	public int findQtdAtendidos(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataDe, Date dataAte) 
	{
		Criteria criteria = getSession().createCriteria(HistoricoCandidato.class, "hc");
		criteria.createCriteria("hc.candidatoSolicitacao", "cs");
		criteria.createCriteria("cs.solicitacao", "s");
		criteria.createCriteria("cs.candidato", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.countDistinct("cs.candidato.id"));
		
		criteria.setProjection(p);
		
		if(LongUtil.arrayIsNotEmpty(solicitacaoIds))
			criteria.add(Expression.in("cs.solicitacao.id", solicitacaoIds));
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			criteria.add(Expression.in("s.estabelecimento.id", estabelecimentoIds));
		
		if (LongUtil.arrayIsNotEmpty(areaIds))
			criteria.add(Expression.in("s.areaOrganizacional.id", areaIds));
		
		criteria.add(Expression.between("hc.data", dataDe, dataAte));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Integer) criteria.uniqueResult();
	}

	public Collection<HistoricoCandidato> findQtdParticipantes(String ano, Long empresaId, Long cargoId, Long[] etapaIds)
	{	
		StringBuilder hql = new StringBuilder();
		hql.append("select new HistoricoCandidato(e.id, e.nome, count(hc.id), month(hc.data), coalesce(hc.apto, 'N')) ");
		hql.append("from HistoricoCandidato as hc ");
		hql.append("left join hc.etapaSeletiva as e ");
		hql.append("left join hc.candidatoSolicitacao as cs ");
		hql.append("left join cs.solicitacao as s ");
		hql.append("left join s.faixaSalarial as fs ");
		hql.append("left join fs.cargo c ");
		
		hql.append("where date_part('year', hc.data) = :ano ");
		hql.append("and hc.data >= s.data ");
		hql.append("and c.empresa.id = :empresaId ");
		
		if(cargoId != null)
			hql.append("and fs.cargo.id = :cargoId ");
		
		if(etapaIds != null && etapaIds.length != 0)
			hql.append("and  e.id in ( :etapaIds ) ");
		
		hql.append("group by e.id, e.ordem, e.nome, month(hc.data), coalesce(hc.apto, 'N') ");
		hql.append("order by e.ordem, month(hc.data), coalesce(hc.apto, 'N') ");

		Query query = getSession().createQuery(hql.toString());
		query.setDouble("ano", Double.parseDouble(ano));
		query.setLong("empresaId", empresaId);

		if(cargoId != null)
			query.setLong("cargoId", cargoId);
		
		if(etapaIds != null && etapaIds.length != 0)
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

	public Collection<HistoricoCandidato> getEventos(String responsavel, Long empresaId, Date dataIni, Date dataFim) 
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
		
		if(dataIni != null && dataFim != null)
			criteria.add(Expression.between("hc.data", dataIni, dataFim));
		
		criteria.setProjection(p);
		criteria.addOrder(Order.asc("hc.data"));
		criteria.addOrder(Order.asc("hc.horaIni"));
		criteria.addOrder(Order.asc("hc.horaFim"));
		criteria.addOrder(Order.asc("c.nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoCandidato.class));

		return criteria.list();
	}

	public int findQtdEtapasRealizadas(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacoesIds, Date dataIni, Date dataFim)
	{
		Criteria criteria = getSession().createCriteria(HistoricoCandidato.class, "hc");
		criteria.createCriteria("hc.candidatoSolicitacao", "cs");
		criteria.createCriteria("cs.solicitacao", "s");
		criteria.createCriteria("cs.candidato", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.count("hc.id"));
		
		criteria.setProjection(p);
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			criteria.add(Expression.in("s.estabelecimento.id", estabelecimentoIds));
		
		if(LongUtil.arrayIsNotEmpty(areaIds))
			criteria.add(Expression.in("s.areaOrganizacional.id", areaIds));
		
		if(LongUtil.arrayIsNotEmpty(solicitacoesIds))
			criteria.add(Expression.in("cs.solicitacao.id", solicitacoesIds));
		
		criteria.add(Expression.between("hc.data", dataIni, dataFim));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		return (Integer) criteria.uniqueResult();
	}

	public void removeByCandidatoSolicitacao(Long candidatoSolicitcaoid) 
	{
		Query query = getSession().createQuery("delete HistoricoCandidato hc where hc.candidatoSolicitacao.id = :candidatoSolicitcaoid");
		query.setLong("candidatoSolicitcaoid", candidatoSolicitcaoid);
		query.executeUpdate();
	}
}