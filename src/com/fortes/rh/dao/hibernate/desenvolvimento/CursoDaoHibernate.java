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

	public IndicadorTreinamento findSomaCustoEHorasTreinamentos(Date dataIni, Date dataFim, Long empresaId, Boolean realizada)
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.desenvolvimento.IndicadorTreinamento(sum(t.custo), sum(curso.cargaHoraria)) ");
		hql.append("from Turma t ");
		hql.append("join t.curso curso ");
		hql.append("where t.dataPrevIni between :dataIni and :dataFim ");
		hql.append("and curso.empresa.id = :empresaId ");

		if(realizada != null)
			hql.append("and t.realizada = :realizada ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setLong("empresaId", empresaId);
		
		if(realizada != null)
			query.setBoolean("realizada", realizada);

		return (IndicadorTreinamento)query.uniqueResult();
	}

	public Integer findQtdColaboradoresInscritosTreinamentos(Date dataIni, Date dataFim, Long empresaId)
	{
		StringBuilder hql = new StringBuilder("select count(ct.id) ");
		hql.append("from ColaboradorTurma ct ");
		hql.append("join ct.turma t ");
		hql.append("where t.dataPrevIni between :dataIni and :dataFim ");
		hql.append("and t.empresa.id = :empresaId ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setLong("empresaId", empresaId);

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

	public Integer countTreinamentos(Date dataIni, Date dataFim, Long empresaId, Boolean realizado)
	{
		Criteria criteria = getSession().createCriteria(Turma.class, "t");
		criteria.createCriteria("t.curso", "c");
		criteria.setProjection(Projections.rowCount());

		criteria.add(Expression.ge("t.dataPrevIni", dataIni));
		criteria.add(Expression.le("t.dataPrevIni", dataFim));
		if (realizado != null)
			criteria.add(Expression.eq("realizada", realizado));
		criteria.add(Expression.eq("c.empresa.id", empresaId));

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
		criteria.setProjection(Projections.rowCount());

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		if (curso != null && StringUtils.isNotBlank(curso.getNome()))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + curso.getNome() + "%", Hibernate.STRING));

		return (Integer)criteria.uniqueResult();
	}

	public Collection<Curso> findByFiltro(Integer page, Integer pagingSize, Curso curso, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.empresa.id"), "projectionEmpresaId");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.cargaHoraria"), "cargaHoraria");

		criteria.setProjection(p);
		criteria.add(Expression.eq("c.empresa.id", empresaId));
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
}