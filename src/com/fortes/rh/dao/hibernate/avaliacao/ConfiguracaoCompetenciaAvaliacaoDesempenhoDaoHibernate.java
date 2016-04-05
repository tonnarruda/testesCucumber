package com.fortes.rh.dao.hibernate.avaliacao;

import java.util.Collection;
import java.util.LinkedList;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;

@SuppressWarnings("unchecked")
public class ConfiguracaoCompetenciaAvaliacaoDesempenhoDaoHibernate extends GenericDaoHibernate<ConfiguracaoCompetenciaAvaliacaoDesempenho> implements ConfiguracaoCompetenciaAvaliacaoDesempenhoDao
{
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
		p.add(Projections.sqlProjection(existeResposta(), new String[] {"possuiResposta"}, new Type[] {Hibernate.BOOLEAN}), "possuiResposta");
		p.add(Projections.property("ccad.tipoCompetencia"), "tipoCompetencia");
		//Nao aumentar a projection
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("ccad.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
		criteria.add(Expression.eq("ccad.avaliador.id", avaliadorId));
		criteria.add(Expression.eq("ccncf.faixaSalarial.id", faixaSalarialId));
		
		criteria.addOrder(Order.asc("ccad.tipoCompetencia"));
		criteria.addOrder(Order.asc("competenciaDescricao"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	private String existeResposta(){
		StringBuilder stringBuilder = new StringBuilder(); 
		stringBuilder.append("exists (select cncc.id from ConfiguracaoNivelCompetenciaColaborador cncc");
		stringBuilder.append("			inner join ColaboradorQuestionario cq on cq.id = cncc.colaboradorQuestionario_id");
		stringBuilder.append("			inner join ConfiguracaoNivelCompetencia cnc on cnc.configuracaoNivelCompetenciaColaborador_id =  cncc.id ");
		stringBuilder.append("			where cq.avaliacaoDesempenho_id = {alias}.avaliacaoDesempenho_id");
		stringBuilder.append("			and cq.avaliador_id = {alias}.avaliador_id");
		stringBuilder.append("			and cnc.competencia_id = {alias}.competencia_id");
		stringBuilder.append("			and cnc.tipocompetencia = {alias}.tipocompetencia");
		stringBuilder.append(") as possuiResposta");
		return stringBuilder.toString();
	}
	
	public ConfiguracaoNivelCompetenciaFaixaSalarial getConfiguracaoNivelCompetenciaFaixaSalarial(Long avaliadorId, Long faixaSalarialId, Long avaliacaoDesempenhoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ccad");
		criteria.createCriteria("ccad.configuracaoNivelCompetenciaFaixaSalarial", "ccncf");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("ccncf.id")), "id");
		p.add(Projections.property("ccncf.nivelCompetenciaHistorico.id"), "nivelCompetenciaHistoricoId");
		p.add(Projections.property("ccncf.data"), "data");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("ccad.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
		criteria.add(Expression.eq("ccad.avaliador.id", avaliadorId));
		criteria.add(Expression.eq("ccncf.faixaSalarial.id", faixaSalarialId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaFaixaSalarial.class));
		
		return (ConfiguracaoNivelCompetenciaFaixaSalarial) criteria.uniqueResult();
	}
	
	public void removeByAvaliacaoDesempenho(Long avaliacaoDesempenhoId) {
		String hql = "delete from ConfiguracaoCompetenciaAvaliacaoDesempenho where avaliacaoDesempenho.id = :avaliacaoDesempenhoId ";

		Query query = getSession().createQuery(hql);

		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);

		query.executeUpdate();
	}
	
	public Collection<FaixaSalarial> findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(Long avaliacaoDesempenhoId) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select distinct new FaixaSalarial(fs.id, fs.nome, ca.nome, cncf.id) ");
		hql.append("from ConfiguracaoCompetenciaAvaliacaoDesempenho as ccad ");
		hql.append("inner join ccad.configuracaoNivelCompetenciaFaixaSalarial as cncf ");
		hql.append("left join cncf.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("where ccad.avaliacaoDesempenho.id = :avaliacaoDesempenhoId ");
		hql.append("order by ca.nome, fs.nome ");

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

	public boolean existe(Long configuracaoNivelCompetenciaFaixaSalarialId, Long avaliacaoDesempenhoId) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoCompetenciaAvaliacaoDesempenho.class, "ccad");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ccad.id"), "id");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("ccad.avaliacaoDesempenho.id", avaliacaoDesempenhoId));

		if(configuracaoNivelCompetenciaFaixaSalarialId != null)
			criteria.add(Expression.eq("ccad.configuracaoNivelCompetenciaFaixaSalarial.id", configuracaoNivelCompetenciaFaixaSalarialId));

		return criteria.list().size() > 0;
	}

	public Collection<Colaborador> findColabSemCompetenciaConfiguradaByAvalDesempenhoId(Long avaliacaoDesempenhoId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select c.nome as colabNome from Colaborador c ");
		sql.append("where c.id in ( ");
		sql.append("	select distinct avaliador_id ");
		sql.append("	from Colaboradorquestionario  ");
		sql.append("	where avaliacaodesempenho_id = :avaliacaoDesempenhoId ");
		sql.append("	and avaliador_id not in (select distinct avaliador_id from ConfiguracaoCompetenciaAvaliacaoDesempenho where avaliacaodesempenho_id = :avaliacaoDesempenhoId) ");
		sql.append(") ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
				
		Collection<Colaborador> colaboradores = new LinkedList<Colaborador>();
		Collection<String> lista = query.list();

		for (String nomeColaborador : lista)
			colaboradores.add(new Colaborador(nomeColaborador));

		return colaboradores;
	}
	
	public Collection<AvaliacaoDesempenho> findAvaliacoesComColabSemCompetenciaConfiguradaByAvalDesempenhoIds(Long[] avaliacaoDesempenhoIds) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select a.titulo from avaliacaodesempenho a ");
		sql.append("where a.id in (:avaliacaoDesempenhoIds) and a.id in ( ");
		sql.append("	select cq.avaliacaodesempenho_id ");
		sql.append("	from colaboradorquestionario cq ");
		sql.append("	where cq.avaliacaodesempenho_id = a.id ");
		sql.append("	and ( select count(*) from configuracaocompetenciaavaliacaodesempenho ccad where ccad.avaliacaodesempenho_id = a.id ) > 0 ");
		sql.append("	and avaliador_id not in (select distinct avaliador_id from ConfiguracaoCompetenciaAvaliacaoDesempenho where avaliacaodesempenho_id = a.id) ");
		sql.append(") ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameterList("avaliacaoDesempenhoIds", avaliacaoDesempenhoIds);
		
		Collection<AvaliacaoDesempenho> avaliacoes = new LinkedList<AvaliacaoDesempenho>();
		Collection<String> lista = query.list();
		
		for (String tituloAvaliacao : lista)
			avaliacoes.add(new AvaliacaoDesempenho(tituloAvaliacao));
		
		return avaliacoes;
	}
}