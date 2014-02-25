package com.fortes.rh.dao.hibernate.avaliacao;

import java.math.BigInteger;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.util.LongUtil;

@SuppressWarnings("unchecked")
public class AvaliacaoDaoHibernate extends GenericDaoHibernate<Avaliacao> implements AvaliacaoDao
{
	private Criteria montaCriteriaFindAllSelect(Long empresaId, Boolean ativo, 	char modeloAvaliacao, String titulo) 
	{
		Criteria criteria = getSession().createCriteria(Avaliacao.class, "a");
		
		criteria.add(Expression.eq("a.empresa.id", empresaId));
		
		boolean modeloDeDesempenho = TipoModeloAvaliacao.DESEMPENHO == modeloAvaliacao;
		if (modeloDeDesempenho)
			criteria.add(Expression.ne("a.tipoModeloAvaliacao", TipoModeloAvaliacao.SOLICITACAO));
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
		
		if(LongUtil.isNotEmpty(perguntaIds))
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
		
		if(LongUtil.isNotEmpty(perguntaIds))
			sql.append("  	 								and p6.id not in (:perguntaIds) ");

		sql.append("  	 									and p6.tipo = :tipoPerguntaMultiplaEscolha) as multiplaescolha");
		
		sql.append(" 	from pergunta p ");
		sql.append(" 	where p.avaliacao_id = :avaliacaoId ");
		sql.append(" 	and p.tipo = :tipoPerguntaNota ");
		
		Query q = getSession().createSQLQuery(sql.toString());
		
		q.setLong("avaliacaoId", avaliacaoId);
		q.setInteger("tipoPerguntaNota", TipoPergunta.NOTA);
		q.setInteger("tipoPerguntaObjetiva", TipoPergunta.OBJETIVA);
		q.setInteger("tipoPerguntaMultiplaEscolha", TipoPergunta.MULTIPLA_ESCOLHA);
		
		if(LongUtil.isNotEmpty(perguntaIds))
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
}