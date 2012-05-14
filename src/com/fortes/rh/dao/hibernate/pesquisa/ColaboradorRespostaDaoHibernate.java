package com.fortes.rh.dao.hibernate.pesquisa;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.pesquisa.ColaboradorRespostaDao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Questionario;

@SuppressWarnings("unchecked")
public class ColaboradorRespostaDaoHibernate extends GenericDaoHibernate<ColaboradorResposta> implements ColaboradorRespostaDao
{
	public List<Object[]> countRespostas(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Date periodoIni, Date periodoFim, Long turmaId, Long empresaId)
	{
		String whereEmpresa = "";
		String whereAreas = "";
		String whereAreasSub = "";
		String whereEstabelecimentos = "";
		String whereEstabelecimentosSub = "";
		String wherePerguntas = "";
		String wherePeriodoIni = "";
		String wherePeriodoIniSub = "";
		String wherePeriodoFim = "";
		String wherePeriodoFimSub = "";
		String whereTurmaSub = "";
		String whereTurma = "";

		if(empresaId != null && empresaId != -1)
			whereEmpresa = "and c.empresa.id = :empresaId ";
		
		if(perguntasIds != null && perguntasIds.length > 0)
			wherePerguntas = "and p.id in (:perguntasIds) ";
		if(areasIds != null && areasIds.length > 0)
		{
			whereAreas = "and a.id in (:areasIds) ";
			whereAreasSub = "and crsub.areaOrganizacional.id in (:areasIds) ";
		}
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
		{
			whereEstabelecimentos = "and e.id in (:estabelecimentosIds) ";
			whereEstabelecimentosSub = "and crsub.estabelecimento.id in (:estabelecimentosIds) ";
		}
		if(periodoIni != null)
		{
			wherePeriodoIni = "and cq.respondidaEm >= :periodoIni ";
			wherePeriodoIniSub = "and cqsub.respondidaEm >= :periodoIni ";
		}
		if(periodoFim != null)
		{
			wherePeriodoFim = "and cq.respondidaEm <= :periodoFim ";
			wherePeriodoFimSub = "and cqsub.respondidaEm <= :periodoFim ";
		}
		if(turmaId != null)
		{
			whereTurmaSub = "and cqsub.turma.id = :turmaId ";
			whereTurma = "and cq.turma.id = :turmaId ";			
		}

		String queryHQL =	"select r.ordem, count(r.id), p.id, r.id, " +
							"(select count(crsub.pergunta.id) from ColaboradorResposta as crsub " +
							"left join crsub.colaboradorQuestionario as cqsub " +
							"where crsub.pergunta.id = p.id " +
							"and crsub.resposta.id is not null " +
							whereAreasSub +
							whereEstabelecimentosSub +
							wherePeriodoIniSub +
							wherePeriodoFimSub +
							whereTurmaSub +
							"group by crsub.pergunta.id) " +
							"from ColaboradorResposta cr " +
							"left join cr.areaOrganizacional as a "	+
							"left join cr.estabelecimento as e "	+
							"left join cr.resposta as r "	+
							"left join cr.pergunta as p "	+
							"left join cr.colaboradorQuestionario as cq "	+
							"left join cq.colaborador as c "	+
							"where p.tipo = :tipoPergunta "+
							"and cr.resposta.id is not null " +
							wherePerguntas +
							whereAreas +
							whereEstabelecimentos +
							wherePeriodoIni +
							wherePeriodoFim +
							whereTurma +
							whereEmpresa +
							"group by r.ordem, p.id, r.id "+
							"order by r.ordem ";

		Query query = getSession().createQuery(queryHQL);
		query.setInteger("tipoPergunta", TipoPergunta.OBJETIVA);

		if(perguntasIds != null && perguntasIds.length > 0)
			query.setParameterList("perguntasIds", perguntasIds, Hibernate.LONG);

		if(areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);

		if(empresaId != null && empresaId != -1)
			query.setLong("empresaId", empresaId);
		
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);

		if(periodoIni != null)
			query.setDate("periodoIni", periodoIni);

		if(periodoFim != null)
			query.setDate("periodoFim", periodoFim);
		if(turmaId != null)
			query.setLong("turmaId", turmaId);
		
		return query.list();
	}

	public List<Object[]> countRespostasMultiplas(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Date periodoIni, Date periodoFim, Long turmaId, Long empresaId)
	{
		String whereEmpresa = "";
		String whereAreas = "";
		String whereEstabelecimentos = "";
		String wherePerguntas = "";
		String wherePeriodoIni = "";
		String wherePeriodoFim = "";
		String whereTurma = "";
		
		if(empresaId != null && empresaId != -1)
			whereEmpresa = "and c.empresa.id = :empresaId ";
		if(perguntasIds != null && perguntasIds.length > 0)
			wherePerguntas = "and p.id in (:perguntasIds) ";
		if(areasIds != null && areasIds.length > 0)
			whereAreas = "and a.id in (:areasIds) ";
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			whereEstabelecimentos = "and e.id in (:estabelecimentosIds) ";
		if(periodoIni != null)
			wherePeriodoIni = "and cq.respondidaEm >= :periodoIni ";
		if(periodoFim != null)
			wherePeriodoFim = "and cq.respondidaEm <= :periodoFim ";
		if(turmaId != null)
			whereTurma = "and cq.turma.id = :turmaId ";			
		
		String queryHQL =	"select r.ordem, count(r.id), p.id, r.id " +
		"from ColaboradorResposta cr " +
		"left join cr.areaOrganizacional as a "	+
		"left join cr.estabelecimento as e "	+
		"left join cr.resposta as r "	+
		"left join cr.pergunta as p "	+
		"left join cr.colaboradorQuestionario as cq "	+
		"left join cq.colaborador as c "	+
		"where p.tipo = :tipoPergunta "+
		"and cr.resposta.id is not null " +
		wherePerguntas +
		whereAreas +
		whereEstabelecimentos +
		wherePeriodoIni +
		wherePeriodoFim +
		whereTurma +
		whereEmpresa +
		"group by r.ordem, p.id, r.id "+
		"order by r.ordem ";
		
		Query query = getSession().createQuery(queryHQL);
		query.setInteger("tipoPergunta", TipoPergunta.MULTIPLA_ESCOLHA);
		
		if(perguntasIds != null && perguntasIds.length > 0)
			query.setParameterList("perguntasIds", perguntasIds, Hibernate.LONG);
		
		if(areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);

		if(empresaId != null && empresaId != -1)
			query.setLong("empresaId", empresaId);
		
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		
		if(periodoIni != null)
			query.setDate("periodoIni", periodoIni);
		
		if(periodoFim != null)
			query.setDate("periodoFim", periodoFim);
		if(turmaId != null)
			query.setLong("turmaId", turmaId);
		
		return query.list();
	}
	
	public Collection<ColaboradorResposta> findInPerguntaIds(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Date periodoIni, Date periodoFim, Long turmaId, Questionario questionario, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"cr");
		criteria.createCriteria("cr.colaboradorQuestionario", "cq", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.turma", "t", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.candidato", "cand", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.areaOrganizacional", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.estabelecimento", "e", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.pergunta", "p", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.resposta", "r", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cr.id"), "id");
		p.add(Projections.property("cq.id"), "projectionColaboradorQuestionarioId");
		p.add(Projections.property("cr.comentario"), "comentario");
		p.add(Projections.property("cr.valor"), "valor");
		p.add(Projections.property("r.id"), "projectionRespostaId");
		p.add(Projections.property("r.ordem"), "projectionRespostaOrdem");
		p.add(Projections.property("p.id"), "projectionPerguntaId");
		p.add(Projections.property("c.nomeComercial"), "projectionColaboradorNomeComercial");
		p.add(Projections.property("c.nome"), "projectionColaboradorNome");
		p.add(Projections.property("cand.nome"), "projectionColaboradorNomeComercial");

		criteria.setProjection(p);

		if(turmaId != null)
			criteria.add(Expression.eq("t.id", turmaId));
			
		if(perguntasIds != null && perguntasIds.length > 0)
			criteria.add(Expression.in("p.id", perguntasIds));

		if(areasIds != null && areasIds.length > 0)
			criteria.add(Expression.in("a.id", areasIds));
		
		if(empresaId != null && empresaId != -1)
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			criteria.add(Expression.in("e.id", estabelecimentosIds));

		String periodo = "cq.respondidaEm";
		if (questionario != null && questionario.verificaTipo(TipoQuestionario.ENTREVISTA))
			periodo = "c.dataDesligamento";
			
		if(periodoIni != null)
			criteria.add(Expression.ge(periodo, periodoIni));

		if(periodoFim != null)
			criteria.add(Expression.le(periodo, periodoFim));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorResposta.class));

		return criteria.list();
	}

	public Collection<ColaboradorResposta> findRespostasColaborador(Long colaboradorQuestionarioId, Boolean aplicarPorAspecto)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cr");
		criteria.createCriteria("cr.colaboradorQuestionario", "cq", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.pergunta", "pg", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.resposta", "rs", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("pg.aspecto", "a", Criteria.LEFT_JOIN);

		ProjectionList cr = Projections.projectionList().create();

		cr.add(Projections.property("pg.id"), "projectionPerguntaId");
		cr.add(Projections.property("pg.texto"), "projectionPerguntaTexto");
		cr.add(Projections.property("pg.tipo"), "projectionPerguntaTipo");
		cr.add(Projections.property("pg.ordem"), "projectionPerguntaOrdem");
		cr.add(Projections.property("pg.aspecto.id"), "projectionPerguntaAspectoId");
		cr.add(Projections.property("a.nome"), "projectionPerguntaAspectoNome");
		cr.add(Projections.property("cr.comentario"), "comentario");
		cr.add(Projections.property("cr.valor"), "valor");
		cr.add(Projections.property("cr.resposta.id"), "projectionRespostaId");
		cr.add(Projections.property("rs.texto"), "projectionRespostaTexto");
		cr.add(Projections.property("c.nomeComercial"), "projectionColaboradorNomeComercial");

		criteria.setProjection(cr);

		criteria.add(Expression.eq("cq.id", colaboradorQuestionarioId));

		if (aplicarPorAspecto != null && aplicarPorAspecto)
		{
			criteria.addOrder(Order.asc("pg.aspecto.id"));
		}
		else
		{
			criteria.addOrder(Order.asc("pg.ordem"));
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public void removeByColaboradorQuestionario(Long colaboradorQuestionarioId)
	{
		String queryHQL = "delete from ColaboradorResposta r where r.colaboradorQuestionario.id = :colaboradorQuestionarioId";

		Query query = getSession().createQuery(queryHQL);

		query.setLong("colaboradorQuestionarioId", colaboradorQuestionarioId);

		query.executeUpdate();
	}
	
	public void removeByColaboradorQuestionario(Long[] colaboradorQuestionarioIds) 
	{
		String queryHQL = "delete from ColaboradorResposta r where r.colaboradorQuestionario.id in (:colaboradorQuestionarioIds)";
		
		Query query = getSession().createQuery(queryHQL);
		
		query.setParameterList("colaboradorQuestionarioIds", colaboradorQuestionarioIds, Hibernate.LONG);
		
		query.executeUpdate();
	}

	public Collection<ColaboradorResposta> findByQuestionarioColaborador(Long questionarioId, Long colaboradorId, Long turmaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cr");
		criteria.createCriteria("cr.colaboradorQuestionario", "cq", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.pergunta", "p", Criteria.LEFT_JOIN);

		ProjectionList cr = Projections.projectionList().create();

		cr.add(Projections.property("cr.id"), "id");
		cr.add(Projections.property("cr.valor"), "valor");
		cr.add(Projections.property("cr.comentario"), "comentario");
		cr.add(Projections.property("cr.resposta.id"), "projectionRespostaId");
		cr.add(Projections.property("cr.pergunta.id"), "projectionPerguntaId");
		cr.add(Projections.property("p.ordem"), "projectionPerguntaOrdem");
		cr.add(Projections.property("p.texto"), "projectionPerguntaTexto");
		cr.add(Projections.property("p.tipo"), "projectionPerguntaTipo");
		cr.add(Projections.property("p.notaMinima"), "projectionPerguntaNotaMinima");		
		cr.add(Projections.property("p.notaMaxima"), "projectionPerguntaNotaMaxima");

		criteria.setProjection(cr);

        Disjunction disjunction = Expression.disjunction();
        disjunction.add(Expression.eq("cq.questionario.id", questionarioId));
        disjunction.add(Expression.eq("cq.avaliacao.id", questionarioId));
        criteria.add(disjunction);
		criteria.add(Expression.eq("c.id", colaboradorId));

		if(turmaId != null)
			criteria.add(Expression.eq("cq.turma.id", turmaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<ColaboradorResposta> findByColaboradorQuestionario(Long colaboradorQuestionarioId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cr");
		criteria.createCriteria("cr.colaboradorQuestionario", "cq", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.pergunta", "p", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.resposta", "r", Criteria.LEFT_JOIN);

		ProjectionList cr = Projections.projectionList().create();

		cr.add(Projections.property("cr.id"), "id");
		cr.add(Projections.property("cr.valor"), "valor");
		cr.add(Projections.property("cr.comentario"), "comentario");
		cr.add(Projections.property("cr.resposta.id"), "projectionRespostaId");
		cr.add(Projections.property("cr.pergunta.id"), "projectionPerguntaId");
		cr.add(Projections.property("p.ordem"), "projectionPerguntaOrdem");
		cr.add(Projections.property("p.texto"), "projectionPerguntaTexto");
		cr.add(Projections.property("p.tipo"), "projectionPerguntaTipo");
		cr.add(Projections.property("p.notaMinima"), "projectionPerguntaNotaMinima");		
		cr.add(Projections.property("p.notaMaxima"), "projectionPerguntaNotaMaxima");
		cr.add(Projections.property("p.peso"), "projectionPerguntaPeso");
		cr.add(Projections.property("r.peso"), "projectionRespostaPeso");

		criteria.setProjection(cr);

		criteria.add(Expression.eq("cq.id", colaboradorQuestionarioId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	public Collection<ColaboradorResposta> findByQuestionarioCandidato(Long questionarioId, Long candidatoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cr");
		criteria.createCriteria("cr.colaboradorQuestionario", "cq", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.candidato", "c", Criteria.LEFT_JOIN);
		
		ProjectionList cr = Projections.projectionList().create();
		
		cr.add(Projections.property("cr.id"), "id");
		cr.add(Projections.property("cr.valor"), "valor");
		cr.add(Projections.property("cr.comentario"), "comentario");
		cr.add(Projections.property("cr.resposta.id"), "projectionRespostaId");
		cr.add(Projections.property("cr.pergunta.id"), "projectionPerguntaId");
		
		criteria.setProjection(cr);
		
		criteria.add(Expression.eq("q.id", questionarioId));
		criteria.add(Expression.eq("c.id", candidatoId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public Collection<ColaboradorResposta> findByAvaliadoAndAvaliacaoDesempenho(Long avaliadoId, Long avaliacaoDesempenhoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"cr");
		criteria.createCriteria("cr.colaboradorQuestionario", "cq");
		criteria.createCriteria("cq.colaborador", "c");
		criteria.createCriteria("cq.avaliador", "av");
		criteria.createCriteria("cq.avaliacaoDesempenho", "ad");
		criteria.createCriteria("cr.pergunta", "p");
		criteria.createCriteria("cr.resposta", "r", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cr.id"), "id");
		p.add(Projections.property("cq.id"), "projectionColaboradorQuestionarioId");
		p.add(Projections.property("cr.comentario"), "comentario");
		p.add(Projections.property("cr.valor"), "valor");
		p.add(Projections.property("cr.resposta"), "resposta");
		p.add(Projections.property("r.id"), "projectionRespostaId");
		p.add(Projections.property("r.ordem"), "projectionRespostaOrdem");
		p.add(Projections.property("cr.pergunta"), "pergunta");
		p.add(Projections.property("c.nomeComercial"), "projectionColaboradorNomeComercial");
		p.add(Projections.property("c.id"), "projectionColaboradorId");
		p.add(Projections.property("av.id"), "projectionAvaliadorId");
		p.add(Projections.property("av.nomeComercial"), "projectionAvaliadorNomeComercial");

		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", avaliadoId));
		criteria.add(Expression.eq("ad.id", avaliacaoDesempenhoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorResposta.class));

		return criteria.list();
	}

	public List<Object[]> countRespostas(Long avaliadoId, Long avaliacaoDesempenhoId)
	{
		String queryHQL =	"select r.ordem, count(r.id), p.id, r.id, " +
							
							"				(select count(crsub.pergunta.id) " +
							"				from ColaboradorResposta as crsub " +
							"				left join crsub.colaboradorQuestionario as cqsub " +
							"				where crsub.pergunta.id = p.id " +
							"				and crsub.resposta.id is not null " +
							" 				and cqsub.avaliacaoDesempenho.id = :avaliacaoDesempenhoId " +
							" 				and cqsub.colaborador.id = :avaliadoId " +
							"				group by crsub.pergunta.id) " +
							
							"from ColaboradorResposta cr " +
							"left join cr.resposta as r "	+
							"left join cr.pergunta as p "	+
							"left join cr.colaboradorQuestionario as cq "	+
							"where p.tipo = :tipoPergunta "+
							"and cr.resposta.id is not null " +
							"and cq.avaliacaoDesempenho.id = :avaliacaoDesempenhoId " +
							"and cq.colaborador.id = :avaliadoId " +
							"group by r.ordem, p.id, r.id "+
							"order by r.ordem ";

		Query query = getSession().createQuery(queryHQL);
		
		query.setInteger("tipoPergunta", TipoPergunta.OBJETIVA);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		query.setLong("avaliadoId", avaliadoId);
		
		return query.list();
	}
	
	public List<Object[]> countRespostasMultiplas(Long avaliadoId, Long avaliacaoDesempenhoId)
	{
		String queryHQL =	"select r.ordem, count(r.id), p.id, r.id " +
		"from ColaboradorResposta cr " +
		"left join cr.areaOrganizacional as a "	+
		"left join cr.estabelecimento as e "	+
		"left join cr.resposta as r "	+
		"left join cr.pergunta as p "	+
		"left join cr.colaboradorQuestionario as cq "	+
		"where p.tipo = :tipoPergunta "+
		"and cr.resposta.id is not null " +
		"and cq.avaliacaoDesempenho.id = :avaliacaoDesempenhoId " +
		"and cq.colaborador.id = :avaliadoId " +
		"group by r.ordem, p.id, r.id "+
		"order by r.ordem ";
		
		Query query = getSession().createQuery(queryHQL);
		query.setInteger("tipoPergunta", TipoPergunta.MULTIPLA_ESCOLHA);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		query.setLong("avaliadoId", avaliadoId);
		
		return query.list();
	}

	public Collection<ColaboradorResposta> findRespostasAvaliacaoDesempenho(Long colaboradorQuestionarioId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cr");
		criteria.createCriteria("cr.colaboradorQuestionario", "cq");
		criteria.createCriteria("cr.resposta", "resp", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.pergunta", "perg");
		criteria.createCriteria("perg.aspecto", "asp", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cr.id"), "id");
		p.add(Projections.property("cr.valor"), "valor");
		p.add(Projections.property("cr.comentario"), "comentario");
		p.add(Projections.property("cq.id"), "projectionColaboradorQuestionarioId");
		p.add(Projections.property("cq.observacao"), "projectionColaboradorQuestionarioObservacao");
		p.add(Projections.property("perg.id"), "projectionPerguntaId");
		p.add(Projections.property("perg.ordem"), "projectionPerguntaOrdem");
		p.add(Projections.property("perg.texto"), "projectionPerguntaTexto");
		p.add(Projections.property("perg.comentario"), "projectionPerguntaComentario");
		p.add(Projections.property("perg.tipo"), "projectionPerguntaTipo");
		p.add(Projections.property("resp.id"), "projectionRespostaId");
		p.add(Projections.property("resp.ordem"), "projectionRespostaOrdem");
		p.add(Projections.property("resp.texto"), "projectionRespostaTexto");
		p.add(Projections.property("resp.peso"), "projectionRespostaPeso");
		p.add(Projections.property("asp.id"), "projectionPerguntaAspectoId");
		p.add(Projections.property("asp.nome"), "projectionPerguntaAspectoNome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cq.id", colaboradorQuestionarioId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
}