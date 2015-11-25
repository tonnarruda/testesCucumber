package com.fortes.rh.dao.hibernate.captacao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;


@SuppressWarnings("unchecked")
public class ConfiguracaoNivelCompetenciaDaoHibernate extends GenericDaoHibernate<ConfiguracaoNivelCompetencia> implements ConfiguracaoNivelCompetenciaDao
{
	
	public Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId, Date data) 
	{
		Criteria criteria = createCriteria(data);
		criteria.createCriteria("cnc.configuracaoNivelCompetenciaFaixaSalarial", "cncfs", Criteria.LEFT_JOIN);
		
		criteria.add(Expression.eq("cncfs.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.isNull("cnc.candidato.id"));
		criteria.add(Expression.isNull("cnc.configuracaoNivelCompetenciaColaborador.id"));

		DetachedCriteria subQueryHc = DetachedCriteria.forClass(ConfiguracaoNivelCompetenciaFaixaSalarial.class, "cncfs2")
				.setProjection(Projections.max("cncfs2.data"))
				.add(Restrictions.eq("cncfs2.faixaSalarial.id", faixaSalarialId))
				.add(Restrictions.le("cncfs2.data", (data == null ? new Date() : data)));

		criteria.add(Subqueries.propertyEq("cncfs.data", subQueryHc));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));

		return criteria.list();
	}

	public Collection<ConfiguracaoNivelCompetencia> findByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId) 
	{
		Criteria criteria = createCriteria(null);

		if(candidatoId != null)
			criteria.add(Expression.eq("cnc.candidato.id", candidatoId));
		
		if(solicitacaoId != null)
			criteria.add(Expression.eq("cnc.solicitacao.id", solicitacaoId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));

		return criteria.list();
	}

	private Criteria createCriteria(Date data) 
	{
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(NivelCompetenciaHistorico.class, "nch2")
				.setProjection(Projections.max("nch2.data"))
				.add(Restrictions.eqProperty("nch2.empresa.id", "nc.empresa.id"))
				.add(Restrictions.le("nch2.data", data == null ? new Date() : data ));
		
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetencia.class,"cnc");
		criteria.createCriteria("cnc.nivelCompetencia", "nc", Criteria.LEFT_JOIN);
		criteria.createCriteria("nc.configHistoricoNiveis", "chn", Criteria.LEFT_JOIN);
		criteria.createCriteria("chn.nivelCompetenciaHistorico", "nch", Criteria.INNER_JOIN);
		
		criteria.createCriteria("cnc.solicitacao", "s", Criteria.LEFT_JOIN);
		criteria.createCriteria("cnc.candidato", "cand", Criteria.LEFT_JOIN);
		criteria.createCriteria("s.faixaSalarial", "f", Criteria.LEFT_JOIN);
		criteria.createCriteria("f.cargo", "c", Criteria.LEFT_JOIN);
		criteria.add(Subqueries.propertyEq("nch.data", subQueryHc));

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cnc.id"), "id");
		p.add(Projections.property("cnc.faixaSalarial.id"), "faixaSalarialIdProjection");
		p.add(Projections.property("cnc.candidato.id"), "candidatoIdProjection");
		p.add(Projections.property("cnc.configuracaoNivelCompetenciaColaborador.id"), "projectionConfiguracaoNivelCompetenciaColaboradorId");
		p.add(Projections.property("cnc.configuracaoNivelCompetenciaFaixaSalarial.id"), "configuracaoNivelCompetenciaFaixaSalarialId");
		p.add(Projections.property("cnc.configuracaoNivelCompetenciaCriterios"), "configuracaoNivelCompetenciaCriterios");
		p.add(Projections.property("cnc.pesoCompetencia"), "pesoCompetencia");
		p.add(Projections.property("nc.id"), "nivelCompetenciaIdProjection");
		p.add(Projections.property("nc.descricao"), "projectionNivelCompetenciaDescricao");
		p.add(Projections.property("chn.ordem"), "nivelCompetenciaOrdemProjection");
		p.add(Projections.property("cnc.competenciaId"), "competenciaId");
		p.add(Projections.sqlProjection("(select nome from competencia where id = {alias}.competencia_id and {alias}.tipoCompetencia = tipo) as competenciaDescricao", new String[] {"competenciaDescricao"}, new Type[] {Hibernate.STRING}), "competenciaDescricao");
		p.add(Projections.property("cnc.tipoCompetencia"), "tipoCompetencia");
		p.add(Projections.property("s.id"), "solicitacaoId");
		p.add(Projections.property("s.descricao"), "solicitacaoDescricao");
		p.add(Projections.property("s.data"), "solicitacaoData");
		p.add(Projections.property("f.nome"), "faixaSalarialNome");
		p.add(Projections.property("f.id"), "faixaSalarialIdProjection");
		p.add(Projections.property("c.nome"), "cargoNome");
		p.add(Projections.property("cand.nome"), "candidatoNome");
		p.add(Projections.property("cand.id"), "candidatoId");

		criteria.setProjection(p);
		return criteria;
	}

	public void deleteConfiguracaoByCandidatoFaixa(Long candidatoId, Long faixaSalarialId, Long solicitacaoId) 
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia where candidato.id = :candidatoId and faixaSalarial.id = :faixaSalarialId and solicitacao.id = :solicitacaoId";
		
		getSession().createQuery(queryHQL).setLong("candidatoId", candidatoId).setLong("faixaSalarialId", faixaSalarialId).setLong("solicitacaoId", solicitacaoId).executeUpdate();		
	}
	
	public void deleteByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId) 
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia where configuracaoNivelCompetenciaColaborador.id = :configuracaoNivelCompetenciaColaboradorId";

		getSession().createQuery(queryHQL).setLong("configuracaoNivelCompetenciaColaboradorId", configuracaoNivelCompetenciaColaboradorId).executeUpdate();		
	}
	
	public void deleteByConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelCompetenciaFaixaSalarialId)
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia where configuracaoNivelCompetenciaFaixaSalarial.id = :configuracaoNivelCompetenciaFaixaSalarialId";

		getSession().createQuery(queryHQL).setLong("configuracaoNivelCompetenciaFaixaSalarialId", configuracaoNivelCompetenciaFaixaSalarialId).executeUpdate();		
	}

	public Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId) 
	{
		Criteria criteria = createCriteria(null);

		criteria.addOrder(Order.asc("competenciaDescricao"));
		criteria.add(Expression.eq("cnc.configuracaoNivelCompetenciaColaborador.id", configuracaoNivelCompetenciaColaboradorId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));

		return criteria.list();
	}

	public Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelCompetenciaFaixaSalarialId)
	{
		Criteria criteria = createCriteria(null);

		criteria.add(Expression.eq("cnc.configuracaoNivelCompetenciaFaixaSalarial.id", configuracaoNivelCompetenciaFaixaSalarialId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));

		return criteria.list();
	}

	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId, Date data) 
	{
		StringBuilder sql = new StringBuilder();

		sql.append("select cnc.id, cnc.tipoCompetencia, cnc.competencia_id as competenciaId, COALESCE(a.nome, conhe.nome, h.nome) as competenciaDescricao, COALESCE(a.observacao, conhe.observacao, h.observacao) as competenciaObservacao, cnc.nivelcompetencia_id, nc.descricao, chn.ordem, cnc.pesoCompetencia ");
		sql.append("from ConfiguracaoNivelCompetencia cnc ");
		sql.append("join ConfiguracaoNivelCompetenciaFaixaSalarial cncf on cncf.id = cnc.ConfiguracaoNivelCompetenciaFaixaSalarial_id ");
		sql.append("left join ConfigHistoricoNivel chn on cncf.nivelCompetenciahistorico_id = chn.nivelCompetenciahistorico_id and chn.NivelCompetencia_id=cnc.NivelCompetencia_id ");
		sql.append("left join NivelCompetencia nc on nc.id=chn.nivelCompetencia_id ");
		sql.append("left join Atitude a on a.id = cnc.competencia_id and 'A' = cnc.tipocompetencia ");
		sql.append("left join Conhecimento conhe on conhe.id = cnc.competencia_id and 'C' = cnc.tipocompetencia ");
		sql.append("left join Habilidade h on h.id = cnc.competencia_id and 'H' = cnc.tipocompetencia ");
		sql.append("where cncf.faixasalarial_id = :faixaSalarialId  ");
		sql.append("and cncf.data = (select max(data) from ConfiguracaoNivelCompetenciaFaixaSalarial cncf2 where cncf2.faixasalarial_id = cncf.faixasalarial_id and cncf2.data <= :data) ");
		sql.append("and cnc.configuracaoNivelCompetenciaColaborador_id is null ");
		sql.append("and cnc.candidato_id is null ");		
		sql.append("order by competenciaDescricao ");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("faixaSalarialId", faixaId);
		query.setDate("data", data == null ? new Date() : data);
		
		Collection<Object[]> resultado = query.list();
		
		Collection<ConfiguracaoNivelCompetencia> lista = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			
			Integer pesoCompetencia = 1;
			if(res[8] != null)
				pesoCompetencia = ((Short)res[8]).intValue();
			
			lista.add(new ConfiguracaoNivelCompetencia(((BigInteger)res[0]).longValue(), (Character)res[1], ((BigInteger)res[2]).longValue(), (String)res[3], (String)res[4], ((BigInteger)res[5]).longValue(), (String)res[6], (Integer)res[7], pesoCompetencia ));
		}

		return lista;				
	}
	
	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaColaborador(Date dataIni, Date dataFim, Long[] competenciaIds, Long faixaSalarialColaboradorId, boolean ordenarPorNivel) 
	{
		StringBuilder sql = new StringBuilder();

		sql.append("select distinct cnc.competencia_id as competenciaId, COALESCE(a.nome, conhe.nome, h.nome) as competencia, c.nome as colabNome, c.id as colabId, nc.descricao as nivelColabDescricao, chn.ordem as nivelColabOrdem, cncc.id as configNCColaboradorId, cncc.data as configNCData, av.nome as avaliadorNome, ad.anonima as avaliacaoAnonima ");
		sql.append("from ConfiguracaoNivelCompetencia cnc ");
		sql.append("left join Atitude a on a.id = cnc.competencia_id and :tipoAtitude = cnc.tipocompetencia ");
		sql.append("left join Conhecimento conhe on conhe.id = cnc.competencia_id and :tipoConhecimento = cnc.tipocompetencia ");
		sql.append("left join Habilidade h on h.id = cnc.competencia_id and :tipoHabilidade = cnc.tipocompetencia ");
		sql.append("left join ConfigHistoricoNivel chn on chn.nivelCompetencia_id = cnc.nivelCompetencia_id ");
		sql.append("left join NivelCompetencia nc on nc.id = chn.nivelCompetencia_id ");
		sql.append("left join NivelCompetenciaHistorico nch on nch.id=chn.nivelCompetenciaHistorico_id and nch.data = (select max(data) from nivelCompetenciaHistorico where empresa_id = nc.empresa_id ");

		if(dataFim != null)
			sql.append("and data <= :dataFim ");
		
		sql.append("						) ");
		sql.append("inner join ConfiguracaoNivelCompetenciaColaborador cncc on cncc.id = cnc.ConfiguracaoNivelCompetenciaColaborador_id ");
		sql.append("left join Colaborador c on c.id = cncc.colaborador_id ");
		sql.append("left join Colaborador av on av.id = cncc.avaliador_id ");
		sql.append("left join ColaboradorQuestionario cq on cq.id = cncc.colaboradorQuestionario_id ");
		sql.append("left join AvaliacaoDesempenho ad on ad.id = cq.avaliacaoDesempenho_id ");
		sql.append("where cncc.faixaSalarial_id = :faixaSalarialColaboradorId ");
	
		if(dataIni != null)
			sql.append("and cncc.data >= :dataIni ");
		
		if(dataFim != null)
			sql.append("and cncc.data <= :dataFim ");
		
		if (competenciaIds != null)
			sql.append("and cnc.competencia_id in (:competenciasIds) ");
		
		if(ordenarPorNivel)
			sql.append("order by c.nome, c.id, cncc.data, cncc.id, competencia ");
		else
			sql.append("order by c.nome, c.id, competencia, cncc.data, cncc.id ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setCharacter("tipoAtitude", TipoCompetencia.ATITUDE);
		query.setCharacter("tipoConhecimento", TipoCompetencia.CONHECIMENTO);
		query.setCharacter("tipoHabilidade", TipoCompetencia.HABILIDADE);
		query.setLong("faixaSalarialColaboradorId", faixaSalarialColaboradorId);
		
		if (competenciaIds != null)
			query.setParameterList("competenciasIds", competenciaIds, Hibernate.LONG);
		
		if(dataIni != null)
			query.setDate("dataIni", dataIni);

		if(dataFim != null)
			query.setDate("dataFim", dataFim);
		
		Collection<Object[]> resultado = query.list();
		
		Collection<ConfiguracaoNivelCompetencia> lista = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			lista.add(new ConfiguracaoNivelCompetencia((BigInteger)res[0], (String)res[1], (String)res[2], ((BigInteger)res[3]), (String)res[4], (Integer)res[5], ((BigInteger)res[6]), (Date)res[7], (String)res[8], (Boolean)res[9] ));
		}
		
		return lista;				
	}
	
	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaCandidato(Long faixaSalarialId, Collection<Long> candidatosIds) 
	{
		StringBuilder sql = new StringBuilder();
		
		sql.append("select c.id as candidatoId, c.nome as candidatoNome, COALESCE(a.nome, conhe.nome, h.nome) as competencia, nc.descricao as nivelFaixaDescricao, chn.ordem as nivelFaixaOrdem ");       
		sql.append("from configuracaonivelcompetencia cnc ");
		sql.append("left join Atitude a on a.id = cnc.competencia_id and :tipoAtitude = cnc.tipocompetencia ");
		sql.append("left join Conhecimento conhe on conhe.id = cnc.competencia_id and :tipoConhecimento = cnc.tipocompetencia ");
		sql.append("left join Habilidade h on h.id = cnc.competencia_id and :tipoHabilidade = cnc.tipocompetencia ");
		sql.append("left join Candidato c on c.id = cnc.candidato_id ");
		sql.append("left join ConfigHistoricoNivel chn on chn.nivelCompetencia_id = cnc.nivelCompetencia_id ");
		sql.append("left join NivelCompetencia nc on nc.id = chn.nivelCompetencia_id ");
		sql.append("left join NivelCompetenciaHistorico nch on nch.id=chn.nivelCompetenciaHistorico_id and nch.data = (select max(data) from nivelCompetenciaHistorico where empresa_id = nc.empresa_id) ");
		sql.append("where cnc.faixasalarial_id = :faixaSalarialId ");
		sql.append("and cnc.configuracaonivelcompetenciacolaborador_id is null ");
		
		if(!candidatosIds.isEmpty())
			sql.append("and (c.id in (:candidatosIds) or c.id is null) ");		
		
		sql.append("order by c.id nulls first, c.nome ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setCharacter("tipoAtitude", TipoCompetencia.ATITUDE);
		query.setCharacter("tipoConhecimento", TipoCompetencia.CONHECIMENTO);
		query.setCharacter("tipoHabilidade", TipoCompetencia.HABILIDADE);
		query.setLong("faixaSalarialId", faixaSalarialId);
		
		if(!candidatosIds.isEmpty())
			query.setParameterList("candidatosIds", candidatosIds, Hibernate.LONG);
		
		Collection<Object[]> resultado = query.list();
		
		Collection<ConfiguracaoNivelCompetencia> lista = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			lista.add(new ConfiguracaoNivelCompetencia((BigInteger)res[0], (String)res[1], (String)res[2], (String)res[3], (Integer)res[4] ));
		}
		
		return lista;				
	}

	public void removeByFaixas(Long[] faixaSalarialIds) 
	{
		String hql = "delete ConfiguracaoNivelCompetencia where faixaSalarial.id in (:faixaSalarialIds)";

		Query query = getSession().createQuery(hql);
		query.setParameterList("faixaSalarialIds", faixaSalarialIds, Hibernate.LONG);
		query.executeUpdate();
	}

	public void removeColaborador(Colaborador colaborador) 
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia cnc where cnc.configuracaoNivelCompetenciaColaborador.id in (select id from ConfiguracaoNivelCompetenciaColaborador where colaborador.id = :colaboradorId)";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("colaboradorId", colaborador.getId());
		query.executeUpdate();
	}

	public void removeByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId)
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia cnc where cnc.configuracaoNivelCompetenciaColaborador.id = :configuracaoNivelColaboradorId";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("configuracaoNivelColaboradorId", configuracaoNivelCompetenciaColaboradorId);
		query.executeUpdate();
	}

	public void removeDependenciasComConfiguracaoNivelCompetenciaColaboradorByFaixasSalariais(Long[] faixaIds)
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia cnc where cnc.configuracaoNivelCompetenciaColaborador.id in (select id from ConfiguracaoNivelCompetenciaColaborador where faixaSalarial.id in (:faixaIds))";

		removeDependenciasComConfiguracaoNivelCompetencia(faixaIds, queryHQL);
	}
	
	public void removeDependenciasComConfiguracaoNivelCompetenciaFaixaSalarialByFaixasSalariais(Long[] faixaIds)
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia cnc where cnc.configuracaoNivelCompetenciaFaixaSalarial.id in (select id from ConfiguracaoNivelCompetenciaFaixaSalarial where faixaSalarial.id in (:faixaIds))";

		removeDependenciasComConfiguracaoNivelCompetencia(faixaIds, queryHQL);
	}

	private void removeDependenciasComConfiguracaoNivelCompetencia(Long[] faixaIds, String queryHQL)
	{
		Query query = getSession().createQuery(queryHQL);
		query.setParameterList("faixaIds", faixaIds, Hibernate.LONG);
		query.executeUpdate();
	}

	public void removeByConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelCompetenciaFaixaSalarialId)
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia cnc where cnc.configuracaoNivelCompetenciaFaixaSalarial.id = :configuracaoNivelFaixaSalarialId";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("configuracaoNivelFaixaSalarialId", configuracaoNivelCompetenciaFaixaSalarialId);
		query.executeUpdate();
	}

	public void removeByCandidato(Long candidatoId)
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia cnc where cnc.candidato.id = :candidatoId";
		getSession().createQuery(queryHQL).setLong("candidatoId", candidatoId).executeUpdate();
	}

	public Long[] findCompetenciasIdsConfiguradasByFaixaSolicitacao(Long faixaSalarialId) {
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetencia.class, "cnc");

		ProjectionList p = Projections.projectionList().create();
		p. add(Projections.property("cnc.competenciaId"), "faixaSalarialIdProjection");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cnc.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.isNull("cnc.candidato.id"));
		criteria.add(Expression.isNull("cnc.configuracaoNivelCompetenciaColaborador.id"));
		
		Collection<Long> colIds = criteria.list();
		Long[] compIds = new Long[colIds.size()];
		
		return colIds.toArray(compIds);
	}

	public Integer somaConfiguracoesByFaixa(Long faixaSalarialId) 
	{
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(NivelCompetenciaHistorico.class, "nch2")
				.setProjection(Projections.max("nch2.data"))
				.add(Restrictions.eqProperty("nch2.empresa.id", "nc.empresa.id"))
				.add(Restrictions.le("nch2.data", new Date()));

		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetencia.class, "cnc");
		criteria.createCriteria("cnc.nivelCompetencia", "nc", Criteria.LEFT_JOIN);
		criteria.createCriteria("nc.configHistoricoNiveis", "chn", Criteria.INNER_JOIN);
		criteria.createCriteria("chn.nivelCompetenciaHistorico", "nch", Criteria.INNER_JOIN);
		criteria.add(Subqueries.propertyEq("nch.data", subQueryHc));

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.sum("chn.ordem"));
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cnc.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.isNull("cnc.candidato.id"));
		criteria.add(Expression.isNull("cnc.configuracaoNivelCompetenciaColaborador.id"));
		
		return (Integer) criteria.uniqueResult();
	}

	public Collection<ConfiguracaoNivelCompetencia> findByColaborador(Long colaboradorId, Long avaliadorId, Long colaboradorQuestionarioId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ConfiguracaoNivelCompetencia(cnc.tipoCompetencia, cnc.competenciaId, cnc.nivelCompetencia.id, chn.ordem) "); 
		hql.append("from ConfiguracaoNivelCompetencia cnc "); 
		hql.append("left join cnc.configuracaoNivelCompetenciaColaborador cncc ");
		hql.append("left join cnc.nivelCompetencia nc ");
		hql.append("left join nc.configHistoricoNiveis chn ");
		hql.append("left join chn.nivelCompetenciaHistorico nch ");
		hql.append("where cncc.colaborador.id = :colaboradorId ");
		hql.append("and cncc.data = (select max(data) from ConfiguracaoNivelCompetenciaColaborador where colaborador.id = cncc.colaborador.id) ");
		hql.append("and (cncc.colaboradorQuestionario.id = :colaboradorQuestionarioId or cncc.colaboradorQuestionario.id is null) ");
		hql.append("and (cncc.avaliador.id = :avaliadorId or cncc.avaliador.id is null) ");
		hql.append("and nch.data = (select max(data) from NivelCompetenciaHistorico where empresa.id = nc.empresa.id) ");
		hql.append("order by cnc.competenciaId");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("colaboradorId", colaboradorId);
		query.setLong("colaboradorQuestionarioId", colaboradorQuestionarioId);
		query.setLong("avaliadorId", avaliadorId);
		
		return query.list();
	}

	public Collection<ConfiguracaoNivelCompetencia> findColaboradoresCompetenciasAbaixoDoNivel(	Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Boolean colaboradoresAvaliados, char agruparPor) 
	{
		getSession().flush();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select c.id as colabId, c.nome as colabNome, fs.id as faixaId, emp.nome as empNome, est.nome as estNome, cnc.competencia_id as compId, cnc.tipoCompetencia as tipoComp, nc.descricao as nivelCompetenciaDescricao, nc2.descricao as nivelCompetenciaColaboradorDescricao, COALESCE(a.nome, conhe.nome, h.nome) as competencia, COALESCE (cconhe.id, chabil.id, cati.id ) as cursoId, COALESCE (cconhe.nome, chabil.nome, cati.nome ) as cursoNome ");
		sql.append("from HistoricoColaborador hc ");
		sql.append("inner join faixaSalarial fs on fs.id = hc.faixasalarial_id ");
		sql.append("inner join estabelecimento est on est.id = hc.estabelecimento_id ");
		sql.append("left join configuracaoNivelCompetenciaFaixaSalarial cncfs on cncfs.faixasalarial_id = fs.id  ");
		sql.append("left join configuracaoNivelCompetencia cnc on cnc.configuracaoNivelCompetenciaFaixaSalarial_id = cncfs.id  ");
		
		sql.append("left join ConfigHistoricoNivel chn on chn.nivelCompetencia_id = cnc.nivelCompetencia_id ");
		sql.append("left join NivelCompetencia nc on nc.id = chn.nivelCompetencia_id ");
		sql.append("left join NivelCompetenciaHistorico nch on nch.id = chn.nivelCompetenciaHistorico_id and nch.data = (select max(data) from nivelCompetenciaHistorico where empresa_id = nc.empresa_id) ");
		
		sql.append("left join Atitude a on a.id = cnc.competencia_id and 'A' = cnc.tipocompetencia ");
		sql.append("	left join atitude_curso at on at.atitudes_id = a.id ");
		sql.append("	left join curso cati on cati.id = at.cursos_id ");
		sql.append("left join Habilidade h on h.id = cnc.competencia_id and 'H' = cnc.tipocompetencia ");
		sql.append("	left join habilidade_curso hac on hac.habilidades_id = h.id ");
		sql.append("	left join curso chabil on chabil.id = hac.cursos_id ");
		sql.append("left join Conhecimento conhe on conhe.id = cnc.competencia_id and 'C' = cnc.tipocompetencia ");
		sql.append("	left join conhecimento_curso cc on cc.conhecimentos_id = conhe.id ");
		sql.append("	left join curso cconhe on cconhe.id = cc.cursos_id ");
		
		sql.append("inner join colaborador c on c.id = hc.colaborador_id ");
		sql.append("inner join empresa emp on emp.id = c.empresa_id ");
		sql.append("left join configuracaoNivelCompetenciaColaborador cncc on cncc.colaborador_id = c.id  ");
		sql.append("left join configuracaoNivelCompetencia cnc2 on cnc2.configuracaoNivelCompetenciaColaborador_id = cncc.id and cnc2.competencia_id = cnc.competencia_id and cnc2.tipoCompetencia = cnc.tipoCompetencia ");
		
		sql.append("left join ConfigHistoricoNivel chn2 on chn2.nivelCompetencia_id = cnc2.nivelCompetencia_id ");
		sql.append("left join NivelCompetencia nc2 on nc2.id = chn2.nivelCompetencia_id ");
		sql.append("left join NivelCompetenciaHistorico nch2 on nch2.id = chn2.nivelCompetenciaHistorico_id and nch2.data = (select max(data) from nivelCompetenciaHistorico where empresa_id = nc2.empresa_id) ");
		
		sql.append("where c.desligado = false ");
		sql.append("and hc.data = (select max(hc2.data) from HistoricoColaborador hc2 where hc2.colaborador_id = hc.colaborador_id and hc2.status = :status and hc2.data <= :hoje) ");
		sql.append("and cncfs.data = (select max(cncfs2.data) from ConfiguracaoNivelCompetenciaFaixaSalarial cncfs2 where cncfs2.faixaSalarial_id = fs.id and cncfs2.data <= :hoje) ");
		sql.append("and (cncc.data = (select max(cncc2.data) from ConfiguracaoNivelCompetenciaColaborador cncc2 where cncc2.colaborador_id = c.id and cncc2.data <= :hoje) or cncc.data is null) ");
		sql.append("and COALESCE (cconhe.id, chabil.id, cati.id ) is not null ");
		
		if (empresaId != null)
			sql.append("and c.empresa_id = :empresaId ");
		
		if (areaIds != null && areaIds.length > 0)
			sql.append("and hc.areaOrganizacional_id in (:areaIds) ");
		
		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			sql.append("and hc.estabelecimento_id in (:estabelecimentoIds) ");
		
		if (colaboradoresAvaliados != null && colaboradoresAvaliados)
			sql.append("and nc2.descricao is not null ");
		else if (colaboradoresAvaliados != null && !colaboradoresAvaliados)
			sql.append("and nc2.descricao is null ");
		
		sql.append("group by emp.nome, est.nome, c.id, c.nome, fs.id, cnc.competencia_id, cnc.tipoCompetencia, nc.id, nc.descricao, chn.ordem, nc2.id, nc2.descricao, chn2.ordem, competencia, cursoId, cursoNome ");
		sql.append("having (coalesce(chn2.ordem, 0) - chn.ordem) < 0 ");
		
		if (agruparPor == 'C')
			sql.append("order by emp.nome, est.nome, c.nome, c.id, COALESCE(a.nome, conhe.nome, h.nome), COALESCE (cconhe.nome,  chabil.nome,cati.nome )");
		else
			sql.append("order by emp.nome, est.nome, COALESCE (cconhe.nome,  chabil.nome,cati.nome ), COALESCE(a.nome, conhe.nome, h.nome), c.nome, c.id");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setDate("hoje", new Date());

		if (empresaId != null)
			query.setLong("empresaId", empresaId);
		
		if (areaIds != null && areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);
		
		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		
		Collection<Object[]> resultado = query.list();
		Collection<ConfiguracaoNivelCompetencia> lista = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			lista.add(new ConfiguracaoNivelCompetencia((BigInteger)res[0], (String)res[1], (BigInteger)res[2], (String)res[3], (String)res[4], (BigInteger)res[5], (Character)res[6], (String)res[7], (String)res[8], (String)res[9], (BigInteger)res[10], (String)res[11]));
		}
		
		return lista;	
	}

	public Collection<ConfiguracaoNivelCompetencia> findCompetenciasFaixaSalarial(Long[] competenciaIds, Long faixaSalarialId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select cnc.competencia_id as competenciaId, COALESCE(a.nome, conhe.nome, h.nome) as competencia, nc.descricao as nivelFaixaDescricao, chn.ordem as nivelFaixaOrdem, cncf.data as dataCNCHistoricoFaixa ");       
		sql.append("from configuracaonivelcompetencia cnc ");
		sql.append("inner join configuracaoNivelCompetenciaFaixaSalarial cncf on cncf.id = cnc.configuracaoNivelCompetenciaFaixaSalarial_id ");
		sql.append("left join Atitude a on a.id = cnc.competencia_id and :tipoAtitude = cnc.tipocompetencia ");
		sql.append("left join Conhecimento conhe on conhe.id = cnc.competencia_id and :tipoConhecimento = cnc.tipocompetencia ");
		sql.append("left join Habilidade h on h.id = cnc.competencia_id and :tipoHabilidade = cnc.tipocompetencia ");
		sql.append("left join ConfigHistoricoNivel chn on chn.nivelCompetencia_id = cnc.nivelCompetencia_id ");
		sql.append("left join NivelCompetencia nc on nc.id = chn.nivelCompetencia_id ");
		sql.append("left join NivelCompetenciaHistorico nch on nch.id=chn.nivelCompetenciaHistorico_id and nch.data = (select max(data) from nivelCompetenciaHistorico where empresa_id = nc.empresa_id) ");
		sql.append("where cncf.faixasalarial_id = :faixaSalarialId ");
		sql.append("and cnc.configuracaonivelcompetenciafaixasalarial_id is not null ");
		
		if (competenciaIds != null)
			sql.append("and cnc.competencia_id in (:competenciasIds) ");
		
		sql.append("order by cncf.data, cncf.id, competencia, nc.descricao ");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setCharacter("tipoAtitude", TipoCompetencia.ATITUDE);
		query.setCharacter("tipoConhecimento", TipoCompetencia.CONHECIMENTO);
		query.setCharacter("tipoHabilidade", TipoCompetencia.HABILIDADE);
		query.setLong("faixaSalarialId", faixaSalarialId);

		if (competenciaIds != null)
			query.setParameterList("competenciasIds", competenciaIds, Hibernate.LONG);
		
		Collection<Object[]> resultado = query.list();
		
		Collection<ConfiguracaoNivelCompetencia> lista = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = new ConfiguracaoNivelCompetencia();
			configuracaoNivelCompetencia.setCompetenciaId(((BigInteger)res[0]).longValue());
			configuracaoNivelCompetencia.setCompetenciaDescricao((String)res[1]);
			configuracaoNivelCompetencia.setNivelCompetenciaDescricaoProjection((String)res[2]);
			configuracaoNivelCompetencia.setNivelCompetenciaOrdemProjection((Integer)res[3]);
			configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaFaixaSalarialData((Date)res[4]);
			
			lista.add(configuracaoNivelCompetencia);
		}
		
		return lista;				
	}

	public Collection<Competencia> findCompetenciasColaboradorByFaixaSalarialAndPeriodo(Long faixaId, Date dataIni, Date dataFim) 
	{
		getSession().flush();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct cnc.competencia_id as competenciaId, COALESCE(a.nome, conhe.nome, h.nome) as competenciaDescricao ");
		sql.append("from ConfiguracaoNivelCompetencia cnc ");
		sql.append("inner join ConfiguracaoNivelCompetenciaColaborador cncc on cncc.id = cnc.configuracaoNivelCompetenciaColaborador_id ");
		sql.append("left join Atitude a on a.id = cnc.competencia_id and 'A' = cnc.tipocompetencia ");
		sql.append("left join Conhecimento conhe on conhe.id = cnc.competencia_id and 'C' = cnc.tipocompetencia ");
		sql.append("left join Habilidade h on h.id = cnc.competencia_id and 'H' = cnc.tipocompetencia ");
		sql.append("where cncc.faixaSalarial_id = :faixaSalarialId  ");
		sql.append("and cncc.data >= :dataIni ");
		
		if(dataFim != null)
			sql.append("and cncc.data <= :dataFim ");
		
		sql.append("order by competenciaDescricao ");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("faixaSalarialId", faixaId);
		query.setDate("dataIni", dataIni);
		
		if(dataFim != null)
			query.setDate("dataFim", dataFim);
		
		Collection<Object[]> resultado = query.list();
		Collection<Competencia> lista = new ArrayList<Competencia>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			Competencia competencia = new Competencia();
			competencia.setId(((BigInteger)res[0]).longValue());
			competencia.setNome((String)res[1]);
			lista.add(competencia);
		}

		return lista;				
	}

	public boolean existeDependenciaComCompetenciasDoCandidato(Long faixaSalarialId, Date dataInicial, Date dataFinal)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cnc");
		criteria.createCriteria("cnc.candidato", "ca", Criteria.INNER_JOIN);
		criteria.createCriteria("ca.candidatoSolicitacaos", "cs", Criteria.INNER_JOIN);
		criteria.createCriteria("cs.solicitacao", "s", Criteria.INNER_JOIN);
		
		criteria.setProjection(Projections.count("s.data"));
		
		criteria.add(Expression.eq("cnc.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.eq("cs.triagem", false));
		criteria.add(Expression.ge("s.data", dataInicial));
		
		if(dataFinal != null)
			criteria.add(Expression.lt("s.data", dataFinal));
		
		return ((Integer) criteria.uniqueResult()) > 0;		
	}

	public Collection<Colaborador> findDependenciaComColaborador(Long faixaSalarialId, Date data) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaColaborador.class, "cncc");
		criteria.createCriteria("cncc.configuracaoNivelCompetencias", "cnc", Criteria.INNER_JOIN);
		criteria.createCriteria("cncc.colaborador", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("cncc.colaboradorQuestionario", "cq", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.avaliacaoDesempenho", "ad", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("cncc.data"), "dataAtualizacao");
		p.add(Projections.property("ad.titulo"), "avaliacaoDesempenhoTitulo");

		criteria.setProjection(Projections.distinct(p));
		criteria.add(Expression.eq("cncc.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.ge("cncc.data", data));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));
		
		return criteria.list();
	}
	
	public Collection<Candidato> findDependenciaComCandidato(Long faixaSalarialId, Date data) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetencia.class, "cnc");
		criteria.createCriteria("cnc.solicitacao", "s", Criteria.INNER_JOIN);
		criteria.createCriteria("cnc.candidato", "c", Criteria.INNER_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("s.data"), "dataAtualizacao");

		criteria.setProjection(Projections.distinct(p));
		criteria.add(Expression.eq("s.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.ge("s.data", data));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Candidato.class));
		
		return criteria.list();
	}

	public void removeByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId) 
	{
		Query query = getSession().createQuery("delete from ConfiguracaoNivelCompetencia cnc where cnc.solicitacao.id = :solicitacaoId and cnc.candidato.id = :candidatoId");
		query.setLong("candidatoId", candidatoId);
		query.setLong("solicitacaoId", solicitacaoId);
		query.executeUpdate();
	}

	public void removeBySolicitacaoId(Long solicitacaoId) 
	{
		Query query = getSession().createQuery("delete from ConfiguracaoNivelCompetencia cnc where cnc.solicitacao.id = :solicitacaoId ");
		query.setLong("solicitacaoId", solicitacaoId);
		query.executeUpdate();
	}
	
	public Collection<ConfiguracaoNivelCompetencia> findBySolicitacaoIdCandidatoIdAndDataNivelCompetenciaHistorico(Long solicitacaoId, Long candidatoId, Date dataNivelCompetenciaHistorico) {
		Criteria criteria = createCriteria(dataNivelCompetenciaHistorico);
		criteria.add(Expression.eq("cnc.solicitacao.id", solicitacaoId));
		criteria.add(Expression.eq("cnc.candidato.id", candidatoId));
		criteria.addOrder(Order.asc("cand.nome"));
		criteria.addOrder(Order.asc("cand.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));
		return criteria.list();
	}
}