package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;
import java.util.Date;

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
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.IndicadorTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Empresa;
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
	
	public Collection<IndicadorTreinamento> findIndicadorHorasTreinamentos(Date dataIni, Date dataFim, Long[] empresaIds, Long[] areasIds, Long[] cursoIds)
	{
		StringBuilder hql = new StringBuilder("select new IndicadorTreinamento(tur.id, count(ct.id), ct3.qtdeInscritosTotal, cur.cargaHoraria/60, tur.custo) ");
		hql.append("from ColaboradorTurma ct ");
		hql.append("inner join ct.turma tur ");
		hql.append("inner join tur.curso cur ");
		hql.append("inner join ct.colaborador col ");
		hql.append("inner join hc.historicoColaboradors hc "); 
		hql.append("inner join ( ");
		hql.append("   select ct2.turma.id, count(ct2.id) as qtdeInscritosTotal from colaboradorTurma ct2 group by ct2.turma.id ");
		hql.append(") as ct3 on ct3.turma_id = tur.id ");
		hql.append("where hc.data = (select max(hc2.data) from HistoricoColaborador hc2 where hc2.colaborador.id = col.id) ");
		hql.append("group by tur.id, cur.cargahoraria, tur.custo, ct3.qtde_inscritos_total ");
		hql.append("order by tur.id ");
		
		
		
		
		
		
		
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.desenvolvimento.IndicadorTreinamento(c.id, cast((c.cargaHoraria/60 * count(ct.id)) as double)) ");
		hql.append("from Curso c ");
		hql.append("left join c.turmas t ");
		hql.append("left join t.colaboradorTurmas ct "); 
		hql.append("inner join ct.colaborador co "); 
		hql.append("inner join co.historicoColaboradors hc ");
		hql.append("where c.empresa.id in (:empresaIds) ");
		hql.append("and co.empresa.id in (:empresaIds) ");
		hql.append("and t.dataPrevIni between :dataIni and :dataFim "); 
		hql.append("and t.realizada = true ");
		hql.append("and hc.data = ( ");
		hql.append("	select max(hc2.data) ");
		hql.append("	from HistoricoColaborador as hc2 ");
		hql.append("	where hc2.colaborador.id = co.id ");
		hql.append("	and hc2.status = :status ");
		hql.append(") ");
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			hql.append("and c.id in (:cursoIds) ");
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			hql.append("and hc.areaOrganizacional.id in (:areasIds) ");
		
		hql.append("group by c.id, c.cargaHoraria ");
		hql.append("order by c.id ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setParameterList("empresaIds", empresaIds);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			query.setParameterList("cursoIds", cursoIds, Hibernate.LONG);
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		return query.list();
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
		criteria.setProjection(Projections.rowCount());

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

		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Curso.class));

		return criteria.list();
	}

	public Collection<Long> findTurmas(Long empresaId, Long[] cursoIds)
	{
		Criteria criteria = getSession().createCriteria(Curso.class,"c");
		criteria.createCriteria("c.turmas", "t");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("t.id"), "id");

		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.add(Expression.in("c.id", cursoIds));


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

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");

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
}