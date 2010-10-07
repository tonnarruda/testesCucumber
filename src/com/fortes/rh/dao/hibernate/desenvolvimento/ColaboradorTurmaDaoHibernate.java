package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
@SuppressWarnings("unchecked")
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

	public Collection<ColaboradorTurma> findByTurma(Long turmaId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id, pt.id, co.id, co.nome, co.nomeComercial, co.matricula, ao.id, ao.nome, ct.aprovado, e.nome, fs.nome, c.nome) ");
		hql.append("from ColaboradorTurma as ct ");
		hql.append("left join ct.colaborador as co ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.estabelecimento as e ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as c ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join ct.turma as t ");
		hql.append("left join ct.prioridadeTreinamento as pt ");
		hql.append("where ");
		hql.append("	t.id = :turmaId ");
		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("	) ");
		hql.append(" order by co.nome asc");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("turmaId", turmaId);

		Collection<ColaboradorTurma> colaboradorTurmas = query.list();

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
		hql.append("and ao.id in (:areasId) ");
		hql.append("and es.id in (:estabelecimentosId) ");
		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("	) ");
		hql.append("and ( co.id not in ( ");
		hql.append("		select ct2.colaborador.id ");
		hql.append("		from ColaboradorTurma ct2 join ct2.turma t2 ");
		hql.append("		where t2.dataPrevFim >= :data and t2.dataPrevFim <= :hoje) ");
		hql.append("or ct.id = null) ");

		hql.append("order by es.nome, ao.nome, co.nome asc, t.dataPrevFim desc ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setParameterList("areasId", areas, Hibernate.LONG);
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

	public Collection<ColaboradorTurma> getColaboradoresByTurma(Collection<Long> turmaIds)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.turma", "t", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("ct.colaborador", "c", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("ct.curso","cur", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		p.add(Projections.property("cur.nome"), "cursoNome");
		p.add(Projections.property("cur.conteudoProgramatico"), "projectionCursoConteudoProgramatico");
		p.add(Projections.property("t.descricao"), "turmaDescricao");
		p.add(Projections.property("t.instrutor"), "instrutor");
		p.add(Projections.property("t.dataPrevIni"), "projectionDataPrevIni");
		p.add(Projections.property("t.dataPrevFim"), "projectionDataPrevFim");

		criteria.setProjection(Projections.distinct(p));

		if (turmaIds != null && turmaIds.size() >=1 )
			criteria.add(Expression.in("t.id", turmaIds));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));

		return criteria.list();
	}

	public List findCustoRateado()
	{
		String hql = " select t.id,(t.custo / count(ct.id)) from ColaboradorTurma ct inner join ct.turma t group by t.id,t.custo";

		Query query = getSession().createQuery(hql);

		return query.list();
	}

	public Integer getCount(Long turmaId)
	{
		String hql = "select count(ct.id) from ColaboradorTurma ct where ct.turma.id = :turmaId";
		Query query = getSession().createQuery(hql);
		query.setLong("turmaId", turmaId);

		return (Integer)query.uniqueResult();
	}

	public Collection<ColaboradorTurma> findRelatorioSemTreinamento(Long empresaId, Curso curso, Long[] areaIds, Long[] estabelecimentoIds)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new ColaboradorTurma(c.id, c.nome, c.matricula, ao.id, es.nome) " );
		hql.append("from HistoricoColaborador as hc ");
		hql.append("left join hc.areaOrganizacional as ao ");
		hql.append("left join hc.estabelecimento as es ");
		hql.append("left join hc.colaborador as c ");
		hql.append("where c.desligado = false and c.empresa.id = :empresaId ");

		if (areaIds.length > 0)
			hql.append("and ao.id in (:areasId) ");

		if (estabelecimentoIds.length > 0)
			hql.append("and es.id in (:estabelecimentosId) ");

		hql.append("and hc.data = (select max(hc2.data) from HistoricoColaborador hc2 where hc2.colaborador.id=c.id and hc2.data <= :hoje and hc2.status = :status ) ");
		hql.append("and c.id not in (select ct.colaborador.id from ColaboradorTurma as ct where ct.colaborador.id=c.id ");
		hql.append("and ct.curso.id = :cursoId ");

		hql.append(") order by es.nome, ao.nome, c.nome ");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("empresaId", empresaId);
		query.setLong("cursoId", curso.getId());

		if (areaIds.length > 0)
			query.setParameterList("areasId", areaIds, Hibernate.LONG);

		if (estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentosId", estabelecimentoIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<ColaboradorTurma> findRelatorioComTreinamento(Long empresaId, Curso curso, Long[] areaIds, Long[] estabelecimentoIds, Long[] colaboradorTurmaIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(c.id, c.nome, c.matricula, ao.id, es.nome, t.descricao, t.dataPrevIni, t.dataPrevFim, ct.id, ct.aprovado) " );

		hql.append("from Colaborador as c ");
		hql.append("join c.historicoColaboradors as hc ");
		hql.append("join c.colaboradorTurmas as ct ");
		hql.append("join ct.turma as t ");
		hql.append("join hc.areaOrganizacional as ao ");
		hql.append("join hc.estabelecimento as es ");
		hql.append("where c.desligado = false and c.empresa.id = :empresaId ");

		if (areaIds != null && areaIds.length > 0)
			hql.append("and ao.id in (:areasId) ");

		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append("and es.id in (:estabelecimentosId) ");

		if (colaboradorTurmaIds != null && colaboradorTurmaIds.length > 0)
			hql.append("and ct.id in (:colaboradorTurmaIds) ");

		hql.append("and hc.data = (select max(hc2.data) from HistoricoColaborador hc2 where hc2.colaborador.id=c.id and hc2.data <= :hoje and hc2.status = :status ) ");
		hql.append("and ct.curso.id = :cursoId ");

		hql.append(" order by es.nome, ao.nome, c.nome ");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("empresaId", empresaId);
		query.setLong("cursoId", curso.getId());

		if (areaIds != null && areaIds.length > 0)
			query.setParameterList("areasId", areaIds, Hibernate.LONG);

		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentosId", estabelecimentoIds, Hibernate.LONG);

		if (colaboradorTurmaIds != null && colaboradorTurmaIds.length > 0)
			query.setParameterList("colaboradorTurmaIds", colaboradorTurmaIds, Hibernate.LONG);

		return query.list();
	}
	
	public Collection<ColaboradorTurma> findColaboradoresCertificacoes(Long empresaId, Certificacao certificacao, Long[] areaIds, Long[] estabelecimentoIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(c.id, c.nome, c.matricula, ao.id, es.nome, t.id, t.descricao, t.dataPrevIni, t.dataPrevFim, ct.id, ct.aprovado, curso.id,curso.nome) " );
		hql.append("from Colaborador as c ");
		hql.append("join c.historicoColaboradors as hc ");
		hql.append("left join c.colaboradorTurmas as ct ");
		
		hql.append("left join ct.curso as curso ");
		hql.append("left join curso.certificacaos as certificacao ");
		hql.append("left join ct.turma as t ");
		
		hql.append("join hc.areaOrganizacional as ao ");
		hql.append("join hc.estabelecimento as es ");
		hql.append("join hc.faixaSalarial as fs ");
		hql.append("join fs.certificacaos as certificacao ");

//		hql.append("join certificacao.cursos curso ");
//		hql.append("join curso.turmas as t ");
		
		hql.append("where c.desligado = false and c.empresa.id = :empresaId ");

		if (areaIds != null && areaIds.length > 0)
			hql.append("and ao.id in (:areasId) ");

		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			hql.append("and es.id in (:estabelecimentosId) ");
		
		hql.append("and hc.data = (select max(hc2.data) from HistoricoColaborador hc2 where hc2.colaborador.id=c.id and hc2.data <= :hoje and hc2.status = :status ) ");
		hql.append("and certificacao.id = :certificacaoId ");
		hql.append("and fs.certificacaos.id = certificacao.id ");

		hql.append(" order by es.nome, ao.nome, certificacao.nome, c.nome ");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("empresaId", empresaId);
		query.setLong("certificacaoId", certificacao.getId());

		if (areaIds != null && areaIds.length > 0)
			query.setParameterList("areasId", areaIds, Hibernate.LONG);

		if (estabelecimentoIds != null && estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentosId", estabelecimentoIds, Hibernate.LONG);
		
		return query.list();
	}

	public Collection<ColaboradorTurma> findByTurmaSemPresenca(Long turmaId, Long diaTurmaId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id) from ColaboradorTurma ct where ct.turma.id = :turmaId ");
		hql.append("and ct.id not in (select cp.colaboradorTurma.id from ColaboradorPresenca cp where cp.diaTurma.id = :diaTurmaId)");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("turmaId", turmaId);
		query.setLong("diaTurmaId", diaTurmaId);

		return query.list();
	}

	public Collection<ColaboradorTurma> findByIdProjection(Long[] ids)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.turma", "t", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("ct.colaborador", "c", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("ct.curso","cur", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		p.add(Projections.property("cur.nome"), "cursoNome");
		p.add(Projections.property("cur.cargaHoraria"), "projectionCursoCargaHoraria");
		p.add(Projections.property("cur.conteudoProgramatico"), "projectionCursoConteudoProgramatico");
		p.add(Projections.property("t.descricao"), "turmaDescricao");
		p.add(Projections.property("t.instrutor"), "instrutor");
		p.add(Projections.property("t.dataPrevIni"), "projectionDataPrevIni");
		p.add(Projections.property("t.dataPrevFim"), "projectionDataPrevFim");

		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.in("ct.id", ids));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));

		return criteria.list();
	}

	public Collection<ColaboradorTurma> findHistoricoTreinamentosByColaborador(Long empresaId, Long colaboradorId, Date dataIni, Date dataFim)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id, co.id, co.nome, c.id, c.nome, ca.nome, fs.id, fs.nome, t.id, t.descricao, t.dataPrevIni, t.dataPrevFim) ");
		hql.append("from ColaboradorTurma as ct ");
		hql.append("left join ct.colaborador as co ");
		hql.append("left join co.historicoColaboradors as hc ");
		hql.append("left join hc.faixaSalarial as fs ");
		hql.append("left join fs.cargo as ca ");
		hql.append("left join ct.turma as t ");
		hql.append("left join t.curso as c ");
//		hql.append("where co.desligado = false and co.empresa.id = :empresaId ");
		hql.append("where co.empresa.id = :empresaId ");
		hql.append("and co.id = :colaboradorId ");
		hql.append("and t.realizada = true ");

		if(dataIni != null)
			hql.append("and t.dataPrevIni >= :dataIni ");
		if (dataFim != null)
			hql.append("and t.dataPrevFim <= :dataFim ");

		hql.append("	and hc.data = ( ");
		hql.append("		select max(hc2.data) " );
		hql.append("		from HistoricoColaborador as hc2 ");
		hql.append("		where hc2.colaborador.id = co.id ");
		hql.append("			and hc2.data <= :hoje and hc2.status = :status ");
		hql.append("	) ");

		hql.append("order by t.dataPrevFim desc ");
		Query query = getSession().createQuery(hql.toString());

		query.setDate("hoje", new Date());
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		query.setLong("empresaId", empresaId);
		query.setLong("colaboradorId", colaboradorId);
		if(dataIni != null)
			query.setDate("dataIni", dataIni);
		if (dataFim != null)
			query.setDate("dataFim", dataFim);

		return query.list();
	}

	public Collection<ColaboradorTurma> findAllSelect(Collection<Long> ids)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.colaborador", "c", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ct.id"), "id");
		p.add(Projections.property("c.id"), "colaboradorId");
		p.add(Projections.property("c.nome"), "colaboradorNome");
		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.in("ct.id", ids));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorTurma.class));

		return criteria.list();
	}

	public Integer findQuantidade(Date dataIni, Date dataFim, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorTurma.class, "ct");
		criteria.createCriteria("ct.turma", "t");
		criteria.createCriteria("t.empresa", "e");
		
        criteria.setProjection(Projections.count("ct.id"));
        criteria.add(Expression.ge("t.dataPrevIni", dataIni));
        criteria.add(Expression.le("t.dataPrevFim", dataFim));
        criteria.add(Expression.eq("e.id", empresaId));

        Integer valor = (Integer) criteria.uniqueResult();
        if(valor == null)
        	return 0;
        else
        	return valor;
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

	public Collection<ColaboradorTurma> findColaboradorByTurma(Long turmaId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorTurma(ct.id, co.id, co.nome, co.nomeComercial, co.matricula ) ");
		hql.append("from ColaboradorTurma as ct ");
		hql.append("left join ct.colaborador as co ");
		hql.append("left join ct.turma as t ");
		hql.append("where t.id = :turmaId ");
		hql.append("order by co.nome asc");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("turmaId", turmaId);

		Collection<ColaboradorTurma> colaboradorTurmas = query.list();

		return colaboradorTurmas;	
	}

}