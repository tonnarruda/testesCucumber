package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;

public class ConfiguracaoNivelCompetenciaColaboradorDaoHibernate extends GenericDaoHibernate<ConfiguracaoNivelCompetenciaColaborador> implements ConfiguracaoNivelCompetenciaColaboradorDao
{
	public ConfiguracaoNivelCompetenciaColaborador findByIdProjection(Long configuracaoNivelCompetenciaColaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaColaborador.class, "cncc");
		criteria.createCriteria("cncc.colaborador", "co", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.faixaSalarial", "fs", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.configuracaoNivelCompetenciaFaixaSalarial", "cncf", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.colaboradorQuestionario", "cq", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.avaliador", "av", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cq.avaliacaoDesempenho", "ad", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cncc.id"), "id");
		p.add(Projections.property("cncc.data"), "data");
		p.add(Projections.property("cncf.id"), "configuracaoNivelCompetenciaFaixaSalarialId");
		p.add(Projections.property("cncf.nivelCompetenciaHistorico.id"), "cncfNivelCompetenciaHistoricoId");
		p.add(Projections.property("cncf.data"), "cncfData");
		p.add(Projections.property("co.id"), "projectionColaboradorId");
		p.add(Projections.property("co.nome"), "projectionColaboradorNome");
		p.add(Projections.property("ca.id"), "projectionCargoId");
		p.add(Projections.property("ca.nome"), "projectionCargoNome");
		p.add(Projections.property("fs.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");
		p.add(Projections.property("ad.id"), "projectionAvaliacaoDesempenhoId");
		p.add(Projections.property("ad.titulo"), "projectionAvaliacaoDesempenhoTitulo");
		p.add(Projections.property("ad.anonima"), "projectionAvaliacaoDesempenhoAnonima");
		p.add(Projections.property("av.id"), "projectionAvaliadorId");
		p.add(Projections.property("av.nome"), "projectionAvaliadorNome");
		p.add(Projections.property("cq.id"), "projectionColaboradorQuestionarioId");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cncc.id", configuracaoNivelCompetenciaColaboradorId));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaColaborador.class));
		
		return (ConfiguracaoNivelCompetenciaColaborador)criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetenciaColaborador> findByColaborador(Long colaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaColaborador.class, "cncc");
		criteria.createCriteria("cncc.colaboradorQuestionario", "cq", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.avaliador", "av", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cq.avaliacaoDesempenho", "ad", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.faixaSalarial", "fs", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cncc.id"), "id");
		p.add(Projections.property("cncc.data"), "data");
		p.add(Projections.property("ca.nome"), "projectionCargoNome");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");
		p.add(Projections.property("ad.id"), "projectionAvaliacaoDesempenhoId");
		p.add(Projections.property("ad.titulo"), "projectionAvaliacaoDesempenhoTitulo");
		p.add(Projections.property("ad.anonima"), "projectionAvaliacaoDesempenhoAnonima");
		p.add(Projections.property("av.id"), "projectionAvaliadorId");
		p.add(Projections.property("av.nome"), "projectionAvaliadorNome");
		p.add(Projections.property("cq.id"), "projectionColaboradorQuestionarioId");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cncc.colaborador.id", colaboradorId));
		criteria.addOrder(Order.desc("cncc.data"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaColaborador.class));
		
		return criteria.list();
	}

	public void removeColaborador(Colaborador colaborador) {
		String queryHQL = "delete from ConfiguracaoNivelCompetenciaColaborador cncc where cncc.colaborador.id = :colaboradorId";

		Query query = getSession().createQuery(queryHQL);

		query.setLong("colaboradorId", colaborador.getId());

		query.executeUpdate();
	}

	public void deleteByFaixaSalarial(Long[] faixaIds) throws Exception {
		if(faixaIds != null && faixaIds.length > 0)
		{
			String hql = "delete ConfiguracaoNivelCompetenciaColaborador where faixaSalarial.id in (:faixaIds)";
			Query query = getSession().createQuery(hql);

			query.setParameterList("faixaIds", faixaIds, Hibernate.LONG);
			query.executeUpdate();		
		}
	}

	public ConfiguracaoNivelCompetenciaColaborador findByData(Date data, Long colaboradorId, Long avaliadorId, Long colaboradorQuestionarioId) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaColaborador.class, "cncc");
		criteria.createCriteria("cncc.colaborador", "co", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.colaboradorQuestionario", "cq", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.faixaSalarial", "fs", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cncc.id"), "id");
		p.add(Projections.property("cncc.data"), "data");
		p.add(Projections.property("co.nome"), "projectionColaboradorNome");
		p.add(Projections.property("ca.nome"), "projectionCargoNome");
		p.add(Projections.property("fs.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");
		p.add(Projections.property("cncc.configuracaoNivelCompetenciaFaixaSalarial.id"), "configuracaoNivelCompetenciaFaixaSalarialId");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("co.id", colaboradorId));
		criteria.add(Expression.or(Expression.eq("cq.id", colaboradorQuestionarioId),Expression.isNull("cq.id")));
		criteria.add(Expression.or(Expression.eq("cncc.avaliador.id", avaliadorId),Expression.isNull("cncc.avaliador.id")));
		criteria.add(Expression.eq("cncc.data", data));
		criteria.addOrder(Order.desc("cncc.data"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaColaborador.class));
		criteria.setMaxResults(1);
		
		return (ConfiguracaoNivelCompetenciaColaborador) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetenciaColaborador> colabsComDependenciaComCompetenciasDaFaixaSalarial(Long faixaSalarialId, Date dataInicial, Date dataFinal)
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaColaborador.class, "cncc");
		criteria.createCriteria("cncc.colaborador", "co");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.nome"), "colaboradorNome");
		criteria.setProjection(Projections.distinct(p));
				
		criteria.add(Expression.eq("cncc.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.ge("cncc.data", dataInicial));
		
		if(dataFinal != null)
			criteria.add(Expression.lt("cncc.data", dataFinal));

		criteria.addOrder(Order.asc("co.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaColaborador.class));
		
		return criteria.list();	
	}

	public boolean existeConfigCompetenciaParaAFaixaDestehistorico(Long historicoColaboradorId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select cncc.id "); 
		hql.append("from ConfiguracaoNivelCompetenciaColaborador cncc "); 
		hql.append("where cncc.faixaSalarial.id in ( ");
		hql.append("								select hc.faixaSalarial.id from HistoricoColaborador hc ");
		hql.append("								where hc.colaborador.id = cncc.colaborador.id and hc.id = :historicoColaboradorId ");
		hql.append("								) ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("historicoColaboradorId", historicoColaboradorId);
		
		return query.list().size() > 0;
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetenciaColaborador> findByDataAndFaixaSalarial(Date dataInicio, Date dataFim, Long faixaSalarialId) {
		ProjectionList projectionList = Projections.projectionList().create();
		projectionList.add(Projections.property("cncc.id"), "id");
		projectionList.add(Projections.property("cncc.data"), "data");
		projectionList.add(Projections.property("cncc.faixaSalarial.id"), "projectionFaixaSalarialId");
		projectionList.add(Projections.property("cncc.colaborador.id"), "projectionColaboradorId");
		projectionList.add(Projections.property("cncf.id"), "configuracaoNivelCompetenciaFaixaSalarialId");
		projectionList.add(Projections.property("cncf.data"), "configuracaoNivelCompetenciaFaixaSalarialData");
		projectionList.add(Projections.property("cncf.nivelCompetenciaHistorico.id"), "cncfNivelCompetenciaHistoricoId");
		projectionList.add(Projections.property("av.id"), "projectionAvaliadorId");
		projectionList.add(Projections.property("av.nome"), "projectionAvaliadorNome");
		projectionList.add(Projections.property("co.id"), "projectionColaboradorId");
		projectionList.add(Projections.property("co.nome"), "projectionColaboradorNome");
		projectionList.add(Projections.property("cq.id"), "projectionColaboradorQuestionarioId");
		projectionList.add(Projections.property("avd.id"), "projectionAvaliacaoDesempenhoId");
		projectionList.add(Projections.property("avd.anonima"), "projectionAvaliacaoDesempenhoAnonima");
		
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaColaborador.class, "cncc");
		criteria.createCriteria("cncc.configuracaoNivelCompetenciaFaixaSalarial", "cncf", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("cncc.colaborador", "co", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("cncc.avaliador", "av", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.colaboradorQuestionario", "cq", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cq.avaliacaoDesempenho", "avd", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Expression.eq("cncc.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.ge("cncc.data", dataInicio));
		criteria.add(Expression.le("cncc.data", dataFim));
		criteria.setProjection(projectionList);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaColaborador.class));
		
		return criteria.list();	
	}

	@SuppressWarnings("unchecked")
	public Collection<Colaborador> findAvaliadores(Long avaliacaoDesempenhoId, Long avaliadoId) {
		DetachedCriteria subQueryHc = montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO);
		
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaColaborador.class, "cncc");
		criteria.createCriteria("cncc.colaboradorQuestionario", "cq", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("cncc.avaliador", "c", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("c.historicoColaboradors", "hc", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("fs.cargo", "cg", CriteriaSpecification.INNER_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("cg.nome"), "cargoNomeProjection");
		p.add(Projections.property("fs.nome"), "faixaSalarialNomeProjection");
		criteria.setProjection(Projections.distinct(p));
		
		criteria.add(Expression.eq("cq.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
		criteria.add(Expression.eq("cncc.colaborador.id", avaliadoId));
		criteria.add(Expression.eq("cq.respondida", true));
		criteria.add(Subqueries.propertyEq("hc.data", subQueryHc));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));
		
		return criteria.list();
	}

	private DetachedCriteria montaSubQueryHistoricoColaborador(Date data, Integer status)
	{
		return DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
				.setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"))
				.add(Restrictions.le("hc2.data", data))
				.add(Restrictions.eq("hc2.status", status));
	}
}
