package com.fortes.rh.dao.hibernate.captacao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaCandidatoDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCandidato;

@Component
public class ConfiguracaoNivelCompetenciaCandidatoDaoHibernate extends GenericDaoHibernate<ConfiguracaoNivelCompetenciaCandidato> implements ConfiguracaoNivelCompetenciaCandidatoDao
{
	public ConfiguracaoNivelCompetenciaCandidato findByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cncc.id"), "id");
		p.add(Projections.property("cncc.data"), "data");
		p.add(Projections.property("cncc.candidato.id"), "candidatoId");
		p.add(Projections.property("cand.nome"), "candidatoNome");
		p.add(Projections.property("cncc.solicitacao.id"), "solicitacaoId");
		p.add(Projections.property("cncf.id"), "configuracaoNivelCompetenciaFaixaSalarialId");
		p.add(Projections.property("cncf.nivelCompetenciaHistorico.id"), "CNCFNivelCompetenciaHistoricoId");

		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaCandidato.class, "cncc");
		criteria.createCriteria("cncc.configuracaoNivelCompetenciaFaixaSalarial", "cncf", Criteria.LEFT_JOIN);
		criteria.createCriteria("cncc.candidato", "cand");
		criteria.add(Expression.eq("cncc.candidato.id", candidatoId));
		criteria.add(Expression.eq("cncc.solicitacao.id", solicitacaoId));
		
		criteria.setProjection(p);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaCandidato.class));
		return (ConfiguracaoNivelCompetenciaCandidato) criteria.uniqueResult();
	}

	public void removeByCandidatoAndSolicitacao(Long candidatoId,Long solicitacaoId) {
		String queryHQL = "delete from ConfiguracaoNivelCompetenciaCandidato cncc where cncc.candidato.id = :candidatoId and cncc.solicitacao.id = :solicitacaoId";
		getSession().createQuery(queryHQL).setLong("candidatoId", candidatoId).setLong("solicitacaoId", solicitacaoId).executeUpdate();		
	}
	
	public void removeByCandidato(Long candidatoId) {
		String queryHQL = "delete from ConfiguracaoNivelCompetenciaCandidato cncc where cncc.candidato.id = :candidatoId ";
		getSession().createQuery(queryHQL).setLong("candidatoId", candidatoId).executeUpdate();		
	}
}