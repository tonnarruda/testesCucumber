package com.fortes.rh.dao.hibernate.avaliacao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AnaliseDesempenhoOrganizacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

@SuppressWarnings("unchecked")
public class AvaliacaoDesempenhoDaoHibernate extends GenericDaoHibernate<AvaliacaoDesempenho> implements AvaliacaoDesempenhoDao
{
	public Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa, Character tipoModeloAvaliacao) {
		
		Criteria criteria = getSession().createCriteria(AvaliacaoDesempenho.class, "a");
		criteria.createCriteria("a.avaliacao", "avaliacao", Criteria.LEFT_JOIN);
		criteria.createCriteria("a.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.inicio"), "inicio");
		p.add(Projections.property("a.fim"), "fim");
		p.add(Projections.property("a.titulo"), "titulo");
		p.add(Projections.property("a.liberada"), "liberada");
		p.add(Projections.property("avaliacao.id"), "projectionAvaliacaoId");
		p.add(Projections.property("avaliacao.titulo"), "projectionAvaliacaoTitulo");
		p.add(Projections.property("emp.id"), "empresaId");
		p.add(Projections.property("emp.nome"), "empresaNome");

		criteria.setProjection(p);

		if(tipoModeloAvaliacao != null)
			criteria.add(Expression.eq("avaliacao.tipoModeloAvaliacao", tipoModeloAvaliacao));			
	
		if(empresaId != null && empresaId > -1)
			criteria.add(Expression.eq("emp.id", empresaId));

		if(ativa != null)
			criteria.add(Expression.or(Expression.eq("avaliacao.ativo", ativa), Expression.isNull("avaliacao.id")));
		
		criteria.addOrder(Order.asc("a.titulo"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	public Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada, Long... empresasIds)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		criteria.createCriteria("cq.avaliacaoDesempenho", "ad");
		criteria.createCriteria("cq.avaliacao", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("ad.empresa", "e");
		
		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.distinct(Projections.property("ad.id")), "id");
		p.add(Projections.property("ad.titulo"), "titulo");
		p.add(Projections.property("ad.inicio"), "inicio");
		p.add(Projections.property("ad.fim"), "fim");
		p.add(Projections.property("e.nome"), "empresaNome");

		criteria.setProjection(p);
		
		if(avaliadorId != null)
			criteria.add(Expression.eq("cq.avaliador.id", avaliadorId));
		
		if(liberada != null)
			criteria.add(Expression.eq("ad.liberada", liberada));
		
		if(empresasIds != null && empresasIds.length > 0)
			criteria.add(Expression.in("ad.empresa.id", empresasIds));

		criteria.addOrder(Order.asc("ad.titulo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public AvaliacaoDesempenho findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"ad");
		criteria.createCriteria("ad.avaliacao", "avaliacao", Criteria.LEFT_JOIN);
		criteria.createCriteria("ad.empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ad.id"), "id");
		p.add(Projections.property("ad.inicio"), "inicio");
		p.add(Projections.property("ad.fim"), "fim");
		p.add(Projections.property("ad.titulo"), "titulo");
		p.add(Projections.property("ad.anonima"), "anonima");
		p.add(Projections.property("ad.liberada"), "liberada");
		p.add(Projections.property("ad.permiteAutoAvaliacao"), "permiteAutoAvaliacao");
		p.add(Projections.property("ad.exibeResultadoAutoAvaliacao"), "exibeResultadoAutoAvaliacao");
		p.add(Projections.property("ad.avaliacao.id"), "projectionAvaliacaoId");
		p.add(Projections.property("e.id"), "empresaId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("ad.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return (AvaliacaoDesempenho) criteria.uniqueResult();
	}

	public void liberarOrBloquear(Long id, boolean liberar)
	{
		String hql = "update AvaliacaoDesempenho ad  set ad.liberada = :liberada where ad.id = :id"; 

		Query query = getSession().createQuery(hql);
		query.setLong("id", id);
		query.setBoolean("liberada", liberar);
		
		query.executeUpdate();
	}
	
	public Integer findCountTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String tituloBusca, Long avaliacaoId, Boolean liberada) {
		Criteria criteria = geraCriteria(page, pagingSize, periodoInicial, periodoFinal, empresaId, tituloBusca, avaliacaoId, liberada);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.rowCount());
		criteria.setProjection(p);
		
		return (Integer) criteria.uniqueResult();
	}
	
	public Collection<AvaliacaoDesempenho> findTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String tituloBusca, Long avaliacaoId, Boolean liberada) {
		Criteria criteria = geraCriteria(page, pagingSize, periodoInicial, periodoFinal, empresaId, tituloBusca, avaliacaoId, liberada);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.inicio"), "inicio");
		p.add(Projections.property("a.fim"), "fim");
		p.add(Projections.property("a.titulo"), "titulo");
		p.add(Projections.property("a.liberada"), "liberada");
		p.add(Projections.property("avaliacao.id"), "projectionAvaliacaoId");
		p.add(Projections.property("avaliacao.titulo"), "projectionAvaliacaoTitulo");
		p.add(Projections.property("avaliacao.avaliarCompetenciasCargo"), "projectionAvaliacaoAvaliarCompetenciasCargo");
		p.add(Projections.property("emp.id"), "empresaId");
		p.add(Projections.sqlProjection("case when (select exists(select id from ColaboradorQuestionario where avaliacaoDesempenho_id = this_.id and respondida = true)) then true else false end as possuiResposta ", new String[] {"possuiResposta"}, new Type[] {Hibernate.BOOLEAN}), "possuiResposta");		
		criteria.setProjection(p);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	private Criteria geraCriteria(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String tituloBusca, Long avaliacaoId, Boolean liberada) {
		Criteria criteria = getSession().createCriteria(AvaliacaoDesempenho.class, "a");
		criteria.createCriteria("a.avaliacao", "avaliacao", Criteria.LEFT_JOIN);
		criteria.createCriteria("a.empresa", "emp");

		if(empresaId != null)
			criteria.add(Expression.eq("emp.id", empresaId));
		
		if(tituloBusca != null && !tituloBusca.trim().equals(""))
			criteria.add(Expression.like("a.titulo", "%"+ tituloBusca +"%").ignoreCase() );
		
		if(avaliacaoId != null)
			criteria.add(Expression.eq("avaliacao.id", avaliacaoId));

		if(liberada != null)
			criteria.add(Expression.eq("a.liberada", liberada));

		if(periodoInicial != null && periodoFinal != null)
		{
			Disjunction disjunction = Expression.disjunction();
			disjunction.add(Expression.or(
								Expression.and(Expression.ge("a.inicio", periodoInicial), Expression.le("a.inicio", periodoFinal)),
								Expression.and(Expression.ge("a.fim", periodoInicial), Expression.le("a.fim", periodoFinal))
								)
							);
			disjunction.add(Expression.or(
								Expression.and(Expression.le("a.inicio", periodoInicial), Expression.ge("a.fim", periodoFinal)),
								Expression.and(Expression.le("a.inicio", periodoInicial), Expression.ge("a.fim", periodoFinal))
								)
							);
			criteria.add(disjunction);
		}
		
		if (pagingSize != null)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
			
			criteria.addOrder(Order.desc("a.inicio"));
			criteria.addOrder(Order.asc("a.titulo"));
		}
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria;
	}
	
	public Collection<AvaliacaoDesempenho> findIdsAvaliacaoDesempenho(Long avaliacaoId) 
	{
		Criteria criteria = getSession().createCriteria(AvaliacaoDesempenho.class, "ad");
		criteria.createCriteria("ad.avaliacao", "a", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ad.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("a.id", avaliacaoId));
		criteria.addOrder(Order.asc("ad.id"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public Collection<AvaliacaoDesempenho> findComCompetencia(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaColaborador.class, "cncc");
		criteria.createCriteria("cncc.colaboradorQuestionario", "cq", Criteria.INNER_JOIN);
		criteria.createCriteria("cq.avaliacaoDesempenho", "ad", Criteria.INNER_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ad.id"), "id");
		p.add(Projections.property("ad.titulo"), "titulo");
		criteria.setProjection(Projections.distinct(p));
		
		criteria.add(Expression.eq("ad.liberada", true));
		criteria.add(Expression.eq("ad.empresa.id", empresaId));
		
		criteria.addOrder(Order.asc("ad.titulo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public boolean isExibiNivelCompetenciaExigido(Long avaliacaoDesempenhoId) {
		String hql = "select avd.exibirNivelCompetenciaExigido from AvaliacaoDesempenho avd where avd.id = :avaliacaoDesempenhoId"; 

		Query query = getSession().createQuery(hql);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		
		return (Boolean) query.uniqueResult();
	}

	public Collection<AvaliacaoDesempenho> findByCncfId(Long configuracaoNivelCompetenciaFaixaSalarialId) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ad.id"), "id");
		p.add(Projections.property("ad.titulo"), "titulo");

		Criteria criteria = getSession().createCriteria(ConfiguracaoCompetenciaAvaliacaoDesempenho.class, "ccad");
		criteria.createCriteria("ccad.avaliacaoDesempenho", "ad", Criteria.INNER_JOIN);
		criteria.add(Expression.eq("ccad.configuracaoNivelCompetenciaFaixaSalarial.id", configuracaoNivelCompetenciaFaixaSalarialId));
		
		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AvaliacaoDesempenho.class));
		return criteria.list();
	}

	public Collection<AvaliacaoDesempenho> findByModelo(Long modeloId) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ad.id"), "id");
		p.add(Projections.property("ad.titulo"), "titulo");

		Criteria criteria = getSession().createCriteria(AvaliacaoDesempenho.class, "ad");
		criteria.add(Expression.eq("ad.avaliacao.id", modeloId));
		
		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AvaliacaoDesempenho.class));
		return criteria.list();
	}

	public Collection<Estabelecimento> findEstabelecimentosDosParticipantes(Long[] avaliacoesDesempenhoIds) {
		Criteria criteria = criteriaDoHistoricoDosParticipantes(avaliacoesDesempenhoIds);
		criteria.createCriteria("hc.estabelecimento", "e", Criteria.INNER_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		criteria.setProjection(p);
		
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Estabelecimento.class));
		
		return criteria.list();
	}

	public Collection<AreaOrganizacional> findAreasOrganizacionaisDosParticipantes(Long[] avaliacoesDesempenhoIds) {
		Criteria criteria = criteriaDoHistoricoDosParticipantes(avaliacoesDesempenhoIds);
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.INNER_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ao.id"), "id");
		p.add(Projections.property("ao.ativo"), "ativo");
		p.add(Projections.sqlProjection("monta_familia_area(ao4_.id) as nome", new String[] {"nome"}, new Type[] {Hibernate.TEXT}), "nome");
		criteria.setProjection(p);
		
		criteria.addOrder(Order.asc("ao.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AreaOrganizacional.class));
		
		return criteria.list();
	}

	public Collection<Cargo> findCargosDosParticipantes(Long[] avaliacoesDesempenhoIds) {
		Criteria criteria = criteriaDoHistoricoDosParticipantes(avaliacoesDesempenhoIds);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.INNER_JOIN);
		criteria.createCriteria("fs.cargo", "car", Criteria.INNER_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("car.id"), "id");
		p.add(Projections.property("car.nomeMercado"), "nomeMercado");
		p.add(Projections.property("car.ativo"), "ativo");
		criteria.setProjection(p);
		
		criteria.addOrder(Order.asc("car.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cargo.class));
		
		return criteria.list();
	}
	
	private Criteria criteriaDoHistoricoDosParticipantes(Long[] avaliacoesDesempenhoIds) {
		
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
				.setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "co.id"))
				.add(Restrictions.leProperty("hc2.data", "ad.inicio"))
				.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
		
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		criteria.createCriteria("cq.avaliacaoDesempenho", "ad", Criteria.INNER_JOIN);
		criteria.createCriteria("cq.colaborador", "co", Criteria.INNER_JOIN);
		criteria.createCriteria("co.historicoColaboradors", "hc", Criteria.INNER_JOIN);
		
		criteria.add(Subqueries.propertyEq("hc.data", subQueryHc));
		criteria.add(Expression.in("ad.id", avaliacoesDesempenhoIds));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	public Collection<AnaliseDesempenhoOrganizacao> findAnaliseDesempenhoOrganizacao(Long[] avaliacoesDesempenhoIds, Long[] estabelecimentosIds, Long[] cargosIds, Long[] areasIds, Long[] competenciasIds, String agrupamentoDasCompetencias, Long empresaId) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select conf.compId, conf.compNome, conf.agrupadorId, conf.agrupadorNome, ");
		sql.append("	round (((cast(sum(conf.pesoavaliador_ordem) as numeric) / (count(conf.avaliador_id) * "); 
		sql.append("	(select max(ordem) from confighistoriconivel chn2 where chn2.nivelcompetenciahistorico_id = conf.nivelcompetenciahistorico_id)))*100), 2) as performance ");
		sql.append("from ( ");
		sql.append("	select ad.id, cncc.colaborador_id, cnc.competencia_id as compId, comp.nome as compNome, agrupador.id as agrupadorId, agrupador.nome as agrupadorNome, ");
		sql.append("	    cncc.avaliador_id, chn.nivelcompetenciahistorico_id, (cq.pesoavaliador*chn.ordem) as pesoavaliador_ordem ");
		sql.append("	from configuracaonivelcompetenciacolaborador cncc ");
		sql.append("	inner join configuracaonivelcompetencia cnc on cnc.configuracaonivelcompetenciacolaborador_id = cncc.id ");
		sql.append("	inner join competencia comp on comp.id = cnc.competencia_id and comp.tipo = cnc.tipocompetencia ");
		sql.append("	inner join configuracaonivelcompetenciafaixasalarial cncf on cncf.id = cncc.configuracaonivelcompetenciafaixasalarial_id ");
		sql.append("	left join confighistoriconivel chn on cncf.nivelcompetenciahistorico_id = chn.nivelcompetenciahistorico_id and chn.nivelcompetencia_id = cnc.nivelcompetencia_id ");
		sql.append("	inner join colaboradorquestionario cq on cq.id = cncc.colaboradorquestionario_id and cq.respondida ");
		sql.append("	inner join avaliacaodesempenho ad on ad.id = cq.avaliacaodesempenho_id ");
		sql.append("	inner join historicocolaborador hc on cncc.colaborador_id = hc.colaborador_id "); 
		sql.append("		  and hc.data = (select max(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = hc.colaborador_id and hc2.status = 1 and hc2.data <= ad.inicio) ");

		if(agrupamentoDasCompetencias.equals(AnaliseDesempenhoOrganizacao.POR_CARGO)){
	    	sql.append("	inner join faixasalarial fs on fs.id = hc.faixasalarial_id ");
	    	sql.append("	inner join cargo agrupador on agrupador.id = fs.cargo_id ");
	    } else if(agrupamentoDasCompetencias.equals(AnaliseDesempenhoOrganizacao.POR_AREA)){
	    	sql.append("	inner join areaorganizacional agrupador on agrupador.id = hc.areaorganizacional_id ");
	    } else {
	    	sql.append("	inner join empresa agrupador on agrupador.id = ad.empresa_id ");
	    }
		sql.append("	where ad.empresa_id = :empresaId ");
		sql.append("	and ad.id in (:avaliacoesDesempenhoIds) ");
		sql.append("	and hc.estabelecimento_id in (:estabelecimentosIds) ");
		sql.append("	and cnc.competencia_id in (:competenciasIds) ");
		
	    if(!agrupamentoDasCompetencias.equals(AnaliseDesempenhoOrganizacao.POR_EMPRESA))
	    	sql.append("	and agrupador.id in (:agrupadorIds) ");
		
	    sql.append("	group by ad.id, cncc.colaborador_id, cnc.competencia_id, comp.nome, agrupador.id, agrupador.nome, cncc.avaliador_id, chn.nivelcompetenciahistorico_id, cq.pesoavaliador, chn.ordem ");
		sql.append("	order by  cncc.colaborador_id, cnc.competencia_id, agrupador.id ");
		sql.append(") as conf ");
		sql.append("group by  conf.compId, conf.compNome, conf.agrupadorId, conf.agrupadorNome, conf.nivelcompetenciahistorico_id ");
		sql.append("order by conf.compNome, conf.agrupadorNome ");
		
		Query query = getSession().createSQLQuery(sql.toString());

		query.setLong("empresaId", empresaId);
		query.setParameterList("avaliacoesDesempenhoIds", avaliacoesDesempenhoIds);
		query.setParameterList("estabelecimentosIds", estabelecimentosIds);
		query.setParameterList("competenciasIds", competenciasIds);

	    if(agrupamentoDasCompetencias.equals(AnaliseDesempenhoOrganizacao.POR_CARGO)){
	    	query.setParameterList("agrupadorIds", cargosIds);
	    } else if(agrupamentoDasCompetencias.equals(AnaliseDesempenhoOrganizacao.POR_AREA)){
	    	query.setParameterList("agrupadorIds", areasIds);
	    }
		Collection<Object[]> lista = query.list();

		Collection<AnaliseDesempenhoOrganizacao> analiseDesempenhoOrganizacaos = new ArrayList<AnaliseDesempenhoOrganizacao>();
		
		for (Iterator<Object[]> it = lista.iterator(); it.hasNext();){
			Object[] obj = it.next();
			
			AnaliseDesempenhoOrganizacao analiseDesempenhoOrganizacao = new AnaliseDesempenhoOrganizacao((String) obj[1],(String) obj[3],((BigDecimal) obj[4]).doubleValue());
			analiseDesempenhoOrganizacaos.add(analiseDesempenhoOrganizacao);
		}
		
		return analiseDesempenhoOrganizacaos;
	}
}