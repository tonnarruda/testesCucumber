package com.fortes.rh.dao.hibernate.pesquisa;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.pesquisa.PerguntaDao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.Pergunta;

@Component
@SuppressWarnings("unchecked")
public class PerguntaDaoHibernate extends GenericDaoHibernate<Pergunta> implements PerguntaDao
{
	public Collection<Pergunta> findByQuestionario(Long questionarioId)
	{
		Criteria criteria = getCriteriaParaQuestionario(questionarioId);
		criteria.addOrder(Order.asc("pergunta.ordem"));
		
		return criteria.list();
	}
	
	public Collection<Pergunta> findByQuestionarioAgrupadoPorAspecto(Long questionarioId, boolean ordenarPorAspecto)
	{
		Criteria criteria = getCriteriaParaQuestionario(questionarioId);
		
		if (ordenarPorAspecto)
			criteria.addOrder(Order.asc("aspecto.nome"));
		
		criteria.addOrder(Order.asc("pergunta.ordem"));
		
		return criteria.list();
	}

	private Criteria getCriteriaParaQuestionario(Long questionarioId) {
		
		Criteria criteria = getSession().createCriteria(getEntityClass(),"pergunta");
		criteria.createCriteria("pergunta.aspecto", "aspecto", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("pergunta.id"), "id");
		p.add(Projections.property("pergunta.texto"), "texto");
		p.add(Projections.property("pergunta.ordem"), "ordem");
		p.add(Projections.property("pergunta.comentario"), "comentario");
		p.add(Projections.property("pergunta.textoComentario"), "textoComentario");
		p.add(Projections.property("pergunta.tipo"), "tipo");
		p.add(Projections.property("pergunta.notaMaxima"), "notaMaxima");
		p.add(Projections.property("pergunta.notaMinima"), "notaMinima");
		p.add(Projections.property("pergunta.peso"), "peso");
		p.add(Projections.property("questionario.id"), "projectionQuestionarioId");
		p.add(Projections.property("avaliacao.id"), "projectionAvaliacaoId");
		p.add(Projections.property("aspecto.id"), "projectionAspectoId");
		p.add(Projections.property("aspecto.nome"), "projectionAspectoNome");

		criteria.setProjection(p);
		
        Disjunction disjunction = Expression.disjunction();
        disjunction.add(Expression.eq("questionario.id", questionarioId));
        disjunction.add(Expression.eq("avaliacao.id", questionarioId));
        criteria.add(disjunction);
        
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria;
	}

	public Collection<Pergunta> findByQuestionarioAspecto(Long questionarioId, Long[] aspectosIds)
	{
		return findPergunta(questionarioId, aspectosIds, null, false);
	}

	public Collection<Pergunta> findByQuestionarioAspectoPergunta(Long questionarioId, Long[] aspectosIds, Long[] perguntasIds, boolean agruparPorAspectos)
	{
		return findPergunta(questionarioId, aspectosIds, perguntasIds, agruparPorAspectos);
	}

	private Collection<Pergunta> findPergunta(Long questionarioId, Long[] aspectoIds, Long[] perguntasIds, boolean agruparPorAspectos)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"pergunta");
		criteria.createCriteria("pergunta.aspecto", "aspecto", Criteria.LEFT_JOIN);
		criteria.createCriteria("pergunta.questionario", "questionario", Criteria.LEFT_JOIN);
		criteria.createCriteria("pergunta.avaliacao", "avaliacao", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("pergunta.id"), "id");
		p.add(Projections.property("pergunta.texto"), "texto");
		p.add(Projections.property("pergunta.ordem"), "ordem");
		p.add(Projections.property("pergunta.peso"), "peso");
		p.add(Projections.property("pergunta.comentario"), "comentario");
		p.add(Projections.property("pergunta.textoComentario"), "textoComentario");
		p.add(Projections.property("pergunta.tipo"), "tipo");
		p.add(Projections.property("pergunta.notaMaxima"), "notaMaxima");
		p.add(Projections.property("pergunta.notaMinima"), "notaMinima");
		p.add(Projections.property("pergunta.aspecto"), "aspecto");
		p.add(Projections.property("questionario.id"), "projectionQuestionarioId");
		p.add(Projections.property("avaliacao.id"), "projectionAvaliacaoId");

		criteria.setProjection(p);
		
		// consulta pelo questionario ou avaliacao (ambos usam a mesma sequence)
		Disjunction disjunction = Expression.disjunction();
        disjunction.add(Expression.eq("questionario.id", questionarioId));
        disjunction.add(Expression.eq("avaliacao.id", questionarioId));
        criteria.add(disjunction);

		if(aspectoIds != null && aspectoIds.length > 0)
			criteria.add(Expression.in("aspecto.id", aspectoIds));

		if(perguntasIds != null && perguntasIds.length > 0)
			criteria.add(Expression.in("pergunta.id", perguntasIds));

		if(agruparPorAspectos)
//				|| (aspectoIds != null && aspectoIds.length > 0))
		{
			criteria.addOrder(Order.asc("aspecto.nome"));
			criteria.addOrder(Order.asc("pergunta.ordem"));
		}
		else
			criteria.addOrder(Order.asc("pergunta.ordem"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Integer getUltimaOrdenacao(Long questionarioId) throws Exception
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"p");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.ordem"), "ordem");

		criteria.setProjection(p);

		Disjunction disjunction = Expression.disjunction();
        disjunction.add(Expression.eq("questionario.id", questionarioId));
        disjunction.add(Expression.eq("avaliacao.id", questionarioId));
        criteria.add(disjunction);

		criteria.addOrder(Order.desc("p.ordem"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		Collection<Pergunta> perguntas = criteria.list();

		if(perguntas == null || perguntas.size() == 0)
			return 0;
		else
			return ((Pergunta) perguntas.toArray()[0]).getOrdem();
	}

	public Pergunta findByIdProjection(Long perguntaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"pergunta");
		criteria.createCriteria("pergunta.aspecto", "aspecto", Criteria.LEFT_JOIN);
		criteria.createCriteria("pergunta.questionario", "questionario", Criteria.LEFT_JOIN);
		criteria.createCriteria("pergunta.avaliacao", "ape", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("pergunta.id"), "id");
		p.add(Projections.property("pergunta.texto"), "texto");
		p.add(Projections.property("pergunta.ordem"), "ordem");
		p.add(Projections.property("pergunta.comentario"), "comentario");
		p.add(Projections.property("pergunta.textoComentario"), "textoComentario");
		p.add(Projections.property("pergunta.tipo"), "tipo");
		p.add(Projections.property("pergunta.notaMaxima"), "notaMaxima");
		p.add(Projections.property("pergunta.notaMinima"), "notaMinima");
		p.add(Projections.property("pergunta.peso"), "peso");
		p.add(Projections.property("aspecto.id"), "projectionAspectoId");
		p.add(Projections.property("aspecto.nome"), "projectionAspectoNome");
		p.add(Projections.property("questionario.id"), "projectionQuestionarioId");
		p.add(Projections.property("ape.id"), "projectionAvaliacaoId");

		criteria.setProjection(p);
		criteria.add(Expression.eq("pergunta.id", perguntaId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		criteria.setMaxResults(1);

		return (Pergunta) criteria.uniqueResult();
	}

	public Long findUltimaPerguntaObjetiva(Long questionarioOrAvaliacaoId)
	{
		String hql = "select p.id " +
		  "from Pergunta as p " +
		  "left join p.questionario as q " +
		  "where " +
		  "(p.questionario.id = :questionarioId or p.avaliacao.id = :questionarioId) " +
		  "and p.tipo = :tipoPergunta " +
		  " order by p.ordem desc ";

		Query query = getSession().createQuery(hql);
		query.setLong("questionarioId", questionarioOrAvaliacaoId);
		query.setInteger("tipoPergunta", TipoPergunta.OBJETIVA);

		List resultado = query.list();

		if(!resultado.isEmpty())
			return (Long) resultado.get(0);
		else
			return null;
	}

	public boolean reordenaPergunta(Long perguntaId, char sinal)
	{
		String queryHQL = "update Pergunta p set p.ordem = (p.ordem " + sinal + " 1) where p.id = :perguntaId";

		Query query = getSession().createQuery(queryHQL);

		query.setLong("perguntaId",perguntaId);

		int result = query.executeUpdate();

		return result == 1;
	}

	public Long findIdByOrdem(Long questionarioId, int ordem)
	{
		String hql = "select p.id " +
		  "from Pergunta as p " +
		  "left join p.questionario as q " +
		  "where " +
		  "		q.id = :questionarioId and " +
		  "		p.ordem = :ordem " +
		  " order by p.ordem desc limit 1";

		Query query = getSession().createQuery(hql);
		query.setLong("questionarioId", questionarioId);
		query.setInteger("ordem", ordem);

		List resultado = query.list();

		if(resultado.size() > 0)
			return (Long) resultado.get(0);
		else
			return null;
	}

	public boolean reposicionarPerguntas(Long questionarioId, Integer ordem, char sinal)
	{
		String queryHQL = "update Pergunta p set p.ordem = (p.ordem " + sinal + " 1) where (p.questionario.id = :questionarioId or p.avaliacao.id = :questionarioId) and p.ordem > :ordem";

		Query query = getSession().createQuery(queryHQL);

		query.setLong("questionarioId", questionarioId);
		query.setLong("ordem", ordem-1);

		int result = query.executeUpdate();

		return result > 0;
	}

	public void updateAndReorder(Pergunta pergunta)
	{
		if (existsOrdem(pergunta.getQuestionarioOrAvaliacaoId(), pergunta.getOrdem()))
		{
			Integer ordemNova = pergunta.getOrdem();
			Integer ordemAtual = findById(pergunta.getId()).getOrdem();
			
			char sinal = '-';
			Integer ordemInit = ordemAtual+1;
			Integer ordemFim = ordemNova;
			
			if (ordemAtual > ordemNova)
			{
				sinal = '+';
				ordemInit = ordemNova;
				ordemFim = ordemAtual-1;			
			}
			
			String queryHQL = "update Pergunta p set p.ordem = (p.ordem " + sinal + " 1) where (p.questionario.id = :questionarioId or p.avaliacao.id = :questionarioId) and (p.ordem >= :ordemInit and p.ordem <= :ordemFim)";
	
			Query query = getSession().createQuery(queryHQL);
	
			query.setLong("questionarioId", pergunta.getQuestionarioOrAvaliacaoId());
			query.setLong("ordemInit", ordemInit);
			query.setLong("ordemFim", ordemFim);
	
			query.executeUpdate();
		}
		update(pergunta);
	}
	
	public void updateOrdem(Long perguntaId, int novaOrdem) throws Exception
	{
		String queryHQL = "update Pergunta p set p.ordem = :novaOrdem where p.id = :perguntaId";

		Query query = getSession().createQuery(queryHQL);

		query.setLong("perguntaId",perguntaId);
		query.setInteger("novaOrdem",novaOrdem);

		query.executeUpdate();
	}

	public void removerPerguntasDoQuestionario(Long questionarioId)
	{
		String queryHQL = "delete from Pergunta p where p.questionario.id = :questionarioId";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("questionarioId", questionarioId);

		query.executeUpdate();
	}
	
	public void removerPerguntasDaAvaliacao(Long avaliacaoId)
	{
		String queryHQL = "delete from Pergunta p where p.avaliacao.id = :avaliacaoId";
		
		Query query = getSession().createQuery(queryHQL);
		query.setLong("avaliacaoId", avaliacaoId);
		
		query.executeUpdate();
	}

	public Collection<Long> findPerguntasDoQuestionario(Long questionarioId)
	{
		String hql = "select p.id from Pergunta p where p.questionario.id = :questionarioId";

		Query query = getSession().createQuery(hql);
		query.setLong("questionarioId", questionarioId);

		return query.list();
	}

	public int getTotalPerguntas(Long questionarioId)
	{
		String hql = "select count(id) as total from Pergunta p where p.questionario.id = :questionarioId";

		Query query = getSession().createQuery(hql);
		query.setLong("questionarioId", questionarioId);

		return (Integer)query.uniqueResult();
	}

	public boolean existsOrdem(Long questionarioOrAvaliacaoId, Integer ordem)
	{
		String queryHQL = "select id from Pergunta p where (p.questionario.id = :questionarioId or p.avaliacao.id = :questionarioId) and p.ordem = :ordem";

		Query query = getSession().createQuery(queryHQL);

		query.setLong("questionarioId", questionarioOrAvaliacaoId);
		query.setLong("ordem", ordem);

		Collection<Object> perguntas = query.list();

		return !perguntas.isEmpty();
	}

	public Map<Long, Integer> getPontuacoesMaximas(Long[] perguntasIds) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select p.id, ");
		sql.append("case p.tipo ");
		sql.append("when 1 then (select coalesce(max(r.peso), 0) from resposta r where r.pergunta_id = p.id) "); 
		sql.append("when 4 then coalesce(p.notamaxima, 0) ");
		sql.append("when 5 then (select coalesce(sum(r.peso), 0) from resposta r where r.pergunta_id = p.id) "); 
		sql.append("else 0 end as pontuacaomaxima ");
		sql.append("from pergunta p where p.id in (:perguntasIds)");
		
		Query query = getSession().createSQLQuery(sql.toString());
		
		query.setParameterList("perguntasIds", perguntasIds);
		
		Collection<Object[]> resultado = query.list();
		Map<Long, Integer> map = new HashMap<Long, Integer>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			map.put(((BigInteger)res[0]).longValue(), ((BigInteger)res[1]).intValue());
		}
		
		return map;
	}
}