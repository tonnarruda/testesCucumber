package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

@SuppressWarnings("unchecked")
public class TurmaDaoHibernate extends GenericDaoHibernate<Turma> implements TurmaDao
{

    public Turma findByIdProjection(Long turmaId)
    {
        Criteria criteria = getSession().createCriteria(Turma.class,"t");
        criteria.createCriteria("t.curso", "c");
        criteria.createCriteria("t.empresa", "e");
        criteria.createCriteria("t.avaliacaoTurma", "a", Criteria.LEFT_JOIN);

        ProjectionList p = Projections.projectionList().create();

        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
        p.add(Projections.property("t.dataPrevIni"), "dataPrevIni");
        p.add(Projections.property("t.dataPrevFim"), "dataPrevFim");
        p.add(Projections.property("t.instrutor"), "instrutor");
        p.add(Projections.property("t.custo"), "custo");
        p.add(Projections.property("t.horario"), "horario");
        p.add(Projections.property("t.instituicao"), "instituicao");
        p.add(Projections.property("t.qtdParticipantesPrevistos"), "qtdParticipantesPrevistos");
        p.add(Projections.property("t.realizada"), "realizada");
        p.add(Projections.property("c.id"), "cursoId");
        p.add(Projections.property("c.nome"), "cursoNome");
        p.add(Projections.property("c.cargaHoraria"), "projectionCursoCargaHoraria");
        p.add(Projections.property("e.id"), "projectionEmpresaId");
        p.add(Projections.property("a.id"), "projectionAvaliacaoTurmaId");
        p.add(Projections.property("a.questionario.id"), "projectionAvaliacaoTurmaQuestionarioId");

        criteria.setProjection(p);
        criteria.add(Expression.eq("t.id", turmaId));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

        Turma turma = new Turma();
        Collection<Turma> turmas = criteria.list();
        if(turmas != null && !turmas.isEmpty())
            turma = (Turma) turmas.toArray()[0];

        return turma;
    }

    public Collection<Turma> getTurmaFinalizadas(Long cursoId)
	{
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
        criteria.createCriteria("t.curso", "c");

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
        criteria.setProjection(p);

        criteria.add(Expression.eq("c.id", cursoId));
        criteria.add(Expression.eq("t.realizada", true));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

        return criteria.list();
	}

	public List filtroRelatorioByAreas(LinkedHashMap parametros)
	{
		String hql = " select ao.nome, c.nome, t.dataPrevFim, t.custo," +
					 " (" +
					 "		select count(ct2.id) " +
					 "		from ColaboradorTurma ct2 " +
					 "		inner join ct2.turma t2 " +
					 "		where t2.id = t.id " +
					 "	), ao.id " +
					 " from Turma t " +
					 " inner join t.curso c " +
					 " inner join t.colaboradorTurmas ct " +
					 " inner join ct.colaborador co " +
					 " inner join co.historicoColaboradors hc " +
					 " inner join hc.areaOrganizacional ao " +
					 " where ao.id in (:areasId) " +
					 " and hc.data = (" +
					 "					select max(hc2.data) " +
					 "					from HistoricoColaborador hc2 " +
					 "					where hc2.data <= t.dataPrevFim " +
					 "					and hc2.status = :status " +
					 "					and hc2.colaborador.id = co.id " +
					 "				  ) " +
					 " and t.dataPrevFim between :dataIni and :dataFim " +
					 " order by ao.id,c.id,t.id " ;

		Query query = getSession().createQuery(hql);
		query.setDate("dataIni", (Date)parametros.get("dataIni"));
		query.setDate("dataFim", (Date)parametros.get("dataFim"));
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		query.setParameterList("areasId",(Collection<Long>)parametros.get("areas"), Hibernate.LONG);

		return query.list();
	}

	public List filtroRelatorioByColaborador(LinkedHashMap parametros)
	{
		StringBuilder hql = new StringBuilder(" select ao.nome, c.nome, t.dataPrevFim, t.custo, ");
					 hql.append(" (" );
					 hql.append("		select count(ct2.id) " );
					 hql.append("		from ColaboradorTurma ct2 " );
					 hql.append("		inner join ct2.turma t2 " );
					 hql.append("		where t2.id = t.id " );
					 hql.append("	), ao.id " );
					 hql.append(" from Turma t " );
					 hql.append(" inner join t.curso c " );
					 hql.append(" inner join t.colaboradorTurmas ct " );
					 hql.append(" inner join ct.colaborador co " );
					 hql.append(" inner join co.historicoColaboradors hc " );
					 hql.append(" inner join hc.areaOrganizacional ao " );
					 hql.append(" where co.id = (:colaboradorId) " );
					 hql.append(" and hc.data = (" );
					 hql.append("					select max(hc2.data) " );
					 hql.append("					from HistoricoColaborador hc2 " );
					 hql.append("					where hc2.data <= t.dataPrevFim and hc2.status = :status " );
					 hql.append("					and hc2.colaborador.id = co.id " );
					 hql.append("			) " );
					 hql.append(" and t.dataPrevFim between :dataIni and :dataFim " );
					 hql.append(" order by ao.id,c.id,t.id " );

			Query query = getSession().createQuery(hql.toString());
			query.setDate("dataIni", (Date)parametros.get("dataIni"));
			query.setDate("dataFim", (Date)parametros.get("dataFim"));
			query.setInteger("status", StatusRetornoAC.CONFIRMADO);

			Colaborador colaborador = null;
			colaborador = (Colaborador)parametros.get("colaborador");
			query.setLong("colaboradorId", colaborador.getId());

			return query.list();
	}

	public Collection<Turma> findAllSelect(Long cursoId)
	{
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
        criteria.createCriteria("t.curso", "c");
        criteria.createCriteria("t.avaliacaoTurma", "a", Criteria.LEFT_JOIN);
        criteria.createCriteria("a.questionario", "q", Criteria.LEFT_JOIN);

        ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
        p.add(Projections.property("t.dataPrevIni"), "dataPrevIni");
        p.add(Projections.property("t.dataPrevFim"), "dataPrevFim");
        p.add(Projections.property("t.empresa.id"), "projectionEmpresaId");
        p.add(Projections.property("q.id"), "projectionAvaliacaoTurmaQuestionarioId");

        p.add(Projections.property("t.instrutor"), "instrutor");
        p.add(Projections.property("t.realizada"), "realizada");

        criteria.setProjection(p);

        criteria.add(Expression.eq("c.id", cursoId));

        criteria.addOrder(Order.asc("t.descricao"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

        return criteria.list();
	}

	public Collection<Turma> findTurmas(Integer page, Integer pagingSize, Long cursoId)
	{
		Criteria criteria = prepareCriteriaTurmas();

		criteria.add(Expression.eq("c.id", cursoId));

		if(pagingSize != 0)
        {
        	criteria.setFirstResult(((page - 1) * pagingSize));
        	criteria.setMaxResults(pagingSize);
        }

		criteria.addOrder(Order.desc("t.dataPrevIni"));
		criteria.addOrder(Order.asc("t.descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

		return criteria.list();
	}

	private Criteria prepareCriteriaTurmas()
	{
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
		criteria.createCriteria("t.curso", "c");
		criteria.createCriteria("t.avaliacaoTurma", "a", Criteria.LEFT_JOIN);
		criteria.createCriteria("a.questionario", "q", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("t.id"), "id");
		p.add(Projections.property("t.descricao"), "descricao");
		p.add(Projections.property("t.dataPrevIni"), "dataPrevIni");
		p.add(Projections.property("t.dataPrevFim"), "dataPrevFim");
		p.add(Projections.property("t.empresa.id"), "projectionEmpresaId");
		p.add(Projections.property("q.id"), "projectionAvaliacaoTurmaQuestionarioId");

		p.add(Projections.property("t.instrutor"), "instrutor");
		p.add(Projections.property("t.realizada"), "realizada");

		criteria.setProjection(p);

		return criteria;
	}

	public Collection<Turma> findPlanosDeTreinamento(int page, int pagingSize, Long cursoId, Date dataIni, Date dataFim, Boolean realizada)
	{
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
		criteria.createCriteria("t.curso", "c");

		ProjectionList p = Projections.projectionList().create();

        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
        p.add(Projections.property("t.dataPrevIni"), "dataPrevIni");
        p.add(Projections.property("t.dataPrevFim"), "dataPrevFim");
        p.add(Projections.property("t.empresa.id"), "projectionEmpresaId");
        p.add(Projections.property("t.horario"), "horario");
        p.add(Projections.property("t.qtdParticipantesPrevistos"), "qtdParticipantesPrevistos");
        p.add(Projections.property("t.custo"), "custo");
        p.add(Projections.property("t.instrutor"), "instrutor");
        p.add(Projections.property("t.realizada"), "realizada");
        p.add(Projections.property("c.id"), "cursoId");
        p.add(Projections.property("c.nome"), "cursoNome");
        p.add(Projections.property("c.cargaHoraria"), "projectionCursoCargaHoraria");

        criteria.setProjection(p);

		if (cursoId != null)
			criteria.add(Expression.eq("c.id", cursoId));

		if (dataIni != null)
			criteria.add(Expression.ge("t.dataPrevIni", dataIni));

		if (dataFim != null)
			criteria.add(Expression.le("t.dataPrevFim", dataFim));

        if (realizada != null)
        	criteria.add(Expression.eq("t.realizada", realizada));

        if(pagingSize != 0)
        {
        	criteria.setFirstResult(((page - 1) * pagingSize));
        	criteria.setMaxResults(pagingSize);
        }

        criteria.addOrder(Order.desc("t.dataPrevIni"));
        criteria.addOrder(Order.asc("c.nome"));
        criteria.addOrder(Order.asc("t.descricao"));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

        return criteria.list();
	}

	public Integer countPlanosDeTreinamento(Long cursoId, Date dataIni, Date dataFim, Boolean realizada)
	{
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
		criteria.createCriteria("t.curso", "c");

		criteria.setProjection(Projections.rowCount());

		if (cursoId != null)
			criteria.add(Expression.eq("c.id", cursoId));

		if (dataIni != null)
			criteria.add(Expression.ge("t.dataPrevIni", dataIni));

		if (dataFim != null)
			criteria.add(Expression.le("t.dataPrevFim", dataFim));

		if (realizada != null)
			criteria.add(Expression.eq("t.realizada", realizada));

		return (Integer) criteria.uniqueResult();
	}

	public void updateRealizada(Long turmaId, boolean realizada)throws Exception
	{
		String hql = "update Turma set realizada = :realizada where id = :turmaId";

		Query query = getSession().createQuery(hql);
		query.setLong("turmaId", turmaId);
		query.setBoolean("realizada", realizada);

		query.executeUpdate();
	}

	public Collection<Turma> findByFiltro(Date dataPrevIni, Date dataPrevFim, Boolean realizada, Long empresaId)
	{
        Criteria criteria = getSession().createCriteria(Turma.class,"t");
        criteria.createCriteria("t.curso", "c");
        criteria.createCriteria("t.empresa", "e");

        ProjectionList p = Projections.projectionList().create();

        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
        p.add(Projections.property("c.nome"), "cursoNome");

        criteria.setProjection(p);
        criteria.add(Expression.eq("e.id", empresaId));

		if (dataPrevIni != null)
			criteria.add(Expression.ge("t.dataPrevIni", dataPrevIni));

		if (dataPrevFim != null)
			criteria.add(Expression.le("t.dataPrevFim", dataPrevFim));

		if (realizada != null)
			criteria.add(Expression.eq("t.realizada", realizada));

		criteria.addOrder(Order.desc("t.dataPrevIni"));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("t.descricao"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

        return criteria.list();
	}

	public Collection<Turma> findByIdProjection(Long[] ids)
	{
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
        criteria.createCriteria("t.curso", "c");
        criteria.createCriteria("t.empresa", "e");

        ProjectionList p = Projections.projectionList().create();

        p.add(Projections.property("t.id"), "id");
        p.add(Projections.property("t.descricao"), "descricao");
        p.add(Projections.property("t.dataPrevIni"), "dataPrevIni");
        p.add(Projections.property("t.dataPrevFim"), "dataPrevFim");
        p.add(Projections.property("t.instrutor"), "instrutor");
        p.add(Projections.property("t.horario"), "horario");
        p.add(Projections.property("c.id"), "cursoId");
        p.add(Projections.property("c.nome"), "cursoNome");

        criteria.setProjection(p);
        criteria.add(Expression.in("t.id", ids));

        criteria.addOrder(Order.asc("t.dataPrevIni"));
        criteria.addOrder(Order.asc("c.nome"));
        criteria.addOrder(Order.asc("t.descricao"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Turma.class));

		return criteria.list();
	}
	
	 public Integer quantidadeParticipantesPrevistos(Date dataIni, Date dataFim, Long empresaId)
	 {
		Criteria criteria = getSession().createCriteria(Turma.class,"t");
		criteria.createCriteria("t.empresa", "e");

        criteria.setProjection(Projections.sum("t.qtdParticipantesPrevistos"));
        criteria.add(Expression.ge("t.dataPrevIni", dataIni));
        criteria.add(Expression.le("t.dataPrevFim", dataFim));
        criteria.add(Expression.eq("e.id", empresaId));

        Integer valor = (Integer) criteria.uniqueResult();
        if(valor == null)
        	return 0;
        else
        	return valor;
	  }
	

	public Collection<Turma> findTurmaPresencaMinima (Collection<Long> turmaIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Turma(t.id,(count(dt.id) * (c.percentualMinimoFrequencia / 100))) ");
		hql.append("from DiaTurma dt ");
		hql.append("join dt.turma t ");
		hql.append("join t.curso c ");
		
		if (turmaIds != null && !turmaIds.isEmpty())
			hql.append("where  t.id in (:turmaId) ");
		
		hql.append("group by t.id, c.percentualMinimoFrequencia ");
		hql.append("order by t.id ");
		
		Query query = getSession().createQuery(hql.toString());
		
		if (turmaIds != null && !turmaIds.isEmpty())
			query.setParameterList("turmaId", turmaIds, Hibernate.LONG);

		return query.list();	
	}
 
}