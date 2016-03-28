package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.LongUtil;

@SuppressWarnings("unchecked")
public class CursoDaoHibernate extends GenericDaoHibernate<Curso> implements CursoDao
{
	public Curso findByIdProjection(Long cursoId)
	{
		Criteria criteria = getSession().createCriteria(Curso.class,"c");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.id", cursoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Curso.class));

		return (Curso) criteria.uniqueResult();
	}
	
	public Collection<Curso> findByIdProjection(Long[] cursoIds)
	{
		Criteria criteria = getSession().createCriteria(Curso.class,"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.codigoTru"), "codigoTru");
		criteria.setProjection(p);

		criteria.add(Expression.in("c.id", cursoIds));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Curso.class));

		return criteria.list();
	}

	public Collection<Curso> findAllSelect(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Curso.class,"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		if(empresaId != null && !empresaId.equals(-1L))
			criteria.add(Expression.eq("c.empresa.id", empresaId));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Curso.class));

		return criteria.list();
	}
	

	public String getConteudoProgramatico(Long id)
	{
		Criteria criteria = getSession().createCriteria(Curso.class,"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.conteudoProgramatico"), "conteudoProgramatico");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", id));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (String) criteria.uniqueResult();
	}

	public Collection<Curso> findByCertificacao(Long certificacaoId)
	{
		StringBuilder hql = new StringBuilder("select new Curso(curso.id, curso.nome, curso.cargaHoraria, curso.conteudoProgramatico, curso.criterioAvaliacao, curso.percentualMinimoFrequencia ) ");
		hql.append("from Certificacao cert ");
		hql.append("left join cert.cursos curso ");
		hql.append("where cert.id = :certificacaoId ");

		hql.append("order by curso.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("certificacaoId", certificacaoId);

		return query.list();
	}

	public Double somaCustosTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds)
	{
		Criteria criteria = getSession().createCriteria(Curso.class,"c");
		criteria.createCriteria("c.turmas", "t");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.sum("t.custo"), "somaCusto");
		criteria.setProjection(p);

		criteria.add(Expression.eq("t.realizada", true));
		criteria.add(Expression.between("t.dataPrevIni", dataIni, dataFim));
		criteria.add(Expression.in("c.empresa.id", empresaIds));
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			criteria.add(Expression.in("c.id", cursoIds));
			
		return (Double) criteria.uniqueResult();
	}
	
	public IndicadorTreinamento findIndicadorHorasTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] cursoIds)
	{
		getSession().flush();
		
		StringBuilder sql = new StringBuilder("select tur.id, coalesce(tur.qtdparticipantesprevistos, 0), cast(coalesce(count(ct.id), 0) as integer) as qtdeInscritosFiltrado, ct3.qtdeInscritosTotal, ");
		sql.append("cast(coalesce(round((cur.cargaHoraria/60.0),1) * count(ct.id), 0) as double precision) as cargaHoraria, ");
		sql.append("CASE when diasTurmaTotal > 0 ");
		sql.append("	then cast(coalesce(((cur.cargaHoraria/60)/dt.diasTurmaTotal)* dt2.diasTurmaRealizado * count(ct.id),0) as double precision) "); 
		sql.append("	else cast(coalesce((cur.cargaHoraria/60) * count(ct.id),0) as double precision) ");
		sql.append("END as cargaHorariaRatiada,");
		sql.append("coalesce((tur.custo/ct3.qtdeInscritosTotal) * count(ct.id), 0) as custo ");
		sql.append("from ColaboradorTurma ct ");
		sql.append("inner join turma tur on ct.turma_id = tur.id "); 
		sql.append("inner join curso cur on tur.curso_id = cur.id ");
		sql.append("inner join colaborador col on ct.colaborador_id = col.id ");
		sql.append("inner join historicoColaborador hc on hc.colaborador_id = col.id and data = (select max(data) from historicoColaborador hc1 where hc1.colaborador_id = col.id and hc1.data <= tur.dataPrevIni) ");
		sql.append("left join ( ");
		sql.append("   select ct2.turma_id, cast(coalesce(count(ct2.id), 0) as integer) as qtdeInscritosTotal from colaboradorTurma ct2 group by ct2.turma_id ");
		sql.append(") as ct3 on ct3.turma_id = tur.id ");
		sql.append("left join ( ");
		sql.append("			select  dt.turma_id as turma_id, cast(coalesce(count(dt.id),0) as integer) as diasTurmaTotal ");
		sql.append("			from diaTurma dt group by dt.turma_id ");
		sql.append(") as dt on dt.turma_id = tur.id  ");
		sql.append("left join ( ");
		sql.append("			select  dt2.turma_id as turma_id, cast(coalesce(count(dt2.id),0) as integer) as diasTurmaRealizado ");
		sql.append("			from diaTurma dt2 ");
		sql.append("			where  dt2.dia between :dataIni and :dataFim " );
		sql.append("			group by dt2.turma_id ");
		sql.append(") as dt2 on dt2.turma_id = tur.id  ");
		sql.append("where col.empresa_id in (:empresaIds) ");
		sql.append("and tur.dataPrevIni between :dataIni and :dataFim "); 
		sql.append("and tur.realizada = true ");
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			sql.append("and cur.id in (:cursoIds) ");
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			sql.append("and hc.areaOrganizacional_id in (:areasIds) ");
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentosIds)){
			sql.append("and hc.estabelecimento_id in (:estabelecimentosIds) ");
		}
		
		sql.append("group by tur.id, tur.qtdparticipantesprevistos, cur.cargahoraria, tur.custo, ct3.qtdeInscritosTotal, dt.diasTurmaTotal, dt2.diasTurmaRealizado ");
		sql.append("order by tur.id ");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameterList("empresaIds", empresaIds);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			query.setParameterList("cursoIds", cursoIds, Hibernate.LONG);
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentosIds)){
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, Hibernate.LONG);
		}
		
		Collection<Object[]> resultado = query.list();
		Object[] res;
		Integer qtdColaboradoresPrevistos = 0, qtdColaboradoresFiltrados = 0, qtdColaboradoresInscritos = 0;
		Double somaHoras = 0.0, somaCustos = 0.0, somaHorasRatiada = 0.0;
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();)
		{
			res = it.next();
			qtdColaboradoresPrevistos += (Integer)res[1];
			qtdColaboradoresFiltrados += (Integer)res[2];
			qtdColaboradoresInscritos += (Integer)res[3];
			somaHoras += (Double)res[4];
			somaHorasRatiada += (Double)res[5];
			somaCustos += (Double)res[6];
		}
		
		return new IndicadorTreinamento(qtdColaboradoresPrevistos, qtdColaboradoresFiltrados, qtdColaboradoresInscritos, somaHoras, somaHorasRatiada, somaCustos);
	}
	
	public Integer findQtdColaboradoresInscritosTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds)
	{
		StringBuilder hql = new StringBuilder("select count(ct.id) ");
		hql.append("from ColaboradorTurma ct ");
		hql.append("join ct.turma t ");
		hql.append("where t.dataPrevIni between :dataIni and :dataFim ");
		hql.append("and t.empresa.id in (:empresaIds) ");
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			hql.append("and t.curso.id in (:cursoIds) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setParameterList("empresaIds", empresaIds);
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			query.setParameterList("cursoIds", cursoIds, Hibernate.LONG);

		return (Integer)query.uniqueResult();
	}

	public Integer findSomaColaboradoresPrevistosTreinamentos(Date dataIni, Date dataFim, Long empresaId)
	{
		StringBuilder hql = new StringBuilder("select sum(t.qtdParticipantesPrevistos) ");
		hql.append("from Turma t ");
		hql.append("join t.curso curso ");
		hql.append("where t.dataPrevIni between :dataIni and :dataFim ");
		hql.append("and curso.empresa.id = :empresaId ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setLong("empresaId", empresaId);

		return (Integer)query.uniqueResult();
	}

	public Integer countTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds, Boolean realizado)
	{
		Criteria criteria = getSession().createCriteria(Turma.class, "t");
		criteria.createCriteria("t.curso", "c");
		criteria.setProjection(Projections.rowCount());

		criteria.add(Expression.ge("t.dataPrevIni", dataIni));
		criteria.add(Expression.le("t.dataPrevIni", dataFim));
		if (realizado != null)
			criteria.add(Expression.eq("realizada", realizado));
		criteria.add(Expression.in("c.empresa.id", empresaIds));
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			criteria.add(Expression.in("c.id", cursoIds));

		return (Integer)criteria.uniqueResult();
	}

	public Integer findCargaHorariaTotalTreinamento(Long[] cursosIds, Long[] empresasIds, Long[] estabelecimentosIds, Long[] areasOrganizacionaisIds, Date dataInicio, Date dataFim, boolean realizada){
		StringBuilder hql = new StringBuilder("");
		hql.append("select  coalesce( sum(totalHorasTreinamento.cargahoraria * totalHorasTreinamento.qtdTurmaCursoId),0)  from (select distinct c.id,  c.cargahoraria, count(t.id) OVER( PARTITION BY c.id) AS qtdTurmaCursoId ");
		hql.append("from Curso c ");
		hql.append("inner join Turma t  on t.curso_id = c.id ");
		hql.append("inner join ColaboradorTurma ct on t.id = ct.turma_id ");
		hql.append("inner join historicoColaborador hc on hc.colaborador_id = ct.colaborador_id and data = (select max(data) from historicoColaborador hc1 where hc1.colaborador_id = ct.colaborador_id and hc1.data <= t.dataPrevIni) ");
		hql.append("where t.realizada = :realizada and t.dataPrevIni between :dataInicio and :dataFim " );
		
		if(cursosIds != null && cursosIds.length > 0)
			hql.append("and c.id in(:cursosIds) ");
		if(areasOrganizacionaisIds != null && areasOrganizacionaisIds.length > 0)
			hql.append("and hc.areaorganizacional_id in (:areasOrganizacionaisIds) ");
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			hql.append("and hc.estabelecimento_id in (:estabelecimentosIds) ");
		if(empresasIds != null && empresasIds.length > 0)
			hql.append("and c.empresa_id in(:empresasIds) ");								        

		hql.append("group by c.id, t.id, c.cargahoraria) as totalHorasTreinamento "); 
												
		Query query = getSession().createSQLQuery(hql.toString());
		
		if(cursosIds != null && cursosIds.length > 0)
			query.setParameterList("cursosIds", cursosIds);
			
		if(areasOrganizacionaisIds != null && areasOrganizacionaisIds.length > 0)
			query.setParameterList("areasOrganizacionaisIds", areasOrganizacionaisIds);
		
		if(estabelecimentosIds != null && estabelecimentosIds.length > 0)
			query.setParameterList("estabelecimentosIds", estabelecimentosIds);
		
		if(empresasIds != null && empresasIds.length > 0)
			query.setParameterList("empresasIds", empresasIds);
		
		query.setBoolean("realizada", realizada);
		query.setDate("dataInicio", dataInicio);
		query.setDate("dataFim", dataFim);
		return ((BigDecimal)query.uniqueResult()).intValue();
	}
	
	public Collection<Long> findComAvaliacao(Long empresaId, Date dataIni, Date dataFim)
	{
		Criteria criteria = getSession().createCriteria(Curso.class,"c");
		criteria.createCriteria("c.turmas", "t");
		criteria.createCriteria("c.avaliacaoCursos", "a");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");

		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.ge("t.dataPrevIni", dataIni));
		criteria.add(Expression.le("t.dataPrevIni", dataFim));

		return criteria.list();
	}

	public Integer getCount(Curso curso, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");
		criteria.createCriteria("c.empresasParticipantes", "e", Criteria.LEFT_JOIN);
		criteria.setProjection(Projections.countDistinct("c.id"));

		criteria.add( Expression.or( Expression.eq("c.empresa.id", empresaId), Expression.eq("e.id", empresaId) ) );
		if (curso != null && StringUtils.isNotBlank(curso.getNome()))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + curso.getNome() + "%", Hibernate.STRING));

		return (Integer)criteria.uniqueResult();
	}

	public Collection<Curso> findByFiltro(Integer page, Integer pagingSize, Curso curso, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");
		criteria.createCriteria("c.empresasParticipantes", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.empresa.id"), "projectionEmpresaId");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.cargaHoraria"), "cargaHoraria");

		criteria.setProjection(Projections.distinct(p));
		criteria.add( Expression.or( Expression.eq("c.empresa.id", empresaId), Expression.eq("e.id", empresaId) ) );
		
		if (curso != null && StringUtils.isNotBlank(curso.getNome()))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + curso.getNome() + "%", Hibernate.STRING));

		if(page != null && pagingSize != null){
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Curso.class));

		return criteria.list();
	}

	public Collection<Curso> findCursosSemTurma(Long empresaId)
	{
		DetachedCriteria subQuery = DetachedCriteria.forClass(Turma.class, "t");
        
		ProjectionList pSub = Projections.projectionList().create();
		pSub.add(Projections.property("t.curso.id"), "id");
        
		subQuery.setProjection(pSub);
        subQuery.add(Expression.eq("t.empresa.id", empresaId));
        subQuery.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		Criteria criteria = getSession().createCriteria(Curso.class,"c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.conteudoProgramatico"), "conteudoProgramatico");
		p.add(Projections.property("c.criterioAvaliacao"), "criterioAvaliacao");
		p.add(Projections.property("c.cargaHoraria"), "cargaHoraria");
		p.add(Projections.property("c.percentualMinimoFrequencia"), "percentualMinimoFrequencia");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Subqueries.propertyNotIn("c.id", subQuery));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Curso.class));
		
		return criteria.list();
	}

	public Collection<Curso> findByCompetencia(Long competenciaId, Character tipoCompetencia)
	{
		StringBuilder hql = new StringBuilder("select new Curso(curso.id, curso.nome, curso.cargaHoraria, curso.conteudoProgramatico, curso.criterioAvaliacao, curso.percentualMinimoFrequencia ) ");
		
		if (tipoCompetencia == TipoCompetencia.CONHECIMENTO)
			hql.append("from Conhecimento comp ");
		else if (tipoCompetencia == TipoCompetencia.HABILIDADE) 
			hql.append("from Habilidade comp ");
		else if (tipoCompetencia == TipoCompetencia.ATITUDE) 
			hql.append("from Atitude comp ");
		
		hql.append("inner join comp.cursos curso ");
		hql.append("where comp.id = :competenciaId ");

		hql.append("order by curso.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("competenciaId", competenciaId);

		return query.list();
	}

	public Collection<Curso> findAllByEmpresasParticipantes(Long... empresasIds) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");
		criteria.createCriteria("c.empresasParticipantes", "e", Criteria.LEFT_JOIN);
		criteria.createCriteria("c.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("emp.nome"), "empresaNome");

		criteria.setProjection(Projections.distinct(p));
		criteria.add( Expression.or( Expression.in("c.empresa.id", empresasIds), Expression.in("e.id", empresasIds) ) );
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Curso.class));

		return criteria.list();
	}
	
	public boolean existeEmpresasNoCurso(Long empresaId, Long cursoId) 
	{
		Criteria criteria = getSession().createCriteria(Curso.class,"c");
        criteria.createCriteria("c.empresasParticipantes", "ep", Criteria.LEFT_JOIN);

    	ProjectionList p = Projections.projectionList().create();

    	p.add(Projections.property("c.id"), "id");
    	criteria.setProjection(p);
    	
    	criteria.add( Expression.or( Expression.eq("c.empresa.id", empresaId), Expression.eq("ep.id", empresaId) ) );
    	criteria.add(Expression.eq("c.id", cursoId));

    	return criteria.list().size() > 0;
	}

	public Collection<Empresa> findEmpresasParticipantes(Long cursoId) 
	{
		Criteria criteria = getSession().createCriteria(Empresa.class,"e");
        criteria.createCriteria("e.cursos", "c", Criteria.LEFT_JOIN);

    	ProjectionList p = Projections.projectionList().create();
    	p.add(Projections.property("e.id"), "id");
    	p.add(Projections.property("e.nome"), "nome");
    	criteria.setProjection(p);
    	
    	criteria.add( Expression.eq("c.id", cursoId) );
    	criteria.setResultTransformer(new AliasToBeanResultTransformer(Empresa.class));

    	return criteria.list();
	}

	public Empresa findEmpresaByCurso(Long cursoId) 
	{
		Criteria criteria = getSession().createCriteria(Curso.class,"c");
        criteria.createCriteria("c.empresa", "e", Criteria.LEFT_JOIN);

    	ProjectionList p = Projections.projectionList().create();
    	p.add(Projections.property("e.id"), "id");
    	criteria.setProjection(p);
    	
    	criteria.add( Expression.eq("c.id", cursoId) );
    	criteria.setResultTransformer(new AliasToBeanResultTransformer(Empresa.class));

    	return (Empresa) criteria.uniqueResult();
	}
	
	public boolean existeAvaliacaoAlunoRespondida(Long cursoId, char tipoAvaliacaoCurso) 
	{
		Criteria criteria = null;
		
		if(tipoAvaliacaoCurso == TipoAvaliacaoCurso.AVALIACAO){
			criteria = getSession().createCriteria(ColaboradorQuestionario.class,"cq");
			criteria.createCriteria("cq.turma", "join");
			criteria.add(Expression.isNull("cq.questionario.id"));
		} else {
			criteria = getSession().createCriteria(AproveitamentoAvaliacaoCurso.class,"a");
			criteria.createCriteria("a.colaboradorTurma", "join");
		}
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.count("id"));
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("join.curso.id", cursoId));
		
		if(tipoAvaliacaoCurso != TipoAvaliacaoCurso.AVALIACAO){ 
			criteria.add(Expression.not(Expression.eq("a.valor", 0.0)));
		}
		
		return ((Integer) criteria.uniqueResult()) > 0;
	}

	public Collection<Curso> findByEmpresaIdAndCursosId(Long empresaId,	Long... cursosIds) 
	{
		Criteria criteria = getSession().createCriteria(Curso.class,"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		if(empresaId != null && !empresaId.equals(-1L))
			criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		if(LongUtil.arrayIsNotEmpty(cursosIds))
			criteria.add(Expression.in("c.id", cursosIds));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Curso.class));

		return criteria.list();
	}
	
	public Collection<Curso> somaDespesasPorCurso(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds) 
	{
		Criteria criteria = getSession().createCriteria(Curso.class,"c");
		criteria.createCriteria("c.turmas", "t");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.alias(Projections.groupProperty("c.id"), "id"));
		p.add(Projections.alias(Projections.groupProperty("c.nome"), "nome"));
		p.add(Projections.sum("t.custo"), "totalDespesas");
		criteria.setProjection(p);

		criteria.add(Expression.eq("t.realizada", true));
		criteria.add(Expression.ge("t.dataPrevIni", dataIni));
		criteria.add(Expression.le("t.dataPrevFim", dataFim));
		criteria.add(Expression.ne("t.custo", 0.0));
		
		if (LongUtil.arrayIsNotEmpty(empresaIds))
			criteria.add(Expression.in("c.empresa.id", empresaIds));
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			criteria.add(Expression.in("c.id", cursoIds));
		
		criteria.addOrder(Order.desc("totalDespesas"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Curso.class));
		
		return  criteria.list();
	}
}