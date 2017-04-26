package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
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
import org.springframework.dao.DataAccessResourceFailureException;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.FiltroAgrupamentoCursoColaborador;
import com.fortes.rh.model.dicionario.FiltroSituacaoCurso;
import com.fortes.rh.model.dicionario.MotivoReprovacao;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusAprovacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.StatusTAula;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.ws.TAula;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
@SuppressWarnings({"unchecked","rawtypes"})
public class ColaboradorTurmaDaoHibernate extends GenericDaoHibernate<ColaboradorTurma> implements ColaboradorTurmaDao
{
	//pega apenas os colaboradores que não estao em nenhuma turma
	public Collection<ColaboradorTurma> findColaboradoresByCursoTurmaIsNull(Long cursoId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.colaborador", "col", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.property("col.id"), "colaboradorId");
		p.add(Projections.property("col.nome"), "colaboradorNome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("ct.curso.id", cursoId));
		criteria.add(Expression.isNull("ct.turma"));
		criteria.add(Expression.eq("ct.origemDnt" , true));

		criteria.addOrder(Order.asc("col.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));

		return criteria.list();
	}

	public Collection<ColaboradorTurma> findByColaboradorAndTurma(int page, int pagingSize, Long[] colaboradoresIds, Long cursoId, Colaborador colaborador)
	{
		if(colaboradoresIds == null || colaboradoresIds.length == 0)
			return new ArrayList<ColaboradorTurma>();

		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.colaborador", "col", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("ct.prioridadeTreinamento", "pt", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.property("col.id"), "colaboradorId");
		p.add(Projections.property("pt.id"), "prioridadeTreinamentoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("ct.curso.id", cursoId));

		if(colaboradoresIds != null && colaboradoresIds.length > 0)
			criteria.add(Expression.in("col.id", colaboradoresIds));

		if (colaborador != null)
		{
			if(colaborador.getNome() != null && !colaborador.getNome().equals(""))
				criteria.add(Expression.like("col.nome", "%"+ colaborador.getNome() +"%").ignoreCase() );

			if(colaborador.getMatricula() != null && !colaborador.getMatricula().equals(""))
				criteria.add(Expression.like("col.matricula", "%"+ colaborador.getMatricula() +"%").ignoreCase() );
		}

		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);

		criteria.addOrder(Order.asc("col.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));

		return criteria.list();

	}

	public void updateTurmaEPrioridade(Long colaboradorTurnaId, Long turmaId, Long prioridadeId)
	{
		String queryHQL = "update ColaboradorTurma ct set ct.turma.id = "+ turmaId +" , ct.prioridadeTreinamento.id = "+ prioridadeId +" where ct.id = :colaboradorTurnaId";

		Session newSession = getSession();
		newSession.createQuery(queryHQL).setLong("colaboradorTurnaId", colaboradorTurnaId).executeUpdate();
	}

	public Collection<ColaboradorTurma> findByTurmaCurso(Long cursoId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.colaborador", "col", Criteria.LEFT_JOIN);
		criteria.createCriteria("ct.turma", "t", Criteria.LEFT_JOIN);
		criteria.createCriteria("t.curso", "c", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("col.id"), "colaboradorId");
		p.add(Projections.property("col.nome"), "colaboradorNome");
		p.add(Projections.property("t.id"), "turmaId");
		p.add(Projections.property("t.descricao"), "turmaDescricao");

		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", cursoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));

		return criteria.list();
	}

	public Collection<ColaboradorTurma> findByDNTColaboradores(DNT dnt, Collection<Colaborador> colaboradors)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria = criteria.createCriteria("ct.colaborador", "c", CriteriaSpecification.LEFT_JOIN);
		criteria = criteria.createCriteria("ct.curso", "cu", CriteriaSpecification.LEFT_JOIN);
		criteria = criteria.createCriteria("ct.turma", "tu", CriteriaSpecification.LEFT_JOIN);
		criteria = criteria.createCriteria("ct.prioridadeTreinamento", "pt", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		p.add(Projections.property("c.nomeComercial"), "colaboradorNomeComercial");

		p.add(Projections.property("cu.id"), "cursoId");
		p.add(Projections.property("cu.nome"), "cursoNome");

		p.add(Projections.property("tu.id"), "turmaId");
		p.add(Projections.property("tu.descricao"), "turmaDescricao");

		p.add(Projections.property("pt.id"), "prioridadeTreinamentoId");
		p.add(Projections.property("pt.descricao"), "prioridadeTreinamentoDescricao");

		criteria.setProjection(p);

		criteria.add(Expression.eq("ct.dnt", dnt));
		criteria.add(Expression.in("ct.colaborador", colaboradors));

		criteria.addOrder(Order.asc("c.nomeComercial"));
		criteria.addOrder(Order.asc("cu.nome"));
		criteria.addOrder(Order.asc("tu.descricao"));
		criteria.addOrder(Order.asc("pt.descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));

		return criteria.list();
	}

	public Empresa findEmpresaDoColaborador(ColaboradorTurma colaboradorTurma)
	{
		Empresa empresa = new Empresa();

		Query query = getSession().createQuery("select emp.id,emp.nome from ColaboradorTurma as ct " +
				"left join ct.colaborador as colab left join colab.empresa as emp where ct.id = :id");
		query.setLong("id", colaboradorTurma.getId());
		query.setMaxResults(1);
		Collection result = query.list();

		if (!result.isEmpty())
		{
			Object[] array = (Object[]) result.toArray()[0];
			empresa.setId((Long) array[0]);
			empresa.setNome((String) array[1]);
		}

		return empresa;
	}

	public Collection<Long> findIdEstabelecimentosByTurma(Long turmaId, Long empresaId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct e.id ");
		hql.append("from ColaboradorTurma as ct ");
		hql.append("left join ct.colaborador as co ");
		hql.append("left join co.empresa as emp ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.estabelecimento as e ");
		hql.append("left join ct.turma as t ");
		hql.append("left join t.curso as c ");
		hql.append("left join c.empresasParticipantes as ep ");
		hql.append("where ");
		hql.append("	t.id = :turmaId ");
		
		if(empresaId != null)
			hql.append("	and (c.empresa.id = :empresaId or ep.id = :empresaId)");
		
		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.status = :status ");
		hql.append("	) ");
		
		Query query = getSession().createQuery(hql.toString());
		
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("turmaId", turmaId);
		
		if(empresaId != null)
			query.setLong("empresaId", empresaId);

		return query.list();
	}

	public Collection<ColaboradorTurma> findByTurma(Long turmaId, String colaboradorNome, Long empresaId, Long[] estabelecimentoIds, Long[] cargoIds, boolean exibirSituacaoAtualColaborador, Integer page, Integer pagingSize, Boolean aprovado)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id, ct.curso.id, pt.id, t.id, co.id, co.nome, co.nomeComercial, co.matricula, co.pessoal.cpf, ao.id, ao.nome, ct.aprovado, e, fs.nome, ");
		hql.append("  c.nome, emp.id, emp.nome, emp.razaoSocial, emp.cnpj, (select count(respondida) from ColaboradorQuestionario where colaborador.id = co.id and turma.id = t.id and respondida = true),  ");
		hql.append(" ( select case when count(*) > 0 then true else false end from ParticipanteCursoLnt pcl inner join pcl.cursoLnt cl inner join cl.lnt l where pcl.colaborador.id= co.id and cl.curso.id= t.curso.id and l.dataFinalizada is not null )  ");
		hql.append(") from ColaboradorTurma as ct ");
		hql.append("left join ct.colaborador as co ");
		hql.append("left join co.empresa as emp ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.estabelecimento as e ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as c ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join ct.turma as t ");
		hql.append("left join ct.prioridadeTreinamento as pt ");
		hql.append("where ");
		hql.append("	t.id = :turmaId ");
		
		if(aprovado != null)
			hql.append("	and ct.aprovado = :aprovado ");
		
		if(empresaId != null)
			hql.append("	and emp.id = :empresaId ");
		
		if(StringUtils.isNotBlank(colaboradorNome))
			hql.append("	and lower(co.nome) like :nome ");

		if(LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			hql.append("	and e.id in (:estabelecimentoIds) ");

		if(LongUtil.arrayIsNotEmpty(cargoIds))
			hql.append("	and c.id in (:cargoIds) ");
		
		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.status = :status ");
		
		if (!exibirSituacaoAtualColaborador)
			hql.append("		and hc2.data <= t.dataPrevIni ");
		
		hql.append("	) ");
		
		hql.append(" order by co.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("turmaId", turmaId);
		
		if(page != null && pagingSize!=null){
			query.setFirstResult((page - 1) * pagingSize);
			query.setMaxResults(pagingSize);
		}
		
		if(aprovado != null)
			query.setBoolean("aprovado", aprovado);
		
		if(empresaId != null)
			query.setLong("empresaId", empresaId);
		
		if(StringUtils.isNotBlank(colaboradorNome))
			query.setString("nome", "%" + colaboradorNome.toLowerCase() + "%");
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);

		if(LongUtil.arrayIsNotEmpty(cargoIds))
			query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);
		
		Collection<ColaboradorTurma> colaboradorTurmas = query.list();

		return colaboradorTurmas;
	}
	
	public Collection<ColaboradorTurma> findAprovadosReprovados(Long[] colaboradorTurmaIds) 
	{
		StringBuilder sql = new StringBuilder();		
		
		sql.append("select ");
		sql.append("  ct.id as colaboradorturma, ");
		sql.append("  cp.qtdpresenca, ");
		sql.append("  dt.totaldias, ");
		sql.append("  c.percentualMinimoFrequencia as percentualMinimoFrequencia, ");
		sql.append("  ca.qtdavaliacoescurso, ");
		sql.append("  rct.qtdavaliacoesaprovadaspornota, ");
		sql.append("  rct.nota ");
		sql.append("from Colaboradorturma ct ");
		sql.append("  left join turma t on t.id=ct.turma_id ");
		sql.append("  left join curso c on c.id=t.curso_id ");
		sql.append("  left join ( select cursos_id, count(avaliacaocursos_id) qtdavaliacoescurso ");
		sql.append("              from curso_avaliacaocurso ");
		sql.append("              group by cursos_id ");
		sql.append("              order by cursos_id )as ca ");
		sql.append("              on ca.cursos_id = c.id ");
		sql.append("  left join ( select turma_id, count(dia) totaldias ");
		sql.append("              from diaturma ");
		sql.append("              group by turma_id ");
		sql.append("              order by turma_id ) as dt ");
		sql.append("              on dt.turma_id = t.id ");
		sql.append("  left join ( select colaboradorturma_id, count(id) qtdpresenca ");
		sql.append("              from colaboradorpresenca ");
		sql.append("              where presenca=true ");
		sql.append("              group by colaboradorturma_id ");
		sql.append("              order by colaboradorturma_id  )as cp ");
		sql.append("              on cp.colaboradorturma_id = ct.id ");
		sql.append("  left join ( select aac.colaboradorturma_id, count(aac.colaboradorturma_id) as qtdavaliacoescurso, ");
		sql.append("              sum(  cast(((aac.valor >= ac.minimoaprovacao) or ac.minimoaprovacao is null) as int)  ) as qtdavaliacoesaprovadaspornota, sum(aac.valor) as nota ");
		sql.append("              from aproveitamentoavaliacaocurso aac ");
		sql.append("                 left join avaliacaocurso ac ");
		sql.append("                 on ac.id = aac.avaliacaocurso_id ");
		sql.append("                 group by aac.colaboradorturma_id ");
		sql.append("                 order by aac.colaboradorturma_id ) as rct ");
		sql.append("              on rct.colaboradorturma_id = ct.id ");
		
		if(colaboradorTurmaIds != null)
			sql.append("where ct.id in (:colaboradorTurmaIds) ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		
		if(colaboradorTurmaIds != null)
			query.setParameterList("colaboradorTurmaIds", colaboradorTurmaIds);
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		
		List resultado = query.list();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			ColaboradorTurma ct = new ColaboradorTurma();
			
			Object[] res = it.next();
			
			ct.setId(((BigInteger)res[0]).longValue());
			if(res[1] != null)
				ct.setQtdPresenca(((BigInteger)res[1]).intValue());
			if(res[2] != null)
				ct.setTotalDias(((BigInteger)res[2]).intValue());
			
			ct.setCurso(new Curso());
			if(res[3] != null)
				ct.getCurso().setPercentualMinimoFrequencia(converteParaDouble(res[3]));
			if(res[4] != null)
				ct.setQtdAvaliacoesCurso(((BigInteger)res[4]).intValue());
			if(res[5] != null)
				ct.setQtdAvaliacoesAprovadasPorNota(((BigInteger)res[5]).intValue());
			
			colaboradorTurmas.add(ct);
		}
		
		return colaboradorTurmas;
	}


	public Collection<ColaboradorTurma> filtroRelatorioMatriz(HashMap parametros)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id, pt.numero, pt.sigla, co.id, co.nomeComercial, t.id, c.cargaHoraria, t.custo, c.id, c.nome ) ");
		hql.append("from ColaboradorTurma as ct ");
		hql.append("left join ct.colaborador as co ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join ct.turma as t ");
		hql.append("left join ct.curso as c ");
		hql.append("left join ct.prioridadeTreinamento as pt ");
		hql.append("left join ct.dnt as dnt ");
		hql.append("where ");
		hql.append("	dnt.data >= :dntData ");
		hql.append("	and ct.origemDnt = :origemDnt ");
		hql.append("	and ao.id in (:areasId) ");
		hql.append("	and es.id in (:estabelecimentosId) ");
		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("	) ");
		hql.append("order by co.nome,c.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setDate("dntData", (Date) parametros.get("data"));
		query.setBoolean("origemDnt", true);
		query.setParameterList("areasId", (Collection<Long>) parametros.get("areas"), Hibernate.LONG);
		query.setParameterList("estabelecimentosId", (Collection<Long>) parametros.get("estabelecimentos"), Hibernate.LONG);

		Collection<ColaboradorTurma> colaboradorTurmas = query.list();

		return colaboradorTurmas;
	}

	public Collection<ColaboradorTurma> filtroRelatorioPlanoTrei(HashMap parametros)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id, co.id, co.nomeComercial, es.nome, ao.nome, t, c.id, c.nome, c.cargaHoraria ) ");
		hql.append("from ColaboradorTurma as ct ");
		hql.append("left join ct.colaborador as co ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join ct.turma as t ");
		hql.append("left join ct.curso as c ");
		hql.append("left join ct.dnt as dnt ");
		hql.append("where ");
		hql.append("	dnt.id = :dntId");

		if (parametros.get("colaboradorId") != null)
			hql.append("	and co.id = :colaboradorId ");
		else
			{
				hql.append("	and ao.id in (:areasId) ");
				hql.append("	and es.id in (:estabelecimentosId) ");
			}

		if (parametros.get("semPlano") != null && (Boolean)parametros.get("semPlano"))
			hql.append("	and t.id is null ");
		else
			hql.append("	and t.id is not null ");

		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("	) ");

		hql.append("order by ao.nome, co.nome, t.descricao ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("dntId", (Long) parametros.get("dntId"));

		if (parametros.get("colaboradorId") != null)
			query.setLong("colaboradorId", (Long) parametros.get("colaboradorId"));
		else
			{
				query.setParameterList("areasId", (Collection<Long>) parametros.get("areas"), Hibernate.LONG);
				query.setParameterList("estabelecimentosId", (Collection<Long>) parametros.get("estabelecimentos"), Hibernate.LONG);
			}

		Collection<ColaboradorTurma> colaboradorTurmas = query.list();

		return colaboradorTurmas;
	}

	public Collection<ColaboradorTurma> findRelatorioSemIndicacaoDeTreinamento(Long empresaId, Long[] areas, Long[] estabelecimentos, Date data)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id, co.id, co.nome, c.nome, ao.nome, es.nome, t.descricao, t.dataPrevIni, t.dataPrevFim) ");
		hql.append("from ColaboradorTurma as ct ");
		hql.append("right join ct.colaborador as co ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join ct.turma as t ");
		hql.append("left join t.curso as c ");
		hql.append("where co.desligado = false and co.empresa.id = :empresaId ");
		
		if (areas.length > 0)
			hql.append("and ao.id in (:areasId) ");
		if (estabelecimentos.length > 0)
			hql.append("and es.id in (:estabelecimentosId) ");
		
		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.status = :status ");
		hql.append("	) ");
		hql.append("and ( co.id not in ( ");
		hql.append("		select ct2.colaborador.id ");
		hql.append("		from ColaboradorTurma ct2 join ct2.turma t2 ");
		hql.append("		where t2.dataPrevFim >= :data and t2.dataPrevFim <= :hoje) ");
		hql.append("or ct.id = null) ");
		hql.append("and co.dataAdmissao <= :data ");
		hql.append("order by es.nome, ao.nome, co.nome asc, t.dataPrevFim desc ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if (areas.length > 0)
			query.setParameterList("areasId", areas, Hibernate.LONG);
		
		if (estabelecimentos.length > 0)
			query.setParameterList("estabelecimentosId", estabelecimentos, Hibernate.LONG);
		
		query.setDate("data", data);
		query.setLong("empresaId", empresaId);

		return query.list();
	}

	public void updateColaboradorTurmaSetPrioridade(long colaboradorTurmaId, long prioridadeId)
	{
		String queryHQL = "update ColaboradorTurma ct set ct.prioridadeTreinamento.id = :prioridadeId where ct.id = :colaboradorTurmaId";

		Session session = getSession();
		Query query = session.createQuery(queryHQL);
		query.setLong("colaboradorTurmaId", colaboradorTurmaId);
		query.setLong("prioridadeId", prioridadeId);

		query.executeUpdate();
	}

	public void updateColaboradorTurmaSetAprovacao(Long colaboradorTurmaId, boolean aprovacao) throws Exception
	{
		String queryHQL = "update ColaboradorTurma ct set ct.aprovado = :aprovado where ct.id = :colaboradorTurmaId";

		Session session = getSession();
		Query query = session.createQuery(queryHQL);
		query.setLong("colaboradorTurmaId", colaboradorTurmaId);
		query.setBoolean("aprovado", aprovacao);

		query.executeUpdate();
	}

	public List findCustoRateado()
	{
		String hql = " select t.id,(t.custo / count(ct.id)) from ColaboradorTurma ct inner join ct.turma t group by t.id,t.custo";

		Query query = getSession().createQuery(hql);

		return query.list();
	}

	public Integer getCount(Long turmaId, Long empresaId, String colaboradorNome, Long[] estabelecimentoIds, Long[] cargoIds, Boolean aprovado)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select count(ct.id) from ColaboradorTurma ct " );
		hql.append("left join ct.colaborador as co ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.faixaSalarial as fc ");
		hql.append("left join fc.cargo as c ");
		hql.append("left join ct.turma as t ");
		hql.append("where ");
		hql.append("	t.id = :turmaId ");
		
		if(aprovado != null)
			hql.append("	and ct.aprovado = :aprovado ");
		
		if(empresaId != null)
			hql.append("	and co.empresa.id = :empresaId ");
		
		if(StringUtils.isNotBlank(colaboradorNome))
			hql.append("	and lower(co.nome) like :nome ");

		if(LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			hql.append("	and hc.estabelecimento.id in (:estabelecimentoIds) ");

		if(LongUtil.arrayIsNotEmpty(cargoIds))
			hql.append("	and c.id in (:cargoIds) ");
			
		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.status = :status ");
		hql.append("	) ");
		
		hql.append(" group by ct.id ");
		
		Query query = getSession().createQuery(hql.toString());
		
		query.setLong("turmaId", turmaId);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if(aprovado != null)
			query.setBoolean("aprovado", aprovado);
		
		if(empresaId != null)
			query.setLong("empresaId", empresaId);
		
		if(StringUtils.isNotBlank(colaboradorNome))
			query.setString("nome", "%" + colaboradorNome.toLowerCase() + "%");
		
		if(LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		
		if(LongUtil.arrayIsNotEmpty(cargoIds))
			query.setParameterList("cargoIds", cargoIds, Hibernate.LONG);

		
		return (Integer)query.list().size();
	}

	public Collection<ColaboradorTurma> findRelatorioSemTreinamentoAprovadosOrReprovados(Long[] empresasIds, Long[] cursosIds, Long[] areaIds, Long[] estabelecimentoIds, Date data, String situacaoColaborador, Boolean aprovado){
		StringBuilder sql = new StringBuilder();
		sql.append(" select c.id as colaboradorId, c.nome as colaboradorNome, c.matricula as matricula, ao.id as areaId, monta_familia_area(ao.id) as areaNome, ");
		sql.append(" es.nome as estabelecimentoNome, emp.nome as empresaNome, max(colabTurmaRealizadaPeriodo.dataPrevFim) as dataPrevFim, ");
		
		sql.append(" colabTurmaRealizadaPeriodo.cursoId, colabTurmaRealizadaPeriodo.cursoNome, colabTurmaRealizadaPeriodo.aprovado as aprovado " );
		sql.append(" from colaborador c ");
		sql.append(" inner join ( ");
		sql.append(" 	select distinct ct.colaborador_id, t.dataPrevFim, c.id as cursoId, c.nome as cursoNome, ct.aprovado as aprovado ");
		sql.append(" 	from colaboradorturma as ct  ");
		sql.append(" 	inner join turma as t on t.id = ct.turma_id and t.realizada ");
		sql.append(" 	inner join curso c on c.id = t.curso_id ");
		sql.append(" 	where c.id in (:cursosIds) ");
		if(aprovado != null)
			sql.append(" and ct.aprovado = :aprovado ");
		sql.append(" 	and t.dataPrevFim = ( select max(t2.dataPrevFim) from colaboradorturma as ct2 inner join turma as t2 on t2.id = ct2.turma_id and t2.realizada "); 
		sql.append(" 	inner join curso c2 on c2.id = t2.curso_id  where ct2.colaborador_id = ct.colaborador_id and c2.id = c.id ) ");
		sql.append(" 	) as colabTurmaRealizadaPeriodo on colabTurmaRealizadaPeriodo.colaborador_id = c.id  ");
		sql.append(" left join historicocolaborador as hc on hc.colaborador_id = c.id and hc.data=(select max(hc2.data) from HistoricoColaborador hc2  ");
		sql.append(" 									where hc2.colaborador_id=c.id and hc2.data <= current_date and hc2.status = :status  ");
		sql.append(" 									)  ");
		sql.append(" left join areaOrganizacional as ao on ao.id = hc.areaorganizacional_id  ");
		sql.append(" left join estabelecimento as es on es.id = hc.estabelecimento_id  ");
		sql.append(" left join empresa as emp on emp.id = c.empresa_id  ");
		sql.append(" where (current_date - colabTurmaRealizadaPeriodo.dataPrevFim) >= (current_date - :data) " );
		
		if (situacaoColaborador != null)
		{
			if (situacaoColaborador.equalsIgnoreCase(SituacaoColaborador.ATIVO))
			{
				sql.append("	and (c.dataDesligamento is null ");
				if (data != null)
					sql.append("	or c.dataDesligamento > :data ");
				sql.append("	) ");
			}
			else if (situacaoColaborador.equalsIgnoreCase(SituacaoColaborador.DESLIGADO))
			{
				sql.append("	and (c.dataDesligamento is not null ");
				if (data != null)
					sql.append("	and c.dataDesligamento <= :data ");
				sql.append("	) ");
			}
		}
		
		sql.append(	" and c.empresa_id in(:empresasIds)");

		if (LongUtil.arrayIsNotEmpty(areaIds))
			sql.append(" and ao.id in (:areasId) ");

		if (LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			sql.append(" and es.id in (:estabelecimentosId) ");
		
		sql.append(" group by colabTurmaRealizadaPeriodo.cursoId, colabTurmaRealizadaPeriodo.cursoNome, emp.nome ,es.nome, ao.id, areaNome, c.id, c.nome, c.matricula, colabTurmaRealizadaPeriodo.aprovado ");
		sql.append(" order by colabTurmaRealizadaPeriodo.cursoNome, emp.nome, es.nome, areaNome, c.nome ");

		SQLQuery query = getSession().createSQLQuery(sql.toString());

		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		query.setDate("data", data);
		query.setParameterList("cursosIds", cursosIds, Hibernate.LONG);
		query.setParameterList("empresasIds", empresasIds);

		if (LongUtil.arrayIsNotEmpty(areaIds))
			query.setParameterList("areasId", areaIds, Hibernate.LONG);

		if (LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			query.setParameterList("estabelecimentosId", estabelecimentoIds, Hibernate.LONG);
		
		if(aprovado != null)
			query.setBoolean("aprovado", aprovado);

		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		List resultado = query.list();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			ColaboradorTurma ct = new ColaboradorTurma();
			
			Object[] res = it.next();
			
			if(res[0] != null)
				ct.setColaboradorId(((BigInteger)res[0]).longValue());
			if(res[1] != null)
				ct.setColaboradorNome(res[1].toString());
			if(res[2] != null)
				ct.setColaboradorMatricula(res[2].toString());
			if(res[3] != null)
				ct.setAreaOrganizacionalId(((BigInteger)res[3]).longValue());
			if(res[4] != null)
				ct.setAreaOrganizacionalNome(res[4].toString());
			if(res[5] != null) 
				ct.getColaborador().setEstabelecimentoNomeProjection(res[5].toString());
			if(res[6] != null)
				ct.setEmpresaNome(res[6].toString());
			if(res[7] != null)
				ct.setTurmaDataPrevFim((Date)res[7]);
			if(res[8] != null)
				ct.setCursoId(((BigInteger)res[8]).longValue());
			if(res[9] != null)
				ct.setCursoNome(res[9].toString());
			if(res[10] != null)
				ct.setAprovado(((boolean)res[10]));
			colaboradorTurmas.add(ct);
		}
		
		return colaboradorTurmas;
	}

	
	public Collection<ColaboradorTurma> findColabororesTurmaComTreinamento(Long[] empresasIds, Long[] areaIds, Long[] estabelecimentoIds, Long[] cursosIds)
	{
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("colab.id"), "colaboradorId");
		p.add(Projections.property("ct.curso.id"), "cursoId");

		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.turma", "t", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.colaborador", "colab", Criteria.INNER_JOIN);
		criteria.createCriteria("colab.empresa", "e", Criteria.INNER_JOIN);
		criteria.createCriteria("colab.historicoColaboradors", "hc", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.estabelecimento", "es", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.INNER_JOIN);
		
		if(LongUtil.arrayIsNotEmpty(empresasIds))
			criteria.add(Expression.in("e.id", empresasIds));
		
		if (LongUtil.arrayIsNotEmpty(areaIds))
			criteria.add(Expression.in("ao.id", areaIds));

		if (LongUtil.arrayIsNotEmpty(estabelecimentoIds))
			criteria.add(Expression.in("es.id", estabelecimentoIds));
		
		criteria.add(Expression.or(Expression.isNull("colab.dataDesligamento"), Expression.gt("colab.dataDesligamento", new Date())));
	
		if (LongUtil.arrayIsNotEmpty(cursosIds))
			criteria.add(Expression.in("ct.curso.id", cursosIds));
			
		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("ct.curso.id")).addOrder(Order.asc("colab.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));
		return criteria.list();
	}

	public Collection<ColaboradorTurma> findColaboradoresComCustoTreinamentos(Long colaboradorId, Date dataIni, Date dataFim, Boolean turmaRealizada)
	{
		StringBuilder hql = new StringBuilder();
	
		hql.append("select new ColaboradorTurma(colab.id, colab.nome, emp.nome, curso.nome, curso.cargaHoraria, turma.descricao, turma.dataPrevIni, turma.dataPrevFim, turma.realizada, ");
		hql.append(                            "(select (turma.custo / count(ct.turma.id)) from ColaboradorTurma ct where ct.turma.id = turma.id group by ct.turma.id)) " );
		hql.append("from ColaboradorTurma as colabTurma ");
		hql.append("left join colabTurma.turma as turma ");
		hql.append("left join colabTurma.colaborador as colab ");
		hql.append("left join colabTurma.curso as curso ");
		hql.append("left join curso.empresa as emp ");
		hql.append("where colab.id = (:colaboradorId) ");
		
		if (dataIni != null && dataFim != null)
			hql.append("and turma.dataPrevIni between :dataIni and :dataFim ");
		
		if (turmaRealizada != null)
			hql.append("and turma.realizada = :turmaRealizada ");
		
		hql.append("order by emp.nome, turma.dataPrevIni ");
		
		Query query = getSession().createQuery(hql.toString());
		
		query.setLong("colaboradorId", colaboradorId);
		
		if (dataIni != null && dataFim != null)
		{
			query.setDate("dataIni", dataIni);
			query.setDate("dataFim", dataFim);
		}
		
		if (turmaRealizada != null)
			query.setBoolean("turmaRealizada", turmaRealizada);
		
		return query.list();
	}

	public Collection<ColaboradorTurma> findRelatorioComTreinamento(Long empresaId, Long[] cursoIds, Long[] areaIds, Long[] estabelecimentoIds, Date dataIni, Date dataFim, String situacao, char aprovacaoFiltro){
		Criteria criteria = criteriaFindRelatorioComTreinamento();
		criteria.setProjection(Projections.distinct(projectionFindRelatorioComTreinamento()));
		
		criteria.add(Expression.eq("t.realizada", true));
		criteria.add(Expression.in("c.id", cursoIds));
		criteria.add(Subqueries.propertyEq("hc.data", maxDataHistoricoColaborador()));
		
		if (empresaId != null)
			criteria.add(Expression.eq("emp.id", empresaId));
		
		if(areaIds != null && areaIds.length > 0)
			criteria.add(Expression.in("ao.id", areaIds));
		
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
			criteria.add(Expression.in("es.id", estabelecimentoIds));
		
		if (situacao != null){
			if (situacao.equalsIgnoreCase(SituacaoColaborador.ATIVO)){
				if (dataFim != null)
					criteria.add(Expression.or(Expression.isNull("co.dataDesligamento"),Expression.le("co.dataDesligamento", dataFim)));
				else
					criteria.add(Expression.isNull("co.dataDesligamento"));
			} else if (situacao.equalsIgnoreCase(SituacaoColaborador.DESLIGADO)){
				criteria.add(Expression.isNotNull("co.dataDesligamento")); 
				if(dataFim != null)
					criteria.add(Expression.le("co.dataDesligamento", dataFim));
			}
		}
		
		if(aprovacaoFiltro == StatusAprovacao.APROVADO)
			criteria.add(Expression.eq("ct.aprovado", true));
		else if(aprovacaoFiltro == StatusAprovacao.REPROVADO)
			criteria.add(Expression.eq("ct.aprovado", false));
		
		if (dataIni != null && dataFim != null){
			criteria.add(Expression.le("t.dataPrevIni", dataFim));
			criteria.add(Expression.ge("t.dataPrevFim", dataIni));
		}
		
		criteria.addOrder(Order.asc("c.nome")).addOrder(Order.asc("emp.nome")).addOrder(Order.asc("es.nome")).addOrder(Order.asc("areaOrganizacionalNome")).addOrder(Order.asc("co.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));
	    return criteria.list();
	}

	private DetachedCriteria maxDataHistoricoColaborador() {
		DetachedCriteria maxHistorico = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2").setProjection(Projections.max("hc2.data")).add(Restrictions.eqProperty("hc2.colaborador.id", "co.id")).add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
		return maxHistorico;
	}

	private Criteria criteriaFindRelatorioComTreinamento() throws DataAccessResourceFailureException, IllegalStateException, HibernateException {
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.colaborador", "co", Criteria.INNER_JOIN);
		criteria.createCriteria("co.empresa", "emp", Criteria.INNER_JOIN);
		criteria.createCriteria("co.historicoColaboradors", "hc", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.estabelecimento", "es", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.curso", "c", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.turma", "t", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.INNER_JOIN);
		criteria.createCriteria("fs.cargo", "ca", Criteria.INNER_JOIN);
		return criteria;
	}

	private ProjectionList projectionFindRelatorioComTreinamento() {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "colaboradorId");
		p.add(Projections.property("co.nome"), "colaboradorNome");
		p.add(Projections.property("co.matricula"), "colaboradorMatricula");
		p.add(Projections.property("es.id"), "estabelecimentoId");
		p.add(Projections.property("es.nome"), "estabelecimentoNome");
		p.add(Projections.property("ao.id"), "areaOrganizacionalId");
		p.add(Projections.alias(Projections.sqlProjection("monta_familia_area(ao4_.id) as areaOrganizacionalNome", new String[] {"areaOrganizacionalNome"}, new Type[] {Hibernate.TEXT}), "areaOrganizacionalNome"));
		p.add(Projections.property("t.id"), "turmaId");
		p.add(Projections.property("t.descricao"), "turmaDescricao");
		p.add(Projections.property("t.dataPrevIni"), "projectionDataPrevIni");
		p.add(Projections.property("t.dataPrevFim"), "projectionDataPrevFim");
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.property("ct.aprovado"), "aprovado");
		p.add(Projections.property("c.id"), "cursoId");
		p.add(Projections.property("c.nome"), "cursoNome");
		p.add(Projections.property("c.cargaHoraria"), "cursoCargaHoraria");
		p.add(Projections.property("emp.nome"), "empresaNome");
		p.add(Projections.property("fs.nome"), "faixaSalarialNome");
		p.add(Projections.property("ca.nome"), "cargoNome");
		p.add(Projections.alias(Projections.sqlProjection("carga_horaria_efetiva(c6_.id, t7_.id, this_.id)::text as cargaHorariaEfetiva", new String[] {"cargaHorariaEfetiva"}, new Type[] {Hibernate.TEXT}), "cargaHorariaEfetiva"));
		return p;
	}
	
	public Collection<ColaboradorTurma> findAprovadosReprovados(Long empresaId, Certificacao certificacao, Long[] areaIds, Long[] estabelecimentoIds, Date dataIni, Date dataFim, String orderBy, boolean comHistColaboradorFuturo, String situacao)
	{
		StringBuilder sql = new StringBuilder();		
		sql.append("select distinct co.id as colaborador, co.nome as colaboradornome, co.matricula as colaboradormatricula, e.id as estabelecimentoId, e.nome as estabelecimento, a.id as area, monta_familia_area(a.id) as areaNome, ");
		sql.append("	emp.nome as empNome, fs.nome as faixaNome, cg.nome as cargoNome, t.id as turma, t.descricao as descricao, t.dataPrevIni as dataPrevIni, t.dataPrevFim as dataPrevFim, t.horario, t.instrutor, ");
		sql.append("	ct.id as colaboradorturma, ct.aprovado as colaboradorturmaAprovado, c.id as cursoId, c.nome as cursoNome, c.cargaHoraria, c.conteudoProgramatico, rct.nota ");
		sql.append("from Colaboradorturma ct  ");
		sql.append("left join colaborador co on co.id = ct.colaborador_id ");
		sql.append("left join empresa emp on emp.id = co.empresa_id ");
		sql.append("left join historicocolaborador hc on hc.colaborador_id = co.id ");
		sql.append("left join estabelecimento e on e.id = hc.estabelecimento_id ");
		sql.append("left join areaorganizacional a on a.id = hc.areaorganizacional_id ");
		sql.append("left join faixaSalarial fs on fs.id = hc.faixaSalarial_id ");
		sql.append("left join cargo cg on cg.id = fs.cargo_id ");
		sql.append("left join turma t on t.id=ct.turma_id ");
		sql.append("left join curso c on c.id=t.curso_id ");
		sql.append("left join certificacao_curso cc on cc.cursos_id=c.id ");
		
		sql.append("left join View_CursoNota as rct on rct.colaboradorturma_id = ct.id ");
		
		sql.append("where hc.data = ( ");
		sql.append("	select max(hc2.data) from historicocolaborador hc2 ");
		sql.append("	where hc2.colaborador_id = co.id ");
		if(!comHistColaboradorFuturo)
			sql.append("	and hc2.data <= :hoje ");
		sql.append("	and hc2.status <> :statusCancelado ) ");
		
		sql.append("	and t.realizada = true ");
		sql.append("	and (co.dataDesligamento is null ");
		if (dataFim != null)
			sql.append("	or co.dataDesligamento > :dataFim ");
		sql.append("	) ");
		sql.append("	and cc.certificacaos_id = :certificacaoId ");
		sql.append("	and co.empresa_id = :empresaId ");
			
		if (areaIds != null && areaIds.length > 0)
			sql.append("and a.id in (:areasId) ");

		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			sql.append("and e.id in (:estabelecimentosId) ");
		
		if (dataIni != null && dataFim != null)
			sql.append("and ((( t.dataPrevIni between :dataIni and :dataFim ) or ( t.dataPrevFim between :dataIni and :dataFim )) or ( t.dataPrevIni <= :dataIni and t.dataPrevFim >= :dataFim )) ");
			
		sql.append("	order by " + orderBy);

		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("empresaId", empresaId);
		query.setLong("certificacaoId", certificacao.getId());
		
		if (areaIds != null && areaIds.length > 0)
			query.setParameterList("areasId", areaIds, Hibernate.LONG);

		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentosId", estabelecimentoIds, Hibernate.LONG);
		
		if (dataIni != null && dataFim != null) {
			query.setDate("dataIni", dataIni);
			query.setDate("dataFim", dataFim);
		}

		if(!comHistColaboradorFuturo)
			query.setDate("hoje", new Date());
		
		query.setInteger("statusCancelado", StatusRetornoAC.CANCELADO);
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		
		List resultado = query.list();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			ColaboradorTurma ct = new ColaboradorTurma();
			
			Object[] res = it.next();
			ct.setColaboradorId(((BigInteger)res[0]).longValue());
			ct.setColaboradorNome((String)res[1]);
			ct.setColaboradorMatricula((String)res[2]);
			
			ct.getColaborador().setEstabelecimento(new Estabelecimento());
			ct.getColaborador().getEstabelecimento().setId(((BigInteger)res[3]).longValue());
			ct.getColaborador().getEstabelecimento().setNome((String)res[4]);
			
			ct.getColaborador().setAreaOrganizacional(new AreaOrganizacional());
			ct.getColaborador().getAreaOrganizacional().setId(((BigInteger)res[5]).longValue());
			ct.getColaborador().getAreaOrganizacional().setNome((String)res[6]);

			ct.getColaborador().setEmpresa(new Empresa());
			ct.getColaborador().getEmpresa().setNome((String)res[7]);
			
			ct.getColaborador().setFaixaSalarial(new FaixaSalarial());
			ct.getColaborador().getFaixaSalarial().setNome((String)res[8]);
			ct.getColaborador().getFaixaSalarial().setCargo(new Cargo());
			ct.getColaborador().getFaixaSalarial().getCargo().setNome((String)res[9]);

			if(res[10] != null)
			{
				ct.setTurma(new Turma());
				ct.getTurma().setId(((BigInteger)res[10]).longValue());
				ct.getTurma().setDescricao((String)res[11]);
				ct.getTurma().setDataPrevIni((Date)res[12]);
				ct.getTurma().setDataPrevFim((Date)res[13]);
				
				ct.getTurma().setHorario((String)res[14]);
				ct.getTurma().setInstrutor((String)res[15]);
			}
			
			ct.setId(((BigInteger)res[16]).longValue());
			ct.setAprovado((Boolean)res[17]);
			
			if(res[18] != null)
			{
				ct.setCurso(new Curso());
				ct.getCurso().setId(((BigInteger)res[18]).longValue());
				ct.getCurso().setNome((String)res[19]);
				
				if(res[20] != null)
					ct.getCurso().setCargaHoraria(((Integer)res[20]));
				ct.getCurso().setConteudoProgramatico(((String)res[21]));
			}
			
			if(res[22] != null)
			{
				ct.setNota((Double)res[22]);
				ct.setValorAvaliacao((Double)res[22]);				
			}
			
			colaboradorTurmas.add(ct);
		}
		
		return colaboradorTurmas;
	}

	public Collection<ColaboradorTurma> findByTurmaSemPresenca(Long turmaId, Long diaTurmaId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id, ct.aprovado, ct.colaborador.id, ct.turma.id, ct.curso.id) from ColaboradorTurma ct ");
		hql.append("where ct.turma.id = :turmaId ");
		hql.append("and ct.id not in (select cp.colaboradorTurma.id from ColaboradorPresenca cp where cp.diaTurma.id = :diaTurmaId)");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("turmaId", turmaId);
		query.setLong("diaTurmaId", diaTurmaId);

		return query.list();
	}
	
	public Collection<ColaboradorTurma> findByTurmaPresenteNoDiaTurmaId(Long turmaId, Long diaTurmaId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id, ct.aprovado, ct.colaborador.id, ct.turma.id, ct.curso.id) from ColaboradorTurma ct ");
		hql.append("where ct.turma.id = :turmaId ");
		hql.append("and ct.id in (select cp.colaboradorTurma.id from ColaboradorPresenca cp where cp.diaTurma.id = :diaTurmaId)");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("turmaId", turmaId);
		query.setLong("diaTurmaId", diaTurmaId);

		return query.list();
	}
	
	public Collection<ColaboradorTurma> findHistoricoTreinamentosByColaborador(Long empresaId, Date dataIni, Date dataFim, Long... colaboradorIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id, ct.aprovado, ac.valor, co.id, co.nome, c.id, c.nome, c.cargaHoraria, ca.nome, fs.id, fs.nome, t.id, t.descricao, t.dataPrevIni, t.dataPrevFim, t.instrutor) ");
		hql.append("from ColaboradorTurma as ct ");
		hql.append("left join ct.colaborador as co ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join ct.turma as t ");
		hql.append("left join t.curso as c ");
		hql.append("left join ct.aproveitamentoAvaliacaoCursos as ac ");
		hql.append("where co.empresa.id = :empresaId ");
		hql.append("and co.id in (:colaboradorIds) ");
		hql.append("and t.realizada = true ");

		if(dataIni != null)
			hql.append("and t.dataPrevIni >= :dataIni ");
		if (dataFim != null)
			hql.append("and t.dataPrevFim <= :dataFim ");

		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.status = :status ");
		hql.append("	) ");

		hql.append("order by co.nome, t.dataPrevFim desc ");
		Query query = getSession().createQuery(hql.toString());

		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("empresaId", empresaId);
		query.setParameterList("colaboradorIds", colaboradorIds);
		
		if(dataIni != null)
			query.setDate("dataIni", dataIni);
		if (dataFim != null)
			query.setDate("dataFim", dataFim);

		return query.list();
	}

	public Collection<Long> findColaboradoresSemAvaliacao(Long empresaId, Date dataIni, Date dataFim)
	{
		StringBuilder hql = new StringBuilder("select ct.id from ColaboradorTurma ct ");
		hql.append("join ct.curso c ");
		hql.append("left join c.avaliacaoCursos ac ");
		hql.append("join ct.turma t ");
		hql.append("where  t.dataPrevIni >= :dataIni and t.dataPrevFim <= :dataFim ");
		hql.append("and c.empresa.id = :empresaId ");
		hql.append("and ac = null ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setLong("empresaId", empresaId);

		return query.list();
	}

	public ColaboradorTurma findByColaboradorAndTurma(Long turmaId, Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria = criteria.createCriteria("ct.colaborador", "c", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("ct.curso", "cu", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("ct.turma", "tu", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("ct.prioridadeTreinamento", "pt", Criteria.LEFT_JOIN);
		criteria = criteria.createCriteria("ct.dnt", "d", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.property("ct.origemDnt"), "origemDnt");
		p.add(Projections.property("ct.aprovado"), "aprovado");

		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("cu.id"), "cursoId");
		p.add(Projections.property("tu.id"), "turmaId");
		p.add(Projections.property("pt.id"), "prioridadeTreinamentoId");
		p.add(Projections.property("d.id"), "dntId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("tu.id", turmaId));
		criteria.add(Expression.eq("c.id", colaboradorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));

		return (ColaboradorTurma) criteria.uniqueResult();
	}

	public Collection<ColaboradorTurma> findColaboradorByTurma(Long turmaId, Long avaliacaoCursoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id, co.id, co.nome, co.nomeComercial, co.matricula, cq.id) ");
		hql.append("from ColaboradorTurma as ct ");
		hql.append("left join ct.colaborador as co ");
		hql.append("left join co.colaboradorQuestionarios as cq with cq.turma.id = ct.turma.id and cq.avaliacaoCurso.id = :avaliacaoCursoId ");
		hql.append("where ct.turma.id = :turmaId ");
		hql.append("order by co.nome asc");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("turmaId", turmaId);
		query.setLong("avaliacaoCursoId", avaliacaoCursoId);

		Collection<ColaboradorTurma> colaboradorTurmas = query.list();

		return colaboradorTurmas;	
	}

	public HashMap<String, Integer> findAprovadosReprovados(Date dataIni, Date dataFim, Long[] empresaIds, Long[] areasIds, Long[] cursoIds, Long[] estabelecimentosIds) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct ct.aprovado, count(ct.id) as total ");
		sql.append("from Colaboradorturma ct ");
		sql.append("inner join historicocolaborador hc on hc.colaborador_id = ct.colaborador_id ");
		sql.append("left join turma t on t.id=ct.turma_id ");
		sql.append("left join curso c on c.id=t.curso_id ");
		sql.append("where t.dataPrevIni >= :dataIni and t.dataPrevFim <= :dataFim and t.realizada = :realizada and t.id is not null ");
		sql.append("and hc.data = (select max(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = hc.colaborador_id) ");
		
		if (LongUtil.arrayIsNotEmpty(empresaIds))
			sql.append("and c.empresa_id in (:empresaIds) ");
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			sql.append("and hc.areaorganizacional_id in (:areasIds) ");
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			sql.append("and c.id in (:cursoIds) ");
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentosIds)){
			sql.append("and hc.estabelecimento_id in (:estabelecimentosIds) ");
		}		
		sql.append(" group by ct.aprovado ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setBoolean("realizada", true);
		
		if (LongUtil.arrayIsNotEmpty(empresaIds))
			query.setParameterList("empresaIds", empresaIds);
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			query.setParameterList("areasIds", areasIds);
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			query.setParameterList("cursoIds", cursoIds);
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentosIds)){
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		}
		
		List resultado = query.list();
		HashMap<String, Integer> mapAprovadosAndReprovados = new HashMap<String, Integer>();
		mapAprovadosAndReprovados.put("qtdAprovados", 0);
		mapAprovadosAndReprovados.put("qtdReprovados", 0);
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			
			if((Boolean) res[0])
				mapAprovadosAndReprovados.put("qtdAprovados", ((BigInteger)res[1]).intValue());
			else
				mapAprovadosAndReprovados.put("qtdReprovados", ((BigInteger)res[1]).intValue());
		}
		return mapAprovadosAndReprovados;
	}
	
	private double converteParaDouble(Object valor) {
		if (valor instanceof BigDecimal)
			return ((BigDecimal) valor).doubleValue();
		else
			return (Double) valor;
	}

	public Collection<ColaboradorTurma> findColaboradoresComEmailByTurma(Long turmaId, boolean somentePresentes) 
	{
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria = criteria.createCriteria("ct.colaborador", "c", Criteria.LEFT_JOIN);
		
		if(somentePresentes)
			criteria = criteria.createCriteria("ct.colaboradorPresencas", "cp", Criteria.INNER_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("ct.id")), "id");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		p.add(Projections.property("c.contato.email"), "colaboradorEmail");

		criteria.setProjection(p);

		criteria.add(Expression.eq("ct.turma.id", turmaId));
		criteria.add(Expression.eq("c.desligado", false));
		
		if(somentePresentes)
			criteria.add(Expression.eq("cp.presenca", true));
		
		criteria.add(Expression.not(Expression.eq("c.contato.email", "")));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));

		return criteria.list();
	}

	public Collection<ColaboradorTurma> findColabTreinamentos(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] cursoIds, Long[] turmaIds, boolean considerarSomenteDiasPresente) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id, co.codigoAC, dt.dia) ");
		hql.append("from ColaboradorTurma as ct ");
		hql.append("left join ct.colaborador as co ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join ct.turma as t ");
		hql.append("left join t.diasTurma as dt ");
		hql.append("where t.curso.id in (:cursoIds) ");
		
		if(considerarSomenteDiasPresente)
			hql.append("and exists (select id from ColaboradorPresenca cp where cp.diaTurma.id = dt.id and cp.colaboradorTurma.id = ct.id ) ");
		
		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append("and hc.estabelecimento.id in (:estabelecimentosIds) ");
		
		if (areaIds != null && areaIds.length > 0)
			hql.append("and hc.areaOrganizacional.id in (:areasIds) ");

		if (turmaIds != null && turmaIds.length > 0)
			hql.append("and t.id in (:turmasIds) ");

		hql.append("and co.empresa.id = :empresaId ");
		hql.append("and co.naoIntegraAc = false ");
		hql.append("and co.codigoAC is not null ");
		hql.append("and co.codigoAC <> '' ");

		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("		and hc2.data <= :hoje ");
		hql.append("			and hc2.status = :status ");
		hql.append("	) ");
		
		hql.append("order by co.codigoAC, dt.dia ");

		Query query = getSession().createQuery(hql.toString());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("empresaId", empresaId);
		
		query.setDate("hoje", new Date());
		
		query.setParameterList("cursoIds", cursoIds, Hibernate.LONG);
		
		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentosIds", estabelecimentoIds, Hibernate.LONG);
		
		if (areaIds != null && areaIds.length > 0)
			query.setParameterList("areasIds", areaIds, Hibernate.LONG);
		
		if (turmaIds != null && turmaIds.length > 0)
			query.setParameterList("turmasIds", turmaIds, Hibernate.LONG);

		Collection<ColaboradorTurma> colaboradorTurmas = query.list();

		return colaboradorTurmas;	
	}
	
	public TAula[] findColaboradorTreinamentosParaTRU(String colaboradorCodigoAC, Long empresaId, String periodoIni, String periodoFim, boolean considerarPresenca) 
	{
		Date dataIni = DateUtil.montaDataComHora(periodoIni, "00:00");
		Date dataFim = DateUtil.montaDataComHora(periodoFim, "23:59");
		
		StringBuilder sql = new StringBuilder();
		sql.append("select ct.id as ctId, co.codigoAC as colabCodigoAC, dt.dia as diaTurma, dt.horaIni as horaIni, dt.horaFim as horaFim, cu.nome as cursoNome, t.descricao as turmaDescricao, cp.presenca as presenca ");
		sql.append("from ColaboradorTurma as ct ");
		sql.append("left join colaborador as co on co.id = ct.colaborador_id ");
		sql.append("left join turma as t on t.id = ct.turma_id ");
		sql.append("left join curso as cu on cu.id = t.curso_id ");
		sql.append("left join diaTurma as dt on dt.turma_id = t.id ");
		sql.append("left join colaboradorPresenca as cp on cp.colaboradorTurma_id = ct.id and cp.diaturma_id = dt.id ");
		
		sql.append("where (");
		sql.append("		( ");
		sql.append("			( 	cast(dt.dia || ' ' || coalesce(dt.horaIni, '00:00') as timestamp) > cast(:periodoIni as timestamp) ");		
		sql.append("          		and cast(dt.dia || ' ' || coalesce(dt.horaIni, '00:00') as timestamp) < cast(:periodoFim as timestamp) ");
		sql.append("	         ) ");
		sql.append("			or ( ");
		sql.append("				cast(dt.dia || ' ' || coalesce(dt.horaFim, '23:59') as timestamp) > cast(:periodoIni as timestamp) ");
		sql.append("		  		and cast(dt.dia || ' ' || coalesce(dt.horaFim, '23:59') as timestamp) < cast(:periodoFim as timestamp) ");
		sql.append("				) ");
		sql.append("		) ");
		sql.append("		or ");
		sql.append("		( ");
		sql.append("			( 	cast(dt.dia || ' ' || coalesce(dt.horaIni, '00:00') as timestamp) <= cast(:periodoIni as timestamp) ");		
		sql.append("          		and cast(dt.dia || ' ' || coalesce(dt.horaIni, '00:00') as timestamp) <= cast(:periodoFim as timestamp) ");
		sql.append("	         ) ");
		sql.append("			and ( ");
		sql.append("				cast(dt.dia || ' ' || coalesce(dt.horaFim, '23:59') as timestamp) >= cast(:periodoIni as timestamp) ");
		sql.append("		  		and cast(dt.dia || ' ' || coalesce(dt.horaFim, '23:59') as timestamp) >= cast(:periodoFim as timestamp) ");
		sql.append("				) ");
		sql.append("		) ");
		sql.append("	  ) ");
		
		sql.append("and co.empresa_id = :empresaId ");
		sql.append("and co.naoIntegraAc = false ");
		sql.append("and co.codigoAC is not null ");
		
		if(colaboradorCodigoAC != null && !"".equals(colaboradorCodigoAC))
			sql.append("and co.codigoAC = :empregadoCodigo ");

		sql.append("order by co.codigoAC, dt.dia ");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("empresaId", empresaId);
		query.setTimestamp("periodoIni", dataIni);
		query.setTimestamp("periodoFim", dataFim);
		
		if(colaboradorCodigoAC != null && !"".equals(colaboradorCodigoAC))
			query.setString("empregadoCodigo", colaboradorCodigoAC);
		
		int i = 0;
		List resultado = query.list();
		TAula[] aulas = new TAula[resultado.size()];
		
		for(Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			TAula tAula = new TAula();
			tAula.setEmpregadoCodigo((String)res[1]);
			tAula.setData(DateUtil.formataDiaMesAno((Date)res[2]));
			tAula.setHoraIni((String)res[3]);
			tAula.setHoraFim((String)res[4]);
			tAula.setCursoNome((String)res[5]);
			tAula.setTurmaNome((String)res[6]);
			
			if(considerarPresenca)
			{
				if (res[7] != null && (Boolean)res[7])
					tAula.setStatus(StatusTAula.getPresente());
				else
					tAula.setStatus(StatusTAula.getFalta());
			}
				
			aulas[i++] = tAula;
		}
		
		return aulas;
	}

	public Collection<Colaborador> findColaboradorByCursos(Long[] cursosIds, Long[] turmasIds) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Colaborador(co.nome, co.nomeComercial) ");
		hql.append("from ColaboradorTurma as ct ");
		hql.append("left join ct.colaborador as co ");
		hql.append("where ct.curso.id in (:cursoIds) ");
		hql.append("and co.naoIntegraAc = false ");
		hql.append("and (co.codigoAC is null or co.codigoAC = '') ");
		
		if (turmasIds != null && turmasIds.length > 0)
			hql.append("and ct.turma.id in (:turmasIds) ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setParameterList("cursoIds", cursosIds, Hibernate.LONG);

		if (turmasIds != null && turmasIds.length > 0)
			query.setParameterList("turmasIds", turmasIds, Hibernate.LONG);

		return query.list();	
	}
	
	public Collection<ColaboradorTurma> findTurmaRealizadaByCodigoAc(String colaboradorCodigoAC, Date dataIni, Date dataFim) 
	{
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class,"ct");
        criteria.createCriteria("ct.colaborador", "c");
        criteria.createCriteria("ct.turma", "t");
        criteria.createCriteria("t.curso", "cu");
        criteria.createCriteria("t.diasTurma", "dt");

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("t.id"), "turmaId");
        p.add(Projections.property("t.descricao"), "turmaDescricao");
        p.add(Projections.property("c.codigoAC"), "colaboradorCodigoAc");
        p.add(Projections.property("cu.nome"), "cursoNome");
        criteria.setProjection(Projections.distinct(p));

        if(colaboradorCodigoAC != null && !"".equals(colaboradorCodigoAC))
        	criteria.add(Expression.eq("c.codigoAC", colaboradorCodigoAC));
        
        criteria.add(Expression.eq("t.realizada", true));
        criteria.add(Expression.between("dt.dia", dataIni, dataFim));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));

        return criteria.list();
	}
	
	public Collection<ColaboradorTurma> findCursosVencidosAVencer(Date dataIni, Long[] empresasIds, Long[] cursosIds, char filtroAgrupamento, char filtroSituacao, char filtroAprovado) {
	    
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.colaborador", "cb", Criteria.INNER_JOIN);
		criteria.createCriteria("cb.empresa", "e", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.curso", "c", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.turma", "t", Criteria.INNER_JOIN);
		criteria.createCriteria("c.empresasParticipantes", "ep", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "empresaId");
		p.add(Projections.property("e.nome"), "empresaNome");
		p.add(Projections.property("cb.id"), "colaboradorId");
		p.add(Projections.property("cb.nome"), "colaboradorNome");
		p.add(Projections.property("cb.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("c.id"), "cursoId");
		p.add(Projections.property("c.nome"), "cursoNome");
		p.add(Projections.property("c.periodicidade"), "cursoPeriodicidade");
		p.add(Projections.property("c.percentualMinimoFrequencia"), "cursoPercentualMinimoFrequencia");
		p.add(Projections.property("t.id"), "turmaId");
		p.add(Projections.property("t.descricao"), "turmaDescricao");
		p.add(Projections.property("t.dataPrevFim"), "turmaDataPrevFim");
		p.add(Projections.property("t.realizada"), "turmaRealizada");
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.property("ct.aprovado"), "aprovado");
		p.add(Projections.sqlProjection("( t4_.dataprevfim + (c3_.periodicidade || ' month')::interval) as vencimento", new String[] {"vencimento"}, new Type[] {Hibernate.DATE}), "vencimento");
				
		if(filtroSituacao ==FiltroSituacaoCurso.TODOS.getOpcao())
		    p.add(Projections.sqlProjection("case when (t4_.dataprevfim + (c3_.periodicidade || ' month')::interval) < to_timestamp('"+ DateUtil.formataAnoMesDia(dataIni) +"', 'YYYY-MM-DD') then true else false end as vencido", new String[] {"vencido"}, new Type[] {Hibernate.BOOLEAN}), "vencido");
		
		criteria.setProjection(p);

		criteria.add(Expression.or(Expression.in("ep.id", empresasIds),Expression.in("c.empresa.id", empresasIds)));
		criteria.add(Expression.eq("t.realizada", true));
		criteria.add(Expression.eq("cb.desligado", false));
		criteria.add(Expression.gt("c.periodicidade", 0));
		if(cursosIds != null && cursosIds.length > 0)
			criteria.add(Expression.in("c.id", cursosIds));
		
		if (filtroSituacao == FiltroSituacaoCurso.A_VENCER.getOpcao()) {
			criteria.add(criterionCursosAVencer(dataIni, ">=", filtroAprovado));
		} else if (filtroSituacao == FiltroSituacaoCurso.VENCIDOS.getOpcao()) {
			criteria.add(criterionCursosVencidos(dataIni));
		} else {
			criteria.add(Restrictions.or(criterionCursosAVencer(dataIni, ">=", filtroAprovado), criterionCursosVencidos(dataIni)));
		}
		
		criteria.addOrder(Order.asc("e.nome"));
		
		if (filtroAgrupamento == FiltroAgrupamentoCursoColaborador.CURSOS.getOpcao()) {
			criteria.addOrder(Order.asc("c.nome"));			
			criteria.addOrder(Order.asc("cb.nome"));
		} else {
			criteria.addOrder(Order.asc("cb.nome"));
			criteria.addOrder(Order.asc("c.nome"));
		}
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));
		
		Collection<ColaboradorTurma> colaboradores = criteria.list();
		
		return colaboradores;
	}
	
	public Collection<ColaboradorTurma> findCursosCertificacoesAVencer(Date dataReferencia, Long empresaId) {
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		joinsFindCursosCertificacoesAVencer(criteria);
		projectionsListFindCursosCertificacoesAVencer(criteria);
		criteria.add(Expression.eq("e.id", empresaId));
		criteria.add(Expression.eq("t.realizada", true));
		criteria.add(Expression.eq("cb.desligado", false));
		criteria.add(Expression.gt("c.periodicidade", 0));
		
	    DetachedCriteria subSelect = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
	    		.setProjection(Projections.max("hc2.data"))
	    		.add(Restrictions.eqProperty("hc2.colaborador.id", "cb.id"))
	    		.add(Restrictions.eq("hc2.status", StatusRetornoAC.CONFIRMADO));
	    
	    criteria.add(Subqueries.propertyEq("hc.data", subSelect));
		criteria.add(criterionCursosAVencer(dataReferencia, "=", StatusAprovacao.APROVADO));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("cb.nome"));
		criteria.addOrder(Order.asc("cb.id"));
		criteria.addOrder(Order.asc("cc.nome"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));
		Collection<ColaboradorTurma> colaboradores = criteria.list();
		
		return colaboradores;
	}

	private void joinsFindCursosCertificacoesAVencer(Criteria criteria) throws HibernateException {
		criteria.createCriteria("ct.colaborador", "cb", Criteria.INNER_JOIN);
		criteria.createCriteria("cb.empresa", "e", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.curso", "c", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.turma", "t", Criteria.INNER_JOIN);
		criteria.createCriteria("cb.historicoColaboradors", "hc", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.faixaSalarial", "fs", Criteria.INNER_JOIN);
		criteria.createCriteria("fs.cargo", "ca", Criteria.INNER_JOIN);
		criteria.createCriteria("hc.areaOrganizacional", "ao", Criteria.INNER_JOIN);
		criteria.createCriteria("c.certificacaos", "cc", Criteria.LEFT_JOIN);
	}

	private void projectionsListFindCursosCertificacoesAVencer(Criteria criteria) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cc.nome"), "certificacaoNome");
		p.add(Projections.property("cb.id"), "colaboradorId");
		p.add(Projections.property("cb.nome"), "colaboradorNome");
		p.add(Projections.property("cb.contato.email"), "colaboradorEmail");
		p.add(Projections.property("c.id"), "cursoId");
		p.add(Projections.property("c.nome"), "cursoNome");
		p.add(Projections.property("c.periodicidade"), "cursoPeriodicidade");
		p.add(Projections.property("ao.id"), "areaOrganizacionalId");
		p.add(Projections.sqlProjection("monta_familia_area(ao8_.id) as areaOrganizacionalNome", new String[] {"areaOrganizacionalNome"}, new Type[] {Hibernate.TEXT}), "areaOrganizacionalNome");
		p.add(Projections.property("fs.nome"), "faixaSalarialNome");
		p.add(Projections.property("ca.nome"), "cargoNome");
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.sqlProjection("( t4_.dataprevfim + (c3_.periodicidade || ' month')::interval) as vencimento", new String[] {"vencimento"}, new Type[] {Hibernate.DATE}), "vencimento");
		
		criteria.setProjection(p);
	}
	
	private Criterion criterionCursosAVencer(Date dataReferencia, String operador, char filtroAprovado){
		StringBuilder str = new StringBuilder();
		str.append("(select max(tr.dataprevfim) + (c3_.periodicidade || ' month')::interval from colaboradorturma ct2 join turma tr on ct2.turma_id = tr.id where ct2.id = this_.id and tr.realizada = true ");
				
		if(StatusAprovacao.APROVADO == filtroAprovado)
			str.append("and ct2.aprovado ");
		else if(StatusAprovacao.REPROVADO == filtroAprovado)
			str.append("and not ct2.aprovado ");
		
		str.append(" ) " + operador + " ?");
		return Expression.sqlRestriction(str.toString(), dataReferencia, Hibernate.DATE);
	}
	
	private Criterion criterionCursosVencidos(Date dataReferencia){
		StringBuilder sql = new StringBuilder("(select max(tr.dataprevfim) + (c3_.periodicidade || ' month')::interval ");
		sql.append("			                           from colaboradorturma ct2 join turma tr on ct2.turma_id = tr.id ");
		sql.append("		                            		where ct2.id = this_.id and tr.curso_id = c3_.id  and tr.realizada = true and ct2.aprovado ) < ? " );
		sql.append(" 						and cb1_.id not in (");
		sql.append("                               				 select distinct(ct.colaborador_id) ");
		sql.append("                                      			from turma t join colaboradorturma ct on ct.turma_id = t.id ");
		sql.append("                                           			where (t.dataprevfim + (c3_.periodicidade || ' month')::interval) >= ? and t.realizada = true and t.curso_id = c3_.id and ct.aprovado ");
		sql.append("                               				) ");
		return Expression.sqlRestriction(sql.toString(), new Date[] {dataReferencia, dataReferencia}, new Type[]{Hibernate.DATE, Hibernate.DATE});
	}
	
	public Collection<ColaboradorTurma> findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(Long certificacaoId, Long colaboradorCertificacaoId, Long... colaboradoresId) 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select ct.id as ctId, c.id as cursoId, c.nome, t.id as turmaId, t.descricao, t.dataPrevIni, t.dataPrevFim, t.realizada, ct.aprovado, ct.colaborador_id as colaboradorId ");
		sql.append("from ColaboradorTurma as ct ");
		sql.append("inner join turma as t on ct.turma_id = t.id ");
		sql.append("inner join curso as c on t.curso_id = c.id ");
		sql.append("inner join certificacao_curso as cec on cec.cursos_id = c.id ");
		sql.append("inner join certificacao as ce on cec.certificacaos_id = ce.id ");
		sql.append("where ct.colaborador_id in (:colaboradorId) ");
		sql.append("and ce.id = :certificacaoId ");
		sql.append("and t.dataPrevFim = ( ");
		sql.append("                       select max(t2.dataPrevFim) ");
		sql.append("                       from ColaboradorTurma ct2 ");
		sql.append("                       inner join turma t2 on ct2.turma_id = t2.id ");
		sql.append("                       where t2.curso_id = c.id ");
		sql.append("                       and ct2.colaborador_id = ct.colaborador_id ");
		
		if(colaboradorCertificacaoId != null){
			sql.append("and ");
			sql.append("(t2.dataPrevFim between ");
			sql.append("			coalesce( ");
			sql.append("						(select data from colaboradorcertificacao where data = (select max(data) from colaboradorcertificacao ");
			sql.append("								where data < (select data from colaboradorcertificacao where id = :colaboradorCertificacaoId) ");
			sql.append("								and certificacao_id = :certificacaoId and colaborador_id = ct.colaborador_id ");
			sql.append("						) and certificacao_id = :certificacaoId and colaborador_id = ct.colaborador_id) ");
			sql.append("					, '01/01/2000' ");
			sql.append("					) ");
			sql.append("	and (select data from colaboradorcertificacao where id = :colaboradorCertificacaoId) ");
			sql.append(") ");
		}else{
			sql.append("and ");
			sql.append("t2.dataPrevFim  > coalesce((select max(data) from colaboradorcertificacao where certificacao_id = :certificacaoId and colaborador_id = ct.colaborador_id),'01/01/2000') ");
		}	
		
		sql.append("                     ) ");
		sql.append("order by c.nome, t.descricao ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameterList("colaboradorId", colaboradoresId, Hibernate.LONG);
		query.setLong("certificacaoId", certificacaoId);
		
		if(colaboradorCertificacaoId != null)
			query.setLong("colaboradorCertificacaoId",colaboradorCertificacaoId);

		List resultado = query.list();
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		
		for(Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			
			ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
			colaboradorTurma.setId(((BigInteger)res[0]).longValue());
			colaboradorTurma.setCursoId(((BigInteger)res[1]).longValue());
			colaboradorTurma.setCursoNome((String)res[2]);
			colaboradorTurma.setTurmaId(((BigInteger)res[3]).longValue());
			colaboradorTurma.setTurmaDescricao((String)res[4]);
			colaboradorTurma.setTurmaDataPrevIni((Date)res[5]);
			colaboradorTurma.setTurmaDataPrevFim((Date)res[6]);
			colaboradorTurma.setTurmaRealizada((Boolean)res[7]);
			colaboradorTurma.setAprovado((Boolean)res[8]);
			colaboradorTurma.setColaboradorId(((BigInteger)res[9]).longValue());
			
			colaboradorTurmas.add(colaboradorTurma);
		}
		return colaboradorTurmas;	
	}

	public Colaborador verificaColaboradorCertificado(Long colaboradorId, Long certificacaoId) {
		Criteria criteria = getSession().createCriteria(ColaboradorCertificacao.class, "cc");
		criteria.createCriteria("cc.certificacao", "cert", Criteria.INNER_JOIN);
		criteria.createCriteria("cc.colaborador", "co", Criteria.INNER_JOIN);
		
		StringBuilder sql = new StringBuilder(" (case when coalesce (cert1_.periodicidade,0) > 0 ");
		sql.append("THEN(this_.data + (cert1_.periodicidade || ' month')::interval  >= now())");
		sql.append(" else true end )"); 
		
		criteria.add(Expression.sqlRestriction( sql.toString()));
		
		DetachedCriteria subSelect = DetachedCriteria.forClass(ColaboradorCertificacao.class, "cc2")
	    		.setProjection(Projections.max("cc2.data"))
	    		.add(Restrictions.eq("cc2.colaborador.id", colaboradorId))
	    		.add(Restrictions.eq("cc2.certificacao.id", certificacaoId));
	    
	    criteria.add(Subqueries.propertyEq("cc.data", subSelect));
	    
	    ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "id");
		p.add(Projections.property("co.nome"), "nome");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cc.colaborador.id", colaboradorId));
		criteria.add(Expression.eq("cc.certificacao.id", certificacaoId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));
		
	    return (Colaborador) criteria.uniqueResult();
	}

	public Collection<ColaboradorTurma> findByTurmaId(Long turmaId) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.property("ct.aprovado"), "aprovado");
		p.add(Projections.property("ct.motivoReprovacao"), "motivoReprovacao");
		p.add(Projections.property("ct.curso.id"), "cursoId");
		p.add(Projections.property("ct.turma.id"), "turmaId");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.nome"), "colaboradorNome");

		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.colaborador", "c");
		criteria.add(Expression.eq("ct.turma.id", turmaId));
		criteria.setProjection(p);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));
		
	    return criteria.list();
	}
	
	public Collection<ColaboradorTurma> findColaboradorTurmaByCertificacaoControleVencimentoPorCertificacao(Long certificacaoId) {
		ProjectionList p  = Projections.projectionList().create();
		p.add(Projections.property("co.id"), "colaboradorId");
		p.add(Projections.property("co.nome"), "colaboradorNome");
		p.add(Projections.sqlProjection("case when colabCert5_.id is not null then true else false end as certificado", new String[] {"certificado"}, new Type[] {Hibernate.BOOLEAN}), "certificado");
		
		DetachedCriteria subSelect = DetachedCriteria.forClass(ColaboradorCertificacao.class, "cc2")
	    		.setProjection(Projections.max("cc2.data"))
	    		.add(Restrictions.eqProperty("cc2.colaborador.id", "co.id"))
	    		.add(Restrictions.eq("cc2.certificacao.id", certificacaoId));
	    
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.colaborador", "co", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.turma", "t", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.curso", "c", Criteria.INNER_JOIN);
		criteria.createAlias("c.certificacaos", "cc", Criteria.INNER_JOIN);
		criteria.createAlias("co.colaboradorCertificacaos", "colabCert", Criteria.LEFT_JOIN);
		
		criteria.add(Expression.eq("cc.id", certificacaoId));
		criteria.add(Expression.eq("t.realizada", true));
		criteria.add(Expression.isNull("co.dataDesligamento"));
		criteria.add(Expression.or(Subqueries.propertyEq("colabCert.data", subSelect), Expression.isNull("colabCert.data")));
		criteria.addOrder(Order.asc("co.nome"));

		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));
		return criteria.list();
	}

	public Collection<ColaboradorTurma> findColaboradorTurmaByCertificacaoControleVencimentoPorCurso(Long certificacaoId, int qtdCursos) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct co.id, co.nome, CASE WHEN :qtdCursos = 1 THEN ct.aprovado ELSE count(c.id) OVER(PARTITION BY co.id, ct.aprovado) = :qtdCursos END as certificado  ");
		sql.append("	FROM ColaboradorTurma ct ");
		sql.append("		INNER JOIN Colaborador co ON co.id = ct.colaborador_id ");
		sql.append("		INNER JOIN Turma t ON t.id = ct.turma_id ");
		sql.append("		INNER JOIN Curso c ON c.id = ct.curso_id ");
		sql.append("		INNER JOIN Certificacao_Curso certif_curso ON  certif_curso.cursos_id = c.id ");
		sql.append("		INNER JOIN Certificacao cc ON cc.id = certif_curso.certificacaos_id");
		sql.append("	WHERE cc.id = :certificacaoId AND t.realizada = true  AND co.dataDesligamento is null ");
		sql.append("	AND t.dataPrevIni = (SELECT MAX(t2.dataPrevIni) FROM Turma t2  INNER JOIN ColaboradorTurma ct2 ON ct2.turma_id = t2.id WHERE t2.curso_id = ct.curso_id AND ct2.colaborador_id = ct.colaborador_id )");
		sql.append("ORDER BY co.nome ASC");
												
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("certificacaoId", certificacaoId);
		query.setInteger("qtdCursos", qtdCursos);
		
		List resultado = query.list();
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		
		for(Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
			colaboradorTurma.setColaboradorId(((BigInteger)res[0]).longValue());
			colaboradorTurma.setColaboradorNome((String)res[1]);
			colaboradorTurma.setCertificado((Boolean)res[2]);
			colaboradorTurmas.add(colaboradorTurma);
		}
		return colaboradorTurmas;	
	}

	
	public boolean aprovarOrReprovarColaboradorTurma(Long colaboradorTurmaId, Long turmaId, Long cursoId) {
		HashMap<String, Boolean> map = verificaAprovacao(colaboradorTurmaId, turmaId, cursoId);
		
		boolean aprovado = !map.containsValue(false);
		String motivoReprovacao = "";
		
		if(!aprovado)
			motivoReprovacao = getMotivoReprovacao(map);
		
			
		String queryHQL = "update ColaboradorTurma ct set ct.aprovado = :aprovado, ct.motivoReprovacao = :motivoReprovacao where ct.id = :colaboradorTurmaId";

		Query query = getSession().createQuery(queryHQL);
		query.setBoolean("aprovado", aprovado);
		query.setLong("colaboradorTurmaId", colaboradorTurmaId);
		query.setString("motivoReprovacao", motivoReprovacao);
		query.executeUpdate();
		
		return aprovado;
	}
	
	private String getMotivoReprovacao(HashMap<String, Boolean> map) {
		
		if(map.get(MotivoReprovacao.NOTA.getMotivo()) && map.get(MotivoReprovacao.FREQUENCIA.getMotivo()))
			return MotivoReprovacao.NOTA_FREQUENCIA.getMotivo();
		
		if(map.get(MotivoReprovacao.NOTA.getMotivo()))
			return MotivoReprovacao.NOTA.getMotivo();
		
		if(map.get(MotivoReprovacao.FREQUENCIA.getMotivo()))
			return MotivoReprovacao.FREQUENCIA.getMotivo();
			
		return MotivoReprovacao.REPROVADO.getMotivo();
		
	}

	private HashMap<String, Boolean> verificaAprovacao(Long colaboradorTurmaId, Long turmaId, Long cursoId){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM verifica_aprovacao(:cursoId, :turmaId, :colaboradorTurmaId ) as aprovacao ");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setLong("cursoId", cursoId);
		query.setLong("turmaId", turmaId);
		query.setLong("colaboradorTurmaId", colaboradorTurmaId);
		
		List resultado = query.list();
		
		HashMap<String, Boolean> map = new HashMap<String, Boolean>(); 
	
		for(Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			Object[] res = it.next();
			map.put("turmaRealizada", (Boolean)res[0]);
			map.put(MotivoReprovacao.NOTA.getMotivo(), (Boolean)res[1]);
			map.put(MotivoReprovacao.FREQUENCIA.getMotivo(), (Boolean)res[2]);
		}
		
		return map;
	}

	public ColaboradorTurma findByProjection(Long colaboradorTurmaId) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.property("ct.curso.id"), "cursoId");
		p.add(Projections.property("ct.turma.id"), "turmaId");
		p.add(Projections.property("ct.aprovado"), "aprovado");
		p.add(Projections.property("ct.cursoLnt.id"), "cursoLntId");

		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.add(Expression.eq("ct.id", colaboradorTurmaId));
		criteria.setProjection(p);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));
		
	    return (ColaboradorTurma) criteria.uniqueResult();
	}

	public Collection<ColaboradorTurma> findAprovadosByTurma(Long turmaId) {
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.property("ct.curso.id"), "cursoId");
		p.add(Projections.property("ct.turma.id"), "turmaId");
		p.add(Projections.property("ct.aprovado"), "aprovado");
		p.add(Projections.property("t.descricao"), "turmaDescricao");
		p.add(Projections.property("t.dataPrevIni"), "projectionDataPrevIni");
		p.add(Projections.property("t.dataPrevFim"), "projectionDataPrevFim");
		p.add(Projections.property("t.horario"), "turmaHorario");
		p.add(Projections.property("t.instrutor"), "instrutor");
		p.add(Projections.property("c.nome"), "cursoNome");
		p.add(Projections.property("c.cargaHoraria"), "projectionCursoCargaHoraria");
		p.add(Projections.property("c.conteudoProgramatico"), "projectionCursoConteudoProgramatico");
		p.add(Projections.property("co.id"), "colaboradorId");
		p.add(Projections.property("co.nome"), "colaboradorNome");
		
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.colaborador", "co", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.curso", "c", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.turma", "t", Criteria.INNER_JOIN);
		
		criteria.add(Expression.eq("t.id", turmaId));
		criteria.add(Expression.eq("t.realizada", true));
		criteria.add(Expression.eq("ct.aprovado", true));
		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("co.nome"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));
		
	    return criteria.list();
	}		
	
	public Collection<Colaborador> findColabodoresByTurmaId(Long turmaId) {
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class,"ct");
        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("ct.colaborador.id"), "id");
		
        criteria.setProjection(p);
        criteria.add(Expression.eq("ct.turma.id", turmaId));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Colaborador.class));

		return criteria.list();
	}

	public Collection<ColaboradorTurma> findByCursoLntId(Long cursoLntId) {
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class,"ct");
        criteria.createCriteria("ct.colaborador", "c");

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("c.id"), "colaboradorId");
        p.add(Projections.property("c.nome"), "colaboradorNome");
        criteria.setProjection(Projections.distinct(p));

       	criteria.add(Expression.eq("ct.cursoLnt.id", cursoLntId));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));

        return criteria.list();
	}

	public void updateCursoLnt(Long cursoId, Long colaboradorTurmaId, Long lntId) {
		String queryHQL = "update ColaboradorTurma set cursoLnt.id = (select id from CursoLnt where lnt.id = :lntId and curso.id = :cursoId) where id = :colaboradorTurmaId)";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("cursoId", cursoId);
		query.setLong("colaboradorTurmaId", colaboradorTurmaId);
		query.setLong("lntId", lntId);
		query.executeUpdate();
	}
	
	public void removeCursoLnt(Long colaboradorTurmaId) {
		String queryHQL = "update ColaboradorTurma set cursoLnt.id = null where id = :colaboradorTurmaId ";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("colaboradorTurmaId", colaboradorTurmaId);
		query.executeUpdate();
	}
	
	public void removeAllCursoLntByLnt(Long lntId) {
		String queryHQL = "update ColaboradorTurma set cursoLnt.id = null where cursoLnt.id in (select id from CursoLnt where lnt.id = :lntId)";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("lntId", lntId);
		query.executeUpdate();
	}
	
	public void updateCursoLnt(Long colaboradorTurmaId, Long cursoLnt) {
		String queryHQL = "update ColaboradorTurma set cursoLnt.id = :cursoLnt where id = :colaboradorTurmaId ";

		Query query = getSession().createQuery(queryHQL);
		query.setLong("cursoLnt", cursoLnt);
		query.setLong("colaboradorTurmaId", colaboradorTurmaId);
		query.executeUpdate();
	}
	
	public void removeCursoLntByParticipantesCursoLnt(Long[] participantesRemovidos) {
		String sql = "update ColaboradorTurma set cursoLnt_id = null " 
				+ "where id in ( " 
				+ "select ct.id from ParticipanteCursoLnt pcl "
				+ "inner join colaboradorTurma ct on pcl.cursoLnt_id = ct.cursoLnt_id and pcl.colaborador_id = ct.colaborador_id " 
				+ "where pcl.id in ";
		
		JDBCConnection.executeQuery(new String[] {sql + "(" + StringUtils.join(participantesRemovidos, ",") + "));"});
	}

	public Collection<ColaboradorTurma> findParticipantesCursoLnt(Long cursoLntId) {
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.turma", "t", Criteria.INNER_JOIN);
		criteria.createCriteria("ct.colaborador", "c", Criteria.INNER_JOIN);
		criteria.createCriteria("c.historicoColaboradors", "hc");
		criteria.createCriteria("hc.estabelecimento", "est");
		criteria.createCriteria("hc.areaOrganizacional", "ao");
		criteria.createCriteria("c.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ct.turma.id"), "turmaId");
		p.add(Projections.property("t.descricao"), "turmaDescricao");
		p.add(Projections.property("t.dataPrevIni"), "projectionDataPrevIni");
		p.add(Projections.property("t.dataPrevFim"), "projectionDataPrevFim");
		p.add(Projections.property("t.horario"), "turmaHorario");
		p.add(Projections.property("t.instrutor"), "instrutor");
		p.add(Projections.property("t.instituicao"), "turmaInstituicao");
		p.add(Projections.property("t.custo"), "turmaCusto");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		p.add(Projections.property("c.nomeComercial"), "colaboradorNomeComercial");
		p.add(Projections.property("ao.nome"), "areaOrganizacionalNome");
		p.add(Projections.sqlProjection("monta_familia_area(ao5_.id) as areaOrganizacionalNomeComHierarquia", new String[] {"areaOrganizacionalNomeComHierarquia"}, new Type[] {Hibernate.TEXT}), "areaOrganizacionalNomeComHierarquia");
		p.add(Projections.property("est.nome"), "estabelecimentoNome");
		p.add(Projections.property("emp.nome"), "empresaNome");
		criteria.setProjection(Projections.distinct(p));
		
		criteria.add(Subqueries.propertyEq("hc.data", montaSubQueryHistoricoColaborador(new Date(), StatusRetornoAC.CONFIRMADO)));

		criteria.add(Expression.eq("ct.cursoLnt.id", cursoLntId));		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));
		
	    return criteria.list();
	}
	
	private DetachedCriteria montaSubQueryHistoricoColaborador(Date data, Integer status)
	{
		return DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
				.setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"))
				.add(Restrictions.le("hc2.data", data))
				.add(Restrictions.eq("hc2.status", status));
	}
}