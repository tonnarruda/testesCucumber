package com.fortes.rh.dao.hibernate.captacao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

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
	public Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId, Date data){
		Criteria criteria = createCriteria();
		criteria.createCriteria("cnc.configuracaoNivelCompetenciaFaixaSalarial", "cncfs", Criteria.LEFT_JOIN);
		criteria.add(Expression.eqProperty("nch.id", "cncfs.nivelCompetenciaHistorico.id"));
		criteria.add(Expression.eq("cncfs.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.isNull("cnc.configuracaoNivelCompetenciaCandidato.id"));
		criteria.add(Expression.isNull("cnc.configuracaoNivelCompetenciaColaborador.id"));

		DetachedCriteria subQueryCNCF = DetachedCriteria.forClass(ConfiguracaoNivelCompetenciaFaixaSalarial.class, "cncfs2")
				.setProjection(Projections.max("cncfs2.data"))
				.add(Restrictions.eq("cncfs2.faixaSalarial.id", faixaSalarialId))
				.add(Restrictions.le("cncfs2.data", (data == null ? new Date() : data)));

		criteria.add(Subqueries.propertyEq("cncfs.data", subQueryCNCF));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));

		return criteria.list();
	}

	public Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidatoId){
		ProjectionList p = montaProjectionFindByConfiguracaoNivelCompetenciaFaixaSalarial();
		p.add(Projections.property("cnc_candidato.id"), "configuracaoNivelCompetenciaCandidatoId");
		p.add(Projections.property("cncf.id"), "configuracaoNivelCompetenciaCandidatoCNCFId");
		p.add(Projections.property("nch.id"), "configuracaoNivelCompetenciaCandidatoNivelCompetenciaHistoricoId");
		p.add(Projections.property("s.id"), "configuracaoNivelCompetenciaCandidatoSolicitacaoId");
		p.add(Projections.property("s.descricao"), "configuracaoNivelCompetenciaCandidatoSolicitacaoDescricao");
		p.add(Projections.property("cand.id"), "configuracaoNivelCompetenciaCandidatoCandidatoId");
		p.add(Projections.property("fs.nome"), "faixaSalarialNome");
		p.add(Projections.property("c.nome"), "cargoNome");
		
		Criteria criteria = createCriteriaConfiguracaoCompetenciaCandidato();
		criteria.createCriteria("cnc_candidato.candidato", "cand", Criteria.INNER_JOIN);
		criteria.createCriteria("cnc_candidato.solicitacao", "s", Criteria.LEFT_JOIN);
		criteria.createCriteria("cnc.faixaSalarial", "fs", Criteria.INNER_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);
		criteria.add(Expression.eq("cand.id", candidatoId));
		criteria.setProjection(p);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));
		
		return criteria.list();
	}
	
	private Criteria createCriteriaConfiguracaoCompetenciaCandidato(){
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetencia.class,"cnc");
		criteria.createCriteria("cnc.configuracaoNivelCompetenciaCandidato", "cnc_candidato", Criteria.INNER_JOIN);
		criteria.createCriteria("cnc_candidato.configuracaoNivelCompetenciaFaixaSalarial", "cncf", Criteria.LEFT_JOIN);
		criteria.createCriteria("cnc.nivelCompetencia", "nc", Criteria.LEFT_JOIN);
		criteria.createCriteria("nc.configHistoricoNiveis", "chn", Criteria.LEFT_JOIN);
		criteria.createCriteria("chn.nivelCompetenciaHistorico", "nch", Criteria.LEFT_JOIN);
		criteria.add(Expression.eqProperty("nch.id", "cncf.nivelCompetenciaHistorico.id"));
		return criteria;
	}
	
	public Collection<ConfiguracaoNivelCompetencia> findByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId){
		ProjectionList p = montaProjectionFindByConfiguracaoNivelCompetenciaFaixaSalarial();
		p.add(Projections.property("cnc_candidato.id"), "configuracaoNivelCompetenciaCandidatoId");
		p.add(Projections.property("cnc_candidato.solicitacao.id"), "configuracaoNivelCompetenciaCandidatoSolicitacaoId");
		p.add(Projections.property("cnc_candidato.candidato.id"), "configuracaoNivelCompetenciaCandidatoCandidatoId");
		p.add(Projections.property("cncf.id"), "configuracaoNivelCompetenciaCandidatoCNCFId");
		p.add(Projections.property("nch.id"), "configuracaoNivelCompetenciaCandidatoNivelCompetenciaHistoricoId");
		
		Criteria criteria = createCriteriaConfiguracaoCompetenciaCandidato();
		criteria.add(Expression.eq("cnc_candidato.candidato.id", candidatoId));
		criteria.add(Expression.eq("cnc_candidato.solicitacao.id", solicitacaoId));
		criteria.setProjection(p);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));
		return criteria.list();
	}

	private Criteria createCriteria(){
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetencia.class,"cnc");
		criteria.createCriteria("cnc.nivelCompetencia", "nc", Criteria.LEFT_JOIN);
		criteria.createCriteria("nc.configHistoricoNiveis", "chn", Criteria.LEFT_JOIN);
		criteria.createCriteria("chn.nivelCompetenciaHistorico", "nch", Criteria.INNER_JOIN);
		
		criteria.setProjection(montaProjectionFindByConfiguracaoNivelCompetenciaFaixaSalarial());
		return criteria;
	}

	public void deleteConfiguracaoByCandidatoFaixa(Long candidatoId, Long solicitacaoId) 
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia cnc where cnc.configuracaoNivelCompetenciaCandidato.id = ( select cncc.id from ConfiguracaoNivelCompetenciaCandidato cncc where cncc.candidato.id = :candidatoId and cncc.solicitacao.id = :solicitacaoId ) ";
		getSession().createQuery(queryHQL).setLong("candidatoId", candidatoId).setLong("solicitacaoId", solicitacaoId).executeUpdate();		
	}
	
	public void deleteByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId){
		String queryHQL = "delete from ConfiguracaoNivelCompetencia where configuracaoNivelCompetenciaColaborador.id = :configuracaoNivelCompetenciaColaboradorId";

		getSession().createQuery(queryHQL).setLong("configuracaoNivelCompetenciaColaboradorId", configuracaoNivelCompetenciaColaboradorId).executeUpdate();		
	}
	
	public void deleteByConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelCompetenciaFaixaSalarialId){
		String queryHQL = "delete from ConfiguracaoNivelCompetencia where configuracaoNivelCompetenciaFaixaSalarial.id = :configuracaoNivelCompetenciaFaixaSalarialId";

		getSession().createQuery(queryHQL).setLong("configuracaoNivelCompetenciaFaixaSalarialId", configuracaoNivelCompetenciaFaixaSalarialId).executeUpdate();		
	}

	public Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaColaborador(Long[] competenciasIds, Long configuracaoNivelCompetenciaColaboradorId, Long configuracaoNivelCompetenciaFaixaSalarialId){
		Criteria criteria = createCriteria();
		criteria.addOrder(Order.asc("competenciaDescricao"));
		criteria.createCriteria("cnc.configuracaoNivelCompetenciaColaborador", "cncc");
		criteria.createCriteria("cncc.configuracaoNivelCompetenciaFaixaSalarial", "cncf");
		criteria.add(Expression.eq("cnc.configuracaoNivelCompetenciaColaborador.id", configuracaoNivelCompetenciaColaboradorId));
		criteria.add(Restrictions.eq("cncf.id", configuracaoNivelCompetenciaFaixaSalarialId));
		criteria.add(Restrictions.eqProperty("nch.id", "cncf.nivelCompetenciaHistorico.id"));
		
		if(competenciasIds != null && competenciasIds.length > 0)
			criteria.add(Expression.in("cnc.competenciaId", competenciasIds));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));

		return criteria.list();
	}

	public Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelCompetenciaFaixaSalarialId){
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetencia.class,"cnc");
		criteria.createCriteria("cnc.configuracaoNivelCompetenciaFaixaSalarial", "cncf", Criteria.INNER_JOIN);
		criteria.createCriteria("cncf.nivelCompetenciaHistorico", "nch", Criteria.INNER_JOIN);
		criteria.createCriteria("nch.configHistoricoNiveis", "chn", Criteria.INNER_JOIN);
		criteria.createCriteria("chn.nivelCompetencia", "nc", Criteria.INNER_JOIN);
		criteria.setProjection(montaProjectionFindByConfiguracaoNivelCompetenciaFaixaSalarial());

		criteria.add(Expression.eq("cncf.id", configuracaoNivelCompetenciaFaixaSalarialId));
		criteria.add(Expression.eqProperty("chn.nivelCompetencia.id", "cnc.nivelCompetencia.id"));
		
		criteria.addOrder(Order.asc("cnc.tipoCompetencia"));
		criteria.addOrder(Order.asc("chn.ordem"));
		criteria.addOrder(Order.asc("nc.descricao"));
		criteria.addOrder(Order.asc("cnc.id"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));

		return criteria.list();
	}

	private ProjectionList montaProjectionFindByConfiguracaoNivelCompetenciaFaixaSalarial(){
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cnc.id"), "id");
		p.add(Projections.property("cnc.faixaSalarial.id"), "faixaSalarialIdProjection");
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
		return p;
	}

	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId, Date data, Long configuracaoNivelCompetenciaFaixaSalarialId, Long avaliadorId, Long avaliacaoDesempenhoId) 
	{
		StringBuilder sql = new StringBuilder();

		sql.append("select cnc.id, cnc.tipoCompetencia, cnc.competencia_id as competenciaId, COALESCE(a.nome, conhe.nome, h.nome) as competenciaDescricao, COALESCE(a.observacao, conhe.observacao, h.observacao) as competenciaObservacao, cnc.nivelcompetencia_id, nc.descricao, chn.ordem, cnc.pesoCompetencia, chn.percentual ");
		sql.append("from ConfiguracaoNivelCompetencia cnc ");
		sql.append("join ConfiguracaoNivelCompetenciaFaixaSalarial cncf on cncf.id = cnc.ConfiguracaoNivelCompetenciaFaixaSalarial_id ");
		
		if(avaliadorId != null && avaliacaoDesempenhoId != null){
			sql.append("join configuracaoCompetenciaAvaliacaoDesempenho ccad on ccad.ConfiguracaoNivelCompetenciaFaixaSalarial_id = cncf.id ");
			sql.append("and cnc.competencia_id = ccad.competencia_id and ccad.tipoCompetencia = cnc.tipoCompetencia ");
		}
		
		sql.append("left join ConfigHistoricoNivel chn on cncf.nivelCompetenciahistorico_id = chn.nivelCompetenciahistorico_id and chn.NivelCompetencia_id=cnc.NivelCompetencia_id ");
		sql.append("left join NivelCompetencia nc on nc.id=chn.nivelCompetencia_id ");
		sql.append("left join Atitude a on a.id = cnc.competencia_id and 'A' = cnc.tipocompetencia ");
		sql.append("left join Conhecimento conhe on conhe.id = cnc.competencia_id and 'C' = cnc.tipocompetencia ");
		sql.append("left join Habilidade h on h.id = cnc.competencia_id and 'H' = cnc.tipocompetencia ");
		
		sql.append("where cnc.configuracaoNivelCompetenciaColaborador_id is null ");
		
		if(avaliadorId != null && avaliacaoDesempenhoId != null){
			sql.append("and ccad.avaliacaodesempenho_id = :avaliacaoDesempenhoId ");
			sql.append("and ccad.avaliador_id = :avaliadorId ");
		}
		
		if(configuracaoNivelCompetenciaFaixaSalarialId != null){
			sql.append("and cncf.id = :cncfId  ");
		}else{
			sql.append("and cncf.faixasalarial_id = :faixaSalarialId  ");
			sql.append("and cncf.data = (select max(data) from ConfiguracaoNivelCompetenciaFaixaSalarial cncf2 where cncf2.faixasalarial_id = cncf.faixasalarial_id and cncf2.data <= :data) ");
		}
		
		sql.append("and cnc.configuracaoNivelCompetenciaCandidato_id is null ");		
		sql.append("order by competenciaDescricao ");

		Query query = getSession().createSQLQuery(sql.toString());
		
		if(avaliadorId != null && avaliacaoDesempenhoId != null){
			query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
			query.setLong("avaliadorId", avaliadorId);
		}
		
		if(configuracaoNivelCompetenciaFaixaSalarialId != null){
			query.setLong("cncfId", configuracaoNivelCompetenciaFaixaSalarialId);
		}else{
			query.setLong("faixaSalarialId", faixaId);
			query.setDate("data", data == null ? new Date() : data);
		}
		
		Collection<Object[]> resultado = query.list();
		
		Collection<ConfiguracaoNivelCompetencia> lista = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			
			Integer pesoCompetencia = 1;
			if(res[8] != null)
				pesoCompetencia = ((Short)res[8]).intValue();
			
			lista.add(new ConfiguracaoNivelCompetencia(((BigInteger)res[0]).longValue(), (Character)res[1], ((BigInteger)res[2]).longValue(), (String)res[3], (String)res[4], ((BigInteger)res[5]).longValue(), (String)res[6], (Integer)res[7], pesoCompetencia, (Double)res[9]));
		}

		return lista;				
	}
	
	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaColaborador(Date dataIni, Date dataFim, Long[] competenciaIds, Long faixaSalarialColaboradorId, boolean ordenarPorNivel) 
	{
		StringBuilder sql = new StringBuilder();

		sql.append("select distinct cnc.competencia_id as competenciaId, COALESCE(a.nome, conhe.nome, h.nome) as competencia, c.nome as colabNome, c.id as colabId, nc.descricao as nivelColabDescricao, chn.ordem as nivelColabOrdem, cncc.id as configNCColaboradorId, cncc.data as configNCData, av.nome as avaliadorNome, ad.anonima as avaliacaoAnonima, cncf.id as cncfId, cnc.tipoCompetencia as tipoCompetencia, cncf.nivelCompetenciaHistorico_id as cncfNivelCompetenciaHistoricoId ");
		sql.append("from ConfiguracaoNivelCompetencia cnc ");
		sql.append("left join Atitude a on a.id = cnc.competencia_id and :tipoAtitude = cnc.tipocompetencia ");
		sql.append("left join Conhecimento conhe on conhe.id = cnc.competencia_id and :tipoConhecimento = cnc.tipocompetencia ");
		sql.append("left join Habilidade h on h.id = cnc.competencia_id and :tipoHabilidade = cnc.tipocompetencia ");
		sql.append("inner join ConfiguracaoNivelCompetenciaColaborador cncc on cncc.id = cnc.ConfiguracaoNivelCompetenciaColaborador_id ");

		if(dataFim != null)
			sql.append("and data <= :dataFim ");

		sql.append("inner join  ConfiguracaoNivelCompetenciaFaixaSalarial cncf on cncc.ConfiguracaoNivelCompetenciaFaixaSalarial_id = cncf.id ");
		sql.append("inner join ConfigHistoricoNivel chn on chn.NivelCompetenciaHistorico_id = cncf.nivelcompetenciahistorico_id and chn.nivelCompetencia_id = cnc.nivelCompetencia_id ");
		sql.append("inner join NivelCompetencia nc on nc.id = chn.nivelCompetencia_id ");
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
			lista.add(new ConfiguracaoNivelCompetencia((BigInteger)res[0], (String)res[1], (String)res[2], ((BigInteger)res[3]), (String)res[4], (Integer)res[5], ((BigInteger)res[6]), (Date)res[7], (String)res[8], (Boolean)res[9], ((BigInteger)res[10]).longValue(), (Character)res[11], ((BigInteger)res[10]).longValue()));
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
		String queryHQL = "delete from ConfiguracaoNivelCompetencia cnc where cnc.configuracaoNivelCompetenciaCandidato.id in ( select cncc.id from ConfiguracaoNivelCompetenciaCandidato cncc where cncc.candidato.id = :candidatoId) ";
		getSession().createQuery(queryHQL).setLong("candidatoId", candidatoId).executeUpdate();
	}

	public Long[] findCompetenciasIdsConfiguradasByFaixaSolicitacao(Long faixaSalarialId) {
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetencia.class, "cnc");

		ProjectionList p = Projections.projectionList().create();
		p. add(Projections.property("cnc.competenciaId"), "faixaSalarialIdProjection");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cnc.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.isNull("cnc.configuracaoNivelCompetenciaCandidato.id"));
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
		criteria.add(Expression.isNull("cnc.configuracaoNivelCompetenciaCandidato.id"));
		criteria.add(Expression.isNull("cnc.configuracaoNivelCompetenciaColaborador.id"));
		
		return (Integer) criteria.uniqueResult();
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
		
		sql.append("left join NivelCompetenciaHistorico nch on nch.id = cncfs.nivelCompetenciaHistorico_id ");
		sql.append("left join ConfigHistoricoNivel chn on chn.nivelCompetenciaHistorico_id = nch.id and chn.nivelCompetencia_id = cnc.nivelCompetencia_id ");
		sql.append("left join NivelCompetencia nc on nc.id = chn.nivelCompetencia_id ");
		
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

		sql.append("left join ConfiguracaoNivelCompetenciaFaixaSalarial cncf2 on cncf2.id = cncc.configuracaoNivelCompetenciaFaixaSalarial_id ");
		sql.append("left join NivelCompetenciaHistorico nch2 on nch2.id = cncf2.nivelCompetenciaHistorico_id ");
		sql.append("left join ConfigHistoricoNivel chn2 on chn2.nivelCompetenciaHistorico_id = nch2.id and chn2.nivelCompetencia_id = cnc2.nivelCompetencia_id ");
		sql.append("left join NivelCompetencia nc2 on nc2.id = chn2.nivelCompetencia_id ");

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

	public Collection<ConfiguracaoNivelCompetencia> findCompetenciasConfiguracaoNivelCompetenciaFaixaSalarial(Long[] competenciaIds, Long configuracaoNivelCompetenciaFaixaSalarialId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select cnc.competencia_id as competenciaId, COALESCE(a.nome, conhe.nome, h.nome) as competencia, nc.descricao as nivelFaixaDescricao, chn.ordem as nivelFaixaOrdem, cncf.data as dataCNCHistoricoFaixa ");       
		sql.append("from configuracaonivelcompetencia cnc ");
		sql.append("inner join configuracaoNivelCompetenciaFaixaSalarial cncf on cncf.id = cnc.configuracaoNivelCompetenciaFaixaSalarial_id ");
		sql.append("left join Atitude a on a.id = cnc.competencia_id and :tipoAtitude = cnc.tipocompetencia ");
		sql.append("left join Conhecimento conhe on conhe.id = cnc.competencia_id and :tipoConhecimento = cnc.tipocompetencia ");
		sql.append("left join Habilidade h on h.id = cnc.competencia_id and :tipoHabilidade = cnc.tipocompetencia ");
		sql.append("inner join NivelCompetenciaHistorico nch on nch.id = cncf.nivelCompetenciaHistorico_id ");
		sql.append("inner join ConfigHistoricoNivel chn on chn.nivelCompetencia_id = cnc.nivelCompetencia_id and nch.id = chn.NivelCompetenciaHistorico_id ");
		sql.append("inner join NivelCompetencia nc  on nc.id = chn.nivelCompetencia_id  ");
		sql.append("where cnc.configuracaonivelcompetenciafaixasalarial_id = :configuracaoNivelCompetenciaFaixaSalarialId ");
		
		if (competenciaIds != null)
			sql.append("and cnc.competencia_id in (:competenciasIds) ");
		
		sql.append("order by competencia, nc.descricao ");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setCharacter("tipoAtitude", TipoCompetencia.ATITUDE);
		query.setCharacter("tipoConhecimento", TipoCompetencia.CONHECIMENTO);
		query.setCharacter("tipoHabilidade", TipoCompetencia.HABILIDADE);
		query.setLong("configuracaoNivelCompetenciaFaixaSalarialId", configuracaoNivelCompetenciaFaixaSalarialId);

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
		criteria.createCriteria("cnc.configuracaoNivelCompetenciaCandidato", "cncc", Criteria.INNER_JOIN);
		criteria.createCriteria("cncc.candidato", "ca", Criteria.INNER_JOIN);
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
		criteria.createCriteria("cnc.configuracaoNivelCompetenciaCandidato", "cncc", Criteria.INNER_JOIN);
		criteria.createCriteria("cncc.candidato", "c", Criteria.INNER_JOIN);
		criteria.createCriteria("cncc.solicitacao", "s", Criteria.INNER_JOIN);
		
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
		Query query = getSession().createQuery("delete from ConfiguracaoNivelCompetencia cnc where cnc.configuracaoNivelCompetenciaCandidato.id in ( select cncc.id from ConfiguracaoNivelCompetenciaCandidato cncc where cncc.candidato.id = :candidatoId and cncc.solicitacao.id = :solicitacaoId ) ");
		query.setLong("candidatoId", candidatoId);
		query.setLong("solicitacaoId", solicitacaoId);
		query.executeUpdate();
	}

	public void removeBySolicitacaoId(Long solicitacaoId) 
	{
		Query query = getSession().createQuery("delete from ConfiguracaoNivelCompetencia cnc  where cnc.configuracaoNivelCompetenciaCandidato.id in ( select cncc.id from ConfiguracaoNivelCompetenciaCandidato cncc where cncc.solicitacao.id = :solicitacaoId ) ");
		query.setLong("solicitacaoId", solicitacaoId);
		query.executeUpdate();
	}
	
	public Collection<ConfiguracaoNivelCompetencia> findConfiguracaoNivelCompetenciaCandidato(Long configuracaoNivelCompetenciaCandidatoId) {
		ProjectionList p = montaProjectionFindByConfiguracaoNivelCompetenciaFaixaSalarial();
		p.add(Projections.property("cand.nome"), "configuracaoNivelCompetenciaCandidatoCandidatoNome");
		p.add(Projections.property("cand.id"), "configuracaoNivelCompetenciaCandidatoCandidatoId");

		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetencia.class,"cnc");
		criteria.createCriteria("cnc.configuracaoNivelCompetenciaCandidato", "cnc_candidato", Criteria.INNER_JOIN);
		criteria.createCriteria("cnc_candidato.configuracaoNivelCompetenciaFaixaSalarial", "cncf", Criteria.INNER_JOIN);
		criteria.createCriteria("cnc.nivelCompetencia", "nc", Criteria.INNER_JOIN);
		criteria.createCriteria("nc.configHistoricoNiveis", "chn", Criteria.INNER_JOIN);
		criteria.createCriteria("chn.nivelCompetenciaHistorico", "nch", Criteria.INNER_JOIN);
		criteria.createCriteria("cnc_candidato.candidato", "cand", Criteria.INNER_JOIN);

		criteria.add(Expression.eq("cnc_candidato.id", configuracaoNivelCompetenciaCandidatoId));
		criteria.add(Expression.eqProperty("nch.id", "cncf.nivelCompetenciaHistorico.id"));
		
		criteria.addOrder(Order.asc("cand.nome"));
		criteria.addOrder(Order.asc("cand.id"));
		criteria.setProjection(p);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));
		return criteria.list();
	}
	
	public Collection<ConfiguracaoNivelCompetencia> findCompetenciasAndPesos(Long avaliacaoDesempenhoId, Long avaliadoId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct cnc.id, cnc.competencia_Id as competenciaId, cnc.tipocompetencia as tipocompetencia, chn.ordem as ordem, cq.avaliador_id as avaliadorId, cq.pesoAvaliador as pesoAvaliador, nc.descricao, cncf.id as cncfId, chn.nivelcompetenciaHistorico_id as nivelcompetenciaHistoricoId ");
		sql.append("from configuracaonivelcompetenciacolaborador cncc ");
		sql.append("inner join configuracaonivelcompetencia cnc on cncc.id = cnc.configuracaonivelcompetenciacolaborador_id ");
		sql.append("inner join colaboradorquestionario cq on cq.configuracaonivelcompetenciacolaborador_id = cncc.id ");
		sql.append("inner join configuracaonivelcompetenciafaixasalarial cncf on cncf.id = cncc.configuracaonivelcompetenciafaixasalarial_id ");
		sql.append("inner join confighistoriconivel chn on chn.nivelcompetenciaHistorico_id = cncf.nivelcompetenciaHistorico_id and chn.nivelcompetencia_id = cnc.nivelcompetencia_id ");
		sql.append("inner join nivelcompetencia nc on nc.id = chn.nivelcompetencia_id ");
		sql.append("where cncc.colaborador_id = :avaliadoId ");
		sql.append("and cq.avaliacaodesempenho_id  = :avaliacaoDesempenhoId ");
		sql.append("group by cnc.id,cnc.competencia_Id,cnc.tipocompetencia,chn.ordem, cq.avaliador_id, cq.pesoAvaliador, nc.descricao, cncf.id, chn.nivelcompetenciaHistorico_id ");
		sql.append("order by cnc.tipocompetencia, cnc.competencia_Id ");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		query.setLong("avaliadoId", avaliadoId);
		
		Collection<Object[]> resultado = query.list();
		
		Collection<ConfiguracaoNivelCompetencia> lista = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			lista.add(new ConfiguracaoNivelCompetencia(((BigInteger)res[0]).longValue(), ((BigInteger)res[1]).longValue(), (Character)res[2], (Integer)res[3], ((BigInteger)res[4]).longValue(), (res[5]!=null ? (Integer)res[5] : 1), (String)res[6], ((BigInteger)res[7]).longValue(), ((BigInteger)res[8]).longValue()));
		}
		
		return lista;
	}

	public LinkedList<ConfiguracaoNivelCompetencia> findByAvaliacaaDesempenhoAndAvaliado(Long avaliacaoDesempenhoId, Long avaliadoId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct c.id as colabId, c.nome as colabNome, comp.id as compId, comp.nome as compNome, comp.tipo as compTipo, nc.id as ncId, nc.descricao as ncDescricao, chn.ordem as ncOrdem, cg.id as cgId, cg.nome as cgNome ");
		sql.append("from configuracaonivelcompetencia cnc ");
		sql.append("left join competencia comp on comp.id = cnc.competencia_id and comp.tipo = cnc.tipocompetencia ");
		sql.append("inner join configuracaonivelcompetenciacolaborador as cncc on cncc.id = cnc.configuracaonivelcompetenciacolaborador_id ");
		sql.append("inner join configuracaonivelcompetenciafaixasalarial as cncf on cncf.id = cncc.configuracaonivelcompetenciafaixasalarial_id ");
		sql.append("inner join nivelcompetenciahistorico nch on nch.id = cncf.nivelcompetenciahistorico_id ");
		sql.append("inner join confighistoriconivel chn on chn.nivelcompetenciahistorico_id = nch.id ");
		sql.append("inner join nivelcompetencia nc on nc.id = chn.nivelcompetencia_id and nc.id = cnc.nivelcompetencia_id ");
		sql.append("inner join colaboradorquestionario cq on cq.id = cncc.colaboradorquestionario_id ");
		sql.append("inner join colaborador c on c.id = cncc.avaliador_id ");
		sql.append("inner join historicocolaborador hc on hc.colaborador_id = c.id ");
		sql.append("inner join faixasalarial fs on fs.id = hc.faixasalarial_id ");
		sql.append("inner join cargo cg on cg.id = fs.cargo_id ");
		sql.append("where cncc.colaborador_id = :avaliadoId ");
		sql.append("and cq.avaliacaodesempenho_id = :avaliacaoDesempenhoId ");
		sql.append("and hc.data = (select max(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = hc.colaborador_id and hc2.status = :status) ");
		sql.append("order by comp.nome, cg.nome, c.nome ");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("avaliadoId", avaliadoId);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		Collection<Object[]> resultado = query.list();
		LinkedList<ConfiguracaoNivelCompetencia> lista = new LinkedList<ConfiguracaoNivelCompetencia>();
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			lista.add(new ConfiguracaoNivelCompetencia(((BigInteger)res[0]).longValue(), ((String)res[1]), ((BigInteger)res[2]).longValue(), ((String)res[3]), ((String)res[4]).charAt(0), ((BigInteger)res[5]).longValue(), ((String)res[6]), ((Integer)res[7]), ((BigInteger)res[8]).longValue(), ((String)res[9])));
		}
		
		return lista;
	}
}