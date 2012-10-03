package com.fortes.rh.dao.hibernate.captacao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;

public class ConfiguracaoNivelCompetenciaDaoHibernate extends GenericDaoHibernate<ConfiguracaoNivelCompetencia> implements ConfiguracaoNivelCompetenciaDao
{
	
	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId) 
	{
		Criteria criteria = createCriteria();

		criteria.add(Expression.eq("ncfs.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.isNull("ncfs.candidato.id"));
		criteria.add(Expression.isNull("ncfs.configuracaoNivelCompetenciaColaborador.id"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidatoId) 
	{
		Criteria criteria = createCriteria();

		criteria.add(Expression.eq("ncfs.candidato.id", candidatoId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));

		return criteria.list();
	}

	private Criteria createCriteria() 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetencia.class,"ncfs");
		criteria.createCriteria("ncfs.nivelCompetencia", "nc", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ncfs.id"), "id");
		p.add(Projections.property("ncfs.faixaSalarial.id"), "faixaSalarialIdProjection");
		p.add(Projections.property("ncfs.candidato.id"), "candidatoIdProjection");
		p.add(Projections.property("ncfs.configuracaoNivelCompetenciaColaborador.id"), "projectionConfiguracaoNivelCompetenciaColaboradorId");
		p.add(Projections.property("nc.id"), "nivelCompetenciaIdProjection");
		p.add(Projections.property("nc.descricao"), "projectionNivelCompetenciaDescricao");
		p.add(Projections.property("ncfs.competenciaId"), "competenciaId");
		p.add(Projections.property("ncfs.tipoCompetencia"), "tipoCompetencia");

		criteria.setProjection(p);
		return criteria;
	}

	public void deleteConfiguracaoByFaixa(Long faixaSalarialId) 
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia where faixaSalarial.id = :faixaSalarialId and candidato.id is null and configuracaoNivelCompetenciaColaborador_id is null";

		getSession().createQuery(queryHQL).setLong("faixaSalarialId", faixaSalarialId).executeUpdate();		
	}

	public void deleteConfiguracaoByCandidatoFaixa(Long candidatoId, Long faixaSalarialId) 
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia where candidato.id = :candidatoId and faixaSalarial.id = :faixaSalarialId";
		
		getSession().createQuery(queryHQL).setLong("candidatoId", candidatoId).setLong("faixaSalarialId", faixaSalarialId).executeUpdate();		
	}
	
	public void deleteByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId) 
	{
		String queryHQL = "delete from ConfiguracaoNivelCompetencia where configuracaoNivelCompetenciaColaborador.id = :configuracaoNivelCompetenciaColaboradorId";

		getSession().createQuery(queryHQL).setLong("configuracaoNivelCompetenciaColaboradorId", configuracaoNivelCompetenciaColaboradorId).executeUpdate();		
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId) 
	{
		Criteria criteria = createCriteria();

		criteria.add(Expression.eq("ncfs.configuracaoNivelCompetenciaColaborador.id", configuracaoNivelCompetenciaColaboradorId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetencia.class));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId) 
	{
		StringBuilder sql = new StringBuilder();

		sql.append("select cnc.id, COALESCE(a.nome, conhe.nome, h.nome) as competenciaNome, nc.descricao from ConfiguracaoNivelCompetencia cnc ");
		sql.append("join nivelcompetencia nc on nc.id = cnc.nivelcompetencia_id ");
		sql.append("left join Atitude a on a.id = cnc.competencia_id and 'A' = cnc.tipocompetencia ");
		sql.append("left join Conhecimento conhe on conhe.id = cnc.competencia_id and 'C' = cnc.tipocompetencia ");
		sql.append("left join Habilidade h on h.id = cnc.competencia_id and 'H' = cnc.tipocompetencia ");
		sql.append("where cnc.faixasalarial_id = :faixaSalarialId  ");
		sql.append("and cnc.configuracaoNivelCompetenciaColaborador_id is null ");
		sql.append("and cnc.candidato_id is null ");		
		sql.append("order by competenciaNome ");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("faixaSalarialId", faixaId);
		
		Collection<Object[]> resultado = query.list();
		
		Collection<ConfiguracaoNivelCompetencia> lista = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			lista.add(new ConfiguracaoNivelCompetencia(((BigInteger)res[0]).longValue(), (String)res[1] + " (" + (String)res[2]+ ")"));
		}

		return lista;				
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaColaborador(Long[] configuracaoNivelCompetenciaIds, Long faixaSalarialColaboradorId, boolean ordenarPorNivel) 
	{
		StringBuilder sql = new StringBuilder();

		sql.append("select COALESCE(a.nome, conhe.nome, h.nome) as competencia,  ncncf.descricao as nivelFaixaDescricao, ncncf.ordem as nivelFaixaOrdem, c.nome as colabNome, c.id as colabId, nc.descricao as nivelColabDescricao, nc.ordem as nivelColabOrdem from "); 
		sql.append("ConfiguracaoNivelCompetencia cncf ");
		sql.append("join NivelCompetencia ncncf on ncncf.id = cncf.nivelcompetencia_id ");
		sql.append("left join Atitude a on a.id = cncf.competencia_id and :tipoAtitude = cncf.tipocompetencia ");
		sql.append("left join Conhecimento conhe on conhe.id = cncf.competencia_id and :tipoConhecimento = cncf.tipocompetencia ");
		sql.append("left join Habilidade h on h.id = cncf.competencia_id and :tipoHabilidade = cncf.tipocompetencia ");
		sql.append("left join ConfiguracaoNivelCompetencia cnc on cncf.competencia_id = cnc.competencia_id and cncf.tipocompetencia = cnc.tipocompetencia ");
		sql.append("and cnc.candidato_id is null and cnc.faixasalarial_id is null ");
		sql.append("left join NivelCompetencia nc on nc.id = cnc.nivelcompetencia_id ");
		sql.append("left join ConfiguracaoNivelCompetenciaColaborador cncc on cncc.id = cnc.ConfiguracaoNivelCompetenciaColaborador_id ");
		sql.append("left join Colaborador c on c.id = cncc.colaborador_id ");
		sql.append("where  ");
		sql.append("(cncc.data = (select max(data) from ConfiguracaoNivelCompetenciaColaborador where colaborador_id = cncc.colaborador_id and cncc.faixaSalarial_id = :faixaSalarialColaboradorId) ");
		sql.append("or cncc.data is null) ");
		
		sql.append("and cncf.id in (:configuracaoNivelCompetenciaIds) ");
		
		if(ordenarPorNivel)
			sql.append("order by c.nome, c.id, competencia ");
		else
			sql.append("order by competencia, c.nome ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setCharacter("tipoAtitude", TipoCompetencia.ATITUDE);
		query.setCharacter("tipoConhecimento", TipoCompetencia.CONHECIMENTO);
		query.setCharacter("tipoHabilidade", TipoCompetencia.HABILIDADE);
		query.setParameterList("configuracaoNivelCompetenciaIds", configuracaoNivelCompetenciaIds, Hibernate.LONG);
		query.setLong("faixaSalarialColaboradorId", faixaSalarialColaboradorId);
		
		Collection<Object[]> resultado = query.list();
		
		Collection<ConfiguracaoNivelCompetencia> lista = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			lista.add(new ConfiguracaoNivelCompetencia((String)res[0], (String)res[1], (Integer)res[2], (String)res[3], ((BigInteger)res[4]), (String)res[5], (Integer)res[6] ));
		}
		
		return lista;				
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetencia> findCompetenciaCandidato(Long faixaSalarialId, Collection<Long> candidatosIds) 
	{
		StringBuilder sql = new StringBuilder();
		
		sql.append("select c.id as candidatoId, c.nome as candidatoNome, COALESCE(a.nome, conhe.nome, h.nome) as competencia, nc.descricao as nivelFaixaDescricao, nc.ordem as nivelFaixaOrdem ");       
		sql.append("from configuracaonivelcompetencia cnc ");
		sql.append("left join nivelcompetencia nc on nc.id=cnc.nivelcompetencia_id ");
		sql.append("left join Atitude a on a.id = cnc.competencia_id and :tipoAtitude = cnc.tipocompetencia ");
		sql.append("left join Conhecimento conhe on conhe.id = cnc.competencia_id and :tipoConhecimento = cnc.tipocompetencia ");
		sql.append("left join Habilidade h on h.id = cnc.competencia_id and :tipoHabilidade = cnc.tipocompetencia ");
		sql.append("left join Candidato c on c.id = cnc.candidato_id ");
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
		String hql = "delete ConfiguracaoNivelCompetencia where faixaSalarial.id in(:faixaSalarialIds)";

		Query query = getSession().createQuery(hql);
		query.setParameterList("faixaSalarialIds", faixaSalarialIds, Hibernate.LONG);

		query.executeUpdate();
	}

	public void removeColaborador(Colaborador colaborador) {
		String queryHQL = "delete from ConfiguracaoNivelCompetencia cnc where cnc.configuracaoNivelCompetenciaColaborador.id in (select id from ConfiguracaoNivelCompetenciaColaborador where colaborador.id = :colaboradorId)";

		Query query = getSession().createQuery(queryHQL);

		query.setLong("colaboradorId", colaborador.getId());

		query.executeUpdate();
	}

	public void removeByConfiguracaoNivelColaborador(Long configuracaoNivelColaboradorId) {
		String queryHQL = "delete from ConfiguracaoNivelCompetencia cnc where cnc.configuracaoNivelCompetenciaColaborador.id = :configuracaoNivelColaboradorId";

		Query query = getSession().createQuery(queryHQL);

		query.setLong("configuracaoNivelColaboradorId", configuracaoNivelColaboradorId);

		query.executeUpdate();
		
	}

	public void removeByCandidato(Long candidatoId) {
		String queryHQL = "delete from ConfiguracaoNivelCompetencia cnc where cnc.candidato.id = :candidatoId";
		getSession().createQuery(queryHQL).setLong("candidatoId",candidatoId).executeUpdate();
	}

	@SuppressWarnings("unchecked")
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

	public Integer somaConfiguracoesByFaixa(Long faixaSalarialId) {
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetencia.class, "cnc");
		criteria.createCriteria("cnc.nivelCompetencia", "nc", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.sum("nc.ordem"));
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cnc.faixaSalarial.id", faixaSalarialId));
		criteria.add(Expression.isNull("cnc.candidato.id"));
		criteria.add(Expression.isNull("cnc.configuracaoNivelCompetenciaColaborador.id"));
		
		return (Integer) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetencia> findByColaborador(Long colaboradorId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ConfiguracaoNivelCompetencia(cnc.tipoCompetencia, cnc.competenciaId, cnc.nivelCompetencia.id) "); 
		hql.append("from ConfiguracaoNivelCompetencia cnc "); 
		hql.append("left join cnc.configuracaoNivelCompetenciaColaborador cncc "); 
		hql.append("where cncc.colaborador.id = :colaboradorId ");
		hql.append("and cncc.data = (select max(data) from ConfiguracaoNivelCompetenciaColaborador where colaborador.id = cncc.colaborador.id) ");
		hql.append("order by cnc.competenciaId");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("colaboradorId", colaboradorId);
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetencia> findColaboradoresCompetenciasAbaixoDoNivel(	Long empresaId, Long[] estabelecimentoIds, Long[] areaIds) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select c.id as colabId, c.nome as colabNome, fs.id as faixaId, emp.nome as empNome, est.nome as estNome, cnc.competencia_id as compId, cnc.tipoCompetencia as tipoComp, nc.descricao as nivelCompetenciaDescricao, nc2.descricao as nivelCompetenciaColaboradorDescricao, COALESCE(a.nome, conhe.nome, h.nome) as competencia, COALESCE (cconhe.nome, chabil.nome, cati.nome ) as cursoNome ");
		sql.append("from HistoricoColaborador hc ");
		sql.append("inner join faixaSalarial fs on fs.id = hc.faixasalarial_id ");
		sql.append("inner join estabelecimento est on est.id = hc.estabelecimento_id ");
		
		sql.append("left join configuracaoNivelCompetencia cnc on cnc.faixasalarial_id = fs.id and cnc.candidato_id is null and cnc.configuracaoNivelCompetenciaColaborador_id is null ");
		sql.append("inner join nivelCompetencia nc on nc.id = cnc.nivelCompetencia_id ");
		
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
		sql.append("left join nivelCompetencia nc2 on nc2.id = cnc2.nivelCompetencia_id ");
		
		sql.append("where c.desligado = false ");
		sql.append("and hc.data = (select max(hc2.data) from HistoricoColaborador hc2 where hc2.colaborador_id = hc.colaborador_id and hc2.status = :status and hc2.data <= :hoje) ");
		sql.append("and (cncc.data = (select max(cncc2.data) from ConfiguracaoNivelCompetenciaColaborador cncc2 where cncc2.colaborador_id = c.id and cncc2.data <= :hoje) or cncc.data is null) ");
		
		if (empresaId != null)
			sql.append("and c.empresa.id = :empresaId ");
		
		if (areaIds != null && areaIds.length > 0)
			sql.append("and hc.areaOrganizacional_id in (:areaIds) ");
		
		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			sql.append("and hc.estabelecimento_id in (:estabelecimentoIds) ");
		
		sql.append("group by emp.nome, est.nome, c.id, fs.id, cnc.competencia_id, cnc.tipoCompetencia, nc.id, nc.descricao, nc.ordem, nc2.id, nc2.descricao, nc2.ordem, competencia, cursoNome ");
		sql.append("having (coalesce(nc2.ordem, 0) - nc.ordem) < 0 ");
		sql.append("order by emp.nome, est.nome, c.nome, c.id, cursoNome");
		
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
			lista.add(new ConfiguracaoNivelCompetencia((BigInteger)res[0], (String)res[1], (BigInteger)res[2], (String)res[3], (String)res[4], (BigInteger)res[5], (Character)res[6], (String)res[7], (String)res[8], (String)res[9], (String)res[10]));
		}
		
		return lista;	
	}
}
