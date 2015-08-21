package com.fortes.rh.dao.hibernate.pesquisa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.pesquisa.ColaboradorRespostaDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.relatorio.RespostaQuestionarioVO;

@SuppressWarnings("unchecked")
public class ColaboradorRespostaDaoHibernate extends GenericDaoHibernate<ColaboradorResposta> implements ColaboradorRespostaDao
{
	public List<Object[]> countRespostas(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Long empresaId, Character tipoModeloAvaliacao)
	{
		String whereEmpresa = "";
		String whereAreas = "";
		String whereAreasSub = "";
		String whereCargos = "";
		String whereCargosSub = "";
		String whereEstabelecimentos = "";
		String whereEstabelecimentosSub = "";
		String wherePerguntas = "";
		String wherePeriodoIni = "";
		String wherePeriodoIniSub = "";
		String wherePeriodoFim = "";
		String wherePeriodoFimSub = "";
		String whereTurmaSub = "";
		String whereTurma = "";
		String whereColaboradorQuestionarioSub = "";
		String whereColaboradorQuestionario = "";

		if(empresaId != null && empresaId != -1)
			whereEmpresa = "and c.empresa.id = :empresaId ";
		
		if(perguntasIds != null && perguntasIds.length > 0)
			wherePerguntas = "and p.id in (:perguntasIds) ";
		
		if(areasIds != null && areasIds.length > 0)
		{
			whereAreas = "and a.id in (:areasIds) ";
			whereAreasSub = "and crsub.areaOrganizacional.id in (:areasIds) ";
		}
		
		if(cargosIds != null && cargosIds.length > 0)
		{
			whereCargos = "and cr.cargo.id in (:cargosIds) ";
			whereCargosSub = "and crsub.cargo.id in (:cargosIds) ";
		}

		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
		{
			whereEstabelecimentos = "and e.id in (:estabelecimentosIds) ";
			whereEstabelecimentosSub = "and crsub.estabelecimento.id in (:estabelecimentosIds) ";
		}
		
		String campo = desligamento ? "c.dataDesligamento" : "cq.respondidaEm";
		String campoSub = desligamento ? "csub.dataDesligamento" : "cqsub.respondidaEm";
		
		if(periodoIni != null)
		{
			wherePeriodoIni = "and " + campo + " >= :periodoIni ";
			wherePeriodoIniSub = "and " + campoSub + " >= :periodoIni ";
		}
		
		if(periodoFim != null)
		{
			wherePeriodoFim = "and " + campo + " <= :periodoFim ";
			wherePeriodoFimSub = "and " + campoSub + " <= :periodoFim ";
		}
		
		if(turmaId != null)
		{
			whereTurmaSub = "and cqsub.turma.id = :turmaId ";
			whereTurma = "and cq.turma.id = :turmaId ";			
		}
		
		if(tipoModeloAvaliacao != null)
		{
			if(tipoModeloAvaliacao.equals(TipoModeloAvaliacao.DESEMPENHO)){
				whereColaboradorQuestionarioSub = "   and cqsub.avaliacaoDesempenho.id is not null " ;
				whereColaboradorQuestionario =  "   and cq.avaliacaoDesempenho.id is not null " ;
			}
			else if(tipoModeloAvaliacao.equals(TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA)){
				whereColaboradorQuestionarioSub = "   and cqsub.avaliacaoDesempenho.id is null " ;
				whereColaboradorQuestionario =  "   and cq.avaliacaoDesempenho.id is null " ;
			}
		}

		String queryHQL =	"select r.ordem, count(r.id), p.id, r.id, " +
							"   (select count(crsub.pergunta.id) from ColaboradorResposta as crsub " +
							"   left join crsub.colaboradorQuestionario as cqsub " +
							"   left join cqsub.colaborador as csub " +
							"   where crsub.pergunta.id = p.id " +
							"   and crsub.resposta.id is not null " +
								whereAreasSub +
								whereCargosSub +
								whereEstabelecimentosSub +
								wherePeriodoIniSub +
								wherePeriodoFimSub +
								whereTurmaSub +
								whereColaboradorQuestionarioSub +
							"   group by crsub.pergunta.id) " +
							"from ColaboradorResposta cr " +
							"left join cr.areaOrganizacional as a "	+
							"left join cr.estabelecimento as e "	+
							"left join cr.resposta as r "	+
							"left join cr.pergunta as p "	+
							"left join cr.colaboradorQuestionario as cq "	+
							"left join cq.colaborador as c " +
							"where p.tipo = :tipoPergunta "+
							"and cr.resposta.id is not null " +
							wherePerguntas +
							whereAreas +
							whereCargos +
							whereEstabelecimentos +
							wherePeriodoIni +
							wherePeriodoFim +
							whereTurma +
							whereColaboradorQuestionario +
							whereEmpresa +
							"group by r.ordem, p.id, r.id "+
							"order by r.ordem ";

		Query query = getSession().createQuery(queryHQL);
		query.setInteger("tipoPergunta", TipoPergunta.OBJETIVA);

		if(perguntasIds != null && perguntasIds.length > 0)
			query.setParameterList("perguntasIds", perguntasIds, Hibernate.LONG);

		if(areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		if(cargosIds != null && cargosIds.length > 0)
			query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);

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

	public List<Object[]> countRespostasMultiplas(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Long empresaId)
	{
		String whereEmpresa = "";
		String whereAreas = "";
		String whereCargos = "";
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
		
		if(cargosIds != null && cargosIds.length > 0)
			whereCargos = "and cr.cargo.id in (:cargosIds) ";
		
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			whereEstabelecimentos = "and e.id in (:estabelecimentosIds) ";
		
		if(periodoIni != null)
			wherePeriodoIni = desligamento ? "and c.dataDesligamento >= :periodoIni " : "and cq.respondidaEm >= :periodoIni ";
		
		if(periodoFim != null)
			wherePeriodoFim = desligamento ? "and c.dataDesligamento <= :periodoFim " : "and cq.respondidaEm <= :periodoFim ";
		
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
		whereCargos +
		whereEstabelecimentos +
		wherePeriodoIni +
		wherePeriodoFim +
		whereTurma +
		whereEmpresa +
		"group by r.ordem, p.id, r.id "+
		"order by p.id, r.ordem ";
		
		Query query = getSession().createQuery(queryHQL);
		query.setInteger("tipoPergunta", TipoPergunta.MULTIPLA_ESCOLHA);
		
		if(perguntasIds != null && perguntasIds.length > 0)
			query.setParameterList("perguntasIds", perguntasIds, Hibernate.LONG);
		
		if(areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		if(cargosIds != null && cargosIds.length > 0)
			query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);

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
	
	public Collection<ColaboradorResposta> findInPerguntaIds(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Questionario questionario, Long empresaId)
	{
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
				.setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"))
				.add(Restrictions.le("hc2.data", new Date()))
				.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
		
		Criteria criteria = getSession().createCriteria(getEntityClass(),"cr");
		criteria.createCriteria("cr.colaboradorQuestionario", "cq", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.turma", "t", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.historicoColaboradors", "hc", Criteria.LEFT_JOIN);

		if(desligamento){
			criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.LEFT_JOIN);
			criteria.createCriteria("fs.cargo", "ca", Criteria.LEFT_JOIN);
			criteria.createCriteria("hc.areaOrganizacional", "a", Criteria.LEFT_JOIN);
		} else {
			criteria.createCriteria("cr.areaOrganizacional", "a", Criteria.LEFT_JOIN);
		}
		
		criteria.createCriteria("cq.candidato", "cand", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.estabelecimento", "e", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.pergunta", "p", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.resposta", "r", Criteria.LEFT_JOIN);
		
		criteria.add(Expression.or(Property.forName("hc.data").eq(subQueryHc), Expression.isNull("hc.data")));

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

		if(cargosIds != null && cargosIds.length > 0){
			String cargo = desligamento ? "ca.id" : "cr.cargo.id";
			criteria.add(Expression.in(cargo, cargosIds));
		}
		
		if(empresaId != null && empresaId != -1)
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			criteria.add(Expression.in("e.id", estabelecimentosIds));

		String periodo = desligamento ? "c.dataDesligamento" : "cq.respondidaEm";
		
		if(periodoIni != null)
			criteria.add(Expression.ge(periodo, periodoIni));

		if(periodoFim != null)
			criteria.add(Expression.le(periodo, periodoFim));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorResposta.class));

		return criteria.list();
	}
	
	public Collection<ColaboradorResposta> findInPerguntaIdsAvaliacao(Long[] perguntasIds, Long[] areasIds, Date periodoIni, Date periodoFim, Long empresaId, Character tipoModeloAvaliacao) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"cr");
		criteria.createCriteria("cr.colaboradorQuestionario", "cq", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.turma", "t", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.avaliador", "av", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.candidato", "cand", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.areaOrganizacional", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.estabelecimento", "e", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.pergunta", "p", Criteria.LEFT_JOIN);
		criteria.createCriteria("cr.resposta", "r", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cr.id"), "id");
		p.add(Projections.property("cq.id"), "projectionColaboradorQuestionarioId");
		p.add(Projections.property("cq.observacao"), "projectionColaboradorQuestionarioObservacao");
		p.add(Projections.property("cr.comentario"), "comentario");
		p.add(Projections.property("cr.valor"), "valor");
		p.add(Projections.property("r.id"), "projectionRespostaId");
		p.add(Projections.property("r.ordem"), "projectionRespostaOrdem");
		p.add(Projections.property("p.id"), "projectionPerguntaId");
		p.add(Projections.property("c.nomeComercial"), "projectionColaboradorNomeComercial");
		p.add(Projections.property("c.nome"), "projectionColaboradorNome");
		p.add(Projections.property("av.nome"), "projectionAvaliadorNomeComercial");
		p.add(Projections.property("cand.nome"), "projectionColaboradorNomeComercial");

		criteria.setProjection(p);

		if(tipoModeloAvaliacao != null && tipoModeloAvaliacao == TipoModeloAvaliacao.DESEMPENHO)
			criteria.add(Expression.isNotNull("cq.avaliacaoDesempenho"));
		else if(tipoModeloAvaliacao != null && tipoModeloAvaliacao == TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA)
			criteria.add(Expression.isNull("cq.avaliacaoDesempenho"));
		
		if(perguntasIds != null && perguntasIds.length > 0)
			criteria.add(Expression.in("p.id", perguntasIds));

		if(areasIds != null && areasIds.length > 0)
			criteria.add(Expression.in("a.id", areasIds));

		if(empresaId != null && empresaId != -1)
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		String periodo = "cq.respondidaEm";
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
	
	public void removeByQuestionarioId(Long questionarioId)
	{
		String queryHQL = "delete from ColaboradorResposta where pergunta.id in (select id from Pergunta where questionario.id = :questionarioId)";
		
		Query query = getSession().createQuery(queryHQL);
		
		query.setLong("questionarioId", questionarioId);
		
		query.executeUpdate();
	}
	
	public void removeByColaboradorQuestionario(Long[] colaboradorQuestionarioIds) 
	{
		String queryHQL = "delete from ColaboradorResposta r where r.colaboradorQuestionario.id in (:colaboradorQuestionarioIds)";
		
		Query query = getSession().createQuery(queryHQL);
		
		query.setParameterList("colaboradorQuestionarioIds", colaboradorQuestionarioIds, Hibernate.LONG);
		
		query.executeUpdate();
	}

	public Collection<ColaboradorResposta> findByQuestionarioColaborador(Long questionarioId, Long colaboradorId, Long turmaId, Long colaboradorQuestionarioId)
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

		if(colaboradorQuestionarioId != null)
			criteria.add(Expression.eq("cr.colaboradorQuestionario.id", colaboradorQuestionarioId));
			
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
		cr.add(Projections.property("r.texto"),"projectionRespostaTexto");

		criteria.setProjection(cr);

		criteria.add(Expression.eq("cq.id", colaboradorQuestionarioId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	public Collection<ColaboradorResposta> findByQuestionarioCandidato(Long questionarioId, Long candidatoId, Long colaboradorQuestionarioId)
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
		if(colaboradorQuestionarioId != null)
			criteria.add(Expression.eq("cq.id", colaboradorQuestionarioId));
		
		criteria.add(Expression.eq("q.id", questionarioId));
		criteria.add(Expression.eq("c.id", candidatoId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public Collection<ColaboradorResposta> findByAvaliadoAndAvaliacaoDesempenho(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao)
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
		p.add(Projections.property("cq.observacao"), "projectionColaboradorQuestionarioObservacao");
		p.add(Projections.property("cr.comentario"), "comentario");
		p.add(Projections.property("cr.valor"), "valor");
		p.add(Projections.property("cr.resposta"), "resposta");
		p.add(Projections.property("r.id"), "projectionRespostaId");
		p.add(Projections.property("r.ordem"), "projectionRespostaOrdem");
		p.add(Projections.property("cr.pergunta"), "pergunta");
		p.add(Projections.property("c.nomeComercial"), "projectionColaboradorNomeComercial");
		p.add(Projections.property("c.nome"), "projectionColaboradorNome");
		p.add(Projections.property("c.id"), "projectionColaboradorId");
		p.add(Projections.property("av.id"), "projectionAvaliadorId");
		p.add(Projections.property("av.nomeComercial"), "projectionAvaliadorNomeComercial");

		criteria.setProjection(p);

		criteria.add(Expression.eq("ad.id", avaliacaoDesempenhoId));
		criteria.add(Expression.eq("c.id", avaliadoId));

		if (desconsiderarAutoAvaliacao)
			criteria.add(Expression.ne("av.id", avaliadoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorResposta.class));

		return criteria.list();
	}

	public List<Object[]> countRespostas(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao)
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
							"and cq.colaborador.id = :avaliadoId ";
							
		if(desconsiderarAutoAvaliacao)
			queryHQL += "and cq.colaborador.id <> cq.avaliador.id ";
							
		queryHQL += "group by r.ordem, p.id, r.id order by r.ordem ";

		Query query = getSession().createQuery(queryHQL);
		
		query.setInteger("tipoPergunta", TipoPergunta.OBJETIVA);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		query.setLong("avaliadoId", avaliadoId);
		
		return query.list();
	}
	
	public List<Object[]> countRespostasMultiplas(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao)
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
		"and cq.colaborador.id = :avaliadoId ";
		
		if(desconsiderarAutoAvaliacao)
			queryHQL += " and cq.colaborador.id <> cq.avaliador.id ";
		
		queryHQL += " group by r.ordem, p.id, r.id order by r.ordem ";
		
		Query query = getSession().createQuery(queryHQL);
		query.setInteger("tipoPergunta", TipoPergunta.MULTIPLA_ESCOLHA);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		query.setLong("avaliadoId", avaliadoId);
		
		return query.list();
	}

	public Collection<RespostaQuestionarioVO> findRespostasAvaliacaoDesempenho(Long colaboradorQuestionarioId) 
	{
		StringBuffer hql = new StringBuffer();
		hql.append("select cq.id, cq.performance, p.id as pergunta_id, p.ordem as pergunta_ordem, p.texto as pergunta_texto, p.comentario as pergunta_comentario, p.textocomentario as pergunta_textocomentario, p.tipo as pergunta_tipo, ");
		hql.append("r.id as resposta_id, r.ordem as resposta_ordem, r.texto as resposta_texto, r.peso as resposta_peso, ");
		hql.append("a.id as aspecto_id, a.nome as aspecto_nome, cq.observacao, ");
		hql.append("cr.resposta_id as cr_resposta_id, cr.valor as cr_resposta_valor, cr.comentario as cr_comentario ");  
		hql.append("from colaboradorquestionario cq ");
		hql.append("inner join avaliacao av on cq.avaliacao_id = av.id "); 
		hql.append("inner join pergunta p on p.avaliacao_id = av.id ");
		hql.append("left join resposta r on r.pergunta_id = p.id ");
		hql.append("left  join aspecto a on a.id = p.aspecto_id ");
		hql.append("left join colaboradorresposta cr on cr.pergunta_id = p.id and (r.id = cr.resposta_id or cr.resposta_id is null) and cq.id = cr.colaboradorquestionario_id "); 
		hql.append("where cq.id = :colaboradorQuestionarioId ");
		hql.append("order by p.ordem, r.ordem");
		
		
		SQLQuery query = getSession().createSQLQuery(hql.toString());
		query.setLong("colaboradorQuestionarioId", colaboradorQuestionarioId);
		
		query.addScalar("id", Hibernate.LONG);
		query.addScalar("performance", Hibernate.DOUBLE);
		query.addScalar("pergunta_id", Hibernate.LONG);
		query.addScalar("pergunta_ordem", Hibernate.INTEGER);
		query.addScalar("pergunta_texto", Hibernate.STRING);
		query.addScalar("pergunta_comentario", Hibernate.BOOLEAN);
		query.addScalar("pergunta_textocomentario", Hibernate.STRING);
		query.addScalar("pergunta_tipo", Hibernate.INTEGER);
		query.addScalar("resposta_id", Hibernate.LONG);
		query.addScalar("resposta_ordem", Hibernate.INTEGER);
		query.addScalar("resposta_texto", Hibernate.STRING);
		query.addScalar("resposta_peso", Hibernate.INTEGER);
		query.addScalar("aspecto_id", Hibernate.LONG);
		query.addScalar("aspecto_nome", Hibernate.STRING);
		query.addScalar("observacao", Hibernate.STRING);
		query.addScalar("cr_resposta_id", Hibernate.LONG);
		query.addScalar("cr_resposta_valor", Hibernate.INTEGER);
		query.addScalar("cr_comentario", Hibernate.STRING);
		
		Collection<RespostaQuestionarioVO> vos = new ArrayList<RespostaQuestionarioVO>();
		Collection<Object[]> lista = query.list();
		int i;
		for (Object[] obj : lista) {
			i = 0;
			vos.add(new RespostaQuestionarioVO((Long)obj[i++], (Double)obj[i++], (Long)obj[i++], (Integer)obj[i++], (String)obj[i++], (Boolean)obj[i++], (String)obj[i++], (Integer)obj[i++], (Long)obj[i++], (Integer)obj[i++], (String)obj[i++], (Integer)obj[i++], (Long)obj[i++], (String)obj[i++], (String)obj[i++], (Long)obj[i++], (Integer)obj[i++], (String)obj[i++]));
		}
		return vos;
	}

	public Integer countColaboradorAvaliacaoRespondida(Long avaliacaoId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cr");
		criteria.createCriteria("cr.colaboradorQuestionario", "cq", Criteria.LEFT_JOIN);

		ProjectionList cr = Projections.projectionList().create();
		cr.add(Projections.property("cr.id"), "id");

		criteria.setProjection(cr);

		criteria.add(Expression.eq("cq.avaliacao.id", avaliacaoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list().size();
	}
	
	public boolean verificaQuantidadeColaboradoresQueResponderamPesquisaAnonima(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cargosIds, Long questionarioId, int quantidadeColaboradoresRelatorioPesquisaAnonima) 
	{
		String queryHQL = "select distinct count(pergunta.id) from ColaboradorResposta cr ";
		
		if(perguntasIds != null && perguntasIds.length > 0) {
			queryHQL += "where pergunta.id in ( :perguntasIds ) ";
		} else
			queryHQL += "where pergunta.id in (select p2.id from Pergunta p2 where p2.questionario.id = :questionarioId ) ";
		
		if(areasIds != null && areasIds.length > 0)
			queryHQL += "and areaOrganizacional.id in (:areasIds) ";
		
		if(cargosIds != null && cargosIds.length > 0)
			queryHQL += "and cargo.id in (:cargosIds) ";
		
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			queryHQL += "and estabelecimento.id in (:estabelecimentosIds) ";
		
		queryHQL += "group by colaboradorQuestionario.id, pergunta.id having count(pergunta.id) <= :quantidadeColaboradoresRelatorioPesquisaAnonima";

		Query query = getSession().createQuery(queryHQL);
		if(perguntasIds != null && perguntasIds.length > 0)
			query.setParameterList("perguntasIds", perguntasIds, Hibernate.LONG);
		else
			query.setLong("questionarioId", questionarioId);

		if(areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		if(cargosIds != null && cargosIds.length > 0)
			query.setParameterList("cargosIds", cargosIds, Hibernate.LONG);
		
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		
		query.setInteger("quantidadeColaboradoresRelatorioPesquisaAnonima", quantidadeColaboradoresRelatorioPesquisaAnonima);
		
		return query.list().size() > 0;
	}

	public boolean existeRespostaSemCargo(Long[] perguntasIds) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cr");

		ProjectionList cr = Projections.projectionList().create();
		cr.add(Projections.property("cr.id"), "id");

		criteria.setProjection(cr);

		criteria.add(Expression.in("cr.pergunta.id", perguntasIds));
		criteria.add(Expression.isNull("cr.cargo.id"));

		return criteria.list().size() > 0;  
	}

	public Collection<ColaboradorResposta> findPerguntasRespostasByColaboradorQuestionario(Long colaboradorQuestionarioId, boolean agruparPorAspecto) 
	{
		getSession().flush();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select a.id as apId, a.nome as apNome, p.id as pId, p.ordem as pOrdem, p.texto as pTexto, p.textoComentario as pTextoCom, p.tipo as pTipo, r.texto as rTexto, ");
		sql.append("(select comentario from colaboradorresposta cr2  where cr2.colaboradorquestionario_id =  cq.id and cr2.pergunta_id = p.id and comentario is not null limit 1) as crCom, cr.valor as crValor, cr.resposta_id as crRespId ");  
		sql.append("from colaboradorquestionario cq ");
		sql.append("left join pergunta p on cq.avaliacao_id = p.avaliacao_id "); 
		sql.append("left join aspecto a on p.aspecto_id = a.id ");
		sql.append("left join resposta r on r.pergunta_id = p.id ");
		sql.append("left join colaboradorresposta cr on cr.colaboradorquestionario_id = cq.id and cr.pergunta_id = p.id and ((p.tipo in (1,5) and cr.resposta_id = r.id) or cr.resposta_id is null) ");
		sql.append("where cq.id = :colaboradorQuestionarioId ");
		if (agruparPorAspecto) {
			sql.append("order by a.nome, p.ordem, r.ordem");
		} else {
			sql.append("order by p.ordem, a.nome, r.ordem");
		}
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("colaboradorQuestionarioId", colaboradorQuestionarioId);
		
		Collection<Object[]> resultado = query.list();
		Collection<ColaboradorResposta> lista = new ArrayList<ColaboradorResposta>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			lista.add(new ColaboradorResposta(	(res[0] != null ? ((BigInteger)res[0]).longValue() : null ),
					(res[1] != null ? (String)res[1] : null), 
					(res[2] != null ? ((BigInteger)res[2]).longValue() : null ), 
					(Integer)res[3], 
					(String)res[4], 
					(String)res[5], 
					(Integer)res[6], 
					(String)res[7], 
					(String)res[8], 
					(Integer)res[9],
					res[10] != null ? ((BigInteger)res[10]).longValue() : null ) );
		}
		
		return lista;
	}
}