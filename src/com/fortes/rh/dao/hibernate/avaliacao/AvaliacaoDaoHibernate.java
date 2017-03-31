package com.fortes.rh.dao.hibernate.avaliacao;

import java.math.BigInteger;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
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
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;
import com.fortes.rh.util.LongUtil;

@SuppressWarnings("unchecked")
public class AvaliacaoDaoHibernate extends GenericDaoHibernate<Avaliacao> implements AvaliacaoDao
{
	private Criteria montaCriteriaFindAllSelect(Long empresaId, Boolean ativo, 	char modeloAvaliacao, String titulo) 
	{
		Criteria criteria = getSession().createCriteria(Avaliacao.class, "a");
		
		if(modeloAvaliacao != TipoModeloAvaliacao.AVALIACAO_ALUNO)
			criteria.add(Expression.eq("a.empresa.id", empresaId));
		
		if (TipoModeloAvaliacao.DESEMPENHO == modeloAvaliacao)
			criteria.add(Expression.or(
										Expression.eq("a.tipoModeloAvaliacao", TipoModeloAvaliacao.DESEMPENHO), 
										Expression.eq("a.tipoModeloAvaliacao", TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA)
									  ));
		else
		{
			if (TipoModeloAvaliacao.AVALIACAO_DESEMPENHO == modeloAvaliacao)
				criteria.add(Expression.eq("a.tipoModeloAvaliacao", TipoModeloAvaliacao.DESEMPENHO));
			else
				criteria.add(Expression.eq("a.tipoModeloAvaliacao", modeloAvaliacao));
		}
		if(ativo != null)
			criteria.add(Expression.eq("a.ativo", ativo));
		if(titulo != null)
			criteria.add(Expression.like("a.titulo", "%"+ titulo +"%").ignoreCase() );
		return criteria;
	}
	
	public Collection<Avaliacao> findAllSelect(Integer page, Integer pagingSize, Long empresaId, Boolean ativo, char modeloAvaliacao, String titulo)
	{
		Criteria criteria = montaCriteriaFindAllSelect(empresaId, ativo, modeloAvaliacao, titulo);
		
		criteria.addOrder(Order.asc("a.titulo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		if(pagingSize != null && pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}
		
		return criteria.list();
	}

	public Integer getCount(Long empresaId, Boolean ativo, char modeloAvaliacao, String titulo)
	{
		Criteria criteria = montaCriteriaFindAllSelect(empresaId, ativo, modeloAvaliacao, titulo);
		criteria.setProjection(Projections.rowCount());
		
		return (Integer) criteria.uniqueResult();
	}
	
	public Integer getPontuacaoMaximaDaPerformance(Long avaliacaoId, Long... perguntaIds)
	{
		StringBuilder sql = new StringBuilder();
		
		// Pontuações máximas de: Notas , Objetivas , Multipla Escolha
		
		sql.append("select sum(p.notaMaxima * p.peso) as nota   ");
		
		sql.append("									, (select sum(perguntaDistinct.pesoResp * perguntaDistinct.pesoPerg) " );
		sql.append("											 from ( " );
		sql.append("									 				select distinct(p5.id), r5.peso as pesoResp, p5.peso as pesoPerg ");
		sql.append("													from resposta r5 ");
		sql.append(" 													join pergunta p5 on p5.id = r5.pergunta_id ");		 
		sql.append(" 													where p5.avaliacao_id = :avaliacaoId ");
		sql.append(" 													and p5.tipo = :tipoPerguntaObjetiva ");
		
		if(LongUtil.arrayIsNotEmpty(perguntaIds))
			sql.append(" 												and p5.id not in (:perguntaIds) ");
		
		sql.append(" 													and r5.peso = (select max(r2.peso) ");
		sql.append(" 																	from Resposta r2 ");
		sql.append(" 																	where r2.pergunta_id=p5.id) ");
		sql.append(" 													group by p5.id, r5.peso, p5.peso");
		sql.append(" 												) as perguntaDistinct ");
		sql.append(" 										) as objetiva");
		
		sql.append(" 									, (select sum(r6.peso * p6.peso) ");
		sql.append("  	 									from resposta r6 ");
		sql.append("  	 									join pergunta p6 on p6.id = r6.pergunta_id 	");
		sql.append("  							 			where p6.avaliacao_id = :avaliacaoId ");
		sql.append("  	 									and r6.peso > 0 ");
		
		if(LongUtil.arrayIsNotEmpty(perguntaIds))
			sql.append("  	 								and p6.id not in (:perguntaIds) ");

		sql.append("  	 									and p6.tipo = :tipoPerguntaMultiplaEscolha) as multiplaescolha");
		
		sql.append(" 	from pergunta p ");
		sql.append(" 	where p.avaliacao_id = :avaliacaoId ");
		sql.append(" 	and p.tipo = :tipoPerguntaNota ");
		if(LongUtil.arrayIsNotEmpty(perguntaIds))
			sql.append("  	 								and p.id not in (:perguntaIds) ");
		
		Query q = getSession().createSQLQuery(sql.toString());
		
		q.setLong("avaliacaoId", avaliacaoId);
		q.setInteger("tipoPerguntaNota", TipoPergunta.NOTA);
		q.setInteger("tipoPerguntaObjetiva", TipoPergunta.OBJETIVA);
		q.setInteger("tipoPerguntaMultiplaEscolha", TipoPergunta.MULTIPLA_ESCOLHA);
		
		if(LongUtil.arrayIsNotEmpty(perguntaIds))
			q.setParameterList("perguntaIds", perguntaIds, Hibernate.LONG);
	
		Object[] somas = (Object[]) q.uniqueResult();
		
		Integer pontuacaoMaximaPorNota = somas[0] == null ? 0 :   Integer.parseInt(((BigInteger)somas[0]).toString());
		Integer pontuacaoMaximaPorObjetiva = somas[1] == null ? 0 : Integer.parseInt(((BigInteger)somas[1]).toString());
		Integer pontuacaoMaximaPorMultiplaEscolha = somas[2] == null ? 0 : Integer.parseInt(((BigInteger)somas[2]).toString());
		
		Integer pontuacaoMaxima = pontuacaoMaximaPorNota + pontuacaoMaximaPorObjetiva + pontuacaoMaximaPorMultiplaEscolha;
		
		return pontuacaoMaxima;
	}

	public Collection<Avaliacao> findPeriodoExperienciaIsNull(char acompanhamentoExperiencia, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "a");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.titulo"), "titulo");
		p.add(Projections.property("a.tipoModeloAvaliacao"), "tipoModeloAvaliacao");

		criteria.setProjection(p);

		criteria.add(Expression.eq("a.tipoModeloAvaliacao", acompanhamentoExperiencia));
		criteria.add(Expression.eq("a.empresa.id", empresaId));
		criteria.add(Expression.eq("a.ativo", true));
		criteria.add(Expression.isNull("a.periodoExperiencia"));
		
		criteria.addOrder(Order.asc("a.titulo"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<Avaliacao> findAllSelectComAvaliacaoDesempenho(Long empresaId, boolean ativa) 
	{
		Criteria criteria = getSession().createCriteria(AvaliacaoDesempenho.class, "ad");
		criteria.createCriteria("ad.avaliacao", "a", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("a.id")), "id");
		p.add(Projections.property("a.titulo"), "titulo");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("a.empresa.id", empresaId));
		criteria.add(Expression.eq("a.ativo", ativa));
		
		criteria.addOrder(Order.asc("a.titulo"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	@Override
	public Collection<Avaliacao> findModelosPeriodoExperienciaAtivosAndModelosConfiguradosParaOColaborador(Long empresaId, Long colaboradorId) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.ativo"), "ativo");
		p.add(Projections.property("a.titulo"), "titulo");
		p.add(Projections.property("a.periodoExperiencia"), "periodoExperiencia");
		
		DetachedCriteria subquery =  DetachedCriteria.forClass(ColaboradorPeriodoExperienciaAvaliacao.class, "cpa")
				.setProjection(Projections.property("cpa.avaliacao.id"))
				.add(Restrictions.eq("cpa.colaborador.id", colaboradorId));

		Criteria criteria = getSession().createCriteria(Avaliacao.class, "a");
		criteria.add(Expression.eq("a.empresa.id", empresaId));
		criteria.add(Expression.eq("a.tipoModeloAvaliacao", TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA));
		criteria.add(Expression.or(Expression.eq("a.ativo",true), Subqueries.propertyIn("a.id",subquery)));
	
		criteria.setProjection(p);
		criteria.addOrder(Order.asc("a.titulo"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Avaliacao.class));
		return criteria.list();
	}

	public Collection<Avaliacao> findModelosAcompanhamentoPeriodoExperiencia( boolean ativo, Long empresaId, Long colaboradorId, Long colaboradorLogadoId, Integer tipoResponsavel) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.titulo"), "titulo");
		p.add(Projections.property("a.cabecalho"), "cabecalho");
		p.add(Projections.property("a.percentualAprovacao"), "percentualAprovacao");
		p.add(Projections.property("a.ativo"), "ativo");
		p.add(Projections.property("a.tipoModeloAvaliacao"), "tipoModeloAvaliacao");
		p.add(Projections.property("a.empresa.id"), "projectionEmpresaId");
		p.add(Projections.property("a.periodoExperiencia"), "periodoExperiencia");
		p.add(Projections.property("a.avaliarCompetenciasCargo"), "avaliarCompetenciasCargo");
		p.add(Projections.property("a.respostasCompactas"), "respostasCompactas");
		
		Criteria criteria = getSession().createCriteria(Avaliacao.class, "a");
		criteria.add(Expression.eq("a.ativo",true));
		criteria.add(Expression.eq("a.empresa.id", empresaId));
		criteria.add(Expression.eq("a.tipoModeloAvaliacao", TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA));
		if(tipoResponsavel != null)
			montacriteriaRestringirVisualizacaoDeModelosParaGestorDaPropriaArea(criteria, colaboradorLogadoId, tipoResponsavel);
			
		criteria.setProjection(p);
		criteria.addOrder(Order.asc("a.titulo"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Avaliacao.class));
		return criteria.list();
	}
	
	private Criteria montacriteriaRestringirVisualizacaoDeModelosParaGestorDaPropriaArea(Criteria criteria, Long colaboradorLogadoId, Integer tipoResponsavel){
		DetachedCriteria subquery =  DetachedCriteria.forClass(ColaboradorPeriodoExperienciaAvaliacao.class, "cpe")
				.setProjection(Projections.property("cpe.avaliacao.id"))
				.add(Restrictions.eq("cpe.colaborador.id", colaboradorLogadoId))
				.add(Restrictions.eq("cpe.tipo", 'G'));
		
		criteria.add(Subqueries.propertyNotIn("a.id",subquery));
		criteria.add(montaCriterio(colaboradorLogadoId, tipoResponsavel));
		
		return criteria;
	}
	
	private Criterion montaCriterio(Long colaboradorLogadoId, Integer tipoResponsavel){
		String condicaoTipoResponsavel = "responsavel_id";
		if(tipoResponsavel == AreaOrganizacional.CORRESPONSAVEL)
			condicaoTipoResponsavel = "coResponsavel_id";
		
		StringBuilder sql = new StringBuilder(" ");
		sql.append(" this_.id not in ( ");
		sql.append(" 	select cq.avaliacao_id from colaboradorquestionario cq ");
		sql.append(" 	where cq.colaborador_id = ? and cq.avaliacaoDesempenho_id is null ");
		sql.append("	and cq.respondida = true and avaliacao_id is not null ");
		sql.append("	and (cq.avaliador_id is null or cq.avaliador_id in( ");
		sql.append("		with areaId as( ");
		sql.append("			select ao.id from historicocolaborador  hc ");
		sql.append("				join areaOrganizacional ao on ao.id = hc.areaorganizacional_id ");
		sql.append("				where hc.data = ( select max(hc2.data) from historicoColaborador hc2 where hc2.colaborador_id =? and hc2.status = 1) ");
		sql.append("				and hc.colaborador_id = ? ");
		sql.append("		) ");
		sql.append("		select " + condicaoTipoResponsavel + " from areaorganizacional");
		sql.append("		where id in (select * from ancestrais_areas_ids((select * from areaId))) and " + condicaoTipoResponsavel + " <> ? ");
		sql.append("	)");
		sql.append(" ))");
		
		return Expression.sqlRestriction(sql.toString(), new Long[] {colaboradorLogadoId, colaboradorLogadoId, colaboradorLogadoId, colaboradorLogadoId}, new Type[]{Hibernate.LONG, Hibernate.LONG, Hibernate.LONG, Hibernate.LONG});
	}
}