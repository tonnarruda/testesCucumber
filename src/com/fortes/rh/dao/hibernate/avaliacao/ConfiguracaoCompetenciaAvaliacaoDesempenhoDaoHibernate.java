package com.fortes.rh.dao.hibernate.avaliacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;

public class ConfiguracaoCompetenciaAvaliacaoDesempenhoDaoHibernate extends GenericDaoHibernate<ConfiguracaoCompetenciaAvaliacaoDesempenho> implements ConfiguracaoCompetenciaAvaliacaoDesempenhoDao
{
	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> findByAvaliacaoDesempenho(Long avaliacaoDesempenhoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ccad");
		
		//Nao aumentar a projection, tem uma regra para o id do colaborador e avaliador
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ccad.id"), "id");
		p.add(Projections.property("ccad.avaliacaoDesempenho.id"), "projectionAvaliacaoDesempenhoId");
		p.add(Projections.property("ccad.configuracaoNivelCompetenciaFaixaSalarial.id"), "projectionConfiguracaoNivelCompetenciaFaixaSalarialId");
		p.add(Projections.property("ccad.avaliador.id"), "projectionAvaliadorId");
		p.add(Projections.property("ccad.competenciaId"), "competenciaId");
		p.add(Projections.property("ccad.tipoCompetencia"), "tipoCompetencia");
		//Nao aumentar a projection
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("ccad.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Long faixaSalarialId, Long avaliacaoDesempenhoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ccad");
		criteria.createCriteria("ccad.configuracaoNivelCompetenciaFaixaSalarial", "ccncf");
		
		//Nao aumentar a projection, tem uma regra para o id do colaborado e avaliador
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ccad.id"), "id");
		p.add(Projections.property("ccad.avaliacaoDesempenho.id"), "projectionAvaliacaoDesempenhoId");
		p.add(Projections.property("ccncf.id"), "projectionConfiguracaoNivelCompetenciaFaixaSalarialId");
		p.add(Projections.property("ccad.avaliador.id"), "projectionAvaliadorId");
		p.add(Projections.property("ccad.competenciaId"), "competenciaId");
		p.add(Projections.sqlProjection("(select nome from competencia where id = {alias}.competencia_id and {alias}.tipoCompetencia = tipo) as competenciaDescricao", new String[] {"competenciaDescricao"}, new Type[] {Hibernate.STRING}), "competenciaDescricao");
		p.add(Projections.property("ccad.tipoCompetencia"), "tipoCompetencia");
		//Nao aumentar a projection
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("ccad.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
		criteria.add(Expression.eq("ccad.avaliador.id", avaliadorId));
		criteria.add(Expression.eq("ccncf.faixaSalarial.id", faixaSalarialId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public void replaceConfiguracaoNivelCompetenciaFaixaSalarial(ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial) {
		String hql = "update ConfiguracaoCompetenciaAvaliacaoDesempenho ccad set ccad.configuracaoNivelCompetenciaFaixaSalarial.id = :configuracaoNivelCompetenciaFaixaSalarialId ";
		hql += " where ccad.id in ( select ccad2.id from ConfiguracaoCompetenciaAvaliacaoDesempenho ccad2 ";
		hql += " left join ccad2.configuracaoNivelCompetenciaFaixaSalarial cncf ";
		hql += " left join ccad2.avaliacaoDesempenho av ";
		hql += " where cncf.faixaSalarial.id = :faixaSalarialId and av.liberada = :liberada ) ";

		Query query = getSession().createQuery(hql);

		query.setLong("configuracaoNivelCompetenciaFaixaSalarialId", configuracaoNivelCompetenciaFaixaSalarial.getId());
		query.setLong("faixaSalarialId", configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId());
		query.setBoolean("liberada", false);

		query.executeUpdate();
	}
	
	public void removeByCompetenciasQueNaoPermaneceram(Long[] competenciasIds, Long faixaSalarialId, Character tipo) {
		String hql = "delete from ConfiguracaoCompetenciaAvaliacaoDesempenho ";
		hql += " where id in ( select ccad2.id from ConfiguracaoCompetenciaAvaliacaoDesempenho ccad2 ";
		hql += " left join ccad2.configuracaoNivelCompetenciaFaixaSalarial cncf ";
		hql += " left join ccad2.avaliacaoDesempenho av ";
		hql += " where cncf.faixaSalarial.id = :faixaSalarialId ";
		hql += " and ccad2.competenciaId in ( :competenciasIds ) ";
		hql += " and ccad2.tipoCompetencia = :tipo and av.liberada = false ) ";

		Query query = getSession().createQuery(hql);

		query.setParameterList("competenciasIds", competenciasIds);
		query.setLong("faixaSalarialId", faixaSalarialId);
		query.setCharacter("tipo", tipo);

		query.executeUpdate();
	}
	
	public void removeByAvaliacaoDesempenho(Long avaliacaoDesempenhoId) {
		String hql = "delete from ConfiguracaoCompetenciaAvaliacaoDesempenho where avaliacaoDesempenho.id = :avaliacaoDesempenhoId ";

		Query query = getSession().createQuery(hql);

		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);

		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<FaixaSalarial> findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(Long avaliacaoDesempenhoId) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select distinct new FaixaSalarial(fs.id, fs.nome, ca.nome, cncf.id) ");
		hql.append("from ConfiguracaoCompetenciaAvaliacaoDesempenho as ccad ");
		hql.append("inner join ccad.configuracaoNivelCompetenciaFaixaSalarial as cncf ");
		hql.append("left join cncf.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("where ccad.avaliacaoDesempenho.id = :avaliacaoDesempenhoId ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		
		return query.list();
	}
	
	public boolean existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(Long avaliacaoDesempenhoId){
		StringBuilder sql = new StringBuilder();

		sql.append("select * from configuracaocompetenciaavaliacaodesempenho ccad ");
		sql.append("join configuracaonivelcompetenciafaixasalarial cncf on cncf.id = ccad.configuracaonivelcompetenciafaixasalarial_id ");
		sql.append("join configuracaonivelcompetenciafaixasalarial cncfs on cncfs.data > cncf.data and cncf.faixasalarial_id = cncfs.faixasalarial_id ");
		sql.append("where ccad.avaliacaoDesempenho_id = :avaliacaoDesempenhoId ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		
		return query.list().size() > 0;
	}

	public boolean existe(Long configuracaoNivelCompetenciaFaixaSalarialId, Long avaliadorId, Long avaliacaoDesempenhoId) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoCompetenciaAvaliacaoDesempenho.class, "ccad");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ccad.id"), "id");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("ccad.configuracaoNivelCompetenciaFaixaSalarial.id", configuracaoNivelCompetenciaFaixaSalarialId));
		criteria.add(Expression.eq("ccad.avaliador.id", avaliadorId));
		criteria.add(Expression.eq("ccad.avaliacaoDesempenho.id", avaliacaoDesempenhoId));

		return criteria.list().size() > 0;
	}
}