package com.fortes.rh.dao.hibernate.pesquisa;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pesquisa;

@SuppressWarnings("unchecked")
public class ColaboradorQuestionarioDaoHibernate extends GenericDaoHibernate<ColaboradorQuestionario> implements ColaboradorQuestionarioDao
{
	public int valorTipoQuestionario;

	public Collection<ColaboradorQuestionario> findByQuestionario(Long questionarioOrAvaliacaoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cq.avaliacao", "avaliacao", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c");
		criteria.createCriteria("c.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondida"), "respondida");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("c.id"), "projectionColaboradorId");
		p.add(Projections.property("emp.id"), "projectionColaboradorEmpresaId");
		p.add(Projections.property("emp.nome"), "projectionColaboradorEmpresaNome");
		p.add(Projections.property("c.contato.email"), "projectionColaboradorContatoEmail");
		p.add(Projections.property("c.nomeComercial"), "projectionColaboradorNomeComercial");
		p.add(Projections.property("c.nome"), "projectionColaboradorNome");
		p.add(Projections.property("c.desligado"), "projectionColaboradorDesligado");

		Disjunction disjunction = Expression.disjunction();
	    disjunction.add(Expression.eq("q.id", questionarioOrAvaliacaoId));
	    disjunction.add(Expression.eq("avaliacao.id", questionarioOrAvaliacaoId));
	    criteria.add(disjunction);

		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("c.nomeComercial"));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<ColaboradorQuestionario> findByQuestionarioEmpresaRespondida(Long questionarioOrAvaliacaoId, Boolean respondida, Collection<Long> estabelecimentoIds, Long empresaId)
	{
		DetachedCriteria subQuery = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2");
        ProjectionList pSub = Projections.projectionList().create();

        pSub.add(Projections.max("hc2.data"));
        subQuery.setProjection(pSub);

        subQuery.add(Restrictions.sqlRestriction("this0__.colaborador_id=c3_.id"));
        subQuery.add(Expression.le("hc2.data", new Date()));
        subQuery.add(Expression.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
        
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cq.avaliacao", "avaliacao", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c");
		criteria.createCriteria("c.empresa", "emp");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("hc.areaOrganizacional", "ao", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("hc.estabelecimento", "e", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondida"), "respondida");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("c.id"), "projectionColaboradorId");
		p.add(Projections.property("c.desligado"), "projectionColaboradorDesligado");
		p.add(Projections.property("emp.id"), "projectionColaboradorEmpresaId");
		p.add(Projections.property("emp.nome"), "projectionColaboradorEmpresaNome");
		p.add(Projections.property("c.contato.email"), "projectionColaboradorContatoEmail");
		p.add(Projections.property("c.nomeComercial"), "projectionColaboradorNomeComercial");
		p.add(Projections.property("c.nome"), "projectionColaboradorNome");
		p.add(Projections.property("ao.nome"), "projectionAreaOrganizacionalNome");
		p.add(Projections.property("e.nome"), "estabelecimentoNomeProjection");
		
		Disjunction disjunction = Expression.disjunction();
		disjunction.add(Expression.eq("q.id", questionarioOrAvaliacaoId));
		disjunction.add(Expression.eq("avaliacao.id", questionarioOrAvaliacaoId));
		criteria.add(disjunction);
		
		criteria.add(Subqueries.propertyEq("hc.data", subQuery));
		criteria.setProjection(Projections.distinct(p));
		
		if(empresaId != null && !empresaId.equals(-1L))
			criteria.add(Expression.eq("emp.id", empresaId));

		if(estabelecimentoIds != null && estabelecimentoIds.size() > 0)
			criteria.add(Expression.in("hc.estabelecimento.id", estabelecimentoIds));
		
		if(respondida != null)
			criteria.add(Expression.eq("cq.respondida", respondida));
		
		criteria.addOrder(Order.asc("c.nomeComercial"));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public ColaboradorQuestionario findByQuestionario(Long questionarioId, Long colaboradorId, Long turmaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondida"), "respondida");
		p.add(Projections.property("cq.respondidaEm"), "respondidaEm");
		p.add(Projections.property("cq.colaborador.id"), "projectionColaboradorId");
		p.add(Projections.property("cq.questionario.id"), "projectionQuestionarioId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("q.id", questionarioId));
		criteria.add(Expression.eq("c.id", colaboradorId));

		if(turmaId != null)
			criteria.add(Expression.eq("cq.turma.id", turmaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (ColaboradorQuestionario) criteria.uniqueResult();
	}
	
	public ColaboradorQuestionario findByQuestionarioCandidato(Long questionarioId, Long candidatoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.candidato", "c", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondida"), "respondida");
		p.add(Projections.property("cq.colaborador.id"), "projectionColaboradorId");
		p.add(Projections.property("cq.questionario.id"), "projectionQuestionarioId");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("q.id", questionarioId));
		criteria.add(Expression.eq("c.id", candidatoId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (ColaboradorQuestionario) criteria.uniqueResult();
	}

	public ColaboradorQuestionario findColaboradorComEntrevistaDeDesligamento(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.colaborador.id"), "projectionColaboradorId");
		p.add(Projections.property("cq.questionario.id"), "projectionQuestionarioId");
		p.add(Projections.property("c.nome"), "projectionColaboradorNome");
		p.add(Projections.property("q.cabecalho"), "projectionQuestionarioCabecalho");

		criteria.setProjection(p);

		criteria.add(Expression.eq("cq.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("q.tipo", TipoQuestionario.ENTREVISTA));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (ColaboradorQuestionario) criteria.uniqueResult();
	}
	
	public ColaboradorQuestionario findColaborador(Long colaboradorId, Long questionarioId, Long turmaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.colaborador.id"), "projectionColaboradorId");
		p.add(Projections.property("cq.questionario.id"), "projectionQuestionarioId");
		p.add(Projections.property("cq.turma"), "turma");
		p.add(Projections.property("cq.respondidaEm"), "respondidaEm");
		p.add(Projections.property("c.nome"), "projectionColaboradorNome");
		p.add(Projections.property("q.cabecalho"), "projectionQuestionarioCabecalho");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cq.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("q.id", questionarioId));
		criteria.add(Expression.eq("cq.turma.id", turmaId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (ColaboradorQuestionario) criteria.uniqueResult();
	}

	public void removeByColaboradorETurma(Long colaboradorId, Long turmaId)
	{
		String hqlRespostas = "delete from ColaboradorResposta cr where cr.colaboradorQuestionario.id in " +
								"(select id from ColaboradorQuestionario cq where cq.turma.id=:turmaId ";

		String hql = "delete from ColaboradorQuestionario cq where cq.turma.id=:turmaId ";
		if (colaboradorId != null)
		{
			hql += "and cq.colaborador.id=:colaboradorId";
			hqlRespostas += "and cq.colaborador.id=:colaboradorId";
		}
		hqlRespostas += ")";

		Query queryColaboradorResposta = getSession().createQuery(hqlRespostas);
		Query queryColaboradorQuestionario = getSession().createQuery(hql);

		queryColaboradorQuestionario.setLong("turmaId", turmaId);

		if (colaboradorId != null)
		{
			queryColaboradorResposta.setLong("colaboradorId", colaboradorId);
			queryColaboradorQuestionario.setLong("colaboradorId", colaboradorId);
		}

		queryColaboradorResposta.setLong("turmaId", turmaId);

		queryColaboradorResposta.executeUpdate();
		queryColaboradorQuestionario.executeUpdate();
	}

	public Collection<ColaboradorQuestionario> findRespondidasByColaboradorETurma(Long colaboradorId, Long turmaId, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		criteria.createCriteria("cq.turma", "t", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondida"), "respondida");
		p.add(Projections.property("cq.colaborador.id"), "projectionColaboradorId");
		p.add(Projections.property("cq.questionario.id"), "projectionQuestionarioId");

		criteria.setProjection(p);

		if (colaboradorId != null)
			criteria.add(Expression.eq("cq.colaborador.id", colaboradorId));
		if (empresaId != null)
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.add(Expression.isNull("cq.avaliacaoCurso.id"));
		criteria.add(Expression.eq("cq.turma.id", turmaId));
		criteria.add(Expression.eq("cq.respondida", true));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<ColaboradorQuestionario> findFichasMedicas(Character vinculo, Date dataIni, Date dataFim, String nomeBusca, String cpfBusca, String matriculaBusca)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q", Criteria.LEFT_JOIN);
		
		if(vinculo == 'C')
		{
			criteria.createCriteria("cq.colaborador", "c");
			
			if(StringUtils.isNotBlank(matriculaBusca))
				criteria.add(Expression.like("c.matricula", "%"+ matriculaBusca +"%").ignoreCase());			
		}
		else if(vinculo == 'A')
			criteria.createCriteria("cq.candidato", "c");
			
		if(StringUtils.isNotBlank(nomeBusca))
			criteria.add(Expression.like("c.nome", "%"+ nomeBusca +"%").ignoreCase());
		
		if(StringUtils.isNotBlank(cpfBusca))
			criteria.add(Expression.like("c.pessoal.cpf", "%"+ cpfBusca +"%").ignoreCase());			

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondidaEm"), "respondidaEm");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("q.titulo"), "ProjectionQuestionarioTitulo");
		p.add(Projections.property("c.nome"), "pessoaNome");
		
		criteria.setProjection(p);

		criteria.add(Expression.eq("q.tipo", TipoQuestionario.FICHAMEDICA));
		
		criteria.addOrder(Order.desc("cq.respondidaEm"));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<ColaboradorQuestionario> findFichasMedicas()
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "colab", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.candidato", "cand", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondidaEm"), "respondidaEm");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("q.titulo"), "ProjectionQuestionarioTitulo");
		p.add(Projections.property("colab.id"), "projectionColaboradorId");
		p.add(Projections.property("colab.nome"), "projectionColaboradorNome");
		p.add(Projections.property("cand.id"), "projectionCandidatoId");
		p.add(Projections.property("cand.nome"), "projectionCandidatoNome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("q.tipo", TipoQuestionario.FICHAMEDICA));
		
		criteria.addOrder(Order.desc("cq.respondidaEm"));
		criteria.addOrder(Order.asc("colab.nome"));
		criteria.addOrder(Order.asc("cand.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public ColaboradorQuestionario findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "colab", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.candidato", "cand", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.avaliacaoDesempenho", "avaliacaoDesempenho", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.avaliacao", "avaliacao", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondida"), "respondida");
		p.add(Projections.property("cq.respondidaEm"), "respondidaEm");
		p.add(Projections.property("cq.observacao"), "observacao");
		p.add(Projections.property("cq.performance"), "performance");
		p.add(Projections.property("cq.performanceNivelCompetencia"), "performanceNivelCompetencia");
		p.add(Projections.property("cq.configuracaoNivelCompetenciaColaborador.id"), "configuracaoNivelCompetenciaColaboradorId");
		p.add(Projections.property("colab.id"), "projectionColaboradorId");
		p.add(Projections.property("colab.nome"), "projectionColaboradorNome");
		p.add(Projections.property("cand.id"), "projectionCandidatoId");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("avaliacao.id"), "projectionAvaliacaoId");
		p.add(Projections.property("avaliacao.titulo"), "projectionAvaliacaoTitulo");
		p.add(Projections.property("avaliacao.cabecalho"), "projectionAvaliacaoCabecalho");
		p.add(Projections.property("avaliacao.exibeResultadoAutoavaliacao"), "projectionAvaliacaoExibeResultadoAutoavaliacao");
		p.add(Projections.property("avaliacao.avaliarCompetenciasCargo"), "projectionAvaliacaoAvaliarCompetenciasCargo");
		p.add(Projections.property("avaliacaoDesempenho.id"), "projectionAvaliacaoDesempenhoId");
		p.add(Projections.property("avaliacaoDesempenho.anonima"), "projectionAvaliacaoDesempenhoAnonima");
		p.add(Projections.property("avaliacaoDesempenho.titulo"), "projectionAvaliacaoDesempenhoTitulo");
		p.add(Projections.property("avaliacaoDesempenho.permiteAutoAvaliacao"), "projectionAvaliacaoDesempenhoPermiteAutoAvaliacao");
		p.add(Projections.property("avaliacaoDesempenho.inicio"), "projectionAvaliacaoDesempenhoInicio");
		p.add(Projections.property("cq.avaliador.id"), "projectionAvaliadorId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("cq.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return (ColaboradorQuestionario) criteria.uniqueResult();
	}
	
	public ColaboradorQuestionario findByIdColaboradorCandidato(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "colab", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.candidato", "cand", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondidaEm"), "respondidaEm");
		p.add(Projections.property("colab.id"), "projectionColaboradorId");
		p.add(Projections.property("colab.nome"), "projectionColaboradorNome");
		p.add(Projections.property("colab.pessoal.estadoCivil"), "projectionColaboradorEstadoCivil");
		p.add(Projections.property("colab.pessoal.dataNascimento"), "projectionColaboradorNascimento");
		p.add(Projections.property("colab.pessoal.rg"), "projectionColaboradorRg");
		p.add(Projections.property("colab.pessoal.sexo"), "projectionColaboradorSexo");
		p.add(Projections.property("cand.id"), "projectionCandidatoId");
		p.add(Projections.property("cand.nome"), "projectionCandidatoNome");
		p.add(Projections.property("cand.pessoal.estadoCivil"), "projectionCandidatoEstadoCivil");
		p.add(Projections.property("cand.pessoal.dataNascimento"), "projectionCandidatoNascimento");
		p.add(Projections.property("cand.pessoal.rg"), "projectionCandidatoRg");
		p.add(Projections.property("cand.pessoal.sexo"), "projectionCandidatoSexo");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("q.titulo"), "ProjectionQuestionarioTitulo");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cq.id", id));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return (ColaboradorQuestionario) criteria.uniqueResult();
	}

	public Collection<ColaboradorQuestionario> findAvaliacaoByColaborador(Long colaboradorId, boolean somenteAvaliacaoDesempenho)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.colaborador", "colab", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.avaliacao", "av");//não pode ser LEFT_JOIN 
		criteria.createCriteria("cq.avaliacaoDesempenho", "avd", Criteria.LEFT_JOIN); 
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondidaEm"), "respondidaEm");
		p.add(Projections.property("cq.performance"), "performance");
		p.add(Projections.property("cq.performanceNivelCompetencia"), "performanceNivelCompetencia");
		p.add(Projections.property("cq.observacao"), "observacao");
		p.add(Projections.property("avd.exibirPerformanceProfissional"), "projectionExibirPerformanceProfissional");
		p.add(Projections.property("colab.id"), "projectionColaboradorId");
		p.add(Projections.property("colab.dataAdmissao"), "projectionColaboradorDataAdmissao");
		p.add(Projections.property("av.id"), "projectionAvaliacaoId");
		p.add(Projections.property("av.titulo"), "projectionAvaliacaoTitulo");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("colab.id", colaboradorId));
		
		if(somenteAvaliacaoDesempenho == true)
			criteria.add(Expression.isNotNull("cq.avaliacaoDesempenho"));
		
		if(somenteAvaliacaoDesempenho == false)
			criteria.add(Expression.isNull("cq.avaliacaoDesempenho"));
		
		criteria.addOrder(Order.desc("cq.respondidaEm"));
		criteria.addOrder(Order.asc("colab.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public Collection<ColaboradorQuestionario> findColaboradorHistoricoByQuestionario(Long questionarioId, Boolean respondida, Long empresaId)
	{
		DetachedCriteria subQuery = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2");
        ProjectionList pSub = Projections.projectionList().create();

        pSub.add(Projections.max("hc2.data"));
        subQuery.setProjection(pSub);

        subQuery.add(Restrictions.sqlRestriction("this0__.colaborador_id=c2_.id"));
        subQuery.add(Expression.le("hc2.data", new Date()));
        subQuery.add(Expression.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q");
		criteria.createCriteria("cq.colaborador", "c");
		criteria.createCriteria("c.empresa", "emp");
		criteria.createCriteria("c.historicoColaboradors", "hc", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.estabelecimento", "es", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.LEFT_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondida"), "respondida");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("c.id"), "projectionColaboradorId");
		p.add(Projections.property("emp.id"), "projectionColaboradorEmpresaId");
		p.add(Projections.property("emp.nome"), "projectionColaboradorEmpresaNome");
		p.add(Projections.property("c.contato.email"), "projectionColaboradorContatoEmail");
		p.add(Projections.property("c.nomeComercial"), "projectionColaboradorNomeComercial");
		p.add(Projections.property("c.nome"), "projectionColaboradorNome");
		p.add(Projections.property("ao.id"), "projectionAreaOrganizacionalId");
		p.add(Projections.property("ao.nome"), "projectionAreaOrganizacionalNome");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");
		p.add(Projections.property("ca.nome"), "projectionCargoNome");
		p.add(Projections.property("es.nome"), "estabelecimentoNomeProjection");

		criteria.add(Subqueries.propertyEq("hc.data", subQuery));
		criteria.add(Expression.eq("q.id", questionarioId));

		if(empresaId != null && !empresaId.equals(-1L))
			criteria.add(Expression.eq("emp.id", empresaId));
		
		if(respondida != null)
			criteria.add(Expression.eq("cq.respondida", respondida));
		
		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("emp.nome"));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	//apagar?
	public Collection<ColaboradorQuestionario> findByColaboradorAndAvaliacaoDesempenho(Long colaboradorParticipanteId, Long avaliacaoDesempenhoId, boolean isAvaliado, boolean desconsiderarAutoAvaliacao) {
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		
		if (isAvaliado)
			criteria.createCriteria("cq.colaborador", "colab", Criteria.LEFT_JOIN);
		else
			criteria.createCriteria("cq.avaliador", "colab", Criteria.LEFT_JOIN);

		criteria.createCriteria("cq.avaliacaoDesempenho", "avDesempenho");//não pode ser LEFT_JOIN 
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondida"), "respondida");
		criteria.setProjection(p);
		
		if(desconsiderarAutoAvaliacao)
			criteria.add(Expression.neProperty("cq.colaborador", "cq.avaliador"));
		
		criteria.add(Expression.eq("colab.id", colaboradorParticipanteId));
		criteria.add(Expression.eq("avDesempenho.id", avaliacaoDesempenhoId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public void removeParticipantesSemAssociacao(Long avaliacaoDesempenhoId) 
	{
		String hql = "delete ColaboradorQuestionario cq where cq.avaliacaoDesempenho.id = :avaliacaoDesempenhoId and " +
					 "(cq.colaborador.id is null or cq.avaliador.id is null )";

		Query query = getSession().createQuery(hql);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);

		query.executeUpdate();
	}

	public Integer getCountParticipantesAssociados(Long avaliacaoDesempenhoId) {
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.avaliacaoDesempenho", "avDesempenho");//não pode ser LEFT_JOIN 
		
		criteria.setProjection(Projections.rowCount());
		
		criteria.add(Expression.isNotNull("cq.colaborador.id"));
		criteria.add(Expression.isNotNull("cq.avaliador.id"));
		criteria.add(Expression.eq("avDesempenho.id", avaliacaoDesempenhoId));
		
		return (Integer) criteria.uniqueResult();
	}

	public Collection<ColaboradorQuestionario> findByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, Boolean respondida)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		
		//Nao aumentar a projection, tem uma regra para o id do colaborado e avaliador
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondida"), "respondida");
		p.add(Projections.property("cq.avaliacaoDesempenho.id"), "projectionAvaliacaoDesempenhoId");
		p.add(Projections.property("cq.colaborador.id"), "projectionColaboradorId");
		p.add(Projections.property("cq.avaliador.id"), "projectionAvaliadorId");
		//Nao aumentar a projection
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cq.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
		
		if(respondida != null)
			criteria.add(Expression.eq("cq.respondida", respondida));
			
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
	
	public Collection<ColaboradorQuestionario> findAvaliadosByAvaliador(Long avaliacaoDesempenhoId, Long avaliadorId, Boolean respondida, boolean considerarPeriodoAvalDesempenho)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		
		criteria.createCriteria("cq.colaborador", "avaliado");
		criteria.createCriteria("cq.avaliador", "avaliador");
		criteria.createCriteria("cq.avaliacaoDesempenho", "avDesempenho");
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.performance"), "performance");
		p.add(Projections.property("cq.performanceNivelCompetencia"), "performanceNivelCompetencia");
		p.add(Projections.property("cq.respondida"), "respondida");
		p.add(Projections.property("avaliado.id"), "projectionColaboradorId");
		p.add(Projections.property("avaliado.nome"), "projectionColaboradorNome");
		p.add(Projections.property("avaliador.id"), "projectionAvaliadorId");
		p.add(Projections.property("avDesempenho.id"), "projectionAvaliacaoDesempenhoId");
		p.add(Projections.property("avDesempenho.titulo"), "projectionAvaliacaoDesempenhoTitulo");
		p.add(Projections.property("avDesempenho.inicio"), "projectionAvaliacaoDesempenhoInicio");
		p.add(Projections.property("avDesempenho.fim"), "projectionAvaliacaoDesempenhoFim");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("avaliador.id", avaliadorId));
		criteria.add(Expression.eq("avDesempenho.liberada", true));
		
		if (avaliacaoDesempenhoId != null)
			criteria.add(Expression.eq("avDesempenho.id", avaliacaoDesempenhoId));
		
		if (respondida != null)
			criteria.add(Expression.eq("cq.respondida", respondida));
		
		if(considerarPeriodoAvalDesempenho)
		{
			Date hoje = new Date();
			criteria.add(Expression.le("avDesempenho.inicio", hoje));
			criteria.add(Expression.ge("avDesempenho.fim", hoje));
		}
		
		criteria.addOrder(Order.asc("avDesempenho.titulo"));
		criteria.addOrder(Order.asc("avaliado.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorQuestionario.class));
		
		return criteria.list();
	}

	public Collection<ColaboradorQuestionario> findRespondidasByAvaliacaoDesempenho(Long[] participanteIds, Long avaliacaoDesempenhoId, boolean isAvaliado)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q", Criteria.LEFT_JOIN);
		
		if(isAvaliado)
			criteria.createCriteria("cq.colaborador", "c");
		else
			criteria.createCriteria("cq.avaliador", "c");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.colaborador.id"), "projectionColaboradorId");
		p.add(Projections.property("cq.avaliador.id"), "projectionAvaliadorId");

		criteria.setProjection(p);

		criteria.add(Expression.in("c.id", participanteIds));
		criteria.add(Expression.eq("cq.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
		criteria.add(Expression.eq("cq.respondida", true));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	public Collection<ColaboradorQuestionario> findRespondidasByAvaliacaoDesempenho(Long avaliacaoDesempenhoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		criteria.createCriteria("cq.questionario", "q", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("cq.id"), "id");
		
		criteria.setProjection(p);

		criteria.add(Expression.eq("cq.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
		criteria.add(Expression.eq("cq.respondida", true));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public void removeAssociadosSemResposta(Long avaliacaoDesempenhoId)
	{
		String hql = "delete from ColaboradorQuestionario cq where cq.respondida = false and cq.colaborador != null and cq.avaliador != null and cq.avaliacaoDesempenho.id = :avaliacaoDesempenhoId"; 

		Query query = getSession().createQuery(hql);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		
		query.executeUpdate();
	}

	public void removeByParticipante(Long avaliacaoDesempenhoId, Long[] participanteIds, boolean isAvaliado)
	{
		String participanteRemove = "";
		if (isAvaliado)
			participanteRemove = "cq.colaborador.id";
		else
			participanteRemove = "cq.avaliador.id";
		
		String hql = "delete from ColaboradorQuestionario cq where " + participanteRemove + " in (:participanteId)  and cq.avaliacaoDesempenho.id = :avaliacaoDesempenhoId"; 
		
		Query query = getSession().createQuery(hql);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		query.setParameterList("participanteId", participanteIds, Hibernate.LONG);
		
		query.executeUpdate();
	}

	public Collection<ColaboradorQuestionario> getPerformance(Collection<Long> avaliados, Long avaliacaoDesempenhoId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		
		criteria.createCriteria("cq.colaborador", "avaliado");
		criteria.createCriteria("cq.avaliador", "avaliador");
		criteria.createCriteria("cq.avaliacaoDesempenho", "avDesempenho");
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.performance"), "performance");
		p.add(Projections.property("cq.respondida"), "respondida");
		p.add(Projections.property("avaliado.id"), "projectionColaboradorId");
		p.add(Projections.property("avaliado.nome"), "projectionColaboradorNome");
		p.add(Projections.property("avaliador.id"), "projectionAvaliadorId");
		p.add(Projections.property("avaliador.nome"), "projectionAvaliadorNome");
		p.add(Projections.property("avDesempenho.id"), "projectionAvaliacaoDesempenhoId");
		p.add(Projections.property("avDesempenho.titulo"), "projectionAvaliacaoDesempenhoTitulo");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("avDesempenho.liberada", true));
		criteria.add(Expression.eq("cq.respondida", true));
		
		if (avaliacaoDesempenhoId != null)
			criteria.add(Expression.eq("avDesempenho.id", avaliacaoDesempenhoId));
		if(! avaliados.isEmpty())
			criteria.add(Expression.in("avaliado.id", avaliados));
		
		criteria.addOrder(Order.asc("avaliado.nome"));
		criteria.addOrder(Order.asc("avaliador.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorQuestionario.class));
		
		return criteria.list();
	}

	public Collection<ColaboradorQuestionario> findBySolicitacaoRespondidas(Long solicitacaoId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.candidato.id"), "projectionCandidatoId");

	    criteria.add(Expression.eq("cq.solicitacao.id", solicitacaoId));
	    criteria.add(Expression.eq("cq.respondida", true));

		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<Colaborador> findRespondidasBySolicitacao(Long solicitacaoId, Long avaliacaoId) 
	{
		StringBuilder hql = new StringBuilder();
		  hql.append("select new Colaborador(ca.nome, ca.nome, av.titulo, cq.respondidaEm, cq.performance, av.titulo) ");
		  hql.append("from ColaboradorQuestionario as cq ");
		  hql.append("left join cq.avaliacao as av ");
		  hql.append("left join cq.candidato as ca ");
		  hql.append("where ");
		  hql.append("cq.respondida = true ");
		  hql.append("and cq.solicitacao.id = :solicitacaoId ");
		  hql.append("and cq.avaliacao.id = :avaliacaoId ");
		  		  		  
		  hql.append("order by cq.performance desc, ca.nome asc ");
		  
		  Query query = getSession().createQuery(hql.toString());
		  query.setLong("solicitacaoId", solicitacaoId);
		  query.setLong("avaliacaoId", avaliacaoId);
		  
		  return query.list();
	}

	public Integer countByQuestionarioRespondido(Long questionarioId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		
		criteria.setProjection(Projections.rowCount());
		
		criteria.add(Expression.eq("cq.questionario.id", questionarioId));
		criteria.add(Expression.eq("cq.respondida", true));
		
		return (Integer) criteria.uniqueResult();
	}

	public void excluirColaboradorQuestionarioByAvaliacaoDesempenho(Long avaliacaoDesempenhoId) 
	{
		String hql = "delete from ColaboradorQuestionario cq where cq.avaliacaoDesempenho.id = :avaliacaoDesempenhoId"; 

		Query query = getSession().createQuery(hql);
		query.setLong("avaliacaoDesempenhoId", avaliacaoDesempenhoId);
		
		query.executeUpdate();
	}

	public Collection<ColaboradorQuestionario> findByColaborador(Long colaboradorId) {
		
		Criteria criteria = getSession().createCriteria(Pesquisa.class, "p");
		criteria.createCriteria("p.questionario", "q", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("q.colaboradorQuestionarios", "cq", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("q.titulo"), "projectionQuestionarioTitulo");
		p.add(Projections.property("q.dataInicio"), "projectionQuestionarioDataInicio");
		p.add(Projections.property("q.dataFim"), "projectionQuestionarioDataFim");

		criteria.add(Expression.eq("cq.respondida", true));
	    criteria.add(Expression.eq("p.exibirPerformanceProfissional", true));
	    criteria.add(Expression.eq("cq.colaborador.id", colaboradorId));

		criteria.addOrder(Order.asc("q.dataInicio"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}

	public Double getMediaPeformance(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.avg("cq.performance")) ;
		
		if (desconsiderarAutoAvaliacao)
			criteria.add(Expression.ne("cq.avaliador.id", avaliadoId));
		
		criteria.add(Expression.eq("cq.colaborador.id", avaliadoId));
	    criteria.add(Expression.eq("cq.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
	    criteria.add(Expression.eq("cq.respondida", true));

		criteria.setProjection(Projections.distinct(p));
		
		return (Double) criteria.uniqueResult();
	}

	public Integer getQtdAvaliadores(Long avaliacaoDesempenhoId, Long avaliadoId, boolean desconsiderarAutoAvaliacao) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.countDistinct("avaliador.id"));
		
	    criteria.add(Expression.eq("cq.avaliacaoDesempenho.id", avaliacaoDesempenhoId));
	    criteria.add(Expression.eq("cq.respondida", true));
	    criteria.add(Expression.eq("cq.colaborador.id", avaliadoId));

	    if (desconsiderarAutoAvaliacao)
	    	criteria.add(Expression.ne("cq.avaliador.id", avaliadoId));
	    
		criteria.setProjection(Projections.distinct(p));
		
		return (Integer) criteria.uniqueResult();
	}
	
	/**
	 * Este método retorna ColaboradorQuestionario que não contêm AvaliacaoDesempenho, 
	 * garantindo que não seja selecionado uma avaliação de desempenho que está usando 
	 * um modelo de acompanhamento do periodo de experiência.
	 */
	public ColaboradorQuestionario findByColaboradorAvaliacao(Long colaboradorId, Long avaliacaoId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.colaborador.id"), "projectionColaboradorId");
		p.add(Projections.property("cq.avaliacao.id"), "projectionAvaliacaoId");
		p.add(Projections.property("cq.turma.id"), "projectionTurmaId");
		p.add(Projections.property("cq.respondida"), "respondida");
		p.add(Projections.property("cq.observacao"), "observacao");
		
		criteria.add(Expression.eq("cq.colaborador.id", colaboradorId));
	    criteria.add(Expression.eq("cq.avaliacao.id", avaliacaoId));
	    criteria.add(Expression.isNull("cq.avaliacaoDesempenho.id"));

		criteria.setProjection(Projections.distinct(p));
		
	    criteria.addOrder(Order.desc("cq.id"));
	    criteria.setMaxResults(1);
	    
	    criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (ColaboradorQuestionario) criteria.uniqueResult();
	}

	public ColaboradorQuestionario findByColaboradorAvaliacaoCurso(Long colaboradorId, Long avaliacaoCursoId, Long turmaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "cq");
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.colaborador.id"), "projectionColaboradorId");
		p.add(Projections.property("cq.avaliacaoCurso.id"), "projectionAvaliacaoCursoId");
		p.add(Projections.property("cq.turma.id"), "projectionTurmaId");
		p.add(Projections.property("cq.respondida"), "respondida");
		p.add(Projections.property("cq.observacao"), "observacao");
		
		criteria.add(Expression.eq("cq.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("cq.avaliacaoCurso.id", avaliacaoCursoId));
		criteria.add(Expression.eq("cq.turma.id", turmaId));
		
		criteria.setProjection(Projections.distinct(p));
		
		criteria.addOrder(Order.desc("cq.id"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (ColaboradorQuestionario) criteria.uniqueResult();
	}
	
	public Collection<ColaboradorQuestionario> findQuestionarioByTurmaLiberadaPorUsuario(Long usuarioId) 
	{
		StringBuilder hql = new StringBuilder();
		
		hql.append("select distinct new ColaboradorQuestionario(c, t, q.id, q.titulo, cs.nome) ");
		hql.append("from ColaboradorTurma ct "); 
		hql.append("inner join ct.turma t ");
		hql.append("inner join t.curso cs ");
		hql.append("inner join t.turmaAvaliacaoTurmas tat "); 
		hql.append("inner join tat.avaliacaoTurma at "); 
		hql.append("inner join at.questionario q ");
		hql.append("inner join ct.colaborador c "); 
		hql.append("inner join ct.colaboradorPresencas cp "); 
		hql.append("left join c.colaboradorQuestionarios cq with cq.turma.id=t.id and cq.questionario.id=q.id and c.usuario.id = :usuarioId ");
		hql.append("where c.usuario.id = :usuarioId ");
		hql.append("and tat.liberada = true "); 
		hql.append("and (cq.respondida is null or cq.respondida=false) ");
		hql.append("and cp.presenca = true "); 
		hql.append("order by cs.nome, q.titulo ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("usuarioId", usuarioId);

		return query.list();
	}

	public void removeByCandidato(Long candidatoId) 
	{
		String queryHQL = "delete from ColaboradorResposta cr where cr.colaboradorQuestionario.id in (select cq.id from ColaboradorQuestionario cq where cq.candidato.id = :candidatoId)";
		getSession().createQuery(queryHQL).setLong("candidatoId",candidatoId).executeUpdate();
	
		queryHQL = "delete from ColaboradorQuestionario cq where cq.candidato.id = :candidatoId";
		getSession().createQuery(queryHQL).setLong("candidatoId",candidatoId).executeUpdate();
	}

	public void deleteRespostaAvaliacaoDesempenho(Long colaboradorQuestionarioId) 
	{
		String queryHQL = "delete from ColaboradorResposta ce where ce.colaboradorQuestionario.id = :colaboradorQuestionarioId";
		getSession().createQuery(queryHQL).setLong("colaboradorQuestionarioId",colaboradorQuestionarioId).executeUpdate();

		queryHQL = "delete from ConfiguracaoNivelCompetencia cnc where cnc.configuracaoNivelCompetenciaColaborador.id in (select id from ConfiguracaoNivelCompetenciaColaborador cncc where cncc.colaboradorQuestionario.id = :colaboradorQuestionarioId)";
		getSession().createQuery(queryHQL).setLong("colaboradorQuestionarioId",colaboradorQuestionarioId).executeUpdate();

		String queryUpdateHQL = "update ColaboradorQuestionario cq set cq.respondida = false, cq.respondidaEm = null, cq.performance = null, cq.performanceNivelCompetencia = null, cq.observacao = null, cq.configuracaoNivelCompetenciaColaborador.id = null  where cq.id = :colaboradorQuestionarioId";
		getSession().createQuery(queryUpdateHQL).setLong("colaboradorQuestionarioId",colaboradorQuestionarioId).executeUpdate();

		queryHQL = "delete from ConfiguracaoNivelCompetenciaColaborador cncc where cncc.colaboradorQuestionario.id = :colaboradorQuestionarioId";
		getSession().createQuery(queryHQL).setLong("colaboradorQuestionarioId",colaboradorQuestionarioId).executeUpdate();
	}

	public Collection<ColaboradorQuestionario> findForRankingPerformanceAvaliacaoCurso(Long[] cursosIds, Long[] turmasIds, Long[] avaliacaoCursosIds) 
	{
		StringBuilder hql = new StringBuilder();
		
		hql.append("select new ColaboradorQuestionario(c.id, c.nome, t.id, t.descricao, co.id, co.nome, ac.id, ac.titulo, ac.tipo, (case when ac.tipo = 'a' then cq.performance when ac.tipo = 'n' then (aac.valor / 10) when ac.tipo = 'p' then (aac.valor / 100) end)) "); 
		hql.append("from Curso c ");
		hql.append("inner join c.avaliacaoCursos ac ");
		hql.append("inner join c.turmas t "); 
		hql.append("inner join t.colaboradorTurmas ct ");
		hql.append("inner join ct.colaborador co ");
		hql.append("left join ct.aproveitamentoAvaliacaoCursos aac with aac.avaliacaoCurso.id = ac.id ");
		hql.append("left join co.colaboradorQuestionarios cq with cq.turma.id = t.id and cq.avaliacaoCurso.id = ac.id ");
		hql.append("where (cq.id is not null or aac.id is not null)  ");
		
		if (cursosIds != null && cursosIds.length > 0)
			hql.append("and c.id in (:cursosIds) ");
		
		if (turmasIds != null && turmasIds.length > 0)
			hql.append("and t.id in (:turmasIds) ");
		
		if (avaliacaoCursosIds != null && avaliacaoCursosIds.length > 0)
			hql.append("and ac.id in (:avaliacaoCursosIds) ");
		
		hql.append("order by c.nome, ac.titulo, (case when ac.tipo = 'a' then cq.performance when ac.tipo = 'n' then (aac.valor / 10) when ac.tipo = 'p' then (aac.valor / 100) end) desc, co.nome, t.descricao ");

		Query query = getSession().createQuery(hql.toString());
		
		if (cursosIds != null && cursosIds.length > 0)
			query.setParameterList("cursosIds", cursosIds, Hibernate.LONG);
		
		if (turmasIds != null && turmasIds.length > 0)
			query.setParameterList("turmasIds", turmasIds, Hibernate.LONG);
		
		if (avaliacaoCursosIds != null && avaliacaoCursosIds.length > 0)
			query.setParameterList("avaliacaoCursosIds", avaliacaoCursosIds, Hibernate.LONG);

		return query.list();
	}

	public void removeBySolicitacaoId(Long solicitacaoId) 
	{
			String hql = "delete ColaboradorQuestionario where solicitacao.id = :solicitacaoId";

			Query query = getSession().createQuery(hql);

			query.setLong("solicitacaoId", solicitacaoId);
			query.executeUpdate();
	}

	public Collection<ColaboradorQuestionario> findAutoAvaliacao(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		
		criteria.createCriteria("cq.avaliacao", "av");
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("cq.id"), "id");
		p.add(Projections.property("cq.respondidaEm"), "respondidaEm");
		p.add(Projections.property("cq.avaliacaoDesempenho.id"), "avaliacaoDesempenhoId");
		p.add(Projections.property("av.titulo"), "projectionAvaliacaoTitulo");
		p.add(Projections.property("av.tipoModeloAvaliacao"), "projectionAvaliacaoTipoModelo");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cq.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("cq.avaliador.id", colaboradorId));
		criteria.add(Expression.in("av.tipoModeloAvaliacao", new Character[] {TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, TipoModeloAvaliacao.DESEMPENHO}));
		
		criteria.addOrder(Order.desc("cq.respondidaEm"));
		criteria.addOrder(Order.asc("av.titulo"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorQuestionario.class));
		
		return criteria.list();
	}

	public Collection<ColaboradorQuestionario> findByAvaliacaoComQtdDesempenhoEPeriodoExperiencia(Long avaliacaoId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorQuestionario.class, "cq");
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.groupProperty("cq.avaliacao.id"), "projectionAvaliacaoId");
		p.add(Projections.sqlProjection("sum(case when avaliacaodesempenho_id is null then 1 else 0 end) as qtdPeriodoExperiencia",
				new String []  {"qtdPeriodoExperiencia"}, 
        		new Type[] {Hibernate.INTEGER}), "qtdPeriodoExperiencia");
		p.add(Projections.sqlProjection("sum(case when avaliacaodesempenho_id is not null then 1 else 0 end) as qtdAvaliacaoDesempenho",
				new String []  {"qtdAvaliacaoDesempenho"}, 
				new Type[] {Hibernate.INTEGER}), "qtdAvaliacaoDesempenho");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cq.avaliacao.id", avaliacaoId));
		
		criteria.addOrder(Order.asc("cq.avaliacao.id"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorQuestionario.class));
		
		return criteria.list();
	}
}